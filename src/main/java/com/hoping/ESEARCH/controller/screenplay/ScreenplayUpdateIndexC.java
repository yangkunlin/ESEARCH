package com.hoping.ESEARCH.controller.screenplay;

import com.alibaba.fastjson.JSON;
import com.hoping.ESEARCH.common.ESParams;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hoping.ESEARCH.service.impl.IndexServiceImpl;

import java.util.Map;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
@RestController
public class ScreenplayUpdateIndexC {

    public ScreenplayUpdateIndexC() {
    }

    @RequestMapping(value = "/SCREENPLAY/Update", method = RequestMethod.POST)
    public String updateIndex(@RequestBody String body) throws Exception {

        IndexServiceImpl indexService = new IndexServiceImpl();

        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(body);

        boolean isSuccess = indexService.updateWithID(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, jsonMap, jsonMap.get(ESParams.ELASTICSEARCH_ID).toString());
        if (isSuccess) {
            return "Success";
        } else {
            return "Fail";
        }

    }

}
