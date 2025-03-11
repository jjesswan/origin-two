package o2;

import engine.gameworld.GameWorld;
import engine.screen.Screen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIRect;
import engine.ui.UIText;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import java.util.HashMap;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class LoadScreen extends Screen  {
  private String font = "Pixels";

  public LoadScreen(Vec2d screenSize, DisplayMode displayMode, GameMainScreen gameMainScreen, ScreenManager screenManager) {
    super(screenSize, displayMode, screenManager);
    this.setScreenFill(Color.rgb(82, 112, 103));
    this.initScreenUI();
    this.addRestartLogic(gameMainScreen);
    this.addSave(gameMainScreen);
    this.addLoad(gameMainScreen);

  }

  public void initScreenUI(){
    UIElement died = new UIText("MENU", new Vec2d(0), new Vec2d(30),
        Color.rgb(255, 255, 200), UIType.DISPLAY, this.font);

    this.centerAlignUIElement(died);
    this.addUIElement(died);
  }

  public void addRestartLogic(GameMainScreen gameMainScreen){
    UIElement startButton = new UIRect(new Vec2d(0), new Vec2d(150, 40), Color.rgb(112, 184, 176),
        FillType.FILL, UIType.BUTTON, 20, 20){
      @Override
      public void mouseClickCommand(MouseEvent e){
        System.err.println("RESTART");
        gameMainScreen.restartGame(new GameWorld(new HashMap<>()));
        changeDisplayMode(DisplayMode.HIDE);
        gameMainScreen.changeDisplayMode(DisplayMode.SHOW);
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

    UIElement startText = new UIText("RESTART", new Vec2d(0), new Vec2d(30),
        Color.rgb(255, 255, 255), UIType.DISPLAY, this.font);

    this.verticalAlignUIElement(startButton, 250);
    startButton.centerElement(startText);
    startButton.addChildElement(startText);

    this.addUIElement(startButton);
  }

  public void addSave(GameMainScreen gameMainScreen){
    UIElement button = new UIRect(new Vec2d(0), new Vec2d(150, 40), Color.rgb(112, 184, 176),
        FillType.FILL, UIType.BUTTON, 20, 20){
      @Override
      public void mouseClickCommand(MouseEvent e){
        System.err.println("SAVE");
        //gameMainScreen.saveGameFile();
        changeDisplayMode(DisplayMode.HIDE);
        gameMainScreen.changeDisplayMode(DisplayMode.SHOW);
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

    UIElement startText = new UIText("SAVE GAME", new Vec2d(0), new Vec2d(30),
        Color.rgb(255, 255, 255), UIType.DISPLAY, this.font);

    this.verticalAlignUIElement(button, 350);
    button.centerElement(startText);
    button.addChildElement(startText);

    this.addUIElement(button);
  }

  public void addLoad(GameMainScreen gameMainScreen){
    UIElement button = new UIRect(new Vec2d(0), new Vec2d(150, 40), Color.rgb(112, 184, 176),
        FillType.FILL, UIType.BUTTON, 20, 20){
      @Override
      public void mouseClickCommand(MouseEvent e){
        System.err.println("LOAD");
        //gameMainScreen.loadGameFile();
        changeDisplayMode(DisplayMode.HIDE);
        gameMainScreen.changeDisplayMode(DisplayMode.SHOW);
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

    UIElement startText = new UIText("LOAD GAME", new Vec2d(0), new Vec2d(30),
        Color.rgb(255, 255, 255), UIType.DISPLAY, this.font);

    this.verticalAlignUIElement(button, 400);
    button.centerElement(startText);
    button.addChildElement(startText);

    this.addUIElement(button);
  }
}
