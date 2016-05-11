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
   
   
   
   //All the images variable  
    
   public HVMPanel(){
      
      Display.loadImages();
      
   }
       
   public void play(){
      resetGame();
      
      for(int i=0; i<timeOfDay; i++)
         for(int j=0; j<players.size(); j++)
            players.get(j).playTurn();
   }
   
   public void resetGame(){
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
         //if click start game
         if(e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1 ){
            gameState = GameState.GAME;
            play();
         }
         repaint();
      }else if(gameState == GameState.GAME){
         if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gameState = GameState.PAUSE;
         repaint();
      }else if(gameState == GameState.PAUSE){ 
         if(e.getKeyCode() == KeyEvent.VK_Q){
            System.exit(0);
         }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gameState = GameState.GAME;
         }
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