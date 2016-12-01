package examples

/**
  * FlatMap 的使用 ： 一转多的概念
  *
  */
object Test07_FlatMap {

  def main(args: Array[String]) {

    val wordCountMapArray = Array(Map("福田" -> 9, "卡车" -> 2, "奖品" -> 10), Map("福田" -> 1, "高速" -> 2))

    wordCountMapArray.flatMap(wordCountMap => {
      wordCountMap
    }).foreach(println)
  }
}

