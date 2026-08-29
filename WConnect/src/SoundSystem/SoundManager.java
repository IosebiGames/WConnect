package SoundSystem;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
	private Clip clip;
    
    public void playSound() {
      try {
          clip = AudioSystem.getClip();
          clip.open(AudioSystem.getAudioInputStream(SoundManager.class.getResource("/sound/click.wav")));
          clip.start();
      }catch (Exception e) {
         System.out.println("Problem with playing the sound: " + e.getMessage());
      }
   }
} 