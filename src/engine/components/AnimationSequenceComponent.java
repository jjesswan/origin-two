package engine.components;

import o2.BlackBoard;
import engine.support.Vec2d;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AnimationSequenceComponent implements IComponent{
  private int activeSequence = 0;
  List<AnimationComponent> sequences = new ArrayList<>();
  private boolean isIdle = true;

  public AnimationSequenceComponent(List<AnimationComponent> sequences){
    this.sequences = sequences;

  }

  public AnimationSequenceComponent(Element e, TransformComponent transform, BlackBoard blackBoard){
    NodeList animations = e.getElementsByTagName("Animation");
    for (int j=0; j<animations.getLength(); j++) {
      Element a = (Element) animations.item(j);
      Vec2d idlePos = null;

      if (!a.getAttribute("idleX").isEmpty()) {
        idlePos = new Vec2d(a.getAttribute("idleX"), a.getAttribute("idleY"));
      }
      String src = a.getAttribute("src");

      List<Vec2d> indexSequence = new ArrayList<>();
      NodeList sequences = a.getElementsByTagName("PosSequence");
      for (int i = 0; i < sequences.getLength(); i++) {
        Element s = (Element) sequences.item(i);
        Vec2d seq = new Vec2d(s.getAttribute("x"), s.getAttribute("y"));
        indexSequence.add(seq);
      }

      this.sequences.add(new AnimationComponent(indexSequence, idlePos, src, transform));
    }
  }

  public void setActiveSequence(int active){
    this.isIdle = false;
    if (active < 0 || active > this.sequences.size() - 1) {
      this.activeSequence = 0;
      System.err.println("Active sequence out of bounds!");
    } else {
      this.activeSequence = active;
    }
  }

  public void setIsIdle(boolean isIdle){
    this.isIdle = isIdle;
  }

  @Override
  public void tick(long nanosSinceLastTick) {
      this.sequences.get(this.activeSequence).tick(nanosSinceLastTick);
  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {
    if (this.isIdle && this.sequences.get(this.activeSequence).getIdlePos() != null) this.sequences.get(this.activeSequence).drawIdle(g);
    else this.sequences.get(this.activeSequence).draw(g);

  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.ANIMATION_SEQUENCE;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    for (AnimationComponent a : this.sequences){
      component.appendChild(a.saveComponent(doc));
    }

    return component;
  }
}


