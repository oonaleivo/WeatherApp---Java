/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author oonam
 */
public class ReadFile implements iReadAndWriteToFile {

    private String dataToWrite;
    private String currentJsonData;
    private String hourlyJsonData;
    private String dailyJsonData;

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
                    currentJsonData = fileContent;
                } else if (file.getFileName().toString().equals("hourlyWeatherData")) {
                    hourlyJsonData = fileContent;
                } else {
                    dailyJsonData = fileContent;
                }
            }
            return true;
        }
    }
    
    public CurrentWeather getCurrentWeather() {
        JsonObject jsonObject = JsonParser.parseString(currentJsonData).getAsJsonObject();
        
        // Extracting required data
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

        CurrentWeather weatherData = new CurrentWeather(currentTemp, tempMin, tempMax, feelsLike, clouds, humidity, cityName, wind, description, weatherCode);
        
        return weatherData;
    }
    
    public LocalDateTime convertTimestampToDate(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    
    public ArrayList<HourlyWeather> getHourlyWeather() {
        ArrayList<HourlyWeather> hourlyWeatherList = new ArrayList<>();
        
        JsonObject jsonObject = JsonParser.parseString(hourlyJsonData).getAsJsonObject();
        JsonArray hourlyList = jsonObject.getAsJsonArray("list"); // list of hours
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        
        // Create HourlyWeather object and save it to hourly WeatherList for each hour
        for (JsonElement hourlyElement : hourlyList) {
            JsonObject hourlyObject = hourlyElement.getAsJsonObject();

            long timestamp = hourlyObject.getAsJsonPrimitive("dt").getAsLong();
            LocalDateTime time = convertTimestampToDate(timestamp);
            String formattedTime = time.format(formatter);

            double tempAsDouble = hourlyObject.getAsJsonObject("main").get("temp").getAsDouble();
            String temp = String.format("%.1f ℃", tempAsDouble);
            int weatherCode = hourlyObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsInt();

            HourlyWeather hourlyWeather = new HourlyWeather(formattedTime, temp, weatherCode);
            hourlyWeatherList.add(hourlyWeather);
        }   
        return hourlyWeatherList;
    }
    
    public ArrayList<DailyWeather> getDailyWeather() {
        ArrayList<DailyWeather> dailyWeatherList = new ArrayList<>();
        
        JsonObject jsonObject = JsonParser.parseString(dailyJsonData).getAsJsonObject();
        JsonArray dailyList = jsonObject.getAsJsonArray("list"); // list of days
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.");

        // Create DailyWeather object and save to dailyWeatherList for each day
        for (JsonElement dailyElement : dailyList) {
            JsonObject dailyObject = dailyElement.getAsJsonObject();

            long timestamp = dailyObject.getAsJsonPrimitive("dt").getAsLong();
            LocalDateTime date = convertTimestampToDate(timestamp);
            String formattedDate = date.format(formatter);

            JsonObject tempObject = dailyObject.getAsJsonObject("temp");
            double maxTemp = tempObject.getAsJsonPrimitive("max").getAsDouble();
            double minTemp = tempObject.getAsJsonPrimitive("min").getAsDouble();
            String formattedTemps = String.format("%.1f℃ ... %.1f℃", minTemp, maxTemp);
            
            JsonObject weatherObject = dailyObject.getAsJsonArray("weather").get(0).getAsJsonObject();
            String description = weatherObject.get("description").getAsString();
            int weatherCode = weatherObject.get("id").getAsInt();

            DailyWeather dailyWeatherData = new DailyWeather(formattedDate, formattedTemps, description, weatherCode);
            dailyWeatherList.add(dailyWeatherData);
        }
        return dailyWeatherList;  
    }

    //tarkistetaanko me boolean palautusta missään? voisko olla vaan void
    @Override
    public boolean writeToFile(String fileName) throws IOException {
        if (dataToWrite == null) {
            throw new IllegalStateException("No data set to write.");
        }

        String folderName = "weatherData";
        Path filePath = Paths.get(folderName, fileName);

        try {
            Files.createDirectories(filePath.getParent());  // Make sure the folder exists
            Files.writeString(filePath, dataToWrite, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

}
