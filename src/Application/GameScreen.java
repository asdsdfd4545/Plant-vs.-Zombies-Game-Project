package Application;

import Plants.BasePlant;
import Plants.Plant;
import Plants.SuperPlant;
import Plants.TrapPlant;
import assets.ResourceLoader;
import Logic.Bullet;
import Logic.GameCurrency;
import Logic.ZomieState;
import zombies.Berserker;
import zombies.Kappa;
import zombies.Wukong;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameScreen {
    private Pane root; //root pane ของเกม
    private Label moneyLabel; //โชว์เงินในเกม
    private List<Bullet> bullets;
    private List<Kappa> zombies;
    private List<Plant> plants;
    private Random random; //เอาไว้สุ่มเลข
    private static final int NUM_ROWS = 5; //จำนวนแถว
    private static final int NUM_COLUMNS = 9; //จำนวน column
    private static final int ZOMBIE_SPAWN_INTERVAL = 1250; //เอาไว้กำหนดความถี่ในการเกิดของซอมบี้
    private boolean[][] plantGrid = new boolean[NUM_ROWS][NUM_COLUMNS]; //เอาไว้เช็คว่ามีต้นไม้ในช่องนั้นๆหรือยัง
    private int[] plantColumns; //มีขนาดเท่ากับจำนวนแถว และในแต่ละตัวคือ เอาไว้เก็บ column ปัจจุบันที่อยู่ของแต่ละแถว
    private int currentRowIndex = -1; // เอาไว้บอกว่าตอนนี้ Hilight อยู่ที่แถวอะไรในตอนนี้
    private int LastButtonPush = -1; // เอาไว้บอกว่าก่อนหน้านี้ Hilight อยู่แถวอะไร
    private Rectangle currentHighlight; // เอาไว้เก็บ Hilight ปัจจุบันเพื่อเอาไว้ลบ
    private double X,Y; // ตำแหน่ง X,Y
    private Label timerLabel; // Label เอาไว้จับเวลา
    private Label roundLabel; // Label เอาไว้โชว์ว่าอยู่ Round ไหนแลว
    private int countdownTime = 60; // กำหนดเวลาที่จะใช้ใน แต่ละ round
    private boolean gameStarted; // เอาไว้บอกว่าเกมเริ่มรึยีง
    private boolean gameResetInProgress = false; // เอาไว้ใช้บอกสถานะเพื่อกันรีเซ็ตก่อนที่จะนับถอยหลัง
    private AnimationTimer countdownTimer; // AnimationTimer เอาไว้จับเวลา
    private boolean gamePaused = false;  // เอาไว้เป็นสถานะเพื่อควบคุมว่าเกมหยุดชั่วคราว
    private int Round = 0; // Round ปัจจุบัน
    private boolean gameAlreadyEnd = false; //เอาไว้บอกว่าเกมจบหรือยัง
    private boolean isGameSoundPlaying = false; // เอาไว้บอกว่าเสียงเกมมารึยัง
    private AudioClip gameSound; //เสียงเอาไว้ใช้ในเกม
    private AudioClip nextwaveSound; //เสียงเอาไว้ใช้เลื่อน round
    
    public GameScreen() {
        root = new Pane();
        
        bullets = new ArrayList<>();
        zombies = new ArrayList<>();
        plants = new ArrayList<>();
        
        plantColumns = new int[NUM_ROWS];
        random = new Random();
        gameStarted = false;
        gameAlreadyEnd = false;
        GameCurrency.setMoney();
        initializeGameScreen();
        updateMoneyDisplay();
    }

    private void initializeGameScreen() {
        // Background
        Image backgroundImage = ResourceLoader.getImage("BackGroundImage");
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(800);
        background.setFitHeight(600);
        root.getChildren().add(background);
        
        gameSound = ResourceLoader.getAudio("GameSound");
        gameSound.setCycleCount(AudioClip.INDEFINITE); // เล่นซ้ำเมื่อเสียงจบ
        if(!isGameSoundPlaying) gameSound.play();
        isGameSoundPlaying = true;
    
        //Money Label
        moneyLabel = new Label("Money\n" + GameCurrency.getMoney());
        moneyLabel.setFont(new Font("Elephant", 20));
        moneyLabel.setTextAlignment(TextAlignment.CENTER);
        moneyLabel.setTextFill(Color.DARKGREEN);
        moneyLabel.setLayoutX(10);
        moneyLabel.setLayoutY(10);
        moneyLabel.setStyle("-fx-border-color: green; -fx-border-width: 3px; -fx-background-color: white; -fx-padding: 5;");
        root.getChildren().add(moneyLabel);

        // Plant Buttons for each row (BasePlant, SuperPlant, TrapPlant)
        
        Button basePlantButton = createPlantRowButtons("50", 155, "BasePlantImage", 40, 40);
        basePlantButton.setOnAction(event -> plantAction(currentRowIndex, "BasePlant"));
        Button superPlantButton = createPlantRowButtons("100", 228, "SuperPlantImage", 45, 45);
        superPlantButton.setOnAction(event -> plantAction(currentRowIndex, "SuperPlant"));
        Button trapPlantButton = createPlantRowButtons("20", 299, "TrapPlantImage", 40 ,40);
        trapPlantButton.setOnAction(event -> plantAction(currentRowIndex, "TrapPlant"));
        Button emptyPlantButton = new Button("Empty");
        emptyPlantButton.setId("Plant");
        emptyPlantButton.setPrefWidth(75);
        emptyPlantButton.setPrefHeight(15);
        emptyPlantButton.setLayoutX(9);
        emptyPlantButton.setLayoutY(372);
        emptyPlantButton.setOnAction(event -> plantAction(currentRowIndex, "Empty"));
        root.getChildren().addAll(basePlantButton, superPlantButton, trapPlantButton, emptyPlantButton);

        //selectRows Button
        Button btn1 = createSelectRowButtons("A", 425, 0);
        Button btn2 = createSelectRowButtons("B", 455, 1);
        Button btn3 = createSelectRowButtons("C", 485, 2);
        Button btn4 = createSelectRowButtons("D", 515, 3);
        Button btn5 = createSelectRowButtons("E", 545, 4);

        root.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);
        
        // Timer Label - Display Countdown
        timerLabel = new Label("Time : 01:00");
        timerLabel.setFont(new Font("Comic Sans MS", 14));
        timerLabel.setTextFill(Color.BLACK);
        timerLabel.setLayoutX(701); 
        timerLabel.setLayoutY(2);
        timerLabel.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white; -fx-padding: 5;");
        root.getChildren().add(timerLabel);
        
        roundLabel = new Label("Round : "+(Round+1));
        roundLabel.setFont(new Font("Comic Sans MS", 14));
        roundLabel.setTextFill(Color.BLACK);
        roundLabel.setLayoutX(621); 
        roundLabel.setLayoutY(2);
        roundLabel.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white; -fx-padding: 5;");
        root.getChildren().add(roundLabel);
        
        // Start Button
        Button startButton = new Button("Start");
        startButton.setPrefHeight(30);
        startButton.setPrefWidth(75);
        startButton.setLayoutX(9);
        startButton.setLayoutY(98);
        startButton.setOnAction(event -> startAction());
        root.getChildren().add(startButton);
    }

    private void startAction() {
        Round++;
        AudioClip buttonSound = ResourceLoader.getAudio("ButtonSound");
        buttonSound.play();
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.millis(200)); // 200ms
        pause.setOnFinished(e -> {
        	if (!gameStarted) {
        		startGame();
        	}

        	if (!gameResetInProgress) {
        		startCountdownTimer();
        	}
        });
        pause.play();
    }
    
    private void startGame() {
        // Hide all plant purchase buttons
        for (Node node : root.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if ("Plant".equals(button.getId()) || "select".equals(button.getId())) {
                    button.setVisible(false);
                }
            }
        }
        if (currentHighlight != null) {
            root.getChildren().remove(currentHighlight);
        }
        gamePaused = false;
        spawnZombie();
        startGameLoop();
        gameStarted = true;
    }
    
    private void startCountdownTimer() {
        gameResetInProgress = true; // เอาไว้ป้องกันการจับเวลาที่ซับซ้อนกัน
        nextwaveSound = ResourceLoader.getAudio("NextWaveSound");

        countdownTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) { // 1 sec
                    if (countdownTime > 0) {
                        countdownTime--;
                        int minutes = countdownTime / 60;
                        int seconds = countdownTime % 60;
                        timerLabel.setText(String.format("Time : %02d:%02d", minutes, seconds));
                    } else {
                        if(!gameAlreadyEnd) {
                        	nextwaveSound.play();
                        	resetGame();
                        }
                    }
                    lastUpdate = now;
                }
            }
        };
        countdownTimer.start();
    }
    
    private void resetGame() {
    	stopGameLoop(); 
        countdownTimer.stop(); 

        // ปรับให้plantGrid เป็น false ให้หมดเพื่อเตรียมที่ให้วางต้นไม้สำหรับ round ถัดไป
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                plantGrid[i][j] = false;
            }
        }
        
        currentRowIndex = -1;
        LastButtonPush = -1;
        
        bullets.clear();
        plants.clear();
        zombies.clear();
        
        root.getChildren().clear();

        if (Round >= 3 && !gameAlreadyEnd) {
    		switchToYouWinScreen();
    		return;
    	}
        
        initializeGameScreen(); // Reinitialize หน้าจอเกมใหม่
        countdownTime = 60; // รีเวลา
        gameStarted = false; // ปักไว้ว่าเกมยังไม่เริ่ม
        gameResetInProgress = false; // อนุญาตให้มีการสั่งจับเวลาได้แล้ว
        updateMoneyDisplay(); // รีเซ็ตเงิน
    }
    
    private void selectAction(int rowIndex) {
        	currentRowIndex = rowIndex;
        	plantAction(rowIndex, "Empty");
	}
    
    private Button createSelectRowButtons(String label, double layoutY, int rowIndex) {
        Button button = new Button(label);
        button.setId("select");
        button.setPrefWidth(75);
        button.setPrefHeight(20);
        button.setLayoutX(9);
        button.setLayoutY(layoutY);
        button.setOnAction(event -> selectAction(rowIndex));
        button.setVisible(true);
        return button;
    	
    }

	private Button createPlantRowButtons(String price, double yPosition, String paraImage, int fitWidth, int fitHeights) {
        Button button = new Button(price);
        button.setId("Plant");
        button.setPrefWidth(75);
        button.setPrefHeight(65);
        button.setLayoutX(9);
        button.setLayoutY(yPosition);
        Image image = ResourceLoader.getImage(paraImage);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(fitWidth); 
        imageView.setFitHeight(fitHeights); 
        imageView.setPreserveRatio(true); // เพื่อให้สัดส่วนของรูปภาพไม่ผิดเพี้ยน
        // ตั้งค่าให้แสดงรูปภาพในปุ่ม
        button.setGraphic(imageView);
        button.setContentDisplay(ContentDisplay.TOP);
        return button;
    
    }

    private void plantAction(int rowIndex, String plantType) {
    	AudioClip buttonSound = ResourceLoader.getAudio("ButtonSound");
        buttonSound.play();
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.millis(200)); // 200ms ดีเลย์
        pause.setOnFinished(e -> {
    	if (currentRowIndex == -1) return;
    	if(rowIndex != LastButtonPush) plantColumns[rowIndex] = 0;
    	LastButtonPush = rowIndex;
    	int spendingcost = 0;
        switch (plantType) {
         	case "Empty":
            	spendingcost = 0;
            	break; 
            case "BasePlant":
                spendingcost = 50;
                break; 
            case "SuperPlant":
                spendingcost = 100;
                break; 
            case "TrapPlant":
                 spendingcost = 20;
                 break; 
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
        });
        pause.play();
    }
 
    private void spawnZombie() {
        int row = random.nextInt(NUM_ROWS);
        double spawnY = (row * (600 / NUM_ROWS)) + 30;
        double spawnX = 800; 

//        Random random = new Random();
        int randomNumber = random.nextInt(Round);
        Kappa zombie = null;
        
        switch (randomNumber) {
        case 0:
        	zombie = new Kappa(spawnX, spawnY); 
            zombies.add(zombie);
            break;
        case 1:
        	zombie = new Berserker(spawnX, spawnY); 
            zombies.add(zombie);
            break;
        case 2:
        	zombie = new Wukong(spawnX, spawnY); 
            zombies.add(zombie);
            break;
        }
           
        ImageView zombieShape = zombie.getShape();
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
            Iterator<Kappa> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
            	Kappa zombie = zombieIterator.next();
                if (bullet.getShape().getBoundsInParent().intersects(zombie.getShape().getBoundsInParent())) {
                    zombie.takeDamage();
                    root.getChildren().remove(bullet.getShape());
                    bulletIterator.remove();
                    if (zombie.isDead()) {
                        zombie.setState(ZomieState.DEAD);
                       
                        // ตั้งเวลาให้ลบ Zombie หลังจาก Animation เสร็จ
                        javafx.animation.Timeline removeZombieTimeline = new javafx.animation.Timeline(
                            new javafx.animation.KeyFrame(
                                javafx.util.Duration.seconds(1), // หน่วงเวลา 1 วินาที (หรือความยาว Animation)
                                event -> {
                                    root.getChildren().remove(zombie.getShape());
                                    zombies.remove(zombie);
                                }
                            )
                        );
                        removeZombieTimeline.setCycleCount(1); // รันแค่ครั้งเดียว
                        removeZombieTimeline.play();
                    }

                    break;
                }
            }
        }

        // Check plant collisions
        for (Kappa zombie : zombies) {
            for (Plant plant : plants) {
                if (zombie.getShape().getBoundsInParent().intersects(plant.getShape().getBoundsInParent())) {
                    if (!zombie.isAttacking()&& zombie.getAttackTimeline() == null) {
                        // Zombie เริ่มโจมตี
                        zombie.setState(ZomieState.ATTACK);
                        zombie.stopMovement();

                        // เริ่มโจมตีทุก 1 วินาที
                        javafx.animation.Timeline attackTimeline = new javafx.animation.Timeline(
                            new javafx.animation.KeyFrame(
                                javafx.util.Duration.seconds(1),
                                event -> {
                                    if (!plant.isDead() && !zombie.isDead()) {
                                        plant.takeDamage();
                                        if (plant.isDead()) {
                                            root.getChildren().remove(plant.getShape());
                                            plants.remove(plant);
                                            zombie.startMovement();
                                            zombie.setState(ZomieState.WALK);
                                            zombie.setAttackTimeline(null);
                                            for (Kappa otherZombie : zombies) {
                                                if (otherZombie.isAttacking()&&(zombie.getY()==otherZombie.getY())) {
                                                    otherZombie.startMovement();
                                                    otherZombie.setState(ZomieState.WALK);
                                                    otherZombie.setAttackTimeline(null);
                                                }
                                            }
                                        }
                                        
                                    }
                                }
                            )
                        );
                        attackTimeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
                        attackTimeline.play();
                        zombie.setAttackTimeline(attackTimeline);
                    }
                    break;
                }
            }
        }
    }
    
    private void switchToGameOverScreen() {
        root.getChildren().clear();
        AudioClip loseSound = ResourceLoader.getAudio("FailureSound");
        gameSound.stop();
        loseSound.play();
        gameAlreadyEnd = true;
        
        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(new Font("Comic Sans MS", 120));
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setLayoutX(90);
        gameOverLabel.setLayoutY(185);

        root.setStyle("-fx-background-color: black;");
        root.getChildren().add(gameOverLabel);
    }
    
    private void switchToYouWinScreen() {
        root.getChildren().clear();
        gameSound.stop();
        nextwaveSound.play();
        gameAlreadyEnd = true;

        Label youWinLabel = new Label("You Win");
        youWinLabel.setFont(new Font("Comic Sans MS", 120));
        youWinLabel.setTextFill(Color.GREEN);
        youWinLabel.setLayoutX(160);
        youWinLabel.setLayoutY(185);

        root.setStyle("-fx-background-color: black;");
        root.getChildren().add(youWinLabel);
    }

    private void startGameLoop() {
        if (gamePaused) return; // เอาไว้หยุดเกม loop / ไม่ให้เริ่มเกมถ้า gamepaused อยู่

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastZombieSpawnTime = 0;

            @Override
            public void handle(long now) {
                // If the game is paused, stop the loop
                if (gamePaused) {
                    stop();
                    return;
                }

                // Update bullets
                Iterator<Bullet> bulletIterator = bullets.iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    bullet.update();  // อัพเดตกระสุน
                    if (bullet.getX() > 800) {  // กระสุนออกนอกหน้าจอแล้ว
                        root.getChildren().remove(bullet.getShape()); 
                        bulletIterator.remove();  
                    }
                }

                // Update Zombies
                for (Kappa zombie : zombies) {
                    zombie.update();

                    // zombie วิ่งไปจนถึงบ้านแล้ว
                    if (zombie.getX() < 100 && !gameAlreadyEnd) {
                        switchToGameOverScreen();
                        stop();  // Stop the game loop
                        return;  // Exit the game loop
                    }
                }

                // Update ให้ต้นไม้ยิงกระสุน
                for (Plant plant : plants) {
                    plant.update(bullets, root); 
                }

                checkCollisions();

                // Spawn ซอมบี้ภายใน interval เวลาที่เรากำหนด
                if (now - lastZombieSpawnTime > ZOMBIE_SPAWN_INTERVAL * 1000000L) {
                    spawnZombie();
                    lastZombieSpawnTime = now;
                }
            }
        };

        gameLoop.start();
    }

    private void updateMoneyDisplay() {
        moneyLabel.setText("Money\n" + GameCurrency.getMoney());
    }

    private void stopGameLoop() {
        gamePaused = true; 
    }

    public Pane getRoot() {
        return root;
    }
}
