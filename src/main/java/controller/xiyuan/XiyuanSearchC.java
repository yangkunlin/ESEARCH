package controller.xiyuan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.ESParams;
import common.RSParams;
import model.SearchModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.RedisService;
import service.impl.RedisServiceImpl;
import service.impl.SearchServiceImpl;
import utils.DateUtil;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
@RestController
public class XiyuanSearchC {

    private static Logger logger = LogManager.getRootLogger();

    public XiyuanSearchC() {
    }

    @RequestMapping(value = "/XIYUAN/SearchHotKey", method = RequestMethod.POST)
    public String hotFieldSearch(@RequestBody String body) throws Exception {

        RedisService redisService = new RedisServiceImpl();
        String type = RSParams.REDISSEARCHKEY;
        String dateStr = DateUtil.getYesterday();

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONObject bodyJSON = JSON.parseObject(body);

        int num;
        if (bodyJSON.containsKey(ESParams.HOTKEYNUM)) {
            num = Integer.valueOf(bodyJSON.getString(ESParams.HOTKEYNUM));
        } else {
            num = ESParams.DEFAULT_SIZE;
        }

        String result = redisService.getResult(type, dateStr, num).toString();
        if (result != null) {
            JSONObject resultJSON = JSON.parseObject(result).getJSONObject(RSParams.REDISVALUE);
            if (resultJSON.size() < num) {
                return searchService.hotFieldSearch(ESParams.HOTWORD_INDEX, ESParams.HOTWORD_TYPE, ESParams.DEFAULT_FROM, num);
            } else {
                return JSON.toJSONString(resultJSON.keySet().toArray());
            }
        } else {
            return searchService.hotFieldSearch(ESParams.HOTWORD_INDEX, ESParams.HOTWORD_TYPE, ESParams.DEFAULT_FROM, num);
        }
    }

    @RequestMapping(value = "/XIYUAN/Haproxy", method = RequestMethod.GET)
    public void haproxy() throws Exception {
    }

    @RequestMapping(value = "/XIYUAN/Search", method = RequestMethod.POST)
    public String fieldSearch(@RequestBody String body) throws Exception {

        SearchServiceImpl searchService = new SearchServiceImpl();
        SearchModel searchModel = new SearchModel();
        searchModel.setTime(new DateTime().getMillis());

        JSONObject bodyJSON = JSON.parseObject(body);

        if (bodyJSON.containsKey(ESParams.UID)) {
            searchModel.setUid(bodyJSON.getString(ESParams.UID));
        }
        if (bodyJSON.containsKey(ESParams.IMEI)) {
            searchModel.setImei(bodyJSON.getString(ESParams.IMEI));
        }
        if (bodyJSON.containsKey(ESParams.MEID)) {
            searchModel.setMeid(bodyJSON.getString(ESParams.MEID));
        }

        int from;
        int size;

        if (bodyJSON.containsKey(ESParams.FROM) && bodyJSON.containsKey(ESParams.SIZE)) {
            from = Integer.valueOf(bodyJSON.getString(ESParams.FROM));
            size = Integer.valueOf(bodyJSON.getString(ESParams.SIZE));
        } else {
            from = ESParams.DEFAULT_FROM;
            size = ESParams.DEFAULT_SIZE;
        }

        if (bodyJSON.containsKey(ESParams.TYPE)) {

            searchModel.setType(bodyJSON.getString(ESParams.TYPE));

            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).equals("")) {
                searchModel.setKey(bodyJSON.getString(ESParams.KEY));
                logger.info(com.alibaba.fastjson.JSONObject.toJSONString(searchModel));

                return searchService.allFieldSearchWithType(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, bodyJSON.getString(ESParams.TYPE),
                        bodyJSON.getString(ESParams.KEY), from, size);
            } else {
                return searchService.emptySearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, from, size);
            }
        } else {
            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).equals("")) {
                searchModel.setKey(bodyJSON.getString(ESParams.KEY));
                logger.info(com.alibaba.fastjson.JSONObject.toJSONString(searchModel));

                return searchService.allFieldSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE,
                        bodyJSON.getString(ESParams.KEY),
                        from, size);
            } else {
                return searchService.emptySearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, from, size);
            }
        }

    }

    @RequestMapping(value = "/XIYUAN/Search/Recommend", method = RequestMethod.POST)
    public String recommendSearch(@RequestBody String body) throws Exception {
        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONObject bodyJSON = JSON.parseObject(body);

        int from;
        int size;

        if (bodyJSON.containsKey(ESParams.FROM) && bodyJSON.containsKey(ESParams.SIZE)) {
            from = Integer.valueOf(bodyJSON.getString(ESParams.FROM));
            size = Integer.valueOf(bodyJSON.getString(ESParams.SIZE));
        } else {
            from = ESParams.DEFAULT_FROM;
            size = ESParams.DEFAULT_SIZE;
        }

        if (bodyJSON.containsKey(ESParams.TYPE) && !bodyJSON.getString(ESParams.TYPE).isEmpty()) {
            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).isEmpty()) {
                return searchService.recommendSearch(ESParams.XIYUAN_INDEX,
                        ESParams.XIYUAN_TYPE,
                        bodyJSON.getString(ESParams.TYPE),
                        bodyJSON.getString(ESParams.KEY),
                        bodyJSON.getString(ESParams.RECOMMEND), from, size);
            } else {
                return searchService.recommendSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, bodyJSON.getString(ESParams.TYPE),
                        bodyJSON.getString(ESParams.RECOMMEND), from, size, 0);
            }
        } else {

            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).isEmpty()) {
                return searchService.recommendSearch(ESParams.XIYUAN_INDEX,
                        ESParams.XIYUAN_TYPE,
                        bodyJSON.getString(ESParams.KEY),
                        bodyJSON.getString(ESParams.RECOMMEND), from, size, 1);
            } else {
                return searchService.recommendSearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE,
                        bodyJSON.getString(ESParams.RECOMMEND), from, size);
            }
        }
    }

}
