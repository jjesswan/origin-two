package o2.weapons;

import engine.components.ComponentTag;
import engine.components.HealthComponent;
import engine.components.PhysicsComponent;
import engine.gameworld.GameWorld;
import engine.raycast.RayCollisionInfo;
import engine.support.Vec2d;
import o2.SoundManager;
import o2.player.Player;

public class Weapon {
  protected String id;
  GameWorld gameWorld;
  Player player;
  private int speed, damage;
  private WeaponType weaponType;
  private Vec2d spriteID;
  private String sfx;
  private Vec2d bulletSize;

  public Weapon(String id, GameWorld gameWorld, Player player,
      int speed, int damage, Vec2d bulletSize, Vec2d spriteID, String soundFX, WeaponType weaponType){
    this.id = id;
    this.gameWorld = gameWorld;
    this.player = player;
    this.speed = speed;
    this.damage = damage;
    this.weaponType = weaponType;
    this.spriteID = spriteID;
    this.sfx = soundFX;
    this.bulletSize = bulletSize;
  }

  public void useWeapon(Vec2d target){
    switch(this.weaponType){
      case SHOOTER:
        this.shoot(target);
        break;
      case SWORD:
        break;
    }
  }

  public void shoot(Vec2d target){
    // sfx
    if (!SoundManager.sounds.containsKey(sfx)) System.err.println(sfx + " is not a valid sound.");
    else SoundManager.sounds.get(this.sfx).playOnce();

    RayCollisionInfo rayCollisionInfo = this.gameWorld.shootRay("player", target);

    Vec2d end = target;
    if (rayCollisionInfo != null && rayCollisionInfo.intersection != null) end = rayCollisionInfo.intersection;

    Vec2d source = this.player.getPlayerGO().getTransform().getPosition();
    Vec2d dir = end.minus(source).normalize();

    Ammo ammo = new Ammo(source, new Vec2d(this.bulletSize), this.spriteID, this.gameWorld, this.speed);
    ammo.shoot(dir, end);

    if (rayCollisionInfo == null || rayCollisionInfo.id == null) return;

    // if hit target has health component, then decrement component
    if (this.gameWorld.getGameObject(rayCollisionInfo.id).hasComponent(ComponentTag.HEALTH)){
      ((HealthComponent)this.gameWorld.getGameObject(rayCollisionInfo.id).getComponent(ComponentTag.HEALTH)).takeDamage(this.damage);
    }

    // apply impulse knockback
    if (this.gameWorld.getGameObject(rayCollisionInfo.id).hasComponent(ComponentTag.PHYSICS)){
      ((PhysicsComponent)this.gameWorld.getGameObject(rayCollisionInfo.id).getComponent(ComponentTag.PHYSICS)).applyImpulse(dir.smult(.5));
    }

  }

}
