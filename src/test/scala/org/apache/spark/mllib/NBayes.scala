package org.apache.spark.mllib

import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.tool.SerializeTool
import org.training.spark.utils.{SerializeTool, RedisTool}

/**
  * Function： 测试案例： 通过贝叶斯分类算法挖掘用户性别,离线计算并生成模型
  *
  * Author: Created by fuli.shen on 2016/6/15.
  *
  *
  * Reference Document：
  * [1]Spark MLlib之朴素贝叶斯分类算法
  * http://blog.selfup.cn/683.html
  * [2]Apache Spark Source Code
  * https://github.com/apache/spark/tree/branch-1.6/examples/src/main/scala/org/apache/spark/examples/mllib
  */
object NBayes {

  def main(args: Array[String]): Unit = {

    val dataPath = "hdfs://10.4.1.1:9000/user/fuli.shen/"
    val conf = new SparkConf().setAppName("NBayes").setMaster("local")
    val sc = new SparkContext(conf)
    val data = sc.textFile("data/mllib/sample_naive_bayes_data.txt")

    //数据预处理并转为特征向量，格式要求(label feature)： 0,1 0 0
    val parsedData = data.map { line =>
      val parts = line.split(',')
      val label = parts(0).toDouble // 类别集合 C={y1,y2,..,yn}，例如本案例中:C={0,1,2}
    val feature = Vectors.dense(parts(1).split(' ').map(_.toDouble)) //样本数据: x={a1,a2,..,am}为一个待分类项，而每个 ai 为 xx 的一个特征属性
      //println("sparse.toDense:"+sparse.toDense)
      //LabeledPoint代表一条训练数据，即打过标签的数据
      LabeledPoint(label, feature)
    }
    //分隔为两个部分，60%的数据用于训练，40%的用于测试
    val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    //训练模型，lambda 的值为1.0 （默认数值），作用：P(ai|yj)=0，等于0的情况，将其计数值加1
    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")

    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
    //用测试数据来验证模型的精度
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
    //由于数据的人为捏造过度，可以看到此次训练的模型精度十分高为100%，即测试数据的类别和用模型预测出来的对于类别完全吻合
    println("Accuracy = " + accuracy)
    //预测类别:预测了2在+2个不在训练数据中的数据，结果和大脑判断的类别完全相同
    println("=======================model=======================")
    println("Prediction of (1.0, 0.0, 0.0):" + model.predict(Vectors.dense(Array[Double](1.0, 0.0, 0.0))))
    println("Prediction of (1.0, 0.0, 1.0):" + model.predict(Vectors.dense(Array[Double](1.0, 0.0, 1.0))))

    println("Prediction of (0.0, 0.0, 0.0):" + model.predict(Vectors.dense(Array[Double](0.0, 0.0, 0.0))))
    println("Prediction of (0.0, 1.0, 0.0):" + model.predict(Vectors.dense(Array[Double](0.0, 1.0, 0.0))))


    //---------------------------------------重点看这里-------------------------------------------------------------
    val key = "rank1category:nbayes"

    /**
      * redis 相关
      */
    val redisTool = new RedisTool("10.1.1.122", 6385, "9icaishi")
    val serializeTool = new SerializeTool

    /**
      * hbase 相关
      */
    val zookeeperQuorum = "10.1.1.120:2181,10.1.1.130:2181,10.1.1.140:2181"
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", zookeeperQuorum)
    val connection = ConnectionFactory.createConnection(hbaseConf)
    val mllibModel: Table = connection.getTable(TableName.valueOf("mllib_model"))

    val modelSerialize: Array[Byte] = serializeTool.serialize(model) //model 序列化
    //序列化数据存储redis
    redisTool.write(key.getBytes(), modelSerialize)
    ////序列化数据存储hbase
    val modelPut = new Put(key.getBytes())
    modelPut.addColumn("info".getBytes(), "data".getBytes(), modelSerialize)
    mllibModel.put(modelPut)


    println("=======================读取MongodbGridFS中序列化的数据:sameModelFromGridFS=======================")
    


    println("=======================读取redis中序列化的数据:sameModelFromRedis=======================")
    val modelReader: Array[Byte] = redisTool.read(key.getBytes())
    val unserializeModel: AnyRef = serializeTool.unserialize(modelReader)
    var sameModelFromRedis: NaiveBayesModel = null
    if (unserializeModel.isInstanceOf[NaiveBayesModel]) {
      println("从Redis中获取Model")
      sameModelFromRedis = unserializeModel.asInstanceOf[NaiveBayesModel]
    }
    println("Prediction of (1.0, 0.0, 0.0):" + sameModelFromRedis.predict(Vectors.dense(Array[Double](1.0, 0.0, 0.0))))
    println("Prediction of (1.0, 0.0, 1.0):" + sameModelFromRedis.predict(Vectors.dense(Array[Double](1.0, 0.0, 1.0))))
    println("Prediction of (0.0, 0.0, 0.0):" + sameModelFromRedis.predict(Vectors.dense(Array[Double](0.0, 0.0, 0.0))))
    println("Prediction of (0.0, 1.0, 0.0):" + sameModelFromRedis.predict(Vectors.dense(Array[Double](0.0, 1.0, 0.0))))



    println("=======================读取HBASE中序列化的数据:sameModelFromHBASE=======================")
    val mllibModelGet = new Get(key.getBytes)
    val mllibModelGetResult: Result = mllibModel.get(mllibModelGet)
    val mllibModelValue: Array[Byte] = mllibModelGetResult.getValue("info".getBytes(), "data".getBytes())
    val unserializeMllibModel: AnyRef = serializeTool.unserialize(mllibModelValue)
    var sameModelFromHBase: NaiveBayesModel = null
    if (unserializeMllibModel.isInstanceOf[NaiveBayesModel]) {
      sameModelFromHBase = unserializeMllibModel.asInstanceOf[NaiveBayesModel]
    }
    println("Prediction of (1.0, 0.0, 0.0):" + sameModelFromHBase.predict(Vectors.dense(Array[Double](1.0, 0.0, 0.0))))
    println("Prediction of (1.0, 0.0, 1.0):" + sameModelFromHBase.predict(Vectors.dense(Array[Double](1.0, 0.0, 1.0))))
    println("Prediction of (0.0, 0.0, 0.0):" + sameModelFromHBase.predict(Vectors.dense(Array[Double](0.0, 0.0, 0.0))))
    println("Prediction of (0.0, 1.0, 0.0):" + sameModelFromHBase.predict(Vectors.dense(Array[Double](0.0, 1.0, 0.0))))

    mllibModel.close()
    sc.stop()
  }
}
