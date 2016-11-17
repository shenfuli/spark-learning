package org.training.spark.utils

import org.apache.spark.sql.SQLContext

/**
  * Spark 读取MySQL数据
  * 1： 支持表查询
  * 2： 支持SQL语句查询
  *
  * 操作数据源：sparktraining\data\mysql\category_info.sql
  * Created by fuli.shen on 2016/11/8.
  */
class SparkJdbcReadMySQL(url: String, dbtable: String, user: String, password: String) {


  /**
    * 建立ID和Name的对应关系
    *
    * @param sqlContext
    * @param categoryId
    * @param categoryName
    * @return
    */
  def getCategoryFromMySQL(sqlContext: SQLContext, categoryId: String, categoryName: String): Map[Int, String] = {
    val jdbcDF = sqlContext.read.format("jdbc").options(Map(
      "url" -> url,
      "driver" -> "com.mysql.jdbc.Driver",
      "dbtable" -> dbtable,
      "user" -> user,
      "password" -> password)).load()

    var categoryMap: Map[Int, String] = Map()
    jdbcDF.collect().map(row => {
      val k: Int = row.getAs(categoryId)
      val v: String = row.getAs[String](categoryName)
      categoryMap += (k -> v)
    })
    categoryMap
  }

  /**
    * 获取指定列的数据
    *
    * @param sqlContext
    * @param column
    * @return
    */
  def getCategoryFromMySQL(sqlContext: SQLContext, column: String): Map[Int, Int] = {
    val jdbcDF = sqlContext.read.format("jdbc").options(Map(
      "url" -> url,
      "driver" -> "com.mysql.jdbc.Driver",
      "dbtable" -> dbtable,
      "user" -> user,
      "password" -> password)).load()

    var categoryMap: Map[Int, Int] = Map()
    jdbcDF.collect().map(row => {
      val k: Int = row.getAs(column)
      categoryMap += (k -> k)
    })
    categoryMap
  }

  /**
    *
    * @param sqlContext
    * @return
    */
  def getCategoryFromMySQL(sqlContext: SQLContext): Map[Int, Int] = {
    val jdbcDF = sqlContext.read.format("jdbc").options(Map(
      "url" -> url,
      "driver" -> "com.mysql.jdbc.Driver",
      "dbtable" -> dbtable,
      "user" -> user,
      "password" -> password)).load()

    var categoryMap: Map[Int, Int] = Map()
    jdbcDF.collect().map(row => {
      val k: Int = row.getAs("categoryId")
      var v: Int = 0
      if (row.getAs("pid").isInstanceOf[Long]) {
        v = row.getAs[Long]("pid").toInt
      } else {
        v = row.getAs("pid")
      }
      categoryMap += (k -> v)
    })
    categoryMap
  }
}