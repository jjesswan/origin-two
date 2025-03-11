package engine.gameworld;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators.NthDescendantIterator;
import engine.components.CenterComponent;
import engine.components.ComponentTag;
import engine.components.IComponent;
import engine.components.TransformComponent;
import engine.screen.Viewport;
import engine.support.Vec2d;
import engine.utils.PolygonBuilder;
import engine.utils.SpriteReferenceLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javax.swing.text.View;

public class GameObject {

  private Map<ComponentTag, IComponent> components;
  private TransformComponent transform;
  private int zIndex;
  private String id;
  private CenterComponent centerComponent = null;

  public GameObject(Vec2d initPos, Vec2d initSize, int zIndex, String id){
    this.zIndex = zIndex;
    this.components = new HashMap<>();
    this.transform = new TransformComponent(initPos, initSize);
    this.id = id;
  }

  public GameObject(Vec2d initPos, Vec2d initSize, int zIndex, String id, List<PolygonBuilder> polygonBuilder){
    this.zIndex = zIndex;
    this.components = new HashMap<>();
    this.transform = new TransformComponent(initPos, initSize, polygonBuilder);
    this.id = id;
  }

  public void addPolygonBuilders(List<PolygonBuilder> builders){
    this.transform.addPolygonBuilders(builders);

  }

  public void makeCenterFocus(Viewport viewport){
    this.centerComponent = new CenterComponent(this.getTransform(), viewport);
  }

  public int getZIndex(){
    return this.zIndex;
  }


  public void addComponent(IComponent c){
    this.components.put(c.getTag(), c);

  }


  public void removeComponent(IComponent c){
    this.components.remove(c.getTag());
  }

  public IComponent getComponent(ComponentTag componentType){
    if (this.components.containsKey(componentType)){
      return this.components.get(componentType);
    }

    return null;
  }

  public Map<ComponentTag, IComponent> getAllComponents(){
    return this.components;
  }

  public boolean hasComponent(ComponentTag componentType){
    return this.components.containsKey(componentType);
  }

  public CenterComponent getCenterComponent(){
    return this.centerComponent;
  }

  public TransformComponent getTransform(){
    return this.transform;
  } // you can also add a setter for this

  public void tick(long t){

  }
  public void lateTick() {

  }

  public void draw(GraphicsContext g){

  }

  public String getId(){
    return this.id;
  }
}
