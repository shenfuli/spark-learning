package examples

import scala.util.control.Breaks

/**
  * 循环控制语句改变其正常的顺序执行。当执行离开一个范围，在该范围内创建的所有对象自动被销毁。
  * 但是Scala不支持break或continue语句。
  *
  * 在Scala中可以没有内置break语句，但如果正在运行的Scala2.8，那么还有一个办法使用break语句。
  * 当break语句在循环中遇到循环立即终止，程序控制继续下一个循环语句后面的
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test02_Break01 {

  def main(args: Array[String]) {

    val numList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    val loop = new Breaks
    loop.breakable {
      numList.foreach(num => {
        println("Value of num: " + num);
        if (num == 4) {
          loop.break
        }
      })
      println("After the loop");
    }
  }
  /**
    * Value of num: 1
    * Value of num: 2
    * Value of num: 3
    * Value of num: 4
    */
}
