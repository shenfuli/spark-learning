package examples

import scala.util.control.Breaks

/**
  * 打断嵌套循环,使用嵌套循环打破现有循环。所以，如果使用break嵌套循环
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test02_Break02 {

  def main(args: Array[String]) {

    val numList1 = List(1, 2, 3, 4, 5);
    val numList2 = List(11, 12, 13);

    val outer = new Breaks;
    val inner = new Breaks;

    outer.breakable {
      for (a <- numList1) {
        inner.breakable {
          for (b <- numList2) {
            println(a + "->" + b);
            if (b == 12) {
              inner.break;
            }
          } // inner breakable
        } // outer breakable.
      }
    }
  }
}
