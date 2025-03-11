package engine.dialogue;

import engine.dialogue.types.Dialogue;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DialogueModel {
  public Map<String, Dialogue> dialogueMap;
  public String activeDialogueID;
  public Set<String> completedSequences = new HashSet<>();

  public DialogueModel(Map<String, Dialogue> dialogueMap, String firstID){
    this.dialogueMap = dialogueMap;
    this.activeDialogueID = firstID;
  }

}
