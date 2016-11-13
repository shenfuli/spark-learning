package examples

/**
  * 不像while循环，测试循环条件在循环顶部，do ... while循环循环在底部检查状态。
  * do... while循环类似于while循环，不同的是do ... while循环是保证至少执行一次。
  *
  * 语法：
  * Scala中do... while循环的语法是：

  * do{
  * statement(s);
  * }while( condition );
  * 注意，条件表达式出现在循环结束，所以在循环语句(多个)执行一次前的状态进行测试。
  * 如果条件为真，控制流跳转回后将循环语句(S)再次执行。重复这个过程，直到给定的条件为假。
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test03_DoWhile {

  def main(args: Array[String]) {

    // Local variable declaration:
    var num = 10;
    // while loop execution
    do {
      println("Value of a: " + num);
      num = num + 1;
    } while (num < 20)
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
