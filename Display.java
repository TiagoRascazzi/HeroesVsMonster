import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;

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
   private static Dimension boardSize = new Dimension(0, 0);
   
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
   
   public static void drawGame(Graphics2D g, int screenWidth, int screenHeight){      
      if(gameState == GameState.START){
         g.drawImage(startImg.getImage(),0, 0, screenWidth, screenHeight, null);
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
   
   public static void drawBoard(Graphics2D g, int screenWidth, int screenHeight){        
      double x = Math.min(screenWidth, screenHeight) / ((2.0*board.numColumns()+2.0) );
      
      double tileSize = 2.0*x;
      double cornerTileSize = 3.0*x;
      
      double playerSize = x*1.5;
      double playerSpace = (tileSize-playerSize)/2.0;
      
      double borderSize = x;
      double borderHeigth = x*5.0/6.0;
      double borderSpace = x-borderHeigth;
      
      double mostRight = (tileSize*board.numRows()-1.0-borderSize)+1.0;
      double mostBottom = (tileSize*board.numColumns()-1.0-borderSize)+1.0;
      boardSize.setSize(mostRight+cornerTileSize, mostBottom+cornerTileSize);
      
      double tileScale = tileSize/60.0;
      double playerScale = playerSize/45.0;
      double borderScale = borderHeigth/20.0;
      double cornerTileScale = cornerTileSize/90.0;
      AffineTransform transform = new AffineTransform(1, 0.0, 0.0, 1, 0, 0);

      //Draw Tiles
      for(int i=0; i<board.numRows(); i++){
         for(int j=0; j<board.numColumns(); j++){
            if(i==0 && j==0){ 
               //draw left top corner
               transform.setToTranslation(0, 0);
               transform.scale(cornerTileScale, cornerTileScale);
               g.drawImage(cornerCellImg1.getImage(), transform, null); 
            }else if(i==0 && j==board.numColumns()-1){ 
               //draw rigth top corner
               transform.setToTranslation(mostRight, 0);
               transform.scale(cornerTileScale, cornerTileScale);
               g.drawImage(cornerCellImg2.getImage(), transform, null); 
            }else if(i==board.numRows()-1 && j==board.numColumns()-1){ 
               //draw rigth bottom corner
               transform.setToTranslation(mostRight, mostBottom);
               transform.scale(cornerTileScale, cornerTileScale);
               g.drawImage(cornerCellImg3.getImage(), transform, null); 
            }else if(i==board.numRows()-1 && j==0){ 
               //draw left bottom corner
               transform.setToTranslation(0, mostBottom);
               transform.scale(cornerTileScale, cornerTileScale);
               g.drawImage(cornerCellImg4.getImage(), transform, null); 
            }else if(board.get(i, j) != null){ 
               //not empty cell
               transform.setToTranslation(j*tileSize+borderSize, i*tileSize+borderSize);
               transform.scale(tileScale, tileScale);
               g.drawImage(board.get(i, j).getImage(), transform, null); 
            }else{ 
               //empty cell
               transform.setToTranslation(j*tileSize+borderSize, i*tileSize+borderSize);
               transform.scale(tileScale, tileScale);
               g.drawImage(emptyCellImg.getImage(), transform, null); 
            }
            
         }
      }
      
      //Draw borders
      for(int i=1; i<board.numRows()-1; i++){
         transform.setToTranslation(tileSize*i+borderSize, borderSpace);
         transform.scale(tileScale, borderScale);
         g.drawImage(borderCellImg1.getImage(), transform, null);
          
         transform.setToTranslation(tileSize*i+borderSize, 1+borderSize+tileSize*board.numColumns()-1);
         transform.scale(tileScale, borderScale);
         g.drawImage(borderCellImg1.getImage(), transform, null);
      }
      for(int i=1; i<board.numColumns()-1; i++){
         transform.setToTranslation(borderSpace, tileSize*i+borderSize);
         transform.scale(borderScale, tileScale);
         g.drawImage(borderCellImg2.getImage(), transform, null);
          
         transform.setToTranslation(tileSize*board.numRows()-1+borderSize, tileSize*i+borderSize);
         transform.scale(borderScale, tileScale);
         g.drawImage(borderCellImg2.getImage(), transform, null);
      }
      
      //Draw player
      for(int i=0; i<players.size(); i++){
         transform.setToTranslation((int)(playerSpace+tileSize*(players.get(i).getPosX())+borderSize), (int)(playerSpace+tileSize*(players.get(i).getPosY())+borderSize));
         transform.scale(playerScale, playerScale);
         g.drawImage(players.get(i).getImage(), transform, null);
      }
   }
   
   public static void drawSidebar(Graphics2D g, int screenWidth, int screenHeight){    
      g.setColor(Color.BLACK);      
      if(screenWidth>screenHeight){
         drawSunTrack(g, (int)boardSize.getWidth(), 0);
         players.get(currentPlayer).drawAction(g, (int)boardSize.getWidth()+8, 175);
         drawPlayerSpec(g, (int)boardSize.getWidth(), 325);
         g.drawImage(combatImg.getImage(), (int)boardSize.getWidth(), 425, 200-10, 200-10, null); //draw combat board TODO make zoom to full screen, make a special class for it
      }else{
         drawSunTrack(g, 0, (int)boardSize.getHeight());
         players.get(currentPlayer).drawAction(g, 175, (int)boardSize.getHeight()+28);
         drawPlayerSpec(g, 325, (int)boardSize.getHeight()+20);
         g.drawImage(combatImg.getImage(), 550, (int)boardSize.getHeight(), 200-10, 200-10, null);  //draw combat board TODO make zoom to full screen, make a special class for it
      }
   }
   public static void drawSunTrack(Graphics2D g, int posX, int posY){
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      g.drawString("Sun track", posX+26, posY+20); 
	   for(int i=0; i<timeOfDay; i++)
         g.drawImage(sunImg.getImage(), posX+(i%8)*20, posY+25+20*(i/8), 20, 20, null);
   }
   public static void drawPlayerSpec(Graphics2D g, int posX, int posY){
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
	   for(int i=0; i<players.size(); i++)
         g.drawString((i+1)+". "+players.get(i).getName() + " has "+ players.get(i).life() +" lives", posX, posY+i*25);
   }
   
   public static void drawPauseMenu(Graphics2D g, int screenWidth, int screenHeight){
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