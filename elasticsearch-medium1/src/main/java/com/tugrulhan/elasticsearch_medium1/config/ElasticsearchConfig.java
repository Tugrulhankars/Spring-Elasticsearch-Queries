package com.tugrulhan.elasticsearch_medium1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.tugrulhan.elasticsearch_medium1.repository")
@ComponentScan(basePackages = "com.tugrulhan.elasticsearch_medium1")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private String elasticUrl = "localhost:9200";
    private String username = "elastic";
    private String password = "changeme";

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticUrl)
                .withBasicAuth(username, password)
                .build();
    }
}
