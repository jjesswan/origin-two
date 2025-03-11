package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week5Reqs;
import debugger.support.shapes.PolygonShapeDefine;
import debugger.support.shapes.Shape;
import engine.utils.CollisionInterval;
import java.util.ArrayList;
import java.util.List;

/**
 * Fill this class in during Week 5. Make sure to also change the week variable in Display.java.
 */
public final class Week5 extends Week5Reqs {
	private Week2 collider = new Week2();
	private Week3 mtv_collider = new Week3();

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		return this.mtv_collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		return this.mtv_collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		return this.mtv_collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s2, PolygonShape s1) {
		// convert aab to polygon
		PolygonShape polygonShape = new PolygonShapeDefine(
				s2.getTopLeft(),
				s2.getTopLeft().plus(0, (float) s2.getSize().y),
				s2.getBottomRight(),
				s2.getTopLeft().plus( (float) s2.getSize().x, 0));

		Vec2d mtv = collision(s1, polygonShape);

		if (mtv != null) mtv = mtv.smult(-1);
		return mtv;
//		Vec2d[] aab_points = new Vec2d[2];
//		aab_points[0] = s2.getTopLeft();
//		aab_points[1] = s2.getBottomRight();
//		Double minMagnitude = 10000000.0;
//		Vec2d mtv = null;
//
//		for (int i=0; i<s1.getNumPoints(); i++){
//			Vec2d base = s1.getPoint(i);
//			Vec2d tail = s1.getPoint(0);
//			if (i+1 < s1.getNumPoints()) tail = s1.getPoint(i+1);
//
//			// make edge
//			PolygonEdge edge = new PolygonEdge(base, tail);
//
//			// get vector normal to edge
//			Vec2d perp = edge.dir.perpendicular();
//
//			// now do line/circle for each seperating axis
//
//			// project both shapes onto it
//			// for each shape, project all points and use the largest interval
//			CollisionInterval interval1 = this.projectShape(s1.points, perp);
//			CollisionInterval interval2 = this.projectShape(aab_points, perp);
//
//			// does not collide if an interval doesnt overlap the other for any of the seperating axes
//			Double mtv1d = interval1.overlapPolygon(interval2);
//			if (mtv1d == null) return null;
//			if (Math.abs(mtv1d) < minMagnitude) {
//				minMagnitude = Math.abs(mtv1d);
//				mtv = perp.normalize().smult(mtv1d);
//			}
//		}
//
//		// check x and y axes too
//		CollisionInterval interval1 = this.projectShape(s1.points, new Vec2d(0, 1));
//		CollisionInterval interval2 = this.projectShape(aab_points,  new Vec2d(0, 1));
//		Double mtv1d = interval1.overlapPolygon(interval2);
//		if (mtv1d == null) return null;
//		if (Math.abs(mtv1d) < minMagnitude) {
//			minMagnitude = Math.abs(mtv1d);
//			mtv = new Vec2d(0, 1).smult(mtv1d);
//		}
//
//		CollisionInterval interval1_y = this.projectShape(s1.points, new Vec2d(1, 0));
//		CollisionInterval interval2_y = this.projectShape(aab_points,  new Vec2d(1, 0));
//		mtv1d = interval1_y.overlapPolygon(interval2_y);
//		if (mtv1d == null) return null;
//		if (Math.abs(mtv1d) < minMagnitude) {
//			minMagnitude = Math.abs(mtv1d);
//			mtv = new Vec2d(1, 0).smult(mtv1d);
//		}
//
//
//		// otherwise it overlaps for every axis!
//		return mtv;
	}

	// CIRCLES
	
	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		return this.mtv_collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		return this.mtv_collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(CircleShape s2, PolygonShape s1) {
		Double minMagnitude = 10000000.0;
		Vec2d mtv = null;

		// for each edge, do line/circle detection
		for (int i=0; i<s1.getNumPoints(); i++){
			Vec2d base = s1.getPoint(i);
			Vec2d tail = s1.getPoint(0);
			if (i+1 < s1.getNumPoints()) tail = s1.getPoint(i+1);

			// check if either endpoints are in circle
			if (this.collider.isColliding(s2, base)) break;
			if (this.collider.isColliding(s2, tail)) break;

			// make edge
			PolygonEdge edge = new PolygonEdge(base, tail);

			// get vector normal to edge
			Vec2d perp = edge.dir.perpendicular();

			// now do line/circle for each seperating axis

			// project both shapes onto it
			// for each shape, project all points and use the largest interval
			CollisionInterval interval1 = this.projectShape(s1.points, perp);
			CollisionInterval interval2 = this.line_circle(s2, perp);

			// does not collide if an interval doesnt overlap the other for any of the seperating axes
			Double mtv1d = interval1.overlapPolygon(interval2);
			if (mtv1d == null) return null;
			if (Math.abs(mtv1d) < minMagnitude) {
				minMagnitude = Math.abs(mtv1d);
				mtv = perp.smult(mtv1d);
			}
		}

		// check the circle separating axis too
		Vec2d nearestPt = s1.getPoint(0);
		double smallestDist = nearestPt.dist(s2.getCenter());
		for (int i=0; i<s1.getNumPoints(); i++){
			if (s1.getPoint(i).dist(s2.getCenter()) < smallestDist){
				nearestPt = s1.getPoint(i);
				smallestDist = s1.getPoint(i).dist(s2.getCenter());
			}
		}

		Vec2d axis = nearestPt.minus(s2.getCenter());
		CollisionInterval interval1 = this.projectShape(s1.points, axis);
		CollisionInterval interval2 = this.line_circle(s2, axis);
		Double mtv1d = interval1.overlapPolygon(interval2);
		if (mtv1d == null) return null;
		if (Math.abs(mtv1d) < minMagnitude) {
			minMagnitude = Math.abs(mtv1d);
			mtv = axis.smult(mtv1d);
		}


		// otherwise it overlaps for all possible separating axes!!
		return mtv;
	}

	// reference: https://www.jeffreythompson.org/collision-detection/line-circle.php
	public CollisionInterval line_circle(CircleShape c, Vec2d line){
		// project circle
		double r = c.getRadius();
		Vec2d center = c.getCenter();
		Vec2d norm_line = line.normalize();

		Vec2d dist = norm_line.smult(r);

		Vec2d p1 = center.plus(dist);
		Vec2d p2 = center.minus(dist);

		double proj1 = p1.dot(norm_line);
		double proj2 = p2.dot(norm_line);

		return new CollisionInterval(Math.min(proj1, proj2), Math.max(proj1, proj2));
	}
	
	// POLYGONS

	@Override
	public Vec2d collision(PolygonShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, CircleShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}


	// point in polygon
	@Override
	public Vec2d collision(PolygonShape s1, Vec2d s2) {
		// loop through all edges and use cross product
		for (int i=0; i<s1.getNumPoints(); i++){
			Vec2d base = s1.getPoint(i);
			Vec2d tail = s1.getPoint(0);
			if (i+1 < s1.getNumPoints()) tail = s1.getPoint(i+1);

			// make edge
			PolygonEdge edge = new PolygonEdge(base, tail);
			Vec2d p = s2.minus(base);

			// not inside if any cross product is positive
			if (edge.dir.cross(p) > 0) return null;
		}

		// otherwise there is a collision at s2
		return s2;
	}

	@Override
	public Vec2d collision(PolygonShape s1, PolygonShape s2) {
		List<Vec2d> axes = new ArrayList<>();

		for (int i=0; i<s1.getNumPoints(); i++) {
			Vec2d base = s1.getPoint(i);
			Vec2d tail = s1.getPoint(0);
			if (i + 1 < s1.getNumPoints())
				tail = s1.getPoint(i + 1);

			// make edge
			PolygonEdge edge = new PolygonEdge(base, tail);
			axes.add(edge.dir.perpendicular().normalize());
		}

		for (int i=0; i<s2.getNumPoints(); i++) {
			Vec2d base = s2.getPoint(i);
			Vec2d tail = s2.getPoint(0);
			if (i + 1 < s2.getNumPoints())
				tail = s2.getPoint(i + 1);

			// make edge
			PolygonEdge edge = new PolygonEdge(base, tail);
			axes.add(edge.dir.perpendicular().normalize());
		}

		Double minMtv = Double.MAX_VALUE;
		Vec2d dir = null;

		for (Vec2d axis : axes){
			CollisionInterval interval1 = this.projectShape(s1.points, axis);
			CollisionInterval interval2 = this.projectShape(s2.points, axis);
			Double mtv1d = interval1.overlapPolygon(interval2);

			if (mtv1d == null) return null;
			if (Math.abs(mtv1d) < Math.abs(minMtv)){
				minMtv = mtv1d;
				dir = axis.normalize().smult(-mtv1d);
			}
		}

		return dir;








//		Vec2d mtv1 = this.polygonPolygonCollide(s1, s2);
//		Vec2d mtv2 = this.polygonPolygonCollide(s2, s1);
//
//		if (mtv1 != null && mtv2 != null){
//			if (mtv1.mag2() < mtv2.mag2()) return mtv1;
//			return mtv2;
//		} else if (mtv1 != null) return mtv1;
//		else if (mtv2 != null) return mtv2;


	}

	public CollisionInterval projectShape(Vec2d[] points, Vec2d axis){
		double min = points[0].dot(axis.normalize());
		double max = points[0].dot(axis.normalize());
		for (Vec2d point : points){
			double projection = point.dot(axis.normalize());
			if (projection < min) min = projection;
			if (projection > max) max = projection;
		}

		// make collision interval
		return new CollisionInterval(min, max);
	}

	public Vec2d polygonPolygonCollide(PolygonShape s1, PolygonShape s2){
		Double minMagnitude = Double.MAX_VALUE;
		Vec2d mtv = null;

		for (int i=0; i<s1.getNumPoints(); i++){
			Vec2d base = s1.getPoint(i);
			Vec2d tail = s1.getPoint(0);
			if (i+1 < s1.getNumPoints()) tail = s1.getPoint(i+1);


			// make edge
			PolygonEdge edge = new PolygonEdge(base, tail);

			// get vector normal to edge
			Vec2d perp = edge.dir.perpendicular();


			// project both shapes onto it
			// for each shape, project all points and use the largest interval
			CollisionInterval interval1 = this.projectShape(s1.points, perp);
			CollisionInterval interval2 = this.projectShape(s2.points, perp);

			// does not collide if an interval doesnt overlap the other for any of the seperating axes
			Double mtv1d = interval1.overlapPolygon(interval2);
			if (mtv1d == null) return null;
			if (Math.abs(mtv1d) < minMagnitude) {
				minMagnitude = Math.abs(mtv1d);
				mtv = perp.normalize().smult(mtv1d);
			}
		}
		 return mtv;
	}


	
}
