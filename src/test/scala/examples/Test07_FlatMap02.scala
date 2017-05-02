package examples

/**
  * FlatMap 的使用 ： 一转多的概念
  *
  */
object Test07_FlatMap02 {

  def main(args: Array[String]) {

    val k1 = ("k1",Map("福田" -> 9, "卡车" -> 2, "奖品" -> 10))
    val k2 = ("k2",Map("福田" -> 1, "高速" -> 2))

   val wordCountMapArray = List(k1,k2)
  }
}

