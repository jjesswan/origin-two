package engine.ai.navmesh;

import engine.ai.navmesh.NavmeshNavigator;
import engine.mapGeneration.MapFileLoader;
import engine.support.Vec2d;
import java.util.List;

public class NavmeshParser {
  private boolean[][] navmesh;
  private NavmeshNavigator navigator;

  public NavmeshParser(String file, Vec2d tileSize){
    MapFileLoader mapFileLoader = new MapFileLoader(file);
    this.parseToGameObjects(mapFileLoader.getMap());
    this.navigator = new NavmeshNavigator(this.navmesh, tileSize);
  }

  public NavmeshNavigator getNavigator(){
    return this.navigator;
  }

  private void parseToGameObjects(List<char[]> map){
    if (map.isEmpty()){
      System.err.println("ATTEMPTED TO PARSE EMPTY LEVEL MAP.");
      return;
    }
    this.navmesh = new boolean[map.size()][map.get(0).length];
    int rowNum = 0;
    for (char[] row : map){
      int colNum = 0;
      for (char c : row){
        if (c != '\t') {
          this.checkType(c, colNum, rowNum);
          colNum++;
        }
      }
      rowNum++;
    }
  }

  public void checkType(char c, int col, int row){
    // true indicates navigational places
    this.navmesh[row][col] = true;

    switch(c) {
      case 'x':
        this.navmesh[row][col] = false;
        break;
    }
  }

}
