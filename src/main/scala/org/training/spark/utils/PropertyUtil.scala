package org.training.spark.utils

import scala.io.Source

/**
  * 读取配置文件工具类
  * Created by fuli.shen on 2016/11/14.
  */
object PropertyUtil {
  /**
    * 根据文件路径返回文件关系
    *
    * @param file
    * @return
    */
  def getProperties(file: String): Map[String, String] = Source.fromFile(file).getLines().filter(line => {
    !line.startsWith("#") && line.contains("=")
  }).map(line => {
    val pair = line.split("=")
    (pair(0), pair(1))
  }).toMap
}
