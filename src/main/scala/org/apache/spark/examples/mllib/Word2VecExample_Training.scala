package org.apache.spark.examples.mllib

// $example on$
import org.apache.spark.mllib.feature.Word2Vec
// $example off$
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 处理数据为中文 训练
  *
  * hdfs://10.4.1.1:9000/news/w2v/sampling  在hdfs 上的数据格式：
  * 我 爱 北京 天安门
  * 我 爱 北京 长城
  */
object Word2VecExample_Training {

  // create a function to tokenize each document
  def tokenize(doc: String): Seq[String] = {
    doc.split("\\s").filter(token => token.size >= 1).toSeq // 注意： ONLINE 环境 设置 token.size>=2
  }

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Word2VecExample_Training example").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val textFile = sc.textFile("hdfs://10.4.1.1:9000/news/w2v/sampling")
    // check that our tokenizer achieves the same result as all the steps above
    //println(textFile.flatMap(doc => tokenize(doc)).distinct.count)
    // word2vec 原始数据分词、过滤
    val tokens = textFile.map(doc => tokenize(doc))
    //word2vec 模型训练
    //val word2vec = new Word2Vec().setVectorSize(200).setMinCount(1).setWindowSize(5)
    val word2vec = new Word2Vec()
      .setVectorSize(5) // 每个词向量的特征长度
      .setMinCount(1) // 每个词至少出现MinCount
      .setWindowSize(5)
    val word2vecModel = word2vec.fit(tokens)

    println("-------------------------------1.1.【爱】  关键词的同义词-----------------------------------")
    val synonyms = word2vecModel.findSynonyms("爱", 5)
    for ((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }
    println("-------------------------------1.2.【北京】  关键词的同义词-----------------------------------")
    val synonyms_2 = word2vecModel.findSynonyms("北京", 5)
    for ((synonyms_2, cosineSimilarity) <- synonyms_2) {
      println(s"$synonyms_2 $cosineSimilarity")
    }

    println("-------------------------------2.0.所有词对应的向量表示-----------------------------------")
    val vectors = word2vecModel.getVectors
    vectors.keySet.foreach(key => {
      val value = vectors.getOrElse(key, 0).asInstanceOf[Array[Float]]
      println(key + "->" + value.toList)
    })
    //  保存模型
    word2vecModel.save(sc,"hdfs://10.4.1.1:9000/news/W2V/News_W2V_Model")
    sc.stop()
  }
}

/*
程序输出结果：
-------------------------------1.1.【爱】  关键词的同义词-----------------------------------
长城 0.03517408774640109
我 0.010732922848989943
天安门 -0.018858527423041442
北京 -0.04326692491362484
-------------------------------1.2.【北京】  关键词的同义词-----------------------------------
天安门 -0.02630166985648216
我 -0.060841401740218064
长城 -0.07886706494585992
爱 -0.09392086956252267
-------------------------------2.0.所有词对应的向量表示-----------------------------------
北京->List(-0.059254404, 0.093044914, 0.014430013, -0.0024602984, -0.07765233)
爱->List(0.010509529, -0.035960928, 0.03674319, -0.012174789, 0.03171137)
我->List(0.017137274, 0.018458888, -0.041580245, -0.037729222, 0.08425997)
长城->List(-0.022055076, -0.05837336, -0.01864883, -0.07283274, 0.06480397)
天安门->List(0.016040538, -0.093616754, -0.08548939, 0.08511808, -0.083756626)
  */