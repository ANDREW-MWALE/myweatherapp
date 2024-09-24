package com.example.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class WeatherData {
    private String cityName; // Name of the city
    private String countryCode; // Country code
    private float temp; // Current temperature
    private float appTemp; // Feels like temperature
    private String weatherDescription; // Description of the current weather
    private List<DailyForecast> forecast = new ArrayList<>(); // List for 16-day forecast

    // Set weather data from API response
    public void setWeatherFromResponse(Map<String, Object> response) {
        Map<String, Object> weatherData = getFirstData(response); // Get the first weather data entry
        if (weatherData != null) {
            this.cityName = (String) weatherData.get("city_name");
            this.countryCode = (String) weatherData.get("country_code");
            this.temp = extractFloat(weatherData.get("temp"));
            this.appTemp = extractFloat(weatherData.get("app_temp"));
            this.weatherDescription = extractDescription(weatherData); // Extract weather description
        }
    }

    // Set the forecast data from API response
    public void setForecastFromResponse(Map<String, Object> response) {
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.get("data");
        if (dataList != null) {
            for (Map<String, Object> dayData : dataList) {
                DailyForecast forecastEntry = createDailyForecast(dayData); // Create a DailyForecast object
                this.forecast.add(forecastEntry); // Add to the forecast list
            }
        }
    }

    // Helper method to get the first data entry from the response
    private Map<String, Object> getFirstData(Map<String, Object> response) {
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.get("data");
        return (dataList != null && !dataList.isEmpty()) ? dataList.get(0) : null; // Return the first entry or null
    }

    // Extract the weather description from the response
    private String extractDescription(Map<String, Object> weatherData) {
        Map<String, String> weather = (Map<String, String>) weatherData.get("weather");
        return (weather != null) ? weather.get("description") : "No description"; // Default if not available
    }

    // Create a DailyForecast object from the day's data
    private DailyForecast createDailyForecast(Map<String, Object> dayData) {
        DailyForecast forecast = new DailyForecast();
        forecast.setDate((String) dayData.get("datetime")); // Set the date
        forecast.setMaxTemp(extractFloat(dayData.get("max_temp"))); // Set max temperature
        forecast.setMinTemp(extractFloat(dayData.get("min_temp"))); // Set min temperature
        forecast.setWeatherDescription(extractDescription(dayData)); // Set weather description
        forecast.setPrecipitation(extractFloat(dayData.get("precip"))); // Set precipitation
        forecast.setUvIndex(extractFloat(dayData.get("uv"))); // Set UV index
        forecast.setWindDirection((String) dayData.get("wind_cdir_full")); // Set wind direction
        forecast.setWindSpeed(extractFloat(dayData.get("wind_spd"))); // Set wind speed
        return forecast; // Return the created DailyForecast object
    }

    // Helper method to safely extract a float from an object
    private float extractFloat(Object obj) {
        return (obj instanceof Number) ? ((Number) obj).floatValue() : 0.0f; // Return 0.0f if not a number
    }

    @Getter
    @Setter
    public static class DailyForecast {
        private String date; // Date of the forecast
        private float maxTemp; // Maximum temperature
        private float minTemp; // Minimum temperature
        private String weatherDescription; // Weather description
        private float precipitation; // Precipitation amount
        private float uvIndex; // UV index
        private String windDirection; // Wind direction
        private float windSpeed; // Wind speed
    }
}
