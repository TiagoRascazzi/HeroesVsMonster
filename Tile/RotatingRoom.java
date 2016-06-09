import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;

public class RotatingRoom extends Tile{
  

      
   public RotatingRoom(){
            
      super(8, false, true, true, false, false, false);
      setSearchable(false);
      active = true; 
   }
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   public ActionCard processKeyInput(KeyEvent e){ 
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            Tile tile = null;
            Random random = new Random();
            int dice = random.nextInt(4) + 1;
            
            for(int i=0; i<HVMPanel.board.numRows(); i++){
               for(int j=0; j<HVMPanel.board.numColumns(); j++){
                  tile = HVMPanel.board.get(i, j);
                  if(tile != null && (tile instanceof RotatingRoom)){
                     if(dice == 1)                      //This is working i commented it out so that i can debug the tile.java class rotateclockwise method
                        tile.rotateClockwise(90);
                     if(dice == 2)
                        tile.rotateClockwise(180);
                     if(dice == 3)
                        tile.rotateClockwise(270);
                     if(dice == 4)
                        tile.rotateClockwise(360);
                  }
               }
            }
            active = false;
         }
      }
      return super.processKeyInput(e);  
   }
   
}