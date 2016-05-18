import java.io.File;
import javax.sound.sampled.*;

public class BGMusicPlayer implements LineListener{
   
   private Clip clip;
   private File[] musicFiles;
   private WeightedRandom<Integer> wr;
   private boolean playingRandomMusic;
   
   public BGMusicPlayer(){
      try{
         clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
         clip.addLineListener(this);
      }catch(Exception e){
         e.printStackTrace();
      }
      musicFiles = new File[6];
      musicFiles[0] = new File("Music/menu/track1.wav");
      musicFiles[1] = new File("Music/game/track1.wav");
      musicFiles[2] = new File("Music/game/track2.wav");
      musicFiles[3] = new File("Music/game/track3.wav");
      musicFiles[4] = new File("Music/game/track4.wav");
      musicFiles[5] = new File("Music/game/track5.wav");
      
      wr = new WeightedRandom<Integer>();
      for(int i=1; i<musicFiles.length; i++)
         wr.add(1, i);
      playingRandomMusic = false;
   }
   
   public void playMusic(int musicID){
      playingRandomMusic = false;
      stopMusic();
      if(clip != null){
         if(clip.isOpen())
            clip.close();
         try{
            clip.open(AudioSystem.getAudioInputStream(musicFiles[musicID]));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
         }catch(Exception e){
            e.printStackTrace();
         }
      }
   }
   
   public void playRandomMusic(){
      playingRandomMusic = false;
      stopMusic();
      playingRandomMusic = true;
      if(clip != null){
         try{           
            clip.open(AudioSystem.getAudioInputStream(musicFiles[wr.next()]));
            clip.loop(0);
         }catch(Exception e){
            e.printStackTrace();
         }
      }
   }
   
   public void stopMusic(){
      playingRandomMusic = false;
      clip.stop();
      clip.close();
   }
   
   @Override
   public void update(LineEvent event){
      if(playingRandomMusic && event.getType() == LineEvent.Type.STOP){
         playRandomMusic();
      }
   }
        
}