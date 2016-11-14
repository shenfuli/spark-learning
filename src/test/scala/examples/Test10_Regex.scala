package examples

/**
  * Scala支持通过Regex类的scala.util.matching封装正则表达式
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test10_Regex {

  def main(args: Array[String]) {
    //更换匹配的文本，可以使用replaceFirstIn()以取代第一个匹配项或replaceAllIn()
    val pattern = "(S|s)cala".r
    val str = "Scala is scalable and cool"
    println(pattern replaceFirstIn(str, "Java"))
  }
}

