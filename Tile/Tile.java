import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Tile{
   
   public static final int TOP = 0;
   public static final int BOTTOM = 1;
   public static final int LEFT = 2;
   public static final int RIGHT = 3;
   
   private int orientation;
   private int textureID;
   
   public Tile(int orien, int tID){
      orientation = orien;
      textureID = tID;
   }
   
   public int getTextureID(){
      return textureID;
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
  
}