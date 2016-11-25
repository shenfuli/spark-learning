package examples

/**
  * Scala 中可变的数组
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_ArrayBuffer {

  def main(args: Array[String]) {

    val vectorMeta = Map("双排" -> 1, "通道" -> 2, "卡车" -> 3)

    val words = "[双排:3, 通道:4, 政府:2, 高速:2,福田:1,卡车:4]"

    val wordsList = words.replace("[", "").replace("]", "").split(",")
    var features = ""
    wordsList.filter(words => !"".equals(words) && words.split(":").length == 2).map(words => {
      val wordCount = words.split(":")
      if (wordCount.length == 2) {
        val word = wordCount(0).trim
        val value = vectorMeta.getOrElse(word, 0)
        val count = wordCount(1).trim.toInt
        if (0 != value) {
          features += value + ":" + count + " "
        }
      }
    })
    println(features.trim)//1:3 2:4 3:4
  }
}
