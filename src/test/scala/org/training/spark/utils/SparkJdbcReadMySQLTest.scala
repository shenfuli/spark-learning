package org.training.spark.utils

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fuli.shen on 2016/11/8.
  */
object SparkJdbcReadMySQLTest {

  val url = "jdbc:mysql://10.2.1.1:3306/bigdata"
  val dbtable = "bigdata.category_info"
  val user = "bigdata"
  val password = "bigdata"
  val tableQuery = "(SELECT categoryId FROM bigdata.category_info WHERE categoryLevel=1) tmp"

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SparkJdbcReadMySQLTest")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    /**
      * 1:根据查询条件获取数据
      */
    val tableQuery = "(SELECT categoryId FROM bigdata.category_info WHERE categoryLevel=1) tmp"
    val sparkOperaMySQL1 = new SparkJdbcReadMySQL(url, tableQuery, user, password)

    val categoryIdsMap = sparkOperaMySQL1.getCategoryFromMySQL(sqlContext, "categoryId")
    for ((k, v) <- categoryIdsMap) {
      println(k + "->" + v)
    }

    /**
      * 2:通过表名支持获取数据
      */
    val sparkOperaMySQL2 = new SparkJdbcReadMySQL(url, dbtable, user, password)

    val categoryInfoData = sparkOperaMySQL2.getCategoryFromMySQL(sqlContext, "categoryId", "categoryName");
    val categoryInfoDataBroadcast = sc.broadcast(categoryInfoData)
    val name = categoryInfoDataBroadcast.value.get("10300".toInt)
    println(name.get)
    sc.stop()
  }
}