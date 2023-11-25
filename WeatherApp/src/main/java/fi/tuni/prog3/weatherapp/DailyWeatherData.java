package fi.tuni.prog3.weatherapp;

public class DailyWeatherData {
    private final int date;
    private final double maxTemp;
    private final double minTemp;
    private final String description;

    public DailyWeatherData(int date, double maxTemp, double minTemp, String description) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.description = description;
    }
    
    public int getDate() {
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
}
