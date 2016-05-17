import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class ActionCard{
   
   protected enum CardState {SHOW, ACTION};
   protected CardState cardState;
   private int textureID;
   
   public ActionCard(int tID){
      textureID =  tID;
      cardState = CardState.SHOW;
   }
   
   public int getTextureID(){
      return textureID;
   }
   
   /*
      Comment #11
        the method drawAction in action card is going to draw the default commands to the screen (No default yet)
        
        GOTO EmptyRoom.java at Comment #12
   */
   public void drawAction(Graphics2D g, int posX, int posY){
      
   }
   
   /*
     Comment #4
     
     Here the method processKeyInput is going to be where it is going to do what the card has to do when a button is pressed
     
     In The ActionCard it is the default action 
     which is that if it is in the state of showing a card and you pressed enter it change the state to action
     
     GOTO EmptyRoom.java at Comment #5
     
   */  
   public boolean processKeyInput(KeyEvent e){
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            cardState = CardState.ACTION;
         }
      }
      return false;
   }
   
   /*
      Comment #8
      Here is the same as Comment #4 but for the mouse, and i havent done it yet so it normal that the mouse dont work for that now 
      
     GOTO EmptyRoom.java at Comment #9
   */
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      return false;
   }
   
   public boolean isShowing(){
      return cardState == CardState.SHOW;
   }
   
}