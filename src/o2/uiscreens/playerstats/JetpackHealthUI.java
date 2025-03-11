package o2.uiscreens.playerstats;

import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIPolygon;
import engine.utils.Types.Alignment;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.paint.Color;
import o2.Constants;

public class JetpackHealthUI {
  private UIPolygon healthbar, healthfill;
  private double BAR_HEIGHT = 2;
  private double BAR_MAX_WIDTH = 60;
  private double SLANT = 2;



  public JetpackHealthUI(Screen screen){
    this.healthbar = new UIPolygon(new Vec2d(0), new Vec2d(BAR_MAX_WIDTH, BAR_HEIGHT), Color.WHITE, UIType.DISPLAY, FillType.STROKE, SLANT);
    this.healthfill = new UIPolygon(new Vec2d(0), new Vec2d(BAR_MAX_WIDTH, BAR_HEIGHT), Constants.ELECTRIC_BLUE, UIType.DISPLAY, FillType.FILL, SLANT);

    this.healthbar.setStrokeWidth(1.4);
    screen.setToCorner(this.healthbar, Alignment.TOPLEFT, new Vec2d(100, 40));
    screen.addUIElement(this.healthbar);
    this.healthbar.setToCorner(this.healthfill, Alignment.TOPLEFT, new Vec2d(0));
    this.healthbar.addChildElement(this.healthfill);
  }

}
