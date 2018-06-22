package com.hoping.ESEARCH.model;

import org.springframework.stereotype.Repository;

/**
 * @author YKL on 2018/6/19.
 * @version 1.0
 * @Description:
 * @spark: 梦想开始的地方
 */
@Repository
public class RecommendModel<K, V> {

    private K gid;
    private V recommendGid;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public K getGid() {
        return gid;
    }

    public void setGid(K gid) {
        this.gid = gid;
    }

    public V getRecommendGid() {
        return recommendGid;
    }

    public void setRecommendGid(V recommendGid) {
        this.recommendGid = recommendGid;
    }
}
