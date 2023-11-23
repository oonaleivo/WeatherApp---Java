package fi.tuni.prog3.weatherapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author reett
 */
public class weatherApiImpl implements iAPI {

    @Override
    public String lookUpLocation(String loc) {
        String city = "Lohja";
        String apiKey = "87fa00cc8d6158c3f7fe9efb4cb467cb";
        
        // Build the API URL
        String apiUrl = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s&limit=%d&appid=%s",
                city, "FI", 1, apiKey);

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
                double latitude = jsonArray.get(0).getAsJsonObject().get("lat").getAsDouble();
                double longitude = jsonArray.get(0).getAsJsonObject().get("lon").getAsDouble();

                System.out.printf("Latitude: %f%n", latitude);
                System.out.printf("Longitude: %f%n", longitude);

            } else {
                System.out.println("Failed to get location information. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("Error making the API call: " + e.getMessage());
        }
        return "";
    }

    @Override
    public String getCurrentWeather(double lat, double lon) {
        // Implement the method
        return "Current weather at " + lat + ", " + lon;
    }

    @Override
    public String getForecast(double lat, double lon) {
        // Implement the method
        return "Weather forecast for " + lat + ", " + lon;
    }
}
