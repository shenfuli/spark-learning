package org.training.spark.core

import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Function:Spark 按照指定的文件名称输出
  * Author:Created by fuli.shen on 2017/2/7.
  * Reference：
  * [1]  spark 点滴：多路输出，自定义分区
  * http://blog.csdn.net/godspeedlaile9/article/details/49683049
  * [2] Spark 多文件输出（MultipleOutputFormat)
  * https://www.iteblog.com/archives/1281.html
  */
class RDDMultipleTextOutputFormat extends MultipleTextOutputFormat[Any, Any] {
  override def generateFileNameForKeyValue(key: Any, value: Any, name: String): String = "part-" + key.asInstanceOf[String]
}

object MultiOutFormat {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("MultiOutFormat").setMaster("local")
    val sc = new SparkContext(conf)
    //    val parallelize = sc.parallelize(List(("w", "www 2"), ("b", "blog 2"), ("c", "com 1"), ("w", "bt 2"), ("z", "z1 2")))
    //    val keyCount = parallelize.map(value => (value._1, 1)).reduceByKey((n1, n2) => n1 + n2).count()
    //    parallelize.map(value => (value._1, value._2)).partitionBy(new HashPartitioner(keyCount.toInt))
    //      .saveAsHadoopFile("hdfs://10.4.1.1:9000/data/multi", classOf[String], classOf[String], classOf[RDDMultipleTextOutputFormat])
    val fileName = "part-w"
    val inputFile = "hdfs://10.4.1.1:9000/data/multi/" + fileName
    val outputFile = "hdfs://10.4.1.1:9000/data/multi/part-" + fileName + ".svm"
    val textRDD = sc.textFile(inputFile)
    textRDD.map(line => line.split("\t", 2)(1)).coalesce(1).saveAsTextFile(outputFile)
    sc.stop()
  }
}
