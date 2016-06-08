import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.util.Random;

public class TorchGoesOut extends RoomCard{
   
   private int[] selectedNum = new int[3];
   private int selectedNumSel = -1;
   
   public TorchGoesOut(){
      super(11);
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
   public boolean actionKeyInput(KeyEvent e, int count){
      
      if(selectedNumSel == 1 || selectedNumSel == 2 || selectedNumSel == 3){
         //selectedNum[selectedNumSel-1] = ; //TODO get wich one is click and put it in array
         selectedNumSel++;
      }else{
         if(e.getKeyChar() == (count+"").charAt(0)){ //if you try
            System.out.println("Try to ligth your torch");
            selectedNumSel = 1;         
         }
      }
      if(selectedNumSel == 4){
         Random random = new Random();
         int dice = random.nextInt(6) + 1;
         selectedNum[0] = 1;
         for(int i=0; i<selectedNum.length; i++){
            if(selectedNum[i] == dice){  
               getRidOfCard = true;
            }
         }
         return true;
      }
      return false;
   }
   public boolean actionMouseInput(Point screenSize, Point actionPos, MouseEvent e, int count){
      
      if(selectedNumSel == 1 || selectedNumSel == 2 || selectedNumSel == 3){
         //selectedNum[selectedNumSel-1] = ; //TODO get wich one is click and put it in array
         selectedNumSel++;
      }else{
         if(GUI.hover(actionPos.x, actionPos.x+140, actionPos.y+40+(25*count), actionPos.y+40+25+(25*count))){  //if you try
            System.out.println("Try to ligth your torch");
            selectedNumSel = 1;
         }
      }
      if(selectedNumSel == 4){
         Random random = new Random();
         int dice = random.nextInt(6) + 1;
         selectedNum[0] = 1;
         for(int i=0; i<selectedNum.length; i++){
            if(selectedNum[i] == dice){  
               getRidOfCard = true;
            }
         }
         return true;
      }
      return false;
   }
   
   public void tryToLigth(){
      Random random = new Random();
      int dice = random.nextInt(12) + 1;
      damage -= dice;
      getRidOfCard = true;
   }
   
   public String[] getPrintableAction(){
      if(selectedNumSel == 1 || selectedNumSel == 2 || selectedNumSel == 3){
        //TODO REMOVE ONE ALREADY USED
       /* int count = 0;  
        for(int i=0; i<6; i++)
           for(int j=0; j<selectedNum.length; j++)
              if(i == selectedNum[j])
                 count++;
         String[] actions = new String[6-count];
         count = 0;
         for(int i=0; i<6; i++){
            boolean ok = false;
            for(int j=0; j<selectedNum.length; j++)
               if(i != selectedNum[j])
                  ok = true;
            
            if(ok){
               actions[i-count] = ""+i;
               count++;
            }
         }*/
         String[] actions = {"1", "2", "3", "4", "5", "6"};
         return actions;
      }else{
         String[] actions = {"Try to ligth your torch"};
         return actions;
      }
   }
   
   public boolean disableDefaultAction(){
      return true;
   }
}