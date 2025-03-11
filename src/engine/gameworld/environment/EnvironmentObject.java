package engine.gameworld.environment;

import engine.components.CollisionComponent;
import engine.components.CollisionGroupComponent;
import engine.components.ComponentTag;
import engine.components.DrawComponent;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import engine.utils.PolygonBuilder;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EnvironmentObject {
  private GameObject go;

  /**
   * Environment object with just image.
   * @param filename
   * @param pos
   * @param size
   * @param zIndex
   * @param id
   * @param gameWorld
   */
  public EnvironmentObject(String filename, Vec2d pos, Vec2d size, int zIndex, boolean origSize,
      String id, GameWorld gameWorld){
   this.go = new GameObject(pos, size, zIndex, id);
    go.addComponent(new DrawComponent(filename, go.getTransform(), origSize));
    gameWorld.updateGameObjects(id, go);
  }

  /**
   * Collidable, STATIC environment object.
   * @param filename
   * @param pos
   * @param size
   * @param zIndex
   * @param id
   * @param allRelativePoints
   * @param mass
   * @param restitution
   * @param gameWorld
   */
  public EnvironmentObject(String filename, Vec2d pos, Vec2d size,
      int zIndex, boolean origSize,
      String id, List<Vec2d[]> allRelativePoints,
      double mass, double restitution, GameWorld gameWorld){

    this.go = new GameObject(pos, size, zIndex, id);
    DrawComponent drawComponent = new DrawComponent(filename, go.getTransform(), origSize);
    go.addComponent(drawComponent);
    go.addComponent(new PhysicsComponent(go.getTransform(), mass, restitution, CollisionBehaviorType.STATIC));
    go.addComponent(new CollisionGroupComponent());

    List<PolygonBuilder> polygonBuilders = new ArrayList<>();
    for (Vec2d[] points : allRelativePoints){
      PolygonBuilder polygonBuilder = new PolygonBuilder(go.getTransform().getPosition(), go.getTransform()
          .getSize(), drawComponent, points);
      polygonBuilders.add(polygonBuilder);
    }

    go.addPolygonBuilders(polygonBuilders);

    for (PolygonBuilder polygonBuilder : polygonBuilders){
      ((CollisionGroupComponent)go.getComponent(ComponentTag.COLLISION_GROUP)).addCollisionComponent(
          new CollisionComponent(go.getTransform(), CollisionBehaviorType.STATIC, polygonBuilder));
    }

    gameWorld.updateGameObjects(id, go);
  }

  public void setOpacity(double opacity){
    ((DrawComponent)this.go.getComponent(ComponentTag.DRAW)).setOpacity(opacity);

  }


}
