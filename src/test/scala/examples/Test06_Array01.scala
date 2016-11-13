package examples

/**
  * Scala中提供了一种数据结构-数组，其中存储相同类型的元素的固定大小的连续集合。
  * 数组用于存储数据的集合，但它往往是更加有用认为数组作为相同类型的变量的集合
  *
  * 当要处理数组元素，我们经常使用循环，因为所有的数组中的元素具有相同的类型，并且数组的大小是已知的
  * Created by fuli.shen on 2016/11/13.
  */
object Test06_Array01 {

  def main(args: Array[String]) {

    //方式1:定义数组
    val array = new Array[String](3)
    array(0) = "Hello1"
    array(1) = "hello2"
    array(2) = "hello3"
    println(array.toSeq)
    //方式2:定义数组还有另一种方式
    val array2 = Array("Hello1", "hello2", "hello3")
    println(array2.toSeq)
    //处理数组
    println("--------------处理数组1-------------------------")
    for (x <- array2) {
      println(x)
    }
    println("--------------处理数组2-------------------------")
    for (i <- 0 to (array2.size - 1)) {
      println(array2(i))
    }
  }
}
