import java.awt.Image;
import javax.swing.ImageIcon;

public class SirRohan extends Player{
   
   private ImageIcon image = new ImageIcon("Img/Player/SirRohan.jpg"); 
   
   public SirRohan(){
      super("SirRohan", 0, 0, 17, 6, 4, 9, 4);
   }
   
   public Image getImage(){
      return image.getImage();
   }
   
}