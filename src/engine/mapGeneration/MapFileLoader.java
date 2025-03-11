package engine.mapGeneration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MapFileLoader {
  private List<char[]> map;

  public MapFileLoader(String filename){
    this.map = new ArrayList<>();
    this.readMap(filename);
  }

  public void readMap(String filename) {
    try {
      Reader reader = new FileReader(filename);
      BufferedReader br = new BufferedReader(reader);
      if (br.ready()) {
        String string;
        while ((string = br.readLine()) != null) {
          char[] row = string.toCharArray();
          this.map.add(row);
        }
      }
    } catch (IOException e) {
      System.err.println("IOException: File type invalid or cannot be read.");
    }
  }

  public List<char[]> getMap(){
    return this.map;
  }
}
