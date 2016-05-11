import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;

import java.util.Scanner;

public class HVMPanel extends JPanel{
   
   private int DAY = 26;
   
   public ArrayList<Player> players = new ArrayList<Player>();
   public Board board;
   public int numOfPlayers;
   private int timeOfDay = 26;
   
   private Scanner input = new Scanner(System.in);
   //private boolean playing = false;
   
   
   //All the images variable  
    
   public HVMPanel(){
      
      //Load all the images
      
      setUp();
      showMenu();
   }
      
   public void showMenu(){
      //option including start
      
      //TODO in graphics
      System.out.println("Start new game (N)\nQuit(Q)");
      String in = input.next();
      if(in.toLowerCase().equals("n")){
         play();
      }else if(in.toLowerCase().equals("q")){
         System.exit(0);
      }
      
   }
   

   
    
   public void play(){
      setUp();
      
      for(int i=0; i<timeOfDay; i++)
         for(int j=0; j<players.size(); j++)
            players.get(j).playTurn();
   }
   
   public void setUp(){
      board = new Board(10, 10);
      timeOfDay = DAY;
      
      ArrayList<Player> availablePlayers = new ArrayList<Player>();
      availablePlayers.add(new SirRohan());
      availablePlayers.add(new ElAdoranSureshot());
      availablePlayers.add(new UlvGrimhand());
      availablePlayers.add(new VolrikTheBrave());
            
      numOfPlayers = 4;  //Enter number of player
      //TODO select player
      for(int i=0; i<4; i++)
         players.add(availablePlayers.remove(0));
            
   }
   
   public void processUserInput(KeyEvent e){
      System.out.println(e.getKeyChar());
      if(e.getKeyCode() == 81)
         System.exit(0);
      
   }
   
   
}