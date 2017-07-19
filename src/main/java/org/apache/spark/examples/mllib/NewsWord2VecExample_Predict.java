package org.apache.spark.examples.mllib;

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
public class NewsWord2VecExample_Predict {

    // 1. 加载word2vec 模型
    public static final String w2vModelPath = "hdfs://10.4.1.1:9000/news/W2V/News_W2V_Model/";

    public static void main(String[] args) {
        // 加载模型
        Word2VecModel w2vModel = Word2VecUtils.loadW2VModel(w2vModelPath);
        if (null != w2vModel) {
            System.out.println("-------------------------------1.1.【投资】  关键词的同义词-----------------------------------");
            Tuple2<String, Object>[] w2vModelSynonyms = w2vModel.findSynonyms("投资", 10);
            for (Tuple2<String, Object> tuple2 : w2vModelSynonyms) {
                String word = tuple2._1();
                Double cosineSimilarity = (Double) tuple2._2();
                System.out.println(word + "\t" + cosineSimilarity);
            }
            System.out.println("-------------------------------1.2.【黄飞鸿】  关键词的同义词-----------------------------------");
            Tuple2<String, Object>[] w2vModelSynonyms_2 = w2vModel.findSynonyms("黄飞鸿", 10);
            for (Tuple2<String, Object> tuple2 : w2vModelSynonyms_2) {
                String word = tuple2._1();
                Double cosineSimilarity = (Double) tuple2._2();
                System.out.println(word + "\t" + cosineSimilarity);
            }

            System.out.println("-------------------------------2.1.句子的向量表示-----------------------------------");
            String[] words_1 = "体育 北京 男篮 热身赛 磨合 战术 年轻 新秀 崭露头角 热身赛 中 北京队 小将 张才仁 锻炼 北京首钢队 天津 海峡 杯 比赛 中 大胜 台 啤 队 胜 负 战绩 结束 热身赛 旅 昨晚 比赛 中 后卫 方硕 手感 火热 全队 中锋 张松涛 马布里 面对 防守 马布里 持球 欲 突破 孙悦 上篮 孙悦 突破 马布里 突破 防守 方硕 突破 防守 马布里 撇嘴 莫里斯 遭遇 严防 莫里斯 出阵 张才仁 方硕 上篮 朱彦西 突破 吉喆 打暗号 张松涛 强攻 首钢队 热身赛 中 队伍 磨合 新 战术 李根 队 中 球员 顶替 队员 默契 配合 比赛 建立 首钢队 热身赛 中 锻炼 小将 张才仁 青年队 上调 队员 热身赛 中 打球 尚可 外援 莫里斯 说 打球 整体 打法 首钢队 队长 孙悦 热身赛 新 赛季 孙悦 恢复 寻找 比赛 孙悦 说 场上 稍微 身体 比赛 比赛 中 首钢队 不敌 天津 战全胜 锁定 冠军".split(" ");
            int vectorSize = 200;// 词
            double[] wordsVector_1 = Word2VecUtils.buildWordsVector(words_1, vectorSize, w2vModel);
            printWordsVector(wordsVector_1);//0.026998909888789058,-0.01186487590894103,0.024897634619264863,0.05851895920932293,-0.012189864530228078
            String[] words_2 = "体育 领队 调侃 技师 调车 时 维泰尔 安静 叫喊 阿里 巴贝内 说 莱科宁 新浪 体育讯 莱科宁 新加坡 站 季军 本赛季 登上 领奖台 队友 维泰尔 拿出 新加坡 统治 级 胜利 排位 赛后 莱科宁 赛车 不太好 度过 困难 正 赛中莱 科宁 工程师 报告 赛车 驾驶 法拉利 领队 阿德里巴 贝 澄清 莱科宁 车队 调侃 称 冰人 爱 抱怨 阿里 巴贝内 说 抱怨 技师 调校 赛车 塞巴 安静 叫喊 谈到 车手 阿里 巴贝内 说 蒙扎 强 塞巴 强 塞巴 赛道 强大 车手 领奖台".split(" ");
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


*/