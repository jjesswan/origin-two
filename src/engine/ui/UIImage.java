package engine.ui;

import engine.support.Vec2d;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class UIImage extends UIElement {
  private Image img;
  private double alpha = 1;


  public UIImage(String imgFilepath, Vec2d imgPos, Vec2d size, UIType uiType) {
    super(imgPos, size, Color.grayRgb(0), uiType);
    this.img = new Image(imgFilepath, size.x, size.y, true, false);
    super.size = new Vec2d(this.img.getWidth(), this.img.getHeight());
  }

  public UIImage(String imgFilepath, Vec2d size, UIType uiType, Vec2d imgPos, double alpha) {
    super(imgPos, size, Color.grayRgb(0), uiType);
    this.img = new Image(imgFilepath, size.x, size.y, true, false);
    this.alpha = alpha;
    this.size = new Vec2d(this.img.getWidth(), this.img.getHeight());
  }

  public UIImage(String imgFilepath, Vec2d imgPos, UIType uiType, boolean preserveRatio) {
    super(imgPos, new Vec2d(0), Color.grayRgb(0), uiType);
    this.img = new Image(imgFilepath);
    this.size = new Vec2d(this.img.getWidth(), this.img.getHeight());
  }

  public void changePos(Vec2d newPos){
    this.position = newPos;
  }

  @Override
  public void onDraw(GraphicsContext g) {

    if (this.hidden) return;
    g.setGlobalAlpha(this.alpha);
    super.playAnimations(g);

    g.drawImage(this.img, this.position.x, this.position.y);

    super.endAnimations(g);
    g.setGlobalAlpha(1);
    super.onDraw(g);
  }
}
