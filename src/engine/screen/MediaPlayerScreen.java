package engine.screen;

import engine.utils.SizeConstants;
import engine.utils.Types.DisplayMode;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import o2.Constants;

public class MediaPlayerScreen extends Screen {
  private MediaView mediaView;
  private Scene scene;
  private boolean playing = false;
  private int play = 10;

  public MediaPlayerScreen(Scene scene, ScreenManager screenManager) {
    super(SizeConstants.INIT_WINDOW_SIZE, DisplayMode.HIDE, screenManager);
    this.setScreenFill(Color.BLACK);
    this.scene = scene;

    // Create the view and add it to the Scene.
    this.mediaView = new MediaView();
    this.mediaView.setFitWidth(SizeConstants.INIT_WINDOW_SIZE.x);
    this.mediaView.setFitHeight(SizeConstants.INIT_WINDOW_SIZE.y);
  }

  public void play(String mediaFile){
    this.screenManager.showSingleScreen("mediaPlayer");
    System.out.println("PLAY MEDIA: " + mediaFile);

    File filestring = new File(mediaFile);
    Media media = new Media(filestring.toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(false);

    mediaPlayer.setOnError(()->
        System.out.println("media error"+mediaPlayer.getError().toString()));

    mediaPlayer.setOnEndOfMedia(() -> {
      changeDisplayMode(DisplayMode.HIDE);
      screenManager.showScreens(Constants.GAME_SCREENS);
      ((Pane)scene.getRoot()).getChildren().remove(mediaView);
    });

    mediaPlayer.setOnReady(mediaPlayer::play);

    this.mediaView.setMediaPlayer(mediaPlayer);
    ((Pane)this.scene.getRoot()).getChildren().add(mediaView);
  }
}
