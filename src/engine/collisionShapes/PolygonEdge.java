package engine.collisionShapes;

import engine.support.Vec2d;

public class PolygonEdge {
  public Vec2d base;
  public Vec2d tail;
  public Vec2d dir;

  public PolygonEdge(Vec2d base, Vec2d tail){
    this.base = base;
    this.tail = tail;
    this.dir = this.tail.minus(this.base);
  }

}
