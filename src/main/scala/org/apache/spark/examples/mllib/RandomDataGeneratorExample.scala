package org.apache.spark.examples.mllib

import org.apache.spark.mllib.random.RandomRDDs
import org.apache.spark.{SparkConf, SparkContext}

object RandomDataGeneratorExample {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RandomDataGeneratorExample").setMaster("local")
    val sc = new SparkContext(conf)
    // Generate a random double RDD that contains 1 million i.i.d. values drawn from the
    // standard normal distribution `N(0, 1)`, evenly distributed in 10 partitions.
    val u = RandomRDDs.normalRDD(sc, 1000000L, 10)
    // Apply a transform to get a random double RDD following `N(1, 4)`.
    val v = u.map(x => 1.0 + 2.0 * x)
    sc.stop()
  }
}