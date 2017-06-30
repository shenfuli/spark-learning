package org.apache.spark.examples.ml

// $example on$
import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}
// $example off$
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 处理数据为中文 训练
  */

object Word2VecExample_Training {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Word2VecExample_Training example").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val documentDF = sqlContext.createDataFrame(
      Seq(
        "我 爱 北京 天安门".split(" "),
        "我 爱 北京 长城".split(" ")
      ).map(Tuple1.apply) //seq: Seq[(Array[String],)] = List((Array(我, 爱, 北京, 天安门),), (Array(我, 爱, 北京, 长城),))
    ).toDF("text") //documentDF: org.apache.spark.sql.DataFrame = [text: array<string>]
    documentDF.show()
    // Learn a mapping from words to Vectors.
    //val word2Vec = new Word2Vec().setInputCol("text").setOutputCol("result").setVectorSize(5).setMinCount(1).setWindowSize(5)
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(5) // 每个词向量的特征长度
      .setMinCount(1) // 每个词至少出现MinCount
      .setWindowSize(5)
    // 训练的窗口大小，默默人为5
    val model = word2Vec.fit(documentDF)
    val result = model.transform(documentDF) //result: org.apache.spark.sql.DataFrame = [text: array<string>, result: vector]
    result.foreach(println)
    //[WrappedArray(我,爱,北京,长城),[-0.011474037542939186,-0.07864667475223541,0.08584712445735931,-0.09800227731466293,-0.08710567653179169]]
    //[WrappedArray(我,爱,北京,天安门),[0.0681266188621521,-0.07604432106018066,0.059079062193632126,-0.0622531995177269,0.06959295272827148]]
    // [text: array<string>, result: vector]
    // 1.统计指定词的相关的词  Find "num" number of words closest in similarity to the given word, not including the word itself.
    var synonyms = model.findSynonyms("北京", 5)
    synonyms.show()
    /*
    +----+--------------------+
    |word|          similarity|
    +----+--------------------+
    |  北京| 0.06872695551791062|
    |  长城|-0.00405993056585...|
    |   我|-0.07275691084361235|
    | 天安门|-0.09568356525084958|
+----+--------------------+
     */
    // 2. 获取所有词的向量表示
    val vectors = model.getVectors
    vectors.show()
    /**
      * +----+--------------------+
      * |word|              vector|
      * +----+--------------------+
      * |  北京|[-9.1005966532975...|
      * |   爱|[-1.1355073365848...|
      * |   我|[-3.9310288002525...|
      * |  长城|[-2.4651373678352...|
      * | 天安门|[-5.1531993085518...|
      * +----+--------------------+
      */

    // 2.1 获取指定词的词向量
    vectors.registerTempTable("vectors")
    // SQL statements can be run by using the sql methods provided by sqlContext.
    val vectors_sql = sqlContext.sql("SELECT vector FROM vectors where word='北京'")
    vectors_sql.collect() // Array([[0.06223706901073456,-0.041911911219358444,-0.03222038596868515,0.08804278075695038,-0.0013911654241383076]])
    // 3. 每行文本的向量表示
    result.foreach(println)
    /**
      * [WrappedArray(我, 爱, 北京, 长城),  [0.008295329287648201,-0.026133275590837002,0.03728733956813812,-0.006488188169896603,0.02012144576292485]]
      * [WrappedArray(我, 爱, 北京, 天安门),[0.007144642993807793,-0.056503867730498314,0.03312233975157142,2.4347566068172455E-4,-0.009394552209414542]]
      */
    result.select("result").foreach(println)

    // 4. model 存储hdfs

    // model.save("hdfs://10.4.1.1:9000/news/W2V/News_W2V_Model")
  }
}