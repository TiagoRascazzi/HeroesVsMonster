import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;

public class CurseOfTheWizard extends RoomCard{
   
   public CurseOfTheWizard(){
      super(6);
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
            int dice = random.nextInt(3) + 1;
            
            for(int i=0; i<HVMPanel.board.numRows(); i++){
               for(int j=0; j<HVMPanel.board.numColumns(); j++){
                  tile = HVMPanel.board.get(i, j);
                  if(tile != null && (tile instanceof FourWayCorridors || tile instanceof OneWayCorridor || tile instanceof ThreeWayCorridors || tile instanceof TwoWayCorridor)){
                     //if(dice == 1)                      //This is working i commented it out so that i can debug the tile.java class rotateclockwise method
                        tile.rotateClockwise(90);
                     //if(dice == 2)
                     //   tile.rotateClockwise(180);
                     //if(dice == 3)
                     //   tile.rotateClockwise(270);
                  }
               }
            }
            active = false;
         }
      }
      return super.processKeyInput(e);  
   }
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            active = false;
         }
      }
      return super.processMouseInput(screenSize, e);
   }
   
}