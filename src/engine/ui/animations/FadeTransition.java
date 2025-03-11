package engine.ui.animations;

import javafx.scene.canvas.GraphicsContext;

public class FadeTransition implements ITransition {
  private double fromValue, toValue, step;

  public FadeTransition(double fromValue, double toValue, double duration){
    this.fromValue = fromValue;
    this.toValue = toValue;
    this.step = (this.fromValue - this.toValue)/duration;

  }


  @Override
  public void playTransition(GraphicsContext g){
    if (Math.abs(this.fromValue - this.toValue) < .0001){
      g.setGlobalAlpha(this.toValue);
      return;
    }

    g.setGlobalAlpha(this.fromValue -= this.step);
  }

  @Override
  public void clear(GraphicsContext g){
    g.setGlobalAlpha(1);
  }

}
