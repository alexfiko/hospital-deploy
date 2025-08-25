package com.hn.tgu.hospital.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;
import java.util.Map;

@Document(indexName = "doctores")
@Setting(settingPath = "elasticsearch-settings.json")
public class DoctorElasticsearch {
    
    @Id
    private String id;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;
    
    @Field(type = FieldType.Keyword)
    private String specialty;
    
    @Field(type = FieldType.Text)
    private String img;
    
    @Field(type = FieldType.Integer)
    private int experienceYears;
    
    @Field(type = FieldType.Double)
    private double rating;
    
    // Campo hospital con múltiples tipos para búsqueda avanzada
    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String hospital;
    
    @Field(type = FieldType.Keyword)
    private String hospitalKeyword;
    
    @Field(type = FieldType.Boolean)
    private boolean available;
    
    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;
    
    @Field(type = FieldType.Keyword)
    private List<String> tags;
    
    @Field(type = FieldType.Keyword)
    private List<String> diasLaborales;
    
    @Field(type = FieldType.Keyword)
    private String horarioEntrada;
    
    @Field(type = FieldType.Keyword)
    private String horarioSalida;
    
    @Field(type = FieldType.Integer)
    private int duracionCita;
    
    @Field(type = FieldType.Object)
    private Map<String, List<String>> horariosDisponibles;
    
    // Campo adicional para búsqueda de texto completo
    @Field(type = FieldType.Text, analyzer = "standard")
    private String searchText;
    
    // Campo para facets de experiencia
    @Field(type = FieldType.Keyword)
    private String experienceLevel;
    
    // Constructores
    public DoctorElasticsearch() {}
    
    public DoctorElasticsearch(String id, String name, String specialty, String img, int experienceYears, 
                              double rating, String hospital, boolean available, String description, 
                              List<String> tags, List<String> diasLaborales, String horarioEntrada, 
                              String horarioSalida, int duracionCita, Map<String, List<String>> horariosDisponibles) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.img = img;
        this.experienceYears = experienceYears;
        this.rating = rating;
        this.hospital = hospital;
        this.hospitalKeyword = hospital; // Para facets exactos
        this.available = available;
        this.description = description;
        this.tags = tags;
        this.diasLaborales = diasLaborales;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.duracionCita = duracionCita;
        this.horariosDisponibles = horariosDisponibles;
        this.searchText = name + " " + specialty + " " + description + " " + hospital;
        this.experienceLevel = calculateExperienceLevel(experienceYears);
    }
    
    // Método para calcular el nivel de experiencia
    private String calculateExperienceLevel(int years) {
        if (years <= 2) return "Principiante";
        else if (years <= 5) return "Intermedio";
        else if (years <= 10) return "Experto";
        else return "Senior";
    }
    
    // Método para actualizar searchText
    private void updateSearchText() {
        this.searchText = name + " " + specialty + " " + description + " " + hospital;
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
        updateSearchText();
    }
    
    public String getSpecialty() {
        return specialty;
    }
    
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
        updateSearchText();
    }
    
    public String getImg() {
        return img;
    }
    
    public void setImg(String img) {
        this.img = img;
    }
    
    public int getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
        this.experienceLevel = calculateExperienceLevel(experienceYears);
    }
    
    public double getRating() {
        return rating;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public String getHospital() {
        return hospital;
    }
    
    public void setHospital(String hospital) {
        this.hospital = hospital;
        this.hospitalKeyword = hospital;
        updateSearchText();
    }
    
    public String getHospitalKeyword() {
        return hospitalKeyword;
    }
    
    public void setHospitalKeyword(String hospitalKeyword) {
        this.hospitalKeyword = hospitalKeyword;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        updateSearchText();
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
    
    public String getHorarioEntrada() {
        return horarioEntrada;
    }
    
    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }
    
    public String getHorarioSalida() {
        return horarioSalida;
    }
    
    public void setHorarioSalida(String horarioSalida) {
        this.horarioSalida = horarioSalida;
    }
    
    public int getDuracionCita() {
        return duracionCita;
    }
    
    public void setDuracionCita(int duracionCita) {
        this.duracionCita = duracionCita;
    }
    
    public Map<String, List<String>> getHorariosDisponibles() {
        return horariosDisponibles;
    }
    
    public void setHorariosDisponibles(Map<String, List<String>> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }
    
    public String getSearchText() {
        return searchText;
    }
    
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    
    public String getExperienceLevel() {
        return experienceLevel;
    }
    
    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    
    // Método builder para crear instancias fácilmente
    public static DoctorElasticsearchBuilder builder() {
        return new DoctorElasticsearchBuilder();
    }
    
    // Clase builder interna
    public static class DoctorElasticsearchBuilder {
        private String id;
        private String name;
        private String specialty;
        private String img;
        private int experienceYears;
        private double rating;
        private String hospital;
        private boolean available;
        private String description;
        private List<String> tags;
        private List<String> diasLaborales;
        private String horarioEntrada;
        private String horarioSalida;
        private int duracionCita;
        private Map<String, List<String>> horariosDisponibles;
        
        public DoctorElasticsearchBuilder id(String id) {
            this.id = id;
            return this;
        }
        
        public DoctorElasticsearchBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public DoctorElasticsearchBuilder specialty(String specialty) {
            this.specialty = specialty;
            return this;
        }
        
        public DoctorElasticsearchBuilder img(String img) {
            this.img = img;
            return this;
        }
        
        public DoctorElasticsearchBuilder experienceYears(int experienceYears) {
            this.experienceYears = experienceYears;
            return this;
        }
        
        public DoctorElasticsearchBuilder rating(double rating) {
            this.rating = rating;
            return this;
        }
        
        public DoctorElasticsearchBuilder hospital(String hospital) {
            this.hospital = hospital;
            return this;
        }
        
        public DoctorElasticsearchBuilder available(boolean available) {
            this.available = available;
            return this;
        }
        
        public DoctorElasticsearchBuilder description(String description) {
            this.description = description;
            return this;
        }
        
        public DoctorElasticsearchBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }
        
        public DoctorElasticsearchBuilder diasLaborales(List<String> diasLaborales) {
            this.diasLaborales = diasLaborales;
            return this;
        }
        
        public DoctorElasticsearchBuilder horarioEntrada(String horarioEntrada) {
            this.horarioEntrada = horarioEntrada;
            return this;
        }
        
        public DoctorElasticsearchBuilder horarioSalida(String horarioSalida) {
            this.horarioSalida = horarioSalida;
            return this;
        }
        
        public DoctorElasticsearchBuilder duracionCita(int duracionCita) {
            this.duracionCita = duracionCita;
            return this;
        }
        
        public DoctorElasticsearchBuilder horariosDisponibles(Map<String, List<String>> horariosDisponibles) {
            this.horariosDisponibles = horariosDisponibles;
            return this;
        }
        
        public DoctorElasticsearch build() {
            return new DoctorElasticsearch(id, name, specialty, img, experienceYears, 
                                          rating, hospital, available, description, 
                                          tags, diasLaborales, horarioEntrada, 
                                          horarioSalida, duracionCita, horariosDisponibles);
        }
    }
}
