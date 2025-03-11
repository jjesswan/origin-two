package engine.utils;

import engine.collisionShapes.PolygonEdge;
import engine.components.DrawComponent;
import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import engine.support.Vec2d;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import org.omg.PortableServer.POA;

public class PolygonBuilder {
  SpriteReferenceLoader reference;
  TransformComponent transform;
  Vec2d[] gamePolyPoints;
  List<PolygonEdge> edges = new ArrayList<>();

  // where relativePoints is a coordinate in the original canvas size

  /**
   * For sprite reference sheets. Takes into account one cell in a sprite reference sheet.
   *
   * @param initPos: intial gamePos of sprite
   * @param initSize: initial gameSize of sprite
   * @param reference: sprite reference from which image is pulled
   * @param relativePoints: canvas pixel locations of polygon points in whole canvas.
   */
  public PolygonBuilder(Vec2d initPos, Vec2d initSize, SpriteReferenceLoader reference, Vec2d[] relativePoints){
    this.gamePolyPoints = new Vec2d[relativePoints.length];

    double sprite_w = reference.getSourceDim().x;
    double sprite_h = reference.getSourceDim().y;

    for (int i=0; i< relativePoints.length; i++){
      Vec2d pt = relativePoints[i];
      float ratio_x = (float) ((pt.x % sprite_w) / sprite_w);
      float ratio_y = (float) ((pt.y % sprite_h) / sprite_h);

      Vec2d gamePos = initPos.plus(initSize.pmult(ratio_x, ratio_y));
      this.gamePolyPoints[i] = gamePos;
    }

    // make edges
    for (int i=0; i<gamePolyPoints.length; i++) {
      Vec2d base = gamePolyPoints[i];
      Vec2d tail = gamePolyPoints[0];
      if (i + 1 < gamePolyPoints.length)
        tail = gamePolyPoints[i + 1];

      // make edge
      PolygonEdge edge = new PolygonEdge(base, tail);
      this.edges.add(edge);
    }
  }

  public PolygonBuilder(Vec2d initPos, Vec2d initSize, DrawComponent drawComponent, Vec2d[] relativePoints){
    this.gamePolyPoints = new Vec2d[relativePoints.length];

    double sprite_w = drawComponent.getImageDimensions().x;
    double sprite_h = drawComponent.getImageDimensions().y;

    for (int i=0; i< relativePoints.length; i++){
      Vec2d pt = relativePoints[i];
      float ratio_x = (float) ((pt.x % sprite_w) / sprite_w);
      float ratio_y = (float) ((pt.y % sprite_h) / sprite_h);

      Vec2d gamePos = initPos.plus(initSize.pmult(ratio_x, ratio_y));
      this.gamePolyPoints[i] = gamePos;
    }

    // make edges
    for (int i=0; i<gamePolyPoints.length; i++) {
      Vec2d base = gamePolyPoints[i];
      Vec2d tail = gamePolyPoints[0];
      if (i + 1 < gamePolyPoints.length)
        tail = gamePolyPoints[i + 1];

      // make edge
      PolygonEdge edge = new PolygonEdge(base, tail);
      this.edges.add(edge);
    }
  }

  /**
   * PolygonBuilder using gameworld coordinates. Not to be confused with the other
   * constructors which take in canvas pixel coordinates.
   * @param initPos
   * @param initSize
   * @param gamePoints
   */
  public PolygonBuilder(Vec2d initPos, Vec2d initSize, Vec2d[] gamePoints){
    this.gamePolyPoints = gamePoints;

    // make edges
    for (int i=0; i<gamePolyPoints.length; i++) {
      Vec2d base = gamePolyPoints[i];
      Vec2d tail = gamePolyPoints[0];
      if (i + 1 < gamePolyPoints.length)
        tail = gamePolyPoints[i + 1];

      // make edge
      PolygonEdge edge = new PolygonEdge(base, tail);
      this.edges.add(edge);
    }
  }

  public void updatePolygonCollider(Vec2d translation, Vec2d bounds){
    Vec2d[] newPositions = this.gamePolyPoints;
    for (int i=0; i<this.gamePolyPoints.length; i++){
      newPositions[i] = this.gamePolyPoints[i].plus(translation);
    }

    for (Vec2d newPos : newPositions){
      if (bounds != null && (newPos.x >= bounds.x || newPos.y >= bounds.y || newPos.x < 0 || newPos.y < 0)){
        return;
      }
    }

    // otherwise, update poly points
    this.gamePolyPoints = newPositions;

    for (int i=0; i<this.edges.size(); i++){
      this.edges.get(i).tail = this.edges.get(i).tail.plus(translation);
      this.edges.get(i).base = this.edges.get(i).base.plus(translation);
    }
  }

  public Vec2d[] getPolyPoints(){
    return this.gamePolyPoints;
  }

  public List<PolygonEdge> getPolyEdges(){
    return this.edges;
  }

}
