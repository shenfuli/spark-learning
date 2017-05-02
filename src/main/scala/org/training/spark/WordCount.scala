package org.training.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 统计单词出现次数
  * [hadoop@slave3 data]$ hdfs dfs -cat /user/fuli.shen/data/wordcount/input/
  * hello   you     hello   me
  * hello   you     hello   china
  *
  * Created by fuli.shen on 2016/10/25.
  */
object WordCount {
  def main(args: Array[String]) {

    // /user/fuli.shen/data/wordcount/input /user/fuli.shen/data/wordcount/output
    if (args.length != 2) {
      print("usage:org.training.spark.WordCount <inputpath><outputpath>")
      return
    }
    val Array(input,output) =args ;
    val conf = new SparkConf().setAppName("WordCount")
    val sc = new SparkContext(conf)
    val textFileRDD = sc.textFile(input)
    //res2: Array[(String, Int)] = Array((hello,4), (you,2), (me,1), (china,1))
    val wordRDD = textFileRDD.flatMap(line => line.split("\\t")).map(word => (word, 1)).reduceByKey((word1, word2) => word1 + word2).map(word=>(word._2,word._1)).sortByKey(false).map(word=>(word._2,word._1))
    wordRDD.saveAsTextFile(output)
    sc.stop()
  }
}
