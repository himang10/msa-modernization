package com.skcc.redis.sample;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class dataEntity implements Serializable {
    private static final long serialVersionUID = -7312344588204451235L;

    public dataEntity() {
    }

    public dataEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }
    private String key;
    private String value;

    @Override
    public String toString() {
        return "Data{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public HashMap toHashMap(String key, String value) {
        HashMap hMap = new HashMap();
        hMap.put("key", key);
        hMap.put("value", value);
        return hMap;
    }

}
