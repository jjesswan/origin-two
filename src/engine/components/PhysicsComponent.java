package engine.components;

import o2.BlackBoard;
import engine.gameworld.GameObject;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhysicsComponent implements IComponent{

  private final double mass, u_static, u_kinetic;
  private Vec2d velocity = new Vec2d(0), impulse = new Vec2d(0), force = new Vec2d(0);
  private TransformComponent transform;
  private boolean isGrounded = false;
  private double r;
  private boolean isStatic = true;
  private double normalForce = 0;
  private CollisionBehaviorType behaviorType;
  private boolean isFlying = false;

  public PhysicsComponent(TransformComponent transform, double mass, double restitution, CollisionBehaviorType collisionBehaviorType){
    this.transform = transform;
    this.mass = mass;
    this.r = restitution;
    this.u_kinetic = 0;
    this.u_static = 0;
    if (restitution > 1) this.r = 1;
    if (restitution < 0) this.r = 0;

    this.behaviorType = collisionBehaviorType;
    if (this.behaviorType == CollisionBehaviorType.DYNAMIC){
      this.isStatic = false;
    }
  }

  public void isFlying(boolean isFlying){
    this.isFlying = isFlying;
  }

  public PhysicsComponent(Element element, TransformComponent transform, BlackBoard blackBoard){
    System.out.println("physics");
    this.transform = transform;

    this.mass = Double.parseDouble(element.getAttribute("mass"));
    this.r = Double.parseDouble(element.getAttribute("restitution"));
    this.u_kinetic = 0;
    this.u_static = 0;
    this.behaviorType = CollisionBehaviorType.valueOf(element.getAttribute("behavior"));

    if (this.behaviorType == CollisionBehaviorType.DYNAMIC){
      this.isStatic = false;
    }
  }

  public boolean isStatic(){
    return this.isStatic;
  }

  public double getRestitution(){
    return this.r;
  }

  public Vec2d getVelocity(){
    return this.velocity;
  }

  public double getMass(){
    return this.mass;
  }

  public void resolveCollision(GameObject b, Vec2d mtv_a, Vec2d mtv_b){
    PhysicsComponent b_p = (PhysicsComponent) b.getComponent(ComponentTag.PHYSICS);
    double COR = Math.sqrt(this.r*b_p.getRestitution());

    Vec2d norm_a = new Vec2d(0);
    Vec2d norm_b = new Vec2d(0);
    if (!mtv_a.isZero()) norm_a = mtv_a.normalize();
    if (!mtv_b.isZero()) norm_b = mtv_b.normalize();


    // project velocities onto mtv
    double u_a = this.velocity.dot(norm_a);
    double u_b = b_p.getVelocity().dot(norm_b);

    double m_a = this.mass;
    double m_b = b_p.getMass();

    double I_a = (m_a*m_b*(u_b-u_a)*(1 + COR))/(m_a+m_b);
    double I_b = (m_a*m_b*(u_a-u_b)*(1 + COR))/(m_a+m_b);

    if (this.isStatic){
      I_b = (m_b*(-u_b)*(COR));
      I_a = 0;
    }

    if (b_p.isStatic){
      I_a = (m_a*(-u_a)*(COR));
      I_b = 0;
    }

    // apply these impulses upon collision!
    //this.applyFrictionImpulse(.9, .8, new Vec2d(0, -1), norm_a.smult(I_a));
    this.applyImpulse(norm_a.smult(I_a));
//
    ((PhysicsComponent) b.getComponent(ComponentTag.PHYSICS)).applyImpulse(norm_b.smult(I_b));
  }

  public double getU_static(){
    return this.u_static;
  }
  public double getU_kinetic(){
    return this.u_kinetic;
  }

  public boolean isGrounded(){
    return this.isGrounded;
  }

  public void applyFriction(double frictionCoeff){
    this.force = this.force.minus(frictionCoeff * this.normalForce, 0);
  }

  public void applyForce(Vec2d f){
    this.force = this.force.plus(f);
  }

  public void clearImpulse(){
    this.impulse = new Vec2d(0);
    this.velocity = new Vec2d(0, this.velocity.y);
  }


  public void applyGravity(Vec2d g){
    if (this.isFlying) return;
    this.normalForce = this.mass * g.y;
    if (!this.isGrounded && !this.isStatic){
      this.force = this.force.plus(g.smult(mass));
    } else {
      this.velocity = new Vec2d(this.velocity.x, 0);
    }
  }

  public void jump(float amount){
    if (this.isGrounded && !this.isStatic){
      this.velocity = this.velocity.plus(0, amount);
      this.isGrounded = false;
    }
  }

  public void setIsGrounded(boolean isGrounded){
    this.isGrounded = isGrounded;
  }

  public void applyImpulse(Vec2d p){
    this.impulse = this.impulse.plus(p);
    //this.applyFrictionImpulse(.6, .5, new Vec2d(0, -1), p);
  }

  public void applyFrictionImpulse(double u_static, double u_kinetic, Vec2d normal, Vec2d impulse){
    Vec2d tangent = impulse.minus(normal.smult(impulse.dot(normal))).smult(-1);
    Vec2d fs = tangent.smult(u_static);
    Vec2d fk = tangent.smult(u_kinetic);

    Vec2d I = new Vec2d(0);
    if (this.velocity.mag() == 0){
      if (impulse.x < fs.x && impulse.y > fs.y) I = new Vec2d(0, impulse.y);
      else if (impulse.x > fs.x && impulse.y < fs.y) I = new Vec2d(impulse.x, 0);
      else if (impulse.x > fs.x && impulse.y > fs.y) I = impulse;
    } else {
      I = fk.minus(impulse);
      if (I.x < 0 ) I = new Vec2d(0, I.y);
    }

    this.impulse = this.impulse.plus(I);
  }



  public void physicsTick(double t) {
    if (!this.isStatic){
      this.velocity = this.velocity.plus((this.force.sdiv((float) mass)).smult(t)).plus(this.impulse.sdiv(
          (float) mass));
      this.velocity = this.velocity.pmult(.99f, 1);
      this.transform.translatePos(this.velocity.smult(t));
    }
    this.force = new Vec2d(0);
    this.impulse = new Vec2d(0);

  }

  @Override
  public void tick(long nanosSinceLastTick) {

  }

  @Override
  public void lateTick() {

  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public ComponentTag getTag() {
    return ComponentTag.PHYSICS;
  }

  @Override
  public Element saveComponent(Document doc) {
    Element component = doc.createElement("Component");
    component.setAttribute("id", this.getTag().name());
    component.setAttribute("mass", String.valueOf(this.mass));
    component.setAttribute("restitution", String.valueOf(this.getRestitution()));
    component.setAttribute("behavior", this.behaviorType.name());

    return component;
  }
}
