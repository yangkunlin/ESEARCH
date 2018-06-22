package com.hoping.ESEARCH.service;

/**
 * Description:
 * @author YKL on 2018/6/12.
 * @version 1.0
 * spark:梦想开始的地方
 */
public interface RedisService<T> {

    T getResult(String type, String dateStr, int num) throws Exception;

}
