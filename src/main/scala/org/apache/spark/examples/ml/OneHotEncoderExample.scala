package org.apache.spark.examples.ml

import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer}
import org.apache.spark.sql.SparkSession

/**
  * http://blog.csdn.net/wangpei1949/article/details/53140372
  *
  */
object OneHotEncoderExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("OneHotEncoderExample")
      .getOrCreate()


    val df = spark.createDataFrame(Seq(
      (0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c")
    )).toDF("id", "category")

    val indexer = new StringIndexer()
      .setInputCol("category").setOutputCol("categoryIndex").fit(df)

    val indexed = indexer.transform(df)
    //Spark源码: The last category is not included by default 最后一个种类默认不包含
    // 设置最后一个是否包含,区别python scikit-learn's OneHotEncoder
    val encoder = new OneHotEncoder().setInputCol("categoryIndex").setOutputCol("categoryVec").setDropLast(false)
    //transform 转换成稀疏向量
    val encoded = encoder.transform(indexed)
    encoded.show(20)
    spark.stop()
  }
}
