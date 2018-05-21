package service;

import common.ESClient;
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
public class IndexServiceImpl implements IndexService {

//    private static Logger logger = LogManager.getRootLogger();

    @Override
    public boolean addWithoutID(String _INDEX, String _TYPE, Map<String, Object> _FIELD) throws Exception {

        TransportClient client = new ESClient().getConnection();

        try {
            IndexResponse indexResponse = client.prepareIndex().setIndex(_INDEX)
                    .setType(_TYPE)
                    .setId(UUID.randomUUID().toString())
                    .setSource(_FIELD)
                    .execute()
                    .actionGet();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

//        client.close();
    }

    @Override
    public boolean addWithID(String _INDEX, String _TYPE, Map<String, Object> _FIELD, String _ID) throws Exception {

        try (TransportClient client = new ESClient().getConnection()) {

            IndexResponse indexResponse = client.prepareIndex().setIndex(_INDEX)
                    .setType(_TYPE)
                    .setSource(_FIELD)
                    .setId(_ID)
                    .execute()
                    .actionGet();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


//        client.close();

    }

    @Override
    public boolean bulkAddWithoutID(String _INDEX, String _TYPE, List<Map<String, Object>> _FIELDS) throws Exception {
        TransportClient client = new ESClient().getConnection();
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        try {
           for (Map<String, Object> _FIELD : _FIELDS) {
               bulkRequest.add(client.prepareIndex(_INDEX, _TYPE).setSource(_FIELD));
           }
           bulkRequest.execute().actionGet();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public String getWithID(String _INDEX, String _TYPE, String _ID) throws Exception {

        GetResponse getResponse;
        try (TransportClient client = new ESClient().getConnection()) {

            getResponse = client.prepareGet().setIndex(_INDEX)
                    .setType(_TYPE)
                    .setId(_ID)
                    .execute()
                    .actionGet();
        }

        return getResponse.getSourceAsString();

    }

    @Override
    public boolean delWithID(String _INDEX, String _TYPE, String _ID) throws Exception {

        try {
            TransportClient client = new ESClient().getConnection();

            DeleteResponse delResponse =
                    client.prepareDelete(_INDEX, _TYPE, _ID)
                            //.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)
                            .execute()
                            .actionGet();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateWithID(String _INDEX, String _TYPE, Map<String, Object> _BODY, String _ID) throws Exception {

        try {
            TransportClient client = new ESClient().getConnection();

            UpdateResponse updateResponse =
                    client.prepareUpdate().setIndex(_INDEX)
                            .setType(_TYPE)
                            .setDoc(_BODY)
                            .setId(_ID)
                            .execute()
                            .actionGet();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
