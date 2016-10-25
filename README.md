# fuli.shen sparktraining
Introduces the basics of spark

# how to build dev environment of  spark 

mvn archetype:generate \
-DarchetypeGroupId=org.scala-tools.archetypes \
-DarchetypeArtifactId=scala-archetype-simple  \
-DremoteRepositories=http://scala-tools.org/repo-releases \
-DarchetypeCatalog=internal \
-DinteractiveMode=false \
-Dversion=1.0-SNAPSHOT \
-DgroupId=org.training.spark \
-DartifactId=sparktraining


创建项目后就可以编写scala代码，以上仅生成scala项目的目录，如果需要java，可以参考[1] 

# how to package&deploy spark programing 
mvn clean compile package
执行完成后会生成一个jar包

# reference

##### [1]  maven建立java和scala混合的项目
http://blog.csdn.net/shenfuli/article/details/52668556
#### [2] windowsGithub上传代码
http://www.cnblogs.com/ruofengzhishang/p/3842587.html
