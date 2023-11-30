package fi.tuni.prog3.weatherapp;

public class DailyWeather {
    private final String date;
    private final String temp;
    private final String description;
    private final int weatherCode;

    public DailyWeather(String date, String temp, String description, int weatherCode) {
        this.date = date;
        this.temp = temp;
        this.description = description;
        this.weatherCode = weatherCode;
    }
    
    public String getDate() {
        return date;
    }

    public String getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    } 
    
    public int getWeatherCode() {
        return weatherCode;
    }
}
