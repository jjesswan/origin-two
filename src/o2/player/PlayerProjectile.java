package o2.player;

import o2.BlackBoard;
import engine.components.CollisionComponent;
import engine.components.SpriteComponent;
import engine.components.TickComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;

public class PlayerProjectile {
  private Vec2d dir = null;
  private Vec2d end = null;

  public PlayerProjectile(Vec2d sourcePos, Vec2d initialSize, GameWorld gameWorld, BlackBoard blackBoard, int speed){
    String id = "player_projectile_" + Math.random();
    GameObject projectile = new GameObject(sourcePos, initialSize, 50, id);
    projectile.getTransform().setTravelBounds(gameWorld.getWorldSize());
    projectile.addComponent(new SpriteComponent(new Vec2d(0), "projectiles", projectile.getTransform(), blackBoard));
    projectile.addComponent(new TickComponent(){
      @Override
      public void defineTickAction(){
        if (dir != null){
          // if dir was initialized, then shoot toward dir on each tick
          for (int i=0; i< speed; i++){
            projectile.getTransform().translatePos(dir);
          }
        }

        if (end != null){
          if (projectile.getTransform().getPosition().dist(end) < 1){
            gameWorld.deleteGameObject(id);
          }
        }
      }
    });

    projectile.addComponent(new CollisionComponent(CollisionShapeType.AAB, projectile.getTransform(), CollisionBehaviorType.PASS_THRU){
      @Override
      public void onCollide(){
        // on collision, delete the projectile
        for (String b : this.collidedWith){
          if (!b.contains("player")){
            gameWorld.deleteGameObject(id);
            break;
          }
        }
        super.onCollide();
      }
    });

    gameWorld.addGameObject(id, projectile);
  }

  public void shoot(Vec2d dir, Vec2d end){
    this.dir = dir;
    this.end = end;
  }

}
