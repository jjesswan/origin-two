package engine.components;

import engine.collisionShapes.PolygonEdge;
import engine.support.Vec2d;
import engine.utils.PolygonBuilder;
import engine.utils.SizeConstants;
import engine.utils.SpriteReferenceLoader;
import engine.utils.Types.AABBBoundingBox;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TransformComponent implements IComponent{
  private Vec2d position;
  private Vec2d size;
  private Vec2d dimensionRatio;
  private Vec2d bounds = null;
  private Vec2d[] polyPoints = new Vec2d[0];
  private List<PolygonBuilder> polygonBuilder = new ArrayList<>();

  // everything is in game space !!!!
  public TransformComponent(Vec2d initialPos, Vec2d initialSize){
    this.position = initialPos;
    this.size = initialSize;
    this.dimensionRatio = initialSize.pdiv(SizeConstants.INIT_WINDOW_SIZE);
  }

  // for polygon components
  public TransformComponent(Vec2d initialPos, Vec2d initialSize, List<PolygonBuilder> polygonBuilder){
    this.position = initialPos;
    this.size = initialSize;
    this.dimensionRatio = initialSize.pdiv(SizeConstants.INIT_WINDOW_SIZE);
    this.polygonBuilder = polygonBuilder;
  }

  public void addPolygonBuilder(PolygonBuilder polygonBuilder){
    this.polygonBuilder.add(polygonBuilder);
  }

  public void addPolygonBuilders(List<PolygonBuilder> polygonBuilder){
    this.polygonBuilder.addAll(polygonBuilder);
  }

  public void translatePos(Vec2d translation){
    if (this.polygonBuilder != null){
      for (PolygonBuilder builder : this.polygonBuilder){
        builder.updatePolygonCollider(translation, this.bounds);
      }
    }

    Vec2d newpos = this.position.plus(translation);
    this.updatePos(newpos);


  }

  public void updatePos(Vec2d newPos){
    if (this.bounds != null && newPos.x <= this.bounds.x && newPos.y <= this.bounds.y && newPos.x >= 0 && newPos.y >= 0){
      this.position = newPos;
    } else if (this.bounds == null){
      this.position = newPos;
    }

  }

  public void updateSize(Vec2d newSize){
    this.size = newSize;
  }

  public void setTravelBounds(Vec2d bounds){
    this.bounds = bounds;
  }

  public void resize(Vec2d newSize){
    this.size = this.dimensionRatio.pmult(newSize);

  }

  public Vec2d getPosition(){
    return this.position;
  }

  public Vec2d getSize(){
    return this.size;
  }

  public AABBBoundingBox getAABBClickBounds(){
    Vec2d min = new Vec2d(this.position.x, this.position.y);
    Vec2d max = new Vec2d(this.position.x + this.size.x, this.position.y +this.size.y);

    AABBBoundingBox bounds = new AABBBoundingBox();
    bounds.min = min;
    bounds.max = max;
    return bounds;
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
    return ComponentTag.TRANSFORM;
  }
}
