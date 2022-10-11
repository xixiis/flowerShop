package com.wen.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 连接客户端
 */
@Configuration(proxyBeanMethods = false)
public class ElasticSearchClientConfig {

    /**
     * 连接客户端
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("121.196.109.178", 9200, "http")
        ));
        return client;
    }
}
