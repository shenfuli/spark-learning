package org.apache.spark.mllib.model;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * Created by fuli.shen on 2016/11/11.
 */
public class NaiveBayesDataTest {


    @Test
    public void test1() {

        String json = "{\"labels\":[0.0,1.0,2.0],\"pi\":[-1.2039728043259361,-0.6931471805599456,-1.6094379124341005],\"theta\":[[-0.4054651081081644,-1" +
                ".791759469228055,-1.791759469228055],[-2.5649493574615367,-0.16705408466316607,-2.5649493574615367],[-1.3862943611198906,-1" +
                ".3862943611198906,-0.6931471805599453]],\"modelType\":\"multinomial\"}";

        NaiveBayesData naiveBayesData = JSON.parseObject(json, NaiveBayesData.class);
        System.out.println("multinomial:" + naiveBayesData.getModelType());
        System.out.println("label:" + naiveBayesData.getLabels());
        System.out.println("pi:" + naiveBayesData.getPi());
        System.out.println("theta:" + naiveBayesData.getTheta());

    }
}
