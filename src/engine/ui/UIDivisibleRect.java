package engine.ui;

import engine.support.Vec2d;
import engine.support.Vec2i;
import engine.ui.UIRect;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.paint.Color;

public class UIDivisibleRect extends UIRect {
  private double cellWidth = 0, cellHeight = 0;
  private int rows, cols;

  public UIDivisibleRect(Vec2d position, Vec2d size,
      Color initialColor, FillType fill,
      UIType uiType, int rows, int cols) {
    super(position, size, initialColor, fill, uiType);
    this.divide(rows, cols);
  }

  public UIDivisibleRect(Vec2d position, Vec2d size, FillType fill,
      UIType uiType, int rows, int cols) {
    super(position, size, Color.BLACK, fill, uiType);
    this.alpha = 0;
    this.divide(rows, cols);
  }

  public void divide(int rows, int cols){
    this.rows = rows;
    this.cols = cols;
    this.cellWidth = this.size.x / this.cols;
    this.cellHeight = this.size.y / this.rows;
  }

  public void addAtIndex(int row, int col, UIElement child){
    Vec2d center = this.getCellCenter(col, row);
    Vec2d newPos = center.minus(child.getSize().sdiv(2));

    child.gridPos = new Vec2i(col, row);
    child.changeElementPosition(newPos);
  }

  public Vec2d getCellCenter(int rows, int cols){
    double xoffset = this.cellWidth * rows + this.cellWidth/2;
    double yoffset = this.cellHeight * cols + this.cellHeight/2;

    return super.position.plus((float) xoffset, (float) yoffset);
  }

  @Override
  public void onResize(Vec2d newSize) {
    super.onResize(newSize);

    this.cellWidth = this.size.x / this.cols;
    this.cellHeight = this.size.y / this.rows;

    for (UIElement child : this.childElements){
      if (child.gridPos != null){
        this.addAtIndex(child.gridPos.x, child.gridPos.y, child);
        child.onResize(newSize);
      }
    }
  }


}
