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

    private String dataToWrite; // Data to be written to the file

    public void setDataToWrite(String data) {
        this.dataToWrite = data;
    }

    @Override
    public boolean readFromFile(String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        String jsonData = Files.readString(filePath);

        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        
        // Extracting required data
        double currentTemp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        double tempMin = jsonObject.getAsJsonObject("main").get("temp_min").getAsDouble();
        double tempMax = jsonObject.getAsJsonObject("main").get("temp_max").getAsDouble();
        double feelsLike = jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble();
        int clouds = jsonObject.getAsJsonObject("clouds").get("all").getAsInt();
        int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();

        // Returning true
        WeatherData weatherData = new WeatherData(currentTemp, tempMin, tempMax, feelsLike, clouds, humidity);
        
        return true;
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
