import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class ActionCard{
   
   protected enum CardState {SHOW, ACTION, OPT1, OPT2, OPT3, OPT4, OPT5};
   protected CardState cardState;
   private int textureID;
   
   protected int damage = 0;
   protected int gold = 0;
   
   
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
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            cardState = CardState.ACTION;
         }
      }
      return false;
   }
   
   public boolean isShowing(){
      return cardState == CardState.SHOW;
   }
      
   public int getDamage(){
      return damage;
   }
   public int getGold(){
      return gold;
   }
   
}