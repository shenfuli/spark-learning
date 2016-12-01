package examples

/**
  * scala输出有效位数
  * Created by fuli.shen on 2016/12/1.
  */
object Test {
  def main(args: Array[String]) {
    val pi = scala.math.Pi
    println(pi)//3.141592653589793
    println(f"$pi%1.5f")//3.14159


    val x = 0.8778756035217268
    println(f"$x%1.2f")//0.88

    val x2 = 0.7255979314802844
    println(f"$x2%1.2f")//0.73

    val x3 =  0.7027757685352623
    println(f"$x3%1.2f")//0.70

  }
}
