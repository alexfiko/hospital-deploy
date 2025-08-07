package com.hn.tgu.hospital.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ElasticsearchClientConfig {

    @Value("${spring.data.elasticsearch.uris}")
    private String elasticsearchUris;

    @Value("${spring.data.elasticsearch.username}")
    private String username;

    @Value("${spring.data.elasticsearch.password}")
    private String password;

    @Bean
    @Primary
    public RestHighLevelClient elasticsearchClient() throws URISyntaxException {
        System.out.println("🔧 Configurando RestHighLevelClient para Bonsai...");
        System.out.println("URI: " + elasticsearchUris);
        System.out.println("Username: " + username);

        // Parsear la URI
        URI uri = new URI(elasticsearchUris);

        // Configurar credenciales
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
            AuthScope.ANY,
            new UsernamePasswordCredentials(username, password)
        );

        // Crear el RestHighLevelClient directamente
        RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme())
            )
            .setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                return httpClientBuilder;
            })
        );

        System.out.println("✅ RestHighLevelClient configurado exitosamente para Bonsai");
        return client;
    }
}
