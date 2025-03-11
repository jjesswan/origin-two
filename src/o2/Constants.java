package o2;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

public final class Constants {
  public static final int BEDROCK_LAYER = -10;
  public static final int BG_LAYER = -1;
  public static final int FOREGROUND_LAYER1 = 0;
  public static final int FOREGROUND_LAYER2 = 1;

  public static final int INTERACTABLE_LAYER = 10;
  public static final int ENTITY_LAYER = 20;
  public static final int OVERLAY_LAYER = 100;


  public static final String ROOT_DIRECTORY = "src/o2";


  public static final Color ELECTRIC_BLUE = Color.rgb(164, 246, 255);
  public static final String FONT = "Pixels";


  public static final List<String> GAME_SCREENS =
      Arrays.asList("gameScreen",
      "menuButtonsOverlay",
      "statsOverlay", "dialogueScreen");


  // id constants
  public static final String PLATNINUM_SHOOTER = "shooter_1";
  public static final String NEBULA_SHOOTER = "shooter_2";






}
