package engine.components;

import o2.BlackBoard;
import engine.support.Vec2d;
import engine.utils.SpriteReferenceLoader;
import javafx.scene.canvas.GraphicsContext;
import o2.files.ImageReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SpriteComponent implements IComponent {
  private TransformComponent transform;
  private SpriteReferenceLoader reference;
  private Vec2d sourcePos, indexPos;
  private String referenceName;


  public SpriteComponent(Vec2d index, String referenceName, TransformComponent transform, BlackBoard blackBoard){
    this.transform = transform;
    this.referenceName = referenceName;
    this.indexPos = index;
    this.reference = ImageReference.spriteReferences.get(referenceName);
    if (this.reference == null) this.reference = blackBoard.spriteReferences.get(referenceName);
    this.sourcePos = this.reference.getSpriteAtIndex(this.indexPos);
    this.updateSpriteSize(this.transform.getSize().x);
  }

  public SpriteComponent(Vec2d index, String referenceName, TransformComponent transform){
    this.transform = transform;
    this.referenceName = referenceName;
    this.indexPos = index;
    this.reference = ImageReference.spriteReferences.get(referenceName);
    this.sourcePos = this.reference.getSpriteAtIndex(this.indexPos);
    this.updateSpriteSize(this.transform.getSize().x);
  }

  public SpriteComponent(Vec2d index, SpriteReferenceLoader spriteReferenceLoader, TransformComponent transform, BlackBoard blackBoard){
    this.transform = transform;
    this.reference = spriteReferenceLoader;
    this.indexPos = index;
    this.sourcePos = this.reference.getSpriteAtIndex(this.indexPos);
    this.updateSpriteSize(this.transform.getSize().x);
  }

  public SpriteComponent(Element element, TransformComponent transform, BlackBoard blackBoard){
    this.transform = transform;
    this.referenceName = element.getAttribute("src");
    this.indexPos = new Vec2d(element.getAttribute("x"), element.getAttribute("y"));
    this.reference = ImageReference.spriteReferences.get(this.referenceName);
    this.sourcePos = this.reference.getSpriteAtIndex(this.indexPos);
    this.updateSpriteSize(this.transform.getSize().x);
  }

  public void updateSpriteSize(double scale){
    this.transform.updateSize(new Vec2d(scale, scale * this.reference.getAspectRatio()));
  }

  public void changeSpriteIndex(Vec2d newIndex){
    this.indexPos = newIndex;
    this.sourcePos = this.reference.getSpriteAtIndex(this.indexPos);
  }


  @Override
  public void tick(long nanosSinceLastTick) {

  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {
    g.drawImage(this.reference.getImage(),
        this.sourcePos.x, this.sourcePos.y,
        this.reference.getSourceDim().x, this.reference.getSourceDim().y,
        this.transform.getPosition().x, this.transform.getPosition().y,
        this.transform.getSize().x, this.transform.getSize().y);
  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.SPRITE;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    component.setAttribute("src", this.referenceName);
    component.setAttribute("x", String.valueOf(this.indexPos.x));
    component.setAttribute("y", String.valueOf(this.indexPos.y));

    return component;
  }
}
