import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class ActionCard{
   
   protected enum CardState {SHOW, ACTION};
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
 
    //methods used to do the hovering effect later they migth go in a GUI class
  //// public static boolean hover(int x1, int x2, int y1, int y2){
  //    return (mouse.x>x1 && mouse.x<x2 && mouse.y>y1 && mouse.y<y2);
  // }
   
}