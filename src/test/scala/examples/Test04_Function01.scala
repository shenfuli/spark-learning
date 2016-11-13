package examples

/**
  * 函数是一组一起执行任务的语句。可以将代码放到独立的功能。如何划分你的代码不同功能？在逻辑上，通常是让每个函数执行特定的任务。
  *
  * Scala有函数和方法，我们术语说的方法和函数互换用微小的差别。Scala方法是其中有一个名字，签名，任选一些注释，有的字节码，
  * 其中如在Scala中函数是可被分配给一个变量的完整对象类的一部分。换句话说，函数，其被定义为某些对象的一个成员，被称为方法。
  *
  * 函数定义可以出现在在源文件的任何地方，Scala允许嵌套函数的定义，那就是其他函数定义的内部函数定义。需要注意的最重要的一点是，
  * Scala的函数名称可以类似+, ++, ~, &,-, -- , , /, : 等字符。
  *
  * 函数声明：
  * Scala函数声明有如下形式：
  * def functionName ([list of parameters]) : [return type]
  * 如果保留关闭等号和方法体的方法则为隐式声明，抽象的封闭类型是抽象本身。
  *
  * 函数定义：
  * Scala函数定义有如下形式：
  * def functionName ([list of parameters]) : [return type] = {
  * function body
  * return [expr]
  * }
  *
  * 在这里，返回类型可以是任何有效的scala数据类型，参数列表将是用逗号和参数，返回值类型列表分离变量是可选的。非常类似于Java，
  * 一个返回语句可以在函数表达式可用情况下返回一个值
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test04_Function01 {


  def main(args: Array[String]) {

    println("x1 + x2 = " + add(3, 4)) //x1 + x2 = 7
    show("China") //Hello,China
    show() //Hello,World
  }

  /**
    * 默认参数为World，调用函数不传递参数默认该数值
    *
    * @param name
    */
  def show(name: String = "World"): Unit = {

    println("Hello," + name)
  }

  /**
    * 两个数相加 ,最后一行为返回数值，可以省略return 语句
    *
    * @param x1
    * @param x2
    * @return
    */
  def add(x1: Int, x2: Int): Int = {
    x1 + x2
  }
}
