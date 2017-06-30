package org.nlp.textsimilarity;

import java.io.Serializable;

/**
 * 文本相似度（余弦定理）
 * Created by fuli.shen on 2017/1/5.
 */
public class CosineTextSimilarity implements Serializable {

    /**
     * 分词后的数组一/分词后的数组二 的长度都为size
     */
    private Integer size;

    /**
     * 分词后的数组一
     */
    private int[] x;
    /**
     * 分词后的数组二
     */
    private int[] y;

    public CosineTextSimilarity() {
    }

    public CosineTextSimilarity(Integer size) {
        this.size = size;
    }

    public void setXY(int[] x, int[] y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 外部调用
     *
     * @return 文本距离
     */
    public double run() {
        double rate = handle();
        return rate;
    }

    /**
     * 处理余弦相似度
     *
     * @return
     */
    public double handle() {
        int sum = 0;
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < this.size; i++) {
            Integer xI = x[i];
            Integer yI = y[i];
            sum += xI * yI;
            sumX += Math.pow(xI, 2);
            sumY += Math.pow(yI, 2);
        }
        double rate = sum * 1.0 / Math.sqrt(sumX * sumY);
        return rate;
    }

    public static double computeCosineScore(double[] vectorX, double[] vectorY) {
        if (null == vectorX || null == vectorY || vectorX.length != vectorY.length) {
            return -1.0;
        }

        double sum = 0;
        double sumX = 0;
        double sumY = 0;
        for (int i = 0; i < vectorX.length; i++) {
            double xI = vectorX[i];
            double yI = vectorY[i];
            sum += xI * yI;
            sumX += Math.pow(xI, 2);
            sumY += Math.pow(yI, 2);
        }
        if (sumX <= 0 || sumY <= 0) {
            return -1.0d;
        }
        double rate = sum * 1.0 / Math.sqrt(sumX * sumY);
        return rate;
    }
}
