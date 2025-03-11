package o2.environment.intializers;

import engine.screen.ScreenManager;
import engine.support.Vec2d;
import java.util.HashMap;
import java.util.Map;

public final class WorldOxygenLevels {
  public Map<Integer, OxygenRegion> oxygenRegions; // maps a region to an oxygen level
  private ScreenManager screenManager;

  public WorldOxygenLevels(ScreenManager screenManager){
    this.screenManager = screenManager;
    this.initRegions();
  }

  private void initRegions(){
    this.oxygenRegions = new HashMap<>();
    oxygenRegions.put(1, new OxygenRegion(new Vec2d(0, 1000), new Vec2d(3525, 1664), 70, screenManager));
    oxygenRegions.put(2, new OxygenRegion(new Vec2d(3537, 546), new Vec2d(4950, 1400), 80, screenManager));
    oxygenRegions.put(3, new OxygenRegion(new Vec2d(2590,0), new Vec2d(4950,564), 65, screenManager));
    oxygenRegions.put(4, new OxygenRegion(new Vec2d(0), new Vec2d(2578,53), 40, screenManager));
  }
}
