package mllib

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}
import org.apache.spark.sql.SparkSession

/**
  * We provide column summary statistics for RDD[Vector] through the function colStats available in Statistics.
  * colStats() returns an instance of MultivariateStatisticalSummary, which contains the column-wise max, min, mean, variance,
  * and number of nonzeros, as well as the total count.
  *
  * Created by fuli.shen on 2017/9/10.
  */
object SummaryStatisticsTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local").appName("SummaryStatisticsTest").getOrCreate()
    val sc = spark.sparkContext
    val observations = sc.parallelize(
      Seq(
        Vectors.dense(1.0, 10.0, 100.0),
        Vectors.dense(2.0, 20.0, 200.0),
        Vectors.dense(3.0, 30.0, 300.0)
      )
    )
    // Compute column summary statistics.
    val summary: MultivariateStatisticalSummary = Statistics.colStats(observations)
    // a dense vector containing the mean value for each column
    println(summary.mean) // [2.0,20.0,200.0]
    // column-wise variance
    println(summary.variance) // [1.0,100.0,10000.0]
    // number of nonzeros in each column
    println(summary.numNonzeros) // [3.0,3.0,3.0]
    println(summary.min)//[1.0,10.0,100.0]
    println(summary.max)//[3.0,30.0,300.0]
  }
}
