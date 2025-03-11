package o2.enemies.enemyAI;

import engine.ai.BT.AIUtils.Status;
import engine.ai.BT.Action;
import o2.BlackBoard;
import engine.components.TransformComponent;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import o2.player.Player;
import o2.enemies.Projectile;


public class ShootAction extends Action {
  private TransformComponent source, target;
  private GameWorld gameWorld;
  private BlackBoard blackBoard;
  private int timer = 0;
  private int FREQUENCY = 1000;
  private Player player;

  public ShootAction(TransformComponent source, TransformComponent target, GameWorld gameWorld, BlackBoard blackBoard, Player player, int freq){
    this.source = source;
    this.target = target;
    this.gameWorld = gameWorld;
    this.blackBoard = blackBoard;
    this.player = player;
    this.FREQUENCY = freq;
  }

  // launch projectile every 5 seconds
  public void launchProjectile(){
    Projectile projectile = new Projectile(this.source.getPosition(), new Vec2d(5), this.gameWorld, this.blackBoard, this.player);
    Vec2d dir = this.target.getPosition().minus(this.source.getPosition()).normalize();
    projectile.shoot(dir);
  }

  @Override
  public Status defineAction(){
    if (this.timer > FREQUENCY){
      this.timer = 0;
      this.launchProjectile();
      return Status.SUCCESS;
    }
    this.timer++;
    return Status.RUNNING;
  }

}
