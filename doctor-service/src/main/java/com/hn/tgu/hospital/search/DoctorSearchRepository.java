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
import org.elasticsearch.xcontent.XContentType;
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
            sourceBuilder.query(QueryBuilders.matchQuery("tags", String.join(" ", tags)));
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
}