package controller.screenplay;

import common.ESParams;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.IndexServiceImpl;
import service.SearchServiceImpl;

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

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        String id = searchService.searchForDel(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, "gid", bodyJSON.getAsString("gid"));

        if (id.isEmpty()) {
            return "Fail";
        } else {
            boolean isSuccess = indexService.delWithID(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, id.replaceAll("\"", ""));
            if (isSuccess) {
                return "Success";
            } else {
                return "Fail";
            }
        }

    }

}
