package fi.tuni.prog3.weatherapp;

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
