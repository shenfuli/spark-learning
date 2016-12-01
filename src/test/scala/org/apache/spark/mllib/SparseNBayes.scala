package org.apache.spark.mllib

import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fuli.shen on 2016/11/9.
  */
object SparseNBayes {


  def main(args: Array[String]): Unit = {

    val dataPath = "hdfs://10.4.1.1:9000/user/fuli.shen/"
    val conf = new SparkConf().setAppName("SparseNBayes").setMaster("local")
    val sc = new SparkContext(conf)
    //sample_libsvm_data
    val data = MLUtils.loadLibSVMFile(sc, dataPath + "data/mllib/nbayes_sample_libsvm_data.txt")
    val Array(training, test) = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    training.cache()
    //训练模型，lambda 的值为1.0 （默认数值），作用：P(ai|yj)=0，等于0的情况，将其计数值加1
    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")
    val predictionAndLabel = test.map(p => {
      println(p.label + "->" + p.features) //2.0->(3,[2],[2.0])
      (model.predict(p.features), p.label)
    })
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
    println("Accuracy = " + accuracy)

        // (0.0, 0.0, 3.0)->(3,[2],[3.0])
        // (0.0, 1.0, 3.0)->(3 2:1 3:3)
        //  def sparse(size: Int, indices: Array[Int], values: Array[Double]): Vector =
        val size: Int = 3
        val indices: Array[Int] = Array(1,2)
        val values: Array[Double] = Array(1.0,3.0)

        println("v->" + Vectors.sparse(size, indices, values))
        println("Prediction of (0.0, 1.0, 3.0)->(3 2:1 3:3):" + model.predict(Vectors.sparse(size, indices, values)))

    sc.stop()
  }
}
