package examples

/**
  * Scala中列表是非常类似于数组，这意味着，一个列表的所有元素都具有相同的类型，但有两个重要的区别。
  * 首先，列表是不可变的，这意味着一个列表的元素可以不被分配来改变。第二，列表表示一个链表，而数组平坦的。
  * 具有T类型的元素的列表的类型被写为List[T]
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_List {

  def main(args: Array[String]) {

    // List of Strings
    val fruit: List[String] = List("apples", "oranges", "pears")

    // List of Integers
    val nums: List[Int] = List(1, 2, 3, 4)

    // Empty List.
    val empty: List[Nothing] = List()

    // Two dimensional list
    val dim: List[List[Int]] = List(
      List(1, 0, 0),
      List(0, 1, 0),
      List(0, 0, 1)
    )
    //列表的基本操作
    println(fruit.head) //此方法返回的列表中的第一个元素
    println(fruit.tail) //此方法返回一个由除了第一个元素外的所有元素的列表
    println(!fruit.isEmpty) //如果列表为空，此方法返回true，否则为false
    println(fruit.reverse) //可以使用List.reverse方法来扭转列表中的所有元素
    println(fruit.dropRight(1)) //返回除了最后的n个的元素
    println(fruit.toString()) //列表以一个数组变换

    dim.foreach(outerList => {
      var count = 0
      outerList.foreach(innerList => {
        print(innerList + " ")
        count = count + 1
        if (count == 3) {
          println
        }
      })
    })

  }
}
