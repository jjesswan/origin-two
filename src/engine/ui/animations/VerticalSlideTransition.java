package engine.ui.animations;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class VerticalSlideTransition implements ITransition {
  private double start, end, step;
  private double currTranslation = 0;

  public VerticalSlideTransition(double amount, double duration){
    this.start = 0;
    this.end = amount;
    this.step = (amount)/duration;

  }

  @Override
  public void playTransition(GraphicsContext g) {
    if (this.end - this.start < .0001){
      this.currTranslation = this.end;
      g.translate(0, this.end);
      return;
    }

    this.currTranslation = this.start += this.step;
    g.translate(0, this.currTranslation);
  }

  @Override
  public void clear(GraphicsContext g){
    g.translate(0, -this.currTranslation);
  }
}
