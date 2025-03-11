package o2;

import engine.sound.SoundSample;
import java.util.HashMap;
import java.util.Map;

public final class SoundManager {
  public static Map<String, SoundSample> sounds = initSoundMap();

  private static Map<String, SoundSample> initSoundMap(){
    Map<String, SoundSample> map = new HashMap<>();
    map.put("background", new SoundSample("src/o2/files/sounds/background_music.wav", true, .7f));
    map.put("walking", new SoundSample("src/o2/files/sounds/walking.wav", true, .6f));
    map.put("laser_shot", new SoundSample("src/o2/files/sounds/laser_shot.wav", false, .5f));
    map.put("rifle_shot", new SoundSample("src/o2/files/sounds/rifle_shot.wav", false, .5f));
    map.put("mana", new SoundSample("src/o2/files/sounds/mana.wav", false, 1f));
    map.put("add_inventory", new SoundSample("src/o2/files/sounds/add_inventory.wav", false, 1f));
    map.put("tree_activation", new SoundSample("src/o2/files/sounds/chimes.wav", false, 1f));

    return map;
  }



}
