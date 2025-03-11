package engine.components;


import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public interface IComponent {

  void tick(long nanosSinceLastTick);
  void lateTick();
  void draw(GraphicsContext g);
  ComponentTag getTag();

  default Element saveComponent(Document doc){
    return null;
  };

  default void onMousePressed(Vec2d pressPos) {
  }

  default void onMouseReleased(Vec2d releasePos) {
  }

  default void onMouseDragged(Vec2d mousePos) {
  }

  default void onMouseClicked(Vec2d clickPos) {
  }

  default void onMouseMoved(Vec2d mousePos) {
  }

  default void onResize(Vec2d newSize) {
  }

  default void onKeyPress(KeyEvent e){

  }


//  void updateGameObjects(Map<String, GameObject> gameobjects);
}
