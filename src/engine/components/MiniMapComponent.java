package engine.components;

import o2.BlackBoard;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIRect;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MiniMapComponent implements IComponent{
  private UIElement uiElement;
  private Vec2d size;
  private Color color;
  public MiniMapComponent(Vec2d size, Color color){
    this.size = size;
    this.color = color;
    this.uiElement = new UIRect(new Vec2d(0), size, color, FillType.FILL, UIType.DISPLAY);
  }

  public MiniMapComponent(Element element, TransformComponent transform, BlackBoard blackBoard){
    this.size = new Vec2d(element.getAttribute("w"), element.getAttribute("h"));
    int r = (int) Double.parseDouble(element.getAttribute("r"));
    int g = (int) Double.parseDouble(element.getAttribute("g"));
    int b = (int) Double.parseDouble(element.getAttribute("b"));
    this.color = Color.rgb(r, g, b);
    this.uiElement = new UIRect(new Vec2d(0), size, color, FillType.FILL, UIType.DISPLAY);
  }

  public UIElement getMiniMapVer(){
    return this.uiElement;
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
    return ComponentTag.MINI_MAP;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    component.setAttribute("w", String.valueOf(this.size.x));
    component.setAttribute("h", String.valueOf(this.size.y));
    component.setAttribute("r", String.valueOf(this.color.getRed()));
    component.setAttribute("g", String.valueOf(this.color.getGreen()));
    component.setAttribute("b", String.valueOf(this.color.getBlue()));
    return component;
  }
}
