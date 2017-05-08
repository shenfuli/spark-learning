package examples

import scala.collection.mutable.ArrayBuffer

/**
  * Scala 中可变的数组
  *
  * http://www.artima.com/pins1ed/collections.html
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_ArrayBuffer {

  def main(args: Array[String]) {

    //To use an ArrayBuffer, you must first import it from the mutable collections package:
    val intArrayBuffer = new ArrayBuffer[Int]()
    intArrayBuffer+=10
    intArrayBuffer+=20
    println("1: intArrayBuffer:" + intArrayBuffer)
    intArrayBuffer.map(x=>println(x))


    val wordsMap = Map("A"->1,"B"->2,"C"->10)

    val mapArrayBuffer = new ArrayBuffer[String]()
    wordsMap.foreach(x=>{
      val word = x._1
      val count = x._2
     val fea = word+":" +count
      mapArrayBuffer+=fea
    })
    println(mapArrayBuffer)//ArrayBuffer(A:1, B:2, C:10)
  }
}
