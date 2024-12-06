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

public class GameScreen {
    private Pane root;
    private List<Bullet> bullets;
    private List<Zombie> zombies;

    public GameScreen() {
        root = new Pane();
        bullets = new ArrayList<>();
        zombies = new ArrayList<>();
        initializeGameScreen();
        startGameLoop();
    }

    private void initializeGameScreen() {
        // พื้นหลัง
        Image backgroundImage = new Image(getClass().getResource("/res/Starting.png").toExternalForm());
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(800);
        background.setFitHeight(600);
        root.getChildren().add(background);

        // สร้าง Plant
        Image plantImage = new Image(getClass().getResource("/res/Unknown.jpeg").toExternalForm());
        ImageView plant = new ImageView(plantImage);
        plant.setFitWidth(40);
        plant.setFitHeight(40);
        plant.setX(100);
        plant.setY(300);
        root.getChildren().add(plant);

        // เพิ่ม Zombie
        spawnZombie(700, 300);
    }

    private void spawnZombie(double x, double y) {
        Zombie zombie = new Zombie(x, y, 1);
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

    private void shootBullet(double x, double y) {
        Bullet bullet = new Bullet(x, y, 5);
        bullets.add(bullet);

        Image bulletImage = new Image(getClass().getResource("/res/been.jpg").toExternalForm());
        ImageView bulletShape = new ImageView(bulletImage);
        bulletShape.setFitWidth(10);
        bulletShape.setFitHeight(10);
        bulletShape.setX(bullet.getX());
        bulletShape.setY(bullet.getY());
        bullet.setShape(bulletShape);
        root.getChildren().add(bulletShape);
    }

    private void checkCollisions() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Zombie> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
                Zombie zombie = zombieIterator.next();
                if (bullet.getShape().getBoundsInParent().intersects(zombie.getShape().getBoundsInParent())) {
                    zombie.takeDamage(); // ลดค่า health ของซอมบี้
                    root.getChildren().remove(bullet.getShape()); // ลบกระสุน
                    bulletIterator.remove();
                    if (zombie.isDead()) {
                        root.getChildren().remove(zombie.getShape()); // ลบซอมบี้ที่ตาย
                        zombieIterator.remove();
                        spawnZombie(700, 300); // สร้างซอมบี้ใหม่
                    }
                    break;
                }
            }
        }
    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // อัปเดตกระสุน
                Iterator<Bullet> bulletIterator = bullets.iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    bullet.update();
                    if (bullet.getX() > 800) {
                        root.getChildren().remove(bullet.getShape());
                        bulletIterator.remove();
                    }
                }

                // อัปเดต Zombie
                for (Zombie zombie : zombies) {
                    zombie.update();
                }

                // ตรวจสอบการชน
                checkCollisions();

                // ยิงกระสุนทุกๆ 1 วินาที
                if (now % 60 == 0) {
                    shootBullet(120, 300); // ยิงจากตำแหน่ง Plant
                }
            }
        };

        gameLoop.start();
    }

    public Pane getRoot() {
        return root;
    }
}
