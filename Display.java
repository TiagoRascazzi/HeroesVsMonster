import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

public class Display extends HVMPanel{
   
   private static ImageIcon startImg; 
   private static ImageIcon emptyCellImg;  
   private static ImageIcon cornerCellImg1;    
   private static ImageIcon cornerCellImg2;    
   private static ImageIcon cornerCellImg3;    
   private static ImageIcon cornerCellImg4;   
   private static ImageIcon borderCellImg1;   
   private static ImageIcon borderCellImg2;  
   private static ImageIcon sunImg;  
   private static ImageIcon combatImg;
   
   public static void loadImages(){
      startImg = new ImageIcon("Img/start.jpg");
      emptyCellImg = new ImageIcon("Img/Tile/EmptyCell.png");
      cornerCellImg1 = new ImageIcon("Img/Tile/Corner_1.png");
      cornerCellImg2 = new ImageIcon("Img/Tile/Corner_2.png");
      cornerCellImg3 = new ImageIcon("Img/Tile/Corner_3.png");
      cornerCellImg4 = new ImageIcon("Img/Tile/Corner_4.png");
      borderCellImg1 = new ImageIcon("Img/Tile/Border_1.png");
      borderCellImg2 = new ImageIcon("Img/Tile/Border_2.png");
      sunImg = new ImageIcon("Img/sun.png");
      combatImg = new ImageIcon("Img/combat.png");
   }
   
   public static void drawGame(Graphics g, int screenWidth, int screenHeight){
      if(gameState == GameState.START){
         drawStart(g, screenWidth, screenHeight);
      }else if(gameState == GameState.MAIN){
         mainmenu.draw(g, screenWidth, screenHeight);
      }else if(gameState == GameState.GAME){
         g.setColor(new Color(169,69,46));
         g.fillRect(0, 0, screenWidth, screenHeight); 
         drawBoard(g, screenWidth, screenHeight);
         drawSidebar(g, screenWidth, screenHeight);
      }else if(gameState == GameState.PAUSE){
         drawBoard(g, screenWidth, screenHeight);
         drawSidebar(g, screenWidth, screenHeight);
         drawPauseMenu(g, screenWidth, screenHeight);
      }
   }
   
   public static void drawStart(Graphics g, int screenWidth, int screenHeight){
      g.drawImage(startImg.getImage(),0, 0, screenWidth, screenHeight, null);
   }
   public static void drawBoard(Graphics g, int screenWidth, int screenHeight){  
      
      int boardSize = screenHeight; 
      
      double x = boardSize / 22;
      int cornerTileSize = (int)(3*x);
      int tileSize = (int)(2*x);
      int borderSize = (int)x;
      int borderHeigth = (int)x*5/6;
      int borderSpace = (int)x-borderHeigth;
      int playerSize = (int)(x*1.5);
      int playerSpace = (tileSize-playerSize)/2;
            
      for(int i=0; i<board.numRows(); i++){
         for(int j=0; j<board.numColumns(); j++){
			//Draw Tiles
            if(i==0 && j==0) //draw left top corner
               g.drawImage(cornerCellImg1.getImage(), 0, 0, cornerTileSize, cornerTileSize, null);
            else if(i==0 && j==board.numColumns()-1) //draw rigth top corner
               g.drawImage(cornerCellImg2.getImage(), tileSize*board.numColumns()-1-borderSize, 0, cornerTileSize, cornerTileSize, null);
            else if(i==board.numRows()-1 && j==board.numColumns()-1) //draw rigth bottom corner
               g.drawImage(cornerCellImg3.getImage(), tileSize*board.numColumns()-1-borderSize, tileSize*board.numRows()-1-borderSize, cornerTileSize, cornerTileSize, null);
            else if(i==board.numRows()-1 && j==0) //draw left bottom corner
               g.drawImage(cornerCellImg4.getImage(), 0, tileSize*board.numRows()-1-borderSize, cornerTileSize, cornerTileSize, null);
            else if(board.get(i, j) != null) //not empty cell
               g.drawImage(board.get(i, j).getImage(), tileSize*i+borderSize, tileSize*j+borderSize, tileSize, tileSize, null);
            else //empty cell
               g.drawImage(emptyCellImg.getImage(), tileSize*i+borderSize, tileSize*j+borderSize, tileSize, tileSize, null);
            
         }
      }
      
	   //Draw borders
      for(int i=1; i<board.numRows()-1; i++){
         g.drawImage(borderCellImg1.getImage(), tileSize*i+borderSize, borderSpace, tileSize, borderHeigth, null);
         g.drawImage(borderCellImg1.getImage(), tileSize*i+borderSize, borderSize+tileSize*board.numRows()-1, tileSize, borderHeigth, null);
      }
      for(int i=1; i<board.numColumns()-1; i++){
         g.drawImage(borderCellImg2.getImage(), borderSpace, tileSize*i+borderSize, borderHeigth, tileSize, null);
         g.drawImage(borderCellImg2.getImage(), tileSize*board.numColumns()-1+borderSize, tileSize*i+borderSize, borderHeigth, tileSize, null);
      }
      
      //Draw player
      for(int i=0; i<players.size(); i++)
         g.drawImage(players.get(i).getImage(), playerSpace+tileSize*(players.get(i).getPosX())+borderSize, playerSpace+tileSize*(players.get(i).getPosY())+borderSize, playerSize, playerSize, null);
     
   }
   public static void drawSidebar(Graphics g, int screenWidth, int screenHeight){    
      
      int boardSize = screenHeight; 
      g.setColor(Color.BLACK);
      
	  //draw sun track
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      g.drawString("Sun track", boardSize+26, 45); 
	   for(int i=0; i<timeOfDay; i++)
         g.drawImage(sunImg.getImage(), boardSize+8+(i%8)*20, 50+20*(i/8), 20, 20, null);
         
      //draw player action
      players.get(currentPlayer).drawAction(g, screenWidth, screenHeight);
      
     
	   //draw player spec (life, etc...)
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
	   for(int i=0; i<players.size(); i++)
         g.drawString((i+1)+". "+players.get(i).getName() + " has "+ players.get(i).life() +" lives", boardSize, 325+i*25);
	  
	   //draw combat board TODO make zoom to full screen, make a special class for it
	   g.drawImage(combatImg.getImage(), boardSize, 425, 200-10, 200-10, null);
	  
   }
   
   
   public static void drawPauseMenu(Graphics g, int screenWidth, int screenHeight){
      g.setColor(new Color(125, 125, 125, 225));
      g.fillRect(0, 0, screenWidth, screenHeight);  
      
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 48)); 
      g.drawString("PAUSE", 50, 75);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      g.drawString("(esc) RESUME", 100, 150);
      g.drawString("(q) QUIT", 100, 200);
   }
}