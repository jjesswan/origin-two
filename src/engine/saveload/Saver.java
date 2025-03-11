package engine.saveload;

import engine.gameworld.GameWorld;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Saver {
  private final GameWorld gameWorld;
  private final String rootDirectory;

  public Saver(GameWorld gameWorld, String rootDirectory){
    this.gameWorld = gameWorld;
    this.rootDirectory = rootDirectory;

  }

  public void writeXML(String filename, Map<String, String> metadata) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = factory.newDocumentBuilder();
      Document doc = documentBuilder.newDocument();
      Element game = doc.createElement("Game");

      // save level id / metadata
      Element meta = doc.createElement("Metadata");
      for (Map.Entry<String, String> m : metadata.entrySet()){
        meta.setAttribute(m.getKey(), m.getValue());
      }
      game.appendChild(meta);
      // save gameworld
      game.appendChild(this.gameWorld.saveGameWorld(doc));
      doc.appendChild(game);


      File file = new File( this.rootDirectory + "/files/" + filename + ".xml");
      // write dom document to a file
      try (FileOutputStream output = new FileOutputStream(file)) {
        writeXml(doc, output);
        System.out.println("wrote to file");
      } catch (IOException e) {
        e.printStackTrace();
      }

    } catch (ParserConfigurationException | TransformerException e){
      System.err.println(e);
    }
  }

  // write doc to output stream
  // reference: https://mkyong.com/java/how-to-create-xml-file-in-java-dom/#write-xml-to-a-file
  private static void writeXml(Document doc, OutputStream output) throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    // pretty print XML
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);
    transformer.transform(source, result);
  }

}
