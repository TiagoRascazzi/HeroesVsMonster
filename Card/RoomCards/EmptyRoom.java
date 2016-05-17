import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class EmptyRoom extends RoomCard{
   
   public EmptyRoom(){
      super(0);
   }
   
   /*
      Comment #12
        The drawAction method is overwriten to draw card specific action to the screen (No implemented yet draws nothing)
        
        GOTO Display.java at Comment #13
      
   */
   
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   /*
      Comment #5
        Here it is going to over write the default method (just showed) in the ActionCard
        It is giong to be where the action specific to each card gonna be
        
   */
   public boolean processKeyInput(KeyEvent e){ 
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){// this is just going to say that the card has ended doing its action after pressing the enter button when the card is showed
            return true;
         }
      }
      return super.processKeyInput(e);  //over here you can see that i call th default just in case
   }
   //GOTO CaveIn.java at Comment #6
   
   /* 
     Comment #9
     An again same as comment #5 but for the mouse , and not yet implemented
     
     GOTO Player.java at Comment #10
     
   */
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      return super.processMouseInput(screenSize, e);
   }
   
}