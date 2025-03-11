package engine.ui;

import engine.support.Vec2d;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UIPolygon extends UIElement{
  private double[] xPoints, yPoints;
  private FillType fillType;
  private double slant;

  public UIPolygon(Vec2d position, Vec2d size,
      Color initialColor, UIType uiType, FillType fillType, double slant) {
    super(position, size, initialColor, uiType);
    this.fillType = fillType;
    this.slant = slant;
  }

  public void updateLocation(){
    this.xPoints = new double[]{
        position.x, position.x + size.x, position.x + size.x - slant, position.x - slant
    };

    this.yPoints = new double[]{
        position.y, position.y, position.y + size.y, position.y + size.y
    };
  }



  @Override
  public void onDraw(GraphicsContext g){
    if (this.hidden) return;
    super.playAnimations(g);
    g.setFill(super.initialColor);
    g.setStroke(super.initialColor);
    g.setLineWidth(super.strokeWidth);

    this.updateLocation();

    if (fillType == FillType.FILL) g.fillPolygon(xPoints, yPoints, 4);
    if (fillType == FillType.STROKE) g.strokePolygon(xPoints, yPoints, 4);

    super.endAnimations(g);
    g.setGlobalAlpha(1);
    super.onDraw(g);
  }
}
