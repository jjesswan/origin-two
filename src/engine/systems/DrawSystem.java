package engine.systems;

import engine.components.CollisionComponent;
import engine.components.ComponentTag;
import engine.components.IComponent;
import engine.components.TransformComponent;
import engine.gameworld.GameObject;
import engine.screen.Viewport;
import engine.support.Vec2d;
import engine.utils.ZComparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class DrawSystem implements ISystem{
  private Map<String, GameObject> gameObjects;
  private TreeSet<GameObject> zTree;

  public DrawSystem(Map<String, GameObject> gameObjects){
    this.gameObjects = gameObjects;
    this.zTree = new TreeSet<>(new ZComparator());
    this.zTree.addAll(this.gameObjects.values());
  }

  @Override
  public void tick(long nanosSinceLastTick) {

  }

  @Override
  public void lateTick() {

  }


  @Override
  public void draw(GraphicsContext g) {
    for (GameObject go : this.zTree){
      // hover mouse visual effect
      if (go.hasComponent(ComponentTag.COLLISION)){
        if (((CollisionComponent)go.getComponent(ComponentTag.COLLISION)).getIsMouseHover()){
          ColorAdjust blackout = new ColorAdjust();
          blackout.setBrightness(-.5);
          g.setEffect(blackout);
        }
      }

      // draws either sprites or shapes
      for (IComponent component : go.getAllComponents().values()){
        component.draw(g);
      }
      g.setEffect(null);
    }
  }

  @Override
  public SystemTag getTag() {
    return SystemTag.DRAW_SYSTEM;
  }

  @Override
  public void updateGameObjects(String label, GameObject go) {
    this.gameObjects.put(label, go);
    this.zTree.add(go);
  }

  @Override
  public void removeGameObjects(String label){
    this.gameObjects.remove(label);
    this.zTree = new TreeSet<>(new ZComparator());
    this.zTree.addAll(this.gameObjects.values());
  }

}
