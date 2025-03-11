package engine.screen;

import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.utils.Types.Alignment;
import engine.utils.Types.DisplayMode;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class Screen {
  private List<UIElement> primaryUIElements;
  private Vec2d initScreenSize;
  private DisplayMode displayMode;
  private Color screenColor;
  protected ScreenManager screenManager = new ScreenManager();
  private int showDelay = 0;

  public Screen(Vec2d screenSize, DisplayMode displayMode, ScreenManager screenManager){
    this.primaryUIElements = new ArrayList<>();
    this.initScreenSize = screenSize;
    this.displayMode = displayMode;
    this.screenColor = null;
    this.screenManager = screenManager;
  }

  public void updateScreenManager(ScreenManager screenManager){
    this.screenManager = screenManager;
  }

  public void setScreenFill(Color color){
    this.screenColor = color;

  }

  public void addUIElements(List<UIElement> elements){
    this.primaryUIElements.addAll(elements);
    this.setInitialScreenSize();
  }

  public void addUIElement(UIElement element){
    this.primaryUIElements.add(element);
    this.setInitialScreenSize();
  }

  public void centerAlignUIElement(UIElement element){
    Vec2d centerScreen = this.initScreenSize.sdiv(2);
    Vec2d newPos = centerScreen.minus(element.getSize().sdiv(2));
    element.changeElementPosition(newPos);
  }

  public void setToCorner(UIElement child, Alignment corner, Vec2d offset){
    Vec2d topright = new Vec2d(this.initScreenSize.x, 0);
    Vec2d bottomright = this.initScreenSize;
    Vec2d bottomleft = new Vec2d(0, this.initScreenSize.y);
    Vec2d childPos;

    switch (corner){
      case TOPRIGHT:
        childPos = topright.plus(-offset.x - child.getSize().x, offset.y);
        break;
      case TOPLEFT:
        childPos = offset;
        break;
      case BOTTOMRIGHT:
        childPos = bottomright.minus(offset.plus(child.getSize()));
        break;
      case BOTTOMLEFT:
        childPos = bottomleft.plus(offset.x, -offset.y - child.getSize().y);
        break;
      default:
        System.err.println("INVALID CORNER TYPE");
        return;
    }

    //child.setAlignment(corner);
    child.changeElementPosition(childPos);
  }

  public void verticalAlignUIElement(UIElement element, double ypos){
    Vec2d centerScreen = this.initScreenSize.sdiv(2);
    Vec2d newPos = centerScreen.minus(element.getSize().sdiv(2));
    element.changeElementPosition(new Vec2d(newPos.x, ypos));
  }

  public void horizontalAlignUIElement(UIElement element, double xpos){
    Vec2d centerScreen = this.initScreenSize.sdiv(2);
    Vec2d newPos = centerScreen.minus(element.getSize().sdiv(2));
    element.changeElementPosition(new Vec2d(xpos, newPos.y));
  }

  public void clearUIElements(){
    this.primaryUIElements.clear();
  }

  public List<UIElement> getPrimaryUIElements(){
    return this.primaryUIElements;
  }

  public void changeDisplayMode(DisplayMode newMode){
    this.displayMode = newMode;

    if (newMode == DisplayMode.SHOW) this.showDelay = 3;
  }

  public boolean isShown(){
    return this.displayMode == DisplayMode.SHOW;
  }

  public void onTick(long nanosSincePreviousTick) {
    if (this.displayMode == DisplayMode.SHOW ) {
      if (this.showDelay != 0) this.showDelay--;
      if (this.showDelay < 0) this.showDelay = 0;

      for (UIElement el : this.primaryUIElements) {
        el.onTick(nanosSincePreviousTick);
      }
    }

  }

  public void onLateTick() {
    // Don't worry about this method until you need it. (It'll be covered in class.)
  }


  public void onDraw(GraphicsContext g) {
    if (this.displayMode == DisplayMode.SHOW) {
      if (this.screenColor != null){
        g.setFill(this.screenColor);
        g.fillRect(0, 0, this.initScreenSize.x, this.initScreenSize.y);
      }

      for (UIElement el : this.primaryUIElements) {
        el.onDraw(g);
      }
    }
  }

  public void onKeyTyped(KeyEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onKeyTyped(e);
      }
    }
  }

  public void onKeyPressed(KeyEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onKeyPressed(e);
      }
    }
  }

  public void onKeyReleased(KeyEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onKeyReleased(e);
      }
    }
  }

  public void onMouseClicked(MouseEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {

      for (UIElement el : this.primaryUIElements) {

        el.onMouseClicked(e);
      }
    }
  }

  public void onMousePressed(MouseEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {

      for (UIElement el : this.primaryUIElements) {
        el.onMousePressed(e);
      }
    }
  }

  public void onMouseReleased(MouseEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onMouseReleased(e);
      }
    }
  }

  public void onMouseDragged(MouseEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onMouseDragged(e);
      }
    }

  }

  public void onMouseMoved(MouseEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onMouseMoved(e);
      }
    }

  }

  public void onMouseWheelMoved(ScrollEvent e) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onMouseWheelMoved(e);
      }
    }
  }

  public void onFocusChanged(boolean newVal) {
    if (this.displayMode == DisplayMode.SHOW & this.showDelay == 0) {
      for (UIElement el : this.primaryUIElements) {
        el.onFocusChanged(newVal);
      }
    }
  }

  // sets starting window size in order to calculate position of ui elements
  public void setInitialScreenSize(){
    for (UIElement el : this.primaryUIElements){
      el.setInitialSize(this.initScreenSize);
    }
  }

  public void onResize(Vec2d newSize) {
    this.initScreenSize = newSize;
    for (UIElement el : this.primaryUIElements){
      el.onResize(newSize);
    }
  }

  public void onShutdown() {

  }

  public void onStartup() {

  }

}
