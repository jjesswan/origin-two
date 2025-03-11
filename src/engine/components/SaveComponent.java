package engine.components;

import engine.gameworld.GameObject;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SaveComponent implements IComponent{
  GameObject go;
  String id;

  public SaveComponent(GameObject go, String id){
    this.go = go;
    this.id = id;
  }


  public Element save(Document doc) {
    System.out.println("save component");
    Element gameObject = doc.createElement("GameObject");
    gameObject.setAttribute("id", this.id);

    TransformComponent t = this.go.getTransform();
    gameObject.setAttribute("x", String.valueOf(t.getPosition().x));
    gameObject.setAttribute("y", String.valueOf(t.getPosition().y));
    gameObject.setAttribute("w", String.valueOf(t.getSize().x));
    gameObject.setAttribute("h", String.valueOf(t.getSize().y));
    gameObject.setAttribute("zIndex", String.valueOf(this.go.getZIndex()));

    // set transform and physics attributes
    //gameObject.appendChild(this.go.getTransform().saveComponent(doc));

    for (IComponent component : this.go.getAllComponents().values()){
      if (component.saveComponent(doc) != null){
        gameObject.appendChild(component.saveComponent(doc));
      }
    }

    return gameObject;

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
    return ComponentTag.SAVE;
  }
}
