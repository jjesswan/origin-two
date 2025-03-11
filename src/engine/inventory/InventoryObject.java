package engine.inventory;

import engine.support.Vec2d;

// inventory objects are attached to a gameobject
public class InventoryObject {
  public int count = 0;
  public String name;
  public String desc;
  public ItemType itemType;
  public String gameID;
  private Vec2d imgIndex;
  private String referenceName;


  public InventoryObject(String gameID, String name, String desc, ItemType itemType, Vec2d imgIndex, String referenceName){
    this.gameID = gameID;
    this.name = name;
    this.desc = desc;
    this.itemType = itemType;
    this.imgIndex = imgIndex;
    this.referenceName = referenceName;

  }

  public Vec2d getImgIndex(){
    return this.imgIndex;
  }

  public String getReferenceName(){
    return this.referenceName;
  }

}
