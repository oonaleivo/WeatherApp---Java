/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

public class CurrentWeather {
    private final double currentTemp;
    private final double tempMin;
    private final double tempMax;
    private final double feelsLike;
    private final int clouds;
    private final int humidity;
    private final String cityName;
    private final double rain;
    private final double wind;
    private final String description;
    private final int weatherCode;

    public CurrentWeather(double currentTemp, double tempMin, double tempMax, double feelsLike, int clouds, int humidity, String cityName, double rain, double wind, String description, int weatherCode) {
        this.currentTemp = currentTemp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.feelsLike = feelsLike;
        this.clouds = clouds;
        this.humidity = humidity;
        this.cityName = cityName;
        this.rain = rain;
        this.wind = wind;
        this.description = description;
        this.weatherCode = weatherCode;
    }

    // Getters for each field
    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public int getClouds() {
        return clouds;
    }

    public int getHumidity() {
        return humidity;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public double getRain() {
        return rain;
    }
    
    public double getWind() {
        return wind;
    }
    
    public String getdescription() {
        return description;
    }
    
    public int getWeatherCode() {
        return weatherCode;
    }
}
