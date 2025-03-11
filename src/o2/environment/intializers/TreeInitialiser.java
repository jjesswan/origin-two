package o2.environment.intializers;

import o2.BlackBoard;
import engine.gameworld.GameWorld;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import o2.environment.environmentObjects.OxygenTree;
import o2.player.Player;

public class TreeInitialiser {

  public TreeInitialiser(Player player, GameWorld gameWorld, BlackBoard blackBoard, ScreenManager screenManager){
    WorldOxygenLevels worldOxygenLevels = new WorldOxygenLevels(screenManager);
    new OxygenTree(1, new Vec2d(517, 1262), "src/o2/files/media/tree_1_cutscene.mp4", gameWorld, blackBoard, player, screenManager, worldOxygenLevels);
  }

}
