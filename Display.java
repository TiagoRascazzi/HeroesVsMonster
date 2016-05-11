import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class Display extends HVMPanel{
   
   private static ImageIcon startImg = new ImageIcon("Img/start.jpg");   
   
   public static void loadImages(){
      
   }
   
   public static void drawGame(Graphics g){
      System.out.println(gameState);
      if(gameState == GameState.START){
         drawStart(g);
      }else if(gameState == GameState.MAIN){
         drawMainMenu(g);
      }else if(gameState == GameState.GAME){
         drawBoard(g);
         drawSidebar(g);
      }else if(gameState == GameState.PAUSE){
         drawBoard(g);
         drawSidebar(g);
         drawPauseMenu(g);
      }
   }
   
   public static void drawStart(Graphics g){
      g.drawImage(startImg.getImage(),0, 0, 800, 600, null);
   }
   public static void drawMainMenu(Graphics g){
      g.setColor(Color.GREEN);
      g.fillRect(0, 0, 800, 600);
      
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      g.drawString("Menu", 50, 50);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(1) START", 75, 100);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(?) SETTING", 75, 125);
   }
   public static void drawBoard(Graphics g){
      g.setColor(Color.BLUE);
      g.fillRect(0, 0, 600, 600); 
      
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      g.drawString("BOARD", 50, 50);
   }
   public static void drawSidebar(Graphics g){
      g.setColor(Color.RED);
      g.fillRect(600, 0, 800, 600); 
       
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      g.drawString("INFO", 625, 50);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("Sun track", 625, 75);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(esc) PAUSE", 625, 125);
   }
   public static void drawPauseMenu(Graphics g){
      g.setColor(new Color(125, 125, 125, 225));
      g.fillRect(0, 0, 800, 600);  
      
      g.setColor(Color.BLACK);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
      g.drawString("PAUSE", 50, 50);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(esc) RESUME", 75, 100);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
      g.drawString("(q) QUIT", 75, 125);
   }
   
}