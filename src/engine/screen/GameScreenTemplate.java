package engine.screen;

import com.sun.tools.javac.util.List;
import engine.components.CollisionComponent;
import engine.components.DragComponent;
import engine.components.DrawComponent;
import engine.components.SpriteComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.screen.Screen;
import engine.screen.Viewport;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIImage;
import engine.ui.UIRect;
import engine.ui.UIText;
import engine.utils.SpriteReferenceLoader;
import engine.utils.Types.CollisionShapeType;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;


public class GameScreenTemplate extends Screen{
  protected GameWorld gameWorld;
  protected Map<String, GameObject> gameObjects;
  protected Viewport viewport;
  protected Vec2d screenSize;
  protected Vec2d viewportPos, viewportSize;
  protected double initScale;

  public GameScreenTemplate(Vec2d screenSize, DisplayMode displayMode, Vec2d viewportPos, Vec2d viewportSize, double initScale, ScreenManager screenManager) {
    super(screenSize, displayMode, screenManager);
    this.screenSize = screenSize;
    this.viewportPos = viewportPos;
    this.viewportSize = viewportSize;
    this.initScale = initScale;

    // initialize gameworld
    this.restart(new HashMap<>());
  }

  public void restart(Map<String, GameObject> gameObjects){
    // initialize gameworld
    this.gameWorld = new GameWorld(gameObjects);
    this.viewport = new Viewport(viewportPos, viewportSize, this.screenSize, new Vec2d(40), initScale, this.gameWorld);
  }

  public void restart(GameWorld gameWorld){
    // initialize gameworld
    this.gameWorld = gameWorld;
    this.viewport = new Viewport(viewportPos, viewportSize, this.screenSize, new Vec2d(40), initScale, this.gameWorld);
  }

  @Override
  public void onDraw(GraphicsContext g) {
    super.onDraw(g);
    this.viewport.onDraw(g);

  }

  @Override
  public void onKeyPressed(KeyEvent e) {
    super.onKeyPressed(e);
    this.viewport.onKeyPressed(e);
  }

  @Override
  public void onMouseClicked(MouseEvent e) {
    super.onMouseClicked(e);
    this.viewport.onMouseClicked(e);
  }

  @Override
  public void onMousePressed(MouseEvent e) {
    super.onMousePressed(e);
    this.viewport.onMousePressed(e);
  }

  @Override
  public void onMouseDragged(MouseEvent e) {
    super.onMouseDragged(e);
    this.viewport.onMouseDragged(e);
  }

  @Override
  public void onMouseReleased(MouseEvent e) {
    super.onMouseReleased(e);
    this.viewport.onMouseReleased(e);
  }

  @Override
  public void onMouseMoved(MouseEvent e) {
    super.onMouseReleased(e);
    this.viewport.onMouseMoved(e);
  }

  @Override
  public void onMouseWheelMoved(ScrollEvent e) {
    super.onMouseWheelMoved(e);
    this.viewport.onMouseWheelMoved(e);
  }

  @Override
  public void onTick(long t){
    super.onTick(t);
    this.viewport.onTick(t);
  }

  @Override
  public void onLateTick() {
    super.onLateTick();
    this.viewport.onLateTick();
    // Don't worry about this method until you need it. (It'll be covered in class.)
  }

  @Override
  public void onResize(Vec2d newSize){
    super.onResize(newSize);
    this.viewport.onResize(newSize);
  }
}
