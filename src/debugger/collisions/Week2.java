package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week2Reqs;
import engine.utils.CollisionInterval;

/**
 * Fill this class in during Week 2.
 */
public final class Week2 extends Week2Reqs {

	private static Vec2d xAxis = new Vec2d(1, 0);
	private static Vec2d yAxis = new Vec2d(0, 1);


	// AXIS-ALIGNED BOXES
	
	@Override
	public boolean isColliding(AABShape s1, AABShape s2) {
		CollisionInterval interval1_x = new CollisionInterval(s1.topLeft.dot(xAxis), s1.getBottomRight().dot(xAxis));
		CollisionInterval interval1_y = new CollisionInterval(s1.topLeft.dot(yAxis), s1.getBottomRight().dot(yAxis));

		CollisionInterval interval2_x = new CollisionInterval(s2.topLeft.dot(xAxis), s2.getBottomRight().dot(xAxis));
		CollisionInterval interval2_y = new CollisionInterval(s2.topLeft.dot(yAxis), s2.getBottomRight().dot(yAxis));


		// if both axes overlap, they are colliding
		return interval1_x.overlap(interval2_x) && interval1_y.overlap(interval2_y);
	}

	@Override
	public boolean isColliding(AABShape s1, CircleShape s2) {
		// find closest point on aab from circle center
		Vec2d min = s1.getTopLeft();
		Vec2d max = s1.getBottomRight();
		double closestX = Math.max(min.x, Math.min(max.x, s2.getCenter().x));
		double closestY = Math.max(min.y, Math.min(max.y, s2.getCenter().y));

		// do point-circle collisoin with closest point
		return this.isColliding(s2, new Vec2d(closestX, closestY));
	}

	@Override
	public boolean isColliding(AABShape s1, Vec2d s2) {
		// project point onto axes
		double projectionX = s2.dot(xAxis);
		double projectionY = s2.dot(yAxis);

		CollisionInterval intervalX = new CollisionInterval(s1.topLeft.dot(xAxis), s1.getBottomRight().dot(xAxis));
		CollisionInterval intervalY = new CollisionInterval(s1.topLeft.dot(yAxis), s1.getBottomRight().dot(yAxis));

		if (intervalX.inRange(projectionX) && intervalY.inRange(projectionY)) return true;
		return false;
	}

	// CIRCLES

	// same as above
	@Override
	public boolean isColliding(CircleShape s1, AABShape s2) {
		return isColliding(s2, s1);
	}

	@Override
	public boolean isColliding(CircleShape s1, CircleShape s2) {
		Vec2d C1 = s1.getCenter();
		Vec2d C2 = s2.getCenter();
		double r1 = s1.getRadius();
		double r2 = s2.getRadius();

		if (Math.pow(C1.x-C2.x, 2) + Math.pow(C1.y-C2.y, 2) <= Math.pow(r1 + r2, 2)) return true;
		return false;
	}

	// POINT CIRCLE
	@Override
	public boolean isColliding(CircleShape s1, Vec2d s2) {
		// check if distance between point and cirle center is <= radius
		Vec2d P = s2;
		Vec2d C = s1.getCenter();

		if (Math.pow(P.x-C.x, 2) + Math.pow(P.y-C.y, 2) <= Math.pow(s1.getRadius(),2)) return true;
		return false;
	}

	
}
