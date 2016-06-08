import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;

public class Potion extends RoomCard{
   
   public Potion(){
      super(9);
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
   
   public void actionKeyInput(KeyEvent e, int count){
      if(e.getKeyChar() == (count+"").charAt(0)){ //if use potion
         System.out.println("use potion");
         usePotion();
      }
   }
   public void actionMouseInput(Point screenSize, Point actionPos, MouseEvent e, int count){
      if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*count), actionPos.y+40+25+(25*count))){  //if use potion
         System.out.println("use potion");
         usePotion();
      }
   }
   
   public void usePotion(){
      Random random = new Random();
      int dice = random.nextInt(12) + 1;
      damage -= dice;
      getRidOfCard = true;
   }
   
   public String[] getPrintableAction(){
      String[] actions = {"Use Potion"};
      return actions;
   }
}