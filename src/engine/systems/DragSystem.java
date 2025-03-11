package engine.systems;

import engine.components.ComponentTag;
import engine.components.TransformComponent;
import engine.gameworld.GameObject;
import engine.support.Vec2d;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DragSystem implements ISystem{
  Map<String, GameObject> gameObjects;

  public DragSystem(Map<String, GameObject> gameObjects){
    this.gameObjects = gameObjects;
  }

  @Override
  public void tick(long nanosSinceLastTick) {

  }

  @Override
  public void lateTick() {

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
    this.gameObjects.put(label, go);
  }

  @Override
  public void onMousePressed(Vec2d pos) {
    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.COLLISION)){
        go.getComponent(ComponentTag.COLLISION).onMousePressed(pos);
      }
    }
  }

  @Override
  public void onMouseReleased(Vec2d pos) {
    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.COLLISION)){
        go.getComponent(ComponentTag.COLLISION).onMouseReleased(pos);
      }
    }
  }

  @Override
  public void onMouseDragged(Vec2d pos) {
    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.COLLISION)){
        go.getComponent(ComponentTag.COLLISION).onMouseDragged(pos);
      }
    }
  }

  @Override
  public void onMouseClicked(Vec2d pos) {
    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.COLLISION)){
        go.getComponent(ComponentTag.COLLISION).onMouseClicked(pos);
      }
    }
  }

  @Override
  public void onMouseMoved(Vec2d pos) {
    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.COLLISION)){
        go.getComponent(ComponentTag.COLLISION).onMouseMoved(pos);
      }
    }
  }

  @Override
  public void removeGameObjects(String label){
    this.gameObjects.remove(label);
  }
}
