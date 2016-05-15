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
   
   public enum GameState {START, MAIN, SELECT,  GAME, PAUSE}; 
   private final int DAY = 26; 
   
   public static Point mouse;
   protected static MainMenu mainmenu;
   public static ArrayList<Player> players;
   public static int currentPlayer;
   public static GameState gameState;
   public static SparseMatrix<Tile> board;
   public static int timeOfDay;
     
   public HVMPanel(){
      mouse = new Point(0, 0);
      mainmenu = new MainMenu("Img/main.jpg");
      players = new ArrayList<Player>();
      currentPlayer = 0;
      gameState = GameState.START;
      
      Display.loadImages();
   }
   
   public void resetGame(){
      mainmenu = new MainMenu("Img/main.jpg");
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
      //add corner tile
      board.add(0, 0, new CornerTile(CornerTile.TOP_LEFT));
      board.add(board.numRows()-1, board.numColumns()-1, new CornerTile(CornerTile.BOTTOM_RIGHT));
      board.add(board.numRows()-1, 0, new CornerTile(CornerTile.BOTTOM_LEFT));
      board.add(0, board.numColumns()-1, new CornerTile(CornerTile.TOP_RIGHT));
      board.add(board.numRows()/2, board.numColumns()/2-1, new TreasureChamberTile(Tile.LEFT));
      board.add(board.numRows()/2, board.numColumns()/2, new TreasureChamberTile(Tile.RIGHT));
      board.add(3, 5, new TestTile(Tile.TOP));
      
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
         }else if(e.getKeyCode() == KeyEvent.VK_R){
            gameState = GameState.MAIN;
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
            else if(hover(100, 230, 205, 235))
               gameState = GameState.MAIN;
            else if(hover(100, 230, 255, 285))
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