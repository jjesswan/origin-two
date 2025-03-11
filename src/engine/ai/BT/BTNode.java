package engine.ai.BT;

import engine.ai.BT.AIUtils.Status;

public interface BTNode {
  Status onTick(long t);
  void reset();
  void addChildren(BTNode node);

}
