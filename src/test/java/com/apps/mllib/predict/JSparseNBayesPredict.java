package com.apps.mllib.predict;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

/**
 * 新闻多级分类预测
 * Created by fuli.shen on 2016/11/30.

 * 两条测试数据：
 * println(label + "->" + "->" + predict + "->" + p.features)
 * 4.0->->4.0->(272479,[0,1,8,11,12,14,20,23,25,28,36,38,41,51,53,56,97,104,113,119,123,130,147,162,175,180,183,239,246,307,351,361,383,384,412,427,465,571,638,776,778,860,879,924,935,943,949,950,1034,1209,1395,1430,1756,1791,1806,2103,2117,2118,2236,3330,3388,4416,4794,4816,5073,5337,6026,7267,7451,7456,8876,11013,11116,11896,12851,12914,13485,14375,15347,18414,29898,31910],[2.0,2.0,8.0,2.0,3.0,17.0,5.0,18.0,9.0,16.0,11.0,2.0,3.0,6.0,2.0,5.0,2.0,4.0,4.0,2.0,5.0,7.0,3.0,2.0,2.0,2.0,2.0,10.0,4.0,8.0,2.0,2.0,2.0,2.0,10.0,2.0,6.0,4.0,7.0,2.0,4.0,4.0,2.0,6.0,6.0,7.0,2.0,2.0,2.0,2.0,3.0,7.0,2.0,2.0,3.0,2.0,2.0,2.0,2.0,2.0,2.0,2.0,2.0,4.0,2.0,2.0,2.0,3.0,2.0,4.0,2.0,2.0,2.0,4.0,2.0,2.0,2.0,2.0,3.0,3.0,2.0,2.0])
 * 37.0->->35.0->(272479,[48,726,952,1037,1118,1827,1847,2609,2698,28081],[10.0,2.0,2.0,2.0,2.0,5.0,2.0,2.0,2.0,2.0])
 */
public class JSparseNBayesPredict {


    public static void main(String[] args) {


        SparkConf sparkConf = new SparkConf().setAppName("JSparseNBayesPredict").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        NaiveBayesModel naiveBayesModel = NaiveBayesModel.load(jsc.sc(), "hdfs://10.4.1.1:9000/news/mllib/R1-C/model");
        System.out.println("naiveBayesModel:" + naiveBayesModel);
        //特征向量的维度
        int numFeatures = 272479;
        //文章中出现的单词在词典的位置(排名-1=下标从0开始)
        int[] indices = new int[]{0, 1, 8, 11, 12, 14, 20, 23, 25, 28, 36, 38, 41, 51, 53, 56, 97, 104, 113, 119, 123, 130, 147, 162, 175, 180, 183, 239, 246, 307, 351, 361, 383, 384, 412, 427, 465, 571, 638, 776, 778, 860, 879, 924, 935, 943, 949, 950, 1034, 1209, 1395, 1430, 1756, 1791, 1806, 2103, 2117, 2118, 2236, 3330, 3388, 4416, 4794, 4816, 5073, 5337, 6026, 7267, 7451, 7456, 8876, 11013, 11116, 11896, 12851, 12914, 13485, 14375, 15347, 18414, 29898, 31910};
        //文章中出现的单词个数
        double[] values = new double[]{2.0, 2.0, 8.0, 2.0, 3.0, 17.0, 5.0, 18.0, 9.0, 16.0, 11.0, 2.0, 3.0, 6.0, 2.0, 5.0, 2.0, 4.0, 4.0, 2.0, 5.0, 7.0, 3.0, 2.0, 2.0, 2.0, 2.0, 10.0, 4.0, 8.0, 2.0, 2.0, 2.0, 2.0, 10.0, 2.0, 6.0, 4.0, 7.0, 2.0, 4.0, 4.0, 2.0, 6.0, 6.0, 7.0, 2.0, 2.0, 2.0, 2.0, 3.0, 7.0, 2.0, 2.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 4.0, 2.0, 2.0, 2.0, 3.0, 2.0, 4.0, 2.0, 2.0, 2.0, 4.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 2.0, 2.0} ;
        Vector featuresVector =   Vectors.sparse(numFeatures, indices, values);
        double predict = naiveBayesModel.predict(featuresVector);

        System.out.println("predict->" + predict); //predict->4.0

        jsc.stop();
    }

}
