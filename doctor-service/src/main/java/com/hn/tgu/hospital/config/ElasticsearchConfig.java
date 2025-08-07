package com.hn.tgu.hospital.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig implements CommandLineRunner {

    @Autowired(required = false)
    private RestHighLevelClient elasticsearchClient;

    @Override
    public void run(String... args) throws Exception {
        // Este método se ejecuta después de que todos los beans estén inicializados
        if (elasticsearchClient != null) {
            System.out.println("🔍 Elasticsearch configurado correctamente");
        } else {
            System.err.println("❌ Elasticsearch no disponible - verificar configuración");
        }
    }
}
