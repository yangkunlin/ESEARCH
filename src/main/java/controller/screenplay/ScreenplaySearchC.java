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

    @RequestMapping(value = "/SCREENPLAY/SingleFieldSearch", method = RequestMethod.POST)
    public String singleFieldSearch(@RequestBody String body) throws Exception {
        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        StringBuffer identity = new StringBuffer().append("&");
        if (bodyJSON.containsKey("uid")) {
            identity.append(bodyJSON.getAsString("uid")).append(" ");
        }
        if (bodyJSON.containsKey("imei")) {
            identity.append(bodyJSON.getAsString("imei")).append(" ");
        }
        if (bodyJSON.containsKey("meid")) {
            identity.append(bodyJSON.getAsString("meid"));
        }
        identity.append("&");

//        hotField(bodyJSON.getAsString("query_value"));
        logger.info(identity + " single search field:" + bodyJSON.getAsString("query_value"));
        return searchService.singleFieldSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE,
                bodyJSON.getAsString("query_key"), bodyJSON.getAsString("query_value"),
                Integer.valueOf(bodyJSON.getAsString("from")), Integer.valueOf(bodyJSON.getAsString("size")));
    }

    @RequestMapping(value = "/SCREENPLAY/DoubleFieldSearch", method = RequestMethod.POST)
    public String allFieldSearchWithType(@RequestBody String body) throws Exception {
        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        StringBuffer identity = new StringBuffer().append("&");
        if (bodyJSON.containsKey("uid")) {
            identity.append(bodyJSON.getAsString("uid")).append(" ");
        }
        if (bodyJSON.containsKey("imei")) {
            identity.append(bodyJSON.getAsString("imei")).append(" ");
        }
        if (bodyJSON.containsKey("meid")) {
            identity.append(bodyJSON.getAsString("meid"));
        }
        identity.append("&");

//        hotField(bodyJSON.getAsString("query_value"));
        logger.info(identity + " " + bodyJSON.getAsString("type") + " search field:" + bodyJSON.getAsString("query_value"));
        return searchService.allFieldSearchWithType(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, bodyJSON.getAsString("type"),
                bodyJSON.getAsString("query_value"), Integer.valueOf(bodyJSON.getAsString("from")),
                Integer.valueOf(bodyJSON.getAsString("size")));
    }

    @RequestMapping(value = "/SCREENPLAY/AllFieldSearch", method = RequestMethod.POST)
    public String allFieldSearch(@RequestBody String body) throws Exception {
        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        StringBuffer identity = new StringBuffer().append("&");
        if (bodyJSON.containsKey("uid")) {
            identity.append(bodyJSON.getAsString("uid")).append(" ");
        }
        if (bodyJSON.containsKey("imei")) {
            identity.append(bodyJSON.getAsString("imei")).append(" ");
        }
        if (bodyJSON.containsKey("meid")) {
            identity.append(bodyJSON.getAsString("meid"));
        }
        identity.append("&");

//        hotField(bodyJSON.getAsString("query_value"));
        logger.info(identity + " all search field:" + bodyJSON.getAsString("query_value"));
        return searchService.allFieldSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE,
                bodyJSON.getAsString("query_value"),
                Integer.valueOf(bodyJSON.getAsString("from")), Integer.valueOf(bodyJSON.getAsString("size")));
    }

    @RequestMapping(value = "/SCREENPLAY/SearchHotKey", method = RequestMethod.POST)
    public StringBuffer hotFieldSearch(@RequestBody String body) throws Exception {

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        return searchService.hotFieldSearch("search_word", "wordcount", 0, Integer.valueOf(bodyJSON.getAsString("num")));

    }

    @RequestMapping(value = "/SCREENPLAY/Haproxy", method = RequestMethod.GET)
    public void haproxy() throws Exception {

    }

    @RequestMapping(value = "/SCREENPLAY/Search", method = RequestMethod.POST)
    public String fieldSearch(@RequestBody String body) throws Exception {

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

        StringBuffer identity = new StringBuffer().append("&");
        if (bodyJSON.containsKey("uid")) {
            identity.append(bodyJSON.getAsString("uid")).append(" ");
        }
        if (bodyJSON.containsKey("imei")) {
            identity.append(bodyJSON.getAsString("imei")).append(" ");
        }
        if (bodyJSON.containsKey("meid")) {
            identity.append(bodyJSON.getAsString("meid"));
        }
        identity.append("&");

        int from;
        int size;

        if (bodyJSON.containsKey("from")&&bodyJSON.containsKey("size")) {
            from = Integer.valueOf(bodyJSON.getAsString("from"));
            size = Integer.valueOf(bodyJSON.getAsString("size"));
        } else {
            from = 0;
            size = 10;
        }

        if (bodyJSON.containsKey("type")) {

            if (bodyJSON.containsKey("key") && !bodyJSON.getAsString("key").equals("")) {
                logger.info(identity + " all search field:" + bodyJSON.getAsString("key"));

                return searchService.allFieldSearchWithType(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, bodyJSON.getAsString("type"),
                        bodyJSON.getAsString("key"), from, size);
            } else {
                return "request query value";
            }


        } else {

            if (bodyJSON.containsKey("key") && !bodyJSON.getAsString("key").equals("")) {
                logger.info(identity + " all search field:" + bodyJSON.getAsString("key"));

                return searchService.allFieldSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE,
                        bodyJSON.getAsString("key"),
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

        if (bodyJSON.containsKey("from")&&bodyJSON.containsKey("size")) {
            from = Integer.valueOf(bodyJSON.getAsString("from"));
            size = Integer.valueOf(bodyJSON.getAsString("size"));
        } else {
            from = 0;
            size = 10;
        }

        if (bodyJSON.containsKey("type")) {

            return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, bodyJSON.getAsString("type"),
                    bodyJSON.getAsString("recommend"), from, size);
        } else {

            return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE,
                    bodyJSON.getAsString("recommend"), from, size);
        }


    }

//    public void hotField(String str) throws Exception {
//
//        SearchServiceImpl searchService = new SearchServiceImpl();
//        IndexServiceImpl indexService = new IndexServiceImpl();
//
//        String id = indexService.getWithID("search_word", "wordcount", str);
//
//        Map<String, Object> jsonMap = new HashMap<String, Object>();
//        jsonMap.put("field", str);
//        JSONParser jsonParser = new JSONParser();
//
//        if (id == null) {
//            jsonMap.put("times", 1);
//            indexService.addWithID("search_word", "wordcount", jsonMap, str);
//        } else {
//            String requestStr = indexService.getWithID("search_word", "wordcount", str);
//            JSONObject jsonObject = (JSONObject)jsonParser.parse(requestStr);
//            int times = (int) jsonObject.getAsNumber("times");
//            times++;
//            jsonMap.put("times", times);
//            indexService.updateWithID("search_word", "wordcount", jsonMap, str);
//        }
//
//    }

}
