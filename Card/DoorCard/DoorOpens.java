import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class DoorOpens extends DoorCard{
   
   public DoorOpens(Point p){
      super(26);
      moveTo = p;
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   
   public ActionCard processKeyInput(KeyEvent e){ 
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            BGMusicPlayer.playSound(4);
            active = false;
         }
      }
      return super.processKeyInput(e); 
   }
   
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            BGMusicPlayer.playSound(4);
            active = false;
         }
      }
      return super.processMouseInput(screenSize, e);
   }
}