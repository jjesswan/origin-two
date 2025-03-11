package engine.collisionShapes;


import engine.components.TransformComponent;
import engine.support.Vec2d;
import engine.utils.CollisionInterval;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.List;

public class CollisionShape {
  protected CollisionShapeType shapeType;
  protected static Vec2d xAxis = new Vec2d(1, 0);
  protected static Vec2d yAxis = new Vec2d(0, 1);
  protected TransformComponent transform;

  public CollisionShape(CollisionShapeType shapeType, TransformComponent transform){
    this.shapeType = shapeType;
    this.transform = transform;
  }

  public Vec2d collide(CollisionShape shape){
    return null;
  }

  public CollisionShapeType getCollisionShapeType(){
    return this.shapeType;
  }

  public boolean collidePoint(Vec2d p) {
    return false;
  }

  // AAB - CIRCLE
  public Vec2d isCollidingAABCircle(AABCollisionShape b, CircleCollisionShape c) {
    // find closest point on aab from circle center
    Vec2d min = b.getTopLeft();
    Vec2d max = b.getBottomRight();
    double closestX = Math.max(min.x, Math.min(max.x, c.getCenter().x));
    double closestY = Math.max(min.y, Math.min(max.y, c.getCenter().y));

    // do point-circle collision with closest point
    // check if distance between point and cirle center is <= radius
    Vec2d P = new Vec2d(closestX, closestY);
    Vec2d C = c.getCenter();

    if (Math.pow(P.x-C.x, 2) + Math.pow(P.y-C.y, 2) <= Math.pow(c.getRadius(),2)) {
      // otherwise
      double magnitude = c.getRadius() - P.dist(c.getCenter());
      Vec2d dir = P.minus(c.getCenter());
      Vec2d unitDir = dir.sdiv((float) dir.mag());

      // if circle center inside aab
      if (min.x < C.x && min.y < C.y && max.x > C.x && max.y > C.y){
        magnitude = c.getRadius() + P.dist(C);
        unitDir = new Vec2d(0, 1);
      }

      return unitDir.smult(magnitude);
    }
    return null;
  }

  // AAB - POLYGON
  public Vec2d isCollidingAABPolygon(AABCollisionShape b, PolygonCollisionShape poly) {
    Vec2d[] aab_points = new Vec2d[2];
    aab_points[0] = b.getTopLeft();
    aab_points[1] = b.getBottomRight();
    Double minMagnitude = 10000000.0;
    Vec2d mtv = null;

    for (PolygonEdge edge : poly.getEdges()){
      // get vector normal to edge
      Vec2d perp = edge.dir.perpendicular();

      CollisionInterval interval1 = this.projectShape(poly.getPoints(), perp);
      CollisionInterval interval2 = this.projectShape(aab_points, perp);
      Double mtv1d = interval1.overlapPolygon(interval2);

      if (mtv1d == null) return null;
      if (Math.abs(mtv1d) < minMagnitude) {
        minMagnitude = Math.abs(mtv1d);
        mtv = perp.smult(mtv1d);
      }
    }

    // check x and y axes too
    List<Vec2d> axes = new ArrayList<>();
    axes.add(new Vec2d(0, 1));
    axes.add(new Vec2d(1, 0));

    for (Vec2d axis : axes){
      CollisionInterval interval1 = this.projectShape(poly.getPoints(), axis);
      CollisionInterval interval2 = this.projectShape(aab_points,  axis);
      Double mtv1d = interval1.overlapPolygon(interval2);
      if (mtv1d == null) return null;
      if (Math.abs(mtv1d) < minMagnitude) {
        minMagnitude = Math.abs(mtv1d);
        mtv = axis.smult(mtv1d);
      }
    }

    // otherwise it overlaps for every axis!
    return mtv;
  }

  // CIRCLE - POLYGON
  public Vec2d isCollidingCirclePolygon(CircleCollisionShape c, PolygonCollisionShape poly) {
    Double minMagnitude = 10000000.0;
    Vec2d mtv = null;

    // for each edge, do line/circle detection
    for (PolygonEdge edge : poly.getEdges()){
      // check if either endpoints are in circle
      if (c.collidePoint(edge.base)) break;
      if (c.collidePoint(edge.tail)) break;

      Vec2d perp = edge.dir.perpendicular();

      // project both shapes onto it
      // for each shape, project all points and use the largest interval
      CollisionInterval interval1 = this.projectShape(poly.getPoints(), perp);
      CollisionInterval interval2 = c.line_circle(perp);

      // does not collide if an interval doesnt overlap the other for any of the seperating axes
      Double mtv1d = interval1.overlapPolygon(interval2);
      if (mtv1d == null) return null;
      if (Math.abs(mtv1d) < minMagnitude) {
        minMagnitude = Math.abs(mtv1d);
        mtv = perp.smult(mtv1d);
      }
    }

    // check the circle separating axis too
    Vec2d nearestPt = poly.getPoints()[0];
    double smallestDist = nearestPt.dist(c.getCenter());
    for (int i=0; i<poly.getPoints().length; i++){
      if (poly.getPoints()[i].dist(c.getCenter()) < smallestDist){
        nearestPt = poly.getPoints()[i];
        smallestDist = nearestPt.dist(c.getCenter());
      }
    }

    Vec2d axis = nearestPt.minus(c.getCenter());
    CollisionInterval interval1 = this.projectShape(poly.getPoints(), axis);
    CollisionInterval interval2 = c.line_circle(axis);

    Double mtv1d = interval1.overlapPolygon(interval2);
    if (mtv1d == null) return null;
    if (Math.abs(mtv1d) < minMagnitude) {
      minMagnitude = Math.abs(mtv1d);
      mtv = axis.smult(mtv1d);
    }

    // otherwise it overlaps for all possible separating axes!!
    return mtv;
  }

    public CollisionInterval projectShape(Vec2d[] points, Vec2d axis){
    double min = points[0].dot(axis.normalize());
    double max = min;
    for (Vec2d point : points){
      double projection = point.dot(axis.normalize());
      if (projection < min) min = projection;
      if (projection > max) max = projection;
    }

    // make collision interval
    return new CollisionInterval(min, max);
  }
}
