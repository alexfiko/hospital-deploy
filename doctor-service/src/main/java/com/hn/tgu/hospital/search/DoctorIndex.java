package com.hn.tgu.hospital.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Map;

@Document(indexName = "doctores")
public class DoctorIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;

    @Field(type = FieldType.Keyword)
    private String specialty;

    @Field(type = FieldType.Keyword)
    private String hospital;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    @Field(type = FieldType.Integer)
    private int experienceYears;

    @Field(type = FieldType.Double)
    private double rating;

    @Field(type = FieldType.Boolean)
    private boolean available;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @Field(type = FieldType.Keyword)
    private List<String> diasLaborales;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String searchText;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Constructores
    public DoctorIndex() {}

    public DoctorIndex(String id, String name, String specialty, String hospital, String description, 
                      int experienceYears, double rating, boolean available) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.hospital = hospital;
        this.description = description;
        this.experienceYears = experienceYears;
        this.rating = rating;
        this.available = available;
        this.searchText = name + " " + specialty + " " + description;
    }

    public DoctorIndex(String id, String name, String specialty, String hospital, String description, 
                      int experienceYears, boolean available) {
        this(id, name, specialty, hospital, description, experienceYears, 4.5, available);
    }

    // Métodos de serialización JSON
    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando DoctorIndex a JSON", e);
        }
    }

    public static DoctorIndex fromJson(String json) {
        try {
            return objectMapper.readValue(json, DoctorIndex.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializando JSON a DoctorIndex", e);
        }
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(List<String> diasLaborales) {
        this.diasLaborales = diasLaborales;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
