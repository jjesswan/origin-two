package engine.ai.BT;

import engine.ai.BT.AIUtils.Status;
import java.util.ArrayList;
import java.util.List;

public class BTSelector implements BTNode{
  private List<BTNode> m_children = new ArrayList<>();
  private BTNode m_selected_node;

  @Override
  public Status onTick(long t) {
    for (BTNode node : this.m_children){
      Status result = node.onTick(t);

      // select this one and return its status --> this node is currently running
      if (result != Status.FAIL){
        this.m_selected_node = node;
        return result;
      }
    }

    // otherwise if all children fail, then fail this selector
    return Status.FAIL;
  }

  @Override
  public void reset() {

  }

  @Override
  public void addChildren(BTNode node) {
    this.m_children.add(node);

  }
}
