import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
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
            move();
            state = PlayeState.MOVE;
         }else if(e.getKeyCode() == KeyEvent.VK_2){
            search();
            state = PlayeState.SEARCH;
         }else if(e.getKeyCode() == KeyEvent.VK_3){ //if skip
            return true;
         }
      }else if(state == PlayeState.MOVE){
         if(e.getKeyCode() == KeyEvent.VK_LEFT){
            posX--;
         }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            posX++;
         }else if(e.getKeyCode() == KeyEvent.VK_UP){
            posY--;
         }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            posY++;
         }else{
            state = PlayeState.SELECT;
         }
      }else if(state == PlayeState.SEARCH){
         state = PlayeState.SELECT;
      }
      return false;
         
   }
   
   public void move(){
      //if valid move and not other players are in it
         //if room had no tile then get new tile
      
      //get input from processUserInput using an emun state TODO
      //check if input is a valid move
         //if valid move
         //if not valid wait for new input
      /*if(isValidMove(x, y)){
      
      }*/
      
   }
   
   public void isValidMove(int x, int y){
   
   }
   
   public void search(){
      //get a card from search deck if room wasnt search more then twice consecusively
      //random search card
   }
   
   public void drawAction(Graphics g, int screenWidth, int screenHeight){
      int boardSize = screenHeight; 
      
      g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
      g.drawString(name, boardSize+8, 175);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
         
      if(state == PlayeState.SELECT){
         g.drawString("(1) MOVE", boardSize+16, 200);
         g.drawString("(2) SEARCH", boardSize+16, 225);
         g.drawString("(3) SKIP TURN", boardSize+16, 250);
         g.drawString("(esc) PAUSE", boardSize+16, 275);
      }else if(state == PlayeState.MOVE){
         g.drawString("(<) LEFT", boardSize+16, 200);
         g.drawString("(>) RIGTH", boardSize+16, 225);
         g.drawString("(/\\) UP", boardSize+16, 250);
         g.drawString("(\\/) DOWN", boardSize+16, 275);
         g.drawString("any other key to return", boardSize+16, 300);
      }else if(state == PlayeState.SEARCH){
         g.drawString("SEARCHING", boardSize+16, 200);
         g.drawString("any key to return", boardSize+16, 225);
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