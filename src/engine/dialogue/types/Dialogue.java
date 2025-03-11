package engine.dialogue.types;

import java.util.List;

public class Dialogue {

  public String author;
  public List<String> messages;
  public List<DialogueOption> options;
  public String nextID = null;

  public Dialogue(String author, List<String> messages, List<DialogueOption> options){
    this.author = author;
    this.messages = messages;
    this.options = options;
  }
}
