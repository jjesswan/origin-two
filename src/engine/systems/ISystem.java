package engine.systems;

import engine.gameworld.GameObject;
import engine.support.Vec2d;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import javafx.util.Pair;

public interface ISystem {
  void tick(long nanosSinceLastTick);
  void lateTick();
  void draw(GraphicsContext g);
  SystemTag getTag();
  void updateGameObjects(String label, GameObject go);
  void removeGameObjects(String label);


  default void onMousePressed(Vec2d pos) {
  }

  default void onMouseReleased(Vec2d pos) {
  }

  default void onMouseDragged(Vec2d pos) {
  }

  default void onMouseClicked(Vec2d pos) {
  }
  default void onMouseMoved(Vec2d pos) {
  }

  default Set<Pair<GameObject, GameObject>> getCollidingPairs(){return null;}

  default void updateCollidingPairs(Set<Pair<GameObject, GameObject>> pair){}

  default void onKeyPress(KeyEvent e){}
  default void onKeyHold(KeyEvent e){}




}
