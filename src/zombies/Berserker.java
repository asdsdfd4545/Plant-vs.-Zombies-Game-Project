package zombies;

import java.util.HashMap;

import Logic.ZombieState;
import assets.ResourceLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Berserker extends Kappa{

	public Berserker(double x, double y) {
		super(x, y);
		this.setHealth(14);
		this.setSpeed(0.8);
		
        animations = new HashMap<>();
        animations.put(ZombieState.WALK, new Image[] {
        	ResourceLoader.getImage("Berserker_Walk1Image"),
            ResourceLoader.getImage("Berserker_Walk2Image")
        	});
        animations.put(ZombieState.HITTED, new Image[] {
        		ResourceLoader.getImage("Berserker_Hit1Image"),
        		ResourceLoader.getImage("Berserker_Hit2Image")
        	});
        animations.put(ZombieState.ATTACK, new Image[] {
                ResourceLoader.getImage("Berserker_Attack1Image"),
                ResourceLoader.getImage("Berserker_Attack2Image"),
                ResourceLoader.getImage("Berserker_Attack3Image"),
                ResourceLoader.getImage("Berserker_Attack4Image")
            });
        animations.put(ZombieState.DEAD, new Image[] {
                ResourceLoader.getImage("Berserker_DeadImage")
            });

        shape = new ImageView(animations.get(ZombieState.WALK)[0]);
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
	}

}
