package examples

import scala.Array._

/**
  * 多维数组 需要定义和使用多维数组（即数组的元素数组）。例如，矩阵和表格结构的实例可以实现为二维数组
  * Scala不直接支持多维数组，并提供各种方法来处理任何尺寸数组。以下是定义的二维数组的实例
  *
  * 用任何提及的方法之前，要导入Array._包。创建了一个2维数组。格式 ：
  * def ofDim[T]( n1: Int, n2: Int ): Array[Array[T]]
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test06_Array02 {

  def main(args: Array[String]) {

    //这是一个具有每个都是整数，它有三个元素数组3元素的数组。下面的代码展示了如何处理多维数组
    var myMatrix = ofDim[Int](3, 3)

    // build a matrix
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        myMatrix(i)(j) = j
      }
    }
    // Print two dimensional array
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        print(" " + myMatrix(i)(j));
      }
      println();
    }
  }
}
