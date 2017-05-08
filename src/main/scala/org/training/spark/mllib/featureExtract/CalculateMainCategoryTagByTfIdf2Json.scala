package org.training.spark.mllib.featureExtract
import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer
/**
  * Created by fuli.shen on 2017/5/4.
  */
case class Doc_TfIdf(word: String, count: Int, total_word_count: Int, doc_count: Int, total_doc_count: Int, tf: Double, idf: Double, tfidf: Double)

object CalculateMainCategoryTagByTfIdf2Json {
  def main(args: Array[String]): Unit = {

    if (args.length != 7) {
      println("Usage: CalculateMainCategoryByTfIdf2Json <appName><catPostfix><totalPcatInputData><pcatInputData><total_doc_count><topN><pcatOutputData>")
      sys.exit(1)
    }
    val Array(appName,catPostfix,totalPcatInputData,pcatInputData,total_doc_count,topN,pcatOutputData) = args

    val conf = new SparkConf().setAppName(appName)
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    // 0: 创建所有类别ID和Documents Words 的映射关系
    val totalPcatInputDataJSON = sqlContext.read.json(totalPcatInputData)

    val totalIdWordsMap: Map[String, Map[String, Int]] = totalPcatInputDataJSON.select("id", "name").map(row => {
      val id = row.getAs[String]("id")
      val name = row.getAs[String]("name")
      var wordsMap: Map[String, Int] = Map(name -> 1)
      (id, wordsMap)
    }).reduceByKey((wordsMap1, wordsMap2) => {
      val mergeWordsMap = wordsMap1 ++ wordsMap2
      mergeWordsMap
    }).map(idMergeWordsMap => {
      (idMergeWordsMap._1, idMergeWordsMap._2)
    }).collect().toMap
    //Map(cat-10000 -> Map(台湾 -> 1, 建设 -> 1, 国家 -> 1, 孩子 -> 1), cat-30000 -> Map(民警 -> 1, 医院 -> 1, 北京 -> 1, 孩子 -> 1))
    val totalIdWordsMapBroadCast = sc.broadcast(totalIdWordsMap)

    // 1: 加载单个文档ID的数据且计算 该文档ID下每个单词在其他文档出现的文档记录数
    val pcatJsonDF = sqlContext.read.json(pcatInputData)
    val pcatJsonRDD = pcatJsonDF.map(row => WordCount(row.getAs[String]("name"), row.getAs[Long]("count").toInt)).repartition(3).mapPartitions(wordCountIter => {
      val totalIdWordsMapValue = totalIdWordsMapBroadCast.value
      val totalWordsMap = totalIdWordsMapValue.values

      val buffer = new ArrayBuffer[(String, Int)]()
      wordCountIter.foreach(wordCount => {
        val word = wordCount.name
        var doc_count = 0 //包含当前word的总doc数
        totalWordsMap.foreach(words => {
          doc_count += words.get(word).getOrElse(0)
        })
        val word_docCount = (word -> doc_count)
        buffer += word_docCount
      })
      buffer.iterator
    }).map(x => (x._1, x._2)).reduceByKey((n1, n2) => n1 + n2)
    import sqlContext.implicits._
    val pcatJsonRDD2DF = pcatJsonRDD.map(x => WordCount(x._1, x._2)).toDF()
    pcatJsonRDD2DF.registerTempTable("words_DocCount")
    //2:统计当前doc中总word数
    pcatJsonDF.registerTempTable("words")
    val pcatTotalWordCountDF = sqlContext.sql("select  sum(count) as total_word_count  from words ")
    val total_wordCount = pcatTotalWordCountDF.first().getLong(0)

    //3:计算当前doc的tfidf相关的指标数据
    val wordsJoinDF = sqlContext.sql(s"select w.name as word,w.count as count, $total_wordCount as total_word_count ,wd.count as doc_count ,$total_doc_count as total_doc_count from words_DocCount wd join  words w on(wd.name=w.name)   ").toDF()

    val wordsOutputResultDF = wordsJoinDF.map(row => {
      val word = row.getAs[String]("word")
      //当前doc出现的单词
      val count = row.getAs[Long]("count").toInt
      //当前word在当前doc中出现次数
      val total_word_count = row.getAs[Int]("total_word_count")
      //当前doc中总word数
      val doc_count = row.getAs[Int]("doc_count")
      //包含当前word的总doc数
      val total_doc_count = row.getAs[Int]("total_doc_count")
      //全部doc数
      val tf = 1.0 * count / total_word_count
      //计算word的tf=t在d中出现的次数/d中单词总数
      val idf = Math.log(1.0 * (total_doc_count + 1) / (doc_count + 1))
      //文档总数N/t在所有文档集合中出现的文档数,然后取对数
      val tfidf = tf * idf
      Doc_TfIdf(word, count, total_word_count, doc_count, total_doc_count, tf, idf, tfidf)
    }).toDF()

    wordsOutputResultDF.registerTempTable("Doc_TfIdf")
    val docTfIdfDF = sqlContext.sql(s"select * from Doc_TfIdf order by tfidf desc limit ${topN.toInt}")
    docTfIdfDF.coalesce(1).write.mode(SaveMode.Overwrite).json(pcatOutputData)
    /**
      * |word|count|total_word_count|doc_count|total_doc_count|                 tf|               idf|             tfidf|
      * +----+-----+----------------+---------+---------------+-------------------+------------------+------------------+
      * |  民警|    8|              23|        1|             40|0.34782608695652173|3.0204248861443626|1.0505825690936912|
      * |  医院|    6|              23|        1|             40| 0.2608695652173913|3.0204248861443626|0.7879369268202685|
      * |  北京|    5|              23|        1|             40|0.21739130434782608|3.0204248861443626| 0.656614105683557|
      * |  孩子|    4|              23|        2|             40|0.17391304347826086| 2.614959778036198|0.4547756135715127|
      * +----+-----+----------------+---------+---------------+-------------------+------------------+------------------+
      */
    sc.stop()
  }
}
