package org.apache.spark.examples.ml

// $example on$
import org.apache.spark.ml.feature.{Word2Vec, Word2VecModel}
// $example off$
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 处理数据为中文预测-采用DataFrame
  */
object Word2VecExample_Predict {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("Word2VecExample_Predict example").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    val w2vModelPath = "hdfs://10.4.1.1:9000/news/W2V/News_W2V_Model/"

    // 加载word2vec 模型
    val w2vModel = Word2VecModel.load(w2vModelPath)
    // 获取指定词的同义词
    val synonyms = w2vModel.findSynonyms("爱", 5)
    synonyms.show()
    // 获取当前模型所有词的向量表示
    val vectors = w2vModel.getVectors
    vectors.show()
    // 获取句子的向量
    val documentDF = sqlContext.createDataFrame(Seq(
      "我 爱 北京 天安门".split(" "),
      "我 爱 北京 长城".split(" ")
    ).map(Tuple1.apply)).toDF("text")
    val result = w2vModel.transform(documentDF)

    val resultDataFrame = result.select("result")
    resultDataFrame.foreach(println)
    /**
      [WrappedArray(我, 爱, 北京, 长城),  [0.008295329287648201,-0.026133275590837002,0.03728733956813812,-0.006488188169896603,0.02012144576292485]]
      [WrappedArray(我, 爱, 北京, 天安门),[0.007144642993807793,-0.056503867730498314,0.03312233975157142,2.4347566068172455E-4,-0.009394552209414542]]
    */
    //   [[0.007144642993807793,-0.056503867730498314,0.03312233975157142,2.4347566068172455E-4,-0.009394552209414542]]
    //  [[0.008295329287648201,-0.026133275590837002,0.03728733956813812,-0.006488188169896603,0.02012144576292485]]
    sc.stop()
  }
}