package engine.screen;

import engine.components.ComponentTag;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIRect;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class Viewport extends UIRect {
  private Vec2d viewportCenter; // its upperleft location
  private Vec2d translation;
  private double screenGameRatio = 1;
  private double scaleFactor = 1;
  private GameWorld gameWorld;
  private Affine viewportAffine;
  private Vec2d centerTrans = new Vec2d(0);
  private double maxZoom = 100;
  private double minZoom = .1;


  public Viewport(Vec2d viewportPos, Vec2d viewportDimensions, Vec2d screenSize, Vec2d arc,
      double initialScale, GameWorld gameWorld){
    super(viewportPos, viewportDimensions, Color.grayRgb(0), FillType.STROKE, UIType.DISPLAY, arc.x, arc.y);
    this.viewportCenter = viewportPos.plus(viewportDimensions.sdiv(2));

    this.translation = new Vec2d(0);
    this.scaleFactor = initialScale;

    this.gameWorld = gameWorld;

    this.viewportAffine = new Affine();

    this.setInitialSize(screenSize);
    this.setAffine();
  }

  public void setZoomBounds(double maxZoom, double minZoom){
    this.maxZoom = maxZoom;
    this.minZoom = minZoom;
  }

  // where c is in game pos!!
  public void setViewportCenter(Vec2d newCenter_game){
    this.centerTrans = newCenter_game.minus(this.screenToGame(this.viewportCenter));
    this.viewportAffine = new Affine();
    this.viewportAffine.appendTranslation(this.viewportCenter.x, this.viewportCenter.y);
    this.viewportAffine.appendScale(this.scaleFactor, this.scaleFactor);
    this.viewportAffine.appendTranslation(-this.gameWorld.getWorldCenter().x, -this.gameWorld.getWorldCenter().y);
    this.viewportAffine.appendTranslation(-this.centerTrans.x, -this.centerTrans.y);
  }

  public Vec2d getCenterTrans(){
    return this.centerTrans;
  }

  public Vec2d gameToScreen(Vec2d gameCoord){

    Vec2d screenCoord = ((gameCoord.minus(this.gameWorld.getWorldCenter())).smult(this.scaleFactor)).plus(this.viewportCenter);
    return screenCoord;
  }

  public Vec2d screenToGame(Vec2d screenCoord){
    Vec2d gameCoord = ((screenCoord.minus(this.viewportCenter)).sdiv((float) this.scaleFactor)).plus(this.gameWorld.getWorldCenter());
    return gameCoord;
  }

  public Vec2d screenToGameCenteredOnPlayer(Vec2d screenCoord){
    Vec2d gameCoord = ((screenCoord.minus(this.viewportCenter)).sdiv((float) this.scaleFactor)).plus(this.gameWorld.getWorldCenter());
    return gameCoord.plus(this.centerTrans);
  }

  public void setAffine(){
    this.viewportAffine = new Affine();
    // zoom on center of gameworld
    this.viewportAffine.appendTranslation(this.viewportCenter.x, this.viewportCenter.y);
    this.viewportAffine.appendScale(this.scaleFactor, this.scaleFactor);
    this.viewportAffine.appendTranslation(-this.gameWorld.getWorldCenter().x, -this.gameWorld.getWorldCenter().y);

//    // PANNING ON ARROW KEYS
//    this.viewportAffine.appendTranslation(this.translation.x, this.translation.y);
//    this.gameWorldCenter = this.gameWorldCenter.plus(this.translation);
  }

  @Override
  public void onTick(long t){
    super.onTick(t);
    this.gameWorld.tick(t);
  }

  @Override
  public void onLateTick() {
    super.onLateTick();
    this.gameWorld.onLateTick();
  }

  @Override
  public void onMousePressed(MouseEvent e) {
    this.gameWorld.onMousePressed(this.screenToGame(new Vec2d(e.getX(), e.getY())));
  }

  @Override
  public void onMouseReleased(MouseEvent e) {
    this.gameWorld.onMouseReleased(this.screenToGame(new Vec2d(e.getX(), e.getY())));
  }

  @Override
  public void onMouseDragged(MouseEvent e) {
    this.gameWorld.onMouseDragged(this.screenToGame(new Vec2d(e.getX(), e.getY())));
  }

  @Override
  public void onMouseClicked(MouseEvent e) {
    this.gameWorld.onMouseClicked(this.screenToGame(new Vec2d(e.getX(), e.getY())));
  }

  @Override
  public void onMouseMoved(MouseEvent e) {
    this.gameWorld.onMouseMoved(this.screenToGame(new Vec2d(e.getX(), e.getY())));
  }

  @Override
  public void onMouseWheelMoved(ScrollEvent e) {
    this.translation = new Vec2d(0);
    double factor = .01;
    if (e.getDeltaY() == 0) return;
    if (e.getDeltaY() < 0)  {
      this.scaleFactor = this.scaleFactor * Math.pow(-1/e.getDeltaY(), factor);
    }
    else{
      this.scaleFactor = this.scaleFactor * Math.pow(e.getDeltaY(), factor);
    }

    if (this.scaleFactor > this.maxZoom) this.scaleFactor = this.maxZoom;
    if (this.scaleFactor < this.minZoom) this.scaleFactor = this.minZoom;

    this.setAffine();
  }

  @Override
  public void onKeyPressed(KeyEvent e) {
    float trans = 20;
    this.translation = new Vec2d(0);
    // panning
    switch(e.getCode()){
      case LEFT:
        this.translation = this.translation.plus(trans, 0);
        break;
      case RIGHT:
        this.translation = this.translation.plus(-trans, 0);
        break;
      case UP:
        this.translation = this.translation.plus(0, trans);
        break;
      case DOWN:
        this.translation = this.translation.plus(0, -trans);
        break;
    }
    this.setAffine();
    this.gameWorld.onKeyPressed(e);
  }

  @Override
  public void onDraw(GraphicsContext g) {
    // set global transform
    g.setTransform(this.viewportAffine);
    this.gameWorld.draw(g);
    g.setTransform(new Affine());

    // then draw viewport ui stuffs
    super.onDraw(g);
  }

  @Override
  public void onResize(Vec2d newSize){
    for (UIElement child : this.childElements){
      // resize child individually
      child.onResize(newSize);
      this.realignChild(child);
    }

    // position relative to screen
    this.position = new Vec2d(positionRatio.x * newSize.x, positionRatio.y * newSize.y);

    // to be altered: currently resizes viewport to be same size as window
    this.size = new Vec2d(newSize.x, newSize.y);
    this.gameWorld.onResize(newSize);
  }

}
