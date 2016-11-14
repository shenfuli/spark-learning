package examples

/**
  * 提取器在Scala中是一个对象，有一个叫非应用作为其成员的一种方法。即不应用方法的目的是要匹配的值，并把它拆开。
  * 通常，提取对象还限定了双方法申请构建值，但是这不是必需的
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test12_Apply {

  def main(args: Array[String]) {

    val apply1: String = apply("fuli.shen","gmail.com")
    println(apply1)

    val unapply1: Option[(String, String)] = unapply(apply1)
    println(unapply1.get._1+"->" + unapply1.get._2)
  }

  /**
    * The injection method (optional)
    *
    * @param userName
    * @param domain
    * @return
    */
  def apply(userName: String, domain: String) = {
    userName + "@" + domain
  }

  /**
    * The extraction method (mandatory)
    *
    * @param str
    * @return
    */
  def unapply(str: String): Option[(String, String)] = {
    val parts: Array[String] = str.split("@")
    if (parts.length == 2) {
      Some(parts(0), parts(1))
    } else {
      None
    }
  }
}

