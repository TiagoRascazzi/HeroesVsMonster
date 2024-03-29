import java.io.File;
import javax.sound.sampled.*;

public class BGMusicPlayer{
   
   private static Clip clip;
   private static Clip[] lines;
   private static File[] musicFiles;
   private static File[] soundFiles;
   private static WeightedRandom<Integer> wr;
   private static boolean playingRandomMusic;
   
   private static int musicVolume;
   private static int soundVolume;
   private static int totalVolume;
   
   
   public static void loadMusics(){
      try{
         clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
         clip.addLineListener(new BGMusicListener());
         lines = new Clip[25];
         for(int i=0; i<lines.length; i++)
            lines[i] = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
      }
      catch(Exception e){
         e.printStackTrace();
      }
      musicFiles = new File[7];
      musicFiles[0] = new File("Music/menu/track1.wav");
      musicFiles[1] = new File("Music/game/track1.wav");
      musicFiles[2] = new File("Music/game/track2.wav");
      musicFiles[3] = new File("Music/game/track3.wav");
      musicFiles[4] = new File("Music/game/track4.wav");
      musicFiles[5] = new File("Music/game/track5.wav");
      musicFiles[6] = new File("Music/game/combat.wav");
      
      soundFiles = new File[5];
      soundFiles[0] = new File("Music/sound/footsteps.wav");
      soundFiles[1] = new File("Music/sound/gameOver.wav");
      soundFiles[2] = new File("Music/sound/lifeLost.wav");
      soundFiles[3] = new File("Music/sound/gold.wav");
      soundFiles[4] = new File("Music/sound/door.wav");
      //add all sounds      
      
      musicVolume = 7;
      soundVolume = 8;
      totalVolume = 9;
      
      wr = new WeightedRandom<Integer>();
      int[]msId = new int[]{1, 2, 3, 4, 5};
      for(int i=1; i<msId.length; i++)
         wr.add(1, msId[i]);
      playingRandomMusic = false;
   }
   
   public static void setMusicVolume(int v){
      musicVolume = v;
   }
   public static void setSoundVolume(int v){
      soundVolume = v;
   }
   
   public static void playMusic(int musicID){
      playingRandomMusic = false;
      stopMusic();
      if(clip != null){
         if(clip.isOpen())
            clip.close();
         try{
            clip.open(AudioSystem.getAudioInputStream(musicFiles[musicID]));
            updateMusicVolume();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
         }
         catch(Exception e){
            e.printStackTrace();
         }
      }
   }
   
   public static void playRandomMusic(){
      playingRandomMusic = false;
      stopMusic();
      playingRandomMusic = true;
      if(clip != null){
         if(clip.isOpen())
            clip.close();
         try{           
            clip.open(AudioSystem.getAudioInputStream(musicFiles[wr.next()]));
            updateMusicVolume();
            clip.loop(0);
         }
         catch(Exception e){
            e.printStackTrace();
         }
      }
   }
   
   public static void stopMusic(){
      playingRandomMusic = false;
      clip.stop();
      clip.close();
   }
   
      
   public static void playSound(int soundId){
      Clip validLine = getNextValidLine();
      if(validLine != null){
         try{           
            validLine.open(AudioSystem.getAudioInputStream(soundFiles[soundId]));
            updateSoundVolume();  
            validLine.loop(0);
         }catch(Exception e){
            e.printStackTrace();
         }
      }
   }
   
   public static Clip getNextValidLine(){
      for(int i=0; i<lines.length; i++){
         if(!lines[i].isOpen())
            return lines[i];
      }
      return null;
   }
   
   public static void stopSound(){
      for(int i=0; i<lines.length; i++){
         lines[i].stop();
         lines[i].close();
      }
   }
   
   public static class BGMusicListener implements LineListener{
      @Override
      public void update(LineEvent event){
         if(playingRandomMusic && event.getType() == LineEvent.Type.STOP){
            playRandomMusic();
         }
      }
   }
   
   
   public static void updateTotalVolume(){
      updateMusicVolume();
      updateSoundVolume();
   }
   public static void updateMusicVolume(){
      if(clip.isOpen()){
         FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
         float vol = ((Math.abs(volume.getMinimum()) + volume.getMaximum()) /20*(musicVolume+10)/20*(totalVolume+10) )+volume.getMinimum();
         volume.setValue(vol);
      }    
   }
   public static void updateSoundVolume(){
      for(int i=0; i<lines.length; i++){
         if(lines[i].isOpen()){
            FloatControl volume = (FloatControl) lines[i].getControl(FloatControl.Type.MASTER_GAIN);
            float vol = ((Math.abs(volume.getMinimum()) + volume.getMaximum()) /20*(soundVolume+10)/20*(totalVolume+10) )+volume.getMinimum();
            volume.setValue(vol); 
         }
      }  
   }
   
   public static int getMainVolume(){
      return totalVolume;
   }
   public static int getMusicVolume(){
      return musicVolume;
   }
   public static int getSoundVolume(){
      return soundVolume;
   }
   
   public static void increaseMainVolume(){
      if(totalVolume<10)
         totalVolume++;
      updateTotalVolume();
   }
   public static void decreaseMainVolume(){
      if(totalVolume>0)
         totalVolume--;
      updateTotalVolume();
   }
   
   public static void increaseMusicVolume(){
      if(musicVolume<10)
         musicVolume++;
      updateMusicVolume();
   }
   public static void decreaseMusicVolume(){
      if(musicVolume>0)
         musicVolume--;
      updateMusicVolume();
   }
   
   public static void increaseSoundVolume(){
      if(soundVolume<10)
         soundVolume++;
      updateSoundVolume();
   }
   public static void decreaseSoundVolume(){
      if(soundVolume>0)
         soundVolume--;
      updateSoundVolume();
   }
   
}