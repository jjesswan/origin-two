package engine.ui.buttonCommands;

import engine.screen.Screen;
import engine.utils.Types.DisplayMode;

public class ShowScreenCommand implements IButtonCommand{
  private Screen screen;

  public ShowScreenCommand(Screen screen){
    this.screen = screen;
  }

  @Override
  public void execute() {
    this.screen.changeDisplayMode(DisplayMode.SHOW);
  }
}
