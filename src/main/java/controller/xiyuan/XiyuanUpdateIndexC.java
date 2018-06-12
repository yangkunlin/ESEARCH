package controller.xiyuan;

import com.alibaba.fastjson.JSON;
import common.ESParams;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.impl.IndexServiceImpl;

import java.util.Map;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
@RestController
public class XiyuanUpdateIndexC {

    public XiyuanUpdateIndexC() {
    }

    @RequestMapping(value = "/XIYUAN/Update", method = RequestMethod.POST)
    public String updateIndex(@RequestBody String body) throws Exception {

        IndexServiceImpl indexService = new IndexServiceImpl();

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
