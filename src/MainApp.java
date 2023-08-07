// Imports

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML layout using the FXMLLoader
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        // Create a new Scene with the loaded FXML layout
        Scene scene = new Scene(root);

        // Add the external stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Set the created Scene to the Stage (window)
        stage.setScene(scene);

        // Set the title of the Stage (window)
        stage.setTitle("Random Quote Generator");

        // Set an icon for the Stage (window)
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

        // Show the Stage (window) with the configured Scene
        stage.show();
    }

    // Entry point of the Java application
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
