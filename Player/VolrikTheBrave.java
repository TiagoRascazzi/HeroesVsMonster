import java.awt.Image;
import javax.swing.ImageIcon;

public class VolrikTheBrave extends Player{
   
   private ImageIcon image = new ImageIcon("Img/Player/VolrikTheBrave.jpg"); 
   
   public VolrikTheBrave(){
      super("VolrikTheBrave", 0, 0, 15, 4, 7, 4, 8);
   }
   
   public Image getImage(){
      return image.getImage();
   }
   
}