package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week3Reqs;
import javax.swing.JCheckBox;

/**
 * Fill this class in during Week 3. Make sure to also change the week variable in Display.java.
 */
public final class Week3 extends Week3Reqs {

	private Week2 check = new Week2();

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		if (!check.isColliding(s1, s2)) return new Vec2d(0);

		Vec2d min_1 = s1.getTopLeft();
		Vec2d max_1 = s1.getBottomRight();

		Vec2d min_2 = s2.getTopLeft();
		Vec2d max_2 = s2.getBottomRight();

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

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		if (!check.isColliding(s1, s2)) return new Vec2d(0);


		Vec2d min = s1.getTopLeft();
		Vec2d max = s1.getBottomRight();
		double closestX = Math.max(min.x, Math.min(max.x, s2.getCenter().x));
		double closestY = Math.max(min.y, Math.min(max.y, s2.getCenter().y));
		Vec2d P = new Vec2d(closestX, closestY);


		// otherwise
		double magnitude = s2.getRadius() - P.dist(s2.getCenter());
		Vec2d dir = P.minus(s2.getCenter());
		Vec2d unitDir = dir.sdiv((float) dir.mag());

		// if circle center inside aab
		Vec2d C = s2.getCenter();
		if (min.x < C.x && min.y < C.y && max.x > C.x && max.y > C.y){
			magnitude = s2.getRadius() + P.dist(s2.getCenter());
			unitDir = new Vec2d(0, 1);
		}

		return unitDir.smult(magnitude);
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		if (check.isColliding(s1, s2)) return s2;

		return null;
	}
	
	// CIRCLES

	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		if (!check.isColliding(s1, s2)) return new Vec2d(0);

		double magnitude = s1.getRadius() + s2.getRadius() - s1.getCenter().dist(s2.getCenter());
		Vec2d dir =  s2.getCenter().minus(s1.getCenter());
		Vec2d unitDir = dir.sdiv((float) dir.mag());

		return unitDir.smult(-magnitude);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		if (check.isColliding(s1, s2)) return s2;
	  return null;
	}

}
