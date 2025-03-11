package engine.ai.BT;

import engine.ai.BT.AIUtils.Status;

public class Condition implements BTNode{
  public Condition(){

  }

  public boolean defineCondition(){
    return false;
  }

  // return success if condition is met
  @Override
  public Status onTick(long t) {
    if (this.defineCondition()) return Status.SUCCESS;
    return Status.FAIL;
  }

  @Override
  public void reset() {

  }

  @Override
  public void addChildren(BTNode node) {

  }
}
