import javax.swing.JFrame;
import java.awt.event.KeyListener;
import javax.swing.event.MouseInputListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
      Listener listener = new Listener();
      frame.addKeyListener(listener);
      frame.addMouseListener(listener);
      frame.addMouseMotionListener(listener);
   }
   
   public static class Listener implements KeyListener, MouseInputListener{
      public void keyTyped(KeyEvent e){}
      public void keyPressed(KeyEvent e){}
      public void mousePressed(MouseEvent e){}
      public void mouseReleased(MouseEvent e){}
      public void mouseEntered(MouseEvent e){}
      public void mouseExited(MouseEvent e){}
      public void mouseDragged(MouseEvent e){}
      public void keyReleased(KeyEvent e){
         screen.processKeyInput(e);         
      }
      public void mouseClicked(MouseEvent e){
         screen.processMouseInput(e);
      }
      public void mouseMoved(MouseEvent e){
         screen.processMouseInput(e);
      }
   }
}