package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WeatherAppTest {

    public WeatherAppTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        // Perform any one-time setup if needed
    }

    @AfterAll
    public static void tearDownClass() {
        // Perform any one-time cleanup if needed
    }

    @BeforeEach
    public void setUp() {
        // Perform setup before each test if needed
    }

    @AfterEach
    public void tearDown() {
        // Perform cleanup after each test if needed
    }

    @Test
    public void testWeatherAppFunctionality() {
        WeatherApp weatherApp = new WeatherApp();
    }
}
