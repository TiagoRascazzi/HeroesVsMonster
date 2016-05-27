import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class Potion extends RoomCard{
   
   public Potion(){
      super(10);
      active = true;
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      super.drawAction(g, posX, posY);
   }
   public ActionCard processKeyInput(KeyEvent e){ 
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            active = false;
            return this;
         }
      }
      return super.processKeyInput(e);  
   }
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            active = false;
            return this;
         }
      }
      return super.processMouseInput(screenSize, e);
   }
   
   public String[] getPrintableAction(){
      String[] actions = {"Use Potion"};
      return actions;
   }
}