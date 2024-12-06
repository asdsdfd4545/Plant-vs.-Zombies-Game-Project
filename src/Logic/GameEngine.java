package Logic;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private List<GameObject> gameObjects;

    public GameEngine() {
        gameObjects = new ArrayList<>();
    }

    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void update() {
        for (GameObject obj : gameObjects) {
            obj.update();
        }
    }
}
