import java.awt.Image;
import javax.swing.ImageIcon;

public class Tile{
   
   //private TileSide top;
   //private TileSide bottom;
   //private TileSide left;
   //private TileSide right;
   private int entrySide;//?
   private boolean searchIcon;
   private int textureID;
   private ImageIcon image = new ImageIcon("Img/Tile/4_way_corridors.png");  
   
   public Tile(){
      
   }
   
   public Image getImage(){
      return image.getImage();
   }
   
   public int getTextureID(){
      return textureID;
   }
   
}