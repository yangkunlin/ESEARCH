package com.hoping.ESEARCH.model;

/**
 * Author:  yangkunlin
 * Date:    2018/5/29
 * Domain:  pla.hc10
 */
public class RedisResultModel<K, V> {

    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
