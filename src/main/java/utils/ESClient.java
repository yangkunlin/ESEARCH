package utils;

import common.ESParams;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author YKL on 2018/4/16.
 * @version 1.0
 * spark：
 * 梦想开始的地方
 */
@Component
public class ESClient {

    private static volatile TransportClient client;

    public ESClient() {
    }

    public static synchronized TransportClient getConnection() throws IOException {
        PropertiesUtil prop = new PropertiesUtil("elasticsearch.properties");
        if (client == null) {
            synchronized (ESClient.class) {
                if (client == null) {
                    try {
                        Settings settings = Settings.builder()
//                    .put("client.transport.sniff", true)
                                .put("xpack.security.user", prop.getValue(ESParams.ELASTICSEARCH_XPACK))
                                .put("cluster.name", prop.getValue(ESParams._CLUSTERNAME)).build();
                        client = new PreBuiltXPackTransportClient(settings)
                                .addTransportAddress(new TransportAddress(InetAddress.getByName(prop.getValue(ESParams.HOST_01)), Integer.valueOf(prop.getValue(ESParams._PORT))))
                                .addTransportAddress(new TransportAddress(InetAddress.getByName(prop.getValue(ESParams.HOST_02)), Integer.valueOf(prop.getValue(ESParams._PORT))))
                                .addTransportAddress(new TransportAddress(InetAddress.getByName(prop.getValue(ESParams.HOST_03)), Integer.valueOf(prop.getValue(ESParams._PORT))))
                                .addTransportAddress(new TransportAddress(InetAddress.getByName(prop.getValue(ESParams.HOST_04)), Integer.valueOf(prop.getValue(ESParams._PORT))))
                                .addTransportAddress(new TransportAddress(InetAddress.getByName(prop.getValue(ESParams.HOST_05)), Integer.valueOf(prop.getValue(ESParams._PORT))));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        client.close();
                    }
                }
            }
        }
        return client;
    }

}

