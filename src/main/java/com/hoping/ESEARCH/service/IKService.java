package com.hoping.ESEARCH.service;

import java.util.List;

/**
 * @author YKL on 2018/6/20.
 * @version 1.0
 * @Description:
 * @spark: 梦想开始的地方
 */
public interface IKService {

    List<String> ikAnalyze(String _INDEX, Object _QUERYVALUE) throws Exception;

}
