package Logic;

import javafx.scene.image.ImageView;

public class Zombie extends GameObject {
    private double speed;
    private int health; // จำนวนครั้งที่โดนโจมตีก่อนตาย

    public Zombie(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
        this.health = 5; // ต้องโดนกระสุน 5 ครั้งจึงตาย
    }

    public void takeDamage() {
        health--;
    }

    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void update() {
        setX(getX() - speed); // เลื่อนตำแหน่งในแนวนอน
    }
}
