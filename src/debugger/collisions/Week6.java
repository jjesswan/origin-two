package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week6Reqs;
import debugger.support.shapes.PolygonShapeDefine;
import java.util.ArrayList;
import java.util.List;

/**
 * Fill this class in during Week 6. Make sure to also change the week variable in Display.java.
 */
public final class Week6 extends Week6Reqs {
	private Week5 collider = new Week5();
	private Week2 checkCollider = new Week2();

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		return this.collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		return this.collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		return this.collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s1, PolygonShape s2) {
		return this.collider.collision(s1, s2);
	}

	// CIRCLES
	
	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		return this.collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		return this.collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, PolygonShape s2) {
		return this.collider.collision(s1, s2);
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

	@Override
	public Vec2d collision(PolygonShape s1, Vec2d s2) {
		return this.collider.collision(s1, s2);
	}

	@Override
	public Vec2d collision(PolygonShape s1, PolygonShape s2) {
		return null;
	}
	
	// RAYCASTING
	
	@Override
	public float raycast(AABShape s1, Ray s2) {
		PolygonShape polygonShape = new PolygonShapeDefine(
				s1.getTopLeft(),
				s1.getTopLeft().plus(0, (float) s1.getSize().y),
				s1.getBottomRight(),
				s1.getTopLeft().plus( (float) s1.getSize().x, 0));
		return raycast(polygonShape, s2);
	}

	// reference: https://www.youtube.com/watch?v=23kTf-36Fcw
	@Override
	public float raycast(CircleShape s1, Ray s2) {
		// check source is outside circle
		float t = -1;
		Vec2d E = s1.getCenter().minus(s2.src);
		double a = E.dot(s2.dir);
		double b_squared = E.mag2() - Math.pow(a, 2);
		double f = Math.sqrt(Math.pow(s1.getRadius(), 2) - b_squared);

		if (!this.checkCollider.isColliding(s1, s2.src)){
			t = (float) (a - f);
		} else {
			t = (float) (a + f);
		}

		if (t < 0) return -1;
		return t;
	}
	
	@Override
	public float raycast(PolygonShape s1, Ray s2) {
		// initialize edges and raycast each one
		List<PolygonEdge> edges = new ArrayList<>();
		for (int i=0; i<s1.getNumPoints(); i++) {
			Vec2d base = s1.getPoint(i);
			Vec2d tail = s1.getPoint(0);
			if (i + 1 < s1.getNumPoints())
				tail = s1.getPoint(i + 1);

			// make edge
			PolygonEdge edge = new PolygonEdge(base, tail);
			edges.add(edge);
		}

		float smallestT = Float.MAX_VALUE;
		for (PolygonEdge edge : edges){
			float t = this.raycastEdge(s2, edge);
			if (t < smallestT && t >= 0){
				smallestT = t;
			}
		}

		if (smallestT < Float.MAX_VALUE) return smallestT;
		//System.out.println(smallestT);
		return -1;
	}

	public float raycastEdge(Ray ray, PolygonEdge edge){
		// determine if segment straddles ray
		Vec2d p = ray.src;
		Vec2d d = ray.dir.normalize();
		Vec2d a = edge.base;
		Vec2d b = edge.tail;
		Vec2d m = edge.dir.normalize();
		Vec2d n = m.perpendicular().normalize();


		if (a.minus(p).cross(d) * b.minus(p).cross(d) > 0) return -1;

		// otherwise there is an intersection; solve for t
		float t = (float) ((b.minus(p).dot(n)) / (d.dot(n)));
		if (t < 0) return -1;
		else return t;
	}

}
