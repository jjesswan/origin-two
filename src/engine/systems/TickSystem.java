package engine.systems;

import engine.components.ComponentTag;
import engine.components.IComponent;
import engine.gameworld.GameObject;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class TickSystem implements ISystem{

  Map<String, GameObject> gameObjects;

  public TickSystem(Map<String, GameObject> gameObjects){
    this.gameObjects = gameObjects;
  }

  @Override
  public void tick(long nanosSinceLastTick) {
    for (GameObject go : this.gameObjects.values()){
      for (IComponent component : go.getAllComponents().values()){
        component.tick(nanosSinceLastTick);
      }
    }
  }

  @Override
  public void lateTick() {
    for (GameObject go : this.gameObjects.values()){
      if (go.getCenterComponent() != null){
        go.getCenterComponent().lateTick();
      }
      for (IComponent component : go.getAllComponents().values()){
        component.lateTick();
      }
    }
  }

  @Override
  public void onKeyPress(KeyEvent e){
    for (GameObject go : this.gameObjects.values()){
      for (IComponent component : go.getAllComponents().values()){
        component.onKeyPress(e);
      }
    }
  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public SystemTag getTag() {
    return SystemTag.TICK_SYSTEM;
  }

  @Override
  public void updateGameObjects(String label, GameObject go) {

  }

  @Override
  public void removeGameObjects(String label){
    this.gameObjects.remove(label);
  }
}
