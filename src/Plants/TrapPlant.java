package Plants;

import java.util.List;

import Logic.Bullet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TrapPlant extends Plant {

    public TrapPlant(double x, double y) {
        super(x, y);
        this.plantCost = 20;
        Image plantImage = new Image(getClass().getResource("/res/trapplant.png").toExternalForm());
        shape = new ImageView(plantImage);
        shape.setFitWidth(60);  // Set the width and height
        shape.setFitHeight(60);
        shape.setX(x);  // Set the X position of the plant
        shape.setY(y);  // Set the Y position of the plan
    }

    @Override
    public void update(List<Bullet> bullets,Pane root) {
        // TrapPlant doesn't shoot any bullets.
    }
    
    public void shoot(List<Bullet> bullets,Pane root) {

    }
}
