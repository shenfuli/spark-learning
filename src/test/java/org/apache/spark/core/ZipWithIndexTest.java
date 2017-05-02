package org.apache.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * def zipWithIndex(): RDD[(T, Long)]
 * <p>
 * 该函数将RDD中的元素和这个元素在RDD中的ID（索引号）组合成键/值对。
 * <p>
 * [hadoop@slave3 ~]$ hdfs dfs -cat  /user/fuli.shen/data/wordcount/input/WORD_COUNT
 * hello   you     hello   me
 * hello   you     hello   china
 * Created by fuli.shen on 2017/1/17.
 */
public class ZipWithIndexTest {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Spark ZipWithIndex").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        String inputPath = "hdfs://10.4.1.1:9000/user/fuli.shen/data/wordcount/input";
        JavaRDD<String> lines = sc.textFile(inputPath);
        JavaRDD<KV> kvMap = lines.filter(line -> !line.isEmpty()).zipWithIndex().map(item -> {
            String line = item._1();
            Long indexLong = item._2();
            int index = indexLong.intValue();
            return new KV(line, index);
        });
        kvMap.foreach(item -> {
            System.out.println(item.getKEY() + "->" + item.getVALUE());
        });
        /*
        hello	you	hello	me->0
        hello	you	hello	china->1
         */
        sc.stop();
    }
}
