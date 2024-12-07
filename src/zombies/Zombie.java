package zombies;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Zombie {
    private double x, y;
    private double speed;
    private int health;
    private ImageView shape;
    private Map<String, Image[]> animations; // เก็บแอนิเมชันทั้งหมด
    private String currentState = "walk"; // สถานะแอนิเมชันปัจจุบัน
    private int frameIndex = 0; // เฟรมปัจจุบัน
    private long lastFrameTime = 0; // เวลาเฟรมสุดท้าย
    private long animationSpeed = 300_000_000; // ความเร็วแอนิเมชัน (150ms ต่อเฟรม)

    public Zombie(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = 3; // Example health value
        
        animations = new HashMap<>();
        animations.put("walk", new Image[] {
        	new Image(getClass().getResource("/res/kappa_walk1.png").toExternalForm()),
            new Image(getClass().getResource("/res/kappa_walk2.png").toExternalForm())
        	});
        animations.put("hitted", new Image[] {
                new Image(getClass().getResource("/res/kappa_hit2.png").toExternalForm()),
                new Image(getClass().getResource("/res/kappa_hit1.png").toExternalForm())
        	});
        animations.put("attack", new Image[] {
                new Image(getClass().getResource("/res/kappa_attack1.png").toExternalForm()),
                new Image(getClass().getResource("/res/kappa_attack2.png").toExternalForm()),
                new Image(getClass().getResource("/res/kappa_attack3.png").toExternalForm()),
                new Image(getClass().getResource("/res/kappa_attack4.png").toExternalForm())
            });
        animations.put("dead", new Image[] {
                new Image(getClass().getResource("/res/kappa_dead.png").toExternalForm())
            });

        shape = new ImageView(animations.get("walk")[0]);
        shape.setFitWidth(40);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
    }
    private void startAnimation() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastFrameTime >= animationSpeed) {
                    // เปลี่ยนเฟรมของสถานะปัจจุบัน
                    Image[] currentFrames = animations.get(currentState);
                    if (currentFrames != null) {
                        frameIndex = (frameIndex + 1) % currentFrames.length;
                        shape.setImage(currentFrames[frameIndex]);
                    }
                    lastFrameTime = now;

                    // ถ้าสถานะคือ dead และถึงเฟรมสุดท้ายแล้วหยุดแอนิเมชัน
                    if (currentState.equals("dead") && frameIndex == currentFrames.length - 1) {
                        stop(); // หยุด AnimationTimer
                    }
                }
            }
        };
        animationTimer.start();
    }

    public void update() {
        // ถ้า Zombie ยังไม่ตายให้เดิน
        if (!currentState.equals("dead")) {
            if (health < 3) { // ตัวอย่าง: zombie โดนโจมตีแล้ว
                setState("hitted");
            } 
    
            else {
                setState("walk");
            }
            x -= speed;
            shape.setX(x);
        }
    }
    public void setState(String newState) {
        // เปลี่ยนสถานะแอนิเมชัน
        if (!currentState.equals(newState)) {
            currentState = newState;
            frameIndex = 0; // รีเซ็ตเฟรม
        }
    }


    public void takeDamage() {
        health--;
        if (health <= 0) {
            setState("dead");
        } else {
            setState("hitted");
        }
    }

    public boolean isDead() {
    	return currentState.equals("dead");
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
