package zombies;

import java.util.HashMap;

import assets.ResourceLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wukong extends Kappa{

	public Wukong(double x, double y) {
		super(x, y);
		this.setHealth(9);
		this.setSpeed(2);
		
		animations = new HashMap<>();
        animations.put("walk", new Image[] {
        	ResourceLoader.getWukong_walk1Image(),
            ResourceLoader.getWukong_walk2Image()
        	});
        animations.put("hitted", new Image[] {
        		ResourceLoader.getWukong_hit2Image(),
        		ResourceLoader.getWukong_hit1Image()
        	});
        animations.put("attack", new Image[] {
                ResourceLoader.getWukong_attack1Image(),
                ResourceLoader.getWukong_attack2Image(),
                ResourceLoader.getWukong_attack3Image(),
                ResourceLoader.getWukong_attack4Image()
            });
        animations.put("dead", new Image[] {
                ResourceLoader.getWukong_deadImage()
            });

        shape = new ImageView(animations.get("walk")[0]);
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
	}

}
