package fi.tuni.prog3.weatherapp;

import java.io.File;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main WeatherApp class that extends JavaFX Application.
 */
//git add 
//git stash --staged
//git pull
//git stash apply
//git add
//git commit
//git push

/* Kooditorio muutokset:

Image weatherIcon = new Image(new File("icons/sun.png").toURI().toString());

Lue kaikki tiedostot jostain kansiosta: (ei tarvis yks kerrallaan)
https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java

https://openweathermap.org/forecast16 dt on timestamp päiville, voi muuttaa localdate + localdatetime 
luokilla saa muutettua päiviksi. */
// päiväkohtainen ennusteen päivittäminen, nyt ei päivitä jos hakee uutta kaupunkia - tehty!
// tuntikohtainen ennusteen päivittäminen, nyt ei päivitä jos hakee uutta kaupunkia - tehty!
// current temp meni pieneksi, en pysty muuttamaan fonttikokoa??
// pitää asettaa rain 0 jos null
// rain wind humidity pitää lisätä yksiköt (ja kuvat)
// onko selkeempi jos päiväennusteessa lukee min ja max omilla riveillä eikä ...
// lopuksi tehään nätin näköseksi + nyt ei kauheesti "selkeää omaleimaisuutta annettuun pohjaan verrattuna"
// yksikkötestit

public class WeatherApp extends Application {

    private Stage searchStage;
    private CurrentWeather currentWeather;
    private ArrayList<DailyWeather> dailyWeatherList;
    private ArrayList<HourlyWeather> hourlyWeatherList;
    private Label locationLabel, tempLabel, feelsLikeLabel, rainLabel, windLabel, humLabel;
    private ImageView icon;
    private HBox hourlySection = new HBox(10);
    private HBox dailySection = new HBox(10);
    private Menu favoritesMenu;
    private MenuBar menuBar;
    private Text secondWindowInfoText;
    private TextField cityTextField;

    @Override
    public void start(Stage stage) throws IOException {
        // get the weather
        String lastSearchedCity = loadLastSearchedCity();
        if (lastSearchedCity == null || lastSearchedCity.isEmpty()) {
            lastSearchedCity = "Tampere"; // Replace with a default city name
        }
        weatherApiImpl weatherApi = new weatherApiImpl();
        weatherApi.lookUpLocation(lastSearchedCity);
        ReadFile file = new ReadFile();
        file.readFromFile("weatherData");
        currentWeather = file.getCurrentWeather();
        dailyWeatherList = file.getDailyWeather();
        hourlyWeatherList = file.getHourlyWeather();

        // Create the main sections
        HBox menuSection = createMenuSection();
        HBox currentSection = createCurrentSection();
        HBox hourlySection = createHourlySection();
        HBox dailySection = createDailySection();

        // Create a horizontal scrollbar that is always shown for hourlySection
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hourlySection);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create a VBox to hold the sections
        VBox root = new VBox(10); // 10 is the spacing between sections
        root.setPadding(new Insets(10));
        root.getChildren().addAll(menuSection, currentSection, hourlySection, scrollPane, dailySection);

        // Create the scene
        Scene scene = new Scene(root, 600, 750);

        // Set the stage properties
        stage.setTitle("OONAN JA REETAN WeatherApp");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createMenuSection() {
        // create the top menu section
        Button search = getSearchButton();
        Button quit = getQuitButton();
        HBox menuSection = new HBox(10);
        menuSection.setPadding(new Insets(10));
        menuSection.getChildren().addAll(search, quit);
        menuSection.setStyle("-fx-background-color: lightyellow;");
        menuSection.setPrefHeight(40);

        return menuSection;
    }

    /**
     * Method to create the current weather section of the UI.
     *
     * @return HBox containing the current weather section.
     */
    private HBox createCurrentSection() {
        locationLabel = new Label();
        locationLabel.setStyle("-fx-font: 30 Calibri;");
        tempLabel = new Label();
        tempLabel.setStyle("-fx-font: 50 Calibri;");
        feelsLikeLabel = new Label();
        rainLabel = new Label();
        windLabel = new Label();
        humLabel = new Label();
        icon = getIcon(currentWeather.getWeatherCode());

        GridPane currentInfo = new GridPane();
        currentInfo.setHgap(10);
        currentInfo.setVgap(10);

        // Add to column, row (column span, row span)
        currentInfo.add(locationLabel, 0, 0, 3, 1);
        currentInfo.add(tempLabel, 0, 1, 3, 1);
        currentInfo.add(feelsLikeLabel, 0, 2, 3, 1);
        currentInfo.add(rainLabel, 0, 3);
        currentInfo.add(windLabel, 1, 3);
        currentInfo.add(humLabel, 2, 3);

        currentInfo.setStyle("-fx-background-color: white;");
        currentInfo.setPrefHeight(300);
        currentInfo.setAlignment(Pos.CENTER);

        HBox currentSection = new HBox(30);
        currentSection.setPadding(new Insets(10));
        currentSection.getChildren().addAll(icon, currentInfo);
        currentSection.setStyle("-fx-background-color: white;");
        currentSection.setPrefHeight(300);
        currentSection.setAlignment(Pos.CENTER);

        updateCurrentWeatherSection();

        return currentSection;
    }

    /**
     * Update the content of the current weather section with new data.
     */
    private void updateCurrentWeatherSection() {
        if (currentWeather != null) {
            locationLabel.setText(currentWeather.getCityName());
            tempLabel.setText(String.format("%.1f ℃", currentWeather.getCurrentTemp()));
            feelsLikeLabel.setText(String.format("Feels like %.1f ℃", currentWeather.getFeelsLike()));
            rainLabel.setText("rain");
            windLabel.setText(String.format("wind: %.1f", currentWeather.getWind()));
            humLabel.setText(String.format("humidity: %d", currentWeather.getHumidity()));
            icon.setImage(getIcon(currentWeather.getWeatherCode()).getImage());
        }
    }

    /**
     * Method to create the hourly weather section of the UI.
     *
     * @return HBox containing the hourly weather section.
     */
    private HBox createHourlySection() {

        hourlySection.setPadding(new Insets(10));

        // Create a VBox for each hour with hourly data and add them to the HBox
        for (HourlyWeather data : hourlyWeatherList) {
            VBox hour = new VBox(10);
            Label timeLabel = new Label(data.getTime());
            timeLabel.setStyle("-fx-font: 18 Calibri;");
            ImageView hourlyIcon = getIcon(data.getWeatherCode());
            hourlyIcon.setFitWidth(40);
            hourlyIcon.setFitHeight(40);
            Label hourlyTempLabel = new Label(data.getTemp());
            hourlyTempLabel.setTooltip(new Tooltip(data.getTemp()));
            tempLabel.setStyle("-fx-font: 18 Calibri;");
            hour.getChildren().addAll(timeLabel, hourlyIcon, hourlyTempLabel);
            hour.setAlignment(Pos.CENTER);
            hourlySection.getChildren().add(hour);
        }

        // Set style
        hourlySection.setPrefWidth(50 * 24); // Calculate the width based on the content
        hourlySection.setStyle("-fx-background-color: lightyellow;");
        hourlySection.setPrefHeight(180);

        updateHourlyWeatherSection();

        return hourlySection;
    }

    /**
     * Update the content of the hourly weather section with new data.
     */
    private void updateHourlyWeatherSection() {
        // Update content with new data
        if (hourlyWeatherList != null && !hourlyWeatherList.isEmpty()) {
            int index = 0;
            for (HourlyWeather data : hourlyWeatherList) {
                // Assuming hourlySection is an HBox containing your existing hourly data
                if (index < hourlySection.getChildren().size()) {
                    VBox hour = (VBox) hourlySection.getChildren().get(index);

                    // Update existing labels and icon
                    Label timeLabel = (Label) hour.getChildren().get(0);
                    timeLabel.setText(data.getTime());

                    ImageView hourlyIcon = (ImageView) hour.getChildren().get(1);
                    hourlyIcon.setImage(getIcon(data.getWeatherCode()).getImage());

                    Label hourlyTempLabel = (Label) hour.getChildren().get(2);
                    hourlyTempLabel.setText(data.getTemp());
                    hourlyTempLabel.setTooltip(new Tooltip(data.getTemp()));

                    index++;
                } else {
                    // Handle the case where there's not enough existing VBox elements for the new data
                    break;
                }
            }
        }

        // Set style
        hourlySection.setPrefWidth(50 * 24); // Calculate the width based on the content
        hourlySection.setStyle("-fx-background-color: lightyellow;");
        hourlySection.setPrefHeight(180);
    }

    /**
     * Method to create the daily weather section of the UI.
     *
     * @return HBox containing the daily weather section.
     */
    private HBox createDailySection() {
        // Create Hbox to be the main structure
        dailySection.setPadding(new Insets(10));

        // Create a VBox for each day with daily data and add them to the HBox
        for (DailyWeather data : dailyWeatherList) {
            VBox day = new VBox(10);
            Label dateLabel = new Label(data.getDate());
            dateLabel.setStyle("-fx-font: 18 Calibri;");
            ImageView dailyIcon = getIcon(data.getWeatherCode());
            dailyIcon.setFitWidth(60);
            dailyIcon.setFitHeight(60);
            Label minMaxLabel = new Label(data.getTemp());
            minMaxLabel.setStyle("-fx-font: 14 Calibri;");
            day.getChildren().addAll(dateLabel, dailyIcon, minMaxLabel);
            day.setAlignment(Pos.CENTER);
            dailySection.getChildren().add(day);
        }

        // Set style
        dailySection.setStyle("-fx-background-color: white;");
        dailySection.setPrefHeight(180);
        dailySection.setAlignment(Pos.CENTER);

        updateDailyWeatherSection();

        return dailySection;
    }

    /**
     * Update the content of the daily weather section with new data.
     */
    private void updateDailyWeatherSection() {
        // Update content with new data
        if (dailyWeatherList != null && !dailyWeatherList.isEmpty()) {
            int index = 0;
            for (DailyWeather data : dailyWeatherList) {
                // Assuming dailySection is an HBox containing your existing daily data
                if (index < dailySection.getChildren().size()) {
                    VBox day = (VBox) dailySection.getChildren().get(index);

                    // Update existing labels and icon
                    Label dateLabel = (Label) day.getChildren().get(0);
                    dateLabel.setText(data.getDate());

                    ImageView dailyIcon = (ImageView) day.getChildren().get(1);
                    dailyIcon.setImage(getIcon(data.getWeatherCode()).getImage());

                    Label minMaxLabel = (Label) day.getChildren().get(2);
                    minMaxLabel.setText(data.getTemp());

                    index++;
                } else {
                    // Handle the case where there's not enough existing VBox elements for the new data
                    break;
                }
            }
        }

        // Set style
        dailySection.setStyle("-fx-background-color: white;");
        dailySection.setPrefHeight(180);
        dailySection.setAlignment(Pos.CENTER);
    }

    /**
     * Save the last searched city to a file.
     *
     * @param cityName The name of the city to be saved.
     */
    private void saveLastSearchedCity(String cityName) {
        try {
            Path filePath = Paths.get("lastSearchedCity.txt");
            Files.writeString(filePath, cityName);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
    }

    /**
     * Load the last searched city from a file.
     *
     * @return The last searched city.
     */
    private String loadLastSearchedCity() {
        try {
            Path filePath = Paths.get("lastSearchedCity.txt");
            if (Files.exists(filePath)) {
                return Files.readString(filePath).trim();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
        return null;
    }

    /**
     * Get the appropriate weather icon based on the weather code.
     *
     * @param weatherCode The code representing the weather conditions.
     * @return ImageView containing the weather icon.
     */
    private ImageView getIcon(int weatherCode) {
        List<Integer> lightrainthunderstorm = new ArrayList<>(Arrays.asList(200, 230, 231));
        List<Integer> heavythunderstorm = new ArrayList<>(Arrays.asList(201, 202, 211, 212, 221, 232));
        List<Integer> lightthunderstorm = new ArrayList<>(Arrays.asList(210));
        List<Integer> drizzle = new ArrayList<>(Arrays.asList(300, 301, 302, 310, 311, 313, 321));
        List<Integer> lightrain = new ArrayList<>(Arrays.asList(500));
        List<Integer> rain = new ArrayList<>(Arrays.asList(312, 314, 501, 520, 521));
        List<Integer> heavyrain = new ArrayList<>(Arrays.asList(502, 503, 504, 522, 531));
        List<Integer> heavysnow = new ArrayList<>(Arrays.asList(602, 621, 622));
        List<Integer> lightsnow = new ArrayList<>(Arrays.asList(600, 601, 620));
        List<Integer> sleet = new ArrayList<>(Arrays.asList(611, 612, 613, 615, 616));
        List<Integer> atmosphere = new ArrayList<>(Arrays.asList(711, 721, 771));
        List<Integer> clouds = new ArrayList<>(Arrays.asList(804));
        List<Integer> partlycloudy = new ArrayList<>(Arrays.asList(802, 803));
        List<Integer> mostlysunny = new ArrayList<>(Arrays.asList(801));
        List<Integer> mistandfog = new ArrayList<>(Arrays.asList(701, 741));
        List<Integer> tornado = new ArrayList<>(Arrays.asList(781));
        List<Integer> duststorm = new ArrayList<>(Arrays.asList(731, 751, 761, 762));

        // clear sky by defalt 
        Image weatherIcon = new Image(new File("icons/sun.png").toURI().toString());

        if (lightrainthunderstorm.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/storm-light-rain.png").toURI().toString());
        } else if (heavythunderstorm.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/storm-with-heavy-rain.png").toURI().toString());
        } else if (lightthunderstorm.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/storm-light.png").toURI().toString());
        } else if (drizzle.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/drizzle.png").toURI().toString());
        } else if (lightrain.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/light-rain.png").toURI().toString());
        } else if (rain.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/rain.png").toURI().toString());
        } else if (heavyrain.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/heavy-rain.png").toURI().toString());
        } else if (heavysnow.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/snowy.png").toURI().toString());
        } else if (lightsnow.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/light-snow.png").toURI().toString());
        } else if (sleet.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/sleet.png").toURI().toString());
        } else if (atmosphere.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/smoke.png").toURI().toString());
        } else if (clouds.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/cloud.png").toURI().toString());
        } else if (partlycloudy.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/partly-cloudy.png").toURI().toString());
        } else if (mostlysunny.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/mostlysunny.png").toURI().toString());
        } else if (mistandfog.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/mist.png").toURI().toString());
        } else if (tornado.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/hurricane.png").toURI().toString());
        } else if (duststorm.contains(weatherCode)) {
            weatherIcon = new Image(new File("icons/dust-storm.png").toURI().toString());
        }

        ImageView imageView = new ImageView(weatherIcon);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        return imageView;
    }

    /**
     * Get the Quit button for the UI.
     *
     * @return Button for quitting the application.
     */
    private Button getQuitButton() {
        //Creating a button.
        Button quitButton = new Button("Quit");

        //Adding an event to the button to terminate the application.
        quitButton.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });

        return quitButton;
    }

    /**
     * Get the Search button for the UI.
     *
     * @return Button for searching and accessing favorites.
     */
    private Button getSearchButton() {
        // Create a MenuBar
        Button searchButton = new Button("Search and Favorites");

        searchButton.setOnAction(e -> openSearchWindow());

        return searchButton;
    }

    /**
    * Opens the search window, allowing the user to enter a city name for weather information.
    * If the window does not exist, it creates a new stage; otherwise, it clears the text field.
    * The method handles searching for a city, adding it to favorites, and updating UI elements.
    */
    private void openSearchWindow() {
        // Check if the searchStage is already created
        if (searchStage == null) {
            searchStage = new Stage();

            // Top section: TextField and Button
            VBox topSection = new VBox(10);
            topSection.setAlignment(Pos.CENTER); // Center align the entire top section

            secondWindowInfoText = new Text("");
            secondWindowInfoText.setFill(Color.RED); // Set text color to red
            
            // Create TextField for city name input
            cityTextField = new TextField();
            cityTextField.setPromptText("Enter city name");
            cityTextField.setMaxWidth(200);

            // Create Search Button and define its behavior
            Button searchButton = new Button("Search");
            searchButton.setStyle("-fx-background-color: lightblue; -fx-background-radius: 10;"); // Pastel Pink with dark pink border and rounded corners
            searchButton.setOnAction(e -> {
                // Get city name from the TextField, perform search, and close the window
                String cityName = cityTextField.getText().trim(); // Trim to remove leading and trailing whitespace
                if (!cityName.isEmpty()) {
                    performSearch(cityName);
                    secondWindowInfoText.setText("");
                    searchStage.close();
                } else {
                    // Display a message or log that no city name was entered
                    System.out.println("No city name entered."); // Or update a UI element with this message
                    secondWindowInfoText.setText("Please type a city name.");
                }
            });

             // Create "Add to Favorites" Button and define its behavior
            Button addToFavoritesButton = new Button("Add to Favorites");
            addToFavoritesButton.setStyle("-fx-background-color: lightblue; -fx-background-radius: 10;"); // Pastel Yellow with dark yellow border and rounded corners
            addToFavoritesButton.setOnAction(e -> {
                // Get city name from the TextField and add it to favorites
                String cityName = cityTextField.getText();
                if (cityName.isEmpty()) {
                    Platform.runLater(() -> secondWindowInfoText.setText("Please type a city name."));
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

            // Load favorites
            loadFavorites();

            // Wrap the MenuBar in an HBox for better alignment and sizing
            HBox menuBarContainer = new HBox(menuBar);
            menuBarContainer.setAlignment(Pos.CENTER); // Center the MenuBar within the HBox
            menuBar.setMaxWidth(80); // Set preferred width to make the MenuBar smaller

            topSection.getChildren().addAll(secondWindowInfoText, cityTextField, buttonLayout, menuBar);

            // Main layout
            VBox mainLayout = new VBox(30);
            mainLayout.getChildren().add(topSection);
            mainLayout.setStyle("-fx-background-color: white; -fx-padding: 10;");

            Scene scene = new Scene(mainLayout, 300, 300);
            searchStage.setScene(scene);
            searchStage.setTitle("City Search & Favorites:");

            Platform.runLater(() -> mainLayout.requestFocus());
        } else {
            // Clear the text field if the window already exists
            cityTextField.setText("");
        }
        // Show the stage without re-creating the UI components
        searchStage.show();
    }
    
    /**
    * Loads favorite cities from the "favorites" file and populates the favoritesMenu.
    * Clears existing items in the menu before adding the loaded favorites.
    */
    private void loadFavorites() {
        try {
            // Read favorite cities from file and populate the favoritesMenu
            List<String> favorites = Files.readAllLines(Paths.get("favorites"));
            favoritesMenu.getItems().clear(); // Clear existing items
            for (String city : favorites) {
                MenuItem menuItem = new MenuItem(city);
                menuItem.setOnAction(e -> cityTextField.setText(city)); // Set text field on menu item selection
                favoritesMenu.getItems().add(menuItem);
            }
        } catch (IOException e) {
            // Handle exception

        }
    }

    /**
    * Adds the specified city to the favorites list, saving it to the "favorites" file.
    * Checks if the city is already a favorite before adding, and updates UI accordingly.
    * Handles exceptions and displays appropriate messages.
    * @param cityName The name of the city to be added to favorites.
    */
    private void addToFavorites(String cityName) {
        try {
            // Create or update the favorites file with the new city name
            Path filePath = Paths.get("favorites");
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            String standardizedCityName = cityName.trim().toLowerCase();
            List<String> favorites = Files.readAllLines(filePath);

            List<String> standardizedFavorites = favorites.stream()
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            
            // Check if the city is not already in favorites, then add it
            if (!standardizedFavorites.contains(standardizedCityName)) {
                Files.writeString(filePath, cityName + System.lineSeparator(), StandardOpenOption.APPEND);
                loadFavorites();
                Platform.runLater(() -> secondWindowInfoText.setText("Favorite successfully saved!"));
            } else {
                Platform.runLater(() -> secondWindowInfoText.setText("City is already a favorite."));
            }
        } catch (IOException ex) {
            Platform.runLater(() -> secondWindowInfoText.setText("Error: " + ex.getMessage()));
        }
    }

    /**
    * Performs a weather search for the specified city name using the OpenWeatherMap API.
    * Reads the weather data from a file, updates the UI with the new data, and saves the last searched city.
    * Handles exceptions that may occur during the search or file operations.
    * @param cityName The name of the city for which weather information is to be retrieved.
    */
    private void performSearch(String cityName) {
        // Use the weather API to look up location data for the given city name
        weatherApiImpl weatherApi = new weatherApiImpl();
        weatherApi.lookUpLocation(cityName);

        try {
            // Read weather data from file and update the UI components
            ReadFile file = new ReadFile();
            file.readFromFile("weatherData");

            hourlyWeatherList = file.getHourlyWeather(); // Update hourlyWeatherList
            Platform.runLater(this::updateHourlyWeatherSection);

            dailyWeatherList = file.getDailyWeather();
            Platform.runLater(this::updateDailyWeatherSection);

            currentWeather = file.getCurrentWeather();
            Platform.runLater(this::updateCurrentWeatherSection);

        } catch (IOException e) {
            // Handle exception
        }

        saveLastSearchedCity(cityName);
    }

    public static void main(String[] args) {
        launch();
    }
}
