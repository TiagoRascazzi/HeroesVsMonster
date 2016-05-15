import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.Point;

public class Display extends HVMPanel{
   
   private static Dimension boardSize = new Dimension(0, 0);
   private static ImageIcon[] tileTextures;
   private static ImageIcon[] cardTextures;
   private static ImageIcon startImg;  
   private static ImageIcon sunImg;  
   
   public static void loadImages(){
      startImg = new ImageIcon("Img/start.jpg");
      sunImg = new ImageIcon("Img/sun.png");      
      tileTextures = new ImageIcon[9];
      tileTextures[0] = new ImageIcon("Img/Tile/TestTileImg.png");
      tileTextures[1] = new ImageIcon("Img/Tile/Corner.png");
      tileTextures[2] = new ImageIcon("Img/Tile/Border.png");
      tileTextures[3] = new ImageIcon("Img/Tile/EmptyCell.png");
      tileTextures[4] = new ImageIcon("Img/Tile/CurrentPlayer.png");
      
      tileTextures[5] = new ImageIcon("Img/Tile/GreenBorder.png");
      tileTextures[6] = new ImageIcon("Img/Tile/TreasureChamber.png");
      tileTextures[7] = new ImageIcon("Img/Tile/4xCorridors.png");
      tileTextures[8] = new ImageIcon("Img/Tile/RotatingRoom.png");
      
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
      double x = Math.min(screenWidth, screenHeight) / ((2.0*board.numRows()+2.0) );
      
      double tileSize = 2.0*x;
      double cornerTileSize = 3.0*x;
      
      double playerSize = x*1.5;
      double playerSpace = (tileSize-playerSize)/2.0;
      
      double borderSize = x;
      double borderHeigth = x*5.0/6.0;
      double borderSpace = x-borderHeigth;
      
      double mostBottom = (tileSize*board.numRows()-1.0-borderSize)+1.0;
      double mostRight = (tileSize*board.numColumns()-1.0-borderSize)+1.0;
      boardSize.setSize(mostRight+cornerTileSize, mostBottom+cornerTileSize);
      
      double tileScale = tileSize/60.0;
      double playerScale = playerSize/45.0;
      double borderScale = borderHeigth/20.0;
      double cornerTileScale = cornerTileSize/90.0;
      AffineTransform transform = new AffineTransform(1, 0, 0, 1, 0, 0);

      //Draw Tiles
      for(int i=0; i<board.numRows(); i++){
         for(int j=0; j<board.numColumns(); j++){            
            if(board.get(i, j) != null){ 
               //Draw Tiles
               if(board.get(i, j) instanceof CornerTile){ //if corner
                  double cornerPosX = j*tileSize+(j==board.numColumns()-1? borderSize:0);
                  double cornerPosY = i*tileSize+(i==board.numRows()-1? borderSize:0);
                  transform.setToRotation(board.get(i, j).getRotation(), cornerPosX+(cornerTileSize/2), cornerPosY+(cornerTileSize/2));
                  transform.translate(cornerPosX, cornerPosY);
               }else{
                  transform.setToRotation(board.get(i, j).getRotation(), j*tileSize+borderSize+(tileSize/2), i*tileSize+borderSize+(tileSize/2));
                  transform.translate(j*tileSize+borderSize, i*tileSize+borderSize);
               }
               transform.scale(tileScale, tileScale);
               g.drawImage(tileTextures[board.get(i, j).getTextureID()].getImage(), transform, null); 
            }else{ 
               //empty cell
               transform.setToTranslation(j*tileSize+borderSize, i*tileSize+borderSize);
               transform.scale(tileScale, tileScale);
               g.drawImage(tileTextures[3].getImage(), transform, null); 
            }
            if(players.get(currentPlayer).isMoving() && players.get(currentPlayer).isValidMove(new Point(j, i))){
               //green border
               transform.setToTranslation(j*tileSize+borderSize, i*tileSize+borderSize);
               transform.scale(tileScale, tileScale);
               g.drawImage(tileTextures[5].getImage(), transform, null); 
            }
         }
      }
      
      //Draw borders
      for(int i=1; i<board.numColumns()-1; i++){
         transform.setToTranslation(tileSize*i+borderSize, borderSpace);
         transform.scale(tileScale, borderScale);
         g.drawImage(tileTextures[2].getImage(), transform, null);
          
         transform.setToTranslation(tileSize*i+borderSize, 1+borderSize+tileSize*board.numRows()-1);
         transform.scale(tileScale, borderScale);
         g.drawImage(tileTextures[2].getImage(), transform, null);
      }
      for(int i=1; i<board.numRows()-1; i++){
         transform.setToRotation(Math.toRadians(90), borderSpace+(borderHeigth/2), tileSize*i+borderSize+(borderHeigth/2));
         transform.translate(borderSpace, tileSize*i+borderSize);
         transform.scale(tileScale, borderScale);
         g.drawImage(tileTextures[2].getImage(), transform, null);
          
         transform.setToRotation(Math.toRadians(90), tileSize*board.numColumns()-1+borderSize+(borderHeigth/2), tileSize*i+borderSize+(borderHeigth/2));
         transform.translate(tileSize*board.numColumns()-1+borderSize, tileSize*i+borderSize);
         transform.scale(tileScale, borderScale);
         g.drawImage(tileTextures[2].getImage(), transform, null);
      }
      
      //Draw player
      for(int i=0; i<players.size(); i++){
         transform.setToTranslation((int)(playerSpace+tileSize*(players.get(i).getPosX())+borderSize), (int)(playerSpace+tileSize*(players.get(i).getPosY())+borderSize));
         transform.scale(playerScale, playerScale);
         g.drawImage(players.get(i).getImage(), transform, null);
      }
      //Draw current player bar
      transform.setToTranslation((int)(playerSpace+tileSize*(players.get(currentPlayer).getPosX())+borderSize), (int)(tileSize-playerSpace+tileSize*(players.get(currentPlayer).getPosY())+borderSize));
      transform.scale(playerScale, playerScale);
      g.drawImage(tileTextures[4].getImage(), transform, null);
   }
   
   public static void drawSidebar(Graphics2D g, int screenWidth, int screenHeight){    
      if(screenWidth>screenHeight){ //draw on side
         drawSunTrack(g, (int)boardSize.getWidth(), 0);
         players.get(currentPlayer).drawAction(g, (int)boardSize.getWidth()+8, 175);
         players.get(currentPlayer).drawLife(g, (int)boardSize.getWidth()+8, 325);
         players.get(currentPlayer).drawInfo(g, (int)boardSize.getWidth()+8, 375);
      }else{   //draw on bottom
         drawSunTrack(g, 0, (int)boardSize.getHeight());
         players.get(currentPlayer).drawAction(g, 175, (int)boardSize.getHeight()+28);
         players.get(currentPlayer).drawLife(g, 325, (int)boardSize.getHeight()+20);
         players.get(currentPlayer).drawInfo(g, 325, (int)boardSize.getHeight()+50);
      }
   }
   public static void drawSunTrack(Graphics2D g, int posX, int posY){
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      g.drawString("Sun track", posX+26, posY+20); 
	   for(int i=0; i<timeOfDay; i++)
         g.drawImage(sunImg.getImage(), posX+(i%8)*20, posY+25+20*(i/8), 20, 20, null);
   }
   
   public static void drawPauseMenu(Graphics2D g, int screenWidth, int screenHeight){
      g.setColor(new Color(125, 125, 125, 225));
      g.fillRect(0, 0, screenWidth, screenHeight);  
      
      g.setColor(Color.RED);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 48)); 
      g.drawString("PAUSE", 50, 75);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      chgColorOnHover(g, Color.RED, Color.BLACK, 100, 310, 155, 185);
      g.drawString("(esc) RESUME", 100, 150);
      chgColorOnHover(g, Color.RED, Color.BLACK, 100, 230, 205, 235);
      g.drawString("(r) RESTART", 100, 200);
      chgColorOnHover(g, Color.RED, Color.BLACK, 100, 230, 255, 285);
      g.drawString("(q) QUIT", 100, 250);
      
      /*
      double min = Math.min(screenWidth, screenHeight);
      System.out.println(250/min);
      
      g.setColor(Color.RED);
      g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(0.074*min))); 
      g.drawString("PAUSE", (int)(0.077*min), (int)(0.121*min));
      g.setFont(new Font("TimesRoman", Font.PLAIN, (int)(0.049*min))); 
      chgColorOnHover(g, Color.RED, Color.BLACK, (int)(0.154*min), (int)(0.477*min), (int)(0.238*min), (int)(0.285*min));
      g.drawString("(esc) RESUME", (int)(0.154*min), (int)(0.231*min));
      chgColorOnHover(g, Color.RED, Color.BLACK, (int)(0.154*min), (int)(0.477*min), (int)(0.315*min), (int)(0.362*min));
      g.drawString("(r) RESTART", (int)(0.154*min), (int)(0.308*min));
      chgColorOnHover(g, Color.RED, Color.BLACK, (int)(0.154*min), (int)(0.477*min), (int)(0.392*min), (int)(0.438*min));
      g.drawString("(q) QUIT", (int)(0.154*min), (int)(0.385*min));
      */
   }
}