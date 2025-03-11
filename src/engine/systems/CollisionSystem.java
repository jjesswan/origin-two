package engine.systems;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import engine.collisionShapes.CollisionShape;
import engine.components.CollisionComponent;
import engine.components.CollisionGroupComponent;
import engine.components.ComponentTag;
import engine.components.PhysicsComponent;
import engine.gameworld.GameObject;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public class CollisionSystem implements ISystem{

  Map<String, GameObject> gameObjects;
  List<String> collidables;
  Set<Pair<GameObject, GameObject>> collidingPairs;

  public CollisionSystem(Map<String, GameObject> gameObjects){
    this.gameObjects = gameObjects;
    this.collidables = new ArrayList<>();
    this.collidingPairs = new HashSet<>();
    for (String name : this.gameObjects.keySet()){
      if (this.gameObjects.get(name).hasComponent(ComponentTag.COLLISION) ||
          this.gameObjects.get(name).hasComponent(ComponentTag.COLLISION_GROUP)){
        this.collidables.add(name);
      }
    }

  }

  @Override
  public void tick(long nanosSinceLastTick) {
    // check collisions

  }


  public void checkCollisions(){
    if (this.collidables.size() >= 2) {
      for (int i = 0; i < collidables.size(); i++) {
        GameObject a = this.gameObjects.get(collidables.get(i));
        for (int j = i + 1; j < collidables.size(); j++) {
          GameObject b = this.gameObjects.get(collidables.get(j));
          // if a is not the same GO as b, and at least has collide components and thus is collidable,
          if ((a != b)) {
            // if a is grounded and b is the environment, then no need to collision check
//            if (this.checkOnGround(a, b)) continue;
//            if (this.checkOnGround(b, a)) continue;
            if (a.hasComponent(ComponentTag.COLLISION) && b.hasComponent(ComponentTag.COLLISION)) this.collide(a, b);
            if (a.hasComponent(ComponentTag.COLLISION_GROUP) && b.hasComponent(ComponentTag.COLLISION)) this.collideOneGroup(a, b);
            if (b.hasComponent(ComponentTag.COLLISION_GROUP) && a.hasComponent(ComponentTag.COLLISION)) this.collideOneGroup(b, a);
            if (a.hasComponent(ComponentTag.COLLISION_GROUP) && b.hasComponent(ComponentTag.COLLISION_GROUP)) this.collideTwoGroup(a,b);
          }
        }

        // clear all collisions after resolving them
        this.setGrounded(a);
        if (a.hasComponent(ComponentTag.COLLISION)) {
          ((CollisionComponent) a.getComponent(ComponentTag.COLLISION)).clearCollisions();
        }

        if (a.hasComponent(ComponentTag.COLLISION_GROUP)) {
          for (CollisionComponent c : ((CollisionGroupComponent) a.getComponent(
              ComponentTag.COLLISION_GROUP)).getAllCollisionComponents()) {
            c.clearCollisions();
          }
        }
      }
    }
  }

  public void collideTwoGroup(GameObject a,  GameObject b){
    for (CollisionComponent c : ((CollisionGroupComponent)a.getComponent(ComponentTag.COLLISION_GROUP)).getAllCollisionComponents()) {
      for (CollisionComponent d : ((CollisionGroupComponent) b.getComponent(
          ComponentTag.COLLISION_GROUP)).getAllCollisionComponents()) {
        CollisionBehaviorType a_behavior = c.getCollisionBehavior();
        CollisionBehaviorType b_behavior = d.getCollisionBehavior();

        if (a_behavior == CollisionBehaviorType.STATIC
            && b_behavior == CollisionBehaviorType.STATIC)
          return;

        CollisionShape a_shape = c.getCollisionShape();
        CollisionShape b_shape = d.getCollisionShape();

        Vec2d mtv = a_shape.collide(b_shape);
        if (mtv != null) {
          c.collided(b.getId());
          d.collided(a.getId());

          if (a_behavior == CollisionBehaviorType.STATIC
              && b_behavior == CollisionBehaviorType.DYNAMIC) {
            mtv = mtv.smult(-1);
            d.resolveCollision(mtv);
            ((PhysicsComponent) b.getComponent(ComponentTag.PHYSICS)).resolveCollision(a, mtv, mtv);

          }
          if (b_behavior == CollisionBehaviorType.STATIC
              && a_behavior == CollisionBehaviorType.DYNAMIC) {
            mtv = mtv.smult(-1);
            c.resolveCollision(mtv);
            ((PhysicsComponent) a.getComponent(ComponentTag.PHYSICS)).resolveCollision(b, mtv, mtv);

          }

          // add to colliding pairs, and once collision is resolved then remove from colliding pairs
          if ((a_behavior == CollisionBehaviorType.PASS_THRU
              && b_behavior == CollisionBehaviorType.DYNAMIC) ||
              (b_behavior == CollisionBehaviorType.PASS_THRU
                  && a_behavior == CollisionBehaviorType.DYNAMIC)) {
            this.collidingPairs.add(new Pair<>(a, b));
            return;
          }

          if (a_behavior == CollisionBehaviorType.DYNAMIC
              && b_behavior == CollisionBehaviorType.DYNAMIC) {
            // otherwise, translate both by half the mtv
            Vec2d mtv_a = mtv.smult(.5);
            Vec2d mtv_b = mtv.smult(-.5);
            c.resolveCollision(mtv_a);
            d.resolveCollision(mtv_b);
            ((PhysicsComponent) a.getComponent(ComponentTag.PHYSICS)).resolveCollision(b, mtv_a,
                mtv_b);
          }
        }
      }
    }
  }

  public void collideOneGroup(GameObject a,  GameObject b){
    Vec2d min_mtv = null;
    List<CollisionComponent> c_all = ((CollisionGroupComponent)a.getComponent(ComponentTag.COLLISION_GROUP)).getAllCollisionComponents();
    CollisionBehaviorType a_behavior = c_all.get(0).getCollisionBehavior();
    CollisionBehaviorType b_behavior = ((CollisionComponent) b.getComponent(
        ComponentTag.COLLISION)).getCollisionBehavior();



    // exit detection if they're not collidable
    if (a_behavior == CollisionBehaviorType.STATIC
        && b_behavior == CollisionBehaviorType.STATIC)
      return;

    for (CollisionComponent c : c_all) {
      CollisionShape a_shape = c.getCollisionShape();
      CollisionShape b_shape = ((CollisionComponent) b.getComponent(
          ComponentTag.COLLISION)).getCollisionShape();

      Vec2d mtv = a_shape.collide(b_shape);
      if (mtv != null) {
        c.collided(b.getId());
        ((CollisionComponent) b.getComponent(ComponentTag.COLLISION)).collided(a.getId());

        if (a_behavior == CollisionBehaviorType.STATIC
            && b_behavior == CollisionBehaviorType.DYNAMIC) {
          mtv = mtv.smult(-1);
        }
        if (b_behavior == CollisionBehaviorType.STATIC
            && a_behavior == CollisionBehaviorType.DYNAMIC) {
          mtv = mtv.smult(-1);
        }

        // add to colliding pairs, and once collision is resolved then remove from colliding pairs
        if ((a_behavior == CollisionBehaviorType.PASS_THRU
            && b_behavior == CollisionBehaviorType.DYNAMIC) ||
            (b_behavior == CollisionBehaviorType.PASS_THRU
                && a_behavior == CollisionBehaviorType.DYNAMIC)) {
          this.collidingPairs.add(new Pair<>(a, b));
          return;
        }

        if (a_behavior == CollisionBehaviorType.DYNAMIC
            && b_behavior == CollisionBehaviorType.DYNAMIC) {
          mtv = mtv.smult(.5);
        }

        // check if mtv is the smallest
        if (min_mtv == null) min_mtv = mtv;
        else if (mtv.mag2() < min_mtv.mag2()){
          min_mtv = mtv;
        }
      }
    }

    if (min_mtv == null) return;
    min_mtv = min_mtv.smult(-1);

    // resolve collision after finding min_mtv
    if (a_behavior == CollisionBehaviorType.STATIC
        && b_behavior == CollisionBehaviorType.DYNAMIC) {
      ((CollisionComponent) b.getComponent(ComponentTag.COLLISION)).resolveCollision(min_mtv.smult(-1));
      ((PhysicsComponent) b.getComponent(ComponentTag.PHYSICS)).resolveCollision(a, min_mtv.smult(-1), min_mtv.smult(-1));
    }

    if (b_behavior == CollisionBehaviorType.STATIC
        && a_behavior == CollisionBehaviorType.DYNAMIC) {
      c_all.get(0).resolveCollision(min_mtv);
      ((PhysicsComponent) a.getComponent(ComponentTag.PHYSICS)).resolveCollision(b, min_mtv, min_mtv);
    }

    if (a_behavior == CollisionBehaviorType.DYNAMIC
        && b_behavior == CollisionBehaviorType.DYNAMIC) {
      c_all.get(0).resolveCollision(min_mtv.smult(-1));
      ((CollisionComponent) b.getComponent(ComponentTag.COLLISION)).resolveCollision(min_mtv);
      ((PhysicsComponent) a.getComponent(ComponentTag.PHYSICS)).resolveCollision(b, min_mtv.smult(-1), min_mtv);
    }
  }

  public void collide(GameObject a,  GameObject b){
    CollisionBehaviorType a_behavior = ((CollisionComponent)a.getComponent(ComponentTag.COLLISION)).getCollisionBehavior();
    CollisionBehaviorType b_behavior = ((CollisionComponent)b.getComponent(ComponentTag.COLLISION)).getCollisionBehavior();

    if (a_behavior == CollisionBehaviorType.STATIC
        && b_behavior == CollisionBehaviorType.STATIC) return;

    CollisionShape a_shape = ((CollisionComponent)a.getComponent(ComponentTag.COLLISION)).getCollisionShape();
    CollisionShape b_shape = ((CollisionComponent)b.getComponent(ComponentTag.COLLISION)).getCollisionShape();

    Vec2d mtv = a_shape.collide(b_shape);
    if (mtv != null) {
      ((CollisionComponent)a.getComponent(ComponentTag.COLLISION)).collided(b.getId());
      ((CollisionComponent)b.getComponent(ComponentTag.COLLISION)).collided(a.getId());

      if (a_behavior == CollisionBehaviorType.STATIC
          && b_behavior == CollisionBehaviorType.DYNAMIC){
        mtv = mtv.smult(-1);
        ((CollisionComponent) b.getComponent(ComponentTag.COLLISION)).resolveCollision(mtv);
        ((PhysicsComponent) b.getComponent(ComponentTag.PHYSICS)).resolveCollision(a, mtv, mtv);

      }
      if (b_behavior == CollisionBehaviorType.STATIC
          && a_behavior == CollisionBehaviorType.DYNAMIC){
        mtv = mtv.smult(-1);
        ((CollisionComponent) a.getComponent(ComponentTag.COLLISION)).resolveCollision(mtv);
        ((PhysicsComponent) a.getComponent(ComponentTag.PHYSICS)).resolveCollision(b, mtv, mtv);

      }

      // add to colliding pairs, and once collision is resolved then remove from colliding pairs
      if ((a_behavior == CollisionBehaviorType.PASS_THRU
          && b_behavior == CollisionBehaviorType.DYNAMIC) ||
          (b_behavior == CollisionBehaviorType.PASS_THRU
              && a_behavior == CollisionBehaviorType.DYNAMIC)){
        this.collidingPairs.add(new Pair<>(a, b));
        return;
      }

      if (a_behavior == CollisionBehaviorType.DYNAMIC
          && b_behavior == CollisionBehaviorType.DYNAMIC){
        // otherwise, translate both by half the mtv
        Vec2d mtv_a = mtv.smult(.5);
        Vec2d mtv_b = mtv.smult(-.5);
        ((CollisionComponent) a.getComponent(ComponentTag.COLLISION)).resolveCollision(mtv_a);
        ((CollisionComponent) b.getComponent(ComponentTag.COLLISION)).resolveCollision(mtv_b);
        ((PhysicsComponent) a.getComponent(ComponentTag.PHYSICS)).resolveCollision(b, mtv_a, mtv_b);
      }
    }
  }

  public void setGrounded(GameObject go){
    if (go.hasComponent(ComponentTag.PHYSICS)){
      if (go.hasComponent(ComponentTag.COLLISION)){
        ((PhysicsComponent) go.getComponent(ComponentTag.PHYSICS)).setIsGrounded(((CollisionComponent) go.getComponent(ComponentTag.COLLISION)).checkGrounded());
      }

      if (go.hasComponent(ComponentTag.COLLISION_GROUP)){
        for (CollisionComponent c : ((CollisionGroupComponent)go.getComponent(ComponentTag.COLLISION_GROUP)).getAllCollisionComponents()) {
          if (c.checkGrounded()) {
            // the moment one collision component is grounded, then set the whole object to be grounded
            ((PhysicsComponent) go.getComponent(ComponentTag.PHYSICS)).setIsGrounded(true);
            return;
          }
        }
        // otherwise none were grounded
        ((PhysicsComponent) go.getComponent(ComponentTag.PHYSICS)).setIsGrounded(false);
      }
    }
  }

  @Override
  public void lateTick() {
    this.checkCollisions();
  }

  @Override
  public void draw(GraphicsContext g) {

  }

  @Override
  public SystemTag getTag() {
    return SystemTag.COLLISION_SYSTEM;
  }

  @Override
  public void updateGameObjects(String label, GameObject go) {
    this.gameObjects.put(label, go);
    if (go.hasComponent(ComponentTag.COLLISION) || go.hasComponent(ComponentTag.COLLISION_GROUP)){
      this.collidables.add(label);
    }


  }

  @Override
  public Set<Pair<GameObject, GameObject>> getCollidingPairs(){
    return this.collidingPairs;
  }

  @Override
  public void updateCollidingPairs(Set<Pair<GameObject, GameObject>> pairs){
    this.collidingPairs = pairs;
  }

  @Override
  public void removeGameObjects(String label){
    this.gameObjects.remove(label);
    this.collidables.remove(label);
  }
}
