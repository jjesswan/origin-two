package engine.components;

import engine.screen.Viewport;
import javafx.scene.canvas.GraphicsContext;

public class CenterComponent implements IComponent{
  private TransformComponent transform;
  private Viewport viewport;

  public CenterComponent(TransformComponent transform, Viewport viewport){
    this.transform = transform;
    this.viewport = viewport;
  }

  @Override
  public void tick(long nanosSinceLastTick) {
    //System.out.println("POSS: " + this.transform.getPosition());

  }

  @Override
  public void lateTick() {
    this.viewport.setViewportCenter(this.transform.getPosition().plus(0, -20));
  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.CENTER_FOCUS;
  }

}
