package o2;

import engine.Application;
import engine.dialogue.DialogueController;
import engine.dialogue.DialogueTreeLoader;
import engine.inventory.ui.InventoryUI;
import engine.screen.MediaPlayerScreen;
import engine.screen.Screen;
import engine.support.Vec2d;
import engine.utils.Types.DisplayMode;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import engine.screen.ScreenManager;
import o2.environment.intializers.InventoryInitialiser;
import o2.uiscreens.menus.GameOverScreen;
import o2.uiscreens.menus.MenuButtonsOverlay;
import o2.uiscreens.menus.TitleScreen;
import o2.uiscreens.playerstats.StatsOverlayScreen;


/**
 * This is your Tic-Tac-Toe top-level, App class.
 * This class will contain every other object in your game.
 */
public class App extends Application {
    private Vec2d initWindowSize = new Vec2d(960, 540);
    private GameMainScreen gameMainScreen;
    private DialogueController dialogueController;
    private InventoryUI inventoryScreen;
    private ScreenManager screenManager = new ScreenManager();
    private MediaPlayerScreen mediaPlayerEngine;

    public App(String title) {
      super(title);
    }

    public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
      super(title, windowSize, debugMode, fullscreen);
    }

    public void addScreen(String name, Screen screen){
        super.addScreen(screen);
        this.screenManager.addScreen(name, screen);
    }

    @Override
    protected void onStartup(Scene scene) {



        this.gameMainScreen = new GameMainScreen(initWindowSize, DisplayMode.HIDE, new Vec2d(0), initWindowSize, this.screenManager);
        this.addScreen("gameScreen", gameMainScreen);

        this.inventoryScreen = new InventoryUI(this.screenManager);
        new InventoryInitialiser(this.inventoryScreen);
        this.gameMainScreen.getGameWorld().addInventoryFeature(this.inventoryScreen);
        this.addScreen("inventoryScreen", inventoryScreen.getScreen());
        this.addScreen("statsOverlay", new StatsOverlayScreen(initWindowSize, DisplayMode.HIDE, this.screenManager));
        this.addScreen("menuButtonsOverlay", new MenuButtonsOverlay(initWindowSize, DisplayMode.HIDE, this.screenManager));

        this.addScreen("mediaPlayer", new MediaPlayerScreen(scene, this.screenManager));
        this.addScreen("title", new TitleScreen(DisplayMode.SHOW, this.screenManager));
        this.addScreen("gameOver", new GameOverScreen(DisplayMode.HIDE, this.screenManager));

        DialogueTreeLoader treeLoader = new DialogueTreeLoader("src/o2", "dialogue");
        this.dialogueController = new DialogueController(treeLoader, "001", this, this.screenManager);

        this.gameMainScreen.addDialogueController(dialogueController);
    }

    @Override
    protected void onTick(long nanosSincePreviousTick) {
        super.onTick(nanosSincePreviousTick);
    }


    @Override
    protected void onKeyPressed(KeyEvent e) {
        super.onKeyPressed(e);
        if (e.getCode() == KeyCode.R){
            ((MediaPlayerScreen)this.screenManager.getScreen("mediaPlayer")).play("src/o2/files/media/tree_1_cutscene.mp4");
        }
    }




}
