package fi.tuni.prog3.weatherapp;

import java.time.LocalDateTime;

public class DailyWeatherData {
    private final String date;
    private final double maxTemp;
    private final double minTemp;
    private final String description;
    private final int weatherCode;

    public DailyWeatherData(String date, double maxTemp, double minTemp, String description, int weatherCode) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.description = description;
        this.weatherCode = weatherCode;
    }
    
    public String getDate() {
        return date;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public String getDescription() {
        return description;
    } 
    
    public int getWeatherCode() {
        return weatherCode;
    }
}
