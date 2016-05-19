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
   
   private enum PlayeState {SELECT, MOVE, SEARCH, COMBAT, CARD};
   private PlayeState state = PlayeState.SELECT;
   
   private ImageIcon image;
   private ImageIcon lifeIcon;
   private ImageIcon goldIcon;
   private String name;
   private int posX;
   private int posY;
   private int life;
   private int gold;// Comment 15 I hava also added a gold variable it is going to store to gold that the player collects  GOTO Player.java at Comment #16
   private ArrayList<ActionCard> playerCards;
   private ActionCard playerCurrentCard;
   
   //Charracteristic
   private int strength;
   private int agility;
   private int armor;
   private int luck;
   
   
   private int actionPosX;
   private int actionPosY;
   
   //playerNum is between 1-4
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
      if(state == PlayeState.SELECT){
         if(e.getKeyCode() == KeyEvent.VK_1){ //move();
            state = PlayeState.MOVE;
         }else if(e.getKeyCode() == KeyEvent.VK_2){
            search();
            state = PlayeState.SEARCH;
         }else if(e.getKeyCode() == KeyEvent.VK_3){ //if skip
            return true;
         }
      }else if(state == PlayeState.MOVE){
         Point p = getKeyMove(e);
         if(p == null)
            state = PlayeState.SELECT;
         else
            return move(p);
      }else if(state == PlayeState.SEARCH){
         state = PlayeState.SELECT;
      }else if(state == PlayeState.CARD){
         /*
         Comment #3
           Here it is going to send the key that was pressed to the card
           and if the card returns true it mean that the card has finished to do whatever it has to do
           GOTO ActionCard.java at Comment #4
         */
         if(playerCurrentCard.processKeyInput(e)){
            state = PlayeState.SELECT;
            playerCurrentCard = null;
            return true;
         }
      }
      return false;
   }
   
   public boolean processMouseInput(Point screenSize, MouseEvent e){
      if(state == PlayeState.SELECT){
         if(hover(actionPosX, actionPosX+140, actionPosY+40, actionPosY+65)){
            state = PlayeState.MOVE;
         }else if(hover(actionPosX, actionPosX+140, actionPosY+65, actionPosY+90)){
            search();
            state = PlayeState.SEARCH;
         }else if(hover(actionPosX, actionPosX+140, actionPosY+90, actionPosY+115)){//if skip
            return true;
         }else if(hover(actionPosX, actionPosX+140, actionPosY+115, actionPosY+140)){
            HVMPanel.gameState = HVMPanel.GameState.PAUSE;
         }
      }else if(state == PlayeState.MOVE){
         Point p = getMouseMove(e);
         if(p == null)
            state = PlayeState.SELECT;
         else
            return move(p);
      }else if(state == PlayeState.SEARCH){
         state = PlayeState.SELECT;
      }else if(state == PlayeState.CARD){
         /*
          Comment #7  
            here it is giong to do the same thing then at Comment #3 but it is going to take the mouse input (Not implemented yet)
            GOTO ActionCard.java at Comment #8
        */
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
      if(isValidMoveEmpty(p)){
         if(HVMPanel.board.get(p.y, p.x) == null){
         
            Tile tile =  Tile.getRandomTile();
            
            if(isValidFutureMove(p, tile)){
               if(p.x == posX+1)
                  tile.setOrientation(Tile.LEFT);
               else if(p.x == posX-1)
                  tile.setOrientation(Tile.RIGHT);
               else if(p.y == posY+1)
                  tile.setOrientation(Tile.TOP);
               else if(p.y == posY-1)
                  tile.setOrientation(Tile.BOTTOM);
               
               HVMPanel.board.add(p.y, p.x, tile);
            }
            
         }
         if(isValidMove(p)){
            posX = p.x;
            posY = p.y;
            
            if(HVMPanel.board.get(posY, posX).givesRoomCard()){
               if(!HVMPanel.board.get(posY, posX).keepsPlaying()){
                  playerCurrentCard = RoomCard.getRandom();  //This is going to get a random card and put it in the player current card GOTO RoomCard.java at Comment #2
                  state = PlayeState.CARD;  //This is going to set the state to CARD GOTO Player.java at Comment #3
               }
            }else{
               if(!HVMPanel.board.get(posY, posX).keepsPlaying()){
                  return true;
               }
            }
         }
      }
      return false;
   }
   
   
   public boolean isValidMoveEmpty(Point p){
      if(p.x>=0 && p.y>=0 && p.x<HVMPanel.board.numColumns() && p.y<HVMPanel.board.numRows()){// test if inside board
         if( ((p.x==posX-1 || p.x==posX+1) && p.y==posY)  || ((p.y==posY-1 || p.y==posY+1) && p.x==posX)){ //test if it is next to current one
            return true;
         }
      }
      return false;
   }
   
   public boolean isValidMove(Point p){
      if(isValidMoveEmpty(p)){
         if(HVMPanel.board.get(p.y, p.x) == null || getNumOfPlayersAt(p) < HVMPanel.board.get(p.y, p.x).getMaxNumOfPlayers()){
            if(p.x == posX+1 && HVMPanel.board.get(p.y, p.x).isRightSideOpen())
               return true;
            if(p.x == posX-1 && HVMPanel.board.get(p.y, p.x).isLeftSideOpen())
               return true;
            if(p.y == posY+1 && HVMPanel.board.get(p.y, p.x).isBottomSideOpen())
               return true;
            if(p.y == posY-1 && HVMPanel.board.get(p.y, p.x).isTopSideOpen())
               return true;        
         }
      }
      return false;
   }  
   
   public boolean isValidFutureMove(Point p, Tile tile){
      if(isValidMoveEmpty(p)){
         if(HVMPanel.board.get(p.y, p.x) == null || getNumOfPlayersAt(p) < HVMPanel.board.get(p.y, p.x).getMaxNumOfPlayers()){
            if(p.x == posX+1 && tile.isRightSideOpen())
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
      
      g.setColor(Color.BLACK);   
      g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
      g.drawString(name, posX, posY);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
         
      if(state == PlayeState.SELECT){
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+40, posY+65);
         g.drawString("(1) MOVE", posX+8, posY+25);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+65, posY+90);
         g.drawString("(2) SEARCH", posX+8, posY+50);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+90, posY+115);
         g.drawString("(3) SKIP TURN", posX+8, posY+75);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+115, posY+140);
         g.drawString("(esc) PAUSE", posX+8, posY+100);
      }else if(state == PlayeState.MOVE){
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+40, posY+65);
         g.drawString("(\u2190) LEFT", posX+8, posY+25);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+65, posY+90);
         g.drawString("(\u2192) RIGTH", posX+8, posY+50);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+90, posY+115);
         g.drawString("(\u2191) UP", posX+8, posY+75);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+115, posY+140);
         g.drawString("(\u2193) DOWN", posX+8, posY+100);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+140, posY+165);
         g.drawString("any other key to return", posX+8, posY+125);
      }else if(state == PlayeState.SEARCH){
         g.drawString("SEARCHING", posX+8, posY+25);
         chgColorOnHover(g, Color.BLACK, Color.RED, posX+8, posX+148, posY+65, posY+90);
         g.drawString("any key to return", posX+8, posY+50);
      }else if(state == PlayeState.CARD){
        /*
          Comment #10
            here if the state is CARD it is going to ask the current card to draw what action it can do
          GOTO ActionCard at Comment #11
        */
         playerCurrentCard.drawAction(g, posX, posY);
      }
   } 
   
   public void drawLifeGold(Graphics2D g, int posX, int posY){
      /*
        Comment #16
          to draw the gold value simply change the name of this method from drawLife to drawLifeGold
          because it used to draw only the life but now it also draw to gold valu
      */
      g.drawImage(goldIcon.getImage(), posX, posY, 20, 20, null);  //draw the gold icon
      g.setColor(Color.BLACK); //set font color to black
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));// set font 
      g.drawString(""+gold, posX+20+2, posY+20-2);// draw the gold value which is supposed to stay to zero now because nothing change it
      for(int i=0; i<life; i++)
         g.drawImage(lifeIcon.getImage(), posX+(i%8)*20, posY+25+20*(i/8), 20, 20, null);// this is going to draw asmany hearth as the player life (8 on each row)
         
      //DONE this was the last comment
   } 
   
   public boolean isMoving(){
      return state == PlayeState.MOVE;
   }
   
   public int life(){
      return life;
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