package engine.inventory;

import engine.components.ComponentTag;
import engine.components.IComponent;
import engine.components.InventoryComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.inventory.ui.InventoryUI;
import engine.systems.CollisionSystem;
import engine.systems.ISystem;
import engine.systems.SystemTag;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class InventorySystem implements ISystem {
  Map<String, GameObject> gameObjects;
  InventoryUI inventory;
  GameWorld gameWorld;

  public InventorySystem(Map<String, GameObject> gameObjects, GameWorld gameWorld, InventoryUI inventory){
    this.gameObjects = gameObjects;
    this.gameWorld = gameWorld;
    this.inventory = inventory;
  }

  @Override
  public void tick(long nanosSinceLastTick) {


  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public void onKeyPress(KeyEvent e){
    for (GameObject go : this.gameObjects.values()){
      if (go.hasComponent(ComponentTag.INVENTORY)){
        go.getComponent(ComponentTag.INVENTORY).onKeyPress(e);

        // if item is grabbed, then remove it from the game world and add it to inventory
        if (((InventoryComponent)go.getComponent(ComponentTag.INVENTORY)).checkIfGrabbed()){
          this.gameWorld.deleteGameObject(go.getId());
          this.inventory.addItem(((InventoryComponent)go.getComponent(ComponentTag.INVENTORY)).getInventoryObject());
        }
      }
    }
  }


  @Override
  public SystemTag getTag() {
    return SystemTag.INVENTORY_SYSTEM;
  }

  @Override
  public void updateGameObjects(String label, GameObject go) {

  }

  @Override
  public void removeGameObjects(String label){
    this.gameObjects.remove(label);
  }
}
