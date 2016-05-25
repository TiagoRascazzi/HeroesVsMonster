import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class CrossfireTrap extends RoomCard{
   
   public CrossfireTrap(){
      super(3);
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   public boolean processKeyInput(KeyEvent e){ 
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            return true;
         }
      }
      return super.processKeyInput(e);  
   }
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      return super.processMouseInput(screenSize, e);
   }
   
}