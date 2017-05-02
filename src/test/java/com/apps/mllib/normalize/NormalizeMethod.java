package com.apps.mllib.normalize;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fuli.shen on 2017/1/9.
 */
public class NormalizeMethod {

    @Test
    public void testNormalization() {

        Integer[] x = new Integer[]{1, 1, 2, 4, 6, 8, 20};
        List<Integer> xLists = Arrays.asList(x);
        System.out.println("--------------------------Math.log--------------------------");
        for (int value : x) {
            double xv = Math.log10(value);
            System.out.println(value + "->" + xv);
        }
        System.out.println("--------------------------Standary Method--------------------------");
        int minValue = 1;
        int maxValue = 20;
        for (int value : x) {
            double xv = (value - minValue) * 1.0 / (maxValue - minValue);
            System.out.println(xv);
        }
    }
}
