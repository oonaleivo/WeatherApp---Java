package fi.tuni.prog3.weatherapp;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//git add 
//git stash --staged
//git pull
//git stash apply
//git add
//git commit
//git push

/**
 * JavaFX WeatherApp
 */
public class WeatherApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        weatherApiImpl  weatherApi = new  weatherApiImpl();
        weatherApi.lookUpLocation("Rovaniemi");
        
        ReadFile file = new ReadFile();
        file.readFromFile("weatherData");
        WeatherData weather = file.getWeather();
        
        GridPane grid = new GridPane();

        // Luodaan skene, johon ruudukko sijoitetaan
        Scene scene = new Scene(grid, 350, 275);
        stage.setScene(scene);
        
        String currentTemp = String.valueOf(weather.getCurrentTemp());
        Label currentWeather = new Label(currentTemp);
        grid.add(currentWeather, 2, 2);
        
        stage.show();
        
     
    }

    public static void main(String[] args) {
        launch();
    }
    
    private Button getQuitButton() {
        //Creating a button.
        Button button = new Button("Quit");
        
        //Adding an event to the button to terminate the application.
        button.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        
        return button;
    }
    
    
}