package engine.systems;

import engine.components.ComponentTag;
import engine.components.PhysicsComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CharacterControllerSystem implements ISystem{
  private GameObject player;

  public CharacterControllerSystem(GameObject player){
    this.player = player;

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
  public SystemTag getTag() {
    return SystemTag.CHAR_CONTROLLER_SYSTEM;
  }

  @Override
  public void updateGameObjects(String label, GameObject go) {

  }

  @Override
  public void removeGameObjects(String label) {

  }

  @Override
  public void onKeyPress(KeyEvent e){
    //int i = 0;
    //System.out.println("key held" + i++);
    Vec2d trans;
    double t = 10;
    switch(e.getCode()){
      case W:
        trans = new Vec2d(0, -t);
        //this.player.getTransform().translatePos(trans);
        ((PhysicsComponent) this.player.getComponent(ComponentTag.PHYSICS)).clearImpulse();
        break;
      case A:
        trans = new Vec2d(-t, 0);
        this.player.getTransform().translatePos(trans);
        ((PhysicsComponent) this.player.getComponent(ComponentTag.PHYSICS)).clearImpulse();
        break;
      case S:
        trans = new Vec2d(0, t);
        //this.player.getTransform().translatePos(trans);
        ((PhysicsComponent) this.player.getComponent(ComponentTag.PHYSICS)).clearImpulse();
        break;
      case D:
        trans = new Vec2d(t, 0);
        this.player.getTransform().translatePos(trans);
        ((PhysicsComponent) this.player.getComponent(ComponentTag.PHYSICS)).clearImpulse();
        break;
      case SPACE:
        System.out.println("space");
        ((PhysicsComponent) this.player.getComponent(ComponentTag.PHYSICS)).jump(-200);
        break;
    }
  }
}
