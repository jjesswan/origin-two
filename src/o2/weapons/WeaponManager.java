package o2.weapons;

import o2.BlackBoard;
import engine.gameworld.GameWorld;
import engine.support.Vec2d;
import java.util.HashMap;
import java.util.Map;
import o2.Constants;
import o2.player.Player;

public class WeaponManager {
  public Map<String, Weapon> weapons;
  private GameWorld gameWorld;
  private Player player;

  public WeaponManager(GameWorld gameWorld, Player player){
    this.gameWorld = gameWorld;
    this.player = player;
    this.initWeapons();

  }

  public void initWeapons(){
    this.weapons = new HashMap<>();
    this.weapons.put(Constants.PLATNINUM_SHOOTER, new Weapon(Constants.PLATNINUM_SHOOTER, this.gameWorld, this.player, 20, 100, new Vec2d(10), new Vec2d(0), "rifle_shot", WeaponType.SHOOTER));
    this.weapons.put(Constants.NEBULA_SHOOTER, new Weapon(Constants.NEBULA_SHOOTER, this.gameWorld, this.player, 100, 150, new Vec2d(20), new Vec2d(1,0), "laser_shot", WeaponType.SHOOTER));
  }


  public void useCurrentWeapon(Vec2d target){
    if (this.weapons.containsKey(BlackBoard.currentWeaponID)){
      this.weapons.get(BlackBoard.currentWeaponID).useWeapon(target);
    } else {
      System.err.println("NO WEAPON EQUIPPED!");
    }
  }

}
