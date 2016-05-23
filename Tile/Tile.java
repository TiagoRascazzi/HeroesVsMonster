import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Tile{
   
   public static final int TOP = 0;
   public static final int BOTTOM = 1;
   public static final int LEFT = 2;
   public static final int RIGHT = 3; 
   
   private boolean leftSide;
   private boolean rightSide;
   private boolean topSide;
   private boolean bottomSide;
   
   private int orientation;
   private int textureID;
   private int maxNumOfPlayers;
   
   private boolean keepPlaying;
   private boolean giveRoomCard;
   
   public Tile(int tID, int mnop, boolean kp, boolean grc, boolean ls, boolean rs, boolean ts, boolean bs){
      orientation = LEFT;

      textureID = tID;
      maxNumOfPlayers = mnop;
      keepPlaying = kp;
      giveRoomCard = grc;
      leftSide = ls;
      rightSide = rs;
      topSide = ts;
      bottomSide = bs;
   }
   public Tile(int orien, int tID, int mnop, boolean kp, boolean grc, boolean ls, boolean rs, boolean ts, boolean bs){
      textureID = tID;
      maxNumOfPlayers = mnop;
      keepPlaying = kp;
      giveRoomCard = grc;
      leftSide = ls;
      rightSide = rs;
      topSide = ts;
      bottomSide = bs;
      setOrientation(orien);
   }
   
   public int getMaxNumOfPlayers(){
      return maxNumOfPlayers;
   }
   public int getTextureID(){
      return textureID;
   } 
   
   public void setOrientation(int orien){ 
      //System.out.println("Left: "+isLeftSideOpen());
      //System.out.println("Right: "+isRightSideOpen());
      //System.out.println("Top: "+isTopSideOpen());
      //System.out.println("Bottom: "+isBottomSideOpen());        
      if(orien == TOP){
         boolean tmp1 = leftSide;
         boolean tmp2 = rightSide;
        
         leftSide = bottomSide;
         rightSide = topSide;
         topSide = tmp1;
         bottomSide = tmp2;          
                
      }else if(orien == BOTTOM){
         boolean tmp1 = leftSide;
         boolean tmp2 = rightSide;
        
         leftSide = topSide;
         rightSide = bottomSide;
         topSide = tmp2;
         bottomSide = tmp1;
        
      }else if(orien == LEFT){
       //DO NOTHING
      }else if(orien == RIGHT){
         boolean tmp1 = leftSide;
         boolean tmp2 = topSide;
        
         leftSide = rightSide;
         rightSide = tmp1;
         topSide = bottomSide;
         bottomSide = tmp2;
        
      }
      orientation = orien;
   } 
  
   public double getRotation(){
      if(orientation == TOP)
         return Math.toRadians(90);
      if(orientation == BOTTOM)
         return Math.toRadians(270);
      if(orientation == LEFT)
         return 0;
      if(orientation == RIGHT)
         return Math.toRadians(180);
      return -1;
   }
   
   public static Tile getRandomTile(){ 
      WeightedRandom<Tile> wr = new WeightedRandom<Tile>();
      wr.add(2, new Corridors());
      //wr.add(1, new TestTile());
      wr.add(4, new OneWayCorridor());
      wr.add(8, new ThreeWayCorridors());
      wr.add(2, new BottomLessPit());
      wr.add(2, new FourWayPit());
      wr.add(2, new ThreeWayTrap());
      wr.add(3, new FourWayTrap());
      wr.add(2, new TwoWayDarkRoom());
      wr.add(2, new ThreeWayDarkRoom());
      wr.add(5, new OneWayEmptyRoom());
      wr.add(15, new FourWayEmptyRoom());
      wr.add(5, new RotatingRoom());
      return wr.next();
   }
   
   public boolean keepsPlaying(){
      return keepPlaying;
   }
   public boolean givesRoomCard(){
      return giveRoomCard;
   }
   public boolean isLeftSideOpen(){
      return leftSide;
   }
   public boolean isRightSideOpen(){
      return rightSide;
   }
   public boolean isTopSideOpen(){
      return topSide;
   }
   public boolean isBottomSideOpen(){
      return bottomSide;
   }
  
}