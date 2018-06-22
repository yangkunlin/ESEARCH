package com.hoping.ESEARCH.service.impl;

import com.alibaba.fastjson.JSON;
import com.hoping.ESEARCH.common.ESParams;
import com.hoping.ESEARCH.service.SearchService;
import com.hoping.ESEARCH.utils.elasticsearch.ESClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

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
@Service(value = "searchServiceImpl")
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
                            .must(QueryBuilders.multiMatchQuery(_QUERYVALUE, ESParams.MARK, ESParams.CONTENT)))
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
                    .setQuery(QueryBuilders.multiMatchQuery(_QUERYVALUE, ESParams.MARK, ESParams.CONTENT))
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
    public String hotFieldSearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = ESClient.getConnection();
        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.matchAllQuery())
                    .setFrom(_FROM).setSize(_SIZE)
                    .addSort(ESParams.HOTKEYTIMES, SortOrder.DESC)
                    .setExplain(true)
                    .get();

            SearchHits hits = searchResponse.getHits();
            String[] strArr = new String[_SIZE];
            int i = 0;

            for (SearchHit hit : hits) {
                strArr[i] = hit.getSourceAsMap().get(ESParams.HOTKEYFIELD).toString();
                i++;
            }

            String jsonString = JSON.toJSONString(strArr);
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (client != null) {
                client = null;
            }
            return null;
        }

    }

    @Override
    public String emptySearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = ESClient.getConnection();
        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.matchAllQuery())
                    //.setPostFilter(QueryBuilders.rangeQuery("age").from(19).to(400))
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

    @Override
    public String singleFieldSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE, String _QUERYFIELD) throws Exception {
        _FROM = _FROM * _SIZE;
        TransportClient client = ESClient.getConnection();
        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.matchQuery(_QUERYFIELD, _QUERYVALUE))
                    .setFrom(_FROM).setSize(_SIZE)
                    .setExplain(true)
                    .get();

            SearchHits hits = searchResponse.getHits();
            List<JSON> jsonList = new ArrayList<>();

            hitsToJsonList(hits, jsonList);
            return JSON.toJSONString(jsonList);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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

    public String recommendSearch(String _INDEX, String _TYPE, Object _TYPEORKEYVALUE, Object _RECOMMENDVALUE, int _FROM, int _SIZE, int _FLAG) throws Exception {
        TransportClient client = ESClient.getConnection();
        SearchResponse searchResponse;
        try {
            if (_FLAG == 0) {
                searchResponse = client.prepareSearch(_INDEX)
                        .setTypes(_TYPE)
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(QueryBuilders.boolQuery()
                                .must(QueryBuilders.matchQuery(ESParams.TYPE, _TYPEORKEYVALUE))
                                .must(QueryBuilders.matchQuery(ESParams.RECOMMEND, Integer.valueOf( _RECOMMENDVALUE.toString().trim()))))
                        .setFrom(_FROM).setSize(_SIZE)
                        .addSort(ESParams.DATETIME, SortOrder.DESC)
                        .setExplain(true)
                        .get();
            } else {
                searchResponse = client.prepareSearch(_INDEX)
                        .setTypes(_TYPE)
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(QueryBuilders.boolQuery()
                                .must(QueryBuilders.multiMatchQuery(_TYPEORKEYVALUE, ESParams.MARK, ESParams.CONTENT))
                                .must(QueryBuilders.matchQuery(ESParams.RECOMMEND, Integer.valueOf( _RECOMMENDVALUE.toString().trim()))))
                        .setFrom(_FROM).setSize(_SIZE)
                        .addSort(ESParams.DATETIME, SortOrder.DESC)
                        .setExplain(true)
                        .get();
            }

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

    public String recommendSearch(String _INDEX, String _TYPE, Object _TYPEVALUE, Object _KEYVALUE, Object _RECOMMENDVALUE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {
            SearchResponse searchResponse = client.prepareSearch(_INDEX)
                    .setTypes(_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(ESParams.TYPE, _TYPEVALUE))
                            .must(QueryBuilders.matchQuery(ESParams.RECOMMEND, Integer.valueOf(_RECOMMENDVALUE.toString().trim())))
                            .must(QueryBuilders.multiMatchQuery(_KEYVALUE, ESParams.MARK, ESParams.CONTENT)))
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

    private void hitsToJsonList(SearchHits hits, List<JSON> jsonList) {
        for (SearchHit hit : hits) {
            jsonList.add((JSON) JSON.toJSON(hit.getSourceAsMap()));
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
