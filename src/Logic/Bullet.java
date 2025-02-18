package Logic;

import assets.ResourceLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
    private double x, y;
    private ImageView shape;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;

        // Assuming you have a bullet image
        Image bulletImage = ResourceLoader.getImage("BulletImage");
        shape = new ImageView(bulletImage);
        shape.setFitWidth(10);  // Set the bullet width
        shape.setFitHeight(20); // Set the bullet height
        shape.setX(x);
        shape.setY(y);
    }

    public void update() {
        x += 5;  // Move the bullet to the right (you can adjust the speed)
        shape.setX(x);
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
}
