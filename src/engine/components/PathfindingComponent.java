//package engine.components;
//
//import o2.BlackBoard;
//import engine.ai.Pathfinder;
//import engine.support.Vec2d;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//import javafx.scene.canvas.GraphicsContext;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//public class PathfindingComponent implements IComponent{
//  private Pathfinder pathfinder;
//  private TransformComponent transform;
//  private List<Vec2d> pathway;
//
//  public PathfindingComponent(BlackBoard blackBoard, TransformComponent transform){
//    this.pathfinder = blackBoard.pathfinder;
//    this.transform = transform;
//    this.pathway = new ArrayList<>();
//  }
//
//  public PathfindingComponent(Element element, TransformComponent transform, BlackBoard blackBoard) {
//    this.pathfinder = blackBoard.pathfinder;
//    this.transform = transform;
//    this.pathway = new ArrayList<>();
//  }
//
//  public void setPathway(Vec2d B){
//    this.pathway = this.pathfinder.findPath(this.transform.getPosition(), B);
//  }
//
//  public void clearPathway(){
//    this.pathway = new ArrayList<>();
//  }
//
//  @Override
//  public void tick(long nanosSinceLastTick) {
//    if (!this.pathway.isEmpty()){
////      System.out.println("curr target: " + this.pathway.get(this.pathway.size() - 1));
////      System.out.println("curr pos: " + this.transform.getPosition());
//      Vec2d vector = this.pathway.get(this.pathway.size() - 1).minus(this.transform.getPosition());
//      //System.out.println("v: " + vector);
//
//      // move to next position point if entity is close by
//      if (vector.mag() < 2) {
//        //this.transform.updatePos(this.pathway.get(this.pathway.size() - 1));
//        this.pathway.remove(this.pathway.size() - 1);
//        return;
//      }
//      Vec2d dir = vector.normalize();
//      this.transform.translatePos(dir.smult(.1));
//    }
//
//  }
//
//  @Override
//  public void lateTick() {
//
//  }
//
//  @Override
//  public void draw(GraphicsContext g) {
//
//  }
//
//  @Override
//  public ComponentTag getTag() {
//    return ComponentTag.PATHFINDING;
//  }
//
//  @Override
//  public Element saveComponent(Document doc) {
//    Element component = doc.createElement("Component");
//    component.setAttribute("id", this.getTag().name());
//    return component;
//  }
//}
