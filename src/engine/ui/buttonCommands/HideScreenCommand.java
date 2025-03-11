package engine.ui.buttonCommands;

import engine.screen.Screen;
import engine.utils.Types.DisplayMode;

public class HideScreenCommand implements IButtonCommand{
  private Screen screen;

  public HideScreenCommand(Screen screen){
    this.screen = screen;
  }

  @Override
  public void execute() {
    this.screen.changeDisplayMode(DisplayMode.HIDE);
  }
}
