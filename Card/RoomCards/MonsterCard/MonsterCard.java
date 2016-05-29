import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;

public abstract class MonsterCard extends RoomCard{
   
   protected enum MonsterCardState {SELECT, COMBAT, WAIT_AND_SEE, ESCAPE};
   protected MonsterCardState mscState;
   protected int monsterLifes;
   
   protected int attack;
   protected int waitAndSee;
   protected int escape;
   
   public MonsterCard(int tID, int mlives, String path, int numOfMonst){
      super(tID);
      mscState = MonsterCardState.SELECT;
      monsterLifes = mlives;
      
      try{
         getRandomFileData(path, numOfMonst);
      }catch(IOException e){
         e.printStackTrace();
      }
      
   }
   
   public void getRandomFileData(String path, int numOfMonst)throws IOException{
      
      int rand = (int)(Math.random() * (numOfMonst + 1));
      System.out.println(String.format("%03d", rand));
      
      BufferedReader bufferedReader = new BufferedReader(new FileReader(path+"/"+String.format("%03d", rand)+".monster"));
      
      List<String> textData = new LinkedList<String>();
      String line;
      int count = 0;
      while((line = bufferedReader.readLine()) != null) {
         if (line.charAt(0) != '#'){
            if(count == 0)
               attack = Integer.parseInt(line);
            if(count == 1)
               waitAndSee = Integer.parseInt(line);
            if(count == 2)
               escape = Integer.parseInt(line);
            count++;
         }
      }
      bufferedReader.close();
      System.out.println(this.getClass().getSimpleName()+" -> attack: "+attack+", waitAndSee: "+waitAndSee+", escape: "+escape);
   }
   
   
   public void drawAction(Graphics2D g, int posX, int posY){ 
      actionPos.setLocation(posX, posY);
      if(cardState == CardState.ACTION){
         if(mscState == MonsterCardState.SELECT){
            g.drawString("What do you want to do: ", posX+8, posY+25);
            GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+150, posY+65, posY+90);
            g.drawString("(1) Combat", posX+8, posY+50);
            GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+150, posY+90, posY+115);
            g.drawString("(2) Wait and see", posX+8, posY+75);
            GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+150, posY+115, posY+140);
            g.drawString("(3) Escape", posX+8, posY+100);
         }
      }
      super.drawAction(g, posX, posY);
   }
   
   public ActionCard processKeyInput(KeyEvent e){
      if(cardState == CardState.ACTION){
         if(mscState == MonsterCardState.SELECT){
            if(e.getKeyCode() == KeyEvent.VK_1 ){
               mscState = MonsterCardState.COMBAT;
            }
            else if(e.getKeyCode() == KeyEvent.VK_2 ){
               mscState = MonsterCardState.WAIT_AND_SEE;
            }
            else if(e.getKeyCode() == KeyEvent.VK_3 ){
               mscState = MonsterCardState.ESCAPE;
            }
         }else if(mscState == MonsterCardState.COMBAT){
            if(e.getKeyCode() == KeyEvent.VK_1 ){ 
               System.out.println("You selected LeapAside");
            }
            if(e.getKeyCode() == KeyEvent.VK_2 ){
               System.out.println("You selected MightyBlow");
            }
            if(e.getKeyCode() == KeyEvent.VK_3 ){
               System.out.println("You selected Slash");
            }
         }
      }
      return super.processKeyInput(e);  
   }
   
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.ACTION){
         if(mscState == MonsterCardState.SELECT){
            if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*1), actionPos.y+40+25+(25*1))){
               mscState = MonsterCardState.COMBAT;
            }
            if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*2), actionPos.y+40+25+(25*2))){
               mscState = MonsterCardState.WAIT_AND_SEE;
            }
            if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*3), actionPos.y+40+25+(25*3))){
               mscState = MonsterCardState.ESCAPE;
            }
         }else if(mscState == MonsterCardState.COMBAT){
            int width = (int)(screenSize.x/8);
            int heigth = (int)(screenSize.y/4);
            int space = (int)(screenSize.x/64);
            
            int centerPosX = (int)(screenSize.x/2)-(int)(width/2);
            int bottomPosY = (int)(screenSize.y-(screenSize.y/3));
            if(GUI.hover(centerPosX-width-space, centerPosX-width-space+width, bottomPosY, bottomPosY+heigth)){ 
               System.out.println("You selected LeapAside");
            }
            if(GUI.hover(centerPosX, centerPosX+width, bottomPosY, bottomPosY+heigth)){
               System.out.println("You selected MightyBlow");
            }
            if(GUI.hover(centerPosX+width+space, centerPosX+width+space+width, bottomPosY, bottomPosY+heigth)){
               System.out.println("You selected Slash");
            }
         }
      }
      return super.processMouseInput(screenSize, e);
   }
   
   public void combat(){
      
   }
   public void escape(){
      
   }
   public void flee(){
      
   }
   public void slashEscape(){
      
   }
   public void slashCombat(){
      
   }
   
   //AI
   public void attack(){
   
   }
   
   public int getMonsterLives(){
      return monsterLifes;
   }
   public boolean isInCombat(){
      return mscState == MonsterCardState.COMBAT;
   }
}