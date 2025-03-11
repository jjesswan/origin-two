package o2.uiscreens.menus;

import engine.inventory.ui.InventoryUI;
import engine.screen.Screen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.ui.UIImage;
import engine.ui.UISprite;
import engine.utils.Types.Alignment;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.UIType;
import javafx.scene.input.MouseEvent;
import o2.files.ImageReference;

public class MenuButtonsOverlay extends Screen {
  private UISprite inventoryButton;


  public MenuButtonsOverlay(Vec2d screenSize,
      DisplayMode displayMode, ScreenManager screenManager) {
    super(screenSize, displayMode, screenManager);

    this.inventoryButton = new UISprite(new Vec2d(3,2), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(40), UIType.BUTTON){
      @Override
      public void mouseClickCommand(MouseEvent e){
        screenManager.showSingleScreen("inventoryScreen");
      }
    };
    this.setToCorner(this.inventoryButton, Alignment.TOPRIGHT, new Vec2d(10));
    this.addUIElement(this.inventoryButton);
  }
}
