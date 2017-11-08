package org.apache.spark.examples.ml

import org.apache.spark.ml.feature.NGram
import org.apache.spark.sql.SparkSession

object NGramExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("NGramExample")
      .master("local")
      .getOrCreate()

    val wordDataFrame = spark.createDataFrame(Seq(
      (0, Array("Hi", "I", "heard", "about", "Spark")),
      (1, Array("I", "wish", "Java", "could", "use", "case", "classes")),
      (2, Array("Logistic", "regression", "models", "are", "neat"))
    )).toDF("id", "words")

    val ngram = new NGram().setN(2).setInputCol("words").setOutputCol("ngrams")
    val ngramDataFrame = ngram.transform(wordDataFrame)
    //ngramDataFrame.select("ngrams").show(false)
    ngramDataFrame.show()
    spark.stop()
  }
}