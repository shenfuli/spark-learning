package org.training.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * 统计单词出现次数
 * Created by fuli.shen on 2016/10/25.
 */
public class JWordCount {

    // /user/fuli.shen/data/wordcount/input /user/fuli.shen/data/wordcount/output
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage:org.training.spark.JWordCount <inputpath><outputpath>");
            System.exit(0);
        }

        SparkConf conf = new SparkConf().setAppName("JWordCount");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<String> textFileRDD = jsc.textFile(args[0]);

        JavaPairRDD<String, Integer> wordRDD = textFileRDD.flatMap(line -> Arrays.asList(line.split("\t"))).mapToPair(word -> new Tuple2<>
                (word, 1)).reduceByKey((v1, v2) -> v1 + v2);

        wordRDD.saveAsTextFile(args[1]);

        jsc.stop();
    }
}

