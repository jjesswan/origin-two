package engine.components;

import o2.BlackBoard;
import engine.collisionShapes.AABCollisionShape;
import engine.collisionShapes.CircleCollisionShape;
import engine.collisionShapes.CollisionShape;
import engine.collisionShapes.PolygonCollisionShape;
import engine.support.Vec2d;
import engine.utils.PolygonBuilder;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CollisionComponent implements IComponent {
  private TransformComponent transform;
  private CollisionShape shape;
  private Vec2d clickLocation = new Vec2d(0);
  private boolean mousePressed, mouseHover = false;
  private boolean hasMouseInteraction = false;
  private CollisionBehaviorType collisionBehavior;
  protected List<String> collidedWith = new ArrayList<>();
  private List<Vec2d> allMTVs = new ArrayList<>();
  private CollisionShapeType collisionShapeType;

  public CollisionComponent(CollisionShapeType shapeType, TransformComponent transform, CollisionBehaviorType collisionBehavior){
    this.transform = transform;
    this.collisionBehavior = collisionBehavior;
    this.collisionShapeType = shapeType;
    switch(shapeType){
      case AAB:
        this.shape = new AABCollisionShape(shapeType, transform);
        break;
      case CIRCLE:
        this.shape = new CircleCollisionShape(shapeType, transform);
        break;
      case POLY:
        System.err.println("NEED POLYGON BUILDER TO INTIIALIZER POLYGON COLLISION COMPONENT");
      default:
        this.shape = new AABCollisionShape(shapeType, transform);
        break;
    }
  }

  public CollisionComponent(TransformComponent transform, CollisionBehaviorType collisionBehavior, PolygonBuilder polygonBuilder){
    this.transform = transform;
    this.collisionBehavior = collisionBehavior;
    this.collisionShapeType = CollisionShapeType.POLY;
    this.shape = new PolygonCollisionShape(this.collisionShapeType, transform, polygonBuilder);
  }

  public CollisionComponent(Element element, TransformComponent transform, BlackBoard blackBoard){
    this.collisionBehavior = CollisionBehaviorType.valueOf(element.getAttribute("behavior"));
    CollisionShapeType shapeType = CollisionShapeType.valueOf(element.getAttribute("shapeType"));
    this.collisionShapeType = shapeType;
    this.transform = transform;

    switch(shapeType){
      case AAB:
        this.shape = new AABCollisionShape(shapeType, transform);
        break;
      case CIRCLE:
        this.shape = new CircleCollisionShape(shapeType, transform);
        break;
      default:
        this.shape = new AABCollisionShape(shapeType, transform);
        break;
    }
  }

  public CollisionShapeType getCollisionShapeType(){
    return this.collisionShapeType;
  }

  @Override
  public void tick(long nanosSinceLastTick) {
    this.onCollide();
  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.COLLISION;
  }

  public CollisionShape getCollisionShape(){
    return this.shape;
  }

  public CollisionBehaviorType getCollisionBehavior(){
    return this.collisionBehavior;
  }

  public void collided(String collideWith){
    this.collidedWith.add(collideWith);
  }

  public void removeCollision(String collideWith){
    if (this.collidedWith.contains(collideWith)) this.collidedWith.remove(collideWith);
  }

  public List<String> getCurrentCollisions(){
    return this.collidedWith;
  }

  // custom collision resolution
  public void onCollide(){
    //this.collidedWith.clear();
  }

  public void resolveCollision(Vec2d mtv){
    this.allMTVs.add(mtv);
    this.transform.translatePos(mtv);
  }

  public boolean checkGrounded(){
    for (Vec2d mtv : this.allMTVs){
      if (Math.abs(mtv.dot(new Vec2d(0, -1))) > 0){
        return true;
      }
    }
    return false;
  }

  public void clearCollisions(){
    this.allMTVs = new ArrayList<>();
  }

//  public boolean searchCurrentCollisions(String id){
//    //System.out.println("collided with: " + this.collidedWith);
//    for (String key : this.collidedWith){
//      if (key.equals(id) || key.contains(id)) return true;
//    }
//
//    return false;
//  }


  // where pressPos is in GAME SPACE
  @Override
  public void onMousePressed(Vec2d pressPos) {
    // check that mouse is pressed within bounds of game object --> convert from screen space to game space
    if (this.getCollisionShape().collidePoint(pressPos) && this.hasMouseInteraction) {
      this.mousePressed = true;
      this.clickLocation = pressPos;
    }
  }

  @Override
  public void onMouseMoved(Vec2d pressPos) {
    // check that mouse is pressed within bounds of game object --> convert from screen space to game space
    if (this.getCollisionShape().collidePoint(pressPos)){
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
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    component.setAttribute("shapeType", this.collisionShapeType.name());
    component.setAttribute("behavior", this.collisionBehavior.name());
    return component;
  }
}
