package o2.uiscreens.menus;

import engine.gameworld.GameWorld;
import engine.screen.Screen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.ui.UIImage;
import engine.ui.UIRect;
import engine.ui.UIText;
import engine.utils.SizeConstants;
import engine.utils.Types.Alignment;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import java.util.HashMap;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import o2.Constants;
import o2.GameMainScreen;
import o2.files.ImageReference;

public class TitleScreen extends Screen {

  public TitleScreen(DisplayMode displayMode,
      ScreenManager screenManager) {
    super(SizeConstants.INIT_WINDOW_SIZE, displayMode, screenManager);
    this.setScreenFill(Color.BLACK);

    UIImage bg = new UIImage(ImageReference.fileNameReference.get("title_bg"), new Vec2d(0), SizeConstants.INIT_WINDOW_SIZE, UIType.DISPLAY);
    this.addUIElement(bg);

    UIRect play = new UIRect(new Vec2d(0), new Vec2d(60, 30), Color.BLACK, FillType.FILL, UIType.BUTTON){
      @Override
      public void mouseClickCommand(MouseEvent e){
        changeDisplayMode(DisplayMode.HIDE);
        screenManager.showScreens(Constants.GAME_SCREENS);
        ((GameMainScreen)screenManager.getScreen("gameScreen")).start();
      }
      @Override
      public void mouseMoveCommand(){
        changeColor(Constants.ELECTRIC_BLUE);
      }

      @Override
      public void mouseMoveOffCommand(){
        changeColor(Color.BLACK);
      }

    };
    UIText playText = new UIText("start", new Vec2d(0), new Vec2d(30), Color.WHITE, UIType.DISPLAY, SizeConstants.FONT);
    this.setToCorner(play, Alignment.TOPRIGHT, new Vec2d(50, 20));
    play.centerElement(playText);
    play.addChildElement(playText);
    this.addUIElement(play);
  }
}
