import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GUI{

   private static Point mouse;
   
   public static void update(Point m){
      mouse = m;
   }
   
   public static boolean hover(int x1, int x2, int y1, int y2){
      return (mouse.x>x1 && mouse.x<x2 && mouse.y>y1 && mouse.y<y2);
   }
   public static void chgColorOnHover(Graphics2D g, Color c1, Color c2, int x1, int x2, int y1, int y2){
      g.setColor(c1);
      if(hover(x1, x2, y1, y2))
         g.setColor(c2);
   } 
}