package engine.utils;

import engine.support.Vec2d;
import javafx.scene.image.Image;

public class SpriteReferenceLoader {
  private final Image reference;
  private final double xoffset;
  private final double yoffset;
  private final Vec2d sourceDim;

  public SpriteReferenceLoader(String imgFile, int numCols, int numRows){
    this.reference = new Image(imgFile);
    double width = this.reference.getWidth();
    double height = this.reference.getHeight();

    // divide reference into chunks
    this.xoffset = width / numCols;
    this.yoffset = height / numRows;
    this.sourceDim = new Vec2d(this.xoffset, this.yoffset);
  }

  public Vec2d getSourceDim(){
    return this.sourceDim;
  }

  // returns sx,sy and sw,sh to be used in graphics context
  public Vec2d getSpriteAtIndex(Vec2d index){
    int col = (int) index.x;
    int row = (int) index.y;

    double horizPos = col * this.xoffset;
    double vertPos = row * this.yoffset;

    return new Vec2d(horizPos, vertPos);
  }

  public Image getImage(){
    return this.reference;
  }

  public double getAspectRatio(){
    return this.yoffset / this.xoffset;
  }

}
