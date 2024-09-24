package com.example.weatherapp.controller;

import com.example.weatherapp.model.WeatherData;
//import com.example.weatherapp.model.ForecastResponse; // Assuming you create this class
import com.example.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public WeatherData getCurrentWeather(@RequestParam String city, @RequestParam String country) {
        return weatherService.getCurrentWeather(city, country);
    }

    @GetMapping("/forecast/daily")
    public WeatherData getWeatherForecast(@RequestParam String city, @RequestParam String country) {
        System.out.println("City: " + city + ", Country: " + country); // Log incoming parameters
        return weatherService.getWeatherForecast(city, country);
    }
}