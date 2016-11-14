package examples

/**
  * 模式匹配是Scala中第二个最广泛使用的功能，经过函数值和闭包。Scala中大力支持模式匹配处理消息。
  * 模式匹配包括替代的序列，每个开始使用关键字case。每个备选中包括模式和一个或多个表达式，如果模式匹配将被计算。一个箭头符号=>分开的表达模式。
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test09_Match {

  def main(args: Array[String]) {

    println(matchProvice("100"))//Beijing
    println(matchProvice("102"))//TianJing
    println(matchProvice("103"))//not match data
  }

  def matchProvice(code: String) = code match {
    case "100" => "Beijing"
    case "102" => "TianJing"
    case _ => "not match data"
  }
}

