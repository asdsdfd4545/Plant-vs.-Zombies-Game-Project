package Logic;

import javafx.scene.image.ImageView;

public abstract class GameObject {
    protected double x, y;
    protected ImageView shape; // ใช้ ImageView เป็นตัวแทนกราฟิก

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
        if (shape != null) {
            shape.setX(x); // อัปเดตตำแหน่งกราฟิก
        }
    }

    public void setY(double y) {
        this.y = y;
        if (shape != null) {
            shape.setY(y); // อัปเดตตำแหน่งกราฟิก
        }
    }

    public ImageView getShape() {
        return shape;
    }

    public void setShape(ImageView shape) {
        this.shape = shape;
    }
}
