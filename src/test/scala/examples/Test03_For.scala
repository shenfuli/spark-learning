package examples

/**
  * for循环是一个循环控制结构，可以有效地编写需要执行的特定次数的循环。Scalar的循环说明如下的各种形式：
  * for循环使用范围
  * Scala中for循环最简单的语法是：
  * for( var x <- Range ){
  * statement(s);
  * }
  * 在这里，范围可能是一个数字范围，并且表示为i到j或有时像i到j左箭头< - 操作者被称为生成器，这样命名是因为它是从一个范围产生单个数值。
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test03_For {

  def main(args: Array[String]) {

    var num = 0;
    println("-----------------------Range for-----------------------------")
    // for loop execution with a range
    for (num <- 1 to 4) {
      println("Value of num: " + num);
    }
  }
}
