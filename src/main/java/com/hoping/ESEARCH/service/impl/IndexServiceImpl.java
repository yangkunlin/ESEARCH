package com.hoping.ESEARCH.service.impl;

import com.hoping.ESEARCH.service.IndexService;
import org.springframework.stereotype.Service;
import com.hoping.ESEARCH.utils.elasticsearch.ESClient;
import com.hoping.ESEARCH.common.ESParams;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author YKL on 2018/4/16.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@Service(value = "indexServiceImpl")
public class IndexServiceImpl implements IndexService {

//    private static Logger logger = LogManager.getRootLogger();

    @Override
    public boolean addWithoutID(String _INDEX, String _TYPE, Map<String, Object> _FIELD) throws Exception {

        TransportClient client = ESClient.getConnection();

        try {
            IndexResponse indexResponse = client.prepareIndex().setIndex(_INDEX)
                    .setType(_TYPE)
                    .setId(UUID.randomUUID().toString())
                    .setSource(_FIELD)
                    .execute()
                    .actionGet();
            return true;
        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return false;
        }

//        client.close();
    }

    @Override
    public boolean addWithID(String _INDEX, String _TYPE, Map<String, Object> _FIELD, String _ID) throws Exception {

        TransportClient client = ESClient.getConnection();
        try {

            IndexResponse indexResponse = client.prepareIndex().setIndex(_INDEX)
                    .setType(_TYPE)
                    .setSource(_FIELD)
                    .setId(_ID)
                    .execute()
                    .actionGet();
            return true;
        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return false;
        }


//        client.close();

    }

    @Override
    public boolean bulkAddWithoutID(String _INDEX, String _TYPE, List<Map<String, Object>> _FIELDS) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {
            BulkRequestBuilder bulkRequest = client.prepareBulk();

            for (Map<String, Object> _FIELD : _FIELDS) {
               bulkRequest.add(client.prepareIndex(_INDEX, _TYPE).setSource(_FIELD));
           }
           bulkRequest.execute().actionGet();
            return true;
        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean bulkAddWithID(String _INDEX, String _TYPE, List<Map<String, Object>> _FIELDS) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {
            BulkRequestBuilder bulkRequest = client.prepareBulk();

            for (Map<String, Object> _FIELD : _FIELDS) {
                bulkRequest.add(client.prepareIndex(_INDEX, _TYPE).setSource(_FIELD).setId(_FIELD.get(ESParams.ELASTICSEARCH_ID).toString()));
            }
            bulkRequest.execute().actionGet();
            return true;
        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean bulkDelWithID(String _INDEX, String _TYPE, List<Map<String, Object>> _FIELDS) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {
            BulkRequestBuilder bulkRequest = client.prepareBulk();

            for (Map<String, Object> _FIELD : _FIELDS) {
                bulkRequest.add(client.prepareDelete(_INDEX, _TYPE, _FIELD.get(ESParams.ELASTICSEARCH_ID).toString()));
            }
            bulkRequest.execute().actionGet();
            return true;
        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public String getWithID(String _INDEX, String _TYPE, String _ID) throws Exception {

        TransportClient client = ESClient.getConnection();
        try {
            GetResponse  getResponse = client.prepareGet().setIndex(_INDEX)
                    .setType(_TYPE)
                    .setId(_ID)
                    .execute()
                    .actionGet();
            return getResponse.getSourceAsString();

        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return null;
        }


    }

    @Override
    public boolean delWithID(String _INDEX, String _TYPE, String _ID) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {

            DeleteResponse delResponse =
                    client.prepareDelete(_INDEX, _TYPE, _ID)
                            //.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)
                            .execute()
                            .actionGet();

            return true;
        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateWithID(String _INDEX, String _TYPE, Map<String, Object> _BODY, String _ID) throws Exception {
        TransportClient client = ESClient.getConnection();

        try {

            UpdateResponse updateResponse =
                    client.prepareUpdate().setIndex(_INDEX)
                            .setType(_TYPE)
                            .setDoc(_BODY)
                            .setId(_ID)
                            .execute()
                            .actionGet();
            return true;
        } catch (Exception ex) {
            if (client != null) {
                client = null;
            }
            ex.printStackTrace();
            return false;
        }
    }
}
