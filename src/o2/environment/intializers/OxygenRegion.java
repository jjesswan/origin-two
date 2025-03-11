package o2.environment.intializers;

import engine.components.TransformComponent;
import engine.gameworld.GameObject;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.ui.UIText;
import o2.player.Player;
import o2.uiscreens.playerstats.OxygenLevelUI;
import o2.uiscreens.playerstats.StatsOverlayScreen;

public class OxygenRegion {
  private Vec2d topLeft;
  private Vec2d bottomRight;
  private int oxygenLevel;
  private boolean activatedTree = false;
  private int penalty = 0;
  private ScreenManager screenManager;
  private int countdown = 0;
  private int MAX_TIME = 300;

  public OxygenRegion(Vec2d topLeft, Vec2d bottomRight, int oxygenLevel, ScreenManager screenManager){
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
    this.oxygenLevel = oxygenLevel;

    this.penalty = (int) ((100 - this.oxygenLevel)*.20);
    this.screenManager = screenManager;

  }

  public void checkInRegion(Player player){
    // no need to check for penalty if region is safe
    if (activatedTree) return;
    Vec2d playerPos = player.getPlayerGO().getTransform().getPosition();
    this.countdown --;

    if (topLeft.x < playerPos.x && playerPos.x < bottomRight.x &&
        topLeft.y < playerPos.y && playerPos.y < bottomRight.y){
      if (this.countdown <= 0) {
        this.applyPenalty(player);
        ((StatsOverlayScreen) this.screenManager.getScreen(
            "statsOverlay")).oxygenLevelUI.changeOxygenLevelText(this.oxygenLevel);
        this.countdown = MAX_TIME;
      }
    }
  }

  public void activateRegion(){
    this.oxygenLevel = 100;
    this.activatedTree = true;
    ((StatsOverlayScreen)this.screenManager.getScreen("statsOverlay")).oxygenLevelUI.changeOxygenLevelText(this.oxygenLevel);
  }

  private void applyPenalty(Player player){
    player.onPlayerHit(this.penalty);
  }


}
