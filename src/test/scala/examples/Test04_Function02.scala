package examples

;

/**
  * 闭包是函数，它的返回值取决于此函数之外声明一个或多个变量的值。例如:使用匿名函数的代码
  *
  * val function_name = (function parameter list  ) => {.....}
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test04_Function02 {

  def main(args: Array[String]) {

    println(show("China"))
    println(add(4, 5))
  }

  /**
    * 两个数相加
    *
    * @return
    */
  def add = (x1: Int, x2: Int) => {
    val x = x1 + x2
    x
  }

  /**
    * 返回 最后一行
    *
    * @return
    */
  def show = (name: String) => {
    "Hello," + name
  }

}
