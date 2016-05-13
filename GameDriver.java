import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Dimension;

public class GameDriver{
   public static HVMPanel screen;
   
   public static void main(String[] args){
      screen = new HVMPanel();
      screen.setPreferredSize(new Dimension(900,650));
      JFrame frame = new JFrame("Heroes VS. Monsters");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(screen);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
	  
	  //System.out.println(frame.getContentPane().getSize());
	  //System.out.println(screen.getSize());
	  
      frame.addKeyListener(new Listen());
   }
   
   public static class Listen implements KeyListener{
      public void keyTyped(KeyEvent e){
      }
      public void keyPressed(KeyEvent e){
      }
      public void keyReleased(KeyEvent e){
         screen.processUserInput(e);
         //System.out.println(e.getKeyChar());
         
      }
   }
}