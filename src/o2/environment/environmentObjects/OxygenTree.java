package o2.environment.environmentObjects;

import o2.BlackBoard;
import engine.components.ComponentTag;
import engine.components.SpriteComponent;
import engine.components.TickComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.screen.MediaPlayerScreen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import o2.Constants;
import o2.SoundManager;
import o2.environment.intializers.WorldOxygenLevels;
import o2.player.Player;
import o2.uiscreens.playerstats.StatsOverlayScreen;

public class OxygenTree {
  private boolean activated = false;
  private boolean inRange = false;
  private GameObject go;
  private Player player;
  private ScreenManager screenManager;
  private String cutscene_file;
  private int regionID;
  private WorldOxygenLevels worldOxygenLevels;

  public OxygenTree(int regionID, Vec2d pos, String cutscene_file, GameWorld gameWorld,
      BlackBoard blackBoard, Player player, ScreenManager screenManager, WorldOxygenLevels worldOxygenLevels){
    String id = "o2_tree_" + pos;

    this.regionID = regionID;
    this.cutscene_file = cutscene_file;
    this.screenManager = screenManager;
    this.player = player;
    this.worldOxygenLevels = worldOxygenLevels;

    this.go = new GameObject(pos, new Vec2d(80), Constants.INTERACTABLE_LAYER, id);
    go.addComponent(new SpriteComponent(new Vec2d(0), "tree", go.getTransform(), blackBoard));
    TickComponent tickComponent = new TickComponent(){
      @Override
      public void defineTickAction() {
        checkVicinity();
      }

      @Override
      public void onKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.F && inRange && !activated){
          System.out.println("ACTIVATE TREE");
          activateTree();
        }
      }
    };
    go.addComponent(tickComponent);
    gameWorld.updateGameObjects(id, go);
  }

  public void activateTree(){
    if (((StatsOverlayScreen)this.screenManager.getScreen("statsOverlay")).plantChargeUI.isFullHealth()){
      ((StatsOverlayScreen)this.screenManager.getScreen("statsOverlay")).plantChargeUI.depleteHealth();
      ((SpriteComponent)this.go.getComponent(ComponentTag.SPRITE)).changeSpriteIndex(new Vec2d(1, 0));
      this.activated = true;
      System.out.println("ACTIVATE");
      // clear region penalty
      this.worldOxygenLevels.oxygenRegions.get(this.regionID).activateRegion();

      // play tree cutscene
      ((MediaPlayerScreen)this.screenManager.getScreen("mediaPlayer")).play(this.cutscene_file);
      SoundManager.sounds.get("tree_activation").playOnce();
    }
  }

  // to be called on tick
  public void checkVicinity(){
    if (this.activated) return;

    this.worldOxygenLevels.oxygenRegions.get(this.regionID).checkInRegion(this.player);
    if (this.player.getPlayerGO().getTransform().getPosition().dist2(this.go.getTransform().getPosition()) < 6000){
      this.inRange = true;
    } else this.inRange = false;
  }
}
