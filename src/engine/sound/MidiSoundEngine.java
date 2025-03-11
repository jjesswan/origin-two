package engine.sound;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

public class MidiSoundEngine {

  public MidiSoundEngine(){
    try {
      Sequence song = MidiSystem.getSequence(
          new File("src/engine.sound/billie.mid"));
//      Synthesizer synthesizer = MidiSystem.getSynthesizer();
//      synthesizer.open();
//      synthesizer.getChannels()[0].controlChange(10, 126);

      Sequencer midiPlayer = MidiSystem.getSequencer();


      midiPlayer.open();
      midiPlayer.setSequence(song);
      midiPlayer.setLoopCount(0);
      midiPlayer.start();


      // Pan change
      ShortMessage panChangeMessage = new ShortMessage();
      panChangeMessage.setMessage(ShortMessage.CONTROL_CHANGE, 0, 10, 126);

      //midiPlayer.



    } catch (MidiUnavailableException | InvalidMidiDataException | IOException e){
      System.err.println("Midi engine.sound engine: " + e);
    }
  }

}
