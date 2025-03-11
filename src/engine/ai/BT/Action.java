package engine.ai.BT;

import engine.ai.BT.AIUtils.Status;

public class Action implements BTNode{

  public Status defineAction(){
    return Status.FAIL;
  }

  @Override
  public Status onTick(long t) {
   return this.defineAction();
  }

  @Override
  public void reset() {

  }

  @Override
  public void addChildren(BTNode node) {

  }
}
