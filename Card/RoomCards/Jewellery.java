import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class Jewellery extends RoomCard{
   
   public Jewellery(){
      super(2);
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
      this.gold += 100;  //TODO only thing that this card could need is that instead of 10 its a number between some range
      Display.showTextPopup("You received 100 gold\nyou have a total of "+ (HVMPanel.players.get(HVMPanel.currentPlayer).gold()+100));
   }
   
   
}