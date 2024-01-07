# Programming 3 project repository - WeatherApp, By Oona Leivo & Reetta Koskinen

This is a weather application that provides users with a graphical interface for browsing weather forecasts. 

It includes the following features:
Graphical User Interface: The application offers a graphical interface that enables browsing of weather forecasts.
Favorite Locations: Users can save their preferred locations as favorites, making it easier to access forecasts for these areas later.
Save last searched city: The application saves the user's state, including the current location and favorites, to the disk. This information is retrieved when the application is restarted.
Detailed Forecast: WeatherApp presents a detailed forecast, including hourly forecasts and daily summaries. 
Custom Icons: The program utilizes its own set of icons, different from those provided by the weather data service, to offer a visual representation of weather conditions.
Error Handling in File Processing: The application effectively manages file processing errors, providing user notifications about any issues encountered.
Unit Tests: The program includes unit tests to ensure its functionality, helping to identify and correct potential errors.

Technologies Used: 
Java
JavaFX for the UI
OpenWeather API

Contact Information:
oona.leivo@tuni.fi
reetta.koskinen@tuni.fi

IMPLEMENTED WORK DISTRIBUTION
Reetta focused on the integration of the OpenWeatherMap API. She designed the iAPI interface and implemented the weatherApiImpl class, which handled making API calls and processing weather data. 
Additionally, Reetta designed the data model for weather information, creating classes for Current, Hourly, and Daily weather data. She also worked on the structure of the graphical user interface, including layout and element creation.
Oona focused on implementing the ReadFile class, which involved reading JSON data obtained through OpenWeatherMap API calls. This also included retrieving and storing necessary information for our weather program. 
Oona was responsible for updating information in the user interface when a new city was searched. She handled the functionality of searching for new cities and managing favorites, encompassing the overall functionality of the 
Search and Favorites window. Additionally, Oona played a significant role in documenting the project and creating a UML diagram.

Although we focused on specific aspects more closely, there was some variation in the mentioned responsibilities, and both of us worked on all parts of the project. We assisted each other in problem-solving 
and collaboratively planned how functionalities should be implemented. We also worked together physically a lot. In the end, the workload was evenly distributed between us.

