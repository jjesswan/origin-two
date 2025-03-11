package o2.enemies.enemyAI;

import engine.ai.BT.AIUtils.Status;
import engine.ai.BT.Action;
import engine.gameworld.GameObject;
import engine.support.Vec2d;

public class IdlePaceAction extends Action {
  private Vec2d distance, culmulative; // the full distance between two endpoints of travel
  protected Vec2d travelAmount;
  private GameObject go;

  public IdlePaceAction(GameObject go, Vec2d distance, double speed){
    this.distance = distance;
    this.go = go;
    this.culmulative = new Vec2d(0);

    this.travelAmount = distance.normalize().smult(speed);

  }

  // pace by a certain speed
  private void pace(){
    this.go.getTransform().translatePos(this.travelAmount);
    this.culmulative = this.culmulative.plus(this.travelAmount);

    // if cumulative distance is the same as intended distance, then switch directions
    if (Math.abs(this.culmulative.mag() - this.distance.mag()) < .5){
      this.culmulative = new Vec2d(0);
      this.travelAmount = this.travelAmount.smult(-1);
      this.switchPaceSprite();
    }
  }

  // if sprite should switch dir based on pace
  protected void switchPaceSprite(){
  }

  @Override
  public Status defineAction(){
    this.pace();
    return Status.SUCCESS;
  }


}
