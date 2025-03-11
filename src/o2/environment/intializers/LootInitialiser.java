package o2.environment.intializers;

import o2.BlackBoard;
import engine.gameworld.GameWorld;
import engine.inventory.ItemType;
import engine.support.Vec2d;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import o2.loot.LootObject;
import o2.player.Player;

public class LootInitialiser {
  private GameWorld gameWorld;
  private Player player;
  private BlackBoard blackBoard;

  public LootInitialiser(GameWorld gameWorld, Player player, BlackBoard blackBoard){
    this.gameWorld = gameWorld;
    this.player = player;
    this.blackBoard = blackBoard;

    List<Vec2d> pos = new ArrayList<>();
    pos.addAll(Arrays.asList(new Vec2d(235, 1490), new Vec2d(88,1497), new Vec2d(1027,1277), new Vec2d(1391, 1509), new Vec2d(1945, 1485)));


    this.initializeLoot(new Vec2d(372, 1513), new Vec2d(40), new Vec2d(0), "loot", "nuclear_waste",
        "nuclear waste", "waste remnants from many years ago...", ItemType.LOOT);


    for (int i=0; i<pos.size(); i++){
      Vec2d index = new Vec2d(0);
      if (i % 2 == 0) index = new Vec2d(1,0);
      this.initializeLoot(pos.get(i), new Vec2d(40), index, "loot", "nuclear_waste",
          "nuclear waste", "waste remnants from many years ago...", ItemType.LOOT);
    }


  }

  private void initializeLoot(Vec2d pos, Vec2d size, Vec2d imgIndex, String referenceName, String id,
      String name, String descp, ItemType itemType){
    LootObject lootObject = new LootObject(pos, size, imgIndex, referenceName, id, this.player, this.gameWorld, this.blackBoard );
    lootObject.makeInventoryItem(name, descp, itemType);
  }

}
