import javax.swing.JFrame;
import java.awt.event.KeyListener;
import javax.swing.event.MouseInputListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.Toolkit;

public class GameDriver{
   public static HVMPanel screen;
   
   //Start of the program setup the window create panel
   public static void main(String[] args){
      ImageIcon icon = new ImageIcon("Img/icon.png");
      screen = new HVMPanel();
      screen.setPreferredSize(new Dimension(800,650));
      JFrame frame = new JFrame("Heroes VS. Monsters");
      screen.setCursor(Toolkit.getDefaultToolkit().createCustomCursor( new ImageIcon("Img/cursor/normal.png").getImage(), new Point(0,0),"custom cursor"));
      frame.setIconImage(icon.getImage());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(screen);
      frame.pack();
      frame.setResizable(true);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      Listener listener = new Listener();
      frame.addKeyListener(listener);
      frame.addMouseListener(listener);
      frame.addMouseMotionListener(listener);
      
   }
   
   //The listener it listen for the key and mouse
   public static class Listener implements KeyListener, MouseInputListener{
      public void keyTyped(KeyEvent e){}
      public void keyPressed(KeyEvent e){}
      public void mousePressed(MouseEvent e){}
      public void mouseClicked(MouseEvent e){}
      public void mouseEntered(MouseEvent e){}
      public void mouseExited(MouseEvent e){}
      public void mouseDragged(MouseEvent e){}
      public void keyReleased(KeyEvent e){
         screen.processKeyInput(e);         
      }
      public void mouseReleased(MouseEvent e){
         screen.processMouseInput(e);
      }
      public void mouseMoved(MouseEvent e){
         screen.processMouseInput(e);
      }
   }
}