package o2.player;

import o2.BlackBoard;
import engine.components.AnimationComponent;
import engine.components.AnimationSequenceComponent;
import engine.components.CollisionComponent;
import engine.components.ComponentTag;
import engine.components.PhysicsComponent;
import engine.components.SaveComponent;
import engine.components.TickComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.screen.ScreenManager;
import engine.screen.Viewport;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import o2.SoundManager;
import o2.uiscreens.playerstats.StatsOverlayScreen;
import o2.weapons.WeaponManager;

public class Player {
  private GameObject player;
  private final GameWorld gameWorld;
  private Viewport viewport;
  private ScreenManager screenManager;
  private WeaponManager weaponManager;


  public Player(GameWorld gameWorld, Viewport viewport, ScreenManager screenManager){
    this.gameWorld = gameWorld;
    this.viewport = viewport;
    this.screenManager = screenManager;
    this.weaponManager = new WeaponManager(this.gameWorld, this);

    this.addPlayer();
  }

  public void addPlayer(){
    if (this.gameWorld.getGameObject("player") != null){
      this.player = this.gameWorld.getGameObject("player");
      this.player.makeCenterFocus(this.viewport);
      this.player.getTransform().setTravelBounds(this.gameWorld.getWorldSize());
      this.player.addComponent(new SaveComponent(this.player, "player"));
      this.gameWorld.updateGameObjects("player", player);
      return;
    }
    this.player = new GameObject(new Vec2d(255, 1200), new Vec2d(33), 10, "player");
    this.player.makeCenterFocus(this.viewport);
    this.player.getTransform().setTravelBounds(this.gameWorld.getWorldSize());
    player.addComponent(new CollisionComponent(CollisionShapeType.AAB, player.getTransform(), CollisionBehaviorType.DYNAMIC));
    List<AnimationComponent> animations = new ArrayList<>();


    AnimationComponent forward = this.makeAnimationComponent(
        Arrays.asList(new Vec2d(1, 0), new Vec2d(2,0)), new Vec2d(0));
    AnimationComponent right = this.makeAnimationComponent(Arrays.asList(new Vec2d(0, 1), new Vec2d(1,1),new Vec2d(2,1),new Vec2d(3,1)), new Vec2d(1,1));
    AnimationComponent left = this.makeAnimationComponent(Arrays.asList(new Vec2d(0, 2), new Vec2d(1,2),new Vec2d(2,2),new Vec2d(3,2)), new Vec2d(0, 2));
    AnimationComponent back = this.makeAnimationComponent(Arrays.asList(new Vec2d(1, 3), new Vec2d(2,3)), new Vec2d(0, 3));

    animations.addAll(Arrays.asList(back, left, forward, right));

    player.addComponent(new AnimationSequenceComponent(animations));
    player.addComponent(new PhysicsComponent(player.getTransform(), 70, .3, CollisionBehaviorType.DYNAMIC));
    player.addComponent(new TickComponent(){
      @Override
      public void defineTickAction(){
        checkPlayerDeath();
      }
    });
    player.addComponent(new SaveComponent(this.player, "player"));
    this.gameWorld.updateGameObjects("player", player);
  }


  public AnimationComponent makeAnimationComponent(List<Vec2d> sequence, Vec2d idle){
    return new AnimationComponent(sequence, idle, "player_animations", this.player.getTransform());
  }

  public GameObject getPlayerGO(){
    return this.player;
  }

  public void onPlayerHit(int damage){
    ((StatsOverlayScreen)this.screenManager.getScreen("statsOverlay")).playerHealthUI.changeHealth(-damage);
  }

  public void checkPlayerDeath(){

    if (((StatsOverlayScreen)this.screenManager.getScreen("statsOverlay")).playerHealthUI.checkDeath()){
      System.err.println("PLAYER DIED.");
      this.screenManager.showSingleScreen("gameOver");
    }
  }


  public void onKeyReleased(KeyEvent e) {
    SoundManager.sounds.get("walking").stopSound();
    ((AnimationSequenceComponent)this.player.getComponent(ComponentTag.ANIMATION_SEQUENCE)).setIsIdle(true);
  }

  public void onKeyPressed(KeyEvent e) {
    boolean onGround = ((PhysicsComponent)this.player.getComponent(ComponentTag.PHYSICS)).isGrounded();
    if (onGround) SoundManager.sounds.get("walking").playSound();

    switch (e.getCode()){
      case W:
        ((AnimationSequenceComponent)this.player.getComponent(ComponentTag.ANIMATION_SEQUENCE)).setActiveSequence(0);
        break;
      case A:
        ((AnimationSequenceComponent)this.player.getComponent(ComponentTag.ANIMATION_SEQUENCE)).setActiveSequence(1);
        break;
      case S:
        ((AnimationSequenceComponent)this.player.getComponent(ComponentTag.ANIMATION_SEQUENCE)).setActiveSequence(2);
        break;
      case D:
        ((AnimationSequenceComponent)this.player.getComponent(ComponentTag.ANIMATION_SEQUENCE)).setActiveSequence(3);
        break;
      default:
        SoundManager.sounds.get("walking").stopSound();
        break;
    }

  }

  public void onMouseClick(MouseEvent e){
    Vec2d gamePos = this.viewport.screenToGameCenteredOnPlayer(new Vec2d(e.getX(), e.getY()));
    this.weaponManager.useCurrentWeapon(gamePos);
  }


  public void addPlantCharge(int amount){
    ((StatsOverlayScreen)this.screenManager.getScreen("statsOverlay")).plantChargeUI.changeHealth(amount);
  }

}
