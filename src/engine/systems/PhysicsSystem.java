package engine.systems;

import engine.components.CollisionComponent;
import engine.components.ComponentTag;
import engine.components.PhysicsComponent;
import engine.gameworld.GameObject;
import engine.support.Vec2d;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class PhysicsSystem implements ISystem{
  Map<String, GameObject> gameObjects;
  private CollisionSystem collisionSystem;
  private int count = 0;
  private long snapshotTime = 0;

 public PhysicsSystem(Map<String, GameObject> gameObjects, CollisionSystem collisionSystem){
    this.gameObjects = gameObjects;
    this.collisionSystem = collisionSystem;
  }

  @Override
  public void tick(long nanosSinceLastTick) {

    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.PHYSICS)){
        ((PhysicsComponent) go.getComponent(ComponentTag.PHYSICS)).applyGravity(new Vec2d(0, 100));
        ((PhysicsComponent) go.getComponent(ComponentTag.PHYSICS)).physicsTick(.025);
      }
    }

    // resolve collisions multiple times
//    for (int i = 0; i < 1; i++){
//      this.collisionSystem.tick(nanosSinceLastTick);
//    }

  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public SystemTag getTag() {
    return SystemTag.PHYSICS_SYSTEM;
  }

  @Override
  public void updateGameObjects(String label, GameObject go) {

  }

  @Override
  public void removeGameObjects(String label){
    this.gameObjects.remove(label);
  }
}
