#!/bin/bash
source ~/.bashrc
/home/hadoop/caishi/local/spark-1.6.0/bin/spark-submit \
    --class org.training.spark.JWordCount \
    --master yarn-cluster \
    --num-executors 2 \
    --driver-memory 2g \
    --executor-memory 2g \
    --executor-cores 1 \
    /home/hadoop/fuli.shen/lib/sparktraining-1.0-SNAPSHOT.jar \
    /user/fuli.shen/data/wordcount/input \
    /user/fuli.shen/data/wordcount/output
    
