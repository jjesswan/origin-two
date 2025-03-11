package engine.utils;

import engine.components.ComponentTag;
import engine.gameworld.GameObject;
import java.util.Comparator;

public class ZComparator implements Comparator<GameObject> {

    public int compare(GameObject go1, GameObject go2)
    {
      if (go1.getZIndex() - go2.getZIndex() == 0){
        return go1.getId().compareTo(go2.getId());
      } else {
        return go1.getZIndex() - go2.getZIndex();
      }
    }
}
