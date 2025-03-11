package o2.enemies.enemyAI;

import engine.ai.BT.AIUtils.Status;
import engine.ai.BT.Action;
import o2.BlackBoard;
import engine.components.TransformComponent;
import engine.gameworld.GameWorld;
import engine.raycast.RayCollisionInfo;
import engine.raycast.RayLine;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import o2.player.Player;

public class RayShootAction extends Action {
  private TransformComponent source, target;
  private GameWorld gameWorld;
  private BlackBoard blackBoard;
  private int timer = 0;
  private int FREQUENCY = 1000;
  private Player player;
  private String sourceID;

  public RayShootAction(String sourceID, GameWorld gameWorld, BlackBoard blackBoard, Player player, int freq){
    this.sourceID = sourceID;
    this.gameWorld = gameWorld;
    this.blackBoard = blackBoard;
    this.player = player;
    this.FREQUENCY = freq;
  }

  // launch projectile every 5 seconds
  public void shootRay(){
    Vec2d target = this.player.getPlayerGO().getTransform().getPosition();
    RayCollisionInfo rayCollisionInfo = this.gameWorld.shootRay(this.sourceID, target);

    Vec2d end = target;
    if (rayCollisionInfo != null) end = rayCollisionInfo.intersection;

    new RayLine(rayCollisionInfo, this.gameWorld, Color.rgb(135, 255, 141), 1, 20);

    if (rayCollisionInfo.intersection.dist(target) < .10){
      this.onRayHit(rayCollisionInfo);
    }


  }

  // to be overridden with game specific logic on ray hit
  public void onRayHit(RayCollisionInfo rayCollisionInfo){

  }

  @Override
  public Status defineAction(){
    if (this.timer > FREQUENCY){
      this.timer = 0;
      this.shootRay();
      return Status.SUCCESS;
    }
    this.timer++;
    return Status.RUNNING;
  }

}
