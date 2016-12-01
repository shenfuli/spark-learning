package examples


/**
  * 按照第一个下标进行排序：如何对Map中的Key或Value进行排序
  *
  * 语法：
  * // sort by key can use sorted
  * m.toList.sorted foreach {
  * case (key, value) =>
  * println(key + " = " + value)
  * }
  * // sort by value
  * m.toList sortBy ( _._2 ) foreach {
  * case (key, value) =>
  * println(key + " = " + value)
  *
  * 参考地址： http://www.tuicool.com/articles/UvqUjmi
  * Created by fuli.shen on 2016/11/24.
  */
object Test07_MapSorted03 {

  def main(args: Array[String]) {

    val features = "5538:4 1417:2 245:4 6127:4 144812:2 3482:2 1436:3 63:2"
    val newsFeatures = sortedFeatures(features)
    println(newsFeatures)
  }

  /**
    * 对字符段按照索引的第一个位置进行排序
    *
    * @param features
    * @return
    */
  def sortedFeatures(features: String): String = {
    val featuresSplits = features.split(" ")
    var sortedFeatures = ""
    featuresSplits.map(feature => {
      val splits = feature.split(":")
      (splits(0).trim.toInt, splits(1).trim.toInt)
    }).toList.sorted foreach {
      case (key, value) => {
        sortedFeatures += key + ":" + value + " "
      }
    }
    sortedFeatures.trim
  }
}

