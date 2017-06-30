package org.apache.spark.examples.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.feature.Word2VecModel;
import org.nlp.textsimilarity.CosineTextSimilarity;
import org.nlp.word2vec.Word2VecUtils;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Map;

/**
 * JAVA版本的预测，训练详见scala的项目org.apache.spark.examples.mllib.Word2VecExample_Training
 * <p>
 * 实际实时计算 一个句子的Wordvec 的向量时一条记录
 * Created by fuli.shen on 2017/6/28.
 */
public class JavaWord2VecExample_Predict {

    // 1. 加载word2vec 模型
    public static final String w2vModelPath = "hdfs://10.4.1.1:9000/news/W2V/News_W2V_Model/";

    public static void main(String[] args) {
        // 加载模型
        Word2VecModel w2vModel = Word2VecUtils.loadW2VModel(w2vModelPath);
        if (null != w2vModel) {
            System.out.println("-------------------------------1.1.【爱】  关键词的同义词-----------------------------------");
            Tuple2<String, Object>[] w2vModelSynonyms = w2vModel.findSynonyms("爱", 5);
            for (Tuple2<String, Object> tuple2 : w2vModelSynonyms) {
                String word = tuple2._1();
                Double cosineSimilarity = (Double) tuple2._2();
                System.out.println(word + "\t" + cosineSimilarity);
            }
            System.out.println("-------------------------------1.2.【北京】  关键词的同义词-----------------------------------");
            Tuple2<String, Object>[] w2vModelSynonyms_2 = w2vModel.findSynonyms("北京", 5);
            for (Tuple2<String, Object> tuple2 : w2vModelSynonyms_2) {
                String word = tuple2._1();
                Double cosineSimilarity = (Double) tuple2._2();
                System.out.println(word + "\t" + cosineSimilarity);
            }

            System.out.println("-------------------------------2.0.所有词对应的向量表示-----------------------------------");
            Map<String, float[]> w2vModelVectors = w2vModel.getVectors();
            Iterator<Tuple2<String, float[]>> tuple2Iterator = w2vModelVectors.iterator();
            while (tuple2Iterator.hasNext()) {
                Tuple2<String, float[]> tuple2 = tuple2Iterator.next();
                String word = tuple2._1();
                float[] vector = tuple2._2();
                StringBuffer vectorBuffer = new StringBuffer("");
                for (int i = 0; i < vector.length; i++) {
                    vectorBuffer.append(vector[i] + " ");
                }
                System.out.println(word + "\t" + vectorBuffer.toString());
            }
            System.out.println("-------------------------------2.1.句子的向量表示-----------------------------------");
            String[] words_1 = "我 爱 北京 天安门".split(" ");
            int vectorSize = 5;// 词
            double[] wordsVector_1 = Word2VecUtils.buildWordsVector(words_1, vectorSize, w2vModel);
            printWordsVector(wordsVector_1);//0.026998909888789058,-0.01186487590894103,0.024897634619264863,0.05851895920932293,-0.012189864530228078
            String[] words_2 = "我 爱 北京 长城".split(" ");
            double[] wordsVector_2 = Word2VecUtils.buildWordsVector(words_2, vectorSize, w2vModel);
            printWordsVector(wordsVector_2);//0.004943567619193345,0.010045223403722048,0.03035004214325454,0.02609390113502741,0.017606549547053874

            System.out.println("-------------------------------3.距离的余弦距离比较-----------------------------------");
            double cosineScore = CosineTextSimilarity.computeCosineScore(wordsVector_1, wordsVector_2);
            System.out.println(cosineScore);
        }
    }

    private static void printWordsVector(double[] wordsVector_1) {
        StringBuffer wordsBuffer = new StringBuffer();
        int count = 0;
        int wordsVectorLength = wordsVector_1.length;
        for (int i = 0; i < wordsVectorLength; i++) {
            wordsBuffer.append(wordsVector_1[i]);
            count += 1;
            if (count != wordsVectorLength) {
                wordsBuffer.append(",");
            }
        }
        System.out.println(wordsBuffer);
    }
}


/*
程序的输出结果：

-------------------------------1.1.【爱】  关键词的同义词-----------------------------------
天安门	0.025836238180195994
我	0.01429055042819582
长城	0.011960799854055617
北京	-0.06337247028934608
-------------------------------1.2.【北京】  关键词的同义词-----------------------------------
我	0.03604718666914611
天安门	-0.030663284072035994
爱	-0.0517369181295939
长城	-0.0819203330790363
-------------------------------2.0.所有词对应的向量表示-----------------------------------
北京	-0.054095723 0.032535236 -0.063753165 0.079816766 -0.027538558
爱	0.013147644 -0.07890879 0.09052558 0.04301255 0.079554744
我	0.061772514 0.02594618 6.8058673E-4 0.07466619 -0.0075453143
长城	-0.0010501647 0.06060827 0.093947165 -0.0931199 0.025955327
天安门	0.087171204 -0.027032126 0.072137535 0.036580335 -0.09323033
-------------------------------2.1.句子的向量表示-----------------------------------
0.026998909888789058,-0.01186487590894103,0.024897634619264863,0.05851895920932293,-0.012189864530228078
0.004943567619193345,0.010045223403722048,0.03035004214325454,0.02609390113502741,0.017606549547053874
-------------------------------3.距离的余弦距离比较-----------------------------------
0.6483650831565816
*/