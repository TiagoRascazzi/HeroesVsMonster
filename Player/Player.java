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

public abstract class Player{
   
   private enum PlayerState {SELECT, MOVE, SEARCH, COMBAT, CARD};
   private PlayerState state = PlayerState.SELECT;
   
   private ImageIcon image;
   private ImageIcon lifeIcon;
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
   
   private int actionPosX;
   private int actionPosY;
   
   public Player(String url, String n, int x, int y, int maxLife, int strength, int agility, int armor, int luck){
      lifeIcon = new ImageIcon("Img/hearth.png");
      goldIcon = new ImageIcon("Img/gold.png");
      image = new ImageIcon(url); 
      playerCards =  new ArrayList<ActionCard>();
      playerCurrentCard = null;
      name = n;
      gold = 0;
      
      posX = x;
      posY = y;
      life = maxLife;
      
      this.strength = strength;
      this.agility = agility;
      this.armor = armor;
      this.luck = luck;
   }
   
   public boolean processKeyInput(KeyEvent e){            
      if(state == PlayerState.SELECT){
         if(e.getKeyCode() == KeyEvent.VK_1){
            state = PlayerState.MOVE;
         }else if(e.getKeyCode() == KeyEvent.VK_2){
            search();
            state = PlayerState.SEARCH;
         }else if(e.getKeyCode() == KeyEvent.VK_3){ //if skip
            return true;
         }
      }else if(state == PlayerState.MOVE){
         Point p = getKeyMove(e);
         if(p == null)
            state = PlayerState.SELECT;
         else
            return move(p);
      }else if(state == PlayerState.SEARCH){
         state = PlayerState.SELECT;
      }else if(state == PlayerState.CARD){
         if(playerCurrentCard.processKeyInput(e)){
            state = PlayerState.SELECT;
            life -= playerCurrentCard.getDamage();
            gold += playerCurrentCard.getGold();
            if(life<=0)
               return true;
            playerCurrentCard = null;
            if(!HVMPanel.board.get(posY, posX).keepsPlaying())
               return true;
         }
      }
      return false;
   }
   
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      if(state == PlayerState.SELECT){
         if(hover(actionPosX, actionPosX+140, actionPosY+40, actionPosY+65)){
            state = PlayerState.MOVE;
         }else if(hover(actionPosX, actionPosX+140, actionPosY+65, actionPosY+90)){
            search();
            state = PlayerState.SEARCH;
         }else if(hover(actionPosX, actionPosX+140, actionPosY+90, actionPosY+115)){//if skip
            return true;
         }else if(hover(actionPosX, actionPosX+140, actionPosY+115, actionPosY+140)){
            HVMPanel.gameState = HVMPanel.GameState.PAUSE;
         }
      }else if(state == PlayerState.MOVE){
         Point p = getMouseMove(e);
         if(p == null)
            state = PlayerState.SELECT;
         else
            return move(p);
      }else if(state == PlayerState.SEARCH){
         state = PlayerState.SELECT;
      }else if(state == PlayerState.CARD){
         //TODO return true 
         playerCurrentCard.processMouseInput(screenSize, e);
      }
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
      if(hover(actionPosX, actionPosX+140, actionPosY+40, actionPosY+65)){
         p = new Point();
         p.setLocation(posX-1, posY);
      }
      else if(hover(actionPosX, actionPosX+140, actionPosY+65, actionPosY+90)){
         p = new Point();
         p.setLocation(posX+1, posY);
      }
      else if(hover(actionPosX, actionPosX+140, actionPosY+90, actionPosY+115)){
         p = new Point();
         p.setLocation(posX, posY-1);
      }
      else if(hover(actionPosX, actionPosX+140, actionPosY+115, actionPosY+140)){
         p = new Point();
         p.setLocation(posX, posY+1);
      }
      return p;
   }
   
   public boolean move(Point p){
   
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
            if(HVMPanel.board.get(p.y, p.x) == null)  
               HVMPanel.board.add(p.y, p.x, tile);
            posX = p.x;
            posY = p.y;
            if(HVMPanel.board.get(posY, posX).givesRoomCard()){
               playerCurrentCard = RoomCard.getRandom();
               state = PlayerState.CARD;
            }else if(!HVMPanel.board.get(posY, posX).keepsPlaying()){
               state = PlayerState.SELECT;
               playerCurrentCard = null;
               return true;
            }
         }
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
                        return true;
                     }
                  }
               }else{ // if has no tile
                  return true;
               }
            }
         }
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
         if(HVMPanel.players.get(i).getPosX() == p.x && HVMPanel.players.get(i).getPosY() == p.y)
            count++;
      return count;
   }
   
   public void search(){
      //get a card from search deck if room wasnt search more then twice consecusively
      //random search card
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){ 
      
      actionPosX = posX;
      actionPosY = posY;
      
      g.setColor(Color.RED);   
      g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
      g.drawString(name, posX, posY);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
         
      if(state == PlayerState.SELECT){
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+40, posY+65);
         g.drawString("(1) MOVE", posX+8, posY+25);
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+65, posY+90);
         g.drawString("(2) SEARCH", posX+8, posY+50);
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+90, posY+115);
         g.drawString("(3) SKIP TURN", posX+8, posY+75);
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+115, posY+140);
         g.drawString("(esc) PAUSE", posX+8, posY+100);
      }else if(state == PlayerState.MOVE){
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+40, posY+65);
         g.drawString("(\u2190) LEFT", posX+8, posY+25);
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+65, posY+90);
         g.drawString("(\u2192) RIGTH", posX+8, posY+50);
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+90, posY+115);
         g.drawString("(\u2191) UP", posX+8, posY+75);
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+115, posY+140);
         g.drawString("(\u2193) DOWN", posX+8, posY+100);
         chgColorOnHover(g, Color.RED, Color.WHITE, posX+8, posX+148, posY+140, posY+165);
         g.drawString("any other key to return", posX+8, posY+125);
      }else if(state == PlayerState.SEARCH){
         g.drawString("SEARCHING", posX+8, posY+25);
         chgColorOnHover(g, Color.RED, Color.RED, posX+8, posX+148, posY+65, posY+90);
         g.drawString("any key to return", posX+8, posY+50);
      }else if(state == PlayerState.CARD){
         playerCurrentCard.drawAction(g, posX, posY);
      }
   } 
   
   public void drawLifeGold(Graphics2D g, int posX, int posY){
      g.drawImage(goldIcon.getImage(), posX, posY, 20, 20, null);
      g.setColor(Color.RED);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
      g.drawString(""+gold, posX+20+2, posY+20-2);
      for(int i=0; i<life; i++)
         g.drawImage(lifeIcon.getImage(), posX+(i%8)*20, posY+25+20*(i/8), 20, 20, null);
   } 
   
   public boolean isMoving(){
      return state == PlayerState.MOVE;
   }
   
   public int life(){
      return life;
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
   public Image getImage(){
      return image.getImage();
   }
   
   private boolean hover(int x1, int x2, int y1, int y2){
      return (HVMPanel.mouse.x>x1 && HVMPanel.mouse.x<x2 && HVMPanel.mouse.y>y1 && HVMPanel.mouse.y<y2);
   }
   
   private void chgColorOnHover(Graphics2D g, Color c1, Color c2, int x1, int x2, int y1, int y2){
      g.setColor(c1);
      if(hover(x1, x2, y1, y2))
         g.setColor(c2);
   }
   
   public ActionCard getCurrentCard(){
      return playerCurrentCard;
   }
   
}