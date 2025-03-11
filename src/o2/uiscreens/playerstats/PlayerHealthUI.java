package o2.uiscreens.playerstats;

import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIPolygon;
import engine.ui.UIText;
import engine.utils.Types.Alignment;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.paint.Color;
import o2.Constants;

public class PlayerHealthUI {
  private UIPolygon healthbar, healthfill;
  private double BAR_HEIGHT = 7;
  private double BAR_MAX_WIDTH = 100;
  private double SLANT = 5;
  private UIText healthText;
  private double currLength = BAR_MAX_WIDTH;
  private Color color = Constants.ELECTRIC_BLUE;

  private int maxHealth, currHealth;



  public PlayerHealthUI(Screen screen, int maxHealth){
    this.maxHealth = maxHealth;
    this.currHealth = maxHealth;
    this.healthbar = new UIPolygon(new Vec2d(0), new Vec2d(BAR_MAX_WIDTH, BAR_HEIGHT), Color.WHITE, UIType.DISPLAY, FillType.STROKE, SLANT);
    this.healthfill = new UIPolygon(new Vec2d(0), new Vec2d(BAR_MAX_WIDTH, BAR_HEIGHT), this.color, UIType.DISPLAY, FillType.FILL, SLANT);
    this.healthText = new UIText(this.currHealth + "/" + this.maxHealth, new Vec2d(0), new Vec2d(20), Color.WHITE, UIType.DISPLAY, Constants.FONT);

    this.healthbar.setStrokeWidth(1.4);
    screen.setToCorner(this.healthbar, Alignment.TOPLEFT, new Vec2d(100, 20));
    screen.addUIElement(this.healthbar);
    this.healthbar.setToCorner(this.healthfill, Alignment.TOPLEFT, new Vec2d(0));
    this.healthbar.addChildElement(this.healthfill);
    this.healthbar.appendToRight(this.healthText, 10);
    this.healthbar.horizCenterElement(this.healthText);
    this.healthbar.addChildElement(this.healthText);
  }

  public void changeHealth(int amount){
    this.currHealth += amount;
    if (currHealth < 0) this.currHealth = 0;
    if (currHealth > this.maxHealth) this.currHealth = this.maxHealth;
    this.healthText.setText(this.currHealth + "/" + this.maxHealth);
    this.checkColor();
    this.checkSize();

  }

  public void checkSize(){
    float percent = (float) currHealth / (float) maxHealth;
    this.currLength = BAR_MAX_WIDTH * percent;
    this.healthfill.setSize(new Vec2d(this.currLength, BAR_HEIGHT));

  }

  public void checkColor(){
    float percent = (float) currHealth / (float) maxHealth;

    if (percent >= .75) this.color = Constants.ELECTRIC_BLUE;
    if (percent > .25 && percent < .75) this.color = Color.rgb(255, 180, 75);
    if (percent <= .25) this.color = Color.rgb(255, 0, 0);
    if (percent <= 0.1) this.color = Color.grayRgb(100);

    this.healthfill.changeColor(this.color);
  }

  public void onHit(){

  }

  public void onHeal(){

  }

  public boolean checkDeath(){
    return this.currHealth <= 0;
  }

}
