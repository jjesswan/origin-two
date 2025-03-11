package engine.components;

import o2.BlackBoard;
import engine.support.Vec2d;
import engine.utils.SpriteReferenceLoader;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import o2.files.ImageReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AnimationComponent implements IComponent {
  private TransformComponent transform;
  private SpriteReferenceLoader reference;
  private int currFrame = 0;
  private float timer = 0;
  private List<Vec2d> posSequence, indexSequence;
  private Vec2d idlePos = null;
  private String referenceName;

  public AnimationComponent(List<Vec2d> indexSequence, Vec2d idlePos, String referenceName, TransformComponent transform){
    this.reference = BlackBoard.spriteReferences.get(referenceName);
    if (this.reference == null) this.reference = ImageReference.spriteReferences.get(referenceName);

    this.init(indexSequence,idlePos,referenceName,transform);
  }

  public void init(List<Vec2d> indexSequence, Vec2d idlePos, String referenceName, TransformComponent transform){
    this.transform = transform;
    this.referenceName = referenceName;
    this.idlePos = idlePos;
    this.posSequence = new ArrayList<>();
    this.indexSequence = indexSequence;

    for (Vec2d index : this.indexSequence){
      this.posSequence.add(this.reference.getSpriteAtIndex(index));
    }
  }




  // idle stance position
  public Vec2d getIdlePos(){
    return this.idlePos;
  }


  @Override
  public void tick(long nanosSinceLastTick) {
    this.timer ++;
    if (this.timer >= 5){
      this.currFrame++;
      this.timer = 0;
    }
    if (this.currFrame == this.posSequence.size()) this.currFrame = 0;

  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {
    g.drawImage(this.reference.getImage(),
        this.posSequence.get(this.currFrame).x, this.posSequence.get(this.currFrame).y,
        this.reference.getSourceDim().x, this.reference.getSourceDim().y,
        this.transform.getPosition().x, this.transform.getPosition().y,
        this.transform.getSize().x, this.transform.getSize().y);
  }

  public void drawIdle(GraphicsContext g) {
    //System.out.println("idle pos: " + this.idlePos);
    g.drawImage(this.reference.getImage(),
        this.idlePos.x, this.idlePos.y,
        this.reference.getSourceDim().x, this.reference.getSourceDim().y,
        this.transform.getPosition().x, this.transform.getPosition().y,
        this.transform.getSize().x, this.transform.getSize().y);
  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.ANIMATION_SINGLE;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Animation");
    component.setAttribute("id", this.getTag().name());
    component.setAttribute("src", this.referenceName);

    if (this.idlePos != null){
      component.setAttribute("idleX", String.valueOf(this.idlePos.x));
      component.setAttribute("idleY", String.valueOf(this.idlePos.y));
    }

    // sequence list
    for (Vec2d v : this.indexSequence){
      Element vec = doc.createElement("PosSequence");
      vec.setAttribute("x", String.valueOf(v.x));
      vec.setAttribute("y", String.valueOf(v.y));
      component.appendChild(vec);
    }

    return component;

  }
}

