import java.awt.Image;
import javax.swing.ImageIcon;

public class ElAdoranSureshot extends Player{
   
   private ImageIcon image = new ImageIcon("Img/Player/ElAdoranSureshot.jpg"); 
   
   public ElAdoranSureshot(){
      super("ElAdoranSureshot", 0, 0, 11, 3, 8, 5, 7);
   }
   
   public Image getImage(){
      return image.getImage();
   } 
   
}