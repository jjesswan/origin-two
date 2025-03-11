package engine.inventory.ui;

import engine.inventory.ItemType;
import engine.support.Vec2d;
import engine.ui.UISprite;
import engine.ui.UIText;
import engine.utils.SpriteReferenceLoader;
import engine.utils.Types.UIType;
import javafx.scene.paint.Color;
import o2.Constants;

public class InventoryTab extends UISprite {
  public ItemType itemType;
  private Vec2d activeID, idleID;

  public InventoryTab(ItemType itemType, Vec2d imgIndex_Idle, Vec2d imgIndex_Active, SpriteReferenceLoader spriteReferenceLoader){
    super(imgIndex_Idle, spriteReferenceLoader, new Vec2d(0), new Vec2d(30), UIType.BUTTON);
    this.itemType = itemType;
    this.activeID = imgIndex_Active;
    this.idleID = imgIndex_Idle;
  }

  public void updateTextLocation(){
    UIText tabLabel = new UIText(this.itemType.name(), new Vec2d(0), new Vec2d(20), Color.WHITE, UIType.DISPLAY,
        Constants.FONT);
    this.vertCenterElement(tabLabel);
    this.horizCenterElement(tabLabel);
    tabLabel.setRotation(-90);
    this.addChildElement(tabLabel);
  }

  public void activateTab(){
    this.changeIndexPos(this.activeID);
  }

  public void deactivateTab(){
    this.changeIndexPos(this.idleID);
  }

}
