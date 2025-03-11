package o2.player;

import o2.BlackBoard;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.raycast.RayCollisionInfo;
import engine.raycast.RayLine;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class Shooter {
  GameWorld gameWorld;
  GameObject player;
  BlackBoard blackBoard;

  public Shooter(GameWorld gameWorld, GameObject player, BlackBoard blackBoard){
    this.gameWorld = gameWorld;
    this.player = player;
    this.blackBoard = blackBoard;
  }

  public void shoot(Vec2d target){
    RayCollisionInfo info = this.gameWorld.shootRay("player", target);

    Vec2d end = target;
    if (info != null) end = info.intersection;

    Vec2d source = this.player.getTransform().getPosition();

    RayLine rayLine = new RayLine(info, this.gameWorld, Color.WHITE, 1, 20);


//    PlayerProjectile projectile = new PlayerProjectile(source, new Vec2d(6), this.gameWorld, this.blackBoard,20);
//    Vec2d dir = end.minus(source).normalize().smult(.5);
//    projectile.shoot(dir, end);
//
//    if (info == null) return;
//
//    // if hit target has health component, then decrement component
//    if (this.gameWorld.getGameObject(info.id).hasComponent(ComponentTag.HEALTH)){
//      ((HealthComponent)this.gameWorld.getGameObject(info.id).getComponent(ComponentTag.HEALTH)).takeDamage(40);
//    }
//
//    if (this.gameWorld.getGameObject(info.id).hasComponent(ComponentTag.PHYSICS)){
//      ((PhysicsComponent)this.gameWorld.getGameObject(info.id).getComponent(ComponentTag.PHYSICS)).applyImpulse(dir.smult(.2));
//    }

  }

}
