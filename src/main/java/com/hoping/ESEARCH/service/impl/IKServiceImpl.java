package com.hoping.ESEARCH.service.impl;

import com.hoping.ESEARCH.service.IKService;
import com.hoping.ESEARCH.utils.elasticsearch.ESClient;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YKL on 2018/6/20.
 * @version 1.0
 * @Description:
 * @spark: 梦想开始的地方
 */
@Service(value = "ikServiceImpl")
public class IKServiceImpl implements IKService {
    @Override
    public List<String> ikAnalyze(String _INDEX, Object _QUERYVALUE) throws Exception {
        TransportClient client = ESClient.getConnection();
        try {

            AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(client,

                    AnalyzeAction.INSTANCE, _INDEX, _QUERYVALUE.toString());

            ikRequest.setAnalyzer("ik_max_word");

            List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();

            List<String> searchTermList = new ArrayList<>();

            ikTokenList.forEach(ikToken -> searchTermList.add(ikToken.getTerm()));

            return searchTermList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
