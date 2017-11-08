#!/bin/bash
server_log_home="./logs"
class_name="org.apache.spark.examples.SparkPi"

pid=`ps -ef | grep ${class_name} | grep -v "grep" |grep -v "spark_deploy.sh"| awk '{print $2}'`

sleep 5

[ "x$pid" != "x" ] && kill $pid

classpath=""
for file in /home/hadoop/caishi/local/spark-2.1.1-bin-hadoop2.7/jars/*.jar
do
        classpath="${file},${classpath}"
done

## server_log_home null check
[ -z ${server_log_home} ] && echo "server_log_home is null!!!" && exit 255

## create log directory
[ ! -e ${server_log_home} ] && mkdir -p ${server_log_home}

##  server_log_home is not a directory
[ ! -d ${server_log_home} ] && echo "server_log_home is not a directory!!!" && exit 255
/home/hadoop/caishi/local/spark-2.1.1-bin-hadoop2.7/bin/spark-submit --class org.apache.spark.examples.SparkPi \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --queue thequeue \
    --jars $classpath \
    examples/jars/spark-examples_2.11-2.1.1.jar \
    10