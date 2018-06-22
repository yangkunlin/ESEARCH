package com.hoping.ESEARCH.controller.screenplay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoping.ESEARCH.common.ESParams;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hoping.ESEARCH.service.impl.SearchServiceImpl;

/**
 * @author YKL on 2018/4/18.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
@RestController
public class ScreenplaySearchC {

//    private static Logger logger = LogManager.getRootLogger();

    public ScreenplaySearchC() {
    }

    @RequestMapping(value = "/SCREENPLAY/SearchHotKey", method = RequestMethod.POST)
    public String hotFieldSearch(@RequestBody String body) throws Exception {

        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONObject bodyJSON = JSON.parseObject(body);

        int num;
        if (bodyJSON.containsKey(ESParams.HOTKEYNUM)){
            num = Integer.valueOf(bodyJSON.getString(ESParams.HOTKEYNUM));
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

        JSONObject bodyJSON = JSON.parseObject(body);

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

        int from;
        int size;

        if (bodyJSON.containsKey(ESParams.FROM)&&bodyJSON.containsKey(ESParams.SIZE)) {
            from = Integer.valueOf(bodyJSON.getString(ESParams.FROM));
            size = Integer.valueOf(bodyJSON.getString(ESParams.SIZE));
        } else {
            from = ESParams.DEFAULT_FROM;
            size = ESParams.DEFAULT_SIZE;
        }

        if (bodyJSON.containsKey(ESParams.TYPE)) {
            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).equals("")) {
                return searchService.allFieldSearchWithType(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, bodyJSON.getString(ESParams.TYPE),
                        bodyJSON.getString(ESParams.KEY), from, size);
            } else {
                return searchService.emptySearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, from, size);
            }
        } else {
            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).equals("")) {
                return searchService.allFieldSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE,
                        bodyJSON.getString(ESParams.KEY),
                        from, size);
            } else {
                return searchService.emptySearch(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, from, size);
            }


        }

    }

    @RequestMapping(value = "/SCREENPLAY/Search/Recommend", method = RequestMethod.POST)
    public String recommendSearch(@RequestBody String body) throws Exception {
        SearchServiceImpl searchService = new SearchServiceImpl();
        JSONObject bodyJSON = JSON.parseObject(body);

        int from;
        int size;

        if (bodyJSON.containsKey(ESParams.FROM)&&bodyJSON.containsKey(ESParams.SIZE)) {
            from = Integer.valueOf(bodyJSON.getString(ESParams.FROM));
            size = Integer.valueOf(bodyJSON.getString(ESParams.SIZE));
        } else {
            from = ESParams.DEFAULT_FROM;
            size = ESParams.DEFAULT_SIZE;
        }

        if (bodyJSON.containsKey(ESParams.TYPE) && !bodyJSON.getString(ESParams.TYPE).isEmpty()) {
            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).isEmpty()) {
                return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX,
                        ESParams.SCREENPLAY_TYPE,
                        bodyJSON.getString(ESParams.TYPE),
                        bodyJSON.getString(ESParams.KEY),
                        bodyJSON.getString(ESParams.RECOMMEND), from, size);
            } else {
                return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE, bodyJSON.getString(ESParams.TYPE),
                        bodyJSON.getString(ESParams.RECOMMEND), from, size, 0);
            }
        } else {

            if (bodyJSON.containsKey(ESParams.KEY) && !bodyJSON.getString(ESParams.KEY).isEmpty()) {
                return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX,
                        ESParams.SCREENPLAY_TYPE,
                        bodyJSON.getString(ESParams.KEY),
                        bodyJSON.getString(ESParams.RECOMMEND), from, size, 1);
            } else {
                return searchService.recommendSearch(ESParams.SCREENPLAY_INDEX, ESParams.SCREENPLAY_TYPE,
                        bodyJSON.getString(ESParams.RECOMMEND), from, size);
            }
        }


    }

}
