package engine.ui;

import o2.BlackBoard;
import engine.support.Vec2d;
import engine.utils.SpriteReferenceLoader;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import o2.files.ImageReference;

public class UISprite extends UIRect {
  private Vec2d sourcePos;
  private SpriteReferenceLoader reference;
  private Vec2d scale;

  public UISprite(Vec2d imgIndex, String referenceName, BlackBoard blackBoard, Vec2d pos, Vec2d scale, UIType uiType) {
    super(pos, new Vec2d(0), Color.rgb(0,0,0), FillType.FILL, uiType);

    this.scale = scale;
    this.reference = blackBoard.spriteReferences.get(referenceName);
    this.sourcePos = this.reference.getSpriteAtIndex(imgIndex);
    this.updateSpriteSize(scale.x);
  }

  public void updateSpriteSize(double scale){
    this.size = new Vec2d(scale, scale * this.reference.getAspectRatio());
  }

  public UISprite(Vec2d imgIndex, SpriteReferenceLoader spriteReferenceLoader, Vec2d pos, Vec2d scale, UIType uiType) {
    super(pos, new Vec2d(0), Color.rgb(0,0,0), FillType.FILL, uiType);

    this.scale = scale;
    this.reference = spriteReferenceLoader;
    this.sourcePos = this.reference.getSpriteAtIndex(imgIndex);
    this.updateSpriteSize(scale.x);

  }

  public void changeIndexPos(Vec2d newIndex){
    this.sourcePos = this.reference.getSpriteAtIndex(newIndex);
    this.updateSpriteSize(scale.x);
  }

  public void changeReference(String referenceName){
    this.reference = ImageReference.spriteReferences.get(referenceName);
    if (this.reference == null) System.err.println("REFERENCE DOESN'T EXIST! : " + referenceName);
    this.updateSpriteSize(scale.x);
  }

  @Override
  public void onDraw(GraphicsContext g){
    if (this.hidden) return;
    if (this.position == null) return;
    g.drawImage(this.reference.getImage(),
        this.sourcePos.x, this.sourcePos.y,
        this.reference.getSourceDim().x, this.reference.getSourceDim().y,
        this.position.x, this.position.y,
        this.size.x, this.size.y);

    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onDraw(g);
    }
  }
}
