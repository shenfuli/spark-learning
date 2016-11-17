package org.training.spark.utils

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * <Function> 读取HDFS上的SQL语句，然后导入MySQL </Function>
  *
  * 文件格式： 目前该程序支持insert/delete/drop 等语句，例如:
  * insert into t1(name,age) values('fuli.shen',30);
  * insert into t1(name,age) values('fuli.shen2',40);
  * Author: Created by fuli.shen on 2016/5/30.
  */
object SparkJdbcWriteMySQL {

  var inputPath = ""
  var ip = ""
  var dataBaseName = ""
  var userName = ""
  var password = ""

  def main(args: Array[String]) {
    if (args.length != 5) {
      println("Usage:<inputPath><dataBaseName><userName><password>")
      sys.exit(1)
    }
    inputPath = args(0)
    ip = args(1)
    dataBaseName = args(2)
    userName = args(3)
    password = args(4)
    //初始化Spark环境
    val conf: SparkConf = new SparkConf()
      .setAppName("RDDtoMySQL")
    val sc: SparkContext = new SparkContext(conf)

    //读取HDFS转为RDD
    val textFile: RDD[String] = sc.textFile(inputPath)
    //RDD导入Mysql,使用foreachPartition减少MySQL创建连接数
    textFile.foreachPartition(importMySQL)
    sc.stop()
  }

  /**
    * 批量插入Mysql，提升插入MySQL的效率
    *
    * @param iterator
    */
  def importMySQL(iterator: Iterator[String]): Unit = {

    //初始化变量
    var conn: Connection = null
    var ps: PreparedStatement = null
    try {
      val url = "jdbc:mysql://" + ip + "/" + dataBaseName + ""
      conn = DriverManager.getConnection(url, userName, password);
      val s1 = System.currentTimeMillis();
      iterator.foreach(sql => {
        ps = conn.prepareStatement(sql)
        ps.executeUpdate()
      })
      val s2 = System.currentTimeMillis();
      println("update data  consume time:" + (s2 - s1) + "ms")
    } catch {
      case e: Exception => println("RDDtoMySQL.importMySQL  MySQL Exception ")
    } finally {
      if (null != ps) {
        ps.close()
      }
      if (null != conn) {
        conn.close()
      }
    }
  }
}