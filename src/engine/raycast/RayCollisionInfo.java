package engine.raycast;

import engine.support.Vec2d;
import engine.utils.Types.CollisionShapeType;

public class RayCollisionInfo {
  public String id;
  public Vec2d intersection;
  public Ray ray;

  public RayCollisionInfo(String id, Vec2d intersection, Ray ray){
    this.id = id;
    this.intersection = intersection;
    this.ray = ray;
  }

}
