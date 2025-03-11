package engine.raycast;

import engine.collisionShapes.AABCollisionShape;
import engine.collisionShapes.CircleCollisionShape;
import engine.collisionShapes.PolygonCollisionShape;
import engine.collisionShapes.PolygonEdge;
import engine.components.CollisionComponent;
import engine.components.CollisionGroupComponent;
import engine.components.ComponentTag;
import engine.components.TransformComponent;
import engine.gameworld.GameObject;
import engine.support.Vec2d;

import java.util.Map;

public class Raycaster {
  private TransformComponent sourceTransform;
  private Map<String, GameObject> gameobjects;

  public Raycaster(Map<String, GameObject> gameobjects){
    this.gameobjects = gameobjects;
  }

  public void updateGameObjects(Map<String, GameObject> gameobjects){
    this.gameobjects = gameobjects;
  }

  /**
   * Shoots ray from a gameobject to a target location
   * @param sourceID -- takes in id of gameobject's source; does this to avoid self-shooting the source gameobject
   * @param target -- target location
   * @return
   */
  public RayCollisionInfo shootRay(String sourceID, Vec2d target){
    Ray ray = new Ray(this.gameobjects.get(sourceID).getTransform().getPosition(), target);

    float min_t = Float.MAX_VALUE;
    String closest_id = null;

    float t = Float.MAX_VALUE;

    for (String id : this.gameobjects.keySet()){
      if (id == sourceID) continue;
      if (this.gameobjects.get(id).hasComponent(ComponentTag.COLLISION)) {
        CollisionComponent collisionComponent = ((CollisionComponent) this.gameobjects.get(id)
            .getComponent(ComponentTag.COLLISION));

        switch (collisionComponent.getCollisionShapeType()) {
          case CIRCLE:
            t = this.raycast((CircleCollisionShape) collisionComponent.getCollisionShape(), ray);
            break;
          case AAB:
            t = this.raycast((AABCollisionShape) collisionComponent.getCollisionShape(), ray);
            break;
          case POLY:
            t = this.raycast((PolygonCollisionShape) collisionComponent.getCollisionShape(), ray);
            break;
        }

        if (t < min_t && t >= 0) {
          min_t = t;
          closest_id = id;
        }
      } else if (this.gameobjects.get(id).hasComponent(ComponentTag.COLLISION_GROUP)){
        CollisionGroupComponent group = ((CollisionGroupComponent) this.gameobjects.get(id).getComponent(ComponentTag.COLLISION_GROUP));
        for (CollisionComponent c : group.getAllCollisionComponents()){
          t = this.raycast((PolygonCollisionShape) c.getCollisionShape(), ray);
          if (t < min_t && t >= 0) {
            min_t = t;
            closest_id = id;
          }
        }

      }
    }

    Vec2d intersection = null;
    if (closest_id != null){
      intersection = ray.src.plus(ray.dir.smult(min_t));
    }

    RayCollisionInfo info = new RayCollisionInfo(closest_id, intersection, ray);

    return info;
  }

  public boolean pointInCircle(CircleCollisionShape s1, Vec2d s2) {
    // check if distance between point and cirle center is <= radius
    Vec2d P = s2;
    Vec2d C = s1.getCenter();

    if (Math.pow(P.x-C.x, 2) + Math.pow(P.y-C.y, 2) <= Math.pow(s1.getRadius(),2)) return true;
    return false;
  }
  public float raycast(CircleCollisionShape s1, Ray s2) {
    // check source is outside circle
    float t = -1;
    Vec2d E = s1.getCenter().minus(s2.src);
    double a = E.dot(s2.dir);
    double b_squared = E.mag2() - Math.pow(a, 2);
    double f = Math.sqrt(Math.pow(s1.getRadius(), 2) - b_squared);

    if (!pointInCircle(s1, s2.src)){
      t = (float) (a - f);
    } else {
      t = (float) (a + f);
    }

    if (t < 0) return -1;
    return t;
  }

  public float raycast(AABCollisionShape s1, Ray s2) {
    return raycast(s1.getPolygonAAB(), s2);
  }

  public float raycast(PolygonCollisionShape s1, Ray s2) {
    float smallestT = Float.MAX_VALUE;
    for (PolygonEdge edge : s1.getEdges()){
      float t = this.raycastEdge(s2, edge);
      if (t < smallestT && t >= 0){
        smallestT = t;
      }
    }

    if (smallestT < Float.MAX_VALUE) return smallestT;
    return -1;
  }

  public float raycastEdge(Ray ray, PolygonEdge edge){
    // determine if segment straddles ray
    Vec2d p = ray.src;
    Vec2d d = ray.dir.normalize();
    Vec2d a = edge.base;
    Vec2d b = edge.tail;
    Vec2d m = edge.dir.normalize();
    Vec2d n = m.perpendicular().normalize();


    if (a.minus(p).cross(d) * b.minus(p).cross(d) > 0) return -1;

    // otherwise there is an intersection; solve for t
    float t = (float) ((b.minus(p).dot(n)) / (d.dot(n)));
    if (t < 0) return -1;
    else return t;
  }

}
