import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Color;

public class HVMPanel extends JPanel{
   
   public enum GameState {START, MAIN, GAME, PAUSE, END};  // enum of all the state that the game has
   private static BGMusicPlayer bgMusicPlayer;        // used to play the music in the backgroung
    
   public static Point mouse;                         // Save the mouse position
   protected static MainMenu mainmenu;                // object to display main menu
   public static ArrayList<Player> players;           // array that contain all the player in the game
   public static int currentPlayer;                   // contain the index of the player that is currently playing his turn
   public static GameState gameState;                 // is the state of the game whether it is on the start screen, the menu, playing the game or thegame is paused  
   public static SparseMatrix<Tile> board;            // represents what tile are in the board of the game 
   public static int timeOfDay;                       // represent what
   
   public HVMPanel(){
      bgMusicPlayer = new BGMusicPlayer();
      //bgMusicPlayer.playMusic(0);
      mouse = new Point(0, 0);
      mainmenu = new MainMenu("Img/main.jpg");
      players = new ArrayList<Player>();
      currentPlayer = 0;
      gameState = GameState.START;
      Display.loadImages();
   }
   
   //reset the game to default
   public void resetGame(){
      mainmenu = new MainMenu("Img/main.jpg");
      board = new SparseMatrix(13, 10);
      timeOfDay = 26;
      
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
      //add corner tile and treasure chambers
      board.add(0, 0, new CornerTile(CornerTile.TOP_LEFT));
      board.add(board.numRows()-1, board.numColumns()-1, new CornerTile(CornerTile.BOTTOM_RIGHT));
      board.add(board.numRows()-1, 0, new CornerTile(CornerTile.BOTTOM_LEFT));
      board.add(0, board.numColumns()-1, new CornerTile(CornerTile.TOP_RIGHT));
      board.add(board.numRows()/2, board.numColumns()/2-1, new TreasureChamberTile(Tile.LEFT));
      board.add(board.numRows()/2, board.numColumns()/2, new TreasureChamberTile(Tile.RIGHT));      
   }
   
   //process the key pressed from by keyboard
   public void processKeyInput(KeyEvent e){
      if(gameState == GameState.START){
         //if any key is pressed in the start screen it goes to the main menu
         gameState = GameState.MAIN; 
      }else if(gameState == GameState.MAIN){
         //send the key processing to the mainmenu object expecting it to return an array of player
         players = mainmenu.processKeyInput(e);
         //if it received an array of player then start the game
         if(players != null) 
            startGame();
      }else if(gameState == GameState.GAME){
         if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gameState = GameState.PAUSE;  //pause the game when escape is pressed
         }else{
            //send the key processing to the current player if it return true go to next player 
            if(players.get(currentPlayer).processKeyInput(e))
               nextPlayer();
         } 
      }else if(gameState == GameState.PAUSE){ 
         if(e.getKeyCode() == KeyEvent.VK_Q){
            System.exit(0);               // close the game application
         }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gameState = GameState.GAME;   // return back to game
         }else if(e.getKeyCode() == KeyEvent.VK_R){
            gameState = GameState.MAIN;   // restart the game
         }
      }
      repaint();
   }
   
   //process the mouse button and mouse movement
   public void processMouseInput(MouseEvent e){
      mouse = e.getPoint();
      if(e.getButton() == MouseEvent.BUTTON1){   
         if(gameState == GameState.START){
            //if left mouse button is clicked in the start screen it goes to the main menu
            gameState = GameState.MAIN;  
         }else if(gameState == GameState.MAIN){
            //send the mouse processing to the mainmenu object expecting it to return an array of player
            players = mainmenu.processMouseInput(e);
            //if it received an array of player then start the game
            if(players != null)  
               startGame();
         }else if(gameState == GameState.GAME){
            //send the mouse processing to the current player if it return true go to next player 
            if(players.get(currentPlayer).processMouseInput(new Point((int)this.getSize().getWidth(), (int)this.getSize().getHeight()), e))
               nextPlayer();
         }else if(gameState == GameState.PAUSE){ 
            if(hover(100, 310, 155, 185))
               gameState = GameState.GAME;   // return back to game
            else if(hover(100, 230, 205, 235))
               gameState = GameState.MAIN;   // restart the game
            else if(hover(100, 230, 255, 285))
               System.exit(0);               // close the game application
         }
      }
      repaint();
   }
   
   //goes to next player if its the last one go back to first and remove one sun
   public void nextPlayer(){
      int lastPlayer = currentPlayer;
      currentPlayer++;
      if(currentPlayer>=players.size()){
         currentPlayer = 0;
         timeOfDay--;
      }
      
      boolean atLeastOneAlive = false;
      for(int i=0; i<players.size(); i++)
         if(players.get(i).isAlive())
            atLeastOneAlive = true;
      if(atLeastOneAlive){
         while(!players.get(currentPlayer).isAlive()){
            currentPlayer++;
            if(currentPlayer>=players.size()){
               currentPlayer = 0;
               timeOfDay--;
            }
         }
      }else{
         //show end screen
         gameState = GameState.END;
         System.out.println("THE GAME HAS ENDED");
      }
   }
   
   //play a random music change to game state and reset the game
   public void startGame(){
      //bgMusicPlayer.playRandomMusic();
      gameState = GameState.GAME;
      resetGame();
   }
   
   //this is calling the static object that draw the game to the screen
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Display.drawGame((Graphics2D)g, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
   }
   
   //methods used to do the hovering effect later they migth go in a GUI class
   public static boolean hover(int x1, int x2, int y1, int y2){
      return (mouse.x>x1 && mouse.x<x2 && mouse.y>y1 && mouse.y<y2);
   }
   public static void chgColorOnHover(Graphics2D g, Color c1, Color c2, int x1, int x2, int y1, int y2){
      g.setColor(c1);
      if(hover(x1, x2, y1, y2))
         g.setColor(c2);
   }
}