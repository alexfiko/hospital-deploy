package com.hn.tgu.hospital.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.hn.tgu.hospital.elasticsearch")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Value("${spring.data.elasticsearch.uris:http://localhost:9200}")
    private String elasticsearchUri;

    @Value("${spring.data.elasticsearch.username:}")
    private String username;

    @Value("${spring.data.elasticsearch.password:}")
    private String password;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        try {
            logger.info("🔍 Configurando cliente Elasticsearch...");
            logger.info("📡 URI: {}", elasticsearchUri);
            logger.info("👤 Username: {}", username != null ? "***" : "null");
            logger.info("🔑 Password: {}", password != null ? "***" : "null");
            
            // Extraer solo el host y puerto de la URI
            String cleanUri = elasticsearchUri;
            if (cleanUri.contains("@")) {
                // Si tiene credenciales en la URI, extraer solo la parte del host
                cleanUri = cleanUri.substring(cleanUri.indexOf("@") + 1);
                logger.info("🧹 URI limpia (sin credenciales): {}", cleanUri);
            }
            
            // Remover protocolo
            cleanUri = cleanUri.replace("http://", "").replace("https://", "");
            logger.info("🌐 URI sin protocolo: {}", cleanUri);
            
            // Separar host y puerto
            String host;
            int port;
            if (cleanUri.contains(":")) {
                String[] parts = cleanUri.split(":");
                host = parts[0];
                port = Integer.parseInt(parts[1]);
                logger.info("🏠 Host: {}, Puerto: {}", host, port);
            } else {
                host = cleanUri;
                port = 9200; // Puerto por defecto
                logger.info("🏠 Host: {}, Puerto por defecto: {}", host, port);
            }
            
            ClientConfiguration.MaybeSecureClientConfigurationBuilder builder = ClientConfiguration.builder()
                    .connectedTo(host + ":" + port);
            
            // Configurar autenticación usando las propiedades separadas
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                builder.withBasicAuth(username, password);
                logger.info("🔐 Autenticación básica configurada");
            } else {
                logger.warn("⚠️ No se configuraron credenciales para Elasticsearch");
            }
            
            // Configurar SSL si es HTTPS
            if (elasticsearchUri.startsWith("https://")) {
                builder.usingSsl();
                logger.info("🔒 SSL habilitado para conexión HTTPS");
            } else {
                logger.info("🔓 Conexión HTTP sin SSL");
            }
            
            ClientConfiguration clientConfiguration = builder.build();
            logger.info("⚙️ Configuración del cliente completada");
            
            RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
            logger.info("✅ Cliente Elasticsearch creado exitosamente");
            
            return client;
            
        } catch (Exception e) {
            logger.error("❌ Error configurando cliente Elasticsearch: {}", e.getMessage(), e);
            throw new RuntimeException("Error configurando cliente Elasticsearch: " + e.getMessage(), e);
        }
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        logger.info("🔧 Creando template Elasticsearch...");
        ElasticsearchOperations template = new ElasticsearchRestTemplate(elasticsearchClient());
        logger.info("✅ Template Elasticsearch creado exitosamente");
        return template;
    }
}
