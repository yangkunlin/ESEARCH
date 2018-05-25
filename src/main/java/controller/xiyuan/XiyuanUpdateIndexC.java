package controller.xiyuan;

import com.alibaba.fastjson.JSON;
import common.ESParams;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.IndexServiceImpl;
import service.SearchServiceImpl;

import java.util.Map;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@RestController
public class XiyuanUpdateIndexC {

    public XiyuanUpdateIndexC() {
    }

    @RequestMapping(value = "/XIYUAN/Update", method = RequestMethod.POST)
    public String updateIndex(@RequestBody String body) throws Exception {

        IndexServiceImpl indexService = new IndexServiceImpl();

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

//        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(body);
//        if (jsonMap.containsKey("data")) {
//            String str1 = jsonMap.get("data").toString();
//            jsonMap.put("data", str1);
//        }
//        if (jsonMap.containsKey("mark")) {
//            String str2 = jsonMap.get("mark").toString();
//            jsonMap.put("mark", str2);
//        }

//        String id = searchService.searchForDel(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, "gid", jsonMap.get("gid"));
//
//        if (id.isEmpty()) {
//            XiyuanAddIndexC xiyuanAddIndexC = new XiyuanAddIndexC();
//            return xiyuanAddIndexC.addIndex(body);
//        } else {
        boolean isSuccess = indexService.updateWithID(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, jsonMap, jsonMap.get(ESParams.ELASTICSEARCH_ID).toString());
        if (isSuccess) {
            return "Success";
        } else {
            return "Fail";
        }
//        }

    }

}
