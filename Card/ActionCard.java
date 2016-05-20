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
   
   public void drawAction(Graphics2D g, int posX, int posY){
      
   }
   
   public boolean processKeyInput(KeyEvent e){
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            cardState = CardState.ACTION;
         }
      }
      return false;
   }
   
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      return false;
   }
   
   public boolean isShowing(){
      return cardState == CardState.SHOW;
   }
   
}