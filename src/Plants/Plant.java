package Plants;

import java.util.List;

import Logic.Bullet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Plant {
    protected double x, y;
    protected ImageView shape;
    protected long lastShootTime;
    protected static final long SHOOT_INTERVAL = 1000000000L; // 1 second in nanoseconds
    protected int plantCost;
    private int health;

    public Plant(double x, double y, int initialHealth) {
        this.x = x;
        this.y = y;
        this.health = initialHealth;
        this.lastShootTime = System.nanoTime();

    }

    public ImageView getShape() {
        return shape;
    }

    public int getPlantCost() {
        return plantCost;
    }
    public void takeDamage() {
    	if (health > 0) {
            health--;
//            System.out.println("Plant took damage! Current health: " + health);
    	}
    }

    public boolean isDead() {
        return health <= 0;
    }

    public abstract void update(List<Bullet> bullets,Pane root);

}
