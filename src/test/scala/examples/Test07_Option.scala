package examples

/**
  * Scala的Option[T]是容器对于给定的类型的零个或一个元件。Option[T]可以是一些[T]或None对象，它代表一个缺失值。
  * 例如，Scala映射get方法产生，如果给定的键没有在映射定义的一些(值)，如果对应于给定键的值已经找到，或None。
  * 选项Option类型常用于Scala程序，可以比较这对null值Java可用这表明没有任何值。
  * 例如，java.util.HashMap中的get方法将返回存储在HashMap的值，或null，如果找到没有任何值
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_Option {

  def main(args: Array[String]) {
    val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")

    println("capitals.get('France') : " + capitals.get("France")) //capitals.get('France') : Some(Paris)
    println("capitals.get('Japan') : " + capitals.get("India")) //capitals.get('Japan') : None

    println("show(capitals.get('France')) : " + show(capitals.get("France"))) //Paris
    println("show(capitals.get('Japan')) : " + show(capitals.get("India"))) //?

    //使用getOrElse()方法-使用getOrElse()来访问值或使用默认值
    println("capitals.getOrElse('India', 'No Find') : " + capitals.getOrElse("India", "No Find")) //show(capitals.get('India')) : No Find
  }

  /**
    * 显示Option类型的数值
    *
    * @param x
    * @return
    */
  def show(x: Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }
}

