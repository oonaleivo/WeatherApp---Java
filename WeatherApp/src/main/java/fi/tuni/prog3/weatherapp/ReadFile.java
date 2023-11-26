/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author oonam
 */
public class ReadFile implements iReadAndWriteToFile {

    private String dataToWrite;
    private String readData;

    public void setDataToWrite(String data) {
        this.dataToWrite = data;
    }

    @Override
    public boolean readFromFile(String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        readData = Files.readString(filePath);
        
        return true;
        
        //muuten palauta false
    }
    
    public WeatherData getWeather() {
        JsonObject jsonObject = JsonParser.parseString(readData).getAsJsonObject();
        
        // Extracting required data, minus 273.15 to convert kelvin to celcius
        double currentTemp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        double tempMin = jsonObject.getAsJsonObject("main").get("temp_min").getAsDouble();
        double tempMax = jsonObject.getAsJsonObject("main").get("temp_max").getAsDouble();
        double feelsLike = jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble();
        int clouds = jsonObject.getAsJsonObject("clouds").get("all").getAsInt();
        int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
        String cityName = jsonObject.get("name").getAsString();
        //double rain = jsonObject.getAsJsonObject("rain").get("1h").getAsDouble();
        double wind = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
        String description = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

        WeatherData weatherData = new WeatherData(currentTemp, tempMin, tempMax, feelsLike, clouds, humidity, cityName, wind, description);
        
        return weatherData;
    }
    
    public void getHourlyWeather() {
        
    }
    
    public void getDailyWeather() {
        //DailyWeatherData(int date, double maxTemp, double minTemp, String description)
    }

    @Override
    public boolean writeToFile(String fileName) throws IOException {
        if (dataToWrite == null) {
            throw new IllegalStateException("No data set to write.");
        }
        Path filePath = Path.of(fileName);
        Files.writeString(filePath, dataToWrite, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return true;
    }
}
