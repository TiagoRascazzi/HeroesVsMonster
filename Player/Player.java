import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public abstract class Player{
   
   private enum PlayeState {SELECT, MOVE, SEARCH};
   private PlayeState state = PlayeState.SELECT;
   
   private ImageIcon image;
   private String name;
   private int posX;
   private int posY;
   private int life;
   
   //Charracteristic
   private int strength;
   private int agility;
   private int armor;
   private int luck;
   
   //playerNum is between 1-4
   public Player(String url, String n, int x, int y, int maxLife, int strength, int agility, int armor, int luck){
      image = new ImageIcon(url); 
      name = n;
      
      posX = x;
      posY = y;
      life = maxLife;
      
      this.strength = strength;
      this.agility = agility;
      this.armor = armor;
      this.luck = luck;
   }
   
   public boolean processUserInput(KeyEvent e){            
      System.out.println(name+" did action "+e.getKeyChar());
      if(state == PlayeState.SELECT){
         if(e.getKeyCode() == KeyEvent.VK_1){
            //move();
            state = PlayeState.MOVE;
         }else if(e.getKeyCode() == KeyEvent.VK_2){
            search();
            state = PlayeState.SEARCH;
         }else if(e.getKeyCode() == KeyEvent.VK_3){ //if skip
            return true;
         }
      }else if(state == PlayeState.MOVE){
         if(e.getKeyCode() == KeyEvent.VK_LEFT){
            //posX--;
            move(posX-1, posY);
         }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            //posX++;
            move(posX+1, posY);
         }else if(e.getKeyCode() == KeyEvent.VK_UP){
            //posY--;
            move(posX, posY-1);
         }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            //posY++;
            move(posX, posY+1);
         }else{
            state = PlayeState.SELECT;
         }
      }else if(state == PlayeState.SEARCH){
         state = PlayeState.SELECT;
      }
      return false;
         
   }
   
   public void move(int x, int y){
      //if valid move and not other players are in it
         //if room had no tile then get new tile
      
      //get input from processUserInput using an emun state TODO
      //check if input is a valid move
         //if valid move
         //if not valid wait for new input
      /*if(isValidMove(x, y)){
      
      }*/
      if(isValidMove(x, y)){
         posX = x;
         posY = y;
      }
   }
   
   public boolean isValidMove(int x, int y){
      if(x>=0 && y>=0 && x<HVMPanel.board.numRows() && y<HVMPanel.board.numColumns()){
         return true;
      }
      return false;
   }
   
   public void search(){
      //get a card from search deck if room wasnt search more then twice consecusively
      //random search card
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      //int boardSize = screenHeight; 
      
      g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
      g.drawString(name, posX, posY);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
         
      if(state == PlayeState.SELECT){
         g.drawString("(1) MOVE", posX+8, posY+25);
         g.drawString("(2) SEARCH", posX+8, posY+50);
         g.drawString("(3) SKIP TURN", posX+8, posY+75);
         g.drawString("(esc) PAUSE", posX+8, posY+100);
      }else if(state == PlayeState.MOVE){
         g.drawString("(\u2190) LEFT", posX+8, posY+25);
         g.drawString("(\u2192) RIGTH", posX+8, posY+50);
         g.drawString("(\u2191) UP", posX+8, posY+75);
         g.drawString("(\u2193) DOWN", posX+8, posY+100);
         g.drawString("any other key to return", posX+8, posY+125);
      }else if(state == PlayeState.SEARCH){
         g.drawString("SEARCHING", posX+8, posY+25);
         g.drawString("any key to return", posX+8, posY+50);
      }
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
   
}