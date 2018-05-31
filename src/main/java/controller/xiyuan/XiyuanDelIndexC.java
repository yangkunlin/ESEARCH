package controller.xiyuan;

import com.alibaba.fastjson.JSONArray;
import common.ESParams;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
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
public class XiyuanDelIndexC {

    public XiyuanDelIndexC() {
    }

    @RequestMapping(value = "XIYUAN/Delete", method = RequestMethod.POST)
    public String addIndex(@RequestBody String body) throws Exception {

        IndexServiceImpl indexService = new IndexServiceImpl();
        JSONParser jsonParser = new JSONParser();
        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        boolean isSuccess = indexService.delWithID(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, bodyJSON.getAsString(ESParams.ELASTICSEARCH_ID));
        if (isSuccess) {
            return "Success";
        } else {
            return "Fail";
        }

    }

    @RequestMapping(value = "/XIYUAN/DelBatch", method = RequestMethod.POST)
    public String bulkAddIndex(@RequestBody String body) throws ParseException {

        IndexServiceImpl indexService = new IndexServiceImpl();

        boolean isSuccess;
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            JSONArray array = com.alibaba.fastjson.JSONObject.parseArray(body);
            for (Object jsonObject : array) {
                Map<String, Object> jsonMap = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(jsonObject.toString());
                list.add(jsonMap);
            }
            indexService.bulkDelWithID(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, list);
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
