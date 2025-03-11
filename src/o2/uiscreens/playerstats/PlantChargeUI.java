package o2.uiscreens.playerstats;

import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIImage;
import engine.ui.UISprite;
import engine.utils.SpriteReferenceLoader;
import engine.utils.Types.Alignment;
import engine.utils.Types.UIType;
import java.util.HashMap;
import java.util.Map;
import o2.files.ImageReference;

public class PlantChargeUI {
  private UISprite sprite;
  private Map<Integer, Vec2d> iconMap = new HashMap<>();
  private int MAXHEALTH = 130;
  private int currHealth = 0;


  public PlantChargeUI(Screen screen){
    this.sprite = new UISprite(new Vec2d(0), ImageReference.spriteReferences.get("ui_icons"), new Vec2d(0), new Vec2d(70), UIType.DISPLAY);
    screen.setToCorner(this.sprite, Alignment.TOPLEFT, new Vec2d(10));
    screen.addUIElement(this.sprite);
    this.initMap();
  }

  public void initMap(){
    this.iconMap.put(0, new Vec2d(0));
    this.iconMap.put(1, new Vec2d(0));
    this.iconMap.put(2, new Vec2d(1, 0));
    this.iconMap.put(3, new Vec2d(2, 0));
    this.iconMap.put(4, new Vec2d(3, 0));
    this.iconMap.put(5, new Vec2d(4, 0));
    this.iconMap.put(6, new Vec2d(0, 1));
    this.iconMap.put(7, new Vec2d(1, 1));
    this.iconMap.put(8, new Vec2d(2, 1));
    this.iconMap.put(9, new Vec2d(3, 1));
    this.iconMap.put(10, new Vec2d(4, 1));
    this.iconMap.put(11, new Vec2d(0, 2));
    this.iconMap.put(12, new Vec2d(1, 2));
    this.iconMap.put(13, new Vec2d(2, 2));
  }

  public void changeHealth(int amount){
    this.currHealth += amount;
    if (this.currHealth > MAXHEALTH) this.currHealth = MAXHEALTH;
    if (this.currHealth < 0) this.currHealth = 0;

    int stage = (int) Math.ceil((float)this.currHealth / (float)10);
    this.sprite.changeIndexPos(this.iconMap.get(stage));
  }

  public boolean isFullHealth(){
    return currHealth == MAXHEALTH;
  }

  public void depleteHealth(){
    this.currHealth = 0;
    this.changeHealth(-MAXHEALTH);
  }

  public UISprite getUIElement(){
    return this.sprite;
  }

}
