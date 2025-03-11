package engine.components;

import engine.support.Vec2d;
import engine.utils.Types.AABBBoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DragComponent implements IComponent {
  private TransformComponent transform;
  private Vec2d clickLocation = new Vec2d(0);
  private boolean mousePressed, mouseHover = false;

  public DragComponent(TransformComponent transform){
    this.transform = transform;

  }

  // where pressPos is in GAME SPACE
  @Override
  public void onMousePressed(Vec2d pressPos) {
    // check that mouse is pressed within bounds of game object --> convert from screen space to game space
    AABBBoundingBox bounds = this.transform.getAABBClickBounds();
    if (bounds.min.x <= pressPos.x && pressPos.x <= bounds.max.x
        && bounds.min.y <= pressPos.y && pressPos.y <= bounds.max.y) {
      this.mousePressed = true;
      this.clickLocation = pressPos;
    }
  }


  public void helloo(){

  }

  @Override
  public void onMouseMoved(Vec2d pressPos) {
    // check that mouse is pressed within bounds of game object --> convert from screen space to game space
    AABBBoundingBox bounds = this.transform.getAABBClickBounds();
    if (bounds.min.x <= pressPos.x && pressPos.x <= bounds.max.x
        && bounds.min.y <= pressPos.y && pressPos.y <= bounds.max.y) {
      this.mouseHover = true;
    } else {
      this.mouseHover = false;
    }
  }

  public boolean getIsMouseHover(){
    return this.mouseHover;
  }

  @Override
  public void onMouseReleased(Vec2d releasePos) {
    this.mousePressed = false;
  }

  @Override
  public void onMouseDragged(Vec2d mousePos) {
    if (this.mousePressed) {
      Vec2d translation = mousePos.minus(this.clickLocation);
      this.transform.translatePos(translation);
      this.clickLocation = mousePos;
    }
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
    return ComponentTag.DRAG;
  }
}
