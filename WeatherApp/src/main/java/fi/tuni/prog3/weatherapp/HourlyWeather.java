/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

/**
 *
 * @author reett
 */
public class HourlyWeather {
    private final String time;
    private final String temp;
    private final int weatherCode;

    public HourlyWeather(String time, String temp, int weatherCode) {
        this.time = time;
        this.temp = temp;
        this.weatherCode = weatherCode;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public int getWeatherCode() {
        return weatherCode;
    }
    
    
    
}
