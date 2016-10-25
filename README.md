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
mvn clean compile package  执行完成会生成一个jar，然后参考bin/wordcount.sh 的shell脚本执行任务


# reference

##### [1]  maven建立java和scala混合的项目
http://blog.csdn.net/shenfuli/article/details/52668556
#### [2] windowsGithub上传代码
http://www.cnblogs.com/ruofengzhishang/p/3842587.html
