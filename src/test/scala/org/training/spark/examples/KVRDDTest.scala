package org.training.spark.examples

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Key/Value 类型RDD的测试
  * 对比 reduceByKey/groupByKey/sortByKey 区别
  * Created by fuli.shen on 2016/10/25.
  */
object KVRDDTest {

  def main(args: Array[String]) {


    // 初始化sparkContext
    val conf: SparkConf = new SparkConf().setAppName("KVRDDTest").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)

    //parallelize tuple 数据转为RDD
    val petsRDD = sc.parallelize(List(("cat", 1), ("dog", 2), ("cat", 3))).cache()
    //reduceByKey 在map段进行本地的combine
    petsRDD.reduceByKey((v1, v2) => v1 + v2).collect() //Array((cat,4), (dog,2))
    petsRDD.groupByKey().collect() //Array((cat,CompactBuffer(1, 3)), (dog,CompactBuffer(2)))
    petsRDD.sortByKey().collect() // Array((cat,1), (cat,3), (dog,2))

    sc.stop()
  }
}
