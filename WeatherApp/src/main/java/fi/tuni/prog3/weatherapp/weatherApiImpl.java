package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author reett
 */
public class weatherApiImpl implements iAPI {
    private String apiKey = "87fa00cc8d6158c3f7fe9efb4cb467cb";

    @Override
    public String lookUpLocation(String loc) {
        String coordinates = "";
        double latitude = 0.0;
        double longitude = 0.0;
        
        // Build the API URL
        String apiUrl = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=%d&appid=%s",
                loc, "FI", 1, apiKey);

        // Create an HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an HttpRequest
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();
        
        try {
            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful (status code 200)
            if (response.statusCode() == 200) {
                // Parse the JSON response using Gson
                JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

                // Extract latitude and longitude directly
                latitude = jsonArray.get(0).getAsJsonObject().get("lat").getAsDouble();
                longitude = jsonArray.get(0).getAsJsonObject().get("lon").getAsDouble();
                
                coordinates = String.format("lat %f, lon %f", latitude, longitude);

                // poista testitulosteet jossain vaiheessa
                System.out.printf("Latitude: %f%n", latitude);
                System.out.printf("Longitude: %f%n", longitude);

            } else {
                System.out.println("Failed to get location information. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("Error making the API call: " + e.getMessage());
        }
        getCurrentWeather(latitude, longitude);
        return coordinates;
    }

    @Override
    public String getCurrentWeather(double lat, double lon) {
        String jsonData = "";
        // Build the API URL
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s",
                lat, lon, apiKey);

        // Create an HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an HttpRequest
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();
        
        try {
            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful (status code 200)
            if (response.statusCode() == 200) {
                jsonData = response.body();
                
                //lisää tähän fileen tallennus
                System.out.println(jsonData);
                ReadFile file = new ReadFile();
                file.setDataToWrite(jsonData);
                file.writeToFile("weatherData");

            } else {
                System.out.println("Failed to get weather information. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error making the API call: " + e.getMessage());
        }
        return jsonData;
    }

    @Override
    public String getForecast(double lat, double lon) {
        // Implement the method
        return "Weather forecast for " + lat + ", " + lon;
    }
}
