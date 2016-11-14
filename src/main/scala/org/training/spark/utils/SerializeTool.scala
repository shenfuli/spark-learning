package org.training.spark.utils

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

/**
  * 实现对象序列和反序列化
  */
class SerializeTool {
  def serialize(obj: AnyRef): Array[Byte] = {
    var oos: ObjectOutputStream = null
    var baos: ByteArrayOutputStream = null
    try {
      baos = new ByteArrayOutputStream
      oos = new ObjectOutputStream(baos)
      oos.writeObject(obj)
      val bytes: Array[Byte] = baos.toByteArray
      return bytes
    }
    catch {
      case e: Exception => {
        return null
      }
    }finally {
      if(null!=oos){
        oos.close()
      }
    }
  }

  def unserialize(bytes: Array[Byte]): AnyRef = {
    var bais: ByteArrayInputStream = null
    var ois:ObjectInputStream = null
    try {
      bais = new ByteArrayInputStream(bytes)
      ois = new ObjectInputStream(bais)
      return ois.readObject
    }
    catch {
      case e: Exception => {
        return null
      }
    }finally {
      if(null!=ois){
        ois.close()
      }
    }
  }
}
