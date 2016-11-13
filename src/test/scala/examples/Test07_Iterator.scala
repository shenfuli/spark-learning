package examples

/**
  * 迭代器不是集合，而是一种由一个访问的集合之一的元素。在一个迭代的两种基本操作：next和hasNext。
  * 调用 it.next()将返回迭代器的下一个元素，推进迭代器的状态。可以找出是否有更多的元素使用迭代器的it.hasNext方法返回
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_Iterator {

  def main(args: Array[String]) {

    val iter: Iterator[String] = Iterator("a", "number", "of", "words")
    
    while (iter.hasNext) {
      println(iter.next())
    }
  }
}

