package com.weather.advisory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherApiClient weatherApiClient;
    private final CountyCoordinates countyCoordinates;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(WeatherApiClient weatherApiClient, CountyCoordinates countyCoordinates) {
        this.weatherApiClient = weatherApiClient;
        this.countyCoordinates = countyCoordinates;
    }

    public WeatherResponse fetchWeather(String county) {
        double[] coords = countyCoordinates.getCoordinates(county);
        if (coords == null) {
            throw new IllegalArgumentException("County not found: " + county);
        }

        log.info("Fetching weather for {} at coordinates: [{}, {}]", county, coords[0], coords[1]);

        try {
            return weatherApiClient.getWeather(coords[0], coords[1], apiKey, "metric");
        } catch (Exception e) {
            log.error("Failed to fetch weather for {}: {}", county, e.getMessage());
            throw new RuntimeException("Failed to fetch weather data", e);
        }
    }
}

