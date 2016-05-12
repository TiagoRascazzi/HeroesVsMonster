import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

public class Display extends HVMPanel{
   
   private static ImageIcon startImg = new ImageIcon("Img/start.jpg"); 
   private static ImageIcon emptyCellImg = new ImageIcon("Img/Tile/EmptyCell.png");  
   private static ImageIcon cornerCellImg1 = new ImageIcon("Img/Tile/Corner_1.png");    
   private static ImageIcon cornerCellImg2 = new ImageIcon("Img/Tile/Corner_2.png");    
   private static ImageIcon cornerCellImg3 = new ImageIcon("Img/Tile/Corner_3.png");    
   private static ImageIcon cornerCellImg4 = new ImageIcon("Img/Tile/Corner_4.png");   
   private static ImageIcon borderCellImg1 = new ImageIcon("Img/Tile/Border_1.png");   
   private static ImageIcon borderCellImg2 = new ImageIcon("Img/Tile/Border_2.png");  
   private static ImageIcon sunImg = new ImageIcon("Img/sun.png");  
   private static ImageIcon combatImg = new ImageIcon("Img/combat.png");
   private static Dimension screenSize = new Dimension(800, 600);
   private static int boardSize = 600; 
   
   public static void loadImages(){
      
   }
   
   public static void drawGame(Graphics g){
      if(gameState == GameState.START){
         drawStart(g);
      }else if(gameState == GameState.MAIN){
         mainmenu.draw(g);
         //drawMainMenu(g);
      }else if(gameState == GameState.GAME){
         drawBoard(g);
         drawSidebar(g);
      }else if(gameState == GameState.PAUSE){
         drawBoard(g);
         drawSidebar(g);
         drawPauseMenu(g);
      }
   }
   
   public static void drawStart(Graphics g){
      g.drawImage(startImg.getImage(),0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight(), null);
   }
   public static void drawBoard(Graphics g){      
      //math for size in proportion
	   double x = boardSize / 21.8;
      int cornerTileSize = (int)(3*x);
      int tileSize = (int)(2*x);
      int borderSize = (int)x;
            
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
         g.drawImage(borderCellImg1.getImage(), tileSize*i+borderSize, 0, tileSize, borderSize, null);
         g.drawImage(borderCellImg1.getImage(), tileSize*i+borderSize, borderSize+tileSize*board.numRows()-1, tileSize, borderSize, null);
      }
      for(int i=1; i<board.numColumns()-1; i++){
         g.drawImage(borderCellImg2.getImage(), 0, tileSize*i+borderSize, borderSize, tileSize, null);
         g.drawImage(borderCellImg2.getImage(), tileSize*board.numColumns()-1+borderSize, tileSize*i+borderSize, borderSize, tileSize, null);
      }
      
      //Draw player
      for(int i=0; i<players.size(); i++){
         g.drawImage(players.get(i).getImage(), tileSize*(players.get(i).getPosX())+borderSize, tileSize*(players.get(i).getPosY())+borderSize, 45, 45, null);
      }
      
   }
   public static void drawSidebar(Graphics g){
      g.setColor(new Color(169,69,46));
      g.fillRect(boardSize, 0, (int)screenSize.getWidth()-boardSize, (int)screenSize.getHeight()); 
       
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      g.drawString("INFO", 625, 50);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("Sun track:", 625, 75);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(esc) PAUSE", 625, 175);
	  
	  //draw sun track
	  for(int i=0; i<timeOfDay; i++)
         g.drawImage(sunImg.getImage(), boardSize+25+(i%8)*16, 75+16*(i/8), 16, 16, null);
	  
	  //draw combat board TODO make zoom to full screen, make a special class for it
	   g.drawImage(combatImg.getImage(), boardSize, 200, (int)screenSize.getWidth()-boardSize, (int)screenSize.getWidth()-boardSize, null);
	  
	  //draw player spec (life, etc...)
	  for(int i=0; i<players.size(); i++)
         g.drawString("("+(i+1)+") "+players.get(i).getName() + " has "+ players.get(i).life() +" lives", 625, 500+i*25);
   }
   public static void drawPauseMenu(Graphics g){
      g.setColor(new Color(125, 125, 125, 225));
      g.fillRect(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());  
      
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      g.drawString("PAUSE", 50, 50);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(esc) RESUME", 75, 100);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(q) QUIT", 75, 125);
   }   
   public static void drawSelect(Graphics g){
   
   }
}