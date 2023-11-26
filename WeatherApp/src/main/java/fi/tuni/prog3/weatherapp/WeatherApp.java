package fi.tuni.prog3.weatherapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//git add 
//git stash --staged
//git pull
//git stash apply
//git add
//git commit
//git push



// koodeja pitää jaotella useampiin ryhmiin
// päiväkohtainen ennuste sectio pitää tehdä
// tuntikohtainen ennuste sectio pitää tehdä
// pitää tallentaa viimesin haettu kaupunki
// pitää asettaa rain 0 jos null
// kuvat rain, wind, humidity ?
// yksikkötestit

// Kysy kooditoriossa:
// tuntikohtaisen ennusteen hakeminen ei toimi
// kuvien tiedostopolku
// missä päiväkohtasessa ennusteessa lukee, että minkä päivän ennuste se on

public class WeatherApp extends Application {
    private WeatherData weather;
    private Label locationLabel, tempLabel, feelsLikeLabel, rainLabel, windLabel, humLabel;
    private ImageView icon;

    private Menu favoritesMenu;
    private MenuBar menuBar;
    private Text secondWindowInfoText;
    private TextField cityTextField;
    
    @Override
    public void start(Stage stage) throws IOException {
        // get the weather
        weatherApiImpl  weatherApi = new  weatherApiImpl();
        weatherApi.lookUpLocation("Joensuu");  
        ReadFile file = new ReadFile();
        file.readFromFile("weatherData");
        weather = file.getWeather();
        
        // Create the main sections
        HBox menuSection = createMenuSection();
        HBox currentSection = createCurrentSection();
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
    
    private HBox createCurrentSection() {
        locationLabel = new Label();
        locationLabel.setStyle("-fx-font: 30 Calibri;");
        tempLabel = new Label();
        tempLabel.setStyle("-fx-font: 40 Calibri;");
        feelsLikeLabel = new Label();
        rainLabel = new Label();
        windLabel = new Label();
        humLabel = new Label();
        icon = getIcon();
        
        GridPane currentInfo = new GridPane();
        currentInfo.setHgap(10);
        currentInfo.setVgap(10);
 
        // Add to column, row (column span, row span)
        currentInfo.add(locationLabel, 0, 0,3,1);
        currentInfo.add(tempLabel, 0, 1, 3,1);
        currentInfo.add(feelsLikeLabel, 0, 2,3,1);
        currentInfo.add(rainLabel, 0, 3);
        currentInfo.add(windLabel, 1, 3);
        currentInfo.add(humLabel, 2, 3);
 
        currentInfo.setStyle("-fx-background-color: lightgray;");
        currentInfo.setPrefHeight(300);
        currentInfo.setAlignment(Pos.CENTER);
        
        HBox currentSection = new HBox(30); 
        currentSection.setPadding(new Insets(10));
        currentSection.getChildren().addAll(icon, currentInfo);
        currentSection.setStyle("-fx-background-color: lightgray;");
        currentSection.setPrefHeight(300);
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
            icon.setImage(getIcon().getImage());

            System.out.println(weather.getWeatherCode());
        }
    }
    
    private HBox createHourlySection() {
        // Add elements
        Label footerLabel = new Label("tunti ennuste");
        HBox hourlySection = new HBox(10);
        hourlySection.setPadding(new Insets(10));
        hourlySection.getChildren().addAll(footerLabel);

        // Set style
        hourlySection.setStyle("-fx-background-color: lightgray;");
        hourlySection.setPrefHeight(180);

        return hourlySection;
    }
    
    private HBox createDailySection() {
        // Add elements
        Label titleLabel = new Label("Lähipäivien ennuste");
        HBox dailySection = new HBox(10);
        dailySection.setPadding(new Insets(10));
        dailySection.getChildren().addAll(titleLabel);

        // Set style
        dailySection.setStyle("-fx-background-color: lightgrey;");
        dailySection.setPrefHeight(180);

        return dailySection;
    }
       
    private ImageView getIcon() {
       int weatherCode = weather.getWeatherCode();
       
       System.out.println(weather.getWeatherCode());
       
       List<Integer> thunderstorm = new ArrayList<>(Arrays.asList(200, 201, 202, 210, 211, 212, 221, 230, 231, 232));
       List<Integer> drizzle = new ArrayList<>(Arrays.asList(300, 301, 302, 310, 311, 312, 313, 314, 321));
       List<Integer> rain = new ArrayList<>(Arrays.asList(500, 501, 502, 503, 504, 511, 520, 521, 522, 531));
       List<Integer> snow = new ArrayList<>(Arrays.asList(600, 601, 602, 611, 612, 613, 615, 616, 620, 621, 622));
       List<Integer> atmosphere = new ArrayList<>(Arrays.asList(701, 711, 721, 731, 741, 751, 761, 762, 771, 781));
       List<Integer> clouds = new ArrayList<>(Arrays.asList(801, 802, 803, 804));
       
       System.out.println(weatherCode);
       // clear sky by defalt 
       Image weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\sun.png");

       if (thunderstorm.contains(weatherCode)) {
           weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\storm.png");
       }
       else if (drizzle.contains(weatherCode)) {
           weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\drizzle.png");
       }
       else if (rain.contains(weatherCode)) {
           weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\rain.png");
       }
       else if (snow.contains(weatherCode)) {
           weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\snowy.png");
       }
       else if (atmosphere.contains(weatherCode)) {
           weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\mist.png");
       }
       else if (atmosphere.contains(weatherCode)) {
           weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\mist.png");
       }
       else if (clouds.contains(weatherCode)) {
           weatherIcon = new Image("C:\\Users\\reett\\ohj3projekti\\group3206\\WeatherApp\\icons\\cloud.png");
       }
              
        ImageView imageView = new ImageView(weatherIcon);
        imageView.setFitWidth(200); 
        imageView.setFitHeight(200); 
        
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
    
    private Stage searchStage;
    
    private void openSearchWindow() {
    if (searchStage == null) {
        searchStage = new Stage();

        // Top section: TextField and Button
        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER); // Center align the entire top section
        
        secondWindowInfoText = new Text("");
        secondWindowInfoText.setFill(Color.RED); // Set text color to red
        
        cityTextField = new TextField();
        cityTextField.setPromptText("Enter city name");
        cityTextField.setMaxWidth(200);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #FFC0CB; -fx-border-color: #8B008B; -fx-background-radius: 10;"); // Pastel Pink with dark pink border and rounded corners
        searchButton.setOnAction(e -> {
            performSearch(cityTextField.getText());
            searchStage.close();
        });

        Button addToFavoritesButton = new Button("Add to Favorites");
        addToFavoritesButton.setStyle("-fx-background-color: #FFC0CB; -fx-border-color: #8B008B; -fx-background-radius: 10;"); // Pastel Yellow with dark yellow border and rounded corners
        addToFavoritesButton.setOnAction(e -> {
            String cityName = cityTextField.getText();
            if (cityName.isEmpty()) {
                secondWindowInfoText.setText("Please type a city name.");
            } else {
                addToFavorites(cityName);
                secondWindowInfoText.setText("Favorite successfully saved!");
            }
        });
        
                // Create an HBox for buttons
        HBox buttonLayout = new HBox(10, searchButton, addToFavoritesButton);
        buttonLayout.setAlignment(Pos.CENTER); // Center align the buttons within the HBox

        
        // Initialize MenuBar and Menu for favorites
        favoritesMenu = new Menu("Favorites");
        menuBar = new MenuBar();
        menuBar.getMenus().add(favoritesMenu);
        
        // Load favorites into ComboBox
        loadFavorites();  

        // Wrap the MenuBar in an HBox for better alignment and sizing
        HBox menuBarContainer = new HBox(menuBar);
        menuBarContainer.setAlignment(Pos.CENTER); // Center the MenuBar within the HBox
        menuBar.setMaxWidth(80); // Set preferred width to make the MenuBar smaller

        topSection.getChildren().addAll(secondWindowInfoText, cityTextField, buttonLayout, menuBar);
      
        // Main layout
        VBox mainLayout = new VBox(30);
        mainLayout.getChildren().add(topSection);
        mainLayout.setStyle("-fx-background-color: lightgray; -fx-padding: 10;");

        Scene scene = new Scene(mainLayout, 300, 300);
        searchStage.setScene(scene);
        searchStage.setTitle("City Search & Favorites:");
        
         Platform.runLater(() -> mainLayout.requestFocus());
    }
    // Show the stage without re-creating the UI components
    searchStage.show();

    // Optional: If you need to reset or update any components when the window is opened, do it here.
    // For example, you might want to clear the text field or update a list.
}

    private void loadFavorites() {
        try {
            List<String> favorites = Files.readAllLines(Paths.get("favorites"));
            favoritesMenu.getItems().clear(); // Clear existing items
            for (String city : favorites) {
                MenuItem menuItem = new MenuItem(city);
                menuItem.setOnAction(e -> cityTextField.setText(city)); // Set text field on menu item selection
                favoritesMenu.getItems().add(menuItem);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
    }
    
    private void addToFavorites(String cityName) {
        try {
            Path filePath = Paths.get("favorites");
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            // Read all lines from the file and trim each line
            List<String> favorites = Files.readAllLines(filePath).stream()
                                           .map(String::trim) // Remove leading and trailing whitespace
                                           .map(String::toLowerCase) // Convert to lower case for case-insensitive comparison
                                           .collect(Collectors.toList());

            // Process the input city name similarly
            String processedCityName = cityName.trim().toLowerCase();

            if (!favorites.contains(processedCityName)) {
                Files.writeString(filePath, cityName + System.lineSeparator(), StandardOpenOption.APPEND);
                loadFavorites(); // Reload favorites to include the new city
                secondWindowInfoText.setText("Favorite successfully saved!");
            } else {
                secondWindowInfoText.setText("City is already a favorite.");
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle the exception
        }
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
