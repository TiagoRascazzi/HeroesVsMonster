import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;
import java.awt.Color;

public class CaveIn extends RoomCard{
   
   private Point actionPos;
   
   public CaveIn(){
      super(1);
      actionPos = new Point(0, 0);
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
   
   public boolean processKeyInput(KeyEvent e){
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            cardState = CardState.ACTION;
         }
      }else if(cardState == CardState.ACTION){
         if(e.getKeyCode() == KeyEvent.VK_1 ){
            mainAction(1);
            return true;
         }else if(e.getKeyCode() == KeyEvent.VK_2 ){
            mainAction(2);
            return true;
         }else if(e.getKeyCode() == KeyEvent.VK_3 ){
            mainAction(3);
            return true;
         }else if(e.getKeyCode() == KeyEvent.VK_4 ){
            mainAction(4);
            return true;
         }else if(e.getKeyCode() == KeyEvent.VK_5 ){
            mainAction(5);
            return true;
         }else if(e.getKeyCode() == KeyEvent.VK_6 ){
            mainAction(6);
            return true;
         }
      }
      return super.processKeyInput(e);
   }
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            cardState = CardState.ACTION;
         }
      }else if(cardState == CardState.ACTION){
         if(GUI.hover(actionPos.x+8, actionPos.x+40, actionPos.y+65, actionPos.y+90)){
            mainAction(1);
            return true;
         }else if(GUI.hover(actionPos.x+40, actionPos.x+72, actionPos.y+65, actionPos.y+90)){
            mainAction(2);
            return true;
         }else if(GUI.hover(actionPos.x+72, actionPos.x+104, actionPos.y+65, actionPos.y+90)){
            mainAction(3);
            return true;
         }else if(GUI.hover(actionPos.x+8, actionPos.x+40, actionPos.y+90, actionPos.y+115)){
            mainAction(4);
            return true;
         }else if(GUI.hover(actionPos.x+40, actionPos.x+72, actionPos.y+90, actionPos.y+115)){
            mainAction(5);
            return true;
         }else if(GUI.hover(actionPos.x+72, actionPos.x+104, actionPos.y+90, actionPos.y+115)){
            mainAction(6);
            return true;
         }
      }
      return super.processMouseInput(screenSize, e);
   }
   
   public void mainAction(int guess){
      Random random = new Random();
      int dice = random.nextInt(6) + 1;
      
      if(dice == guess) //maybe print what happened
         damage += 1000000;
      else
         damage += random.nextInt(6) + 1;
   }
   
}