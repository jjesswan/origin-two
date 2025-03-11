package engine.dialogue;

import engine.Application;
import engine.dialogue.types.Dialogue;
import engine.dialogue.view.View;
import engine.screen.ScreenManager;
import java.util.List;
import java.util.Map;
import o2.App;

public class DialogueController {
  private DialogueModel model;
  private View view;
  private int timer;
  private int MAX_TIME = 400; // in ms

  public DialogueController(DialogueTreeLoader dialogueTreeLoader, String firstID, App app, ScreenManager screenManager){
    this.view = new View(app, screenManager);
    this.model = new DialogueModel(dialogueTreeLoader.loadXML(), firstID);
  }

  public void setNewDialogue(String id){

    // make sure id is a valid dialogue id
    if (!this.model.dialogueMap.containsKey(id)){
      this.model.activeDialogueID = null;
      this.view.clearTextBox();
      return;
    }

    this.model.activeDialogueID = id;

    if (id == null){
      this.view.clearTextBox();
      return;
    }

    // starts conversation based on new dialogue id
    Dialogue newDialogue = this.model.dialogueMap.get(id);
    this.view.startConversation(newDialogue, id);
    this.startTimer();
  }


  public void startTimer(){
    this.timer = MAX_TIME;
  }

  public void countdownTimer(){
    if (this.timer > 0){
      this.timer--;
    }
    if (this.timer <= 0){
      this.timer = 0;

      if (this.model.activeDialogueID != null){
        String nextID = this.model.dialogueMap.get(this.model.activeDialogueID).nextID;
        this.setNewDialogue(nextID);
      } else {
        this.setNewDialogue(null);
      }

    }

  }

  public void onTick(long nanos){
    // set timer for dialogue display
    //this.countdownTimer();
    String id = this.view.onTick(nanos);

    // if message sequence has ended, and it's time to move onto next dialogue id
    if (id == null){
      this.setNewDialogue(null);
    } else if (!id.equals(this.model.activeDialogueID)){
      this.setNewDialogue(id);
    }

  }

}
