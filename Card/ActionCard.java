import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class ActionCard{
   
   protected enum CardState {SHOW, ACTION, OPT1, OPT2, OPT3, OPT4, OPT5};
   protected CardState cardState;
   private int textureID;
   protected Point actionPos;
   
   protected int damage = 0;
   protected int gold = 0;
   protected boolean active = false;
   protected boolean newRoomCard = false;
   protected Point moveTo = null;
   protected boolean getRidOfCard = false;
   
   public ActionCard(int tID){
      textureID =  tID;
      cardState = CardState.SHOW;
      actionPos = new Point(0, 0);
   }
   
   public int getTextureID(){
      return textureID;
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){}
   
   public ActionCard processKeyInput(KeyEvent e){
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            cardState = CardState.ACTION;
         }
      }
      return null;
   }
   
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            cardState = CardState.ACTION;
         }
      }
      return null;
   }
   public boolean actionKeyInput(KeyEvent e, int count){return false;}
   public boolean actionMouseInput(Point screenSize, Point actionPos, MouseEvent e, int count){return false;}
   
   public boolean disableDefaultAction(){
      return false;
   }
   public boolean getRidOfCard(){
      return getRidOfCard;
   }
   public boolean isShowing(){
      return cardState == CardState.SHOW;
   } 
   public int getDamage(){
      int tmp = damage;
      damage = 0;
      return tmp;
   }
   public int getGold(){
      int tmp = gold;
      gold = 0;
      return tmp;
   }
   public boolean isActive(){
      return active;
   }
   public boolean receiveNewRoomCard(){
      return newRoomCard;
   }
   public String[] getPrintableAction(){
      return null;
   }
   public Point getMoveTo(){
      return moveTo;
   }
   
}