package engine.ai.BT.conditions;

import com.sun.org.apache.bcel.internal.generic.ARETURN;
import engine.ai.BT.Condition;
import engine.components.TransformComponent;
import engine.support.Vec2d;

public class ProximityCondition extends Condition {
  private TransformComponent transform;
  private TransformComponent targetTransform = null;
  private Vec2d targetLoc = null;
  private double proximity;

  public ProximityCondition(TransformComponent transform, TransformComponent target, double proximity){
    this.transform = transform;
    this.targetTransform = target;
    this.proximity = proximity;
  }


  @Override
  public boolean defineCondition(){
    if (this.targetTransform != null){
      return this.transform.getPosition().dist2(this.targetTransform.getPosition()) < this.proximity;
    }
    return this.targetLoc != null &&
        this.transform.getPosition().dist2(this.targetLoc) < this.proximity;
  }


}
