package org.apache.spark.mllib

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * TODO
  */
object RandomForestClassification {
  def main(args: Array[String]): Unit = {
    val dataPath = "hdfs://10.4.1.1:9000/user/fuli.shen/"
    val conf = new SparkConf().setAppName("RandomForestClassification").setMaster("local")
    val sc = new SparkContext(conf)
    // TODO sample_libsvm_data格式文件表达的含义
    // Load and parse the data file.
    //val data = MLUtils.loadLibSVMFile(sc, dataPath + "data/mllib/sample_libsvm_data.txt")
    val data = MLUtils.loadLibSVMFile(sc, dataPath + "data/mllib/nbayes_sample_libsvm_data.txt")
    // Split the data into training and test sets (30% held out for testing)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    // Train a RandomForest model.
    // Empty categoricalFeaturesInfo indicates all features are continuous.
    val numClasses = 5
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 3 // Use more in practice.
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 4
    val maxBins = 32

    val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)


    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      println(point.features)
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error = " + testErr)
    println("Learned classification forest model:\n" + model.toDebugString)
  }
}
