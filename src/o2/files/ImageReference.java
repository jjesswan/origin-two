package o2.files;

import engine.utils.SpriteReferenceLoader;
import java.util.HashMap;
import java.util.Map;

public final class ImageReference {

  public static Map<String, SpriteReferenceLoader> spriteReferences = createReferenceMap();
  public static Map<String, String> fileNameReference = createFileReferenceMap();

  private static Map<String, SpriteReferenceLoader> createReferenceMap() {
    Map<String, SpriteReferenceLoader> map = new HashMap<>();
    map.put("ui_icons",  new SpriteReferenceLoader("o2/files/ui/plant_health.png", 5, 7));
    map.put("ui_tabs",  new SpriteReferenceLoader("o2/files/ui/tab_ui.png", 4, 2));
    map.put("loot", new SpriteReferenceLoader("o2/files/objects/loot.png", 4, 4));
    map.put("tree", new SpriteReferenceLoader("o2/files/objects/tree.png", 2, 1));
    map.put("small_enemy", new SpriteReferenceLoader("o2/files/objects/small_enemy.png", 4, 4));
    map.put("projectiles", new SpriteReferenceLoader("o2/files/objects/projectiles.png", 5, 5));
    map.put("weapons", new SpriteReferenceLoader("o2/files/objects/weapons.png", 4, 4));
    map.put("drone", new SpriteReferenceLoader("o2/files/objects/drone.png", 4, 4));





    return map;
  }

  private static Map<String, String> createFileReferenceMap() {
    Map<String, String> map = new HashMap<>();
    map.put("side_blurb", "o2/files/ui/side_blurb.png");
    map.put("title_bg", "o2/files/ui/title.png");

    return map;
  }


}

