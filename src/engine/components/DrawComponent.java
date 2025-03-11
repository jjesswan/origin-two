package engine.components;

import o2.BlackBoard;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class DrawComponent implements IComponent{

  private Image img;
  private TransformComponent transform;
  private String imgFilePath;
  private double alpha = 1;
  private boolean isNull = false;

  public DrawComponent(String imgFilepath, TransformComponent initTransform, boolean originalSize){
    this.imgFilePath = imgFilepath;
    this.transform = initTransform;
    if (originalSize)
      this.img = new Image(imgFilepath);
    else this.img = new Image(imgFilepath, transform.getSize().x, transform.getSize().y, true, false);

    // update transform comp's size to be the true size of the image
    this.transform.updateSize(new Vec2d(this.img.getWidth(), this.img.getHeight()));
  }

  // empty drawcomponent for overriding draw function
  public DrawComponent(){
    this.isNull = true;

  }

  public Vec2d getImageDimensions(){
    if (this.isNull) {
      System.err.println("WARNING: DRAW COMPONENT INITIALIZED WITH NULL VALUES");
      return null;
    }
    return new Vec2d(this.img.getWidth(), this.img.getHeight());
  }


  public DrawComponent(Element element, TransformComponent transform, BlackBoard blackBoard) {
    this.imgFilePath = element.getAttribute("src");
    this.transform = transform;
    this.img = new Image(this.imgFilePath, transform.getSize().x, transform.getSize().y, true, false);
    this.transform.updateSize(new Vec2d(this.img.getWidth(), this.img.getHeight()));
  }

  public void setOpacity(double alpha){
    this.alpha = alpha;
  }

  @Override
  public void tick(long nanosSinceLastTick) {

  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {
    if (this.isNull) return;
    g.setGlobalAlpha(this.alpha);
    g.drawImage(this.img, this.transform.getPosition().x, this.transform.getPosition().y);
    g.setGlobalAlpha(1);
  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.DRAW;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    component.setAttribute("src", this.imgFilePath);
    return component;
  }

}
