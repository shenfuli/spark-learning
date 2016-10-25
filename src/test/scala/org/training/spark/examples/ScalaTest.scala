package org.training.spark.examples

/**
  * Created by fuli.shen on 2016/10/25.
  */
object ScalaTest {

  def square(x:Int) = {
    x * x
  }

  def main(args: Array[String]) {

    // 定义可变变量
    var x = 7
    println(x)
    //定义函数
    println(square(x))

    //定义Array数组
    var arr = Array("HelloWorld","China")
    println(arr.apply(0) + "->" + arr.size)
    //定义List以及list操作
    var list = List(1,2,3)
    list.foreach(l=>print(l))
    println()
    list.map(line=>line * 2).foreach(line=>print(line))
    println()
    val sum = list.reduce((num1,num2)=> num1 + num2)
    println("sum:" + sum)


  }
}
