package o2.uiscreens.playerstats;

import engine.screen.Screen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.utils.Types.DisplayMode;

public class StatsOverlayScreen extends Screen  {
  public PlantChargeUI plantChargeUI;
  public PlayerHealthUI playerHealthUI;
  public JetpackHealthUI jetpackHealthUI;
  public OxygenLevelUI oxygenLevelUI;


  public StatsOverlayScreen(Vec2d screenSize, DisplayMode displayMode, ScreenManager screenManager) {
    super(screenSize, displayMode, screenManager);
    this.initializeStats();
  }

  public void initializeStats(){
    this.plantChargeUI = new PlantChargeUI(this);
    this.playerHealthUI = new PlayerHealthUI(this, 2000);
    this.jetpackHealthUI = new JetpackHealthUI(this);
    this.oxygenLevelUI = new OxygenLevelUI(this);
  }

}
