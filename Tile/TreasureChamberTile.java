import java.awt.Image;
import javax.swing.ImageIcon;

public class TreasureChamberTile extends Tile{
  


   public TreasureChamberTile(int orientation){
      super(orientation, 6, false, false, true, true, true, true);
      changeMaxNumOfPlayers(4);
   }
   
}