package org.training.spark.sql

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext};

/**
  * spark 对csv 文件的读写操作
  *
  * Created by fuli.shen on 2016/11/3.
  *
  * Reference
  * [1] https://github.com/databricks/spark-csv
  */
object CarsSparkSQLCSV {

  def main(args: Array[String]) {

    var masterUrl = "local[1]"
    var dataPath = "/user/fuli.shen/data/csv/"
    if (args.length != 0) {
      masterUrl = args(0)
      dataPath = args(1)
    }
    var conf = new SparkConf().setAppName("CarsSparkSQLCSV").setMaster(masterUrl)
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    /**
      * 1:(csv read) Automatically infer schema (data types), otherwise everything is assumed string
      *
      * +----+-----+-----+--------------------+-----+
      * |year| make|model|             comment|blank|
      * +----+-----+-----+--------------------+-----+
      * |2012|Tesla|    S|          No comment|     |
      * |1997| Ford| E350|Go get one now th...|     |
      * |2015|Chevy| Volt|                null| null|
      * +----+-----+-----+--------------------+-----+
      */
    //val inferSchemaDF = sqlContext.read.format("com.databricks.spark.csv").option("header", "true") .option("inferSchema", "true") .load(dataPath + "cars.csv")
    val inferSchemaDF = sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true") // Use first line of all files as header
      .option("inferSchema", "true") // Automatically infer data types
      .load(dataPath + "cars.csv")
    inferSchemaDF.show()

    /**
      * 2:(csv read )You can manually specify the schema when reading data:
      */
    //val customSchema = StructType(Array(StructField("year", IntegerType, true),StructField("make", StringType, true),StructField("model", StringType, true),StructField("comment", StringType, true),StructField("blank", StringType, true)))
    val customSchema = StructType(Array(
      StructField("year", IntegerType, true),
      StructField("make", StringType, true),
      StructField("model", StringType, true),
      StructField("comment", StringType, true),
      StructField("blank", StringType, true)))
    //val customSchemaDF = sqlContext.read.format("com.databricks.spark.csv").option("header", "true") .schema(customSchema).load(dataPath + "cars.csv")
    val customSchemaDF = sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .schema(customSchema)
      .load(dataPath + "cars.csv")
    customSchemaDF.show()

    /**
      * 3:csv write by DataFrame
        +----+-----+
        |year|model|
        +----+-----+
        |2012|    S|
        |1997| E350|
        |2015| Volt|
        +----+-----+
      */
    val selectedData = customSchemaDF.select("year", "model")
    //selectedData.coalesce(1).write.mode(SaveMode.Overwrite).format("com.databricks.spark.csv").option("header", "true").save(dataPath + "newcars.csv")
    selectedData.coalesce(1).write
      .mode(SaveMode.Overwrite)
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .save(dataPath + "newcars.csv")
    selectedData.show()

    sc.stop()
  }
}
