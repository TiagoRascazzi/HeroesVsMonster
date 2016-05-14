import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

public class MainMenu{

   private static ImageIcon mainImg;
   private enum MainMenuState {SELECT, CHOSE};
   private MainMenuState state;
   private int playerSelecting;
   private int numberOfPlayer;
   private int currentPlayer;
   public ArrayList<Player> players = new ArrayList<Player>();
   ArrayList<Player> availablePlayers = new ArrayList<Player>();
   
   public MainMenu(String url){  
      mainImg= new ImageIcon("Img/main.jpg");
      state = MainMenuState.SELECT;
      playerSelecting = 1;
      
      availablePlayers.add(new SirRohan());
      availablePlayers.add(new ElAdoranSureshot());
      availablePlayers.add(new UlvGrimhand());
      availablePlayers.add(new VolrikTheBrave());
   
   }
   
   public void draw(Graphics2D g, int screenWidth, int screenHeight){
      
      g.drawImage(mainImg.getImage(),0, 0, screenWidth, screenHeight, null);
      
      if(state == MainMenuState.SELECT){
         g.setColor(Color.RED);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
         g.drawString("Select the number of players", 50, 50);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
         for(int i=0; i<4; i++){
            g.setColor(Color.RED);
            if(hover(55, 130, 90+25*i, 90+25*(i+1)))
               g.setColor(Color.WHITE);
            g.drawString("("+(i+1)+") "+(i+1)+" Player", 50, 75+(25*i)); 
         }
      }
      else if(state == MainMenuState.CHOSE){
         g.setColor(Color.RED);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
         g.drawString("Select the hero for player #"+currentPlayer, 50, 50);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
         for(int i=0; i<availablePlayers.size(); i++){
            g.setColor(Color.RED);
            if(hover(55, 130, 90+25*i, 90+25*(i+1)))
               g.setColor(Color.WHITE);
            g.drawString("("+(i+1)+") "+availablePlayers.get(i).getName(), 50, 75+25*i);
         } 
      }
   
   }
   
   public ArrayList<Player> processKeyInput(KeyEvent e){
      if(state == MainMenuState.SELECT){
         for(int i = 0; i < 4; i++){
            if(e.getKeyCode() ==(49+i)){
               numberOfPlayer = i+1;
               currentPlayer = 1;
               state = MainMenuState.CHOSE;
            }
         }
      }else if(state == MainMenuState.CHOSE){
         for(int i = 0; i < 4; i++){
            if(e.getKeyCode() == (49+i) && availablePlayers.size()>=(i+1)){
               players.add(availablePlayers.remove(i));
               if(currentPlayer<numberOfPlayer)
                  currentPlayer++;
               else
                  return players;
            }
         }
      }
      return null;   
   }
   
   public ArrayList<Player> processMouseInput(MouseEvent e){
      if(hover(100, 230, 205, 240))
         System.out.println("hover");
         
      if(state == MainMenuState.SELECT){
         for(int i = 0; i < 4; i++){
            if(e.getButton() == MouseEvent.BUTTON1){
               if(hover(55, 130, 90+25*i, 90+25*(i+1))){
                  numberOfPlayer = i+1;
                  currentPlayer = 1;
                  state = MainMenuState.CHOSE;
               }
            }
         }
      }else if(state == MainMenuState.CHOSE){
         for(int i = 0; i < 4; i++){
            if(e.getButton() == MouseEvent.BUTTON1){
               if(hover(55, 130, 90+25*i, 90+25*(i+1))){
                  players.add(availablePlayers.remove(i));
                  if(currentPlayer<numberOfPlayer)
                     currentPlayer++;
                  else
                     return players;
               }
            }
         }
      }
      return null;   
   }
   
   private boolean hover(int x1, int x2, int y1, int y2){
      return (HVMPanel.mouse.x>x1 && HVMPanel.mouse.x<x2 && HVMPanel.mouse.y>y1 && HVMPanel.mouse.y<y2);
   }
}