package engine.inventory.ui;

import engine.inventory.InventoryObject;
import engine.support.Vec2d;
import engine.ui.UIDivisibleRect;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ItemPage {
  private ItemCell[][] page;

  public ItemPage(UIDivisibleRect container, int pageHeight, int pageWidth, Color color, SideBlurb blurb){
    this.page = new ItemCell[pageWidth][pageHeight];
    for (int i=0; i<pageWidth; i++){
      for (int j=0; j<pageHeight; j++){
        this.page[i][j] = new ItemCell(new Vec2d(i, j), new Vec2d(60), color, container, blurb){
          @Override
          public void mouseClickCommand(MouseEvent e){
            showItemDescription();
            if (inventoryObject == null) return;
            showStroke();
          }

          @Override
          public void mouseClickOffCommand(MouseEvent e){
            hideStroke();
          }
        };
      }
    }
  }

  public boolean addItem(InventoryObject inventoryObject){
    String gameID = inventoryObject.gameID;

    for (int i=0; i<this.page.length; i++){
      for (int j=0; j<this.page[0].length; j++){
        if (this.page[i][j].getInventoryID() == gameID){
          this.page[i][j].updateCount();
          return true;
        }
        if (this.page[i][j].getInventoryID() == null){
          this.page[i][j].addInventoryObject(inventoryObject);
          return true;
        }
      }
    }

    // otherwise, no openings or matches were found; thus return false
    return false;
  }

  public void showPage(){
    for (int i=0; i<this.page.length; i++){
      for (int j=0; j<this.page[0].length; j++){
        this.page[i][j].showCell();
      }
    }
  }

  public void hidePage(){
    for (int i=0; i<this.page.length; i++){
      for (int j=0; j<this.page[0].length; j++){
        this.page[i][j].hideCell();
      }
    }
  }

}
