# fuli.shen sparktraining
Introduces the basics of spark

# how to build dev environment of  spark 
```
mvn archetype:generate
-DarchetypeGroupId=org.scala-tools.archetypes
-DarchetypeArtifactId=scala-archetype-simple
-DremoteRepositories=http://scala-tools.org/repo-releases
-DarchetypeCatalog=internal
-DinteractiveMode=false
-Dversion=1.0-SNAPSHOT
-DgroupId=org.training.spark
-DartifactId=sparktraining
```

创建项目后就可以编写scala代码，以上仅生成scala项目的目录，如果需要java，可以参考[1] 

# how to package&deploy spark programing 
mvn clean compile package <br>
执行完成会生成一个jar，然后参考bin/wordcount.sh 的shell脚本执行任务 <br>
```
${SPARK_HOME}/bin/spark-submit
    --class org.training.spark.WordCount
    --master yarn-cluster
    --num-executors 2
    --driver-memory 2g
    --executor-memory 2g
    --executor-cores 1
    /home/hadoop/fuli.shen/lib/sparktraining-1.0-SNAPSHOT.jar
    /user/fuli.shen/data/wordcount/input
    /user/fuli.shen/data/wordcount/output
```
参数说明: <br>
num-executors 启动executors的数量，executor-cores 启动的task任务数，实际的每次执行的task=num-executors * executor-cores


# how to use spark sql 
first,Enter into http://grouplens.org/datasets/movielens/ ,then View MovieLens 10M Dataset-> click "ml-10m.zip" 
# how to user spark to operate csv file 
官方案例：参考[4] databricks 开源的程序包，该程序包详细步骤如下：
https://github.com/shenfuli/sparktraining/blob/master/doc/spark-csv.md<br>
本项目实现：
https://github.com/shenfuli/sparktraining/blob/master/src/main/scala/org/training/spark/sql/CarsSparkSQLCSV.scala 可执行的代码

# reference

[1]  maven建立java和scala混合的项目 <br>
http://blog.csdn.net/shenfuli/article/details/52668556<br>
[2] windowsGithub上传代码<br>
http://www.cnblogs.com/ruofengzhishang/p/3842587.html<br>
[3] MovieLens 10M Dataset<br>
http://files.grouplens.org/datasets/movielens/ml-10m.zip<br>
[4] CSV Data Source for Apache Spark 1.x<br>
https://github.com/databricks/spark-csv<br>
