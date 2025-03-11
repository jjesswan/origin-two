package engine.ui;

import engine.support.FontMetrics;
import engine.support.Vec2d;
import engine.utils.Types.Alignment;
import engine.utils.Types.ShapeType;
import engine.utils.Types.UIType;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;

public class UIText extends UIElement{
  private String text;
  private Font font;
  private FontMetrics fontMetrics;
  private double alpha = 1;
  private double rotation = 0;
  private Affine rotate = null;

  public UIText(String text, Vec2d position, Vec2d size,
      Color initialColor, UIType uiType, String font) {
    super(position, size, initialColor, uiType);
    this.text = text;
    super.shapeType = ShapeType.TEXT;
    this.font = new Font(font, super.size.x);
    this.fontMetrics = new FontMetrics(text, this.font);
    this.size = new Vec2d(this.fontMetrics.width, this.fontMetrics.height);

  }

  @Override
  public void setText(String text){
    this.text = text;
  }

  public void wrapText(String text, int textBoxWidth){
    textBoxWidth = textBoxWidth - 100;
    text = text.trim();

    int charWidth = (int) (this.fontMetrics.width / text.length());
    int numLines = (int) (this.fontMetrics.width / textBoxWidth);
    int charPerLine = textBoxWidth/charWidth;

    // get indicies of all spaces
    List<Integer> spacesIndex = new ArrayList<>();
    char[] string = text.toCharArray();
    for (int i=0; i<string.length; i++){
      if (string[i] == ' '){
        spacesIndex.add(i);
      }
    }

    for (int i=1; i<=numLines; i++){
      int indexPos = i * charPerLine;
      // if indexPos isn't a space, get the closest space
      if (!spacesIndex.contains(indexPos) && !spacesIndex.isEmpty()){
        int diff = indexPos - spacesIndex.get(0);
        int closestIndex = spacesIndex.get(0);
        for (Integer j : spacesIndex){
          if (Math.abs(indexPos - j) < Math.abs(diff) && indexPos - j > 0){
            diff = indexPos - j;
            closestIndex = j;
          }
        }
        indexPos = closestIndex;
      }

      StringBuilder s = new StringBuilder(text);
      s.setCharAt(indexPos, '\n');
      text = s.toString();
    }

    this.text = text;
  }



  public void setRotation(double degrees){
    this.rotate = new Affine();
    this.rotate.appendRotation(degrees, super.position.x + super.size.x/2, super.position.y + super.size.x/2);
    this.rotate.appendTranslation(15, 15);
  }



  @Override
  public void onDraw(GraphicsContext g) {
    if (this.hidden) return;
    this.playAnimations(g);
    if (this.rotate != null){
      g.setTransform(this.rotate);
    }

    g.setFont(this.font);
    g.setFill(this.initialColor);
    g.fillText(this.text, this.position.x, this.position.y);
    this.endAnimations(g);
    g.setTransform(new Affine());

    for (UIElement child : this.getChildElements()){
      child.onDraw(g);
    }
  }

  @Override
  public Vec2d getSize(){
    FontMetrics metrics = new FontMetrics(this.text, this.font);
    return new Vec2d(metrics.width, metrics.height);
  }

  @Override
  public void appendToLeft(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.LEFT);

    double xPos = this.position.x;
    double childWidth = child.getSize().x;
    double newPos = xPos - childWidth - offset;

    child.changeElementPosition(new Vec2d(newPos, child.getPos().y));
  }

  @Override
  public void appendToRight(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.RIGHT);

    double xPos = this.position.x + this.size.x;
    double newPos = xPos + offset;

    child.changeElementPosition(new Vec2d(newPos, child.getPos().y));
  }

  @Override
  public void appendToTop(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.TOP);

    double yPos = this.position.y;
    double childHeight = child.getSize().y;
    double newPos = yPos - childHeight - offset;

    child.changeElementPosition(new Vec2d(child.getPos().x, newPos));
  }

  @Override
  public void appendToBottom(UIElement child, double offset){
    child.offsetFromParent = offset;
    child.setAlignment(Alignment.BOTTOM);

    double yPos = this.position.y;
    double newPos = yPos + child.getSize().y + offset;

    child.changeElementPosition(new Vec2d(child.getPos().x, newPos));
  }
}
