package engine.raycast;

import engine.components.CollisionComponent;
import engine.components.ComponentTag;
import engine.components.DrawComponent;
import engine.components.PhysicsComponent;
import engine.components.SpriteComponent;
import engine.components.TickComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import engine.utils.Types.CollisionBehaviorType;
import engine.utils.Types.CollisionShapeType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import o2.Constants;

public class RayLine {
  private int duration;
  private GameWorld gameWorld;

  public RayLine(RayCollisionInfo rayCollisionInfo, GameWorld gameWorld, Color color, double lineWidth, int duration1){
    this.duration = duration1;
    this.gameWorld = gameWorld;
    if (rayCollisionInfo != null) {
      Ray ray = rayCollisionInfo.ray;
      Vec2d end = rayCollisionInfo.intersection;

      if (ray != null) {
        Vec2d src = ray.src.plus(new Vec2d(10));
        String id = "rayline_" + ray.src + Math.random();
        GameObject rayline = new GameObject(ray.src, new Vec2d(0), Constants.INTERACTABLE_LAYER,
            id);
        rayline.addComponent(new DrawComponent() {
          @Override
          public void draw(GraphicsContext g) {
            if (duration <= 0) return;
            g.setGlobalAlpha(1);
            g.setStroke(color);
            g.setLineWidth(lineWidth);

            if (end != null){
              g.strokeLine(src.x, src.y, end.x, end.y);
            }
            else{
              Vec2d endPos = src.plus(ray.dir.smult(1000));
              g.strokeLine(src.x, src.y, endPos.x, endPos.y);
            }
          }
        });
        rayline.addComponent(new TickComponent() {
          @Override
          public void defineTickAction() {
            decrementTimer(id);
            onTargetHit();
          }
        });

        gameWorld.addGameObject(id, rayline);
      }
    }
  }

  // to be overridden with game specific logic
  protected void onTargetHit(){
  }

  private void decrementTimer(String id){
    if (this.duration >= 0) this.duration--;
    else this.gameWorld.deleteGameObject(id);
  }

}
