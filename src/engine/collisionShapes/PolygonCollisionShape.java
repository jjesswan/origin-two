package engine.collisionShapes;

import engine.components.TransformComponent;
import engine.support.Vec2d;
import engine.utils.CollisionInterval;
import engine.utils.PolygonBuilder;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.List;

public class PolygonCollisionShape extends CollisionShape {
  private PolygonBuilder polygonBuilder;

  public PolygonCollisionShape(CollisionShapeType shapeType,
      TransformComponent transform,
      PolygonBuilder polygonBuilder) {
    super(shapeType, transform);
    this.polygonBuilder = polygonBuilder;
  }

  public List<PolygonEdge> getEdges(){
    return this.polygonBuilder.getPolyEdges();
  }
  public Vec2d[] getPoints(){
    return this.polygonBuilder.getPolyPoints();
  }


  @Override
  public Vec2d collide(CollisionShape shape){
    switch(shape.getCollisionShapeType()){
      case AAB:
        return isCollidingPolygon(((AABCollisionShape) shape).getPolygonAAB());
      case CIRCLE:
        return super.isCollidingCirclePolygon((CircleCollisionShape) shape, this);
      case POLY:
        return isCollidingPolygon((PolygonCollisionShape) shape);
    }
    return null;
  }

  public Vec2d isCollidingPolygon(PolygonCollisionShape poly){
    List<Vec2d> axes = new ArrayList<>();

    for (PolygonEdge edge : this.getEdges()) {
      axes.add(edge.dir.perpendicular().normalize());
    }

    for (PolygonEdge edge : poly.getEdges()) {
      axes.add(edge.dir.perpendicular().normalize());
    }

    Double minMtv = Double.MAX_VALUE;
    Vec2d dir = null;

    for (Vec2d axis : axes){
      CollisionInterval interval1 = this.projectShape(this.getPoints(), axis);
      CollisionInterval interval2 = this.projectShape(poly.getPoints(), axis);
      Double mtv1d = interval1.overlapPolygon(interval2);

      if (mtv1d == null) return null;
      if (Math.abs(mtv1d) < Math.abs(minMtv)){
        minMtv = mtv1d;
        dir = axis.normalize().smult(-mtv1d);
      }
    }

    return dir;
  }

  public Vec2d polygonPolygonCollide(PolygonCollisionShape s1, PolygonCollisionShape s2){
    Double minMagnitude = 10000000.0;
    Vec2d mtv = null;

    for (PolygonEdge edge : s1.getEdges()){
      // get vector normal to edge
      Vec2d perp = edge.dir.perpendicular();

      // project both shapes onto it
      // for each shape, project all points and use the largest interval
      CollisionInterval interval1 = super.projectShape(s1.getPoints(), perp);
      CollisionInterval interval2 = super.projectShape(s2.getPoints(), perp);

      // does not collide if an interval doesnt overlap the other for any of the seperating axes
      Double mtv1d = interval1.overlapPolygon(interval2);
      if (mtv1d == null) return null;
      if (Math.abs(mtv1d) < minMagnitude) {
        minMagnitude = Math.abs(mtv1d);
        mtv = perp.smult(mtv1d);
      }
    }
    return mtv;
  }


  @Override
  public boolean collidePoint(Vec2d p) {
    // loop through all edges and use cross product
    for (PolygonEdge edge : this.getEdges()){
      Vec2d p_vector = p.minus(edge.base);

      // not inside if any cross product is positive
      if (edge.dir.cross(p_vector) > 0) return false;
    }
    // otherwise there is a collision at s2
    return true;
  }
}
