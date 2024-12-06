package Application;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import Logic.Bullet;
import Logic.Zombie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameScreen {
    private Pane root;
    private List<Bullet> bullets;
    private List<Zombie> zombies;
    private Random random;
    private static final int NUM_ROWS = 5;
    private static final int ZOMBIE_SPAWN_INTERVAL = 2000; // Milliseconds between spawns

    public GameScreen() {
        root = new Pane();
        bullets = new ArrayList<>();
        zombies = new ArrayList<>();
        random = new Random();
        initializeGameScreen();
        startGameLoop();
    }

    private void initializeGameScreen() {
        // Background
        Image backgroundImage = new Image(getClass().getResource("/res/Starting.png").toExternalForm());
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(800);
        background.setFitHeight(600);
        root.getChildren().add(background);

        // Plant
        Image plantImage = new Image(getClass().getResource("/res/Unknown.jpeg").toExternalForm());
        ImageView plant = new ImageView(plantImage);
        plant.setFitWidth(40);
        plant.setFitHeight(40);
        plant.setX(100);
        plant.setY(300);
        root.getChildren().add(plant);

        // Spawn first zombies (optional)
        spawnZombie();
    }

    private void spawnZombie() {
        // Randomly select a row (from 0 to 4) and spawn the zombie at a random x-coordinate
        int row = random.nextInt(NUM_ROWS);
        double spawnY;
        
        if (row <= 2) {
        	spawnY = ( row * (600 / NUM_ROWS) ) + 50;
        } else {
        	spawnY = ( row * (600 / NUM_ROWS) ) + 30;
        }
        
        double spawnX = 800;  // Spawn from the right side of the screen

        Zombie zombie = new Zombie(spawnX, spawnY, 1); // Speed 1 for example
        zombies.add(zombie);

        Image zombieImage = new Image(getClass().getResource("/res/ad.jpeg").toExternalForm());
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
