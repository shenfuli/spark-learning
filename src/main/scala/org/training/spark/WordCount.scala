package org.training.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 统计单词出现次数
  * Created by fuli.shen on 2016/10/25.
  */
object WordCount {
  def main(args: Array[String]) {

    // /user/fuli.shen/data/wordcount/input /user/fuli.shen/data/wordcount/output
    if (args.length != 2) {
      print("usage:org.training.spark.WordCount <inputpath><outputpath>")
      return
    }

    val conf = new SparkConf().setAppName("WordCount")
    val sc = new SparkContext(conf)

    val textFileRDD = sc.textFile(args(0))
    val wordRDD = textFileRDD.flatMap(line => line.split("\\t")).map(word => (word, 1)).reduceByKey((word1, word2) => word1 + word2)

    wordRDD.saveAsTextFile(args(1))

    sc.stop()
  }
}
