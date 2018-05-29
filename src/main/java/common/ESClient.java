package common;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author YKL on 2018/4/16.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@Component
public class ESClient {

    //private  EsClient client = new EsClient();
    private static TransportClient client = null;

    public ESClient() {
//        System.out.print("--------------------------------");
        try {
            Settings settings = Settings.builder()
//                    .put("client.transport.sniff", true)
                    .put("xpack.security.user", ESParams.ELASTICSEARCH_XPACK)
                    .put("cluster.name", ESParams._CLUSTERNAME).build();
            client = new PreBuiltXPackTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ESParams.HOST_01), ESParams._PORT));
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ESParams.HOST_02), ESParams._PORT));
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ESParams.HOST_03), ESParams._PORT));
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ESParams.HOST_04), ESParams._PORT));
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ESParams.HOST_05), ESParams._PORT));
//            System.out.print(client.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            client.close();
        }
    }

    public TransportClient getConnection() {

        if (client == null) {
            synchronized (ESClient.class) {
                if (client == null) {
                    new ESClient();
                }
            }
        }
        return client;
    }

}

