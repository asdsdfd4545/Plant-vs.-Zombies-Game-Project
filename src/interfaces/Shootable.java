package interfaces;

import java.util.List;

import Logic.Bullet;
import javafx.scene.layout.Pane;

public interface Shootable {
	void shoot(List<Bullet> bullets,Pane root);
}
