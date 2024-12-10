package Application;

import assets.ResourceLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
        	ResourceLoader.loadResources(); //โหลด resource ต่างๆมาไว้ก่อน
        	
            Pane homePageRoot = createHomePage(primaryStage); 

            Scene homePageScene = new Scene(homePageRoot, 800, 600); 

            primaryStage.setTitle("Plant vs Zombie - Homepage");
            primaryStage.setScene(homePageScene); 
            primaryStage.show();  // เปิดเวที
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pane createHomePage(Stage primaryStage) {
        Pane homePageRoot = new Pane();

        Image homePageImage = ResourceLoader.getImage("HomePageImage");
        ImageView homePageImageView = new ImageView(homePageImage);
        AudioClip button = ResourceLoader.getAudio("ButtonSound");
        AudioClip homeSound = ResourceLoader.getAudio("HomePageSound");
        homePageImageView.setFitWidth(800);
        homePageImageView.setFitHeight(600);
        homePageRoot.getChildren().add(homePageImageView);
        homeSound.setCycleCount(AudioClip.INDEFINITE);
        homeSound.play();
        
        Image logo = ResourceLoader.getImage("Logo");
        ImageView logoView = new ImageView(logo);
        logoView.setLayoutX(125);
        logoView.setLayoutY(-100);
        logoView.setFitHeight(500);
        logoView.setFitWidth(550);
 
        Button enterGameButton = new Button("Enter Game");
        enterGameButton.setPrefWidth(250);
        enterGameButton.setPrefHeight(70);
        enterGameButton.setLayoutX(275);
        enterGameButton.setLayoutY(200); 
        enterGameButton.setFont(new Font("Jokerman", 30));
        enterGameButton.setTextFill(Color.WHITE);
        enterGameButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5;");
        enterGameButton.setOnMouseEntered(event -> {
            enterGameButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5; -fx-effect: dropshadow(gaussian, gray, 10, 0.5, 0, 4);");
        });

        enterGameButton.setOnMouseExited(event -> {
            enterGameButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5; -fx-effect: none;");
        });
        enterGameButton.setOnAction(event -> {
        	homeSound.stop();
            button.play();
            // ใช้ PauseTransition เพื่อหน่วงเวลาเปลี่ยน Scene
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.millis(200));
            pause.setOnFinished(e -> {
                GameScreen gameScreen = new GameScreen(); 
                Scene gameScene = new Scene(gameScreen.getRoot(), 800, 600);  
                primaryStage.setScene(gameScene); 
            });
            pause.play();
        });


        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(250);
        exitButton.setPrefHeight(70);
        exitButton.setLayoutX(275);
        exitButton.setLayoutY(280);
        exitButton.setFont(new Font("Jokerman", 30));
        exitButton.setTextFill(Color.WHITE);
        exitButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5;");
        exitButton.setOnMouseEntered(event -> {
            exitButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5; -fx-effect: dropshadow(gaussian, gray, 10, 0.5, 0, 4);");
        });

        exitButton.setOnMouseExited(event -> {
            exitButton.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-background-color: purple; -fx-padding: 5; -fx-effect: none;");
        });
        exitButton.setOnAction(event -> {
        	button.play();
        	javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.millis(500)); 
            pause.setOnFinished(e -> {
            primaryStage.close();
            });
            pause.play();
        });
    
        homePageRoot.getChildren().addAll(enterGameButton, exitButton,logoView);

        return homePageRoot;
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
