package o2.loot.drops;

import engine.components.CollisionComponent;
import engine.components.ComponentTag;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import java.awt.Color;
import o2.Constants;
import o2.SoundManager;
import o2.player.Player;

public class ManaDrop {

  public ManaDrop(Vec2d sourcePos, Vec2d size, int amount, int chargeAmount, Player player, GameWorld gameWorld){
    for (int i=0; i<amount; i++){
      String id = "mana_drop_" + sourcePos + "_" + Math.random() + "_" + i;
      GameObject go = new GameObject(sourcePos, size, Constants.INTERACTABLE_LAYER, id);
      go.addComponent(new SpriteComponent(new Vec2d(0,1), "projectiles", go.getTransform(), null));
      go.addComponent(new PhysicsComponent(go.getTransform(), 100, .1, CollisionBehaviorType.DYNAMIC));
      go.addComponent(new CollisionComponent(CollisionShapeType.AAB, go.getTransform(), CollisionBehaviorType.DYNAMIC){
        @Override
        public void onCollide(){
          // on collision, delete the projectile
          if (this.collidedWith.contains("player")){
            System.out.println("mana");
            SoundManager.sounds.get("mana").playOnce();
            player.addPlantCharge(chargeAmount);
            gameWorld.deleteGameObject(id);
          }
          super.onCollide();
        }
      });

      gameWorld.addGameObject(id, go);
      ((PhysicsComponent)go.getComponent(ComponentTag.PHYSICS)).applyImpulse(new Vec2d((.5-Math.random())*20, 10));
    }


  }

}
