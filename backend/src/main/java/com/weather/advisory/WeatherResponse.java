package com.weather.advisory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Main main;
    private List<Weather> weather;

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private Double temp;
        private Double humidity;

        public Double getTemp() { return temp; }
        public void setTemp(Double temp) { this.temp = temp; }

        public Double getHumidity() { return humidity; }
        public void setHumidity(Double humidity) { this.humidity = humidity; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main;
        private String description;

        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}

