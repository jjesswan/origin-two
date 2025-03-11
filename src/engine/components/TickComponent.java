package engine.components;

import o2.BlackBoard;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TickComponent implements IComponent{

  public TickComponent(){

  }

  public TickComponent(Element element, TransformComponent transform, BlackBoard blackBoard){

  }

  public void defineTickAction(){

  }

  @Override
  public void tick(long nanosSinceLastTick) {
    this.defineTickAction();

  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public void onKeyPress(KeyEvent e) {
  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.TICK;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    return component;
  }
}
