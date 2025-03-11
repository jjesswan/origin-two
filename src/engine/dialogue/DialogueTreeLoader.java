package engine.dialogue;

import engine.dialogue.types.Dialogue;
import engine.dialogue.types.DialogueOption;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DialogueTreeLoader {
  private String relativePath;

  public DialogueTreeLoader(String rootDirectory, String filename){
    this.relativePath = rootDirectory + "/files/" + filename + ".xml";
  }

  public Map<String, Dialogue> loadXML() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      File file = new File(this.relativePath);
      Document doc = docBuilder.parse(file);
      doc.getDocumentElement().normalize();

      // initialize game objects
      NodeList nodeList = doc.getElementsByTagName("Dialogue");
      Map<String, Dialogue> dialogueMap = new HashMap<>();

      for (int i=0; i<nodeList.getLength(); i++) {
        Element d = (Element) nodeList.item(i);

        String author = d.getAttribute("author");
        String id = d.getAttribute("id");

        // get list of dialogue objects
        NodeList messages = d.getElementsByTagName("Message");

        // gets the nextID of the last message in the message sequence
        int lastIndex = messages.getLength() - 1;
        String nextDialogueID = ((Element) messages.item(lastIndex)).getAttribute("nextID");

        List<String> messageSequence = new ArrayList<>();
        for (int k = 0; k < messages.getLength(); k++) {
          messageSequence.add(messages.item(k).getTextContent());
        }

          Dialogue dialogue = null;
          List<DialogueOption> options = new ArrayList<>();
          if (!nextDialogueID.equals("")) {
            dialogue = new Dialogue(author, messageSequence, options);
            dialogue.nextID = nextDialogueID;
          } else {
            // check for options
            NodeList choices = d.getElementsByTagName("Choice");
            for (int j = 0; j < choices.getLength(); j++) {
              Element choice = (Element) choices.item(j);
              String nextID = choice.getAttribute("nextID");
              String text = choice.getTextContent();
              DialogueOption option = new DialogueOption(text, nextID);
              options.add(option);
            }
            dialogue = new Dialogue(author, messageSequence, options);
          }

          // make new Dialogue object
          dialogueMap.put(id, dialogue);
      }

      System.out.println("LOADED DIALOGUE TREE!");
      return dialogueMap;

    } catch (ParserConfigurationException | IOException | SAXException e) {
      System.err.println(e);
    }

    return new HashMap<>();
  }

}
