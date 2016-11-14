package examples

import examples.model.Student

/**
  * 类是一个对象的蓝图。一旦定义一个类可以创建从类蓝图使用关键字new创建对象
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test08_ClassOrObject {

  def main(args: Array[String]) {
    val person = new Student("fuli.shen", 20, "Bigdata")
    person.print
  }
}

