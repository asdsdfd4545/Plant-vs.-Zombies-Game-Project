package zombies;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Berserker extends Kappa{

	public Berserker(double x, double y) {
		super(x, y);
		this.setHealth(14);
		this.setSpeed(0.8);
		
		animations = new HashMap<>();
        animations.put("walk", new Image[] {
        	new Image(getClass().getResource("/res/berserker_walk1.png").toExternalForm()),
            new Image(getClass().getResource("/res/berserker_walk2.png").toExternalForm())
        	});
        animations.put("hitted", new Image[] {
                new Image(getClass().getResource("/res/berserker_hit2.png").toExternalForm()),
                new Image(getClass().getResource("/res/berserker_hit1.png").toExternalForm())
        	});
        animations.put("attack", new Image[] {
                new Image(getClass().getResource("/res/berserker_attack1.png").toExternalForm()),
                new Image(getClass().getResource("/res/berserker_attack2.png").toExternalForm()),
                new Image(getClass().getResource("/res/berserker_attack3.png").toExternalForm()),
                new Image(getClass().getResource("/res/berserker_attack4.png").toExternalForm())
            });
        animations.put("dead", new Image[] {
                new Image(getClass().getResource("/res/berserker_dead.png").toExternalForm())
            });

        shape = new ImageView(animations.get("walk")[0]);
        shape.setFitWidth(50);
        shape.setFitHeight(60);
        shape.setX(x);
        shape.setY(y);
        startAnimation();
	}

}
