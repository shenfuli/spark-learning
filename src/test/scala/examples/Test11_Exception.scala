package examples

/**
  * Scala的异常的工作像许多其他语言，如Java异常。而不是正常方式返回的值，方法可以通过抛出一个异常终止。然而，Scala实际上并没有检查异常。
  * 当要处理异常，那么可使用try{...}catch{...} 块，就像在Java中除了catch块采用匹配识别和处理异常。
  * Created by fuli.shen on 2016/11/13.
  */
object Test11_Exception {

  def main(args: Array[String]) {

    try {
      val z = 1 / 0
      println("z:" + z)
    } catch {
      case ex: Exception => {
        println("java.lang.ArithmeticException: / by zero")
      }
    } finally {
      println(" finally")
    }


  }
}

