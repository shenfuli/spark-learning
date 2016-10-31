package org.training.spark.sql

import org.apache.spark.sql._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 通过 http://www.grouplens.org/ 中的movielens 数据，演示SparkSQL的使用，具体内容如下：
  * 1：RDD转为DataFrame的3中方式
  * 1.1 显式为RDD注入schema，将其变换为DataFrame
  * 1.2 通过反射方式，为RDD注入schema，将其变换为DataFrame
  * 1.3 读取json格式数据 转RDD
  * 2:DataFrame/DataSet/SparkSQL 操作数据
  * 2.1: DataFrame API 操作数据
  * 2.2：DataFrame 注册临时表->Spark SQL 操作数据
  * 2.3: DataFrame<->DataSet 相互转为,DataSet 可以做到编译期间检查，提前发现错误
  *
  * Created by fuli.shen on 2016/10/31.
  */
object UMRSparkSQL {

  case class User(userID: String, gender: String, age: Integer, occupation: String, zipCode: String)


  def main(args: Array[String]) {

    var masterUrl = "local[1]"
    var dataPath = "/user/fuli.shen/data/ml-1m/"
    if (args.length != 0) {
      masterUrl = args(0)
      dataPath = args(1)
    }
    val conf = new SparkConf().setAppName(UMRSparkSQL.getClass.getSimpleName).setMaster(masterUrl)
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc)

    /**
      * Create RDDs
      * users.dat format is =>UserID::Gender::Age::Occupation::Zip-code
      */
    val usersRdd = sc.textFile(dataPath + "users.dat").cache()

    /**
      * Method 1: 通过显式为RDD注入schema，将其变换为DataFrame
      *
      * rdd->case class->toDF
      */
    val usersRDD = usersRdd.map(user => user.split("::")).map(userFields => User(userFields(0).trim, userFields(1).trim, userFields(2).trim.toInt, userFields(3).trim, userFields(4).trim))
    import sqlContext.implicits._
    val userDataFrame = usersRDD.toDF()
    userDataFrame.show(5)


    /**
      * Method 2: 通过反射方式，为RDD注入schema，将其变换为DataFrame
      *
      * rdd->Row->schema->createDataFrame
      *
      */
    val rowRDD = usersRdd.map(user => user.split("::")).map(userFields => Row(userFields(0).trim, userFields(1).trim, userFields(2).toInt, userFields(3).trim, userFields(4).trim))
    val schema = StructType(
      StructField("userID", StringType, true) ::
        StructField("gender", StringType, false) ::
        StructField("age", IntegerType, false) ::
        StructField("occupation", StringType, false) ::
        StructField("zipCcode", StringType, false) :: Nil
    )
    val userDataFrame2 = sqlContext.createDataFrame(rowRDD, schema)
    userDataFrame2.show(5)
    userDataFrame2.write.mode(SaveMode.Overwrite).json(dataPath + "user.json")
    userDataFrame2.write.mode(SaveMode.Overwrite).parquet(dataPath + "user.parquet")

    /**
      * 读取json格式数据1： read.format("json").load(...)
      */
    val userJsonDF = sqlContext.read.format("json").load(dataPath + "user.json")
    userJsonDF.show(5)

    /**
      * 读取json格式数据2： read.json(...)
      */
    val userJsonDF2 = sqlContext.read.format("parquet").load(dataPath + "user.parquet")
    userJsonDF2.show(5)


    val ratingsRdd = sc.textFile(dataPath + "ratings.dat")
    val ratingsRowRDD = ratingsRdd.map(_.split("::")).map(p => Row(p(0), p(1), p(2), p(3)))

    val ratingsSchemaString = "userID::movieID::rating::timestamp"
    val ratingsSchema = StructType(ratingsSchemaString.split("::").map(fieldName => StructField(fieldName, StringType, true)))
    val ratingsDataFrame = sqlContext.createDataFrame(ratingsRowRDD, ratingsSchema)
    //DF API 操作数据:按照性别、年龄统计用户对电影movieID = 2116的评价人数,并按照评价数降序排序
    val userRatingsDF = ratingsDataFrame.filter("movieID = 2116").join(userDataFrame, "userID")
      .select("gender", "age")
      .groupBy("gender", "age")
      .count
      .sort($"count".desc)
    userRatingsDF.show(5)


    //DF 注册临时表操作数据:按照性别、年龄统计用户对电影movieID = 2116的评价人数,并按照评价数降序排序
    userDataFrame.registerTempTable("user")
    ratingsDataFrame.registerTempTable("ratings")
    val userRatings = sqlContext.sql("select gender,age ,count(1) count from user u join ratings t on(t.userID=u.userID) where t.movieID = 2116  group by u.gender, u.age order by count(1) desc ")
    userRatings.show(5)

    //DF 转为DS，DS格式在编译期类型检查，相比DF可以提前发现错误，并且可以操作domain对象，相比RDD操作比较友好,推荐使用DS操作
    //DS 转为DF时，扔保留了原来的schema，非常方便
    val userDataSet = userDataFrame.as[User]
    userDataSet.filter(u => u.age > 20).show(5)

    val userDS2DF = userDataSet.toDF()
    userDS2DF.filter("age>20").show(5)
    sc.stop()
  }
}
