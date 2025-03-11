package engine.ui.floatingUI;

import engine.components.TransformComponent;
import engine.screen.Viewport;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthBarUI extends UIElement {
  private Color color;
  private int maxHealth;
  private int currHealth;
  private TransformComponent transform;
  private double currLength;
  private int countdown = 0;
  private final int MAX_TIME = 200;
  private boolean showBar = false;

  public HealthBarUI(TransformComponent transform, Vec2d initSize, int maxHealth) {
    super(transform.getPosition().plus(0, -3), initSize, Color.grayRgb(0), UIType.DISPLAY);
    this.maxHealth = maxHealth;
    this.currHealth = maxHealth;
    this.transform = transform;
    this.checkColor();
    this.checkSize();
  }

  public Vec2d getUIPosition(){
    Vec2d offset = this.transform.getSize().smult(-1);
    Vec2d gamePos = this.transform.getPosition().plus((float) ((float) offset.x * .50),
        (float) ((float) offset.y * .2));
    return gamePos;
  }

  public void setCurrHealth(int currHealth){
    // if currhealth is different, then draw the bar and start countdown
    if (currHealth != this.currHealth){
      this.showBar = true;
      this.countdown = MAX_TIME;
    }
    this.currHealth = currHealth;
    if (currHealth < 0) this.currHealth = 0;
    if (currHealth > this.maxHealth) this.currHealth = this.maxHealth;
    this.checkColor();
    this.checkSize();

  }

  public void checkSize(){
    float percent = (float) currHealth / (float) maxHealth;
    this.currLength = super.size.x * percent;
  }

  public void checkColor(){
    float percent = (float) currHealth / (float) maxHealth;

    if (percent >= .75) this.color = Color.rgb(0, 255, 0);
    if (percent > .25 && percent < .75) this.color = Color.rgb(255, 180, 75);
    if (percent <= .25) this.color = Color.rgb(255, 0, 0);
    if (percent <= 0.1) this.color = Color.grayRgb(100);
  }

  @Override
  public void onDraw(GraphicsContext g) {

    this.countdown--;

    if (this.countdown == 0){
      this.countdown = 0;
      this.showBar = false;
    }

    if (this.showBar) {
      g.setFill(this.color);
      g.setStroke(Color.WHITE);
      g.setLineWidth(.5);
      g.restore();

      Vec2d pos = this.getUIPosition();
      g.fillRoundRect(pos.x, pos.y, this.currLength, super.size.y, 2, 2);
      g.strokeRoundRect(pos.x, pos.y, super.size.x, super.size.y, 2, 2);
      super.onDraw(g);
    }
  }

}
