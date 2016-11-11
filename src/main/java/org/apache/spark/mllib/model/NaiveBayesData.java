package org.apache.spark.mllib.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuli.shen on 2016/11/11.
 */
public class NaiveBayesData implements Serializable{

    private List<Double> labels;

    private List<Double> pi;

    private List<List<Double>> theta;

    private String modelType;

    public NaiveBayesData() {
    }

    public NaiveBayesData(List<Double> labels, List<Double> pi, List<List<Double>> theta, String modelType) {
        this.labels = labels;
        this.pi = pi;
        this.theta = theta;
        this.modelType = modelType;
    }

    public List<Double> getLabels() {
        return labels;
    }

    public void setLabels(List<Double> labels) {
        this.labels = labels;
    }

    public List<Double> getPi() {
        return pi;
    }

    public void setPi(List<Double> pi) {
        this.pi = pi;
    }

    public List<List<Double>> getTheta() {
        return theta;
    }

    public void setTheta(List<List<Double>> theta) {
        this.theta = theta;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
