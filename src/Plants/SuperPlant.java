package Plants;

import java.util.List;

import Logic.Bullet;
import assets.ResourceLoader;
import interfaces.Shootable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SuperPlant extends Plant implements Shootable{

    public SuperPlant(double x, double y) {
        super(x, y, 6);
        this.plantCost = 100;
        Image plantImage = ResourceLoader.getSuperplantImage();
        shape = new ImageView(plantImage);
        shape.setFitWidth(60);  // Set the width and height
        shape.setFitHeight(60);
        shape.setX(x);  // Set the X position of the plant
        shape.setY(y);  // Set the Y position of the plan
    }

    @Override
    public void update(List<Bullet> bullets,Pane root) {
        long currentTime = System.nanoTime();
        if (currentTime - lastShootTime > SHOOT_INTERVAL / 2) { // Half the time for faster shooting
            shoot(bullets,root);
            lastShootTime = currentTime;
        }
    }
    
    public void shoot(List<Bullet> bullets,Pane root) {
        // Shoot a bullet from the plant's position
        Bullet bullet = new Bullet(x + 40, y + 20, 5);  // Starts from the right of the plant
        bullets.add(bullet);
        root.getChildren().add(bullet.getShape());
    }
}
