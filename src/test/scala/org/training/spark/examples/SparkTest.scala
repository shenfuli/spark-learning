package org.training.spark.examples

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fuli.shen on 2016/10/25.
  */
object SparkTest {

  def main(args: Array[String]) {

    // 初始化sparkContext
    val conf: SparkConf = new SparkConf().setAppName("SparkTest").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)

    //parallelize 把list数组转为RDD
    val rdd = sc.parallelize(List(1, 2, 3), 3)

    val square = rdd.map(num => num * num) // Array(1, 4, 9)
    // 满足条件的数据显示
    val filter = square.filter(num => num % 2 == 0) //4

    //一个元素生成多个
    val nums = filter.flatMap(num => 1 to num) //Array(1, 2, 3, 4)

    //nums 通过collect保存RDD到本地集合
    //take 从RDD中获取两条记录
    //通过foreach 对每条记录进行打印
    nums.collect().take(2).foreach(num => print(num + "\t"))
    sc.stop()
  }
}
