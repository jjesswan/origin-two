package engine.ai.BT;

public class BehaviorTree {
  private BTNode root;

  public BehaviorTree(BTNode root){
    this.root = root;

  }

  public BTNode getRoot(){
    return this.root;
  }

}
