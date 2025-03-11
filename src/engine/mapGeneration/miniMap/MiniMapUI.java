package engine.mapGeneration.miniMap;

import engine.components.ComponentTag;
import engine.components.MiniMapComponent;
import engine.gameworld.GameObject;
import engine.gameworld.GameWorld;
import engine.mapGeneration.MapFileLoader;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIImage;
import engine.ui.UIRect;
import engine.utils.Types.DisplayMode;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MiniMapUI extends UIElement {
  private UIImage mapbase;
  private GameWorld gameWorld;
  private Vec2d mapCenter;

  public MiniMapUI(Vec2d position, Vec2d size, GameWorld gameWorld, String file) {
    super(position, size, Color.grayRgb(0), UIType.DISPLAY);
    this.gameWorld = gameWorld;
    this.mapbase = new UIImage(file, size, UIType.DISPLAY, position, .5);
    this.mapCenter = this.mapbase.getPos().plus(this.mapbase.getSize().sdiv(2));

  }

  // map gameworld coordinates to minimap
  public Vec2d gameToMiniMap(Vec2d gameCoord){
    Vec2d gTopLeft = this.gameWorld.getWorldTopLeft();
    Vec2d gBottomRight = gTopLeft.plus(this.gameWorld.getWorldSize());

    Vec2d mTopLeft = this.mapbase.getPos();
    Vec2d mBottomRight = mTopLeft.plus(this.mapbase.getSize());

    Vec2d mapCoord = (gameCoord.minus(gTopLeft)).
        pmult((mBottomRight.minus(mTopLeft)).
            pdiv(gBottomRight.minus(gTopLeft))).plus(mTopLeft);

    return mapCoord;
  }

   @Override
   public void onTick(long nanosSincePreviousTick) {
    super.onTick(nanosSincePreviousTick);
     for (GameObject go : this.gameWorld.getAllGameObjects().values()){
       if (go.hasComponent(ComponentTag.MINI_MAP)){
         ((MiniMapComponent)go.getComponent(ComponentTag.MINI_MAP)).getMiniMapVer().setPos(this.gameToMiniMap(go.getTransform()
             .getPosition()));
       }
     }
   }


   @Override
   public void onDraw(GraphicsContext g) {
      super.onDraw(g);
      this.mapbase.onDraw(g);
      for (GameObject go : this.gameWorld.getAllGameObjects().values()){
        if (go.hasComponent(ComponentTag.MINI_MAP)){
          ((MiniMapComponent)go.getComponent(ComponentTag.MINI_MAP)).getMiniMapVer().onDraw(g);
        }
      }
  }


}
