package service;

import com.alibaba.fastjson.JSON;
import utils.ESClient;
import common.ESParams;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
public class SearchServiceImpl implements SearchService {

    @Override
    public String allFieldSearchWithType(String _INDEX, String _TYPE, String _TYPEVALUE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        _FROM = _FROM * _SIZE;
        TransportClient client = ESClient.getConnection();
        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(ESParams.TYPE, _TYPEVALUE))
                            .must(QueryBuilders.multiMatchQuery(_QUERYVALUE, ESParams.MARK, ESParams.CONTENT)))//支持一个值同时匹配多个字段
                    //.setPostFilter(QueryBuilders.rangeQuery("age").from(19).to(400))
                    .setFrom(_FROM).setSize(_SIZE)
                    .addSort(ESParams.SCORE, SortOrder.DESC)
                    .addSort(ESParams.DATETIME, SortOrder.DESC)
                    .setExplain(true)
                    .get();

            SearchHits hits = searchResponse.getHits();
            List<JSON> jsonList = new ArrayList<>();

            mapToJsonList(hits, jsonList);

            return JSON.toJSONString(jsonList);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (client != null) {
                client = null;
            }
            List<String> emptyList;
            emptyList = new ArrayList<>();
            return emptyList.toString();
        }

    }


    @Override
    public String allFieldSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        _FROM = _FROM * _SIZE;
        TransportClient client = ESClient.getConnection();
        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.multiMatchQuery(_QUERYVALUE, ESParams.MARK, ESParams.CONTENT))//支持一个值同时匹配多个字段
                    //.setPostFilter(QueryBuilders.rangeQuery("age").from(19).to(400))
                    .setFrom(_FROM).setSize(_SIZE)
                    .addSort(ESParams.SCORE, SortOrder.DESC)
                    .addSort(ESParams.DATETIME, SortOrder.DESC)
                    .setExplain(true)
                    .get();

            SearchHits hits = searchResponse.getHits();
            List<JSON> jsonList = new ArrayList<>();

            mapToJsonList(hits, jsonList);
            return JSON.toJSONString(jsonList);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (client != null) {
                client = null;
            }
            List<String> emptyList;
            emptyList = new ArrayList<>();
            return emptyList.toString();
        }
    }

    @Override
    public StringBuffer hotFieldSearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = ESClient.getConnection();
        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.matchAllQuery())//支持一个值同时匹配多个字段
                    //.setPostFilter(QueryBuilders.rangeQuery("age").from(19).to(400))
                    .setFrom(_FROM).setSize(_SIZE)
                    .addSort(ESParams.HOTKEYTIMES, SortOrder.DESC)
                    .setExplain(true)
                    .get();

            SearchHits hits = searchResponse.getHits();
            StringBuffer jsonString = new StringBuffer();
            String[] strArr = new String[_SIZE];
            int i = 0;

            for (SearchHit hit : hits) {
                strArr[i] = hit.getSourceAsMap().get(ESParams.HOTKEYFIELD).toString();
                i++;
            }

            jsonString.append(JSON.toJSONString(strArr));
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (client != null) {
                client = null;
            }
            return new StringBuffer();
        }

    }

    @Override
    public String recommendSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.matchQuery(ESParams.RECOMMEND, Integer.valueOf(_QUERYVALUE.toString().trim())))
                    .setFrom(_FROM).setSize(_SIZE)
                    .addSort(ESParams.DATETIME, SortOrder.DESC)
                    .setExplain(true)
                    .get();

            SearchHits hits = searchResponse.getHits();
            List<JSON> jsonList = new ArrayList<>();

            mapToJsonList(hits, jsonList);

            return JSON.toJSONString(jsonList);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (client != null) {
                client = null;
            }
            List<String> emptyList;
            emptyList = new ArrayList<>();
            return emptyList.toString();
        }

    }

    public String recommendSearch(String _INDEX, String _TYPE, Object _TYPEVALUE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(ESParams.TYPE, _TYPEVALUE))
                            .must(QueryBuilders.matchQuery(ESParams.RECOMMEND, Integer.valueOf(_QUERYVALUE.toString().trim()))))
                    .setFrom(_FROM).setSize(_SIZE)
                    .addSort(ESParams.DATETIME, SortOrder.DESC)
                    .setExplain(true)
                    .get();
            SearchHits hits = searchResponse.getHits();
            List<JSON> jsonList = new ArrayList<>();

            mapToJsonList(hits, jsonList);

            return JSON.toJSONString(jsonList);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (client != null) {
                client = null;
            }
            List<String> emptyList;
            emptyList = new ArrayList<>();
            return emptyList.toString();
        }

    }

    private void mapToJsonList(SearchHits hits, List<JSON> jsonList) {
        for (SearchHit hit : hits) {
            Map<String, String> map = new HashMap<>();
            map.put(ESParams.TYPE, hit.getSourceAsMap().get(ESParams.TYPE).toString());
            map.put(ESParams.GID, hit.getSourceAsMap().get(ESParams.GID).toString());
            map.put(ESParams.DATA, hit.getSourceAsMap().get(ESParams.DATA).toString());
            jsonList.add((JSON) JSON.toJSON(map));
        }
    }

}
