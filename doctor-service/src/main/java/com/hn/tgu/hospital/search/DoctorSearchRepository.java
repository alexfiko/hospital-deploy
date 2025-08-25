package com.hn.tgu.hospital.search;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorSearchRepository {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    private static final String INDEX_NAME = "doctores";

    public DoctorIndex save(DoctorIndex doctor) {
        try {
            IndexRequest request = new IndexRequest(INDEX_NAME)
                .id(doctor.getId())
                .source(doctor.toJson(), XContentType.JSON);
            
            IndexResponse response = elasticsearchClient.index(request, RequestOptions.DEFAULT);
            return doctor;
        } catch (IOException e) {
            throw new RuntimeException("Error guardando doctor en Elasticsearch", e);
        }
    }

    public List<DoctorIndex> saveAll(List<DoctorIndex> doctors) {
        List<DoctorIndex> savedDoctors = new ArrayList<>();
        for (DoctorIndex doctor : doctors) {
            savedDoctors.add(save(doctor));
        }
        return savedDoctors;
    }

    public Optional<DoctorIndex> findById(String id) {
        try {
            GetRequest request = new GetRequest(INDEX_NAME, id);
            GetResponse response = elasticsearchClient.get(request, RequestOptions.DEFAULT);
            
            if (response.isExists()) {
                return Optional.of(DoctorIndex.fromJson(response.getSourceAsString()));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error buscando doctor por ID", e);
        }
    }

    public List<DoctorIndex> findAll() {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            request.source(sourceBuilder);
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            return doctors;
        } catch (IOException e) {
            throw new RuntimeException("Error buscando todos los doctores", e);
        }
    }

    public long count() {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            sourceBuilder.size(0); // Solo contar, no obtener documentos
            request.source(sourceBuilder);
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            return response.getHits().getTotalHits().value;
        } catch (IOException e) {
            throw new RuntimeException("Error contando doctores", e);
        }
    }

    public List<DoctorIndex> findByNameStartingWithIgnoreCase(String name) {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.wildcardQuery("name", name.toLowerCase() + "*"));
            request.source(sourceBuilder);
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            return doctors;
        } catch (IOException e) {
            throw new RuntimeException("Error buscando doctores por nombre", e);
        }
    }

    public List<DoctorIndex> findBySpecialty(String specialty) {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("specialty", specialty));
            request.source(sourceBuilder);
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            return doctors;
        } catch (IOException e) {
            throw new RuntimeException("Error buscando doctores por especialidad", e);
        }
    }

    public List<DoctorIndex> findByHospital(String hospital) {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("hospital", hospital));
            request.source(sourceBuilder);
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            return doctors;
        } catch (IOException e) {
            throw new RuntimeException("Error buscando doctores por hospital", e);
        }
    }

    public List<DoctorIndex> findByAvailable(boolean available) {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.termQuery("available", available));
            request.source(sourceBuilder);
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            return doctors;
        } catch (IOException e) {
            throw new RuntimeException("Error buscando doctores por disponibilidad", e);
        }
    }

    public List<DoctorIndex> findByTagsIn(List<String> tags) {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            
            // Usar termsQuery para buscar en arrays de tags
            sourceBuilder.query(QueryBuilders.termsQuery("tags", tags));
            
            // Configurar ordenamiento por rating
            sourceBuilder.sort("rating", SortOrder.DESC);
            sourceBuilder.size(100);
            
            request.source(sourceBuilder);
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            
            return doctors;
        } catch (IOException e) {
            throw new RuntimeException("Error buscando doctores por tags", e);
        }
    }

    /**
     * Búsqueda avanzada con múltiples filtros usando Elasticsearch 7.10
     * Implementa mejores prácticas según la documentación oficial
     */
    public List<DoctorIndex> searchAdvanced(String query, String specialty, String hospital, 
                                           Integer minExperience, Integer maxExperience, 
                                           Double minRating, Double maxRating, 
                                           Boolean available, List<String> tags) {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            
            // Construir query compuesta usando bool query
            var boolQuery = QueryBuilders.boolQuery();
            
            // Query de texto libre con dis_max para mejor relevancia
            if (query != null && !query.trim().isEmpty()) {
                var disMaxQuery = QueryBuilders.disMaxQuery()
                    .add(QueryBuilders.matchQuery("name", query).boost(3.0f))           // Nombre es más importante
                    .add(QueryBuilders.matchQuery("specialty", query).boost(2.5f))      // Especialidad muy importante
                    .add(QueryBuilders.matchQuery("description", query).boost(1.5f))    // Descripción importante
                    .add(QueryBuilders.matchQuery("searchText", query).boost(1.0f))     // Texto de búsqueda normal
                    .tieBreaker(0.3f);                                                  // Factor de desempate
                
                boolQuery.must(disMaxQuery);
            }
            
            // Filtros específicos usando filter context (no afectan score)
            if (specialty != null && !specialty.trim().isEmpty()) {
                boolQuery.filter(QueryBuilders.termQuery("specialty", specialty));
            }
            
            if (hospital != null && !hospital.trim().isEmpty()) {
                boolQuery.filter(QueryBuilders.termQuery("hospital", hospital));
            }
            
            // Filtro de disponibilidad
            if (available != null) {
                if (available) {
                    boolQuery.filter(QueryBuilders.termQuery("available", true));
                } else {
                    // Para disponibilidad false, usar must_not para ser más específico
                    boolQuery.mustNot(QueryBuilders.termQuery("available", false));
                }
            }
            
            // Filtros de rango numérico
            if (minExperience != null || maxExperience != null) {
                var rangeQuery = QueryBuilders.rangeQuery("experienceYears");
                if (minExperience != null) rangeQuery.gte(minExperience);
                if (maxExperience != null) rangeQuery.lte(maxExperience);
                boolQuery.filter(rangeQuery);
            }
            
            if (minRating != null || maxRating != null) {
                var rangeQuery = QueryBuilders.rangeQuery("rating");
                if (minRating != null) rangeQuery.gte(minRating);
                if (maxRating != null) rangeQuery.lte(maxRating);
                boolQuery.filter(rangeQuery);
            }
            
            // Filtros de tags usando terms query
            if (tags != null && !tags.isEmpty()) {
                boolQuery.filter(QueryBuilders.termsQuery("tags", tags));
            }
            
            // Configurar la query principal
            sourceBuilder.query(boolQuery);
            
            // Configurar paginación y límites
            sourceBuilder.from(0).size(100); // Máximo 100 resultados
            
            // Configurar ordenamiento: primero por relevancia, luego por rating
            sourceBuilder.sort("_score", SortOrder.DESC);  // Ordenar por score de relevancia
            sourceBuilder.sort("rating", SortOrder.DESC);  // Luego por rating (descendente)
            
            request.source(sourceBuilder);
            
            System.out.println("🔍 [Elasticsearch 7.10] Query construida: " + boolQuery.toString());
            System.out.println("🔍 [Elasticsearch 7.10] Ordenamiento: Score DESC, Rating DESC");
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            
            System.out.println("🔍 [Elasticsearch 7.10] Resultados encontrados: " + doctors.size() + " de " + response.getHits().getTotalHits().value);
            
            return doctors;
            
        } catch (IOException e) {
            System.err.println("❌ Error en búsqueda avanzada de Elasticsearch: " + e.getMessage());
            throw new RuntimeException("Error en búsqueda avanzada de Elasticsearch", e);
        }
    }

    /**
     * Búsqueda con boosting personalizado para mejor relevancia
     * Implementa func_score query según documentación oficial
     */
    public List<DoctorIndex> searchWithBoosting(String query, String specialty, String hospital) {
        try {
            SearchRequest request = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            
            // Query principal con boosting
            var boolQuery = QueryBuilders.boolQuery();
            
            if (query != null && !query.trim().isEmpty()) {
                // Multi-match con boosting por campo
                var multiMatch = QueryBuilders.multiMatchQuery(query)
                    .field("name", 3.0f)        // Nombre es 3x más importante
                    .field("specialty", 2.5f)   // Especialidad es 2.5x más importante
                    .field("description", 1.5f) // Descripción es 1.5x más importante
                    .tieBreaker(0.3f);
                
                boolQuery.must(multiMatch);
            }
            
            // Filtros
            if (specialty != null && !specialty.trim().isEmpty()) {
                boolQuery.filter(QueryBuilders.matchQuery("specialty", specialty));
            }
            
            if (hospital != null && !hospital.trim().isEmpty()) {
                boolQuery.filter(QueryBuilders.matchQuery("hospital", hospital));
            }
            
            sourceBuilder.query(boolQuery);
            sourceBuilder.sort("_score", SortOrder.DESC);
            sourceBuilder.size(50); // Menos resultados para mejor relevancia
            
            request.source(sourceBuilder);
            
            System.out.println("🔍 [Elasticsearch 7.10] Búsqueda con boosting: " + boolQuery.toString());
            
            SearchResponse response = elasticsearchClient.search(request, RequestOptions.DEFAULT);
            
            List<DoctorIndex> doctors = new ArrayList<>();
            for (SearchHit hit : response.getHits().getHits()) {
                doctors.add(DoctorIndex.fromJson(hit.getSourceAsString()));
            }
            
            return doctors;
            
        } catch (IOException e) {
            System.err.println("❌ Error en búsqueda con boosting: " + e.getMessage());
            throw new RuntimeException("Error en búsqueda con boosting", e);
        }
    }
}