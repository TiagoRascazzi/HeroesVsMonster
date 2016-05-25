import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;

public class CaveIn extends RoomCard{
   
   public CaveIn(){
      super(1);
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   
   public boolean processKeyInput(KeyEvent e){
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            
            Random random = new Random();
            int dice = random.nextInt(6) + 1;
            int guess = random.nextInt(6) + 1;  //maybe ask the player for a number
            
            if(dice == guess) //maybe print what happened
               damage += 1000000;
            else
               damage += random.nextInt(6) + 1;
            
            return true;
         }
      }
      return super.processKeyInput(e);
   }
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      return super.processMouseInput(screenSize, e);
   }
   
}