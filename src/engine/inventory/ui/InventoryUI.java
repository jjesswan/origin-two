package engine.inventory.ui;

import o2.BlackBoard;
import engine.inventory.InventoryObject;
import engine.screen.Screen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.ui.UISprite;
import engine.ui.UIText;
import engine.utils.SizeConstants;
import engine.utils.Types.Alignment;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.UIType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import o2.Constants;
import o2.files.ImageReference;

public class InventoryUI {
  private Screen screen;
  private ItemPages itemPages;
  private UISprite closeButton;

  public InventoryUI(ScreenManager screenManager){
    this.screen = new Screen(SizeConstants.INIT_WINDOW_SIZE, DisplayMode.HIDE, screenManager);
    this.screen.setScreenFill(Color.rgb(47, 47,43));
    this.itemPages = new ItemPages(new BlackBoard(), this.screen);
    this.closeButton = new UISprite(new Vec2d(0, 3), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(20), UIType.BUTTON){
      @Override
      public void mouseClickCommand(MouseEvent e){
        screenManager.showScreens(Constants.GAME_SCREENS);
        screen.changeDisplayMode(DisplayMode.HIDE);

      }
    };
    this.screen.setToCorner(this.closeButton, Alignment.TOPRIGHT, new Vec2d(15));
    this.screen.addUIElement(this.closeButton);

    // embellishments
    UIText inventoryText = new UIText("backpack", new Vec2d(10), new Vec2d(40), Color.WHITE, UIType.DISPLAY,
        Constants.FONT);
    this.screen.verticalAlignUIElement(inventoryText, 40);
    this.screen.addUIElement(inventoryText);

    UISprite corner1 = new UISprite(new Vec2d(1,3), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(40), UIType.DISPLAY);
    UISprite corner2 = new UISprite(new Vec2d(2,3), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(40), UIType.DISPLAY);

    this.screen.setToCorner(corner1, Alignment.TOPLEFT, new Vec2d(10));
    this.screen.addUIElement(corner1);

    this.screen.setToCorner(corner2, Alignment.BOTTOMRIGHT, new Vec2d(10));
    this.screen.addUIElement(corner2);


//    for (int i=0; i< 20; i++){
//      InventoryObject object = new InventoryObject("gameID" + i, "n: " + i, "description", ItemType.LOOT, new Vec2d(0), "ui_icons");
//      this.addItem(object, i);
//    }

  }

  public Screen getScreen(){
    return this.screen;
  }

  public void addItem(InventoryObject inventoryObject){
    this.itemPages.addItem(inventoryObject);
  }

  public void addItem(InventoryObject inventoryObject, int amount){
    for (int i=0; i < amount; i++){
      this.itemPages.addItem(inventoryObject);
    }
  }

}
