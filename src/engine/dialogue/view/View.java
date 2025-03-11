package engine.dialogue.view;

import engine.Application;
import engine.dialogue.types.Dialogue;
import engine.dialogue.types.DialogueOption;
import engine.screen.Screen;
import engine.screen.ScreenManager;
import engine.support.Vec2d;
import engine.utils.SizeConstants;
import engine.utils.Types.DisplayMode;
import java.util.List;
import javafx.scene.input.KeyEvent;
import o2.App;

public class View {
  private Screen overlayScreen;
  private TextBox textBox = null;

  public View(App app, ScreenManager screenManager){
    this.overlayScreen = new Screen(SizeConstants.INIT_WINDOW_SIZE, DisplayMode.SHOW, screenManager){
      @Override
      public void onKeyPressed(KeyEvent e) {
        super.onKeyPressed(e);
        //System.out.println("KEYPRESS");
      }
      };
    app.addScreen("dialogueScreen", this.overlayScreen);
    this.textBox = new TextBox(this.overlayScreen, new Vec2d(400));
  }

  public void clearTextBox(){
    //this.overlayScreen.clearUIElements();
    this.textBox.hideTextBox();
  }

  public void startConversation(Dialogue dialogue, String id){
    this.textBox.setNewConversation(dialogue, id);

//    if (!dialogue.options.isEmpty()){
//      for (DialogueOption option : dialogue.options){
//        ChoiceBox choiceBox = new ChoiceBox(this.overlayScreen, option);
//      }
//    }
  }

  public String onTick(long nanos){
    return this.textBox.onTick(nanos);
  }

}
