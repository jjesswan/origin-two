package engine.components;

import engine.components.ComponentTag;
import engine.components.IComponent;
import engine.components.TransformComponent;
import engine.gameworld.GameObject;
import engine.inventory.InventoryObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import o2.SoundManager;

public class InventoryComponent implements IComponent {
  private GameObject player;
  private TransformComponent transform;
  private InventoryObject inventoryObject;
  private double PROXIMITY = 100;
  private boolean grabbed = false;

  public InventoryComponent(GameObject player, TransformComponent transform, InventoryObject inventoryObject){
    this.player = player;
    this.transform = transform;
    this.inventoryObject = inventoryObject;
  }

  public InventoryObject getInventoryObject(){
    return this.inventoryObject;
  }

  public boolean checkIfGrabbed(){
    return this.grabbed;
  }

  @Override
  public void onKeyPress(KeyEvent e){
      if (e.getCode() == KeyCode.G){
        if (this.player.getTransform().getPosition().dist(this.transform.getPosition()) < PROXIMITY){
          SoundManager.sounds.get("add_inventory").playOnce();
          System.out.println("grabbed!");
        this.grabbed = true;
      }
    }
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
  public ComponentTag getTag() {
    return ComponentTag.INVENTORY;
  }
}
