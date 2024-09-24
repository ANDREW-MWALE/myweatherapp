package com.example.weatherapp.service;

import com.example.weatherapp.model.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    private final String API_KEY = "917590e2a4ed41609e1e445a9085d158";  // API key
    private final String BASE_URL = "https://api.weatherbit.io/v2.0/current";
    private final String FORECAST_URL = "https://api.weatherbit.io/v2.0/forecast/daily";

    public WeatherData getCurrentWeather(String city, String country) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?city=" + city + "&country=" + country + "&key=" + API_KEY;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        WeatherData data = new WeatherData();
        data.setWeatherFromResponse(response);
        return data;
    }

    public WeatherData getWeatherForecast(String city, String country) {
        RestTemplate restTemplate = new RestTemplate();
        String url = FORECAST_URL + "?city=" + city + "&country=" + country + "&key=" + API_KEY;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        WeatherData data = new WeatherData();
        data.setForecastFromResponse(response); // Ensure this method correctly extracts forecast data

        // Optionally, if you want to set the city and country in the forecast response
        data.setCityName(city);
        data.setCountryCode(country);

        return data;
    }

}
