package controller.screenplay;

import common.ESParams;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.SearchServiceImpl;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@RestController
public class ScreenplaySearchC {

    private static Logger logger = LogManager.getRootLogger();

    public ScreenplaySearchC() {
    }

    @RequestMapping(value = "/SCREENPLAY/SearchHotKey", method = RequestMethod.POST)
    public StringBuffer hotFieldSearch(@RequestBody String body) throws Exception {

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();
        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        int num;
        if (bodyJSON.containsKey(ESParams.HOTKEYNUM)){
            num = Integer.valueOf(bodyJSON.getAsString(ESParams.HOTKEYNUM));
        } else {
            num = ESParams.DEFAULT_SIZE;
        }

        return searchService.hotFieldSearch(ESParams.HOTWORD_INDEX, ESParams.HOTWORD_TYPE, ESParams.DEFAULT_FROM, num);

    }

    @RequestMapping(value = "/SCREENPLAY/Haproxy", method = RequestMethod.GET)
    public void haproxy() throws Exception {

    }

    @RequestMapping(value = "/SCREENPLAY/Search", method = RequestMethod.POST)
    public String fieldSearch(@RequestBody String body) throws Exception {

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        StringBuilder identity = new StringBuilder().append(ESParams.SPLITSTR);
        if (bodyJSON.containsKey(ESParams.UID)) {
            identity.append(bodyJSON.getAsString(ESParams.UID)).append(" ");
        }
        if (bodyJSON.containsKey(ESParams.IMEI)) {
            identity.append(bodyJSON.getAsString(ESParams.IMEI)).append(" ");
        }
        if (bodyJSON.containsKey(ESParams.MEID)) {
            identity.append(bodyJSON.getAsString(ESParams.MEID));
        }
        identity.append(ESParams.SPLITSTR);

        int from;
        int size;

        if (bodyJSON.containsKey(ESParams.FROM)&&bodyJSON.containsKey(ESParams.SIZE)) {
            from = Integer.valueOf(bodyJSON.getAsString(ESParams.FROM));
            size = Integer.valueOf(bodyJSON.getAsString(ESParams.SIZE));
        } else {
            from = ESParams.DEFAULT_FROM;
            size = ESParams.DEFAULT_SIZE;
        }

        if (bodyJSON.containsKey(ESParams.TYPE)) {

            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getAsString(ESParams.KEY).equals("")) {
                logger.info(ESParams.XIBEN_LOG_FLAG + identity + bodyJSON.getAsString(ESParams.TYPE) + " " + bodyJSON.getAsString(ESParams.KEY));

                return searchService.allFieldSearchWithType(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, bodyJSON.getAsString(ESParams.TYPE),
                        bodyJSON.getAsString(ESParams.KEY), from, size);
            } else {
                return "request query value";
            }


        } else {

            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getAsString(ESParams.KEY).equals("")) {
                logger.info(ESParams.XIBEN_LOG_FLAG + identity + " " + bodyJSON.getAsString(ESParams.KEY));

                return searchService.allFieldSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE,
                        bodyJSON.getAsString(ESParams.KEY),
                        from, size);
            } else {
                return "request query value";
            }


        }

    }

    @RequestMapping(value = "/SCREENPLAY/Search/Recommend", method = RequestMethod.POST)
    public String recommendSearch(@RequestBody String body) throws Exception {
        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        int from;
        int size;

        if (bodyJSON.containsKey(ESParams.FROM)&&bodyJSON.containsKey(ESParams.SIZE)) {
            from = Integer.valueOf(bodyJSON.getAsString(ESParams.FROM));
            size = Integer.valueOf(bodyJSON.getAsString(ESParams.SIZE));
        } else {
            from = ESParams.DEFAULT_FROM;
            size = ESParams.DEFAULT_SIZE;
        }

        if (bodyJSON.containsKey(ESParams.TYPE)) {

            return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, bodyJSON.getAsString(ESParams.TYPE),
                    bodyJSON.getAsString(ESParams.RECOMMEND), from, size);
        } else {

            return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE,
                    bodyJSON.getAsString(ESParams.RECOMMEND), from, size);
        }


    }

}
