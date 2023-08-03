import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchViewController {

    @FXML
    private TextField cityTextField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> cityListView;

    @FXML
    private Button submitCityButton;

    private Stage primaryStage;
    private WeatherGifApplication weatherGifApp;
    private String selectedCity;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        weatherGifApp = new WeatherGifApplication();
    }

    @FXML
    private void searchCities() {
        String cityName = cityTextField.getText().trim();
        if (!cityName.isEmpty()) {
            try {
                // Call the API to get a list of cities with the same name
                List<String> cities = searchCitiesByName(cityName);
                populateCityListView(cities);
            } catch (IOException e) {
                showErrorMessage("Error while fetching data from the API.");
                e.printStackTrace();
            }
        } else {
            showErrorMessage("Please enter a city name.");
        }
    }

    @FXML
    private void submitSelectedCity() {
        if (selectedCity != null) {
            weatherGifApp.showWeatherAndGif(primaryStage, selectedCity);
        } else {
            showErrorMessage("Please select a city from the list.");
        }
    }

    private List<String> searchCitiesByName(String cityName) throws IOException {
        String apiKey = "321fd67565a241b7aea232847230208";
        String apiUrl = "http://api.weatherapi.com/v1/search.json?q=" + cityName.split(",")[0] + "&key=" + apiKey;

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() == 200) {
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(new InputStreamReader(conn.getInputStream()));
            if (rootElement.isJsonArray()) {
                JsonArray jsonArray = rootElement.getAsJsonArray();
                List<String> cities = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject cityObject = element.getAsJsonObject();
                    String name = cityObject.get("name").getAsString();
                    String country = cityObject.get("country").getAsString();
                    String cityInfo = name + ", " + country;
                    cities.add(cityInfo);
                }
                return cities;
            }
        } else {
            showErrorMessage("Error: Unable to fetch data from the API.");
        }

        return new ArrayList<>();
    }

    private void populateCityListView(List<String> cities) {
        cityListView.getItems().clear();
        cityListView.getItems().addAll(cities);

        cityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCity = newValue;
        });
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
