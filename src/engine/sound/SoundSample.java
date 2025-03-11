package engine.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundSample {
  private Clip clip;
  private InputStream inputStream;
  private float volume;
  private String filename;
  private boolean loop;

  public SoundSample(String filename, boolean loop, float volume) {
    this.loop = loop;
    this.volume = volume;
    this.filename = filename;
    try {
      File file = new File(filename);
      this.inputStream =
          new BufferedInputStream(
              new FileInputStream(file)
          );
      AudioInputStream stream =
          AudioSystem.getAudioInputStream(this.inputStream);

      this.clip = AudioSystem.getClip();
      this.clip.open(stream);

      if (loop) this.clip.loop(Clip.LOOP_CONTINUOUSLY);
      this.clip.stop();

      this.adjustVolume(clip, volume);

    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e){
      System.err.println("SampledSoundEngine error:" + e);
    }
  }

  public void playOnce(){
    try {
      File file = new File(filename);
      InputStream inputStream =
          new BufferedInputStream(
              new FileInputStream(file)
          );
      AudioInputStream stream =
          AudioSystem.getAudioInputStream(inputStream);

      Clip clip = AudioSystem.getClip();
      clip.open(stream);
      clip.loop(0);
      clip.start();

      this.adjustVolume(clip, volume);

    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e){
      System.err.println("SampledSoundEngine error:" + e);
    }

  }

  public void adjustVolume(Clip clip, float volume){
    try {
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(20f * (float) Math.log10(volume));
    } catch (IllegalArgumentException ignored){

    }
  }

  public void playSound(){
    if (this.loop) this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    this.clip.start();
  }

  public void stopSound(){
    this.clip.stop();
  }

}
