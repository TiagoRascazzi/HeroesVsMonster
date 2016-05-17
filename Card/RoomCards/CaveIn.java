import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class CaveIn extends RoomCard{
   
   public CaveIn(){
      super(1);
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   /*
      Comment #6
         here is an other card for example it is over writing the method but for now it is dion the same thing as the emptyroom card (Nothing)
         so we have to add stuff to processKeyInput method
         it is the same for the other 13 card that have have made (copy and pasted the whole class but changd names, and texture id)
         
        GOTO Player.java at Comment #7    
         
   */
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