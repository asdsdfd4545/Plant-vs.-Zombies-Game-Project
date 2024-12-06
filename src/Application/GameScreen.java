package Application;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import Logic.Bullet;
import Logic.GameCurrency;
import Logic.Zombie;
import Logic.Plant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameScreen {
    private Pane root;
    private Label moneyLabel;
    private List<Bullet> bullets;
    private List<Zombie> zombies;
    private List<Plant> plants;
    private Random random;
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLUMNS = 9;
    private static final int ZOMBIE_SPAWN_INTERVAL = 2000;
    private int[] plantColumns;

    public GameScreen() {
        root = new Pane();
        bullets = new ArrayList<>();
        zombies = new ArrayList<>();
        plants = new ArrayList<>();
        plantColumns = new int[NUM_ROWS];
        random = new Random();
        GameCurrency.setMoney();
        initializeGameScreen();
        updateMoneyDisplay();
        
    }

    private void initializeGameScreen() {
        // Background
        Image backgroundImage = new Image(getClass().getResource("/res/background.png").toExternalForm());
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(800);
        background.setFitHeight(600);
        root.getChildren().add(background);
        
        //Money Label
        moneyLabel = new Label("Money: " + GameCurrency.getMoney());
        moneyLabel.setFont(new Font("Arial", 18));
        moneyLabel.setTextFill(Color.BLACK);
        moneyLabel.setLayoutX(10);
        moneyLabel.setLayoutY(10);
        moneyLabel.setStyle("-fx-background-color: white; -fx-padding: 5;");
        root.getChildren().add(moneyLabel);
        

        // Plant Buttons
        Button btn1 = createPlantButton("A", 410, 0);
        Button btn2 = createPlantButton("B", 440, 1);
        Button btn3 = createPlantButton("C", 470, 2);
        Button btn4 = createPlantButton("D", 500, 3);
        Button btn5 = createPlantButton("E", 530, 4);
        
        root.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);
        
        //Start Button
        Button btn6 = new Button("Start");
        btn6.setPrefHeight(20);
        btn6.setPrefWidth(55);
        btn6.setLayoutX(20);
        btn6.setLayoutY(350);
        btn6.setOnAction(event->startAction());
        root.getChildren().add(btn6);

        
    }
    private void updateMoneyDisplay() {
        moneyLabel.setText("Money: " + GameCurrency.getMoney());
    }
     
    private void startAction() {
        // วนลูปผ่าน children ทั้งหมดของ root
        for (Node node : root.getChildren()) {
            // ตรวจสอบว่า node เป็น Button หรือไม่
            if (node instanceof Button) {
                Button button = (Button) node;

                // สมมติว่าคุณมีการตรวจสอบว่าเป็น Plant Button (อาจใช้ id หรือ text)
                if ("Plant".equals(button.getId())) { // หรือใช้ button.getText()
                    button.setVisible(false);
                }
            }
        }
        spawnZombie();
        startGameLoop();
        
    }

    
    private Button createPlantButton(String label, double layoutY, int rowIndex) {
        Button button = new Button(label);
        button.setId("Plant");
        button.setPrefWidth(55);
        button.setPrefHeight(20);
        button.setLayoutX(20);
        button.setLayoutY(layoutY);
        button.setOnAction(event -> plantAction(rowIndex));
        button.setVisible(true);
        return button;
    }
    

    private void plantAction(int rowIndex) {
        // Check if there's space to plant in this row
        if (plantColumns[rowIndex] < NUM_COLUMNS && GameCurrency.spend(100)) {
            double x = 160 + plantColumns[rowIndex] * 68;  // Adjust X based on column index
            double y = rowIndex * (600 / NUM_ROWS) + 30;   // Set Y based on row index

            // Create a new plant
            Plant plant = new Plant(x, y);
            plants.add(plant);
            root.getChildren().add(plant.getShape());

            // Increment the column index for this row
            plantColumns[rowIndex]++;
            updateMoneyDisplay();
        }
    }

    private void spawnZombie() {
        int row = random.nextInt(NUM_ROWS);
        double spawnY = (row * (600 / NUM_ROWS)) + 30;
        double spawnX = 800;  // Spawn from the right side of the screen

        Zombie zombie = new Zombie(spawnX, spawnY, 1); // Speed 1 for example
        zombies.add(zombie);

        Image zombieImage = new Image(getClass().getResource("/res/zombie.png").toExternalForm());
        ImageView zombieShape = new ImageView(zombieImage);
        zombieShape.setFitWidth(40);
        zombieShape.setFitHeight(60);
        zombieShape.setX(zombie.getX());
        zombieShape.setY(zombie.getY());
        zombie.setShape(zombieShape);
        root.getChildren().add(zombieShape);
    }

    private void checkCollisions() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Zombie> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
                Zombie zombie = zombieIterator.next();
                if (bullet.getShape().getBoundsInParent().intersects(zombie.getShape().getBoundsInParent())) {
                    zombie.takeDamage(); // Decrease zombie's health
                    root.getChildren().remove(bullet.getShape()); // Remove the bullet
                    bulletIterator.remove();
                    if (zombie.isDead()) {
                        root.getChildren().remove(zombie.getShape()); // Remove the zombie if it's dead
                        zombieIterator.remove();
                        spawnZombie(); // Spawn a new zombie
                    }
                    break;
                }
            }
        }

        // Check collision between zombies and plants
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            Iterator<Plant> plantIterator = plants.iterator();
            while (plantIterator.hasNext()) {
                Plant plant = plantIterator.next();
                if (zombie.getShape().getBoundsInParent().intersects(plant.getShape().getBoundsInParent())) {
                    // Zombie sacrifices itself to kill the plant
                    root.getChildren().remove(zombie.getShape()); // Remove zombie
                    root.getChildren().remove(plant.getShape()); // Remove plant
                    zombieIterator.remove();
                    plantIterator.remove();
                    break; // Exit loop once a zombie collides with a plant
                }
            }
        }
    }
    
    private void switchToGameOverScreen() {
        // Clear current game objects (optional, but ensures the game stops)
        root.getChildren().clear();

        // Create a Game Over Label
        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(new Font("Arial", 48));
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setLayoutX(260); // Center the text horizontally (adjust as needed)
        gameOverLabel.setLayoutY(260); // Center the text vertically (adjust as needed)

        // Set background to black
        root.setStyle("-fx-background-color: black;");

        // Add the label to the root pane
        root.getChildren().add(gameOverLabel);
    }


    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastZombieSpawnTime = 0;

            @Override
            public void handle(long now) {
                // Update bullets
                Iterator<Bullet> bulletIterator = bullets.iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    bullet.update();
                    if (bullet.getX() > 800) {
                        root.getChildren().remove(bullet.getShape());
                        bulletIterator.remove();
                    }
                }

                // Update Zombies
                for (Zombie zombie : zombies) {
                    zombie.update();

                    // Check if zombie reaches the left side of the screen
                    if (zombie.getX() < 100) {
                        switchToGameOverScreen();
                        stop();  // Stop the game loop
                        return;  // Exit the game loop
                    }
                }

                // Update Plants and shoot bullets
                for (Plant plant : plants) {
                    plant.update(bullets, root);  // Plants shoot bullets when updated
                }

                // Check collisions
                checkCollisions();

                // Spawn a new zombie at regular intervals (e.g., every 2 seconds)
                if (now - lastZombieSpawnTime > ZOMBIE_SPAWN_INTERVAL * 1000000L) {
                    spawnZombie();
                    lastZombieSpawnTime = now;
                }
            }
        };

        gameLoop.start();
    }


    public Pane getRoot() {
        return root;
    }
}