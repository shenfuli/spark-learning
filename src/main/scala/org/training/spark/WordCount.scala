package org.training.spark

import org.apache.spark.sql.SparkSession
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
    val Array(input, output) = args;
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("WordCount")
      .getOrCreate()
    //val wordRDD = textFileRDD.flatMap(line => line.split("\\t")).map(word => (word, 1)).reduceByKey((word1, word2) => word1 + word2).map(word=>(word._2,word._1)).sortByKey(false).map(word=>(word._2,word._1))
    val textFileRDD = spark.sparkContext.textFile(input)
    val wordRDD = textFileRDD.flatMap(line => line.split("\\t"))
      .map(word => (word, 1))
      .reduceByKey((word1, word2) => word1 + word2)
    wordRDD.saveAsTextFile(output)
    spark.stop()
  }
}
