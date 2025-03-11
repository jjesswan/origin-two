package engine.collisionShapes;


import debugger.collisions.PolygonShape;
import engine.components.TransformComponent;
import engine.support.Vec2d;
import engine.utils.CollisionInterval;
import engine.utils.PolygonBuilder;
import engine.utils.Types.CollisionShapeType;

public class AABCollisionShape extends CollisionShape{
  private PolygonCollisionShape polygonAAB;

  public AABCollisionShape(CollisionShapeType shapeType, TransformComponent transform) {
    super(shapeType, transform);

    Vec2d[] points = {
        this.getTopLeft(),
        this.getTopLeft().plus(0, (float) this.transform.getSize().y),
        this.getBottomRight(),
        this.getTopLeft().plus( (float) this.transform.getSize().x, 0)
    };
    PolygonBuilder polygonBuilder = new PolygonBuilder(transform.getPosition(), transform.getSize(), points);
    this.polygonAAB = new PolygonCollisionShape(shapeType, transform, polygonBuilder);
    this.transform.addPolygonBuilder(polygonBuilder);
  }

  public PolygonCollisionShape getPolygonAAB(){
    return this.polygonAAB;
  }

  public Vec2d getTopLeft(){
    return this.transform.getPosition();
  }

  public Vec2d getBottomRight(){
    return this.transform.getPosition().plus(this.transform.getSize());
  }

  @Override
  public Vec2d collide(CollisionShape shape){
    switch(shape.getCollisionShapeType()){
      case AAB:
        return this.isCollidingAAB((AABCollisionShape) shape);
      case CIRCLE:
        return super.isCollidingAABCircle(this, (CircleCollisionShape) shape);
      case POLY:
        return this.polygonAAB.isCollidingPolygon((PolygonCollisionShape) shape);
    }
    return null;
  }

  // AAB - AAB
  public Vec2d isCollidingAAB(AABCollisionShape aab) {
    CollisionInterval interval1_x = new CollisionInterval(this.getTopLeft().dot(xAxis), this.getBottomRight().dot(xAxis));
    CollisionInterval interval1_y = new CollisionInterval(this.getTopLeft().dot(yAxis), this.getBottomRight().dot(yAxis));

    CollisionInterval interval2_x = new CollisionInterval(aab.getTopLeft().dot(xAxis), aab.getBottomRight().dot(xAxis));
    CollisionInterval interval2_y = new CollisionInterval(aab.getTopLeft().dot(yAxis), aab.getBottomRight().dot(yAxis));

    // if both axes overlap, they are colliding
    if (interval1_x.overlap(interval2_x) && interval1_y.overlap(interval2_y)) return this.mtv_AAB(aab);
    return null;
  }

  // AAB - POINT
  @Override
  public boolean collidePoint(Vec2d p) {
    // project point onto axes
    double projectionX = p.dot(xAxis);
    double projectionY = p.dot(yAxis);

    CollisionInterval intervalX = new CollisionInterval(this.getTopLeft().dot(xAxis), this.getBottomRight().dot(xAxis));
    CollisionInterval intervalY = new CollisionInterval(this.getTopLeft().dot(yAxis), this.getBottomRight().dot(yAxis));

    if (intervalX.inRange(projectionX) && intervalY.inRange(projectionY)) return true;
    return false;
  }

  public Vec2d mtv_AAB(AABCollisionShape aab){
    Vec2d min_1 = this.getTopLeft();
    Vec2d max_1 = this.getBottomRight();

    Vec2d min_2 = aab.getTopLeft();
    Vec2d max_2 = aab.getBottomRight();


    Vec2d up = new Vec2d(0, max_1.y - min_2.y);
    Vec2d down = new Vec2d(0, min_1.y - max_2.y);
    Vec2d left = new Vec2d(max_1.x - min_2.x, 0);
    Vec2d right = new Vec2d(min_1.x - max_2.x, 0);

    double shortestHoriz = right.x;
    if (Math.abs(left.x) < Math.abs(right.x)) shortestHoriz = left.x;;

    double shortestVert = down.y;
    if (Math.abs(up.y) < Math.abs(down.y)) shortestVert = up.y;

    Vec2d mtv = new Vec2d(0);
    if (Math.abs(shortestHoriz) < Math.abs(shortestVert)) mtv = new Vec2d(shortestHoriz, 0);
    else mtv = new Vec2d(0, shortestVert);

    return mtv.smult(-1);
  }

}
