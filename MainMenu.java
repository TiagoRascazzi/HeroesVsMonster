import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
   
   public void draw(Graphics g){
      
      g.drawImage(mainImg.getImage(),0, 0, 800, 600, null);
      
      if(state == MainMenuState.SELECT){
         g.setColor(Color.RED);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
         g.drawString("Select the number of players", 50, 50);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
         g.drawString("(1) 1 Player", 50, 75); 
         g.drawString("(2) 2 Player", 50, 100); 
         g.drawString("(3) 3 Player", 50, 125); 
         g.drawString("(4) 4 Player", 50, 150);
      }
      else if(state == MainMenuState.CHOSE){
         g.setColor(Color.RED);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
         g.drawString("Select the hero for player #"+currentPlayer, 50, 50);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
         for(int i=0; i<availablePlayers.size(); i++)
            g.drawString("("+(i+1)+") "+availablePlayers.get(i).getName(), 50, 75+25*i); 
      }
   
   }
   
   public ArrayList<Player> processUserInput(KeyEvent e){
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
}