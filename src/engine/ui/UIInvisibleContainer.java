package engine.ui;

import engine.support.Vec2d;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UIInvisibleContainer extends UIRect {

  public UIInvisibleContainer(Vec2d position, Vec2d size, UIType uiType) {
    super(position, size, Color.BLACK, FillType.FILL, uiType);
  }

}
