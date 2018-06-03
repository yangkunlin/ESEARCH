package controller.xiyuan;

import common.ESParams;
import model.SearchModel;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
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
public class XiyuanSearchC {

    private static Logger logger = LogManager.getRootLogger();

    public XiyuanSearchC() {
    }

    @RequestMapping(value = "/XIYUAN/SearchHotKey", method = RequestMethod.POST)
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

    @RequestMapping(value = "/XIYUAN/Haproxy", method = RequestMethod.GET)
    public void haproxy() throws Exception {
    }

    @RequestMapping(value = "/XIYUAN/Search", method = RequestMethod.POST)
    public String fieldSearch(@RequestBody String body) throws Exception {

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONParser jsonParser = new JSONParser();
        SearchModel searchModel = new SearchModel();
        searchModel.setTime(new DateTime().getMillis());

        JSONObject bodyJSON = (JSONObject) jsonParser.parse(body);

//        StringBuilder identity = new StringBuilder().append(ESParams.SPLITSTR + " ");
//        if (bodyJSON.containsKey(ESParams.UID)) {
//            identity.append(ESParams.UID + ":[").append(bodyJSON.getAsString(ESParams.UID)).append("] ");
//        }
//        if (bodyJSON.containsKey(ESParams.IMEI)) {
//            identity.append(ESParams.IMEI + ":[").append(bodyJSON.getAsString(ESParams.IMEI)).append("] ");
//        }
//        if (bodyJSON.containsKey(ESParams.MEID)) {
//            identity.append(ESParams.MEID + ":[").append(bodyJSON.getAsString(ESParams.MEID)).append("] ");
//        }
//        identity.append(ESParams.SPLITSTR);

        if (bodyJSON.containsKey(ESParams.UID)) {
            searchModel.setUid(bodyJSON.getAsString(ESParams.UID));
        }
        if (bodyJSON.containsKey(ESParams.IMEI)) {
            searchModel.setImei(bodyJSON.getAsString(ESParams.IMEI));
        }
        if (bodyJSON.containsKey(ESParams.MEID)) {
            searchModel.setMeid(bodyJSON.getAsString(ESParams.MEID));
        }

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

            searchModel.setType(bodyJSON.getAsString(ESParams.TYPE));

            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getAsString(ESParams.KEY).equals("")) {
                searchModel.setKey(bodyJSON.getAsString(ESParams.KEY));
                logger.info(com.alibaba.fastjson.JSONObject.toJSONString(searchModel));

                return searchService.allFieldSearchWithType(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, bodyJSON.getAsString(ESParams.TYPE),
                        bodyJSON.getAsString(ESParams.KEY), from, size);
            } else {
                return searchService.allFieldSearchWithType(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, bodyJSON.getAsString(ESParams.TYPE),
                        "", from, size);
            }


        } else {

            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getAsString(ESParams.KEY).equals("")) {
                searchModel.setKey(bodyJSON.getAsString(ESParams.KEY));
                logger.info(com.alibaba.fastjson.JSONObject.toJSONString(searchModel));

                return searchService.allFieldSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE,
                        bodyJSON.getAsString(ESParams.KEY),
                        from, size);
            } else {
                return searchService.allFieldSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE,
                        "", from, size);
            }


        }

    }

    @RequestMapping(value = "/XIYUAN/Search/Recommend", method = RequestMethod.POST)
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

            return searchService.recommendSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, bodyJSON.getAsString(ESParams.TYPE),
                    bodyJSON.getAsString(ESParams.RECOMMEND), from, size);
        } else {

            return searchService.recommendSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE,
                    bodyJSON.getAsString(ESParams.RECOMMEND), from, size);
        }


    }

}
