import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherGifApplication {

    private static final String WEATHER_API_KEY = "321fd67565a241b7aea232847230208";
    private static final String GIF_API_KEY = "wJ7odbDVcDMTG2rl7mVudybDTvg1KhoW";
    private static final String WEATHER_API_URL = "https://api.weatherapi.com/v1/current.json";
    private static final String GIF_API_URL = "https://api.giphy.com/v1/gifs/translate";

    private static final Gson gson = new Gson();

    public void showWeatherAndGif(Stage primaryStage, String cityName) {
        try {
            WeatherInfo weatherInfo = fetchWeatherInfo(cityName);
            GifInfo gifInfo = fetchGifInfo(weatherInfo.getCurrent().getCondition().getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("WeatherView.fxml"));
            AnchorPane root = loader.load();

            WeatherViewController controller = loader.getController();
            controller.setWeatherInfo(weatherInfo);
            controller.setGifInfo(gifInfo);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Weather and GIF Info");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WeatherInfo fetchWeatherInfo(String cityName) throws IOException {
        String urlStr = WEATHER_API_URL + "?key=" + WEATHER_API_KEY + "&q=" + cityName.split(",")[0];
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return gson.fromJson(reader, WeatherInfo.class);
        }
    }

    private GifInfo fetchGifInfo(String condition) throws IOException {
        String urlStr = GIF_API_URL + "?api_key=" + GIF_API_KEY + "&s=" + URLEncoder.encode(condition, StandardCharsets.UTF_8);
        URL url = new URL(urlStr);
        System.out.println(url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return gson.fromJson(reader, GifInfo.class);
        }
    }
}
