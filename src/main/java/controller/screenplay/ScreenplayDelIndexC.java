package controller.screenplay;

import common.ESParams;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.IndexServiceImpl;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@RestController
public class ScreenplayDelIndexC {

    public ScreenplayDelIndexC() {
    }

    @RequestMapping(value = "/SCREENPLAY/Delete", method = RequestMethod.POST)
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

}
