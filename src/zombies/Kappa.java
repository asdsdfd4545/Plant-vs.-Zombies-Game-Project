package zombies;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Kappa {
	protected double x, y;
    protected double speed;
    protected int health;
    protected ImageView shape;
    protected Map<String, Image[]> animations; // เก็บแอนิเมชันทั้งหมด
    protected String currentState = "walk"; // สถานะแอนิเมชันปัจจุบัน
    protected int frameIndex = 0; // เฟรมปัจจุบัน
    protected long lastFrameTime = 0; // เวลาเฟรมสุดท้าย
    protected long animationSpeed = 300_000_000; // ความเร็วแอนิเมชัน (150ms ต่อเฟรม)
    private javafx.animation.Timeline attackTimeline;


    public Kappa(double x, double y) {
        this.x = x;
        this.y = y;
        this.setSpeed(1);
        this.setHealth(3);
        
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
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
    }
    
    public void startAnimation() {
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
        // ถ้า Kappa ยังไม่ตายให้เดิน
        if (!currentState.equals("dead")&&!isAttacking()) {
            if (health < 3) { // ตัวอย่าง: Kappa โดนโจมตีแล้ว
                setState("hitted");
            } 
    
            else {
                setState("walk");
            }
            x -= speed;
            shape.setX(x);
        }


    }
    public void stopMovement() {
        this.setSpeed(0); // ตั้งค่าความเร็วเป็น 0 เพื่อหยุดการเคลื่อนที่

    }
    public void startMovement() {
        setState("walk"); // ถ้าไม่โจมตี เปลี่ยนสถานะเป็นเดิน
        this.setSpeed(1);
    }
    public void setAttackTimeline(javafx.animation.Timeline timeline) {
        this.attackTimeline = timeline;
    }


    
    public void setState(String newState) {
        // เปลี่ยนสถานะแอนิเมชัน
        if (!currentState.equals(newState)) {
            currentState = newState;
            frameIndex = 0; // รีเซ็ตเฟรม
        }
    }
    public boolean isAttacking() {
        return "attack".equals(currentState);
    }


    public void takeDamage() {
        health--;
        if (health <= 0) {
            setState("dead");
        } else {
        	if(!isAttacking()) {
        		setState("hitted");
        	}
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
    
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Object getAttackTimeline() {
		// TODO Auto-generated method stub
		return this.attackTimeline;
	}


	
    
    

}
