package engine.ui;

import engine.support.Vec2d;
import engine.support.Vec2i;
import engine.ui.animations.ITransition;
import engine.ui.buttonCommands.IButtonCommand;
import engine.utils.Types.Alignment;
import engine.utils.Types.ShapeType;
import engine.utils.Types.UIType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class UIElement {

  protected String image;
  protected Vec2d position;
  protected Pair<Vec2d, Vec2d> boundingBox;
  protected double aspectRatio;
  protected Color initialColor;
  protected List<UIElement> childElements;
  protected Vec2d size; // width x height
  protected Vec2d initialWindowSize = new Vec2d(960,540);
  protected Vec2d positionRatio;
  protected Vec2d dimensionRatio;
  protected UIType uiType;
  protected IButtonCommand buttonCommand;
  protected Function function;
  protected Vec2d percentOfParent;
  protected boolean isClicked = false;
  protected List<Alignment> alignment = new ArrayList<>();
  protected ShapeType shapeType;
  protected double strokeWidth = 1;
  protected List<ITransition> animations = new ArrayList<>();
  protected double offsetFromParent = 0;
  protected Vec2i gridPos = null;
  protected boolean hidden = false;

  public UIElement(Vec2d position, Vec2d size, Color initialColor, UIType uiType){
   // this.image = image;
    this.position = position;
    this.size = size;
    this.aspectRatio = size.x / size.y;
    this.initialColor = initialColor;
    this.childElements = new ArrayList<>();
    this.uiType = uiType;
    this.buttonCommand = null;
    this.percentOfParent = new Vec2d(-1);
  }

  public void addAnimation(ITransition t){
    for (UIElement child : this.childElements){
      child.addAnimation(t);
    }
    this.animations.add(t);
  }

  public void addAnimations(List<ITransition> t){
    for (UIElement child : this.childElements){
      child.addAnimations(t);
    }
    this.animations.addAll(t);
  }

  public void clearAnimations(){
    for (UIElement child : this.childElements){
      child.clearAnimations();
    }
    this.animations.clear();
  }

  public void playAnimations(GraphicsContext g){
    for (UIElement child : this.childElements){
      child.playAnimations(g);
    }
    for (ITransition t : this.animations){
      t.playTransition(g);
    }
  }

  public void endAnimations(GraphicsContext g){
    for (UIElement child : this.childElements){
      child.endAnimations(g);
    }
    for (ITransition t : this.animations){
      t.clear(g);
    }
  }



  public void mouseClickCommand(MouseEvent e){

  }


  public void mouseClickOffCommand(MouseEvent e){

  }

  public void mousePressCommand(){

  }
  public void mouseReleaseCommand(){

  }

  public void mouseDragCommand(){

  }

  public void mouseMoveCommand(){

  }

  public void mouseMoveOffCommand(){

  }

  public void setText(String text){

  }

  public ShapeType getShapeType(){
    return this.shapeType;
  }

  public void replaceChildElement(int index, UIElement child){
    if (index < this.childElements.size()) {
      this.childElements.remove(index);
      this.childElements.add(index, child);
    }
  }

  public void clearChildElements(){
    this.childElements.clear();
  }

  public void addChildElement(UIElement child){
    child.setInitialSize(this.initialWindowSize);
    this.childElements.add(child);

  }

  public void addChildElements(List<UIElement> children){
    for (UIElement child : children){
      child.setInitialSize(this.initialWindowSize);
    }
    this.childElements.addAll(children);
  }

  public Vec2d getPercentPosition(Vec2d percentPosition){
    Vec2d span = this.getSize();
    Vec2d childPos = span.pmult(percentPosition);
    return this.position.plus(childPos);
  }

  public void setToCorner(UIElement child, Alignment corner, Vec2d offset){
    Vec2d topleft = this.position;
    Vec2d topright = new Vec2d(this.position.x + this.size.x, this.position.y);
    Vec2d bottomright = this.position.plus(this.size);
    Vec2d bottomleft = new Vec2d(this.position.x, this.position.y + this.size.y);
    Vec2d childPos = child.getPos();

    switch (corner){
      case TOPRIGHT:
        childPos = topright.plus(-offset.x - child.getSize().x, offset.y);
        break;
      case TOPLEFT:
        childPos = topleft.plus(offset);
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

    child.setAlignment(corner);
    child.changeElementPosition(childPos);
  }

  public void setPercentageOfParent(Vec2d percentPosition){
    this.percentOfParent = percentPosition;
  }

  public void appendToLeft(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.LEFT);

    double xPos = this.position.x;
    double childWidth = child.getSize().x;
    double newPos = xPos - childWidth - offset;

    child.changeElementPosition(new Vec2d(newPos, child.getPos().y));
  }

  public void appendToRight(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.RIGHT);

    double xPos = this.position.x + this.size.x;
    double newPos = xPos + offset;

    child.changeElementPosition(new Vec2d(newPos, child.getPos().y));
  }


  public void appendToTop(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.TOP);

    double yPos = this.position.y;
    double childHeight = child.getSize().y;
    double newPos = yPos - childHeight - offset;

    child.changeElementPosition(new Vec2d(child.getPos().x, newPos));
  }

  public void appendToBottom(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.BOTTOM);

    double yPos = this.position.y + this.size.y;
    double newPos = yPos + offset;

    child.changeElementPosition(new Vec2d(child.getPos().x, newPos));
  }

  public void centerElement(UIElement child){
    child.setAlignment(Alignment.CENTER);
    Vec2d parentCenter = this.position.plus(this.getSize().sdiv(2));
    Vec2d halfChildSize = child.getSize().sdiv(2);
    Vec2d childPos = parentCenter.minus(halfChildSize);
    if (child.getShapeType() == ShapeType.TEXT){
      childPos = new Vec2d(parentCenter.x - halfChildSize.x, parentCenter.y + halfChildSize.y/2);
    }
    child.changeElementPosition(childPos);
  }

  public void vertCenterElement(UIElement child){
    child.setAlignment(Alignment.VERT_CENTER);
    double parentCenter = this.position.x + (this.getSize().x/2);
    Vec2d halfChildSize = child.getSize().sdiv(2);
    Vec2d childPos = new Vec2d(parentCenter - halfChildSize.x, child.getPos().y);
    if (child.getShapeType() == ShapeType.TEXT){
      childPos = new Vec2d(parentCenter - halfChildSize.x, child.getPos().y);
    }
    child.changeElementPosition(childPos);
  }

  public void horizCenterElement(UIElement child){
    child.setAlignment(Alignment.HORIZ_CENTER);
    double parentCenter = this.position.y + (this.getSize().y/2);
    Vec2d halfChildSize = child.getSize().sdiv(2);
    Vec2d childPos = new Vec2d(child.getPos().x, parentCenter - halfChildSize.y);
    if (child.getShapeType() == ShapeType.TEXT){
      childPos = new Vec2d(child.getPos().x, parentCenter + halfChildSize.y/2);
    }
    child.changeElementPosition(childPos);
  }

  public void hide(){
    this.hidden = true;
  }

  public void show(){
    this.hidden = false;
  }


  public void setFill(Color color){
    this.initialColor = color;
  }

  public void setStrokeWidth(double width){
    this.strokeWidth = width;
  }

  public List<Alignment> getAlignment(){
    return this.alignment;
  }

  public void setAlignment(Alignment align){
    if (this.alignment.contains(align)) return;
    this.alignment.add(align);
  }

  public Vec2d getPercentageOfParent(){
    return this.percentOfParent;
  }


  public void changeChildPositionPercentageBase(UIElement child){
    Vec2d span = this.getSize();
    Vec2d childPos = span.pmult(child.getPercentageOfParent());
    child.changeElementPosition(this.position.plus(childPos));
  }

  public void changeElementPosition(Vec2d newPos){
    this.position = newPos;
    this.calculateBoundingBox();
  }

  public Vec2d getPos(){
    return this.position;
  }

  public Vec2d getSize(){
    return this.size;
  }

  public void setSize(Vec2d newSize){
    this.size = newSize;
  }

  public void changeColor(Color color){
    this.initialColor = color;
  }

  public void setPos(Vec2d newPos){
    this.position = newPos;

  }

  public List<UIElement> getChildElements(){
    return this.childElements;
  }


  public void onTick(long nanosSincePreviousTick) {
    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onTick(nanosSincePreviousTick);
      this.realignChild(child);
    }
  }

  public void onLateTick() {
    // Don't worry about this method until you need it. (It'll be covered in class.)
  }


  public void onDraw(GraphicsContext g) {
    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onDraw(g);
    }

  }

  public void onKeyTyped(KeyEvent e) {

  }

  public void onKeyPressed(KeyEvent e) {

  }

  public void onKeyReleased(KeyEvent e) {

  }

  public void onMouseClicked(MouseEvent e) {
    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onMouseClicked(e);
    }
  }

  public void onMousePressed(MouseEvent e) {
    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onMousePressed(e);
    }

  }

  public void onMouseReleased(MouseEvent e) {
    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onMouseReleased(e);
    }
  }

  public void onMouseDragged(MouseEvent e) {
    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onMouseDragged(e);
    }

  }

  public void onMouseMoved(MouseEvent e) {
    for (UIElement child : this.childElements){
      if (child.hidden) continue;
      child.onMouseMoved(e);
    }
  }

  public void onMouseWheelMoved(ScrollEvent e) {

  }

  public void onFocusChanged(boolean newVal) {

  }

  public Vec2d getPositionRatio(Vec2d screenSize){
    return new Vec2d(this.getPos().x / screenSize.x, this.getPos().y / screenSize.y);
  }

  public Vec2d getDimensionRatio(Vec2d screenSize){
    return new Vec2d(this.getSize().x / screenSize.x, this.getSize().y / screenSize.y);
  }


  public void setInitialSize(Vec2d initSize){
    this.positionRatio = this.getPositionRatio(initSize);
    this.dimensionRatio = this.getDimensionRatio(initSize);

    for (UIElement child : this.childElements){
      child.setInitialSize(initSize);
    }
  }

  public void realignChild(UIElement child){
      // change child pos based on percentage
      if (child.getPercentageOfParent().x > -1){
        this.changeChildPositionPercentageBase(child);
      }

        for (Alignment alignment : child.getAlignment()) {
          switch (alignment) {
            case CENTER:
              centerElement(child);
              break;
            case VERT_CENTER:
              vertCenterElement(child);
              break;
            case LEFT:
              appendToLeft(child, child.offsetFromParent);
              break;
            case RIGHT:
              appendToRight(child, child.offsetFromParent);
              break;
            case TOP:
              appendToTop(child, child.offsetFromParent);
              break;
            case BOTTOM:
              appendToBottom(child, child.offsetFromParent);
              break;
          }
      }

  }

  public void onResize(Vec2d newSize) {
    for (UIElement child : this.childElements){
        if (child.hidden) continue;

      // resize child individually
        child.onResize(newSize);
        this.realignChild(child);
    }

    // position relative to screen
    this.position = new Vec2d(positionRatio.x * newSize.x, positionRatio.y * newSize.y);

    // size relative to screen height, maintaining aspect ratio
    double height = dimensionRatio.y * newSize.y;
    double width = this.aspectRatio * height;

    // make sure element would fit horizontally before changing size
    if (this.position.x + width <= newSize.x && this.position.y + height <= newSize.y){
      this.size = new Vec2d(width, height);
      return;
    } else if (this.position.y + this.size.y <= newSize.y && this.position.x + this.size.x <= newSize.x){
      return;
    }

    // otherwise try resizing the other way
    width = dimensionRatio.x * newSize.x;
    height = width / this.aspectRatio;

    if (this.position.x + width <= newSize.x && this.position.y + height <= newSize.y){
      this.size = new Vec2d(width, height);
      return;
    }
  }

  public void onShutdown() {

  }

  public void onStartup() {

  }
  public void calculateBoundingBox(){

  }

}
