package Application;

import Plants.BasePlant;
import Plants.Plant;
import Plants.SuperPlant;
import Plants.TrapPlant;
import Logic.Bullet;
import Logic.GameCurrency;
import Logic.Zombie;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private boolean gameStarted;

    public GameScreen() {
        root = new Pane();
        
        bullets = new ArrayList<>();
        zombies = new ArrayList<>();
        plants = new ArrayList<>();
        
        plantColumns = new int[NUM_ROWS];
        random = new Random();
        gameStarted = false;
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
        moneyLabel.setLayoutY(5);
        moneyLabel.setStyle("-fx-background-color: white; -fx-padding: 5;");
        root.getChildren().add(moneyLabel);

        // Plant Buttons for each row (BasePlant, SuperPlant, TrapPlant)
        for (int rowIndex = 0; rowIndex < NUM_ROWS; rowIndex++) {
            createPlantRowButtons(rowIndex);
        }
        
        // Start Button
        Button startButton = new Button("Start");
        startButton.setPrefHeight(30);
        startButton.setPrefWidth(75);
        startButton.setLayoutX(700);
        startButton.setLayoutY(15);
        startButton.setOnAction(event -> startAction());
        root.getChildren().add(startButton);
    }

    private void updateMoneyDisplay() {
        moneyLabel.setText("Money: " + GameCurrency.getMoney());
    }

    private void startAction() {
        if (!gameStarted) {
            // Hide all plant purchase buttons
            for (Node node : root.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if ("Plant".equals(button.getId())) {
                        button.setVisible(false);
                    }
                }
            }
            spawnZombie();
            startGameLoop();
            gameStarted = true;
        }
    }

    private void createPlantRowButtons(int rowIndex) {
        double yPosition = 40 + rowIndex * 110;  // Y position for each row's buttons

        // Create Button for BasePlant
        Button basePlantButton = new Button("BasePlant");
        basePlantButton.setId("Plant");
        basePlantButton.setPrefWidth(55);
        basePlantButton.setPrefHeight(15);
        basePlantButton.setLayoutX(20);
        basePlantButton.setLayoutY(yPosition);
        basePlantButton.setOnAction(event -> plantAction(rowIndex, "BasePlant"));
        root.getChildren().add(basePlantButton);

        // Create Button for SuperPlant
        Button superPlantButton = new Button("SuperPlant");
        superPlantButton.setId("Plant");
        superPlantButton.setPrefWidth(55);
        superPlantButton.setPrefHeight(15);
        superPlantButton.setLayoutX(20);
        superPlantButton.setLayoutY(yPosition + 30);
        superPlantButton.setOnAction(event -> plantAction(rowIndex, "SuperPlant"));
        root.getChildren().add(superPlantButton);

        // Create Button for TrapPlant
        Button trapPlantButton = new Button("TrapPlant");
        trapPlantButton.setId("Plant");
        trapPlantButton.setPrefWidth(55);
        trapPlantButton.setPrefHeight(15);
        trapPlantButton.setLayoutX(20);
        trapPlantButton.setLayoutY(yPosition + 60);
        trapPlantButton.setOnAction(event -> plantAction(rowIndex, "TrapPlant"));
        root.getChildren().add(trapPlantButton);
    }

    private void plantAction(int rowIndex, String plantType) {
        int spendingcost = 0;
        switch (plantType) {
            case "BasePlant":
                spendingcost = 50;
                break; // Prevent fall-through
            case "SuperPlant":
                spendingcost = 100;
                break; // Prevent fall-through
            case "TrapPlant":
                spendingcost = 20;
                break; // Prevent fall-through
        }

        if (plantColumns[rowIndex] < NUM_COLUMNS && GameCurrency.spend(spendingcost)) {
            double x = 160 + plantColumns[rowIndex] * 68;
            double y = rowIndex * (600 / NUM_ROWS) + 30;

            // Create and add the appropriate plant type
            Plant plant = null;
            switch (plantType) {
                case "BasePlant":
                    plant = new BasePlant(x, y);
                    break;
                case "SuperPlant":
                    plant = new SuperPlant(x, y);
                    break;
                case "TrapPlant":
                    plant = new TrapPlant(x, y);
                    break;
            }

            if (plant != null) {
                plants.add(plant);
                root.getChildren().add(plant.getShape());
            }

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
        // Check bullet collisions
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Zombie> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
                Zombie zombie = zombieIterator.next();
                if (bullet.getShape().getBoundsInParent().intersects(zombie.getShape().getBoundsInParent())) {
                    zombie.takeDamage();
                    root.getChildren().remove(bullet.getShape());
                    bulletIterator.remove();
                    if (zombie.isDead()) {
                        root.getChildren().remove(zombie.getShape());
                        zombieIterator.remove();
                        spawnZombie(); // Spawn a new zombie
                    }
                    break;
                }
            }
        }

        // Check plant collisions
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            Iterator<Plant> plantIterator = plants.iterator();
            while (plantIterator.hasNext()) {
                Plant plant = plantIterator.next();
                if (zombie.getShape().getBoundsInParent().intersects(plant.getShape().getBoundsInParent())) {
                    // Zombie sacrifices itself to kill the plant
                    root.getChildren().remove(zombie.getShape());
                    root.getChildren().remove(plant.getShape());
                    zombieIterator.remove();
                    plantIterator.remove();
                    break;
                }
            }
        }
    }

    private void switchToGameOverScreen() {
        root.getChildren().clear();

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(new Font("Arial", 48));
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setLayoutX(260);
        gameOverLabel.setLayoutY(260);

        root.setStyle("-fx-background-color: black;");
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
                    bullet.update();  // Move the bullet
                    if (bullet.getX() > 800) {  // Check if the bullet is off-screen
                        root.getChildren().remove(bullet.getShape());  // Remove bullet
                        bulletIterator.remove();  // Remove from list
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
