package com.example.harkkatyolateantti;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData{
    private String name;
    private String main;
    private String description;
    private String temperature;
    private String windSpeed;

    public WeatherData(String n, String m, String d, String t, String w) {
        name = n;
        main = m;
        description = d;
        temperature = t;
        windSpeed = w;
    }


    public String getName() {
        return name;
    }


    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }


    public String getTemperature() {
        return temperature;
    }


    public String getWindSpeed() {
        return windSpeed;
    }

    public int getTemperatureInCelsius() {
        double temperatureKelvin = Double.parseDouble(temperature);
        double temperatureCelsius = temperatureKelvin - 273.15;
        return (int) Math.round(temperatureCelsius);
    }


}
