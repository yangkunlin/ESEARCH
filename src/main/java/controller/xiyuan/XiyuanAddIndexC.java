package controller.xiyuan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.ESParams;
import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.IndexServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@RestController
public class XiyuanAddIndexC {

    public XiyuanAddIndexC() {
    }

    @RequestMapping(value = "/XIYUAN/Add", method = RequestMethod.POST)
    public String addIndex(@RequestBody String body) throws ParseException {

        IndexServiceImpl indexService = new IndexServiceImpl();

        boolean isSuccess;
        try {
            Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(body);
            indexService.addWithID(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, jsonMap, jsonMap.get(ESParams.ELASTICSEARCH_ID).toString());
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

       if (isSuccess) {
            return "Success";
       } else {
            return "Fail";
       }

    }

    @RequestMapping(value = "/XIYUAN/AddBatch", method = RequestMethod.POST)
    public String bulkAddIndex(@RequestBody String body) throws ParseException {

        IndexServiceImpl indexService = new IndexServiceImpl();

        boolean isSuccess;
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            JSONArray array = JSONObject.parseArray(body);
            for (Object jsonObject : array) {
                Map<String, Object> jsonMap = (Map<String, Object>) JSONObject.parse(jsonObject.toString());
                list.add(jsonMap);
            }
            indexService.bulkAddWithID(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, list);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        if (isSuccess) {
            return "Success";
        } else {
            return "Fail";
        }
    }

}
