package examples

/**
  * Scala的元组结合件多个固定数量在一起，使它们可以被传来传去作为一个整体。
  * 不像一个数组或列表，元组可以容纳不同类型的对象，但它们也是不可改变的。这里是一个元组持有整数，字符串和Console
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_Tuple {

  def main(args: Array[String]) {

    val t = (1, "hello", Console)
    println(t._1 + "\t" + t._2 + "\t" + t._3) //1	hello	scala.Console$@4b4523f8
    //遍历元组-使用Tuple.productIterator()方法来遍历一个元组的所有元素
    t.productIterator.foreach(i => println(i))

    //交换元素-可以使用Tuple.swap方法来交换一个Tuple2的元素
    val t2 = ("Hello", "World")
    println(t2.swap._1 + "\t" + t2.swap._2) //World	Hello
  }
}

