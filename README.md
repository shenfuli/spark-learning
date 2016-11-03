# fuli.shen sparktraining
Introduces the basics of spark

# how to build dev environment of  spark 

mvn archetype:generate \ <br>
-DarchetypeGroupId=org.scala-tools.archetypes \ <br>
-DarchetypeArtifactId=scala-archetype-simple  \ <br>
-DremoteRepositories=http://scala-tools.org/repo-releases \ <br>
-DarchetypeCatalog=internal \ <br>
-DinteractiveMode=false \ <br>
-Dversion=1.0-SNAPSHOT \ <br>
-DgroupId=org.training.spark \ <br>
-DartifactId=sparktraining <br>


创建项目后就可以编写scala代码，以上仅生成scala项目的目录，如果需要java，可以参考[1] 

# how to package&deploy spark programing 
mvn clean compile package <br>
执行完成会生成一个jar，然后参考bin/wordcount.sh 的shell脚本执行任务 <br>

${SPARK_HOME}/bin/spark-submit \ <br>
    --class org.training.spark.WordCount \ <br>
    --master yarn-cluster \ <br>
    --num-executors 2 \ <br>
    --driver-memory 2g \ <br>
    --executor-memory 2g \ <br>
    --executor-cores 1 \ <br>
    /home/hadoop/fuli.shen/lib/sparktraining-1.0-SNAPSHOT.jar \ <br>
    /user/fuli.shen/data/wordcount/input \ <br>
    /user/fuli.shen/data/wordcount/output <br>

参数说明: <br>
num-executors 启动executors的数量，executor-cores 启动的task任务数，实际的每次执行的task=num-executors * executor-cores


# how to use spark sql 
first,Enter into http://grouplens.org/datasets/movielens/ ,then View MovieLens 10M Dataset-> click "ml-10m.zip" 
# how to user spark to operate csv file 
https://github.com/shenfuli/sparktraining/blob/master/doc/spark-csv.md

# reference

[1]  maven建立java和scala混合的项目 <br>
http://blog.csdn.net/shenfuli/article/details/52668556<br>
[2] windowsGithub上传代码<br>
http://www.cnblogs.com/ruofengzhishang/p/3842587.html<br>
[3] MovieLens 10M Dataset<br>
http://files.grouplens.org/datasets/movielens/ml-10m.zip<br>
[4] CSV Data Source for Apache Spark 1.x<br>
https://github.com/databricks/spark-csv<br>
