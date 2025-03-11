package o2.uiscreens.playerstats;

import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIText;
import engine.utils.Types.UIType;
import java.lang.invoke.ConstantCallSite;
import javafx.scene.paint.Color;
import o2.Constants;

public class OxygenLevelUI {

  private UIText oxygenLevel;

  public OxygenLevelUI(Screen screen){
    this.oxygenLevel = new UIText("100 %", new Vec2d(0), new Vec2d(20), Color.WHITE, UIType.DISPLAY,
        Constants.FONT);
    screen.verticalAlignUIElement(this.oxygenLevel, 20);
    screen.addUIElement(this.oxygenLevel);
  }

  public void changeOxygenLevelText(int amount){
    this.oxygenLevel.setText(amount + " %");

  }

}
