
package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link CurrentWeather} class.
 */
public class CurrentWeatherTest {

    /**
     * Test the {@link CurrentWeather#getCurrentTemp()} method.
     */
    @Test
    public void testGetCurrentTemp() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(25.5, currentWeather.getCurrentTemp(), 0.01); // Use delta for double comparison
    }
    /**
     * Test the {@link CurrentWeather#getTempMin()} method.
     */
    @Test
    public void testGetTempMin() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(20.2, currentWeather.getTempMin(), 0.01); // Use delta for double comparison
    }
    /**
     * Test the {@link CurrentWeather#getTempMax()} method.
     */
    @Test
    public void testGetTempMax() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(30.0, currentWeather.getTempMax(), 0.01); // Use delta for double comparison
    }
    /**
     * Test the {@link CurrentWeather#getFeelsLike()} method.
     */
    @Test
    public void testGetFeelsLike() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(26.0, currentWeather.getFeelsLike(), 0.01); // Use delta for double comparison
    }
    /**
     * Test the {@link CurrentWeather#getClouds()} method.
     */
    @Test
    public void testGetClouds() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(40, currentWeather.getClouds());
    }
    /**
     * Test the {@link CurrentWeather#getHumidity()} method.
     */
    @Test
    public void testGetHumidity() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(60, currentWeather.getHumidity());
    }
    /**
     * Test the {@link CurrentWeather#getCityName()} method.
     */
    @Test
    public void testGetCityName() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals("CITY", currentWeather.getCityName());
    }
    /**
     * Test the {@link CurrentWeather#getRain()} method.
     */
    @Test
    public void testGetRain() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(0.5, currentWeather.getRain(), 0.01); // Use delta for double comparison
    }
    /**
     * Test the {@link CurrentWeather#getWind()} method.
     */
    @Test
    public void testGetWind() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(5.0, currentWeather.getWind(), 0.01); // Use delta for double comparison
    }
    /**
     * Test the {@link CurrentWeather#getDescription()} method.
     */
    @Test
    public void testGetDescription() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals("Clear sky", currentWeather.getDescription());
    }
    /**
     * Test the {@link CurrentWeather#getWeatherCode()} method.
     */
    @Test
    public void testGetWeatherCode() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals(800, currentWeather.getWeatherCode());
    }
    /**
     * Test the {@link CurrentWeather#getSunset()} method.
     */
    @Test
    public void testGetSunset() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals("18:30", currentWeather.getSunset());
    }
    /**
     * Test the {@link CurrentWeather#getSunrise()} method.
     */
    @Test
    public void testGetSunrise() {
        CurrentWeather currentWeather = new CurrentWeather(25.5, 20.2, 30.0, 26.0, 40, 60, "City", 0.5, 5.0, "Clear sky", 800, "18:30", "06:00");
        assertEquals("06:00", currentWeather.getSunrise());
    }

}
