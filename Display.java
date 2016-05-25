import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.Point;

public class Display extends HVMPanel{
   
   private static Dimension boardSize = new Dimension(0, 0);   //save the number of row and col in the board 
   private static ImageIcon[] tileTextures;                    //array that contain all the texture for the tiles
   private static ImageIcon[] cardTextures;                    //array that contain all the texture for the cards
   private static ImageIcon startImg;                          //the picture for the start screen 
   private static ImageIcon sunImg;                            //the picture for the sun icon
   private static ImageIcon sidebarBG;                         //the picture background of the sidebar 
   
   public static void loadImages(){
      //load backgroung and icon image
      startImg = new ImageIcon("Img/start.jpg");
      sunImg = new ImageIcon("Img/sun.png"); 
      sidebarBG = new ImageIcon("Img/sidebarBG.jpg");
      
      //load all the image for cards
      cardTextures = new ImageIcon[9];
      cardTextures[0] = new ImageIcon("Img/Cards/RoomCard/EmptyRoom.png");
      //cardTextures[1] = new ImageIcon("Img/Cards/RoomCard/CaveIn.png");
      cardTextures[1] = new ImageIcon("Img/Cards/RoomCard/Jewellery.png");
      cardTextures[2] = new ImageIcon("Img/Cards/RoomCard/Bracelet.png"); 
      cardTextures[3] = new ImageIcon("Img/Cards/RoomCard/CrossfireTrap.png");  
      cardTextures[4] = new ImageIcon("Img/Cards/RoomCard/Crypt.png"); 
      //cardTextures[6] = new ImageIcon("Img/Cards/RoomCard/CurseOfTheWizard.png"); 
      cardTextures[5] = new ImageIcon("Img/Cards/RoomCard/DeadAdventurer.png"); 
      //cardTextures[8] = new ImageIcon("Img/Cards/RoomCard/GiantSpider.png"); 
      //cardTextures[9] = new ImageIcon("Img/Cards/RoomCard/Monster.png"); 
      //cardTextures[10] = new ImageIcon("Img/Cards/RoomCard/Potion.png"); 
      cardTextures[6] = new ImageIcon("Img/Cards/RoomCard/SneakAttack.png"); 
      cardTextures[7] = new ImageIcon("Img/Cards/RoomCard/TorchGoesOut.png"); 
      //cardTextures[13] = new ImageIcon("Img/Cards/RoomCard/TrapDoor.png"); 
      cardTextures[8] = new ImageIcon("Img/Cards/RoomCard/VampireBats.png"); 
        
      //load all the image for tiles
      tileTextures = new ImageIcon[26];
      tileTextures[0] = new ImageIcon("Img/Tile/TestTileImg.png");
      tileTextures[1] = new ImageIcon("Img/Tile/Corner.png");
      tileTextures[2] = new ImageIcon("Img/Tile/Border.png");
      tileTextures[3] = new ImageIcon("Img/Tile/EmptyCell.png");
      tileTextures[4] = new ImageIcon("Img/Tile/CurrentPlayer.png"); 
      tileTextures[5] = new ImageIcon("Img/Tile/GreenBorder.png");
      tileTextures[6] = new ImageIcon("Img/Tile/TreasureChamber.png");
      tileTextures[7] = new ImageIcon("Img/Tile/4WayCorridors.png");
      tileTextures[8] = new ImageIcon("Img/Tile/RotatingRoom.png");
      tileTextures[9] = new ImageIcon("Img/Tile/1WayCorridor.png");
      tileTextures[10] = new ImageIcon("Img/Tile/3WayCorridors.png");
      tileTextures[11] = new ImageIcon("Img/Tile/2WayDarkRoom.png");
      tileTextures[12] = new ImageIcon("Img/Tile/3WayDarkRoom.png");
      tileTextures[13] = new ImageIcon("Img/Tile/BottomlessPit.png");
      tileTextures[14] = new ImageIcon("Img/Tile/4WayPit.png");
      tileTextures[15] = new ImageIcon("Img/Tile/3WayTrap.png");
      tileTextures[16] = new ImageIcon("Img/Tile/4WayTrap.png");
      tileTextures[17] = new ImageIcon("Img/Tile/EndEmptyRoom.png");
      tileTextures[18] = new ImageIcon("Img/Tile/4WayEmptyRoom.png");
      tileTextures[19] = new ImageIcon("Img/Tile/EndPortcullis.png");
      tileTextures[20] = new ImageIcon("Img/Tile/1WayPortcullis.png");
      tileTextures[21] = new ImageIcon("Img/Tile/3WayPortcullis.png");
      tileTextures[22] = new ImageIcon("Img/Tile/2WayEmptyRoom.png");
      tileTextures[23] = new ImageIcon("Img/Tile/3WayEmptyRoom.png");
      
      tileTextures[24] = new ImageIcon("Img/Tile/TwoWayCorridor");
      tileTextures[25] = new ImageIcon("Img/Tile/TwoWayStraightEmptyRoom");
    
   }
   
   //method direction what to draw depending on the game state
   public static void drawGame(Graphics2D g, int screenWidth, int screenHeight){ 
      g.setColor(new Color(5, 5, 5));
      g.fillRect(0, 0, screenWidth, screenHeight); 
      if(gameState == GameState.START){
         g.drawImage(startImg.getImage(),0, 0, screenWidth, screenHeight, null);
      }else if(gameState == GameState.MAIN){
         mainmenu.draw(g, screenWidth, screenHeight);
      }else if(gameState == GameState.GAME){
         drawBoard(g, screenWidth, screenHeight);
         drawSidebar(g, screenWidth, screenHeight);
         drawCard(g, screenWidth, screenHeight);
      }else if(gameState == GameState.PAUSE){
         drawBoard(g, screenWidth, screenHeight);
         drawSidebar(g, screenWidth, screenHeight);
         drawPauseMenu(g, screenWidth, screenHeight);
      }
   }
   
   //draw the board
   public static void drawBoard(Graphics2D g, int screenWidth, int screenHeight){       
   
      //math for the proportion of the board 
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
            //if board contain a tile         
            if(board.get(i, j) != null){
               if(board.get(i, j) instanceof CornerTile){ //translation and rotation if corner
                  double cornerPosX = j*tileSize+(j==board.numColumns()-1? borderSize:0);
                  double cornerPosY = i*tileSize+(i==board.numRows()-1? borderSize:0);
                  transform.setToRotation(board.get(i, j).getRotation(), cornerPosX+(cornerTileSize/2), cornerPosY+(cornerTileSize/2));
                  transform.translate(cornerPosX, cornerPosY);
               }else{ //translation and rotation if any other tile
                  transform.setToRotation(board.get(i, j).getRotation(), j*tileSize+borderSize+(tileSize/2), i*tileSize+borderSize+(tileSize/2));
                  transform.translate(j*tileSize+borderSize, i*tileSize+borderSize);
               }
               //Draw the tile
               transform.scale(tileScale, tileScale);
               g.drawImage(tileTextures[board.get(i, j).getTextureID()].getImage(), transform, null); 
            }else{ 
               //empty cell
               transform.setToTranslation(j*tileSize+borderSize, i*tileSize+borderSize);
               transform.scale(tileScale, tileScale);
               g.drawImage(tileTextures[3].getImage(), transform, null); 
            }
            if(players.get(currentPlayer).isMoving() && players.get(currentPlayer).isValidMove(new Point(j, i))){
               //draw green border on valid moves if player is moving 
               transform.setToTranslation(j*tileSize+borderSize, i*tileSize+borderSize);
               transform.scale(tileScale, tileScale);
               g.drawImage(tileTextures[5].getImage(), transform, null); 
            }
         }
      }
      
      //Draw the borders
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
      
      //Draw the player
      for(int i=0; i<players.size(); i++){
         transform.setToTranslation((int)(playerSpace+tileSize*(players.get(i).getPosX())+borderSize), (int)(playerSpace+tileSize*(players.get(i).getPosY())+borderSize));
         transform.scale(playerScale, playerScale);
         g.drawImage(players.get(i).getImage(), transform, null);
      }
      //Draw current player blue bar 
      transform.setToTranslation((int)(playerSpace+tileSize*(players.get(currentPlayer).getPosX())+borderSize), (int)(tileSize-playerSpace+tileSize*(players.get(currentPlayer).getPosY())+borderSize));
      transform.scale(playerScale, playerScale);
      g.drawImage(tileTextures[4].getImage(), transform, null);
   }
   
   //Draw the side bar
   public static void drawSidebar(Graphics2D g, int screenWidth, int screenHeight){ 
      //draw the sidebar background  
      g.drawImage(sidebarBG.getImage(), (int)boardSize.getWidth(), 0, screenWidth -(int)boardSize.getWidth(), screenHeight, null);
      
      if(screenWidth>screenHeight){ //draw sidebar on side
         drawSunTrack(g, (int)boardSize.getWidth(), 0);
         players.get(currentPlayer).drawAction(g, (int)boardSize.getWidth()+8, 175);
         players.get(currentPlayer).drawLifeGold(g, (int)boardSize.getWidth()+8, 325);
      }else{   //draw sidebar on bottom
         drawSunTrack(g, 0, (int)boardSize.getHeight());
         players.get(currentPlayer).drawAction(g, 175, (int)boardSize.getHeight()+28);
         players.get(currentPlayer).drawLifeGold(g, 325, (int)boardSize.getHeight()+20);
      }
   }
   
   //The suns to display time left
   public static void drawSunTrack(Graphics2D g, int posX, int posY){
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      g.drawString("Sun track", posX+26, posY+20); 
	   for(int i=0; i<timeOfDay; i++)
         g.drawImage(sunImg.getImage(), posX+(i%8)*20, posY+25+20*(i/8), 20, 20, null);
   }
   
   //Draw the option of the pause menu
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
   }
   
   //Draw the card that the player received
   public static void drawCard(Graphics2D g, int screenWidth, int screenHeight){
      ActionCard c = players.get(currentPlayer).getCurrentCard();
      if(c != null && c.isShowing()){
         g.setColor(new Color(65, 105, 225, 225));
         g.fillRoundRect( (int)(screenWidth/6), (int)(screenHeight/6), (int)(screenWidth/1.5), (int)(screenHeight/1.5), screenWidth/8, screenHeight/8); 
         
         g.drawImage( cardTextures[c.getTextureID()].getImage(), (int)(screenWidth/2)-125, (int)(screenHeight/6), 250, 350, null);
         
         g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
         chgColorOnHover(g, Color.RED, Color.BLACK, 400, 545, 475, 565);
         g.drawString("ENTER", (int)(screenWidth/2), (int)(5*screenHeight/6)-10);
         
      }
   }
}