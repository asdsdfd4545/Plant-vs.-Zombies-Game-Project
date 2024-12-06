package Logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
    private double x, y;
    private ImageView shape;
    private double speed;

    public Bullet(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        // Bullet image (use a placeholder or create your own)
        Image bulletImage = new Image(getClass().getResource("/res/bullet.png").toExternalForm());
        shape = new ImageView(bulletImage);
        shape.setFitWidth(10);
        shape.setFitHeight(10);
        shape.setX(x);
        shape.setY(y);
    }

    public ImageView getShape() {
        return shape;
    }

    public double getX() {
        return x;
    }

    public void update() {
        x += speed; // Move bullet to the right
        shape.setX(x);
    }
}
