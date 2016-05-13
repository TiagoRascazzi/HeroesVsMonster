import java.awt.Image;
import java.awt.event.KeyEvent;

public abstract class Player{
   
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
   public Player(String n, int x, int y, int maxLife, int strength, int agility, int armor, int luck){
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
      
      if(e.getKeyCode() == KeyEvent.VK_1){ //move to valid pos
         move();
      }else if(e.getKeyCode() == KeyEvent.VK_2){ //search room if searchable
         search();
      }else if(e.getKeyCode() == KeyEvent.VK_3){
         return true;
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
   
   public abstract Image getImage();
   
}