package com.example.weatherapp;

import org.springframework.boot.SpringApplication;

public class TestWeatherappApplication {

	public static void main(String[] args) {
		SpringApplication.from(WeatherappApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
