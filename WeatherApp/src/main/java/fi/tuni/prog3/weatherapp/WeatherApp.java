package fi.tuni.prog3.weatherapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private WeatherData weather;
    private Label locationLabel, tempLabel, feelsLikeLabel, feelLikeLabel, rainLabel, windLabel, humLabel;

    @Override
    public void start(Stage stage) throws IOException {
        // get the weather
        weatherApiImpl  weatherApi = new  weatherApiImpl();
        weatherApi.lookUpLocation("Rovaniemi");  
        ReadFile file = new ReadFile();
        file.readFromFile("weatherData");
        weather = file.getWeather();
        
        // Create the main sections
        HBox menuSection = createMenuSection();
        GridPane currentSection = createCurrentSection();
        HBox hourlySection = createHourlySection();
        HBox dailySection = createDailySection();

        // Create a VBox to hold the sections
        VBox root = new VBox(10); // 10 is the spacing between sections
        root.setPadding(new Insets(10));
        root.getChildren().addAll(menuSection, currentSection, hourlySection, dailySection);

        // Create the scene
        Scene scene = new Scene(root, 600, 750);

        // Set the stage properties
        stage.setTitle("OONAN JA REETAN WeatherApp");
        stage.setScene(scene);
        stage.show();
     
    }
      
    private HBox createMenuSection() {
        Button search = getSearchButton();
        Button quit = getQuitButton();
        HBox menuSection = new HBox(10);
        menuSection.setPadding(new Insets(10));
        menuSection.getChildren().addAll(search, quit);
        menuSection.setStyle("-fx-background-color: lightgray;");
        menuSection.setPrefHeight(40);

        return menuSection;
    }
    
    private GridPane createCurrentSection() {
        locationLabel = new Label();
        tempLabel = new Label();
        feelsLikeLabel = new Label();
        rainLabel = new Label();
        windLabel = new Label();
        humLabel = new Label();
 
        GridPane currentSection = new GridPane();
        currentSection.setHgap(10);
        currentSection.setVgap(10);
 
        currentSection.add(new Label("Tämän hetken sää"), 0, 0, 2, 1);
        currentSection.add(locationLabel, 0, 1);
        currentSection.add(tempLabel, 1, 2);
        currentSection.add(feelsLikeLabel, 1, 3);
        currentSection.add(rainLabel, 0, 4);
        currentSection.add(windLabel, 1, 4);
        currentSection.add(humLabel, 2, 4);
 
        currentSection.setStyle("-fx-background-color: lightblue;");
        currentSection.setPrefHeight(220);
        currentSection.setAlignment(Pos.CENTER);
 
        updateCurrentWeatherSection();
 
        return currentSection;
    }
        
        private void updateCurrentWeatherSection() {
        if (weather != null) {
            locationLabel.setText(weather.getCityName());
            tempLabel.setText(String.format("%.1f ℃", weather.getCurrentTemp()));
            feelsLikeLabel.setText(String.format("Feels like %.1f ℃", weather.getFeelsLike()));
            rainLabel.setText("rain");
            windLabel.setText(String.format("wind: %.1f", weather.getWind()));
            humLabel.setText(String.format("humidity: %d", weather.getHumidity()));
        }
    }
    
    private HBox createHourlySection() {
        // Add elements
        Label footerLabel = new Label("tunti ennuste");
        Label bottomLabel = new Label("Content for the section");
        HBox hourlySection = new HBox(10);
        hourlySection.setPadding(new Insets(10));
        hourlySection.getChildren().addAll(footerLabel, bottomLabel);

        // Set style
        hourlySection.setStyle("-fx-background-color: lightgray;");
        hourlySection.setPrefHeight(220);

        return hourlySection;
    }
    
       private HBox createDailySection() {
        // Add elements
        Label titleLabel = new Label("Lähipäivien ennuste");
        Label topLabel = new Label("Content for thesection");
        HBox dailySection = new HBox(10);
        dailySection.setPadding(new Insets(10));
        dailySection.getChildren().addAll(titleLabel, topLabel);

        // Set style
        dailySection.setStyle("-fx-background-color: lightblue;");
        dailySection.setPrefHeight(220);

        return dailySection;
    }
       
    private ImageView getIcon() {
       int weatherCode = weather.getWeatherCode();
       
       List<Integer> thunderstorm = new ArrayList<>(Arrays.asList(200, 201, 202, 210, 211, 212, 221, 230, 231, 232));
       List<Integer> drizzle = new ArrayList<>(Arrays.asList(300, 301, 302, 310, 311, 312, 313, 314, 321));
       List<Integer> rain = new ArrayList<>(Arrays.asList(500, 501, 502, 503, 504, 511, 520, 521, 522, 531));
       List<Integer> snow = new ArrayList<>(Arrays.asList(600, 601, 602, 611, 612, 613, 615, 616, 620, 621, 622));
       List<Integer> atmosphere = new ArrayList<>(Arrays.asList(701, 711, 721, 731, 741, 751, 761, 762, 771, 781));
       List<Integer> clouds = new ArrayList<>(Arrays.asList(801, 802, 803, 804));
       
       // clear sky by defalt 
       Image icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\sun.png");

       if (thunderstorm.contains(weatherCode)) {
           icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\storm.png");
       }
       else if (drizzle.contains(weatherCode)) {
           icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\drizzle.png");
       }
       else if (rain.contains(weatherCode)) {
           icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\rain.png");
       }
       else if (snow.contains(weatherCode)) {
           icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\snowy.png");
       }
       else if (atmosphere.contains(weatherCode)) {
           icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\mist.png");
       }
       else if (atmosphere.contains(weatherCode)) {
           icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\mist.png");
       }
       else if (clouds.contains(weatherCode)) {
           icon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\cloud.png");
       }
              
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(100); 
        imageView.setFitHeight(100); 
        
        return imageView;
    }
    
    private Button getQuitButton() {
        //Creating a button.
        Button quitButton = new Button("Quit");
        
        //Adding an event to the button to terminate the application.
        quitButton.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        
        return quitButton;
    }
    
    private Button getSearchButton() {
        // Create a MenuBar
        Button searchButton = new Button("Search and Favorites");
        
        searchButton.setOnAction(e -> openSearchWindow());
        
        return  searchButton;
    }
    
    private void openSearchWindow() {
        Stage searchStage = new Stage();

        // Top section: TextField and Button
        VBox topSection = new VBox(20);
        TextField cityTextField = new TextField();
        cityTextField.setPromptText("Enter city name");
        cityTextField.setMaxWidth(200); // Set the max width of the text field

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> { performSearch(cityTextField.getText());
                  searchStage.close(); }); // Close the search window  );
        topSection.getChildren().addAll(cityTextField, searchButton);
        topSection.setAlignment(Pos.CENTER); // Center align elements in the top section

        // Main layout
        VBox mainLayout = new VBox(20); // Use a larger spacing to separate the top section from the rest
        mainLayout.getChildren().add(topSection);
        // Additional elements can be added to mainLayout here

        // Style the main layout
        mainLayout.setStyle("-fx-background-color: lightblue; -fx-padding: 10;");

        Scene scene = new Scene(mainLayout, 300, 300); // Adjust window size as needed
        searchStage.setScene(scene);
        searchStage.setTitle("City Search & Favorites:");
        searchStage.show();
        
            // Move the focus away from the TextField to show the prompt text
        Platform.runLater(() -> mainLayout.requestFocus());
    }

    
private void performSearch(String cityName) {
    weatherApiImpl weatherApi = new weatherApiImpl();
    weatherApi.lookUpLocation(cityName);

    try {
        ReadFile file = new ReadFile();
        file.readFromFile("weatherData");
        weather = file.getWeather();
        Platform.runLater(this::updateCurrentWeatherSection);
    } catch (IOException e) {
    }
}

    public static void main(String[] args) {
        launch();
    }
}