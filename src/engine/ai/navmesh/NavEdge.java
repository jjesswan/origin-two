package engine.ai.navmesh;

import engine.support.Vec2d;
import java.util.List;
import javafx.util.Pair;

public class NavEdge {
  // all in GAME COORD
  public Pair<Vec2d, Vec2d> flank_points; // ordered from smaller, larger
  public Vec2d center;

  // where a and b are the points that define one edge
  public NavEdge(Vec2d a, Vec2d b){
    if (a.x < b.x || a.y < b.y) this.flank_points = new Pair<>(a, b);
    else this.flank_points = new Pair<>(b, a);

    this.center = this.flank_points.getKey().minus(this.flank_points.getValue()).sdiv(2);
  }
}
