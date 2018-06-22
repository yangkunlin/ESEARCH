package com.hoping.ESEARCH.utils.elasticsearch;

import com.floragunn.searchguard.ssl.SearchGuardSSLPlugin;
import com.hoping.ESEARCH.common.ESParams;
import com.hoping.ESEARCH.utils.PropertiesUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
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
//                                .put("xpack.security.user", prop.getValue(ESParams.ELASTICSEARCH_XPACK))
//                                .put("path.home", ".")
//                                .put("path.conf", "E:\\ESEARCH\\src\\main\\resources")
                                .put("cluster.name", prop.getValue(ESParams._CLUSTERNAME))
                                .put("searchguard.ssl.transport.enabled", true)
//                                .put("searchguard.ssl.transport.keystore_filepath", "E:\\ESEARCH\\src\\main\\resources\\admin-keystore.jks")
//                                .put("searchguard.ssl.transport.truststore_filepath", "E:\\ESEARCH\\src\\main\\resources\\truststore.jks")
                                .put("searchguard.ssl.transport.keystore_filepath", "/usr/local/elasticsearch-6.0.0/plugins/search-guard-6/sgconfig/admin-keystore.jks")
                                .put("searchguard.ssl.transport.truststore_filepath", "/usr/local/elasticsearch-6.0.0/plugins/search-guard-6/sgconfig/truststore.jks")
//                                .put("searchguard.ssl.http.keystore_password", "password")
//                                .put("searchguard.ssl.http.truststore_password", "password")
                                .put("searchguard.ssl.transport.keystore_password", "ks18xy@E0")
                                .put("searchguard.ssl.transport.truststore_password", "ca18xy@Et")
                                .put("searchguard.ssl.transport.enforce_hostname_verification", false)
                                .build();
                        client = new PreBuiltTransportClient(settings, SearchGuardSSLPlugin.class)
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

