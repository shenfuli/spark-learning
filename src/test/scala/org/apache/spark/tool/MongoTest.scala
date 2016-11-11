package org.apache.spark.tool

import java.io.FileInputStream

import com.mongodb.MongoClient
import com.mongodb.gridfs.GridFSDBFile
import org.apache.tool.MongodbGridFS

/**
  * Created by kakaci on 2016/11/10.
  */
case class Person2(name: String, age: Int)

object MongoTest {

  def main(args: Array[String]) {

    val key = "person"
    val mongoClient = new MongoClient("10.1.1.8", 27017)
    val mongodbGridFS: MongodbGridFS = new MongodbGridFS("gridfs", mongoClient)

    val filePath = "src/main/resources/person.txt"
    val fileName = "person.txt"
    // 写gridf
    //把文件保存到gridfs中，并以文件的md5值为id
    mongodbGridFS.save(new FileInputStream(filePath), key, fileName)

    //读gridfs
    val gridFSDBFile: GridFSDBFile = mongodbGridFS.getById(key)

    gridFSDBFile.writeTo("person.tmp")

  }
}
