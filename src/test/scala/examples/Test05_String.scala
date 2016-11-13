package examples

/**
  * 在Scala中与在Java中一样，一个字符串是不可变的对象，也就是，这是不能被修改的对象。另一方面，对象是可以修改的，如数组对象，被称为可变对象。
  * 由于字符串是非常有用的对象，在本节的其余部分，我们目前最重要了解类java.lang.String的方法定义。
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test05_String {

  def main(args: Array[String]) {

    //创建字符串
    val greeting = "Hello world!";
    println(greeting)
    //字符串长度-用于获得关于对象的信息的方法是已知的存取方法。可以使用字符串使用一个存取方法是length()方法，它返回包含在字符串对象中的字符数。
    println(greeting.size)
    //连接字符串
    val concat: String = "Hello".concat("world")
    println(concat) //Helloworld
    //创建格式化字符串: printf 和 format
    printf("I am from %s", "China") //I am from China
    println()
    println("I am from %s".format("China")) //I am from China
  }
}
