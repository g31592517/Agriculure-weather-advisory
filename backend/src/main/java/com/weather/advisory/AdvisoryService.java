package com.weather.advisory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdvisoryService {
    private static final Logger log = LoggerFactory.getLogger(AdvisoryService.class);

    private final WeatherService weatherService;
    private final AdvisoryRepository advisoryRepository;

    public AdvisoryService(WeatherService weatherService, AdvisoryRepository advisoryRepository) {
        this.weatherService = weatherService;
        this.advisoryRepository = advisoryRepository;
    }

    public Advisory getOrCreateAdvisory(String county) {
        // Check if we have a recent advisory (within last 30 minutes)
        Optional<Advisory> existing = advisoryRepository.findTopByCountyOrderByCreatedAtDesc(county);
        if (existing.isPresent() &&
            existing.get().getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(30))) {
            log.info("Returning cached advisory for {}", county);
            return existing.get();
        }

        // Fetch fresh weather data
        log.info("Creating new advisory for {}", county);
        WeatherResponse weather = weatherService.fetchWeather(county);

        // Extract weather data
        double temperature = weather.getMain().getTemp();
        double humidity = weather.getMain().getHumidity();
        String condition = weather.getWeather().get(0).getMain();
        String description = weather.getWeather().get(0).getDescription();

        // Generate farming advisory based on weather conditions
        String advisoryText = generateFarmingAdvisory(temperature, humidity, condition, description);

        // Create and save advisory
        Advisory advisory = new Advisory();
        advisory.setCounty(county);
        advisory.setTemperature(temperature);
        advisory.setHumidity(humidity);
        advisory.setWeatherCondition(condition);
        advisory.setAdvisoryText(advisoryText);
        advisory.setCreatedAt(LocalDateTime.now());

        return advisoryRepository.save(advisory);
    }

    private String generateFarmingAdvisory(double temp, double humidity, String condition, String description) {
        StringBuilder advisory = new StringBuilder();

        // Temperature-based advice
        if (temp > 30) {
            advisory.append("High temperatures detected. Ensure adequate irrigation for crops. ");
            advisory.append("Consider watering early morning or late evening to minimize evaporation. ");
            advisory.append("Protect sensitive crops from heat stress. ");
        } else if (temp > 25) {
            advisory.append("Optimal temperature for most crops. ");
            advisory.append("Good conditions for planting maize, beans, and vegetables. ");
        } else if (temp > 20) {
            advisory.append("Moderate temperatures. Suitable for planting and crop maintenance. ");
        } else if (temp > 15) {
            advisory.append("Cool temperatures. Good for crops like potatoes, carrots, and leafy greens. ");
        } else {
            advisory.append("Low temperatures. Protect sensitive crops from cold. ");
            advisory.append("Consider delaying planting until temperatures rise. ");
        }

        // Humidity-based advice
        if (humidity > 80) {
            advisory.append("High humidity levels increase fungal disease risk. ");
            advisory.append("Monitor crops for signs of mildew or rot. Ensure good air circulation. ");
        } else if (humidity < 40) {
            advisory.append("Low humidity may stress plants. Increase watering frequency. ");
        } else {
            advisory.append("Humidity levels are favorable for crop growth. ");
        }

        // Weather condition-based advice
        switch (condition.toLowerCase()) {
            case "rain":
            case "drizzle":
                advisory.append("Rainfall expected. Delay irrigation and fertilizer application. ");
                advisory.append("Good time to prepare land for planting after rain. ");
                advisory.append("Ensure proper drainage to prevent waterlogging. ");
                break;
            case "thunderstorm":
                advisory.append("Thunderstorms expected. Secure farm equipment and protect livestock. ");
                advisory.append("Postpone outdoor farm activities. Check drainage systems. ");
                break;
            case "clear":
                advisory.append("Clear skies provide excellent conditions for planting and harvesting. ");
                advisory.append("Ideal time for pesticide and fertilizer application. ");
                break;
            case "clouds":
                advisory.append("Cloudy conditions reduce heat stress on crops. ");
                advisory.append("Good time for transplanting and field work. ");
                break;
            case "mist":
            case "fog":
                advisory.append("Misty conditions increase disease risk. Monitor crops closely. ");
                advisory.append("Delay pesticide application until visibility improves. ");
                break;
            default:
                advisory.append("Monitor weather conditions closely and adjust farming activities accordingly. ");
        }

        // General farming advice
        advisory.append("Always check soil moisture before irrigation. ");
        advisory.append("Inspect crops regularly for pests and diseases.");

        return advisory.toString().trim();
    }
}

