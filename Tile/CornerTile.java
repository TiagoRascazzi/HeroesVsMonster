import java.awt.Image;
import javax.swing.ImageIcon;

public class CornerTile extends Tile{
   
   public static final int TOP_LEFT = 2;
   public static final int TOP_RIGHT = 0;
   public static final int BOTTOM_LEFT = 1;
   public static final int BOTTOM_RIGHT = 3;
      
   public CornerTile(int orien){
      super(orien, 1);
   }
   
}