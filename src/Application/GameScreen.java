package Application;

import Plants.BasePlant;
import Plants.Plant;
import Plants.SuperPlant;
import Plants.TrapPlant;
import Logic.Bullet;
import Logic.GameCurrency;
import Logic.Zombie;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private boolean[][] plantGrid = new boolean[NUM_ROWS][NUM_COLUMNS];
    private Random random;
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLUMNS = 9;
    private static final int ZOMBIE_SPAWN_INTERVAL = 5000;
    private int[] plantColumns;
    private boolean gameStarted;
    private int currentRowIndex = -1;
    private int LastButtonPush = -1;
    private Rectangle currentHighlight;
    private double X,Y;

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
        moneyLabel = new Label("Money : " + GameCurrency.getMoney());
        moneyLabel.setFont(new Font("Arial", 13));
        moneyLabel.setTextFill(Color.BLACK);
        moneyLabel.setLayoutX(2);
        moneyLabel.setLayoutY(20);
        moneyLabel.setStyle("-fx-background-color: white; -fx-padding: 5;");
        root.getChildren().add(moneyLabel);

        // Plant Buttons for each row (BasePlant, SuperPlant, TrapPlant)
        
        createPlantRowButtons(1);

        //selectRows Button
        Button btn1 = createSelectRowButtons("A", 410, 0);
        Button btn2 = createSelectRowButtons("B", 440, 1);
        Button btn3 = createSelectRowButtons("C", 470, 2);
        Button btn4 = createSelectRowButtons("D", 500, 3);
        Button btn5 = createSelectRowButtons("E", 530, 4);

        root.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);
        
        // Start Button
        Button startButton = new Button("Start");
        startButton.setPrefHeight(30);
        startButton.setPrefWidth(75);
        startButton.setLayoutX(10);
        startButton.setLayoutY(100);
        startButton.setOnAction(event -> startAction());
        root.getChildren().add(startButton);
    }

    private void updateMoneyDisplay() {
        moneyLabel.setText("Money : " + GameCurrency.getMoney());
    }

    private void startAction() {
        if (!gameStarted) {
            // Hide all plant purchase buttons
            for (Node node : root.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if ("Plant".equals(button.getId()) || "select".equals(button.getId())) {
                        button.setVisible(false);
                    }
                }
            }
            spawnZombie();
            startGameLoop();
            gameStarted = true;
        }
        if (currentHighlight != null) {
            root.getChildren().remove(currentHighlight);
        }
    }
    private Button createSelectRowButtons(String label, double layoutY, int rowIndex) {
        Button button = new Button(label);
        button.setId("select");
        button.setPrefWidth(75);
        button.setPrefHeight(20);
        button.setLayoutX(10);
        button.setLayoutY(layoutY);
        button.setOnAction(event -> selectAction(rowIndex));
        button.setVisible(true);
        return button;
    	
    }
    

    private void selectAction(int rowIndex) {
		// TODO Auto-generated method stub
    	currentRowIndex = rowIndex;
        plantAction(rowIndex, "Empty");
		
	}

	private void createPlantRowButtons(int rowIndex) {
        double yPosition = 100 + rowIndex * 110;  // Y position for each row's buttons

        // Create Button for BasePlant
        Button basePlantButton = new Button("BasePlant");
        basePlantButton.setId("Plant");
        basePlantButton.setPrefWidth(75);
        basePlantButton.setPrefHeight(15);
        basePlantButton.setLayoutX(10);
        basePlantButton.setLayoutY(yPosition);
        basePlantButton.setOnAction(event -> plantAction(currentRowIndex, "BasePlant"));
        root.getChildren().add(basePlantButton);

        // Create Button for SuperPlant
        Button superPlantButton = new Button("SuperPlant");
        superPlantButton.setId("Plant");
        superPlantButton.setPrefWidth(75);
        superPlantButton.setPrefHeight(15);
        superPlantButton.setLayoutX(10);
        superPlantButton.setLayoutY(yPosition + 30);
        superPlantButton.setOnAction(event -> plantAction(currentRowIndex, "SuperPlant"));
        root.getChildren().add(superPlantButton);

        // Create Button for TrapPlant
        Button trapPlantButton = new Button("TrapPlant");
        trapPlantButton.setId("Plant");
        trapPlantButton.setPrefWidth(75);
        trapPlantButton.setPrefHeight(15);
        trapPlantButton.setLayoutX(10);
        trapPlantButton.setLayoutY(yPosition + 60);
        trapPlantButton.setOnAction(event -> plantAction(currentRowIndex, "TrapPlant"));
        root.getChildren().add(trapPlantButton);
        
        Button emptyPlantButton = new Button("Empty");
        emptyPlantButton.setId("Plant");
        emptyPlantButton.setPrefWidth(75);
        emptyPlantButton.setPrefHeight(15);
        emptyPlantButton.setLayoutX(10);
        emptyPlantButton.setLayoutY(yPosition + 90);
        emptyPlantButton.setOnAction(event -> plantAction(currentRowIndex, "Empty"));
        root.getChildren().add(emptyPlantButton);
        
    }

    private void plantAction(int rowIndex, String plantType) {
    	if (currentRowIndex == -1) return;
    	if(rowIndex != LastButtonPush) plantColumns[rowIndex] = 0;
    	LastButtonPush = rowIndex;
    	int spendingcost = 0;
        switch (plantType) {
         	case "Empty":
            	spendingcost = 0;
            	break; // Prevent fall-through
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
        
        if (plantColumns[rowIndex] >= NUM_COLUMNS) plantColumns[rowIndex] = 0;
        
        if (plantGrid[rowIndex][plantColumns[rowIndex]]) {
        	if (currentHighlight != null) {
                root.getChildren().remove(currentHighlight);
            }
        	
        	X = 160 + plantColumns[rowIndex] * 68;
            Y = rowIndex * (600 / NUM_ROWS) + 35;
 
            Rectangle highlight = new Rectangle();
            highlight.setX(X);
            highlight.setY(Y);
            highlight.setWidth(50); // ความกว้างของ column
            highlight.setHeight(50); // ความสูงของ row
            highlight.setFill(Color.TRANSPARENT); // สีโปร่งใส
            highlight.setStroke(Color.RED); // เส้นสีแดง
            highlight.setStrokeWidth(2); // ความหนาของเส้น
            root.getChildren().add(highlight);
        	
            currentHighlight = highlight;
            plantColumns[rowIndex]++;
        
            return;
        }

        if (plantColumns[rowIndex] < NUM_COLUMNS && GameCurrency.spend(spendingcost)) {
            	// Create and add the appropriate plant type
            	if (currentHighlight != null) {
                    root.getChildren().remove(currentHighlight);
                }  
            	
            	Plant plant = null;
                switch (plantType) {
                    case "BasePlant":
                        plant = new BasePlant(X, Y);
                        break;
                    case "SuperPlant":
                        plant = new SuperPlant(X, Y);
                        break;
                    case "TrapPlant":
                        plant = new TrapPlant(X, Y);
                        break;
                }

                if (plant != null) {
                    plants.add(plant);
                    root.getChildren().add(plant.getShape());
                    plantGrid[rowIndex][plantColumns[rowIndex]] = true;
                }
            	
            	
            	X = 160 + plantColumns[rowIndex] * 68;
                Y = rowIndex * (600 / NUM_ROWS) + 35;
     
                
                Rectangle highlight = new Rectangle();
                highlight.setX(X);
                highlight.setY(Y);
                highlight.setWidth(50); // ความกว้างของ column
                highlight.setHeight(50); // ความสูงของ row
                highlight.setFill(Color.TRANSPARENT); // สีโปร่งใส
                highlight.setStroke(Color.RED); // เส้นสีแดง
                highlight.setStrokeWidth(2); // ความหนาของเส้น
                root.getChildren().add(highlight);
                

                // เก็บ Highlight ปัจจุบัน
                currentHighlight = highlight;

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
