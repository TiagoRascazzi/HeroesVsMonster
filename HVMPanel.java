import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Dimension;

import java.util.Scanner;

public class HVMPanel extends JPanel{
   
   protected static MainMenu mainmenu = new MainMenu("Img/main.jpg");
   public enum GameState {START, MAIN, SELECT,  GAME, PAUSE}; 
   private int DAY = 26; 
   
   public static ArrayList<Player> players = new ArrayList<Player>();
   public static int currentPlayer = 0;
   public static GameState gameState = GameState.START;
   public static SparseMatrix<Tile> board;
   public static int timeOfDay = 26;
   
   
   //All the images variable  
   public HVMPanel(){
      Display.loadImages();
   }
   
   public void resetGame(){
      board = new SparseMatrix(10, 10); //4 rows, 5 cols
      timeOfDay = DAY;
      
      //set players start position
      for(int i=0; i<players.size(); i++){
         if(i==0)
            players.get(i).setPos(0, 0);
         if(i==1)
            players.get(i).setPos(board.numColumns()-1, 0);
         if(i==2)
            players.get(i).setPos(board.numColumns()-1, board.numRows()-1);
         if(i==3)
            players.get(i).setPos(0, board.numRows()-1);
       }   
   }
   
   public void processUserInput(KeyEvent e){
      
      if(gameState == GameState.START){
         BGMusic.setBGMusic("Music/game/track1.mp3");
         gameState = GameState.MAIN;
         repaint();
      }else if(gameState == GameState.MAIN){
         players = mainmenu.processUserInput(e);
         if(players != null){
            System.out.println(players);
            gameState = GameState.GAME;
            resetGame();
         }
         repaint();
      }else if(gameState == GameState.GAME){
         if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gameState = GameState.PAUSE;
         }else{
            if(players.get(currentPlayer).processUserInput(e)){
               //TODO if return true go to next player, remove one sun after all played
               currentPlayer++;
               if(currentPlayer>=players.size()){
                  currentPlayer = 0;
                  timeOfDay--;
               }
            }
         } 
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
      Display.drawGame((Graphics2D)g, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
      
   }
   
   
}