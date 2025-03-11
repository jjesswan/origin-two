package o2;

import engine.ai.navmesh.NavmeshNavigator;
import engine.ai.navmesh.NavmeshParser;
import engine.dialogue.DialogueController;
import engine.gameworld.GameWorld;
import engine.screen.GameScreenTemplate;
import engine.sound.SoundSample;
import engine.support.Vec2d;
import engine.utils.SpriteReferenceLoader;
import engine.utils.Types.DisplayMode;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import o2.environment.intializers.EnemyInitialiser;
import o2.environment.intializers.LootInitialiser;
import o2.environment.intializers.TreeInitialiser;
import o2.environment.intializers.WorldInitialiser;
import o2.player.Player;
import engine.screen.ScreenManager;

public class GameMainScreen extends GameScreenTemplate {
  private BlackBoard blackBoard;
  private Player player;
  private Map<String, String> metadata = new HashMap<>();
  private DialogueController dialogueController;
  private NavmeshNavigator navigator;

  public GameMainScreen(Vec2d screenSize,
      DisplayMode displayMode, Vec2d viewportPos,
      Vec2d viewportSize, ScreenManager screenManager) {
    super(screenSize, displayMode, viewportPos, viewportSize, 1, screenManager);
    this.blackBoard = new BlackBoard();
    this.setScreenFill(Color.grayRgb(10));

    // make levels
    BlackBoard.spriteReferences.put("environment_map", new SpriteReferenceLoader(
        "o2/images/env_map.png", 4, 4));
    BlackBoard.spriteReferences.put("player_animations", new SpriteReferenceLoader(
        "o2/images/player_map.png", 4, 4));
    BlackBoard.spriteReferences.put("enemy_animations", new SpriteReferenceLoader(
        "o2/images/monster_map.png", 5, 4));
    this.restartGame(new GameWorld(new HashMap<>()));
  }

  public void restartGame(GameWorld gameWorld){
    super.restart(gameWorld);
    super.viewport.setFill(Color.grayRgb(0));
    super.viewport.setZoomBounds(1.4, .8);
    new WorldInitialiser(this.gameWorld);
    this.player = new Player(this.gameWorld, this.viewport, this.screenManager);
    new LootInitialiser(this.gameWorld, this.player, this.blackBoard);
    new TreeInitialiser(this.player, this.gameWorld, this.blackBoard, this.screenManager);
    NavmeshParser navmeshParser = new NavmeshParser("src/o2/navmesh/navmeshmap.txt", new Vec2d(66));
    this.navigator = navmeshParser.getNavigator();
    SoundManager.sounds.get("background").playSound();
  }

  public void start(){
    this.changeDisplayMode(DisplayMode.SHOW);
    new EnemyInitialiser(this.player, this.gameWorld, this.blackBoard, this.navigator);
    this.dialogueController.setNewDialogue("001");
  }

  public GameWorld getGameWorld(){
    return this.gameWorld;
  }

  public void addDialogueController(DialogueController dialogueController){
    this.dialogueController = dialogueController;

  }


  @Override
  public void onTick(long t){
    super.onTick(t);

    if (this.dialogueController != null) this.dialogueController.onTick(t);
  }

//  public void loadGameFile(){
//    this.pathfinder = new Pathfinder(this.levelParser.getNavmeshNavigator());
//    this.blackBoard.pathfinder = this.pathfinder;
//    Loader loader = new Loader("nin");
//    this.restartGame(loader.loadXML("test", this.blackBoard));
//  }

//  public void saveGameFile(){
//    System.out.println("SAVE");
//    Saver saver = new Saver(this.gameWorld, "nin");
//    saver.writeXML("test", this.metadata);
//  }


  @Override
  public void onMouseClicked(MouseEvent e) {
    super.onMouseClicked(e);
    this.player.onMouseClick(e);
  }


  @Override
  public void onKeyReleased(KeyEvent e) {
    this.player.onKeyReleased(e);
  }

  @Override
  public void onKeyPressed(KeyEvent e) {
    super.onKeyPressed(e);
    this.player.onKeyPressed(e);
  }

}
