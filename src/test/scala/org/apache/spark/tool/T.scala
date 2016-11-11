package org.apache.spark.tool

import java.io.FileInputStream

import com.mongodb.MongoClient
import com.mongodb.client.model.{Filters, UpdateOptions}
import org.apache.spark.mllib.classification.NaiveBayesModel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.tool.MongodbGridFS
import org.bson.Document

/**
  * Created by fuli.shen on 2016/11/11.
  */
object T {
  def main(args: Array[String]) {
    val dataPath = "hdfs://10.4.1.1:9000/user/fuli.shen/"
    val conf = new SparkConf().setAppName("T").setMaster("local")
    val sc = new SparkContext(conf)
    val sameModel: NaiveBayesModel = NaiveBayesModel.load(sc, dataPath + "model/mllib/categoryNbayes")


    val mongoClient = new MongoClient("10.1.1.8", 27017)
    val doc = new Document("data",sameModel.toString)
    mongoClient.getDatabase("gridfs").getCollection("test").updateMany(Filters.eq("_id", "fuli"), new Document("$set", doc), new UpdateOptions().upsert(true))

//    val mongodbGridFS: MongodbGridFS = new MongodbGridFS("gridfs", mongoClient)
//    val filePath = dataPath + "model/mllib/categoryNbayes";
//    val key = "categoryNbayes"
//    val fileName = "categoryNbayes"
//    // 写gridfs
//    mongodbGridFS.save(new FileInputStream(filePath), key, fileName)
    sc.stop()
  }

}
