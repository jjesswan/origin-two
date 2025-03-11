package engine;

import engine.screen.Screen;
import engine.support.FXFrontEnd;
import engine.support.Vec2d;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * This is your main Application class that you will contain your
 * 'draws' and 'ticks'. This class is also used for controlling
 * user input.
 */
public class Application extends FXFrontEnd {

  private List<Screen> allScreens;

  public Application(String title) {
    super(title);
    this.allScreens = new ArrayList<>();
  }
  public Application(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
    this.allScreens = new ArrayList<>();
  }

  public void addScreen(Screen screen){
    this.allScreens.add(screen);
  }

  public void clearScreens(){
    this.allScreens.clear();
  }

  /**
   * Called periodically and used to update the state of your game.
   * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
   */
  @Override
  protected void onTick(long nanosSincePreviousTick) {
    for (Screen s : this.allScreens){
      if (s.isShown()){
        s.onTick(nanosSincePreviousTick);
      }
    }
  }

  /**
   * Called after onTick().
   */
  @Override
  protected void onLateTick() {
    // Don't worry about this method until you need it. (It'll be covered in class.)
    for (Screen s : this.allScreens){
      if (s.isShown()){
        s.onLateTick();
      }
    }
  }

  /**
   *  Called periodically and meant to draw graphical components.
   * @param g		a {@link GraphicsContext} object used for drawing.
   */
  @Override
  protected void onDraw(GraphicsContext g) {
    g.setImageSmoothing(false);
    for (Screen s : this.allScreens) {
      if (s.isShown()) {
        s.onDraw(g);
      }
    }
  }

  /**
   * Called when a key is typed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyTyped(KeyEvent e) {
    for (Screen s : this.allScreens) {
      if (s.isShown()) {
        s.onKeyTyped(e);
      }
    }

  }

  /**
   * Called when a key is pressed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyPressed(KeyEvent e) {
    for (Screen s : this.allScreens) {
      if (s.isShown()) {
        s.onKeyPressed(e);
      }
    }

  }

  /**
   * Called when a key is released.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyReleased(KeyEvent e) {
    for (Screen s : this.allScreens) {
      if (s.isShown()) {
        s.onKeyReleased(e);
      }
    }

  }

  /**
   * Called when the mouse is clicked.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseClicked(MouseEvent e) {
    for (Screen s : this.allScreens){
      if (s.isShown()) {
        s.onMouseClicked(e);
      }
    }

  }

  /**
   * Called when the mouse is pressed.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMousePressed(MouseEvent e) {
    for (Screen s : this.allScreens){
      s.onMousePressed(e);
    }

  }

  /**
   * Called when the mouse is released.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseReleased(MouseEvent e) {
    for (Screen s : this.allScreens){
      s.onMouseReleased(e);
    }

  }

  /**
   * Called when the mouse is dragged.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseDragged(MouseEvent e) {
    for (Screen s : this.allScreens) {
      if (s.isShown()) {
        s.onMouseDragged(e);
      }
    }

  }

  /**
   * Called when the mouse is moved.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseMoved(MouseEvent e) {
    for (Screen s : this.allScreens) {
      if (s.isShown()) {
        s.onMouseMoved(e);
      }
    }

  }

  /**
   * Called when the mouse wheel is moved.
   * @param e		an FX {@link ScrollEvent} representing the input event.
   */
  @Override
  protected void onMouseWheelMoved(ScrollEvent e) {
    for (Screen s : this.allScreens){
      s.onMouseWheelMoved(e);
    }

  }

  /**
   * Called when the window's focus is changed.
   * @param newVal	a boolean representing the new focus state
   */
  @Override
  protected void onFocusChanged(boolean newVal) {
    for (Screen s : this.allScreens){
      s.onFocusChanged(newVal);
    }

  }

  /**
   * Called when the window is resized.
   * @param newSize	the new size of the drawing area.
   */
  @Override
  protected void onResize(Vec2d newSize) {
    for (Screen s : this.allScreens){
      s.onResize(newSize);
    }

  }

  /**
   * Called when the app is shutdown.
   */
  @Override
  protected void onShutdown() {

  }

  /**
   * Called when the app is starting up.s
   */
  @Override
  protected void onStartup(Scene scene) {
    System.out.println("starting up !");
  }

}
