package zombies;

import java.util.HashMap;

import Logic.ZomieState;
import assets.ResourceLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wukong extends Kappa{

	public Wukong(double x, double y) {
		super(x, y);
		this.setHealth(9);
		this.setSpeed(2);
		
		animations = new HashMap<>();
        animations.put(ZomieState.WALK, new Image[] {
        	ResourceLoader.getImage("Wukong_Walk1Image"),
            ResourceLoader.getImage("Wukong_Walk2Image")
        	});
        animations.put(ZomieState.HITTED, new Image[] {
        		ResourceLoader.getImage("Wukong_Hit2Image"),
        		ResourceLoader.getImage("Wukong_Hit1Image")
        	});
        animations.put(ZomieState.ATTACK, new Image[] {
                ResourceLoader.getImage("Wukong_Attack1Image"),
                ResourceLoader.getImage("Wukong_Attack2Image"),
                ResourceLoader.getImage("Wukong_Attack3Image"),
                ResourceLoader.getImage("Wukong_Attack4Image")
            });
        animations.put(ZomieState.DEAD, new Image[] {
                ResourceLoader.getImage("Wukong_DeadImage")
            });

        shape = new ImageView(animations.get(ZomieState.WALK)[0]);
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
	}

}
