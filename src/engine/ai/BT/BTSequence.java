package engine.ai.BT;

import com.sun.org.glassfish.external.statistics.Stats;
import engine.ai.BT.AIUtils.Status;
import java.util.ArrayList;
import java.util.List;

public class BTSequence implements BTNode {
  private List<BTNode> m_sequence = new ArrayList<>();

  @Override
  public Status onTick(long t) {
    for (BTNode node : this.m_sequence){
      if (node.onTick(t) == Status.RUNNING){
        return Status.RUNNING;
      }

      if (node.onTick(t) == Status.FAIL){
        return Status.FAIL;
      }
    }

    return Status.SUCCESS;
  }

  @Override
  public void reset() {

  }

  @Override
  public void addChildren(BTNode node) {
    this.m_sequence.add(node);
  }
}
