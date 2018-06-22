package com.hoping.ESEARCH.controller.screenplay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoping.ESEARCH.common.ESParams;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hoping.ESEARCH.service.impl.IndexServiceImpl;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
@RestController
public class ScreenplayDelIndexC {

    public ScreenplayDelIndexC() {
    }

    @RequestMapping(value = "/SCREENPLAY/Delete", method = RequestMethod.POST)
    public String addIndex(@RequestBody String body) throws Exception {

        IndexServiceImpl indexService = new IndexServiceImpl();
        JSONObject bodyJSON = JSON.parseObject(body);

        boolean isSuccess = indexService.delWithID(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, bodyJSON.getString(ESParams.ELASTICSEARCH_ID));
        if (isSuccess) {
            return "Success";
        } else {
            return "Fail";
        }

    }

}
