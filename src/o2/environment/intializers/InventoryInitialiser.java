package o2.environment.intializers;

import engine.inventory.InventoryObject;
import engine.inventory.ItemType;
import engine.inventory.ui.InventoryUI;
import engine.support.Vec2d;
import o2.Constants;

public class InventoryInitialiser {

  public InventoryInitialiser(InventoryUI inventoryUI){
    inventoryUI.addItem(new InventoryObject(Constants.PLATNINUM_SHOOTER, "Platinum Shooter", "A weapon left to you some time ago.",
        ItemType.WEAPON, new Vec2d(0), "weapons"));
    inventoryUI.addItem(new InventoryObject(Constants.NEBULA_SHOOTER, "Nebula Tranquilizer", "Something you found at \n the bottom of your bag.",
        ItemType.WEAPON, new Vec2d(0,1), "weapons"));
  }

}
