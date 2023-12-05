package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HourlyWeatherTest {

    @Test
    public void testGetTime() {
        HourlyWeather hourlyWeather = new HourlyWeather("12:00 PM", "25°C", 800);
        assertEquals("12:00 PM", hourlyWeather.getTime());
    }

    @Test
    public void testGetTemp() {
        HourlyWeather hourlyWeather = new HourlyWeather("12:00 PM", "25°C", 800);
        assertEquals("25°C", hourlyWeather.getTemp());
    }

    @Test
    public void testGetWeatherCode() {
        HourlyWeather hourlyWeather = new HourlyWeather("12:00 PM", "25°C", 800);
        assertEquals(800, hourlyWeather.getWeatherCode());
    }

}
