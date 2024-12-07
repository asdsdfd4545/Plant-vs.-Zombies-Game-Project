package Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            GameScreen gameScreen = new GameScreen();  // Instantiate the game screen
            Scene scene = new Scene(gameScreen.getRoot(), 800, 600);  // Set the scene size

            primaryStage.setTitle("Plant vs Zombie");
            primaryStage.setScene(scene);  // Set the scene on the stage
            primaryStage.show();  // Show the stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
