package zombies;

import java.util.HashMap;

import assets.ResourceLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Berserker extends Kappa{

	public Berserker(double x, double y) {
		super(x, y);
		this.setHealth(14);
		this.setSpeed(0.8);
		
        animations = new HashMap<>();
        animations.put("walk", new Image[] {
        	ResourceLoader.getImage("Berserker_Walk1Image"),
            ResourceLoader.getImage("Berserker_Walk2Image")
        	});
        animations.put("hitted", new Image[] {
        		ResourceLoader.getImage("Berserker_Hit1Image"),
        		ResourceLoader.getImage("Berserker_Hit2Image")
        	});
        animations.put("attack", new Image[] {
                ResourceLoader.getImage("Berserker_Attack1Image"),
                ResourceLoader.getImage("Berserker_Attack2Image"),
                ResourceLoader.getImage("Berserker_Attack3Image"),
                ResourceLoader.getImage("Berserker_Attack4Image")
            });
        animations.put("dead", new Image[] {
                ResourceLoader.getImage("Berserker_DeadImage")
            });

        shape = new ImageView(animations.get("walk")[0]);
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
	}

}
