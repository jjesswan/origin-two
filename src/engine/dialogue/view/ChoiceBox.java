package engine.dialogue.view;

import engine.dialogue.types.DialogueOption;
import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIRect;
import engine.ui.UIText;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ChoiceBox {
  private DialogueOption option;
  private UIRect box;

  public ChoiceBox(Screen screen, DialogueOption option){
    this.option = option;
    this.box = new UIRect(new Vec2d(0), new Vec2d(150, 40), Color.rgb(112, 184, 176),
        FillType.FILL, UIType.BUTTON, 20, 20) {
      @Override
      public void mouseClickCommand(MouseEvent e) {
        System.err.println("CLICK ON OPTION");
      }
    };
    screen.addUIElement(this.box);
  }

}
