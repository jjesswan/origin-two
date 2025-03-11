package engine.saveload;
import o2.BlackBoard;
import engine.components.ComponentMapping;
import engine.components.ComponentTag;
import engine.components.IComponent;
import engine.components.TransformComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Loader {
  String rootDirectory;
  public Loader(String rootDirectory){
    this.rootDirectory = rootDirectory;

  }

  public GameWorld loadXML(String filename, BlackBoard blackBoard) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      File file = new File( this.rootDirectory + "/files/" + filename + ".xml");
      Document doc = docBuilder.parse(file);
      doc.getDocumentElement().normalize();

      // initialize game objects
      NodeList nodeList = doc.getElementsByTagName("GameObject");
      Map<String, GameObject> gameobjects = new HashMap<>();

      for (int i=0; i<nodeList.getLength(); i++){
        Element go = (Element) nodeList.item(i);

        Vec2d pos = new Vec2d(go.getAttribute("x"),go.getAttribute("y"));
        Vec2d size = new Vec2d(go.getAttribute("w"),go.getAttribute("h"));
        int zIndex = Integer.parseInt(go.getAttribute("zIndex"));
        String id = go.getAttribute("id");


        GameObject gameObject =  new GameObject(pos, size, zIndex, go.getAttribute("id"));

        NodeList comps = go.getElementsByTagName("Component");
        for (int j=0; j<comps.getLength(); j++){
            Element e = (Element) comps.item(j);
            ComponentTag tag = ComponentTag.valueOf(e.getAttribute("id"));
            Class<? extends IComponent> classType = ComponentMapping.componentMap.get(tag);
            IComponent component = classType.getConstructor(Element.class, TransformComponent.class, BlackBoard.class).newInstance(
                e, gameObject.getTransform(), blackBoard);
            gameObject.addComponent(component);
        }

        gameobjects.put(id, gameObject);
      }

      System.out.println("LOADED!");
      return new GameWorld(gameobjects);

    } catch (ParserConfigurationException | IOException | SAXException e) {
      System.err.println(e);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    return new GameWorld(new HashMap<>());
  }

}
