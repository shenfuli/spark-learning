package org.apache.spark.tool

import org.training.spark.utils.{RedisTool, SerializeTool}


/**
  * Created by fuli.shen on 2016/11/10.
  */
case class Person(name: String, age: Int)

object ToolTest {

  def main(args: Array[String]) {

    val person = new Person("zhangsan", 30)

    val serializeTool = new SerializeTool
    //对象序列化
    val serializePerson: Array[Byte] = serializeTool.serialize(person)
    println("serializePerson:" + serializePerson) //serialize:[B@4cf777e8

    //对象反序列化
    val unserializePerson: AnyRef = serializeTool.unserialize(serializePerson)
    println("unserializePerson:" + unserializePerson) //unserialize:Person(zhangsan,30)

    //序列化的对象存储redis
    val redisTool = new RedisTool("10.1.1.122", 6385, "9icaishi")
    val key = "rank1category:nbayes"
    redisTool.write(key.getBytes(), serializePerson)
    //序列化的对象读取redis
    val personReader: Array[Byte] = redisTool.read(key.getBytes())
    val unserializePerson2: AnyRef = serializeTool.unserialize(personReader)
    if (unserializePerson2.isInstanceOf[Person]) {
      val person1: Person = unserializePerson2.asInstanceOf[Person]
      println(person1.name + "->" + person1.age) //zhangsan->30
    }
  }
}
