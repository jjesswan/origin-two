package engine.ai;

import engine.ai.BT.Action;
import engine.ai.navmesh.NavmeshNavigator;
import engine.ai.navmesh.NavSquare;
import engine.gameworld.GameObject;
import engine.support.Vec2d;
import engine.support.Vec2i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Pathfinder extends Action  {
  private Map<Vec2i, NavSquare> navMesh;
  private Vec2i startID;
  private Vec2i endID;
  private NavmeshNavigator navigator;
  private double maxDistance = 100;

  // where navmesh holds the center positions of each navigable square
  public Pathfinder(NavmeshNavigator navigator){
    this.navigator = navigator;
    this.navMesh = navigator.getNavmesh();
  }

  public List<Vec2d> findPath(Vec2d A, Vec2d B){
    try {
      return this.findPathAB(A, B);
    } catch (NullPointerException e){
      System.err.println("Could not find path");
      return new ArrayList<>();
    }
  }

  // finds path from A->B
  public List<Vec2d> findPathAB(Vec2d A, Vec2d B){
    // get grid index that encases start and end points
    this.startID = this.navigator.gameToGridIndex(A);
    this.navMesh.get(startID).Gcost = 0;
    this.endID = this.navigator.gameToGridIndex(B);

    // make sure both start and end points are in navmesh
    if (this.startID == null || this.endID == null) return new ArrayList<>();

    // check if they are in same square
    if (this.startID.x == this.endID.x && this.startID.y == this.endID.y){
      List<Vec2d> destination = new ArrayList<>();
      destination.add(B);
      return destination;
    }

    // calculate distance to end point for each grid in navmesh
    for (Vec2i id : this.navMesh.keySet()){
      this.navMesh.get(id).Hcost = this.getDistance(id, this.endID);
      this.updateFCost(id);
    }

    // otherwise proceed with A*
    // open and closed contain the keys for the traversed grid square
    Set<Vec2i> open = new HashSet<>();
    Set<Vec2i> closed = new HashSet<>();

    // initialize open set with the starting node
    open.add(this.startID);

    // calculate paths with A*
    boolean reachable = traverseAStar(this.startID, open, closed);
    if (reachable) {
     // System.out.println("-- REACHABLE PATH!");
      return this.getNavigablePath(B);
    }

    //System.out.println("!! Not reachable path.");
    return new ArrayList<>();
  }

  private boolean traverseAStar(Vec2i currNodeID, Set<Vec2i> open, Set<Vec2i> closed){
    // basecase: if looking at nodes too far from entity, then just end
    if (this.getDistance(currNodeID, this.startID) > this.maxDistance){
      //System.out.println("--- DISTANCE TOO FAR");
      return false;
    }

    double lowestF = 100000;
    double lowestH = 100000;
    Vec2i C = null; // where C is the next node to visit

    for (Vec2i id : open){
      // calculate G
      double new_G = this.getDistance(id, currNodeID) + this.navMesh.get(id).Gcost;
      this.navMesh.get(id).Gcost = new_G;
      this.updateFCost(id);

      // case where F costs are equal, then go by lowest H cost
      if (this.navMesh.get(id).Fcost == lowestF){
        if (this.navMesh.get(id).Hcost < lowestH){
          C = id;
          lowestF = this.navMesh.get(id).Fcost;
          lowestH = this.navMesh.get(id).Hcost;
        }
      }

      if (this.navMesh.get(id).Fcost < lowestF){
        C = id;
        lowestF = this.navMesh.get(id).Fcost;
        lowestH = this.navMesh.get(id).Hcost;
      }
    }

    // move C from open to closed list
    open.remove(C);
    closed.add(C);

    // check if C is the target destination
    if (C.x == this.endID.x && C.y == this.endID.y){
      this.navMesh.get(this.endID).previousIndex = currNodeID;
      //System.out.println("--- PATH FOUND!");
      return true;
    }

    // for neighbor N of C
    for (Vec2i N : this.navMesh.get(C).neighbors){
      if (closed.contains(N)) continue;

      // calculate potential F cost of C --> N
      double new_G = this.getDistance(N, C) + this.navMesh.get(C).Gcost;
      double new_F = new_G + this.navMesh.get(N).Hcost;

      if (new_F < this.navMesh.get(N).Fcost || !open.contains(N)){
        // update N's G and F cost in map
        this.navMesh.get(N).Gcost = new_G;
        this.updateFCost(N);

        // set previous node of N to become C
        this.navMesh.get(N).previousIndex = C;
        open.add(N);
      }
    }

    // if there are still open nodes, visit the next one recursively
    if (!open.isEmpty()){
      this.traverseAStar(C, open, closed);
    }

    // otherwise no more open nodes, thus A* has finished
    return true;
  }

  // returns a navigable path in GAME COORDS
  private List<Vec2d> getNavigablePath(Vec2d endPos){
    List<Vec2d> path = new ArrayList<>();
    path.add(endPos);

    Vec2i currV = this.endID;
    while (currV.x != this.startID.x || currV.y != this.startID.y){
     // System.out.println("currV: " + currV + " nextV: " + this.navMesh.get(currV).previousIndex);
      Vec2d gamePos = this.navigator.gridToGameCoord(this.navMesh.get(currV).previousIndex);
      path.add(gamePos);
      currV = this.navMesh.get(currV).previousIndex;
    }
    //System.out.println("found path: " + path);
    return path;
  }


  private double getDistance(Vec2i a, Vec2i b){
    double dx = a.x - b.x;
    double dy = a.y - b.y;
    return dx * dx + dy * dy;
  }

  private void updateFCost(Vec2i id){
    this.navMesh.get(id).Fcost = this.navMesh.get(id).Hcost + this.navMesh.get(id).Gcost;
  }

}
