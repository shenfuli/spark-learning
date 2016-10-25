package org.training.spark.examples

import org.apache.spark.rdd.RDD
import org.apache.spark.{Accumulator, SparkContext, SparkConf}

/**
  * Created by fuli.shen on 2016/10/25.
  */
object AccumulatorTest {


  def main(args: Array[String]) {
    // 初始化sparkContext
    val conf: SparkConf = new SparkConf().setAppName("AccumulatorTest").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)

    //定义计数的counter
    val total = sc.accumulator(0L, "total")

    // 统计1-10 在5个partition情况运行，分布式环境下统计总数
    val map = sc.parallelize(1 to 12, 5).map(num => {
      total += 1
    })
    map.collect()

    println("total nums is:"+(total.value))
    sc.stop()
  }
}
