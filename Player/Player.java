import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player{
   
   private enum PlayerState {SELECT, MOVE, COMBAT, CARD};
   private PlayerState state = PlayerState.SELECT;
   
   private ImageIcon image;
   private ImageIcon lifeIcon;
   private ImageIcon strengthIcon;
   private ImageIcon goldIcon;
   private String name;
   
   private ArrayList<ActionCard> playerCards;
   private ActionCard playerCurrentCard;
   
   //Charracteristic
   private int strength;
   private int agility;
   private int armor;
   private int luck;
   
   private int life;
   private int gold;
   private int posX;
   private int posY;
   private Point lastPos;
   
   private int actionPosX;
   private int actionPosY;
   
   public Player(String url, String n, int x, int y, int maxLife, int strength, int agility, int armor, int luck){
      lifeIcon = new ImageIcon("Img/hearth.png");
      goldIcon = new ImageIcon("Img/gold.png");
      strengthIcon = new ImageIcon("Img/strength.png");
      image = new ImageIcon(url); 
      playerCards =  new ArrayList<ActionCard>();
      playerCurrentCard = null;
      name = n;
      gold = 0;
      
      posX = x;
      posY = y;
      lastPos = new Point(x, y);
      life = maxLife;
      
      this.strength = strength;
      this.agility = agility;
      this.armor = armor;
      this.luck = luck;
   }
   
   public boolean processKeyInput(KeyEvent e){   
      if(state == PlayerState.SELECT){
         
         boolean disableDefault = false;
         for(int i=0; i<playerCards.size(); i++)
            if(playerCards.get(i).disableDefaultAction() == true)
               disableDefault = true;
         
         int count = 1;
         if(!disableDefault){
            if(e.getKeyChar() == (count+"").charAt(0))  //move
               state = PlayerState.MOVE;
            count++;
            
            if(HVMPanel.board.get(posY, posX).isSearchable()){
               if(e.getKeyChar() == (count+"").charAt(0)){
                  playerCurrentCard = SearchCard.getRandom(); //search
                  state = PlayerState.CARD;
               }
               count++;
            }
         }
         if(e.getKeyChar() == (count+"").charAt(0)){ //if skip
            if(HVMPanel.board.get(posY, posX) instanceof TreasureChamberTile)
               treasureChamberAction();
            return true;
         }
         count++;
         
         //TODO CARD ACTION
         for(int i=0; i<playerCards.size(); i++){
            boolean done = playerCards.get(i).actionKeyInput(e, count);
            life -= playerCards.get(i).getDamage();
            gold += playerCards.get(i).getGold();
            if(playerCards.get(i).getPrintableAction() != null)
               count += playerCards.get(i).getPrintableAction().length;
            if(playerCards.get(i).getRidOfCard())  //maybe buggy
               playerCards.remove(i);
            if(done == true)
               return true;
         }
         
      }
      else if(state == PlayerState.MOVE){
         Point p = getKeyMove(e);
         if(p == null)
            state = PlayerState.SELECT;
         else
            return move(p, false);
      }
      else if(state == PlayerState.CARD){
         ActionCard tmpCard = playerCurrentCard.processKeyInput(e);               
         if(playerCurrentCard.getMoveTo() != null)
            move(playerCurrentCard.getMoveTo(), true);
         if(tmpCard != null && !hasSameCard(tmpCard))
            playerCards.add(tmpCard);
         
         ActionCard tmpCard2 = playerCurrentCard.getNewCard();
         if(tmpCard2 != null)
            playerCurrentCard = tmpCard2;
         else if(playerCurrentCard != null && !playerCurrentCard.isActive())
            return afterReceivedCard();
      }
      return false;
   }
   
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      if(state == PlayerState.SELECT){
         
         boolean disableDefault = false;
         for(int i=0; i<playerCards.size(); i++)
            if(playerCards.get(i).disableDefaultAction() == true)
               disableDefault = true;
         
         int count = 0;
         if(!disableDefault){
            if(GUI.hover(actionPosX, actionPosX+140, actionPosY+40+(25*count), actionPosY+40+25+(25*count)))   //move
               state = PlayerState.MOVE;
            count++;
            
            if(HVMPanel.board.get(posY, posX).isSearchable()){
               if(GUI.hover(actionPosX, actionPosX+140, actionPosY+40+(25*count), actionPosY+40+25+(25*count))){  //search
                  playerCurrentCard = SearchCard.getRandom();
                  state = PlayerState.CARD;
               }
               count++;
            }
         }
         if(GUI.hover(actionPosX, actionPosX+140, actionPosY+40+(25*count), actionPosY+40+25+(25*count))){  //if skip
            state = PlayerState.SELECT;
            return true;
         }
         count++;
         
         //TODO CARD ACTION
         for(int i=0; i<playerCards.size(); i++){
            boolean done = playerCards.get(i).actionMouseInput(screenSize, new Point(actionPosX, actionPosY), e, count);
            if(playerCards.get(i).getPrintableAction() != null)
               count += playerCards.get(i).getPrintableAction().length;
            if(done == true)
               return true;
         }
         for(int i=0; i<playerCards.size(); i++){
            if(playerCards.get(i).getRidOfCard()){
               playerCards.remove(i);
            }
         }
         
         if(GUI.hover(actionPosX, actionPosX+140, actionPosY+40+(25*count), actionPosY+40+25+(25*count)))   //pause
            HVMPanel.gameState = HVMPanel.GameState.PAUSE;
         //count++
         
      }
      else if(state == PlayerState.MOVE){
         Point p = getMouseMove(e);
         if(p == null)
            state = PlayerState.SELECT;
         else
            return move(p, false);
      }
      else if(state == PlayerState.CARD){
         ActionCard tmpCard = playerCurrentCard.processMouseInput(screenSize, e);
         if(playerCurrentCard.getMoveTo() != null)
            move(playerCurrentCard.getMoveTo(), true);
         if(tmpCard != null && !hasSameCard(tmpCard))
            playerCards.add(tmpCard);
            
         ActionCard tmpCard2 = playerCurrentCard.getNewCard();
         if(tmpCard2 != null)
            playerCurrentCard = tmpCard2;
         else if(playerCurrentCard != null && !playerCurrentCard.isActive())
            return afterReceivedCard();
         
      }
      return false;
   }
   
   public boolean hasSameCard(ActionCard ac){
      for(int i=0; i<playerCards.size(); i++)
         if(playerCards.get(i).getClass().equals( ac.getClass()))
            return true;
      return false;
   }
   
   public boolean afterReceivedCard(){
      state = PlayerState.SELECT;
      life -= playerCurrentCard.getDamage();
      gold += playerCurrentCard.getGold();
      if(life<=0){
         Display.showTextPopup("You just died");
         return true;
      }
      ActionCard oldCard = playerCurrentCard;
      playerCurrentCard = null;
      
      if(oldCard.receiveNewRoomCard())
         afterMove();
      else if(!HVMPanel.board.get(posY, posX).keepsPlaying())
         return true;
      return false;
   }
   
   public Point getKeyMove(KeyEvent e){
      Point p = null;
      if(e.getKeyCode() == KeyEvent.VK_LEFT){
         p = new Point();
         p.setLocation(posX-1, posY);
      }
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
         p = new Point();
         p.setLocation(posX+1, posY);
      }
      else if(e.getKeyCode() == KeyEvent.VK_UP){
         p = new Point();
         p.setLocation(posX, posY-1);
      }
      else if(e.getKeyCode() == KeyEvent.VK_DOWN){
         p = new Point();
         p.setLocation(posX, posY+1);
      }
      return p;
   }
   public Point getMouseMove(MouseEvent e){
      Point p = null;
      if(GUI.hover(actionPosX, actionPosX+140, actionPosY+40, actionPosY+65)){
         p = new Point();
         p.setLocation(posX-1, posY);
      }
      else if(GUI.hover(actionPosX, actionPosX+140, actionPosY+65, actionPosY+90)){
         p = new Point();
         p.setLocation(posX+1, posY);
      }
      else if(GUI.hover(actionPosX, actionPosX+140, actionPosY+90, actionPosY+115)){
         p = new Point();
         p.setLocation(posX, posY-1);
      }
      else if(GUI.hover(actionPosX, actionPosX+140, actionPosY+115, actionPosY+140)){
         p = new Point();
         p.setLocation(posX, posY+1);
      }
      return p;
   }
   
   public boolean move(Point p, boolean door){
      if(isValidMove(p)){ //test if it is in the board
         Tile tile = null;
         if(HVMPanel.board.get(p.y, p.x) == null)
            tile = Tile.getRandomTile();
         if(tile != null){
            if(p.x == posX+1)
               tile.setOrientation(Tile.LEFT);
            else if(p.x == posX-1)
               tile.setOrientation(Tile.RIGHT);
            else if(p.y == posY+1)
               tile.setOrientation(Tile.TOP);
            else if(p.y == posY-1)
               tile.setOrientation(Tile.BOTTOM);
         }
         else
            tile = HVMPanel.board.get(p.y, p.x);
         
         if(isValidMove(p)){
            
            if(!door && hasDoor(p) || hasDoor(tile, p)){
               playerCurrentCard = DoorCard.getRandom(p);
               state = PlayerState.CARD;
             
               if(playerCurrentCard instanceof DoorOpens || (hasDoor(p) && hasDoor(tile, p)))
                  HVMPanel.board.add(p.y, p.x, tile);
               if(playerCurrentCard instanceof DoorOpens){
                  lastPos.setLocation(posX, posY);
                  posX = p.x;
                  posY = p.y;
                  BGMusicPlayer.playSound(4);
                  Display.showCardPopup(playerCurrentCard);
                  return afterMove();
               }               
               return false;
            }else{
               
               int temp = (int)(Math.random()*12+1);
               if( (!(HVMPanel.board.get(posY, posX) instanceof EndPortcullis) && !(HVMPanel.board.get(posY, posX) instanceof OneWayPortcullis) && !(HVMPanel.board.get(posY, posX) instanceof ThreeWayPortcullis)) || isNotInPortcullisDirection(p) || (strength > temp)){
                  HVMPanel.board.add(p.y, p.x, tile);
                  lastPos.setLocation(posX, posY);
                  posX = p.x;
                  posY = p.y;
                  BGMusicPlayer.playSound(0);
                  if(tile instanceof RotatingRoom)
                     tile.rotate180DegClockwise();
                  if(tile instanceof TreasureChamberTile)
                     treasureChamberAction();
                  return afterMove();
               }else if( HVMPanel.board.get(posY, posX) instanceof EndPortcullis || HVMPanel.board.get(posY, posX) instanceof OneWayPortcullis || HVMPanel.board.get(posY, posX) instanceof ThreeWayPortcullis){
                   Display.showTextPopup("The porculus did'nt move!");
                   return true;
               }   
            }
         }
      }
      return false;
   }
   
   public boolean isNotInPortcullisDirection(Point p){
      Tile t = HVMPanel.board.get(posY, posX);
      if(p.x == posX-1 && t.getOneWayLeft() || p.x == posX+1 && t.getOneWayRigth() || p.y == posY-1 && t.getOneWayTop() || p.y == posY+1 && t.getOneWayBottom())
         return false;
      return true;
   }
   
   public void treasureChamberAction(){
      Random random = new Random();
      int g = random.nextInt(80)*10+200; //between 200-1000 by step of ten
      gold += g;
      BGMusicPlayer.playSound(3);
      Display.showTextPopup("You have collected "+g+" gold\nyou have a total of "+gold);
      String dc = DragonCounter.getRandom();
      if(dc.equals("DragonWakesUp")){
         int lf = random.nextInt(12) + 1;
         Display.showTextPopup("THE DRAGON WOKE UP !!!\n you lost all your gold\nand lost "+lf+" lives");
         life -= lf;
         gold = 0;
      }
   }
   
   public boolean hasDoor(Point p){
      Tile t = HVMPanel.board.get(posY, posX);      
      if(p.x == posX+1 && t.getDoors()[1] || p.x == posX-1 && t.getDoors()[3] || p.y == posY+1 && t.getDoors()[2] || p.y == posY-1 && t.getDoors()[0])
         return true; 
      return false;  
   }
   public boolean hasDoor(Tile t, Point p){
      if(p.x == posX+1 && t.getDoors()[3] || p.x == posX-1 && t.getDoors()[1] || p.y == posY+1 && t.getDoors()[0] || p.y == posY-1 && t.getDoors()[2])
         return true; 
      return false;  
   }
   
   public boolean afterMove(){
      if(HVMPanel.board.get(posY, posX).givesRoomCard()){
         playerCurrentCard = RoomCard.getRandom();
         state = PlayerState.CARD;
      }
      else if(!HVMPanel.board.get(posY, posX).keepsPlaying()){
         state = PlayerState.SELECT;
         playerCurrentCard = null;
         return true;
      }
      return false;
   }
   
   public boolean isValidMove(Point p){
      if(isInsideBoard(p)){// test if inside board
         if( isNextToCurrentTile(p) ){ //test if it is next to current one
            if( canExit(p) ){//test if can get out of tile
               if(HVMPanel.board.get(p.y, p.x) != null){// if already has tile
                  if(getNumOfPlayersAt(p) < HVMPanel.board.get(p.y, p.x).getMaxNumOfPlayers()){ //check max number of player
                     if(canEnter(p)){//test if can enter in tile
                        if(isOneWay(p)){
                           return true;
                        }
                     }
                  }
               }
               else{ // if has no tile
                  return true;
               }
            }     
         }// its your end
      }
      return false;
   }
   public boolean isInsideBoard(Point p){
      return p.x>=0 && p.y>=0 && p.x<HVMPanel.board.numColumns() && p.y<HVMPanel.board.numRows();
   }
   public boolean isNextToCurrentTile(Point p){
      return ((p.x==posX-1 || p.x==posX+1) && p.y==posY)  || ((p.y==posY-1 || p.y==posY+1) && p.x==posX);
   }
   public boolean canExit(Point p){
      Tile tile = HVMPanel.board.get(posY, posX);
      return p.x == posX+1 && tile.isRightSideOpen() || p.x == posX-1 && tile.isLeftSideOpen() || p.y == posY+1 && tile.isBottomSideOpen() || p.y == posY-1 && tile.isTopSideOpen();
   }
   public boolean canEnter(Point p){
      Tile tile = HVMPanel.board.get(p.y, p.x);      
      return p.x == posX+1 && tile.isLeftSideOpen() || p.x == posX-1 && tile.isRightSideOpen() || p.y == posY+1 && tile.isTopSideOpen() || p.y == posY-1 && tile.isBottomSideOpen();
   }   
   public boolean isOneWay(Point p){
      Tile t = HVMPanel.board.get(posY, posX);
      if(p.x == posX-1 && t.getOneWayLeft() || p.x == posX+1 && t.getOneWayRigth() || p.y == posY-1 && t.getOneWayTop() || p.y == posY+1 && t.getOneWayBottom())
         return false;
      return true;
   }
   
   public boolean isValidFutureMove(Point p, Tile tile){
      if(isValidMove(p)){
         if(HVMPanel.board.get(p.y, p.x) == null || getNumOfPlayersAt(p) < HVMPanel.board.get(p.y, p.x).getMaxNumOfPlayers()){
            if(p.x == posX+1 && tile.isRightSideOpen())//test if can enter in tile
               return true;
            if(p.x == posX-1 && tile.isLeftSideOpen())
               return true;
            if(p.y == posY+1 && tile.isBottomSideOpen())
               return true;
            if(p.y == posY-1 && tile.isTopSideOpen())
               return true;        
         }
      }
      return false;
   }
   
   public int getNumOfPlayersAt(Point p){
      int count = 0;
      for(int i = 0; i < HVMPanel.players.size(); i++)
         if(HVMPanel.players.get(i).isAlive() && HVMPanel.players.get(i).getPosX() == p.x && HVMPanel.players.get(i).getPosY() == p.y)
            count++;
      return count;
   }
   
   public void drawAction(Graphics2D g, int pX, int pY){ 
      
      actionPosX = pX;
      actionPosY = pY;
      
      g.setColor(Color.RED);   
      g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
      g.drawString(name, pX, pY);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      
      if(state == PlayerState.SELECT){
         boolean disableDefault = false;
         for(int i=0; i<playerCards.size(); i++)
            if(playerCards.get(i).disableDefaultAction() == true)
               disableDefault = true;
            
         
         
         int count = 0;
         if(!disableDefault){
            GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+40+25*count, pY+65+25*count);
            g.drawString("("+(count+1)+") MOVE", pX+8, pY+25+25*count);
            count++;
            
            if(HVMPanel.board.get(posY, posX).isSearchable()){
               GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+40+25*count, pY+65+25*count);
               g.drawString("("+(count+1)+") SEARCH", pX+8, pY+25+25*count);
               count++;
            }
         }
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+40+25*count, pY+65+25*count);
         g.drawString("("+(count+1)+") SKIP TURN", pX+8, pY+25+25*count);
         count++;
         
         for(int i=0; i<playerCards.size(); i++){
            if(playerCards.get(i).getPrintableAction() != null){
               for(int j=0; j<playerCards.get(i).getPrintableAction().length; j++){
                  GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+40+25*count, pY+65+25*count);
                  g.drawString("("+(count+1)+")"+playerCards.get(i).getPrintableAction()[j], pX+8, pY+25+25*count);
                  count++;
               }
            }
         }
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+40+25*count, pY+65+25*count);
         g.drawString("(esc) PAUSE", pX+8, pY+25+25*count);  //TODO fix click on pause with more stuff
         
      }
      else if(state == PlayerState.MOVE){
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+40, pY+65);
         g.drawString("(\u2190) LEFT", pX+8, pY+25);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+65, pY+90);
         g.drawString("(\u2192) RIGTH", pX+8, pY+50);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+90, pY+115);
         g.drawString("(\u2191) UP", pX+8, pY+75);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+115, pY+140);
         g.drawString("(\u2193) DOWN", pX+8, pY+100);
         GUI.chgColorOnHover(g, Color.RED, Color.WHITE, pX+8, pX+148, pY+140, pY+165);
         g.drawString("any other key to return", pX+8, pY+125);
      }
      else if(state == PlayerState.CARD){
         playerCurrentCard.drawAction(g, pX, pY);
      }
   } 
   
   public void drawLifeGoldStrength(Graphics2D g, int posX, int posY){
   
      //Strength
      g.drawImage(strengthIcon.getImage(), posX, posY, 20, 20, null);
      g.setColor(Color.WHITE);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
      g.drawString(""+strength, posX+20+2, posY+20-2);
      
      //Gold
      g.drawImage(goldIcon.getImage(), posX+50, posY, 20, 20, null);
      g.setColor(Color.WHITE);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
      g.drawString(""+gold, posX+20+2+50, posY+20-2);  
      
      //Life
      for(int i=0; i<life; i++)
         g.drawImage(lifeIcon.getImage(), posX+(i%8)*20, posY+25+20*(i/8), 20, 20, null);
   } 

   
   public void drawCards(Graphics2D g, int posX, int posY){
      for(int i=0; i<playerCards.size(); i++)
         g.drawImage(Display.getCardTextures(playerCards.get(i).getTextureID()).getImage(), posX+(70*(i%3))-10, posY-(100*((i/3)+1))-10, 70, 100, null);
   }
   
   public boolean isMoving(){
      return state == PlayerState.MOVE;
   }
   
   public void loseLife(int n){
      life-=n;
   }
   public int life(){
      return life;
   }
   public int gold(){
      return gold;
   }
   public boolean isAlive(){
      return life > 0;
   }
   
   public String getName(){
      return name;
   }
   
   public void setPos(int x, int y){
      posX = x;
      posY = y;
   }
   public int getPosX(){
      return posX;
   }
   public int getPosY(){
      return posY;
   }
   public void returnToLastPos(){
      int tmpX = posX;
      int tmpY = posY;
      posX = lastPos.x;
      posY = lastPos.y;
      lastPos.setLocation(tmpX, tmpY);
   }
   public Image getImage(){
      return image.getImage();
   }
   
   public ActionCard getCurrentCard(){
      return playerCurrentCard;
   }
   
}