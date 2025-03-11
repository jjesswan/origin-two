package engine.ai.navmesh;

import engine.support.Vec2d;
import engine.support.Vec2i;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NavSquare {
  public Set<Vec2i> neighbors = new HashSet<>(); // index of neighbors in 2d grid
  public double Hcost = 0;
  public double Gcost = 0;
  public double Fcost = Gcost + Hcost;
  public Vec2i previousIndex;

}
