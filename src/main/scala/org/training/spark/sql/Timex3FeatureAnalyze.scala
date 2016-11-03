package org.training.spark.sql

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 新闻Timex3特征统计
  *
  * 1 新闻Timex3特征统计-DATE类型
  * 2 新闻Timex3特征统计-TIME类型
  * 3 新闻Timex3特征统计-DURATION类型
  * 4 新闻Timex3特征统计-SET类型
  *
  * Created by fuli.shen on 2016/11/3.
  */
object Timex3FeatureAnalyze {


  def main(args: Array[String]) {

    var masterUrl = "local[1]"
    var dataPath = "/user/fuli.shen/data/timex3-5m/"
    if (args.length != 0) {
      masterUrl = args(0)
      dataPath = args(1)
    }
    val conf = new SparkConf().setAppName(Timex3FeatureAnalyze.getClass.getSimpleName).setMaster(masterUrl)
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc)

    /**
      * Create RDDs
      */
    val timex3DF = sqlContext.read.format("json").load(dataPath + "timex3-5m.json")
    timex3DF.registerTempTable("timex3feature")

    /**
      * 1:all timex3 type
      */
    val timex3type = sqlContext.sql("select distinct type from timex3feature group by type ")
    timex3type.coalesce(1).write.mode(SaveMode.Overwrite).format("com.databricks.spark.csv").option("header", "true").save(dataPath + "timex3type.csv")
    val timex3typeDF = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load(dataPath + "timex3type.csv")
    timex3typeDF.show()


    /**
      * 2:all timex3 type=DATE values
      */
    val timex3Date = sqlContext.sql("select distinct value,name from timex3feature where type='DATE' order by value")
    timex3Date.coalesce(1).write.mode(SaveMode.Overwrite).format("com.databricks.spark.csv").option("header", "true").save(dataPath + "timex3date.csv")
    val timex3dateDF = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load(dataPath + "timex3date.csv")
    timex3dateDF.show()


    /**
      * 3:all timex3 type=TIME values
      */
    val timex3Time = sqlContext.sql("select distinct value,name from timex3feature where type='TIME' order by value")
    timex3Time.coalesce(1).write.mode(SaveMode.Overwrite).format("com.databricks.spark.csv").option("header", "true").save(dataPath + "timex3time.csv")
    val timex3timeDF = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load(dataPath + "timex3time.csv")
    timex3timeDF.show()
    /**
      * 4:all timex3 type=SET values
      */
    val timex3Set = sqlContext.sql("select distinct value,name from timex3feature where type='SET' order by value")
    timex3Set.coalesce(1).write.mode(SaveMode.Overwrite).format("com.databricks.spark.csv").option("header", "true").save(dataPath + "timex3set.csv")
    val timex3SetDF = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load(dataPath + "timex3set.csv")
    timex3SetDF.show()

    /**
      * 5:all timex3 type=DURATION values
      */
    val timex3Duration = sqlContext.sql("select distinct value,name from timex3feature where type='DURATION' order by value")
    timex3Duration.coalesce(1).write.mode(SaveMode.Overwrite).format("com.databricks.spark.csv").option("header", "true").save(dataPath + "timex3duration.csv")
    val timex3DurationDF = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load(dataPath + "timex3duration.csv")
    timex3DurationDF.show()

    sc.stop()
  }
}
