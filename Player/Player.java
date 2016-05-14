import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Color;

public abstract class Player{
   
   private enum PlayeState {SELECT, MOVE, SEARCH};
   private PlayeState state = PlayeState.SELECT;
   
   private ImageIcon image;
   private ImageIcon lifeIcon;
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
      lifeIcon = new ImageIcon("Img/hearth.png");
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
   
   public boolean processKeyInput(KeyEvent e){            
      System.out.println(name+" did action "+e.getKeyChar());
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
            move(p);
      }else if(state == PlayeState.SEARCH){
         state = PlayeState.SELECT;
      }
      return false;
   }
   
   public boolean processMouseInput(MouseEvent e){
      if(state == PlayeState.SELECT){
         if(e.getButton() == MouseEvent.BUTTON1){
            if(hover(530, 670, 215, 240)){
               state = PlayeState.MOVE;
            }else if(hover(530, 670, 240, 265)){
               search();
               state = PlayeState.SEARCH;
            }else if(hover(530, 670, 265, 290)){ //if skip
               return true;
            }else if(hover(530, 670, 290, 315)){
               HVMPanel.gameState = HVMPanel.GameState.PAUSE;
            }
         }
      }else if(state == PlayeState.MOVE){
         if(e.getButton() == MouseEvent.BUTTON1){
            Point p = getMouseMove(e);
            if(p == null)
               state = PlayeState.SELECT;
            else
               move(p);
         }
      }else if(state == PlayeState.SEARCH){
         if(e.getButton() == MouseEvent.BUTTON1)
            state = PlayeState.SELECT;
      }
      return false;
   }
   
   public Point getKeyMove(KeyEvent e){
      Point p = null;
      if(e.getKeyCode() == KeyEvent.VK_LEFT){
         p = new Point();
         p.setLocation(posX-1, posY);
      }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
         p = new Point();
         p.setLocation(posX+1, posY);
      }else if(e.getKeyCode() == KeyEvent.VK_UP){
         p = new Point();
         p.setLocation(posX, posY-1);
      }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
         p = new Point();
         p.setLocation(posX, posY+1);
      }
      return p;
   }
   public Point getMouseMove(MouseEvent e){
      Point p = null;
      if(hover(530, 670, 215, 240)){
         p = new Point();
         p.setLocation(posX-1, posY);
      }else if(hover(530, 670, 240, 265)){
         p = new Point();
         p.setLocation(posX+1, posY);
      }else if(hover(530, 670, 265, 290)){
         p = new Point();
         p.setLocation(posX, posY-1);
      }else if(hover(530, 670, 290, 315)){
         p = new Point();
         p.setLocation(posX, posY+1);
      }
      return p;
   }
   
   public void move(Point p){
      //if valid move and not other players are in it
         //if room had no tile then get new tile
      
      //get input from processUserInput using an emun state TODO
      //check if input is a valid move
         //if valid move
         //if not valid wait for new input
      /*if(isValidMove(x, y)){
      
      }*/
      if(isValidMove(p)){
         posX = p.x;
         posY = p.y;
      }
   }
   
   public boolean isValidMove(Point p){
      if(p.x>=0 && p.y>=0 && p.x<HVMPanel.board.numColumns() && p.y<HVMPanel.board.numRows()){
         return true;
      }
      return false;
   }
   
   public void search(){
      //get a card from search deck if room wasnt search more then twice consecusively
      //random search card
   }
   
   public void drawAction(Graphics2D g, int posX, int posY){
      
      g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
      g.drawString(name, posX, posY);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
         
      if(state == PlayeState.SELECT){
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 215, 240);
         g.drawString("(1) MOVE", posX+8, posY+25);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 240, 265);
         g.drawString("(2) SEARCH", posX+8, posY+50);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 265, 290);
         g.drawString("(3) SKIP TURN", posX+8, posY+75);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 290, 315);
         g.drawString("(esc) PAUSE", posX+8, posY+100);
      }
      else if(state == PlayeState.MOVE){
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 215, 240);
         g.drawString("(\u2190) LEFT", posX+8, posY+25);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 240, 265);
         g.drawString("(\u2192) RIGTH", posX+8, posY+50);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 265, 290);
         g.drawString("(\u2191) UP", posX+8, posY+75);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 290, 315);
         g.drawString("(\u2193) DOWN", posX+8, posY+100);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 315, 340);
         g.drawString("any other key to return", posX+8, posY+125);
      }
      else if(state == PlayeState.SEARCH){
         g.drawString("SEARCHING", posX+8, posY+25);
         chgColorOnHover(g, Color.BLACK, Color.RED, 530, 670, 240, 265);
         g.drawString("any key to return", posX+8, posY+50);
      }
   } 
   
   public void drawLife(Graphics2D g, int posX, int posY){
      
      for(int i=0; i<life; i++)
         g.drawImage(lifeIcon.getImage(), posX+(i%8)*20, posY+25+20*(i/8), 20, 20, null);
      
   } 
   
   public void drawInfo(Graphics2D g, int posX, int posY){
      
      //TODO
      
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
   
   
}