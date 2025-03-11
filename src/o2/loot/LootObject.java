package o2.loot;

import o2.BlackBoard;
import engine.components.CollisionComponent;
import engine.components.InventoryComponent;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.inventory.InventoryObject;
import engine.inventory.ItemType;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import o2.Constants;
import o2.player.Player;

public class LootObject {
  private GameObject go;
  private Vec2d imgIndex;
  private String referenceName;
  private Player player;
  private String id;

  public LootObject(Vec2d pos, Vec2d size, Vec2d imgIndex, String spriteReferenceName, String id, Player player, GameWorld gameWorld, BlackBoard blackBoard){
    this.id = id;
    String uuid = id + pos;
    this.go = new GameObject(pos, size, Constants.INTERACTABLE_LAYER, uuid);
    this.go.addComponent(new SpriteComponent(imgIndex, spriteReferenceName, go.getTransform(),  blackBoard));
    go.addComponent(new CollisionComponent(CollisionShapeType.AAB, go.getTransform(), CollisionBehaviorType.DYNAMIC));
    go.addComponent(new PhysicsComponent(go.getTransform(), 20, 0, CollisionBehaviorType.DYNAMIC));

    this.imgIndex = imgIndex;
    this.referenceName = spriteReferenceName;
    this.player = player;
    gameWorld.addGameObject(uuid, this.go);
  }

  public void makeInventoryItem(String name, String description, ItemType itemType){
    InventoryObject io = new InventoryObject(this.id, name, description, itemType, this.imgIndex, this.referenceName);
    this.go.addComponent(new InventoryComponent(this.player.getPlayerGO(), go.getTransform(), io));
  }

}
