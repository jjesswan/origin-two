package engine.raycast;

import engine.support.Vec2d;

public class Ray {
  public Vec2d src;
  public Vec2d dir;

  public Ray(Vec2d src, Vec2d target){
    this.src = src;
    this.dir = target.minus(src).normalize();
  }

}
