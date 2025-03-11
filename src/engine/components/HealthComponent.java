package engine.components;

import o2.BlackBoard;
import engine.support.Vec2d;
import engine.ui.floatingUI.HealthBarUI;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HealthComponent implements IComponent{
  private HealthBarUI healthbar;
  private int maxHealth;
  private int currHealth;
  private Vec2d size = new Vec2d(50,4);

  public HealthComponent(TransformComponent transform, int maxHealth){
    this.maxHealth = maxHealth;
    this.currHealth = maxHealth;
    this.healthbar = new HealthBarUI(transform, size, this.maxHealth);
  }

  public HealthComponent(Element element, TransformComponent transform, BlackBoard blackBoard) {
    this.maxHealth = Integer.parseInt(element.getAttribute("maxHealth"));
    this.currHealth = Integer.parseInt(element.getAttribute("currHealth"));
    this.healthbar = new HealthBarUI(transform, size, this.maxHealth);
  }

  public void takeDamage(int amount){
    this.currHealth -= amount;
    this.healthbar.setCurrHealth(this.currHealth);
    if (this.currHealth <= 0){
      this.onDeath();
    }
  }

  public float getCurrHealthPercent(){
    return (float) this.currHealth / (float) this.maxHealth;
  }

  public void onDeath(){

  }

  public void heal(){
    this.currHealth += 1;
    if (this.currHealth > this.maxHealth) this.currHealth = currHealth;
  }


  @Override
  public void tick(long nanosSinceLastTick) {
    //this.heal();

  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {
    this.healthbar.onDraw(g);

  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.HEALTH;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    component.setAttribute("maxHealth", String.valueOf(this.maxHealth));
    component.setAttribute("currHealth", String.valueOf(this.currHealth));
    return component;
  }
}
