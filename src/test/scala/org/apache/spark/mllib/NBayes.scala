package org.apache.spark.mllib

import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.{SparseVector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}

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
      val label = parts(0).toDouble// 类别集合 C={y1,y2,..,yn}，例如本案例中:C={0,1,2}
      val feature = Vectors.dense(parts(1).split(' ').map(_.toDouble))//样本数据: x={a1,a2,..,am}为一个待分类项，而每个 ai 为 xx 的一个特征属性

      val sparse: SparseVector = feature.toSparse

      //println("LabeledPoint: label=" + label+ "\tfeature=" + feature)
      println(label+"->"+sparse)
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
  }
}
