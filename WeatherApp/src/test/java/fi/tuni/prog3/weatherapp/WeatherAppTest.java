package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for the non FX methods of WeatherApp class.
 */
public class WeatherAppTest {

    /**
     * Test saving and loading the last searched city.
     */
    @Test
    public void testSaveAndLoadLastSearchedCity() {
        WeatherApp weatherApp = new WeatherApp();

        // Test saving and loading the last searched city
        String cityName = "Tampere";
        weatherApp.saveLastSearchedCity(cityName);
        String loadedCity = weatherApp.loadLastSearchedCity();

        // Check if the loaded city matches the saved city
        assertEquals(cityName, loadedCity, "Saved and loaded city should match");
    }

}
