import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameDriver{
   public static HVMPanel screen;
   
   public static void main(String[] args){
      screen = new HVMPanel();
      JFrame frame = new JFrame("Heroes VS. Monsters");
      frame.setSize(800, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(screen);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      frame.addKeyListener(new Listen());
   }
   
   public static class Listen implements KeyListener{
      public void keyTyped(KeyEvent e){
      }
      public void keyPressed(KeyEvent e){
      }
      public void keyReleased(KeyEvent e){
         screen.processUserInput(e);
      }
   }
}