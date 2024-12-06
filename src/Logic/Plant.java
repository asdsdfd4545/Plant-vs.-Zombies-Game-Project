package Logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.List;

public class Plant {
    private double x, y;
    private ImageView shape;
    private long lastShootTime;
    private static final long SHOOT_INTERVAL = 1_000_000_000L; // 1 second in nanoseconds
    private int PlantCost = 100;

    public Plant(double x, double y) {
        this.x = x;
        this.y = y;
        this.lastShootTime = System.nanoTime();

        // Plant image (use a placeholder or create your own)
        Image plantImage = new Image(getClass().getResource("/res/plant.png").toExternalForm());
        shape = new ImageView(plantImage);
        shape.setFitWidth(40);
        shape.setFitHeight(40);
        shape.setX(x);
        shape.setY(y);
    }

    public ImageView getShape() {
        return shape;
    }

    // Called in game loop to update plant state
    public void update(List<Bullet> bullets, Pane root) {
        if (System.nanoTime() - lastShootTime > SHOOT_INTERVAL) {
            shoot(bullets, root);
            lastShootTime = System.nanoTime(); // Reset last shoot time
        }
    }

    private void shoot(List<Bullet> bullets, Pane root) {
        Bullet bullet = new Bullet(x + 40, y + 20, 5); // Starts from the right of the plant
        bullets.add(bullet);
        root.getChildren().add(bullet.getShape());
    }

	public int getPlantCost() {
		return PlantCost;
	}
    
}
