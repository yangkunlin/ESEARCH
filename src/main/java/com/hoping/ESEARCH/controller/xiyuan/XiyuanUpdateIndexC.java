package com.hoping.ESEARCH.controller.xiyuan;

import com.alibaba.fastjson.JSON;
import com.hoping.ESEARCH.common.ESParams;
import com.hoping.ESEARCH.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
@RestController
public class XiyuanUpdateIndexC {

    private final IndexService indexService;

    @Autowired
    public XiyuanUpdateIndexC(@Qualifier("indexServiceImpl") IndexService indexService) {
        this.indexService = indexService;
    }

    @RequestMapping(value = "/XIYUAN/Update", method = RequestMethod.POST)
    public String updateIndex(@RequestBody String body) throws Exception {

        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(body);
        boolean isSuccess = indexService.updateWithID(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, jsonMap, jsonMap.get(ESParams.ELASTICSEARCH_ID).toString());
        if (isSuccess) {
            return "Success";
        } else {
            return "Fail";
        }
//        }

    }

}
