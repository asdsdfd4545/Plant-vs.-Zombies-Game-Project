package zombies;

import java.util.HashMap;
import java.util.Map;

import Logic.ZombieState;
import assets.ResourceLoader;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Kappa {
	protected double x, y;
    protected double speed;
    protected int health;
    protected ImageView shape;
    protected ZombieState currentState = ZombieState.WALK;
    protected Map<ZombieState, Image[]> animations; // เก็บแอนิเมชันทั้งหมด
//    protected String currentState = "walk"; // สถานะแอนิเมชันปัจจุบัน
    protected int frameIndex = 0; // เฟรมปัจจุบัน
    protected long lastFrameTime = 0; // เวลาเฟรมสุดท้าย
    protected long animationSpeed = 300_000_000; // ความเร็วแอนิเมชัน (150ms ต่อเฟรม)
    private javafx.animation.Timeline attackTimeline;

    public Kappa(double x, double y) {
        this.x = x;
        this.y = y;
        this.setHealth(7);
        this.setSpeed(1);
        
        animations = new HashMap<>();
        animations.put(ZombieState.WALK, new Image[] {
        	ResourceLoader.getImage("Kappa_Walk1Image"),
            ResourceLoader.getImage("Kappa_Walk2Image")
        	});
        animations.put(ZombieState.HITTED, new Image[] {
        		ResourceLoader.getImage("Kappa_Hit1Image"),
        		ResourceLoader.getImage("Kappa_Hit2Image")
        	});
        animations.put(ZombieState.ATTACK, new Image[] {
                ResourceLoader.getImage("Kappa_Attack1Image"),
                ResourceLoader.getImage("Kappa_Attack2Image"),
                ResourceLoader.getImage("Kappa_Attack3Image"),
                ResourceLoader.getImage("Kappa_Attack4Image")
            });
        animations.put(ZombieState.DEAD, new Image[] {
                ResourceLoader.getImage("Kappa_DeadImage")
            });

        shape = new ImageView(animations.get(ZombieState.WALK)[0]);
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
                    if (currentState.equals(ZombieState.DEAD) && frameIndex == currentFrames.length - 1) {
                        stop(); // หยุด AnimationTimer
                    }
                }
            }
        };
        animationTimer.start();
    }

    public void update() {
        // ถ้า Kappa ยังไม่ตายให้เดิน
        if (!currentState.equals(ZombieState.DEAD)&&!isAttacking()) {
            if (health < 3) { // ตัวอย่าง: Kappa โดนโจมตีแล้ว
                setState(ZombieState.HITTED);
            } 
    
            else {
                setState(ZombieState.WALK);
            }
            x -= speed;
            shape.setX(x);
        }


    }
    public void stopMovement() {
        this.setSpeed(0); // ตั้งค่าความเร็วเป็น 0 เพื่อหยุดการเคลื่อนที่

    }
    public void startMovement() {
        setState(ZombieState.WALK); // ถ้าไม่โจมตี เปลี่ยนสถานะเป็นเดิน
        this.setSpeed(1);
    }
    public void setAttackTimeline(javafx.animation.Timeline timeline) {
        this.attackTimeline = timeline;
    }
    
    public void setState(ZombieState state) {
        // เปลี่ยนสถานะแอนิเมชัน
        if (!currentState.equals(state)) {
            currentState = state;
            frameIndex = 0; // รีเซ็ตเฟรม
        }
    }
    public boolean isAttacking() {
        return ZombieState.ATTACK.equals(currentState);
    }

    public void takeDamage() {
        health--;
        if (health <= 0) {
            setState(ZombieState.DEAD);
        } else {
        	if(!isAttacking()) {
        		setState(ZombieState.HITTED);
        	}
        }
    }

    public boolean isDead() {
    	return currentState.equals(ZombieState.DEAD);
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
