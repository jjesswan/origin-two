package engine.systems;

import engine.components.ComponentTag;
import engine.gameworld.GameObject;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;

public class AISystem implements ISystem{
  Map<String, GameObject> gameObjects;

  public AISystem(Map<String, GameObject> gameObjects){
    this.gameObjects = gameObjects;
  }

  @Override
  public void tick(long nanosSinceLastTick) {


  }

  @Override
  public void lateTick() {
    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.AI)){
        go.getComponent(ComponentTag.AI).tick(1);
      }
    }

  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public SystemTag getTag() {
    return null;
  }

  @Override
  public void updateGameObjects(String label, GameObject go) {

  }

  @Override
  public void removeGameObjects(String label){
    this.gameObjects.remove(label);
  }
}
