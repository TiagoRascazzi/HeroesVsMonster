import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

import java.util.Scanner;

public class HVMPanel extends JPanel{
   
   public enum GameState {START, MAIN, GAME, PAUSE};
   private int DAY = 26;
   
   public ArrayList<Player> players = new ArrayList<Player>();
   public Board board;
   public int numOfPlayers;
   private int timeOfDay = 26;
   public static GameState gameState = GameState.START;
   
   private Scanner input = new Scanner(System.in);
   //private boolean playing = false;
   
   
   //All the images variable  
    
   public HVMPanel(){
      
      
      setUp();
      showMenu();
   }
      
   public void showMenu(){
      Display.loadImages();
      
      //TODO in graphics
      /*System.out.println("Start new game (N)\nQuit(Q)");
      String in = input.next();
      if(in.toLowerCase().equals("n")){
         play();
      }else if(in.toLowerCase().equals("q")){
         System.exit(0);
      }*/
      
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
      
      if(gameState == GameState.START){
         gameState = GameState.MAIN;
         repaint();
      }else if(gameState == GameState.MAIN){
         gameState = GameState.GAME;
         repaint();
      }else if(gameState == GameState.GAME){
         gameState = GameState.PAUSE;
         repaint();
      }else if(gameState == GameState.PAUSE){
         gameState = GameState.GAME;
         repaint();
      }
      
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Display.drawGame(g);
      System.out.println("draw");
      
   }
   
   
}