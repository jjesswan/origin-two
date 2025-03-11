package engine.collisionShapes;

import debugger.collisions.CircleShape;
import engine.components.TransformComponent;
import engine.support.Vec2d;
import engine.utils.CollisionInterval;
import engine.utils.Types.CollisionShapeType;

public class CircleCollisionShape extends CollisionShape {

  public CircleCollisionShape(CollisionShapeType shapeType, TransformComponent transform) {
    super(shapeType, transform);
  }

  @Override
  public Vec2d collide(CollisionShape shape){
    switch(shape.getCollisionShapeType()){
      case AAB:
        return super.isCollidingAABCircle((AABCollisionShape) shape, this);
      case CIRCLE:
        return this.isCollidingCircle((CircleCollisionShape) shape);
      case POLY:
        return super.isCollidingCirclePolygon(this, (PolygonCollisionShape) shape);
    }
    return null;
  }

  public double getRadius() {
    return this.transform.getSize().x/2;
  }

  public Vec2d getCenter() {
    return this.transform.getPosition();
  }


  public Vec2d isCollidingCircle(CircleCollisionShape c) {
    Vec2d C1 = this.getCenter();
    Vec2d C2 = c.getCenter();
    double r1 = this.getRadius();
    double r2 = c.getRadius();

    if (Math.pow(C1.x-C2.x, 2) + Math.pow(C1.y-C2.y, 2) <= Math.pow(r1 + r2, 2)) return this.mtv_CircleCollide(c);
    return null;
  }

  // POINT CIRCLE
  @Override
  public boolean collidePoint(Vec2d p) {
    // check if distance between point and cirle center is <= radius
    Vec2d C = this.getCenter();

    if (Math.pow(p.x-C.x, 2) + Math.pow(p.y-C.y, 2) <= Math.pow(this.getRadius(),2)) return true;
    return false;
  }

  public Vec2d mtv_CircleCollide(CircleCollisionShape c){
    double magnitude = this.getRadius() + c.getRadius() - this.getCenter().dist(c.getCenter());
    Vec2d dir =  c.getCenter().minus(this.getCenter());
    Vec2d unitDir = dir.sdiv((float) dir.mag());

    return unitDir.smult(-magnitude);
  }

  // reference: https://www.jeffreythompson.org/collision-detection/line-circle.php
  public CollisionInterval line_circle(Vec2d line){
    // project circle
    double r = this.getRadius();
    Vec2d center = this.getCenter();
    Vec2d norm_line = line.normalize();

    Vec2d dist = norm_line.smult(r);

    Vec2d p1 = center.plus(dist);
    Vec2d p2 = center.minus(dist);

    double proj1 = p1.dot(norm_line);
    double proj2 = p2.dot(norm_line);

    return new CollisionInterval(Math.min(proj1, proj2), Math.max(proj1, proj2));
  }

}
