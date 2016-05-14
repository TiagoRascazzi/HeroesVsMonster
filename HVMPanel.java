import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Color;

import java.awt.Dimension;

import java.util.Scanner;

public class HVMPanel extends JPanel{
   
   public static Point mouse = new Point(0, 0);
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
      board = new SparseMatrix(13, 10);
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
   
   public void processKeyInput(KeyEvent e){
      if(gameState == GameState.START){
         BGMusic.setBGMusic("Music/game/track1.mp3");
         gameState = GameState.MAIN;
         repaint();
      }else if(gameState == GameState.MAIN){
         players = mainmenu.processKeyInput(e);
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
            if(players.get(currentPlayer).processKeyInput(e)){
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
   
   public void processMouseInput(MouseEvent e){
      //System.out.println("mouse event at ("+e.getX()+", "+e.getY()+")");
      mouse = e.getPoint();
         
      if(e.getButton() == MouseEvent.BUTTON1){   
         if(gameState == GameState.START){
            BGMusic.setBGMusic("Music/game/track1.mp3");
            gameState = GameState.MAIN;
         }else if(gameState == GameState.MAIN){
            players = mainmenu.processMouseInput(e);
            if(players != null){
               System.out.println(players);
               gameState = GameState.GAME;
               resetGame();
            }
         }else if(gameState == GameState.GAME){
            if(players.get(currentPlayer).processMouseInput(new Point((int)this.getSize().getWidth(), (int)this.getSize().getHeight()), e)){
               currentPlayer++;
               if(currentPlayer>=players.size()){
                  currentPlayer = 0;
                  timeOfDay--;
               }
            }
         }else if(gameState == GameState.PAUSE){ 
            if(hover(100, 310, 155, 185))
               gameState = GameState.GAME;
            if(hover(100, 230, 205, 240))
               System.exit(0);
         }
      }
      repaint();
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Display.drawGame((Graphics2D)g, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
      
   }
   
   public static boolean hover(int x1, int x2, int y1, int y2){
      return (mouse.x>x1 && mouse.x<x2 && mouse.y>y1 && mouse.y<y2);
   }
   public static void chgColorOnHover(Graphics2D g, Color c1, Color c2, int x1, int x2, int y1, int y2){
      g.setColor(c1);
      if(hover(x1, x2, y1, y2))
         g.setColor(c2);
   }
   
}