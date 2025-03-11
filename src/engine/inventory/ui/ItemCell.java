package engine.inventory.ui;

import engine.gameworld.GameObject;
import engine.inventory.InventoryObject;
import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIDivisibleRect;
import engine.ui.UIElement;
import engine.ui.UIImage;
import engine.ui.UIRect;
import engine.ui.UISprite;
import engine.ui.UIText;
import engine.utils.Types.Alignment;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import o2.Constants;
import o2.files.ImageReference;

public class ItemCell extends UIRect {
  protected InventoryObject inventoryObject = null;
  private int amount = 0;
  private UIDivisibleRect container;
  private SideBlurb blurb;
  private UIText count;
  private UISprite icon;
  private UIRect stroke;

  public ItemCell(Vec2d pos, Vec2d size, Color color, UIDivisibleRect container, SideBlurb blurb){
    super(pos, size, color, FillType.FILL, UIType.BUTTON, 10, 10);
    this.container = container;
    this.blurb = blurb;
    this.container.addAtIndex((int) pos.x, (int) pos.y, this);
    this.container.addChildElement(this);
    this.hide();

    // stroke
    this.stroke = new UIRect(pos, size, Constants.ELECTRIC_BLUE, FillType.STROKE, UIType.BUTTON, 10, 10);
    this.centerElement(this.stroke);
    this.addChildElement(this.stroke);
    this.stroke.hide();
  }

  public void showStroke(){
    this.stroke.show();
  }

  public void hideStroke(){
    this.stroke.hide();
  }

  public void showItemDescription(){
    if (this.inventoryObject == null) return;
    this.blurb.showBlurb(this.inventoryObject);
  }

  public void showCell(){
    this.show();
  }

  public void hideCell(){
    this.hide();
  }

  public String getInventoryID(){
    if (this.inventoryObject == null) return null;
    return this.inventoryObject.gameID;
  }

  public void updateCount(){
    this.amount++;
    this.count.setText(String.valueOf(this.amount));
  }

  public void addInventoryObject(InventoryObject inventoryObject){
    this.inventoryObject = inventoryObject;
    this.icon = new UISprite(inventoryObject.getImgIndex(), ImageReference.spriteReferences.get(inventoryObject.getReferenceName()), new Vec2d(0), new Vec2d(35), UIType.DISPLAY);

    this.centerElement(this.icon);
    this.addChildElement(this.icon);

    this.amount++;
    this.count = new UIText(String.valueOf(this.amount), new Vec2d(0), new Vec2d(20), Color.WHITE, UIType.DISPLAY,
        Constants.FONT);
    this.setToCorner(this.count, Alignment.BOTTOMRIGHT, new Vec2d(0, -10));
    this.addChildElement(this.count);

  }

  public void removeObject(){
    this.inventoryObject = null;
    this.amount = 0;

  }

}
