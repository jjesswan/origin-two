package o2.environment.intializers;

import o2.BlackBoard;
import engine.ai.Pathfinder;
import engine.ai.navmesh.NavmeshNavigator;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import o2.enemies.BatEnemy;
import o2.enemies.DroneEnemy;
import o2.player.Player;

public class EnemyInitialiser {
  private GameWorld gameWorld;
  private BlackBoard blackBoard;
  private NavmeshNavigator navigator;
  private Player player;
  private Pathfinder pathfinder;

  public EnemyInitialiser(Player player, GameWorld gameWorld, BlackBoard blackBoard, NavmeshNavigator navigator){
    this.player = player;
    this.gameWorld = gameWorld;
    this.blackBoard = blackBoard;
    this.navigator = navigator;
    this.pathfinder = new Pathfinder(navigator);

    this.initializeBats();
    this.initializeDrones();

  }

  private void initializeBats(){
    new BatEnemy(this.player, this.pathfinder, new Vec2d(758, 1157), new Vec2d(30), this.gameWorld, this.blackBoard, 1);
    new BatEnemy(this.player, this.pathfinder, new Vec2d(800, 1100), new Vec2d(35), this.gameWorld, this.blackBoard, .3);
    new BatEnemy(this.player, this.pathfinder, new Vec2d(759, 1180), new Vec2d(25), this.gameWorld, this.blackBoard, 1.2);
    new BatEnemy(this.player, this.pathfinder, new Vec2d(714, 1080), new Vec2d(30), this.gameWorld, this.blackBoard, .7);

    new BatEnemy(this.player, this.pathfinder, new Vec2d(2049, 1362), new Vec2d(18), this.gameWorld, this.blackBoard, 2);
    new BatEnemy(this.player, this.pathfinder, new Vec2d(2106, 1299), new Vec2d(25), this.gameWorld, this.blackBoard, .3);
    new BatEnemy(this.player, this.pathfinder, new Vec2d(2060, 1425), new Vec2d(30), this.gameWorld, this.blackBoard, .5);

    new BatEnemy(this.player, this.pathfinder, new Vec2d(1331, 1458), new Vec2d(10), this.gameWorld, this.blackBoard, .4);
  }

  private void initializeDrones(){
    new DroneEnemy(this.player, this.pathfinder, new Vec2d(773, 853), new Vec2d(50), this.gameWorld, this.blackBoard, 1);
    new DroneEnemy(this.player, this.pathfinder, new Vec2d(918, 947), new Vec2d(60), this.gameWorld, this.blackBoard, 1.2);
    new DroneEnemy(this.player, this.pathfinder, new Vec2d(1280, 775), new Vec2d(60), this.gameWorld, this.blackBoard, .8);
    new DroneEnemy(this.player, this.pathfinder, new Vec2d(2245, 1195), new Vec2d(50), this.gameWorld, this.blackBoard, 1.2);
    new DroneEnemy(this.player, this.pathfinder, new Vec2d(2513, 1143), new Vec2d(60), this.gameWorld, this.blackBoard, 1);
    new DroneEnemy(this.player, this.pathfinder, new Vec2d(2678, 1156), new Vec2d(70), this.gameWorld, this.blackBoard, 1.2);
  }

}
