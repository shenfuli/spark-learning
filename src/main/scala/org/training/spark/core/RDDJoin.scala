package org.training.spark.core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * RDD 合并
  *
  * http://www.cnblogs.com/MOBIN/p/5384543.html#9
  * Created by fuli.shen on 2017/5/8.
  */
object RDDJoin {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("spark-RDDJoin").setMaster("local")
    val sc = new SparkContext(conf)
    val data1 = List(("u1", 10000), ("u1", 20000),("u1", 10000),("u3",1000000))// ("u1", 10000)   u1 表示用户ID，20000 表示用户u1访问新闻类别30000
    val data2 = List(("u1", 50000), ("u2", 30000))
    val rdd1 = sc.parallelize(data1)
    val rdd2 = sc.parallelize(data2)

    //join(otherDataSet,numPartitions):对每个Key下的元素进行笛卡尔积，numPartitions设置分区数，提高作业并行度
    println("Join(otherDataSet,numPartitions):对每个Key下的元素进行笛卡尔积，numPartitions设置分区数，提高作业并行度")
    val joinRDD = rdd1.join(rdd2)
    joinRDD.foreach(println(_))
    /*
    (u1,(10000,50000))
    (u1,(20000,50000))
    (u1,(10000,50000))
     */

    println("LeftOutJoin(otherDataSet，numPartitions):左外连接，包含左RDD的所有数据，如果右边没有与之匹配的用None表示,numPartitions设置分区数，提高作业并行度")
    val leftOutJoinRDD = rdd1.leftOuterJoin(rdd2)
    leftOutJoinRDD.foreach(println(_))
    /*
        (u3,(1000000,None))
        (u1,(10000,Some(50000)))
        (u1,(20000,Some(50000)))
        (u1,(10000,Some(50000)))
       结论： 所有rdd1 的数据全部展示
     */


    println("RightOutJoin(otherDataSet, numPartitions):右外连接，包含右RDD的所有数据，如果左边没有与之匹配的用None表示,numPartitions设置分区数，提高作业并行度")
    val RightOutJoin = rdd1.rightOuterJoin(rdd2)
    RightOutJoin.foreach(println(_))
    /*
      (u2,(None,30000))
      (u1,(Some(10000),50000))
      (u1,(Some(20000),50000))
      (u1,(Some(10000),50000))
        结论： 所有rdd2 的数据全部展示
     */
    sc.stop()
  }
}

