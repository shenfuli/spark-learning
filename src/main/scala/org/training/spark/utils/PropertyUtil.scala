package org.training.spark.utils

import org.apache.spark.SparkContext

import scala.io.Source

/**
  * 读取配置文件工具类
  * Created by fuli.shen on 2016/11/14.
  */
object PropertyUtil {

  case class CategoryLabel(rank1Label: Int, rank2Label: Int)

  case class RankCategory(categoryId: Int, categoryName: String)


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


  /**
    * 建立类别和Label的映射关系
    *
    * @param file
    * @return
    */
  def getCategoryLabel(file: String): Map[Int, CategoryLabel] = Source.fromFile(file).getLines().map(line => {
    val splits = line.split("\\|")
    (splits(0).trim.toInt, CategoryLabel(splits(1).trim.toInt, splits(2).trim.toInt))
  }).toMap


  /**
    * 建立Label1 与以及类别的关系
    *
    * @param file
    * @return
    */
  def getRank1CategoryFromHDFS(file: String, sc: SparkContext): scala.collection.Map[Int, RankCategory] = {
    val categoryLabelMap = sc.textFile(file).filter(line => 1 == line.split("\\|")(4).trim.toInt).map(line => {
      val splits = line.split("\\|")
      (splits(1).trim.toInt, RankCategory(splits(0).trim.toInt, splits(3)))
    }).collectAsMap()
    categoryLabelMap
  }

  /**
    * 建立Label2 与以及类别的关系
    *
    * @param file
    * @return
    */
  def getRank2CategoryFromHDFS(file: String, sc: SparkContext): scala.collection.Map[Int, RankCategory] = {
    val categoryLabelMap = sc.textFile(file).filter(line => 2 == line.split("\\|")(4).trim.toInt).map(line => {
      val splits = line.split("\\|")
      (splits(2).trim.toInt, RankCategory(splits(0).trim.toInt, splits(3)))
    }).collectAsMap()
    categoryLabelMap
  }

  /**
    * 建立类别和Label的映射关系
    *
    * @param file
    * @return
    */
  def getCategoryLabelFromHDFS(file: String, sc: SparkContext): scala.collection.Map[Int, CategoryLabel] = {
    val categoryLabelMap = sc.textFile(file).map(line => {
      val splits = line.split("\\|")
      (splits(0).trim.toInt, CategoryLabel(splits(1).trim.toInt, splits(2).trim.toInt))
    }).collectAsMap()
    categoryLabelMap
  }

  /**
    * 建立特定一级类别和Label的映射关系
    *
    * @param file
    * @return
    */
  def getSpecialRank1Category(file: String, sc: SparkContext): scala.collection.Map[Int, Int] = {
    val categoryLabelMap = sc.textFile(file).filter(line => {
      val splits = line.split("\\|")
      val rank1_label = splits(1).trim.toInt
      var result = false
      if (-1 != rank1_label) {
        result = true
      }
      result
    }).map(line => {
      val splits = line.split("\\|")
      (splits(0).trim.toInt, 1)
    }).collectAsMap()
    categoryLabelMap
  }


  /**
    * 建立特定二级类别和Label的映射关系
    *
    * @param file
    * @return <categoryId,1>
    */
  def getSpecialRank2Category(file: String, sc: SparkContext): scala.collection.Map[Int, Int] = {
    val categoryLabelMap = sc.textFile(file).filter(line => {
      val splits = line.split("\\|")
      val rank2_label = splits(2).trim.toInt
      var result = false
      if (-1 != rank2_label) {
        result = true
      }
      result
    }).map(line => {
      val splits = line.split("\\|")
      (splits(0).trim.toInt, 1)
    }).collectAsMap()
    categoryLabelMap
  }

}
