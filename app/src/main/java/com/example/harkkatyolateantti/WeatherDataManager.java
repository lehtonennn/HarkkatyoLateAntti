package com.example.harkkatyolateantti;

import android.util.Log;

public class WeatherDataManager {
    private static WeatherDataManager instance;

    private WeatherData weatherData;

    private WeatherDataManager() {
    }

    public static synchronized WeatherDataManager getInstance() {
        if (instance == null) {
            instance = new WeatherDataManager();
        }
        return instance;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }
}
