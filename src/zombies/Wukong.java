package zombies;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wukong extends Kappa{

	public Wukong(double x, double y) {
		super(x, y);
		this.setHealth(9);
		this.setSpeed(2);
		
		animations = new HashMap<>();
        animations.put("walk", new Image[] {
        	new Image(getClass().getResource("/res/wukong_walk1.png").toExternalForm()),
            new Image(getClass().getResource("/res/wukong_walk2.png").toExternalForm())
        	});
        animations.put("hitted", new Image[] {
                new Image(getClass().getResource("/res/wukong_hit2.png").toExternalForm()),
                new Image(getClass().getResource("/res/wukong_hit1.png").toExternalForm())
        	});
        animations.put("attack", new Image[] {
                new Image(getClass().getResource("/res/wukong_attack1.png").toExternalForm()),
                new Image(getClass().getResource("/res/wukong_attack2.png").toExternalForm()),
                new Image(getClass().getResource("/res/wukong_attack3.png").toExternalForm()),
                new Image(getClass().getResource("/res/wukong_attack4.png").toExternalForm())
            });
        animations.put("dead", new Image[] {
                new Image(getClass().getResource("/res/wukong_dead.png").toExternalForm())
            });

        shape = new ImageView(animations.get("walk")[0]);
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
	}

}
