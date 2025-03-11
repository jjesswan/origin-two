package engine.ai.navmesh;

import engine.support.Vec2d;
import engine.support.Vec2i;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NavmeshNavigator {
  private boolean[][] indexGraph;
  private Map<Vec2i, NavSquare> navmesh;
  private Vec2d gameUnit;

  public NavmeshNavigator(boolean[][] indexGraph, Vec2d gameUnit){
    this.indexGraph = indexGraph; // where true indicates that it IS navigational
    this.gameUnit = gameUnit;
    this.makeNashmesh();
  }

  public Map<Vec2i, NavSquare> getNavmesh(){
    return this.navmesh;
  }

  private void makeNashmesh(){
    this.navmesh = new HashMap<>();
    for (int i=0; i<indexGraph.length; i++){
      for (int j=0; j<indexGraph[0].length; j++){
        if (indexGraph[i][j]) {
          Vec2i index = new Vec2i(i, j);
          NavSquare navSquare = new NavSquare();
          navSquare.neighbors = getNeighbors(index);
          this.navmesh.put(index, navSquare);
        }
      }
    }
  }

  private Set<Vec2i> getNeighbors(Vec2i indexPos){
    Set<Vec2i> neighbors = new HashSet<>();
    int i = indexPos.x;
    int j = indexPos.y;

    Map<Vec2i, List<Vec2i>> adjacent = new HashMap<>();
    adjacent.put(new Vec2i(i, j-1), Arrays.asList(new Vec2i(i-1, j-1), new Vec2i(i+1, j-1)));
    adjacent.put(new Vec2i(i, j+1), Arrays.asList(new Vec2i(i-1, j+1), new Vec2i(i+1, j+1)));
    adjacent.put(new Vec2i(i-1, j), Arrays.asList(new Vec2i(i-1, j-1), new Vec2i(i-1, j+1)));
    adjacent.put(new Vec2i(i+1, j), Arrays.asList(new Vec2i(i+1, j-1), new Vec2i(i+1, j+1)));

    for (Vec2i adj : adjacent.keySet()){
      try {
        // check if adj square is accessible or not
        if (this.indexGraph[adj.x][adj.y]) {
          neighbors.add(adj);
          for (Vec2i corner : adjacent.get(adj)){
            if (this.indexGraph[corner.x][corner.y]) neighbors.add(corner);
          }
        } else {
          // otherwise remove any previously added corners surrounding a blocked square
          neighbors.remove(adj);
          neighbors.removeAll(adjacent.get(adj));
        }
      } catch (ArrayIndexOutOfBoundsException e){

      }
    }

    return neighbors;
  }

  public Vec2i gameToGridIndex(Vec2d gameCoord){
    Vec2d index = gameCoord.pdiv(this.gameUnit);
    int row = ((int) Math.floor(index.y));
    int col = ((int) Math.floor(index.x));
    try {
      if (!this.indexGraph[row][col])
        return null;
      return new Vec2i(row, col);
    } catch (ArrayIndexOutOfBoundsException e){
      return null;
    }
  }

  // gets the center of the square in game coords
  public Vec2d gridToGameCoord(Vec2i index){
    Vec2d unit = this.gameUnit.pmult(index.y, index.x);
    return unit.plus(this.gameUnit.sdiv(2));
  }


}
