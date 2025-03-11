package engine.components;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class CollisionGroupComponent implements IComponent {
  List<CollisionComponent> components;

  public CollisionGroupComponent(){
    this.components = new ArrayList<>();
  }

  public CollisionGroupComponent(List<CollisionComponent> components){
    this.components = components;
  }

  public void addCollisionComponent(CollisionComponent component){
    this.components.add(component);
  }

  public List<CollisionComponent> getAllCollisionComponents(){
    return this.components;
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
  public ComponentTag getTag() {
    return ComponentTag.COLLISION_GROUP;
  }
}
