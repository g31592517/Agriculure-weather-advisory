package com.weather.advisory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather-api", url = "${weather.api.url}")
public interface WeatherApiClient {

    @GetMapping("/weather")
    WeatherResponse getWeather(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude,
            @RequestParam("appid") String apiKey,
            @RequestParam("units") String units
    );
}

