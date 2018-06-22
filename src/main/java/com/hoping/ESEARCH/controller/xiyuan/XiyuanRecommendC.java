package com.hoping.ESEARCH.controller.xiyuan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoping.ESEARCH.common.ESParams;
import com.hoping.ESEARCH.common.MSParams;
import com.hoping.ESEARCH.model.RecommendModel;
import com.hoping.ESEARCH.service.IndexService;
import com.hoping.ESEARCH.service.SearchService;
import com.hoping.ESEARCH.utils.CosineUtil;
import com.hoping.ESEARCH.utils.SortUtil;
import com.hoping.ESEARCH.utils.mysqlUtil.MysqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.*;

/**
 * @author YKL on 2018/6/19.
 * @version 1.0
 * @Description:
 * @spark: 梦想开始的地方
 */
@RestController
public class XiyuanRecommendC {

    private final SearchService searchService;

    private final IndexService indexService;

    private final RecommendModel<String, Set<String>> recommendModel;

    @Autowired
    public XiyuanRecommendC(@Qualifier("searchServiceImpl") SearchService searchService, @Qualifier("indexServiceImpl") IndexService indexService, RecommendModel recommendModel) {
        this.searchService = searchService;
        this.indexService = indexService;
        this.recommendModel = recommendModel;
    }


    @RequestMapping(value = "XIYUAN/CosineRecommendUpdate", method = RequestMethod.POST)
    public boolean CosineRecommendUpdate(@RequestBody String body) throws Exception {

        JSONObject bodyJSON = JSON.parseObject(body);

        int num;
        if (bodyJSON.containsKey(ESParams.HOTKEYNUM)) {
            num = Integer.valueOf(bodyJSON.getString(ESParams.HOTKEYNUM));
        } else {
            num = ESParams.DEFAULT_SIZE;
        }

        ResultSet resultSet = MysqlUtil.getStatement().executeQuery("SELECT videoID, videoName, artists FROM video");
        Map<String, ArrayList<String>> resultMap;
        resultMap = new HashMap<>();
        Map<String, String> scoreMap;
        scoreMap = new HashMap<>();
        while (resultSet.next()) {
            ArrayList<String> fieldList = new ArrayList<>();
            fieldList.add(resultSet.getString(MSParams.VIDEONAME));
            fieldList.add(resultSet.getString(MSParams.ARTISTS));
            resultMap.put(resultSet.getString(MSParams.VIDEOID), fieldList);
        }

        List<Map<String, Object>> listMap = new ArrayList<>();

        for (Object key : resultMap.keySet()) {
            Map<String, Object> tmpMap = new HashMap<>();
            String nameA = resultMap.get(key).get(0);
            String artistA = resultMap.get(key).get(1).split(" ")[0];
            resultSet.first();
            while (resultSet.next()) {
                String nameB = resultSet.getString(MSParams.VIDEONAME);
                String gidB = resultSet.getString(MSParams.VIDEOID);
                String artistB = resultSet.getString(MSParams.ARTISTS).split(" ")[0];
                if (!nameA.equals(nameB)) {
                    Double scoreN = CosineUtil.getSimilarity(nameA, nameB);
                    Double scoreA = CosineUtil.getSimilarity(artistA, artistB);
                    if (scoreN > 0.9 ) {
                        scoreMap.put(gidB, "1.0");
                    } else if (scoreA == 1.0){
                        scoreMap.put(gidB, String.valueOf(scoreA * 0.9));
                    }else {
                        scoreMap.put(gidB, scoreN.toString());
                    }
//                    scoreMap.put(gidB, scoreN.toString());
                }
            }
            Set<String> keySet = SortUtil.sortMapByValue(scoreMap, num).keySet();
            tmpMap.put(ESParams.ELASTICSEARCH_ID, key);
            tmpMap.put(ESParams.TYPE, ESParams.COSINERECOMMENDTYPE);
            tmpMap.put(ESParams.RECOMMENDGID, keySet.toArray());
            listMap.add(tmpMap);
        }
        resultSet.close();
        MysqlUtil.close();
        return indexService.bulkAddWithID(ESParams.RECOMMEND_INDEX, ESParams.RECOMMEND_TYPE, listMap);
    }

    @RequestMapping(value = "XIYUAN/CosineRecommend", method = RequestMethod.POST)
    public String CosineRecommend(@RequestBody String body) throws Exception {
        JSONObject bodyJSON = JSON.parseObject(body);

        if (bodyJSON.containsKey(ESParams.KEY)) {
            String recommendGids = indexService.getWithID(ESParams.RECOMMEND_INDEX, ESParams.RECOMMEND_TYPE, bodyJSON.getString(ESParams.KEY));
            if (recommendGids != null && !recommendGids.isEmpty()) {
                JSONArray gidsJSONArray = JSON.parseObject(recommendGids).getJSONArray(ESParams.RECOMMENDGID);
                JSONArray resultJSONArray = new JSONArray();
                for (Object object : gidsJSONArray) {
                    resultJSONArray.add(indexService.getWithID(ESParams.XIYUAN_INDEX, ESParams.XIYUAN_TYPE, object.toString()));
                }
                return resultJSONArray.toString();
            }else {
                return null;
            }
        } else {
            return null;
        }
    }

}
