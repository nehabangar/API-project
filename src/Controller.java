import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Controller {
    @FXML
    private Label quoteLabel;

    @FXML
    private ImageView gifImageView;

    @FXML
    private Button generateButton;

    @FXML
    private void generateButtonClicked(ActionEvent event) {
        try {
            // Fetch random quote from Quotes API
            HttpRequest quoteRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://quotes15.p.rapidapi.com/quotes/random/?language_code=en"))
                    .header("X-RapidAPI-Key", "14c380c9e4msh55758bc7889d3c6p160dabjsn3de21ea54787")
                    .header("X-RapidAPI-Host", "quotes15.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> quoteResponse = HttpClient.newHttpClient().send(quoteRequest, HttpResponse.BodyHandlers.ofString());
            String quoteJson = quoteResponse.body();

            // Parse JSON response
            JsonObject quoteObject = JsonParser.parseString(quoteJson).getAsJsonObject();
            String quoteText = quoteObject.get("content").getAsString();
            quoteLabel.setText(quoteText);

            // Extract tags
            String tags = quoteObject.get("tags").getAsJsonArray().toString();
            tags = URLEncoder.encode(tags, StandardCharsets.UTF_8);

            // Fetch related GIF from Giphy API
            HttpRequest gifRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.giphy.com/v1/gifs/random?api_key=2GGhMBThamriG7BLTqC121NIO777s5Xi&tag=" + tags))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> gifResponse = HttpClient.newHttpClient().send(gifRequest, HttpResponse.BodyHandlers.ofString());
            String gifJson = gifResponse.body();

            // Parse GIF JSON response
            JsonObject gifObject = JsonParser.parseString(gifJson).getAsJsonObject();
            String gifUrl = gifObject.getAsJsonObject("data").get("images").getAsJsonObject().get("original").getAsJsonObject().get("url").getAsString();
            Image gifImage = new Image(gifUrl);
            gifImageView.setImage(gifImage);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
