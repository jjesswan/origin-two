package engine.inventory.ui;


import o2.BlackBoard;
import engine.inventory.InventoryObject;
import engine.inventory.ItemType;
import engine.support.Vec2d;
import engine.ui.UIDivisibleRect;
import engine.ui.UIElement;
import engine.ui.UIImage;
import engine.ui.UIRect;
import engine.ui.UISprite;
import engine.ui.UIText;
import engine.utils.SizeConstants;
import engine.utils.Types.Alignment;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import o2.files.ImageReference;

public class SideBlurb {

  private UIDivisibleRect blurb;
  private UIImage blurbOverlay;
  private double HEIGHT = 300;
  private double WIDTH = 200;
  private UIText titleText, descriptionText;
  private UISprite icon;
  private BlackBoard blackBoard;
  private UIRect actionsContainer;

  public SideBlurb(UIElement container, BlackBoard blackBoard){
    this.blackBoard = blackBoard;
    this.blurbOverlay = new UIImage(ImageReference.fileNameReference.get("side_blurb"), new Vec2d(0), new Vec2d(300), UIType.DISPLAY);
    this.blurb = new UIDivisibleRect(new Vec2d(0), this.blurbOverlay.getSize(), Color.RED, FillType.FILL, UIType.DISPLAY, 4, 1);
    this.blurb.setAlpha(0);
    container.appendToRight(this.blurbOverlay, 50);
    container.horizCenterElement(this.blurbOverlay);
    container.addChildElement(this.blurbOverlay);
    this.blurbOverlay.centerElement(this.blurb);
    this.blurbOverlay.addChildElement(this.blurb);
    this.initBlurb();
    this.hideBlurb();

  }

  public void hideBlurb(){
    this.blurb.hide();
    this.blurbOverlay.hide();
  }

  public void showBlurb(InventoryObject inventoryObject){
    this.titleText.setText(inventoryObject.name);
    this.descriptionText.setText(inventoryObject.desc);
    this.icon.changeReference(inventoryObject.getReferenceName());
    this.icon.changeIndexPos(inventoryObject.getImgIndex());
    this.blurb.show();
    this.blurbOverlay.show();

    // show equip button if type weapon
    if (inventoryObject.itemType == ItemType.WEAPON){
      this.weaponEquipmentButton(inventoryObject.gameID);
    } else {
      this.actionsContainer.hide();
    }
  }

  private void initBlurb(){
    this.blurb.hide();

    UIRect container = new UIRect(new Vec2d(0), new Vec2d(WIDTH, 60), Color.GREEN, FillType.FILL, UIType.DISPLAY);
    container.setAlpha(0);
    this.titleText = new UIText("Hello", new Vec2d(100), new Vec2d(30), Color.WHITE, UIType.DISPLAY,
        SizeConstants.FONT);
    this.descriptionText = new UIText("description", new Vec2d(100), new Vec2d(20), Color.WHITE, UIType.DISPLAY, SizeConstants.FONT);

    container.centerElement(titleText);
    container.addChildElement(titleText);

    titleText.appendToBottom(descriptionText, 5);
    titleText.vertCenterElement(descriptionText);
    titleText.addChildElement(descriptionText);

    UIRect imgContainer = new UIRect(new Vec2d(0), new Vec2d(WIDTH, WIDTH/1.2), Color.PINK, FillType.FILL, UIType.DISPLAY);
    imgContainer.setAlpha(0);
    container.appendToBottom(imgContainer, 10);
    container.vertCenterElement(imgContainer);
    container.addChildElement(imgContainer);

    this.icon = new UISprite(new Vec2d(0, 3), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(100), UIType.DISPLAY);
    imgContainer.centerElement(icon);
    imgContainer.addChildElement(icon);

    // TBA for buttons
    this.actionsContainer = new UIRect(new Vec2d(0), new Vec2d(WIDTH, 30), Color.BLUE, FillType.STROKE, UIType.DISPLAY);
    actionsContainer.setAlpha(0);
    imgContainer.appendToBottom(actionsContainer, 10);
    imgContainer.vertCenterElement(actionsContainer);
    imgContainer.addChildElement(actionsContainer);

    this.blurb.addAtIndex(0, 0, container);
    this.blurb.addChildElement(container);


    // exit button
    UISprite exitButton = new UISprite(new Vec2d(0, 3), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(10), UIType.BUTTON){
      @Override
      public void mouseClickCommand(MouseEvent e){
        hideBlurb();
      }
    };

    this.blurb.setToCorner(exitButton, Alignment.TOPRIGHT, new Vec2d(5));
    this.blurb.addChildElement(exitButton);
  }

  private void weaponEquipmentButton(String weaponID){
    this.actionsContainer.clearChildElements();
    this.actionsContainer.show();
    UIText equipText = new UIText("equip", new Vec2d(0), new Vec2d(20), Color.WHITE, UIType.DISPLAY, SizeConstants.FONT);

    Color buttonColor = Color.GRAY;
    if (BlackBoard.currentWeaponID != null && BlackBoard.currentWeaponID.equals(weaponID)){
      equipText.setText("unequip");
      buttonColor = Color.LIGHTSEAGREEN;
    }

      UIRect equipButton = new UIRect(new Vec2d(0), new Vec2d(50, 20), buttonColor, FillType.FILL, UIType.BUTTON){
      @Override
      public void mouseClickCommand(MouseEvent e){

        // unequip
        if (BlackBoard.currentWeaponID != null && BlackBoard.currentWeaponID.equals(weaponID)){
          equipText.setText("equip");
          this.changeColor(Color.GRAY);
          BlackBoard.currentWeaponID = null;
        } else {
          // equip
          equipText.setText("unequip");
          this.changeColor(Color.LIGHTSEAGREEN);
          BlackBoard.currentWeaponID = weaponID;
        }
        System.out.println("equipped weapon: " + BlackBoard.currentWeaponID);
      }
    };
    this.actionsContainer.centerElement(equipButton);
    this.actionsContainer.addChildElement(equipButton);
    equipButton.centerElement(equipText);
    equipButton.addChildElement(equipText);
  }



}
