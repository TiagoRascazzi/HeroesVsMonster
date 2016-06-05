import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;
import java.awt.Color;

public class CaveIn extends RoomCard{
      
   public CaveIn(){
      super(1);
      active = true;
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      actionPos.setLocation(posX, posY);
      if(cardState == CardState.ACTION){
         g.drawString("Select number", posX+8, posY+25);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+40, posY+65, posY+90);
         g.drawString("(1)", posX+8, posY+50);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+40, posX+72, posY+65, posY+90);
         g.drawString("(2)", posX+40, posY+50);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+72, posX+104, posY+65, posY+90);
         g.drawString("(3)", posX+72, posY+50);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+40, posY+90, posY+115);
         g.drawString("(4)", posX+8, posY+75);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+40, posX+72, posY+90, posY+115);
         g.drawString("(5)", posX+40, posY+75);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+72, posX+104, posY+90, posY+115);
         g.drawString("(6)", posX+72, posY+75);
      }
      super.drawAction(g, posX, posY);
   }
   
   public ActionCard processKeyInput(KeyEvent e){
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER )
            cardState = CardState.ACTION;
      }else if(cardState == CardState.ACTION){
         if(e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_2 || 
             e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_4 || 
             e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_6){
            mainAction(e.getKeyChar()-'0');
            active = false;
         }
      }
      return super.processKeyInput(e);
   }
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) )
            cardState = CardState.ACTION;
      }else if(cardState == CardState.ACTION){
         if(GUI.hover(actionPos.x+8, actionPos.x+40, actionPos.y+65, actionPos.y+90)){
            mainAction(1);
            active = false;
         }else if(GUI.hover(actionPos.x+40, actionPos.x+72, actionPos.y+65, actionPos.y+90)){
            mainAction(2);
            active = false;
         }else if(GUI.hover(actionPos.x+72, actionPos.x+104, actionPos.y+65, actionPos.y+90)){
            mainAction(3);
            active = false;
         }else if(GUI.hover(actionPos.x+8, actionPos.x+40, actionPos.y+90, actionPos.y+115)){
            mainAction(4);
            active = false;
         }else if(GUI.hover(actionPos.x+40, actionPos.x+72, actionPos.y+90, actionPos.y+115)){
            mainAction(5);
            active = false;
         }else if(GUI.hover(actionPos.x+72, actionPos.x+104, actionPos.y+90, actionPos.y+115)){
            mainAction(6);
            active = false;
         }
      }
      return super.processMouseInput(screenSize, e);
   }
   
   public void mainAction(int guess){
      Random random = new Random();
      int dice = random.nextInt(6) + 1;
      
      if(dice == guess)
         damage += 1000000;
      else
         damage += random.nextInt(6) + 1;
      
      BGMusicPlayer.playSound(2);
      Display.showTextPopup("You lost: "+damage+" lives");
   }
   
}