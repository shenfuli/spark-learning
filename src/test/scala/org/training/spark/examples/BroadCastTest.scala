package org.training.spark.examples

import org.apache.spark.{SparkConf, SparkContext}

/**
  * BroadCast 广播变量的使用,计算一个Set集合的长度
  * Created by fuli.shen on 2016/10/25.
  */
object BroadCastTest {

  def main(args: Array[String]): Unit = {
    // 初始化sparkContext
    val conf: SparkConf = new SparkConf().setAppName("BroadCastTest").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)


    val data = Set(1, 2, 3, 4, 5, 6, 7, 8) //数据我们假想为128MB

    // 1-6 的数字分布在2个partition
    val rdd = sc.parallelize(1 to 6, 2)

    // 通过broadcast 把data广播出去，数据分发到executor上，同一个excutor下的task可以共享data
    val broadcast = sc.broadcast(data)
    rdd.foreach(num => {
      //println(num + "\t" + data.size)
      println(num + "\t" + broadcast.value.size)
    })
    sc.stop()
  }
}
