/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

public class WeatherData {
    private final double currentTemp;
    private final double tempMin;
    private final double tempMax;
    private final double feelsLike;
    private final int clouds;
    private final int humidity;
    private final String cityName;
    //private final Double rain;
    private final double wind;

    public WeatherData(double currentTemp, double tempMin, double tempMax, double feelsLike, int clouds, int humidity, String cityName, double wind) {
        this.currentTemp = currentTemp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.feelsLike = feelsLike;
        this.clouds = clouds;
        this.humidity = humidity;
        this.cityName = cityName;
        //this.rain = (rain != null) ? rain : 0.0; // Set rain to 0 if it's null
        this.wind = wind;
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
    
    public double getWind() {
        return wind;
    }
}
