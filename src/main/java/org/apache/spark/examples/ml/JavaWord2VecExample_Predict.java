package org.apache.spark.examples.ml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.Word2VecModel;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.*;
import org.apache.spark.mllib.linalg.DenseVector;
import org.dmg.pmml.TextModelSimiliarity;
import org.nlp.textsimilarity.CosineTextSimilarity;

import java.util.Arrays;

/**
 * JAVA版本的预测，训练详见scala的项目
 * <p>
 * 实际实时计算 一个句子的Wordvec 的向量时一条记录
 * Created by fuli.shen on 2017/6/28.
 */
public class JavaWord2VecExample_Predict {

    // 1. 加载word2vec 模型
    public static final String w2vModelPath = "hdfs://10.4.1.1:9000/news/W2V/News_W2V_Model/";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaWord2VecExample_Predict").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // 加载模型
        Word2VecModel w2vModel = loadW2VModel(w2vModelPath);
        String sentence_1 = "我 爱 北京 长城";//我 爱 北京 长城=>[0.008295329287648201,-0.026133275590837002,0.03728733956813812,-0.006488188169896603,0.02012144576292485]
        // Input data: Each row is a bag of words from a sentence or document.
        DenseVector denseVector_1 = buildSentenceDenseVector(sentence_1, w2vModel,jsc);
        System.out.println(sentence_1+"=>" + denseVector_1);

        String sentence_2 = "我 爱 北京 天安门";
        // Input data: Each row is a bag of words from a sentence or document.
        DenseVector denseVector_2 = buildSentenceDenseVector(sentence_2, w2vModel,jsc);
        System.out.println(sentence_2+"=>" + denseVector_2);//我 爱 北京 天安门=>[0.008295329287648201,-0.026133275590837002,0.03728733956813812,-0.006488188169896603,0.02012144576292485]

        double[] x = denseVector_1.toArray();
        double[] y = denseVector_2.toArray();

        double cosineScore = CosineTextSimilarity.computeCosineScore(x, y);
        System.out.println(cosineScore);//0.7619795645989174
    }

    public static Word2VecModel loadW2VModel(String w2vModelPath){
        Word2VecModel w2vModel = Word2VecModel.load(w2vModelPath);
        return w2vModel;
    }

    public static DenseVector buildSentenceDenseVector(String sentence, Word2VecModel w2vModel, JavaSparkContext jsc) {

        DenseVector sentenceDenseVector = null;
        SQLContext sqlContext = new SQLContext(jsc);
        JavaRDD<Row> jrdd = jsc.parallelize(Arrays.asList(
                RowFactory.create(Arrays.asList(sentence.split(" ")))
        ));
        StructType schema = new StructType(new StructField[]{
                new StructField("text", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });
        DataFrame documentDF = sqlContext.createDataFrame(jrdd, schema);
        DataFrame result = w2vModel.transform(documentDF);
        Row[] results = result.select("result").take(1);
        if (null != results && results.length == 1) {
            Object sentenceObject = results[0].get(0);
            if (null != sentenceObject) {
                sentenceDenseVector = (DenseVector) sentenceObject;
            }
        }
        return sentenceDenseVector;
    }
}
