package Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the homepage layout
            Pane homePageRoot = createHomePage(primaryStage); 

            // Set the homepage scene
            Scene homePageScene = new Scene(homePageRoot, 800, 600); 

            // Set the title of the window
            primaryStage.setTitle("Plant vs Zombie - Homepage");
            primaryStage.setScene(homePageScene);  // Set the scene on the primary stage
            primaryStage.show();  // Show the stage (window)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pane createHomePage(Stage primaryStage) {
        Pane homePageRoot = new Pane();

        // Set background image for homepage
        Image homePageImage = new Image(getClass().getResource("/res/Homepage.png").toExternalForm());
        ImageView homePageImageView = new ImageView(homePageImage);
        homePageImageView.setFitWidth(800);
        homePageImageView.setFitHeight(600);
        homePageRoot.getChildren().add(homePageImageView);

        // Create "Enter Game" button
        Button enterGameButton = new Button("Enter Game");
        enterGameButton.setPrefWidth(250);
        enterGameButton.setPrefHeight(70);
        enterGameButton.setLayoutX(275);
        enterGameButton.setLayoutY(200); // Adjust Y-position for centering
        enterGameButton.setFont(new Font("Jokerman", 30));
        enterGameButton.setTextFill(Color.WHITE);
        enterGameButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5;");
        enterGameButton.setOnAction(event -> {
            // When clicked, switch to the game screen
            GameScreen gameScreen = new GameScreen();  // Instantiate the game screen
            Scene gameScene = new Scene(gameScreen.getRoot(), 800, 600);  // Set the scene size
            primaryStage.setScene(gameScene);  // Switch to the game screen
        });

        // Create "Exit Game" button
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(250);
        exitButton.setPrefHeight(70);
        exitButton.setLayoutX(275);
        exitButton.setLayoutY(280); // Adjust Y-position to appear below the "Enter Game" button
        exitButton.setFont(new Font("Jokerman", 30));
        exitButton.setTextFill(Color.WHITE);
        exitButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5;");
        exitButton.setOnAction(event -> {
            // When clicked, exit the application
            primaryStage.close();
        });

        // Add the buttons to the homepage root
        homePageRoot.getChildren().addAll(enterGameButton, exitButton);

        return homePageRoot;
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
