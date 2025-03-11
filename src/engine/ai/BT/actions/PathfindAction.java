package engine.ai.BT.actions;

import engine.ai.BT.AIUtils.Status;
import engine.ai.BT.Action;
import engine.ai.Pathfinder;
import engine.components.TransformComponent;
import engine.gameworld.GameObject;
import engine.support.Vec2d;
import java.util.ArrayList;
import java.util.List;

public class PathfindAction extends Action  {
  private Pathfinder pathfinder;
  private TransformComponent transform;
  private List<Vec2d> pathway = new ArrayList<>();
  private boolean findingPath = false;
  private boolean finishedPath = false;
  private double stoppingDistance = 0;
  private GameObject targetObject;
  private Vec2d currTarget = null;
  private double speed; // speed of pathfinding

  public PathfindAction(Pathfinder pathfinder, TransformComponent transform, GameObject targetObject, double speed){
    this.pathfinder = pathfinder;
    this.transform = transform;
    this.targetObject = targetObject;
    this.speed = speed;
  }

  public void setPathway(Vec2d B){
    //System.out.println("find path from :" + this.transform.getPosition() + ", to: " + B);
    this.pathway = this.pathfinder.findPath(this.transform.getPosition(), B);
    this.findingPath = true;
    this.finishedPath = false;
  }

  public void setPathway(TransformComponent target){
    this.currTarget = target.getPosition();
    this.pathway = this.pathfinder.findPath(this.transform.getPosition(), this.currTarget);
    this.findingPath = true;
    this.finishedPath = false;
  }

  public void checkUpdatePathway(){
    // if new target is different enough from current target, update the pathway
    Vec2d newTarget = this.targetObject.getTransform().getPosition();
    if (newTarget != null){
      if (this.currTarget == null) {
        this.currTarget = newTarget;
        this.setPathway(this.currTarget);
      } else if (newTarget.dist2(this.currTarget) > 2){
        this.currTarget = newTarget;
        this.setPathway(this.currTarget);
      }
    }

  }

  public void clearPathway(){
    this.pathway = new ArrayList<>();
    this.findingPath = false;
    this.finishedPath = false;
  }

  public void setStoppingDistance(double stoppingDistance){
    this.stoppingDistance = stoppingDistance;
  }

  public Status navigatingPath(){
    if (!this.pathway.isEmpty()){
      Vec2d vector = this.pathway.get(this.pathway.size() - 1).minus(this.transform.getPosition());

      // move to next position point if entity is close by
      if (this.pathway.size() == 1 && vector.mag() < this.stoppingDistance){
        this.pathway.remove(this.pathway.size() - 1);
        return Status.SUCCESS;
      }
      if (vector.mag() < 2) {
        this.pathway.remove(this.pathway.size() - 1);
        return Status.RUNNING;
      }
      Vec2d dir = vector.normalize();
      this.transform.translatePos(dir.smult(this.speed));
      return Status.RUNNING;
    } else {
      if (this.findingPath){
        this.finishedPath = true;
        return Status.SUCCESS;
      }
    }

    return Status.FAIL;
  }

  @Override
  public Status defineAction(){
    this.checkUpdatePathway();
    this.navigatingPath();
    if (this.pathway.isEmpty() && !this.findingPath) {
      return Status.FAIL; // never found path
    }
    if (this.pathway.isEmpty() && this.findingPath){
      this.findingPath = false;
      return Status.SUCCESS; // finished travelling the path
    }

    // in process of navigating path
    if (this.findingPath && !this.finishedPath){
      return Status.RUNNING;
    }

    return Status.FAIL;
  }


}
