package org.training.spark.examples

import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * Created by fuli.shen on 2016/10/25.
  */
object JoinTest {

  def main(args: Array[String]) {

    // 初始化sparkContext
    val conf: SparkConf = new SparkConf().setAppName("JoinTest").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)


    val visits = sc.parallelize(List(("index1.html", "ip1"), ("index2.html", "ip2"), ("index3.html", "ip3"))) // Array((index1.html,ip1), (index2.html,ip2), (index3.html,ip3))
    val pages = sc.parallelize(List(("index1.html", "Home"), ("index2.html", "Home"), ("index3.html", "Office"))) //Array((index1.html,Home), (index2.html,Home), (index3.html,Office))

    println("default partition: " + visits.getNumPartitions)
    // join 对应的tuple的Key相同进行关联， join把相同key的value组成一个tuple
    // Array((index2.html,(ip2,Home)), (index3.html,(ip3,Office)), (index1.html,(ip1,Home)))
    // 可以指定partition
    visits.join(pages, 4).foreach(visitsPage => {
      println(visitsPage._1 + "\t" + visitsPage._2._1 + "\t" + visitsPage._2._2);
    })
    /*
    输出结果:
    index2.html	ip2	Home
    index3.html	ip3	Office
    index1.html	ip1	Home
     */

    //Array((index2.html,(CompactBuffer(ip2),CompactBuffer(Home))), (index3.html,(CompactBuffer(ip3),CompactBuffer(Office))), (index1.html,(CompactBuffer(ip1),CompactBuffer(Home))))
    visits.cogroup(pages)

    sc.stop()
  }
}
