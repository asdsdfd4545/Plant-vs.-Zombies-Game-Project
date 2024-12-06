package Logic;

import javafx.scene.image.ImageView;

public class Bullet extends GameObject {
    private double speed;

    public Bullet(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
    }

    @Override
    public void update() {
        setX(getX() + speed); // เลื่อนตำแหน่งในแนวนอน
    }
}
