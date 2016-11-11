package org.apache.spark.tool

import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.tool.SerializeTool

/**
  * scala使用hbase新api
  * Created by fuli.shen on 2016/11/10.
  */

case class Student(name: String, age: Int)

object HBaseTest {


  def main(args: Array[String]) {

    val zookeeperQuorum = "10.1.1.120:2181,10.1.1.130:2181,10.1.1.140:2181"
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", zookeeperQuorum)
    val connection = ConnectionFactory.createConnection(hbaseConf)
    val mllibModel: Table = connection.getTable(TableName.valueOf("mllib_model"))

    println("-------------------HBASE操作字符串-----------------------------")
    val put = new Put("rk2".getBytes())
    put.addColumn("info".getBytes(), "data".getBytes(), "USA".getBytes())
    mllibModel.put(put)
    val get = new Get("rk2".getBytes)
    val result: Result = mllibModel.get(get)
    val value: Array[Byte] = result.getValue("info".getBytes(), "data".getBytes())
    println(new String(value))

    println("-------------------HBASE操作序列化对象-----------------------------")
    val student = new Student("lisi", 30)
    val serializeTool = new SerializeTool
    //对象序列化
    val serializeStudent: Array[Byte] = serializeTool.serialize(student)
    println("serializeStudent:" + serializeStudent)
    val studentPut = new Put("rk3".getBytes())
    studentPut.addColumn("info".getBytes(), "data".getBytes(), serializeStudent)
    mllibModel.put(studentPut)

    val studentGet = new Get("rk3".getBytes)
    val studentGetResult: Result = mllibModel.get(studentGet)
    val studentValue: Array[Byte] = studentGetResult.getValue("info".getBytes(), "data".getBytes())
    val unserializeStudent2: AnyRef = serializeTool.unserialize(studentValue)
    if (unserializeStudent2.isInstanceOf[Student]) {
      val student: Student = unserializeStudent2.asInstanceOf[Student]
      println(student.name + "->" + student.age)
    }
    mllibModel.close()
  }

}

