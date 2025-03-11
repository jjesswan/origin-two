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
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import o2.Constants;
import o2.loot.drops.ManaDrop;
import o2.player.Player;
import o2.enemies.enemyAI.ShootAction;

public class BatEnemy {

  private GameObject enemy;
  private Player player;
  private Pathfinder pathfinder;
  private GameWorld gameWorld;
  private BlackBoard blackBoard;
  private double speed;

  public BatEnemy(Player player, Pathfinder pathfinder,
      Vec2d initialPos, Vec2d size, GameWorld gameWorld, BlackBoard blackBoard, double speed){
    this.player = player;
    this.pathfinder = pathfinder;
    this.gameWorld = gameWorld;
    this.blackBoard = blackBoard;
    this.speed = speed;
    this.makeEnemy(initialPos, size);

  }

  public void makeEnemy(Vec2d initialPos, Vec2d size){
    String id = "bat_enemy_" + Math.random();
    this.enemy = new GameObject(initialPos, size, Constants.INTERACTABLE_LAYER, id);
    this.enemy.addComponent(new CollisionComponent(CollisionShapeType.AAB, enemy.getTransform(), CollisionBehaviorType.DYNAMIC));

    // animation component
    AnimationComponent animation = this.makeAnimationComponent(Arrays.asList(new Vec2d(0, 0), new Vec2d(1,0), new Vec2d(2,0), new Vec2d(3,0)), new Vec2d(0));
    List<AnimationComponent> animations = new ArrayList<>(Arrays.asList(animation));

    this.enemy.addComponent(new AnimationSequenceComponent(animations));

    // ai component
    this.enemy.addComponent(new AIComponent(this.makeBT()));
    enemy.addComponent(new PhysicsComponent(enemy.getTransform(), 50, .3, CollisionBehaviorType.DYNAMIC));
    ((PhysicsComponent) enemy.getComponent(ComponentTag.PHYSICS)).isFlying(true);
    enemy.addComponent(new HealthComponent(enemy.getTransform(), 900){
      @Override
      public void onDeath(){
        new ManaDrop(enemy.getTransform().getPosition(), new Vec2d(10), 3, 20, player, gameWorld);
        gameWorld.deleteGameObject(id);
      }
    });


    this.gameWorld.updateGameObjects(id, this.enemy);
  }

  public AnimationComponent makeAnimationComponent(List<Vec2d> sequence, Vec2d idle){
    return new AnimationComponent(sequence, null, "small_enemy", this.enemy.getTransform());
  }

  public BehaviorTree makeBT(){
    BTNode proximCond = new ProximityCondition(enemy.getTransform(), this.player.getPlayerGO().getTransform(), 30000);
    PathfindAction pathfindAction = new PathfindAction(this.pathfinder, enemy.getTransform(), this.player.getPlayerGO(), this.speed);
    pathfindAction.setStoppingDistance(200);

    // pathfind sequence
    BTNode pathfindSeq = new BTSequence();
    pathfindSeq.addChildren(proximCond);
    pathfindSeq.addChildren(pathfindAction);

    // idle sequence here
    BTNode shootSeq = new BTSequence();
    shootSeq.addChildren(proximCond);
    shootSeq.addChildren(new ShootAction(this.enemy.getTransform(), this.player.getPlayerGO().getTransform(), this.gameWorld, this.blackBoard, this.player, 50));


    // root
    BTNode bt_root = new BTSelector();
    bt_root.addChildren(pathfindSeq);
    bt_root.addChildren(shootSeq);


    return new BehaviorTree(bt_root);
  }

}
