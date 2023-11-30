/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author oonam
 */
public class ReadFile implements iReadAndWriteToFile {

    private String dataToWrite;
    private String currentData;
    private String hourlyData;
    private String dailyData;

    public void setDataToWrite(String data) {
        this.dataToWrite = data;
    }

    //miksi palauttaa boolean. käytetäänkö me sitä missään?
    @Override
    public boolean readFromFile(String folderName) throws IOException {
        Path folderPath = Paths.get(folderName);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
            for (Path file : stream) {
                String fileContent = Files.readString(file);
                if (file.getFileName().toString().equals("currentWeatherData")) {
                    currentData = fileContent;
                } else if (file.getFileName().toString().equals("hourlyWeatherData")) {
                    hourlyData = fileContent;
                } else {
                    dailyData = fileContent;
                }
            }
            return true;
        }
    }
    
    public WeatherData getWeather() {
        JsonObject jsonObject = JsonParser.parseString(currentData).getAsJsonObject();
        
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
        int weatherCode = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsInt();

        WeatherData weatherData = new WeatherData(currentTemp, tempMin, tempMax, feelsLike, clouds, humidity, cityName, wind, description, weatherCode);
        
        return weatherData;
    }
    
    public LocalDateTime convertTimestampToDate(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    
    public void getHourlyWeather() {
        
    }
    
    public void getDailyWeather() {
        JsonObject jsonObject = JsonParser.parseString(dailyData).getAsJsonObject();
        //DailyWeatherData(int date, double maxTemp, double minTemp, String description)
    }

    @Override
    public boolean writeToFile(String fileName) throws IOException {
        if (dataToWrite == null) {
            throw new IllegalStateException("No data set to write.");
        }

        // Specify the folder name
        String folderName = "weatherData";

        // Combine the folder name and file name to get the full path
        Path filePath = Paths.get(folderName, fileName);

        try {
            Files.createDirectories(filePath.getParent());  // Ensure the folder exists
            Files.writeString(filePath, dataToWrite, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

}
