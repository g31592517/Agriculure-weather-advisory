package com.weather.advisory;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "advisories")
public class Advisory {
    @Id
    private String id;
    private String county;
    private String weatherCondition;
    private Double temperature;
    private Double humidity;
    private String advisoryText;
    private LocalDateTime createdAt;

    public Advisory() {}

    public Advisory(String id, String county, String weatherCondition, Double temperature,
                   Double humidity, String advisoryText, LocalDateTime createdAt) {
        this.id = id;
        this.county = county;
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
        this.humidity = humidity;
        this.advisoryText = advisoryText;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    public String getWeatherCondition() { return weatherCondition; }
    public void setWeatherCondition(String weatherCondition) { this.weatherCondition = weatherCondition; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public String getAdvisoryText() { return advisoryText; }
    public void setAdvisoryText(String advisoryText) { this.advisoryText = advisoryText; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

