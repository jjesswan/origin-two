package engine.dialogue.view;

import engine.dialogue.types.Dialogue;
import engine.screen.Screen;
import engine.support.Vec2d;
import engine.ui.UIImage;
import engine.ui.UIRect;
import engine.ui.UIText;
import engine.ui.animations.FadeTransition;
import engine.ui.animations.VerticalSlideTransition;
import engine.utils.Types.Alignment;
import engine.utils.Types.FillType;
import engine.utils.Types.UIType;
import java.util.Arrays;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TextBox {
  private UIImage box;
  private Dialogue dialogue;
  private int countdown = 0;
  private final int MAX_TIME = 200;
  private int currentMessageID = 0;
  private String id;
  private String FILEPATH = "o2/files/ui/textbox.png";

  public TextBox(Screen screen, Vec2d pos){
    this.box = new UIImage(FILEPATH, new Vec2d(0, 40), new Vec2d(400), UIType.DISPLAY);
    this.box.clearAnimations();
    this.box.addAnimations(Arrays.asList(
        new FadeTransition(.01, 1, 100)));

    screen.setToCorner(this.box, Alignment.BOTTOMLEFT, new Vec2d(10));
    screen.addUIElement(this.box);
    this.box.hide();
  }

  public void setNewConversation(Dialogue dialogue, String id){
    this.box.show();
    this.box.clearChildElements();
    this.box.clearAnimations();
    this.box.addAnimations(Arrays.asList(
        new FadeTransition(.01, 1, 200)));
    this.dialogue = dialogue;
    this.id = id;

    this.playMessage();
  }

  public void playMessage(){
    this.box.clearChildElements();
    this.box.show();
    System.out.println("playing message");
    UIText text = new UIText(this.dialogue.messages.get(this.currentMessageID), new Vec2d(0), new Vec2d(20), Color.grayRgb(255), UIType.DISPLAY, "Pixels");
    text.wrapText(this.dialogue.messages.get(this.currentMessageID), (int) this.box.getSize().x - 40);

    this.setCountdown();

    text.addAnimations(Arrays.asList(
        new FadeTransition(.01, 1, 80)));

    this.box.setToCorner(text, Alignment.TOPLEFT, new Vec2d(80, 30));
    this.box.addChildElement(text);
  }

  public void setCountdown(){
    this.countdown = MAX_TIME;
  }

  public String countdown(){
    if (this.countdown == -1) return null;
    this.countdown --;

    // switch to next message in messageSequence
    if (this.countdown <= 0){
      this.currentMessageID++;

      // if reached the end of the message sequence, return the next id and then exit
      if (this.currentMessageID >= this.dialogue.messages.size()){
        this.countdown = -1;
        return this.dialogue.nextID;
      }

      this.playMessage();
    }
    return this.id;
  }

  public String onTick(long nanos){
    return this.countdown();
  }

  public void onKeyPress(KeyEvent e){
    if (e.getCode() == KeyCode.SPACE){
      //System.out.println("NEXT KEY");
    }
  }

  public void hideTextBox(){
    this.box.hide();
  }

  public void showTextBox(){
    this.box.show();
  }



}
