package engine.ui;

import engine.support.Vec2d;
import engine.ui.animations.FadeTransition;
import engine.ui.animations.VerticalSlideTransition;
import engine.utils.Types.AABBBoundingBox;
import engine.utils.Types.FillType;
import engine.utils.Types.ShapeType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class UIRect extends UIElement{
  private boolean isRound = false;
  private FillType fillType;
  private double arcWidth;
  private double arcHeight;
  private AABBBoundingBox boundingBox;
  protected double alpha = 1;
  private double strokeWidth = 1;

  // normal rect
  public UIRect(Vec2d position, Vec2d size, Color initialColor, FillType fill, UIType uiType) {
    super(position, size, initialColor, uiType);
    this.fillType = fill;
    this.calculateBoundingBox();
    super.shapeType = ShapeType.RECT;
  }
  // rounded rect
  public UIRect(Vec2d position, Vec2d size, Color initialColor, FillType fill,  UIType uiType, double arcWidth, double arcHeight) {
    super(position, size, initialColor, uiType);
    this.isRound = true;
    this.fillType = fill;
    this.arcHeight = arcHeight;
    this.arcWidth = arcWidth;
    this.calculateBoundingBox();
    super.shapeType = ShapeType.ROUND_RECT;
  }


  public void setAlpha(double alpha){
    this.alpha = alpha;

  }

  @Override
  public void onDraw(GraphicsContext g) {
    if (this.hidden) return;
    g.setFill(super.initialColor);
    g.setStroke(super.initialColor);
    g.setGlobalAlpha(this.alpha);
    super.playAnimations(g);

    g.restore();
    if(this.isRound){
      switch(this.fillType){
        case FILL:
          g.fillRoundRect(super.position.x, super.position.y, super.size.x, super.size.y, this.arcWidth, this.arcHeight);
          break;
        case STROKE:
          g.setLineWidth(super.strokeWidth);
          g.strokeRoundRect(super.position.x, super.position.y, super.size.x, super.size.y, this.arcWidth, this.arcHeight);
          break;
      }
    } else {
      switch(this.fillType){
        case FILL:
          g.fillRect(super.position.x, super.position.y, super.size.x, super.size.y);
          break;
        case STROKE:
          g.setLineWidth(super.strokeWidth);
          g.strokeRect(super.position.x, super.position.y, super.size.x, super.size.y);
          break;
      }
    }

    super.endAnimations(g);

    g.setGlobalAlpha(1);
    super.onDraw(g);
  }

  @Override
  public void onMousePressed(MouseEvent e) {
    super.onMousePressed(e);
    if (this.uiType == UIType.BUTTON && !this.hidden) {
      if (this.boundingBox.min.x <= e.getX() && e.getX() <= this.boundingBox.max.x
          && this.boundingBox.min.y <= e.getY() && e.getY() <= this.boundingBox.max.y) {
        this.mouseClickCommand(e);
        super.isClicked = true;
      } else if (super.isClicked) {
        super.isClicked = false;
        this.mouseClickOffCommand(e);
      }
    }

  }

  @Override
  public void onMouseMoved(MouseEvent e) {
    super.onMouseMoved(e);
    if (this.uiType == UIType.BUTTON) {
      if (this.boundingBox.min.x <= e.getX() && e.getX() <= this.boundingBox.max.x
          && this.boundingBox.min.y <= e.getY() && e.getY() <= this.boundingBox.max.y) {
        this.mouseMoveCommand();
      } else {
        this.mouseMoveOffCommand();
      }
    }
  }

  @Override
  public void onResize(Vec2d newSize) {
    super.onResize(newSize);
    this.calculateBoundingBox();
  }

  @Override
  public void calculateBoundingBox(){
    Vec2d min = new Vec2d(super.position.x, super.position.y);
    Vec2d max = new Vec2d(super.position.x + super.size.x, super.position.y + super.size.y);

    this.boundingBox = new AABBBoundingBox();
    this.boundingBox.min = min;
    this.boundingBox.max = max;
  }

  }
