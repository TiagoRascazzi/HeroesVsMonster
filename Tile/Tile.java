import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Tile{
   
   public static final int TOP = 0;
   public static final int BOTTOM = 1;
   public static final int LEFT = 2;
   public static final int RIGHT = 3;
   private static WeightedRandom<Tile> wr; 
   
   private int orientation;
   private int textureID;
   private int maxNumOfPlayers;
   
   public Tile(int orien, int tID, int mnop){
      orientation = orien;
      textureID = tID;
      maxNumOfPlayers = mnop;
   }
   public int getMaxNumOfPlayers(){
      return maxNumOfPlayers;
   }
   public int getTextureID(){
      return textureID;
   } 
   
   public void setOrientation(int orien){
      orientation = orien;
   } 
  
  public double getRotation(){
     if(orientation == TOP)
        return Math.toRadians(90);
     if(orientation == BOTTOM)
        return Math.toRadians(270);
     if(orientation == LEFT)
        return 0;
     if(orientation == RIGHT)
        return Math.toRadians(180);
     return -1;
  }
  public static Tile getRandomTile(){
     wr = new WeightedRandom<Tile>();
     wr.add(1, new Corridors(LEFT));
     wr.add(1, new TestTile(LEFT));
     return wr.next();
  }
  
}