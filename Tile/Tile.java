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
   
   public Tile(int tID, boolean kp, boolean grc, boolean ls, boolean rs, boolean ts, boolean bs){
      orientation = LEFT;

      textureID = tID;
      maxNumOfPlayers = 1;
      keepPlaying = kp;
      giveRoomCard = grc;
      leftSide = ls;
      rightSide = rs;
      topSide = ts;
      bottomSide = bs;
   }
   public Tile(int orien, int tID, boolean kp, boolean grc, boolean ls, boolean rs, boolean ts, boolean bs){
      textureID = tID;
      maxNumOfPlayers = 1;
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
      
      //wr.add(1, new TestTile());
      wr.add(1, new RotatingRoom());
      wr.add(1, new BottomLessPit());
      
      wr.add(1, new EndEmptyRoom());
      wr.add(1, new EndPortcullis());      
      
      wr.add(1, new OneWayCorridor());
      wr.add(1, new OneWayPortcullis());
      
      wr.add(1, new TwoWayEmptyRoom());
      wr.add(1, new TwoWayDarkRoom());
      
      wr.add(1, new ThreeWayCorridors());
      wr.add(1, new ThreeWayTrap());
      wr.add(1, new ThreeWayDarkRoom());
      wr.add(1, new ThreeWayEmptyRoom());
      wr.add(1, new ThreeWayPortcullis());
      
      wr.add(1, new FourWayEmptyRoom());
      wr.add(1, new FourWayPit());
      wr.add(1, new FourWayTrap());
      wr.add(1, new FourWayCorridors());
            
      return wr.next();
   }
   public void changeMaxNumOfPlayers(int mnop){
      maxNumOfPlayers = mnop;
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