import java.awt.Image;
import javax.swing.ImageIcon;

public class UlvGrimhand extends Player{
   
   private ImageIcon image = new ImageIcon("Img/Player/UlvGrimhand.jpg"); 
   
   public UlvGrimhand(){
      super("UlvGrimhand", 0, 0, 16, 7, 5, 6, 5);
   }
   
   public Image getImage(){
      return image.getImage();
   }
   
}