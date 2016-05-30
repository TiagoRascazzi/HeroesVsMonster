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
   
   private ArrayList<Integer>AttackVals;
   private ArrayList<Integer>WaitAndSeeVals;
   private ArrayList<Integer>EscapeVals;
   
   protected enum MonsterCardState {SELECT, COMBAT};
   protected MonsterCardState mscState;
   protected int monsterLifes;
   
   protected int attack;
   protected int waitAndSee;
   protected int escape;
   
   protected String combatMessage = "";
   
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
      
      String randFilename = String.format("%03d", (int)(Math.random() * (numOfMonst + 1)) );
      //System.out.println(randFilename);  //print the random filename
      
      //tmp for testing purpose while monster file are not done
      //BufferedReader bufferedReader = new BufferedReader(new FileReader(path+"/"+randFilename+".monster"));
      BufferedReader bufferedReader = new BufferedReader(new FileReader("Card/RoomCards/MonsterCard/ChampionOfChaos/000.monster"));
      
      String line;
      int count = 0;
      while((line = bufferedReader.readLine()) != null && count<3){
         if(line.charAt(0) != '#'){
            String[] strVal = line.split(" ");
            ArrayList<Integer>val = new ArrayList<Integer>();
            for(int i=0; i<strVal.length; i++) 
               val.add(Integer.parseInt(strVal[i]));
            if(count == 0)
               AttackVals = val;
            else if(count == 1)
               WaitAndSeeVals = val;
            else if(count == 2)
               EscapeVals = val;
            count++;
         }
      }
      bufferedReader.close();
      
      /*  To print them to the screen for testing
      System.out.print("AttackVals: ");
      for(int i=0; i<AttackVals.length; i++)
         System.out.print(AttackVals[i]+" ");
      System.out.print("\nWaitAndSeeVals: ");
      for(int i=0; i<WaitAndSeeVals.length; i++)
         System.out.print(WaitAndSeeVals[i]+" ");
      System.out.print("\nEscapeVals: ");
      for(int i=0; i<EscapeVals.length; i++)
         System.out.print(EscapeVals[i]+" ");
      */
   }
   
   
   public void drawAction(Graphics2D g, int posX, int posY){ 
      actionPos.setLocation(posX, posY);
      if(cardState == CardState.ACTION){
         if(mscState == MonsterCardState.SELECT){
            g.drawString("What do you want to do: ", posX+8, posY+25);
            GUI.chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+150, posY+65, posY+90);
            g.drawString("(1) Attack", posX+8, posY+50);
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
            if(e.getKeyCode() == KeyEvent.VK_1 )
               whatsNext(new ArrayList<Integer>(AttackVals));
            else if(e.getKeyCode() == KeyEvent.VK_2 )
               whatsNext(new ArrayList<Integer>(WaitAndSeeVals));
            else if(e.getKeyCode() == KeyEvent.VK_3 )
               whatsNext(new ArrayList<Integer>(EscapeVals));
         }else if(mscState == MonsterCardState.COMBAT){
            if(e.getKeyCode() == KeyEvent.VK_1 )
               combat("LeapAside");
            else if(e.getKeyCode() == KeyEvent.VK_2 )
               combat("MightyBlow");
            else if(e.getKeyCode() == KeyEvent.VK_3 )
               combat("Slash");
         }
      }
      return super.processKeyInput(e);  
   }
   
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.ACTION){
         if(mscState == MonsterCardState.SELECT){
            if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*1), actionPos.y+40+25+(25*1)))
               whatsNext(new ArrayList<Integer>(AttackVals));
            if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*2), actionPos.y+40+25+(25*2)))
               whatsNext(new ArrayList<Integer>(WaitAndSeeVals));
            if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*3), actionPos.y+40+25+(25*3)))
               whatsNext(new ArrayList<Integer>(EscapeVals));
         }else if(mscState == MonsterCardState.COMBAT){
            int width = (int)(screenSize.x/8);
            int heigth = (int)(screenSize.y/4);
            int space = (int)(screenSize.x/64);
            
            int centerPosX = (int)(screenSize.x/2)-(int)(width/2);
            int bottomPosY = (int)(screenSize.y-(screenSize.y/3));
            if(GUI.hover(centerPosX-width-space, centerPosX-width-space+width, bottomPosY, bottomPosY+heigth))
               combat("LeapAside");
            else if(GUI.hover(centerPosX, centerPosX+width, bottomPosY, bottomPosY+heigth))
               combat("MightyBlow");
            else if(GUI.hover(centerPosX+width+space, centerPosX+width+space+width, bottomPosY, bottomPosY+heigth))
               combat("Slash");
         }
      }
      return super.processMouseInput(screenSize, e);
   }
   
   //funtion that decide what happen depending on the file input
   public void whatsNext(ArrayList<Integer>CurrentVals){
      if(CurrentVals.get(0) == 1){ //Combat
         System.out.println("COMBAT");          // <- print for testing purpose
         System.out.println(CurrentVals);       // <- print for testing purpose
         CurrentVals.remove(0);
         int numOfLifeOfMonster = CurrentVals.remove(0);
         mscState = MonsterCardState.COMBAT;
         System.out.println(CurrentVals);       // <- print for testing purpose
      }else if(CurrentVals.get(0) == 2){ //Flee
         System.out.println("FLEE");            // <- print for testing purpose
         System.out.println(CurrentVals);       // <- print for testing purpose
         CurrentVals.remove(0);
         active = false;
         Display.showTextPopup("The monster fleed");
         System.out.println(CurrentVals);       // <- print for testing purpose
      }else if(CurrentVals.get(0) == 3){ //Escape
         System.out.println("ESCAPE");          // <- print for testing purpose
         System.out.println(CurrentVals);       // <- print for testing purpose
         CurrentVals.remove(0);
         Display.showTextPopup("You escaped");
         HVMPanel.players.get(HVMPanel.currentPlayer).returnToLastPos();
         active = false;
         System.out.println(CurrentVals);       // <- print for testing purpose
      }else if(CurrentVals.get(0) == 4){ //Slash recursive
         System.out.println("SLASH");           // <- print for testing purpose
         System.out.println(CurrentVals);       // <- print for testing purpose
         CurrentVals.remove(0);
         int lifeLost = CurrentVals.remove(0);
         HVMPanel.players.get(HVMPanel.currentPlayer).loseLife(lifeLost);
         Display.showTextPopup("You got slash\nand loss "+lifeLost+" lives");
         System.out.println(CurrentVals);       // <- print for testing purpose
         if(CurrentVals.size() > 0)
            whatsNext(CurrentVals);
      }
   }
   
   public void combat(String playerTatic){
      String monsterTactic = getMonsterTactic();
      combatMessage = "The  monster selected:\n"+monsterTactic+"\n\n";
      int ml = 0, pl = 0;
      if(playerTatic == "MightyBlow"){
         if(monsterTactic == "MightyBlow")    { ml = 1; pl = 1; }
         else if(monsterTactic == "Slash")    { ml = 2; pl = 0; }
         else if(monsterTactic == "LeapAside"){ ml = 0; pl = 1; }
      }else if(playerTatic == "Slash"){
         if(monsterTactic == "MightyBlow")    { ml = 0; pl = 1; }
         else if(monsterTactic == "Slash")    { ml = 1; pl = 1; }
         else if(monsterTactic == "LeapAside"){ ml = 1; pl = 0; }
      }else if(playerTatic == "LeapAside"){
         if(monsterTactic == "MightyBlow")    { ml = 1; pl = 0; }
         else if(monsterTactic == "Slash")    { ml = 0; pl = 1; }
         else if(monsterTactic == "LeapAside"){ ml = 1; pl = 1; }
      }
      monsterLifes -= ml;
      HVMPanel.players.get(HVMPanel.currentPlayer).loseLife(pl);
      combatMessage += "The monster lost: "+ml+"\n";
      combatMessage += "You lost: "+pl+"\n";
      
      //check if someone died
      if(HVMPanel.players.get(HVMPanel.currentPlayer).life() <= 0 || monsterLifes <= 0)
         active = false;
   }
   
   //AI just a random
   public String getMonsterTactic(){
      Random random = new Random();
      int randNum = random.nextInt(3) + 1;
      if(randNum == 1)
         return "LeapAside";
      else if(randNum == 2)
         return "MightyBlow";
      else if(randNum == 3)
         return "Slash";
      return "";
   }
   
   public int getMonsterLives(){
      return monsterLifes;
   }
   public boolean isInCombat(){
      return mscState == MonsterCardState.COMBAT;
   }
   public String getCombatMessage(){
     return combatMessage;
   }
}