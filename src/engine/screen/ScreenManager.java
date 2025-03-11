package engine.screen;

import engine.screen.Screen;
import engine.systems.ISystem;
import engine.utils.Types.DisplayMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreenManager {
  public Map<String, Screen> screens = new HashMap<>();

  public void ScreenManager(){

  }

  public void addScreen(String name, Screen screen){
    this.screens.put(name, screen);
  }

  public Screen getScreen(String name){
    if (this.screens.get(name) == null) System.err.println("SCREEN : " + name + "does not exist!");

    return this.screens.get(name);
  }

  public void showSingleScreen(String name){
    if (this.screens.get(name) == null) {
      System.err.println("SCREEN : " + name + " cannot be found.");
      return;
    }

    for (String s : this.screens.keySet()){
      this.screens.get(s).changeDisplayMode(DisplayMode.HIDE);
    }
    this.screens.get(name).changeDisplayMode(DisplayMode.SHOW);
  }

//  public void showScreen(String name){
//    if (this.screens.get(name) == null) {
//      System.err.println("SCREEN : " + name + " cannot be found.");
//      return;
//    }
//    this.screens.get(name).changeDisplayMode(DisplayMode.SHOW);
//  }

  public void showScreens(List<String> names){
    for (String name : names) {
      if (this.screens.get(name) == null) {
        System.err.println("SCREEN : " + name + " cannot be found.");
        return;
      }
      this.screens.get(name).changeDisplayMode(DisplayMode.SHOW);
    }
  }

  public void removeScreen(String name){
    this.screens.remove(name);
  }

}
