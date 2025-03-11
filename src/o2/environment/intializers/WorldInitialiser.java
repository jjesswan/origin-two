package o2.environment.intializers;

import engine.gameworld.GameWorld;
import engine.gameworld.environment.EnvironmentObject;
import engine.support.Vec2d;
import java.util.List;
import o2.Constants;
import o2.environment.WorldValues;

public class WorldInitialiser {
  private GameWorld gameWorld;
  private String ROOT_ENV = "o2/files/environment/";
  public WorldInitialiser(GameWorld gameWorld){
    this.gameWorld = gameWorld;

    this.intializeImage("background_filler.png", Constants.BG_LAYER, "background_filler_img", new Vec2d(-695, -239), 1);
    this.intializeImage("atmosphere_overlay.png", Constants.OVERLAY_LAYER, "background_overlay_img", new Vec2d(-695, -239), .3);

    this.intializeImage("background.png", Constants.BG_LAYER, "background_img");
    this.intializeImage("bridge.png", Constants.FOREGROUND_LAYER1, "bridge");
    this.intializeCollidable("landscape.png", Constants.FOREGROUND_LAYER2, "landscape", .1,
        WorldValues.LANDSCAPE_POLYGON_PTS);
  }

  private void intializeImage(String filename, int layer, String id){
    new EnvironmentObject(ROOT_ENV + filename, new Vec2d(0), new Vec2d(0), layer,true, id, this.gameWorld);
  }

  private void intializeImage(String filename, int layer, String id, Vec2d pos, double alpha){
    EnvironmentObject environmentObject = new EnvironmentObject(ROOT_ENV + filename, pos, new Vec2d(0), layer,true, id, this.gameWorld);
    environmentObject.setOpacity(alpha);
  }

  private void intializeCollidable(String filename, int layer, String id, double restitution, List<Vec2d[]> points){
    new EnvironmentObject(ROOT_ENV + filename, new Vec2d(0), new Vec2d(0), layer,true, id, points, 10000, restitution, this.gameWorld);
  }

}
