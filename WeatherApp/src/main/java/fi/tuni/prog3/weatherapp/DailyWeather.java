package fi.tuni.prog3.weatherapp;

public class DailyWeather {
    private final String date;
    private final double maxTemp;
    private final double minTemp;
    private final String description;
    private final int weatherCode;

    public DailyWeather(String date, double maxTemp, double minTemp, String description, int weatherCode) {
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
