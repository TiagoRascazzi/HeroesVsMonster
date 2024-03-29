import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;

public class VampireBats extends RoomCard{
   
   public VampireBats(){
      super(13);
      active = true;
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   public ActionCard processKeyInput(KeyEvent e){ 
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            mainAction();
            active = false;
         }
      }
      return super.processKeyInput(e);  
   }
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            mainAction();
            active = false;
         }
      }
      return super.processMouseInput(screenSize, e);
   }
   
   public void mainAction(){
      Random random = new Random();
      int typeOfDice = random.nextInt(2) + 1;
      if(typeOfDice == 1){
         damage += random.nextInt(6) + 1;
      }else if(typeOfDice == 2){
         damage += random.nextInt(12) + 1;
      }
      BGMusicPlayer.playSound(2);
      Display.showTextPopup("You have lost "+damage+" lifes");
   }
   
}