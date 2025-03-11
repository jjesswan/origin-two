package engine.components;

import engine.ai.BT.AIUtils.Status;
import engine.ai.BT.BehaviorTree;
import javafx.scene.canvas.GraphicsContext;

public class AIComponent implements IComponent{
  private BehaviorTree behaviorTree;
  private Status currStatus = Status.SUCCESS;

  public AIComponent(BehaviorTree behaviorTree){
    this.behaviorTree = behaviorTree;
  }

  @Override
  public void tick(long nanosSinceLastTick) {
    this.currStatus = this.behaviorTree.getRoot().onTick(nanosSinceLastTick);
  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.AI;
  }
}
