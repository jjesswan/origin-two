package o2;

import engine.screen.Viewport;
import engine.support.Vec2d;
import engine.utils.PolygonBuilder;
import engine.utils.SpriteReferenceLoader;
import java.util.HashMap;
import java.util.Map;
import o2.Constants;

public final class BlackBoard {

  public static String currentWeaponID = Constants.PLATNINUM_SHOOTER;
  public static Map<String, Vec2d> locations = new HashMap<>();
  public static Map<String, Boolean> conditions = new HashMap<>();
  public static Map<String, SpriteReferenceLoader> spriteReferences = new HashMap<>();

}
