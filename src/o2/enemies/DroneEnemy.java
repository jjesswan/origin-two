package o2.enemies;

import engine.ai.BT.BTNode;
import engine.ai.BT.BTSelector;
import engine.ai.BT.BTSequence;
import engine.ai.BT.BehaviorTree;
import engine.ai.BT.actions.PathfindAction;
import engine.ai.BT.conditions.ProximityCondition;
import o2.BlackBoard;
import engine.ai.Pathfinder;
import engine.components.AIComponent;
import engine.components.AnimationComponent;
import engine.components.AnimationSequenceComponent;
import engine.components.CollisionComponent;
import engine.components.ComponentTag;
import engine.components.HealthComponent;
import engine.components.PhysicsComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.raycast.RayCollisionInfo;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import o2.Constants;
import o2.enemies.enemyAI.IdlePaceAction;
import o2.enemies.enemyAI.RayShootAction;
import o2.loot.drops.ManaDrop;
import o2.player.Player;

public class DroneEnemy {

  private GameObject enemy;
  private Player player;
  private Pathfinder pathfinder;
  private GameWorld gameWorld;
  private BlackBoard blackBoard;
  private double speed;

  public DroneEnemy(Player player, Pathfinder pathfinder,
      Vec2d initialPos, Vec2d size, GameWorld gameWorld, BlackBoard blackBoard, double speed){
    this.player = player;
    this.pathfinder = pathfinder;
    this.gameWorld = gameWorld;
    this.blackBoard = blackBoard;
    this.speed = speed;
    this.makeEnemy(initialPos, size);

  }

  public void makeEnemy(Vec2d initialPos, Vec2d size){
    String id = "drone_enemy_" + initialPos + "_" + Math.random();
    this.enemy = new GameObject(initialPos, size, Constants.INTERACTABLE_LAYER, id);
    this.enemy.addComponent(new CollisionComponent(CollisionShapeType.AAB, enemy.getTransform(), CollisionBehaviorType.DYNAMIC));

    // animation component
    AnimationComponent rightFacing = this.makeAnimationComponent(
        Arrays.asList(new Vec2d(0, 0), new Vec2d(1,0), new Vec2d(2,0)));
    AnimationComponent leftFacing = this.makeAnimationComponent(
        Arrays.asList(new Vec2d(0, 1), new Vec2d(1,1), new Vec2d(2,1)));
    List<AnimationComponent> animations = new ArrayList<>(Arrays.asList(rightFacing, leftFacing));

    this.enemy.addComponent(new AnimationSequenceComponent(animations));

    // ai component
    this.enemy.addComponent(new AIComponent(this.makeBT()));

    // physics
    enemy.addComponent(new PhysicsComponent(enemy.getTransform(), 50, .3, CollisionBehaviorType.DYNAMIC));
    ((PhysicsComponent) enemy.getComponent(ComponentTag.PHYSICS)).isFlying(true);

    // health
    enemy.addComponent(new HealthComponent(enemy.getTransform(), 1200){
      @Override
      public void onDeath(){
        new ManaDrop(enemy.getTransform().getPosition(), new Vec2d(7), 2, 30, player, gameWorld);
        gameWorld.deleteGameObject(id);
      }
    });

    // tick


    this.gameWorld.updateGameObjects(id, this.enemy);
  }

  public AnimationComponent makeAnimationComponent(List<Vec2d> sequence){
    return new AnimationComponent(sequence, null, "drone", this.enemy.getTransform());
  }

  public BehaviorTree makeBT(){
    BTNode proximCond = new ProximityCondition(enemy.getTransform(), this.player.getPlayerGO().getTransform(), 80000);
    PathfindAction pathfindAction = new PathfindAction(this.pathfinder, enemy.getTransform(), this.player.getPlayerGO(), this.speed);
    pathfindAction.setStoppingDistance(1000);

//    // pathfind sequence
//    BTNode pathfindSeq = new BTSequence();
//    pathfindSeq.addChildren(proximCond);
//    pathfindSeq.addChildren(pathfindAction);

    // shoot sequence
    BTNode shootSeq = new BTSequence();
    shootSeq.addChildren(proximCond);
    shootSeq.addChildren(new RayShootAction(this.enemy.getId(), this.gameWorld, this.blackBoard, this.player,
        (int) (500*Math.random())){
      @Override
      public void onRayHit(RayCollisionInfo rayCollisionInfo){
        player.onPlayerHit(270);
        ((PhysicsComponent)player.getPlayerGO().getComponent(ComponentTag.PHYSICS)).applyImpulse(rayCollisionInfo.ray.dir.smult(1000));
      }
    });

    // idle wandering sequence
    BTNode idleSeq = new BTSequence();
    idleSeq.addChildren(new IdlePaceAction(enemy, new Vec2d(300*Math.random(), 0), this.speed * .1){
      @Override
      protected void switchPaceSprite(){
        if (this.travelAmount.x > 0){
          ((AnimationSequenceComponent)enemy.getComponent(ComponentTag.ANIMATION_SEQUENCE)).setActiveSequence(0);
        } else {
          ((AnimationSequenceComponent)enemy.getComponent(ComponentTag.ANIMATION_SEQUENCE)).setActiveSequence(1);
        }
      }
    });



    // root
    BTNode bt_root = new BTSelector();
    //bt_root.addChildren(pathfindSeq);
    bt_root.addChildren(shootSeq);
    bt_root.addChildren(idleSeq);

    return new BehaviorTree(bt_root);
  }

}
