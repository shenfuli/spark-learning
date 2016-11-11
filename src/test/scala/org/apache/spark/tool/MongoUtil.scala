package com.caishi.util

import java.util

import com.mongodb.{MongoCredential, ServerAddress, MongoClient}
import com.mongodb.client.model.{UpdateOptions, Filters}
import com.mongodb.client.result.UpdateResult
import org.bson.Document

/**
  * mongodb 辅助类
  * Created by root on 15-10-17.
  */
object MongoUtil {
  @transient private var instance : MongoClient = _

   /**
    * 返回MongoDB实例
    *
    * @param address  MongoDB 实例的地址列表，
    * @param userName MongoDB 安全认证用户名称
    * @param password MongoDB 安全认证用户密码
    * @return
    */
  def getInstance(address:String, userName:String, password:String):MongoClient = {
    val adds : Array[ServerAddress] = address.split(",").map(rem => new ServerAddress(rem.split(":")(0),rem.split(":")(1).toInt))
    val seeds : util.ArrayList[ServerAddress] = new util.ArrayList[ServerAddress]()
    adds.foreach(s => seeds.add(s))

    if (instance == null) {
      //Creates a Mongo based on a list of replica set members or a list of mongos  and support sharded cluster
      val credentialsList: util.ArrayList[MongoCredential] = new util.ArrayList[MongoCredential]()
      val mongoCredential: MongoCredential = MongoCredential.createCredential(userName, "user_profile", password.toCharArray)
      credentialsList.add(mongoCredential);
      instance = new MongoClient(seeds, credentialsList)
    }
    instance
  }
  /**
    * 更新or添加-支持MongoDB安全认证
    */
  def upsert(address: String, databaseName: String, collection: String, _id: String, doc: Document, userName: String,
             password: String): Unit = {
    val result: UpdateResult = MongoUtil.getInstance(address, userName, password).getDatabase(databaseName).getCollection(collection).updateMany(Filters.eq("_id", _id), new Document("$set", doc), new UpdateOptions().upsert(true))
    result.getModifiedCount
  }
  /**
    * 根据_ID获得一个doc对象-支持MongoDB安全认证
    *
    * @param mongoRemotes
    * @param mongoDb
    * @param collection
    * @param userId
    * @param userName
    * @param password
    * @return
    */
  def getFirstDoc(mongoRemotes: String, mongoDb: String, collection: String, userId: String, userName: String,
                  password: String): Document = {
    var doc: Document = MongoUtil.getInstance(mongoRemotes, userName, password).getDatabase(mongoDb).getCollection(collection).find(Filters.eq("_id", userId)).first()
    if (doc == null) {
      // 如果为空则默认使用default用户的画像作为基础
      doc = MongoUtil.getInstance(mongoRemotes, userName, password).getDatabase(mongoDb).getCollection(collection).find(Filters.eq("_id", "default")).first()
      doc = doc.append("_id", userId)
    }
    doc
  }
}