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
        	ResourceLoader.getBerserker_walk1Image(),
            ResourceLoader.getBerserker_walk2Image()
        	});
        animations.put("hitted", new Image[] {
        		ResourceLoader.getBerserker_hit2Image(),
        		ResourceLoader.getBerserker_hit1Image()
        	});
        animations.put("attack", new Image[] {
                ResourceLoader.getBerserker_attack1Image(),
                ResourceLoader.getBerserker_attack2Image(),
                ResourceLoader.getBerserker_attack3Image(),
                ResourceLoader.getBerserker_attack4Image()
            });
        animations.put("dead", new Image[] {
                ResourceLoader.getBerserker_deadImage()
            });

        shape = new ImageView(animations.get("walk")[0]);
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
	}

}
