package o2.enemies;

import o2.BlackBoard;
import engine.components.CollisionComponent;
import engine.components.ComponentTag;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.components.TickComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import o2.Constants;
import o2.player.Player;

public class Projectile {
  private Vec2d dir = null;
  public Projectile(Vec2d sourcePos, Vec2d initialSize, GameWorld gameWorld, BlackBoard blackBoard, Player player){
    String id = "projectile_" + sourcePos + Math.random();
    GameObject projectile = new GameObject(sourcePos, initialSize, Constants.INTERACTABLE_LAYER, id);
    projectile.getTransform().setTravelBounds(gameWorld.getWorldSize());
    projectile.addComponent(new SpriteComponent(new Vec2d(0,2), "projectiles", projectile.getTransform(), blackBoard));
    projectile.addComponent(new CollisionComponent(CollisionShapeType.AAB, projectile.getTransform(), CollisionBehaviorType.PASS_THRU){
      @Override
      public void onCollide(){
        // on collision, delete the projectile
        for (String b : this.collidedWith){
          if (!b.contains("enemy")){
            if (b.equals("player")){
              player.onPlayerHit(20);
              ((PhysicsComponent)player.getPlayerGO().getComponent(ComponentTag.PHYSICS)).applyImpulse(dir.smult(1000));
            }
            gameWorld.deleteGameObject(id);
            break;
          }
        }
        super.onCollide();
      }
    });
    projectile.addComponent(new TickComponent(){
      @Override
      public void defineTickAction(){
        if (dir != null){
          //System.out.println("shoot");
          // if dir was initialized, then shoot toward dir on each tick
          projectile.getTransform().translatePos(dir);
        }
      }
    });

    gameWorld.addGameObject(id, projectile);
  }

  public void shoot(Vec2d dir){
    this.dir = dir;
  }

}
