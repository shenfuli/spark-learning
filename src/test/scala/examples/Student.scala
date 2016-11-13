package examples

/**
  * Created by fuli.shen on 2016/11/13.
  */
class Student(val name: String, val age: Int, val career: String) extends Person(name, age) {

  /**
    * 重写父类的print方法
    */
  override def print(): Unit = {
    println(name + "->" + age + "->" + career)
  }
}
