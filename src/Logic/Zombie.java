package Logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Zombie {
    private double x, y;
    private double speed;
    private int health;
    private ImageView shape;

    public Zombie(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = 3; // Example health value

        // Zombie image (customize accordingly)
        Image zombieImage = new Image(getClass().getResource("/res/zombie.png").toExternalForm());
        shape = new ImageView(zombieImage);
        shape.setFitWidth(40);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
    }

    public void update() {
        // Move the zombie to the left
        x -= speed;
        shape.setX(x);
    }

    public void takeDamage() {
        health--;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ImageView getShape() {
        return shape;
    }

    public void setShape(ImageView shape) {
        this.shape = shape;
    }
}
