package o2;

import engine.screen.Screen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIRect;
import engine.ui.UIText;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MenuScreen extends Screen  {
  private String font = "Pixels";

  public MenuScreen(Vec2d screenSize, DisplayMode displayMode, ScreenManager screenManager) {
    super(screenSize, displayMode, screenManager);
    this.setScreenFill(Color.rgb(82, 112, 103));
    this.initScreenUI();
  }

  public void initScreenUI(){
    UIElement died = new UIText("YOU DIED :(", new Vec2d(0), new Vec2d(30),
        Color.rgb(255, 255, 255), UIType.DISPLAY, this.font);

    this.centerAlignUIElement(died);
    this.addUIElement(died);
  }

  public void addRestartLogic(GameMainScreen gameMainScreen){
    UIElement startButton = new UIRect(new Vec2d(0), new Vec2d(150, 40), Color.rgb(112, 184, 176),
        FillType.FILL, UIType.BUTTON, 20, 20){
      @Override
      public void mouseClickCommand(MouseEvent e){
        System.err.println("RESTART SCREEN");
        //gameMainScreen.restartGame(new GameWorld(new HashMap<>()));
      }

      @Override
      public void mouseMoveCommand() {
        this.setFill(Color.rgb(112, 184, 176).darker());
      }

      @Override
      public void mouseMoveOffCommand() {
        this.setFill(Color.rgb(112, 184, 176));
      }
    };

    UIElement startText = new UIText("RESTART LEVEL", new Vec2d(0), new Vec2d(30),
        Color.rgb(255, 255, 255), UIType.DISPLAY, this.font);

    this.verticalAlignUIElement(startButton, 350);
    startButton.centerElement(startText);
    startButton.addChildElement(startText);

    this.addUIElement(startButton);
  }
}
