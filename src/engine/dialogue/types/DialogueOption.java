package engine.dialogue.types;

public class DialogueOption {
  public String message;
  public String nextID;

  public DialogueOption(String message, String nextDialogueID){
    this.message = message;
    this.nextID = nextDialogueID;
  }

}
