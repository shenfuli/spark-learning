package org.apache.spark.core;

import java.io.Serializable;

/**
 * Created by fuli.shen on 2017/1/17.
 */
public class KV implements Serializable{

    private String KEY;

    private Integer VALUE;

    public KV() {
    }

    public KV(String KEY, Integer VALUE) {
        this.KEY = KEY;
        this.VALUE = VALUE;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public Integer getVALUE() {
        return VALUE;
    }

    public void setVALUE(Integer VALUE) {
        this.VALUE = VALUE;
    }
}
