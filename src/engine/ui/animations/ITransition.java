package engine.ui.animations;

import javafx.scene.canvas.GraphicsContext;

public interface ITransition {
  void playTransition(GraphicsContext g);
  void clear(GraphicsContext g);

}
