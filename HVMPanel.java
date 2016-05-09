import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;

public class HVMPanel extends JPanel{
   
   public ArrayList<Player> players = new ArrayList<Player>();
   public Board board;
   public int numOfPlayers;
   private int timeOfDay = 26;
   
   
   //All the images variable  
    
   public HVMPanel(){
      
      //Load all the images
      
      init();
      showMenu();
   }
   
   public void init(){
      board = new Board(10, 10);
      
      players = new ArrayList<Player>();
      numOfPlayers = 4;  //Enter number of player
      if(numOfPlayers>=1)
         players.add(new SirRohan());
      if(numOfPlayers>=2)
         players.add(new ElAdoranSureshot());
      if(numOfPlayers>=3)
         players.add(new UlvGrimhand());
      if(numOfPlayers>=4)
         players.add(new VolrikTheBrave());
   }
   
   public void showMenu(){
      
      //option including start
      
   }
   
   public void processUserInput(KeyEvent e){
      System.out.println(e.getKeyChar());
   }
   
   public void play(){
      boolean playing = false;
      while(playing){
         
         
         drawToScreen();
      }
      
      
   }
   
   public void drawToScreen(){
   
   }
   
}