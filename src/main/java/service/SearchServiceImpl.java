package service;

import com.alibaba.fastjson.JSON;
import common.ESClient;
import common.IKSplit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
public class SearchServiceImpl implements SearchService {
    @Override
    public String singleFieldSearch(String _INDEX, String _TYPE, String _QUERYKEY, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        _FROM = _FROM * _SIZE;

        TransportClient client = new ESClient().getConnection();

        SearchResponse searchResponse = client.prepareSearch(_INDEX)
                .setTypes(_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(getWildcardQueryBuilder(_QUERYKEY, _QUERYVALUE.toString()))
                .addSort("datetime", SortOrder.DESC)
                .setFrom(_FROM).setSize(_SIZE).setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();
        List<JSON> jsonList = new ArrayList<>();

        mapToJsonList(hits, jsonList);

        return JSON.toJSONString(jsonList);
    }

    @Override
    public String allFieldSearchWithType(String _INDEX, String _TYPE, String _TYPEVALUE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        _FROM = _FROM * _SIZE;

        TransportClient client = new ESClient().getConnection();

        SearchResponse searchResponse = client.prepareSearch(_INDEX)
                .setTypes(_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("type", _TYPEVALUE))
                        .must(QueryBuilders.multiMatchQuery(_QUERYVALUE, "mark", "content")))//支持一个值同时匹配多个字段
                //.setPostFilter(QueryBuilders.rangeQuery("age").from(19).to(400))
                .setFrom(_FROM).setSize(_SIZE)
                .addSort("datetime", SortOrder.DESC)
                .setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();
        List<JSON> jsonList = new ArrayList<>();

        mapToJsonList(hits, jsonList);

        return JSON.toJSONString(jsonList);
    }


    @Override
    public String allFieldSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {

        _FROM = _FROM * _SIZE;

        TransportClient client = new ESClient().getConnection();

        SearchResponse searchResponse = client.prepareSearch(_INDEX)
                .setTypes(_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.multiMatchQuery(_QUERYVALUE, "mark", "content"))//支持一个值同时匹配多个字段
                //.setPostFilter(QueryBuilders.rangeQuery("age").from(19).to(400))
                .setFrom(_FROM).setSize(_SIZE)
                .addSort("datetime", SortOrder.DESC)
                .setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();
        List<JSON> jsonList = new ArrayList<>();

        mapToJsonList(hits, jsonList);

        return JSON.toJSONString(jsonList);
    }

    @Override
    public StringBuffer hotFieldSearch(String _INDEX, String _TYPE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = new ESClient().getConnection();

        SearchResponse searchResponse = client.prepareSearch(_INDEX)
                .setTypes(_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())//支持一个值同时匹配多个字段
                //.setPostFilter(QueryBuilders.rangeQuery("age").from(19).to(400))
                .setFrom(_FROM).setSize(_SIZE)
                .addSort("times", SortOrder.DESC)
                .setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();
        StringBuffer jsonString = new StringBuffer();
        String[] strArr = new String[_SIZE];
        int i = 0;

        for (SearchHit hit : hits) {
            strArr[i] = hit.getSourceAsMap().get("field").toString();
            i++;
        }

        jsonString.append(JSON.toJSONString(strArr));

        return jsonString;
    }

    @Override
    public String recommendSearch(String _INDEX, String _TYPE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = new ESClient().getConnection();

        SearchResponse searchResponse = client.prepareSearch(_INDEX)
                .setTypes(_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("recommend", Integer.valueOf(_QUERYVALUE.toString().trim())))
                .setFrom(_FROM).setSize(_SIZE)
                .addSort("datetime", SortOrder.DESC)
                .setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();
        List<JSON> jsonList = new ArrayList<>();

        mapToJsonList(hits, jsonList);

        return JSON.toJSONString(jsonList);
    }

    public String recommendSearch(String _INDEX, String _TYPE, Object _TYPEVALUE, Object _QUERYVALUE, int _FROM, int _SIZE) throws Exception {
        TransportClient client = new ESClient().getConnection();

        SearchResponse searchResponse = client.prepareSearch(_INDEX)
                .setTypes(_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("type", _TYPEVALUE))
                        .must(QueryBuilders.matchQuery("recommend", Integer.valueOf(_QUERYVALUE.toString().trim()))))
                .setFrom(_FROM).setSize(_SIZE)
                .addSort("datetime", SortOrder.DESC)
                .setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();
        List<JSON> jsonList = new ArrayList<>();

        mapToJsonList(hits, jsonList);

        return JSON.toJSONString(jsonList);
    }

    private void mapToJsonList(SearchHits hits, List<JSON> jsonList) {
        for (SearchHit hit : hits) {
            Map<String, String> map = new HashMap<>();
            map.put("type", hit.getSourceAsMap().get("type").toString());
            map.put("gid", hit.getSourceAsMap().get("gid").toString());
            map.put("data", hit.getSourceAsMap().get("data").toString());
            jsonList.add((JSON) JSON.toJSON(map));
        }
    }

    private WildcardQueryBuilder getWildcardQueryBuilder(String _QUERYKEY, String _QUERYVALUE) {

        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(_QUERYKEY, "*" + _QUERYVALUE.trim() + "*");
        return wildcardQueryBuilder;

    }

    private MultiMatchQueryBuilder getMultiMatchQueryBuilder(String _QUERYKEY, String _QUERYVALUE) throws IOException {

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(_QUERYKEY, IKSplit.ikSplit(_QUERYVALUE.trim()));
        return multiMatchQueryBuilder;

    }

    private MatchQueryBuilder getMatchQueryBuilder(String _QUERYKEY, String _QUERYVALUE) {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(_QUERYKEY, _QUERYVALUE.trim());
        return matchQueryBuilder;

    }


}
