package examples

/**
  * while 循环语句多次执行，只要给定的条件为真执行目标语句
  * 语法:
  * Scala while循环的语法是：
  * while(condition){
  * statement(s);
  * }
  * 在这里，声明可以是单个语句或语句块。所述条件可以是任何表达式，真值是任何非零值。当条件为true，则循环迭代。
  * 当条件为false，则程序控制进到紧接在循环之后的行
  *
  * while循环的关键点是循环可能不会永远运行。当条件测试结果为false，循环体将跳过while循环后的第一个语句执行
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test03_While {

  def main(args: Array[String]) {

    // Local variable declaration:
    var num = 10;
    // while loop execution
    while (num < 20) {
      println("Value of a: " + num);
      num = num + 1;
    }
    /*
    Value of a: 10
    Value of a: 11
    Value of a: 12
    Value of a: 13
    Value of a: 14
    Value of a: 15
    Value of a: 16
    Value of a: 17
    Value of a: 18
    Value of a: 19
     */
  }
}
