import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;

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
   private boolean searchable;
   
   private boolean[]Doors = new boolean[4];
   
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
      searchable = true;
      Doors = new boolean[]{false, false, false, false};
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
      searchable = true;
      Doors = new boolean[]{false, false, false, false};
   }
   
   public void setPossibleDoor(boolean top, boolean rigth, boolean bottom, boolean left){
      Random random = new Random();
      int prob = 10;
      if(top && random.nextInt(prob) < 1)
         Doors[0] = true;
      if(rigth && random.nextInt(prob) < 1)
         Doors[1] = true;
      if(bottom && random.nextInt(prob) < 1)
         Doors[2] = true;
      if(left && random.nextInt(prob) < 1)
         Doors[3] = true;
   }
   
   public int getMaxNumOfPlayers(){
      return maxNumOfPlayers;
   }
   public int getTextureID(){
      return textureID;
   } 
   
   public void setOrientation(int orien){    
      
      //default orientation of picture
      orientation = LEFT;
       
      if(orien == TOP){ 
         rotate90DegClockwise();
      }else if(orien == RIGHT){ 
         rotate180DegClockwise();
       }else if(orien == BOTTOM){ 
         rotate270DegClockwise();
      }
   } 
   
   //deg is a multiple of 90
   public void rotateClockwise(int deg){
      
      if((deg/90)%4 == 0){        //0
         //Do nothing
      }else if((deg/90)%4 == 1){  //90
         rotate90DegClockwise();
      }else if((deg/90)%4 == 2){  //180
         rotate180DegClockwise();
      }else if((deg/90)%4 == 3){  //270
         rotate270DegClockwise();
      }
   }
   
   public void rotate90DegClockwise(){
      boolean tmp1 = leftSide;
      boolean tmp2 = rightSide;
     
      leftSide = bottomSide;
      rightSide = topSide;
      topSide = tmp1;
      bottomSide = tmp2; 
      
      orientation = getNextCwRotation(orientation);
   }
   
   public void rotate180DegClockwise(){
      boolean tmp1 = leftSide;
      boolean tmp2 = topSide;
      
      leftSide = rightSide;
      rightSide = tmp1;
      topSide = bottomSide;
      bottomSide = tmp2;
      
      orientation = getNextCwRotation(getNextCwRotation(orientation));
   }
   
   
   public void rotate270DegClockwise(){
      boolean tmp1 = leftSide;
      boolean tmp2 = rightSide;
     
      leftSide = topSide;
      rightSide = bottomSide;
      topSide = tmp2;
      bottomSide = tmp1;
      
      orientation = getNextCwRotation(getNextCwRotation(getNextCwRotation(orientation)));
   }
   
   public int getNextCwRotation(int orien){
      if(orien == TOP)
         return RIGHT;
      if(orien == BOTTOM)
         return LEFT;
      if(orien == LEFT)
         return TOP;
      if(orien == RIGHT)
         return BOTTOM;
      return -1;
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
      
      //for testing purpose not to change since we are not done doing the cards
      //wr.add(1, new TestTile());
      
      //wr.add(5, new RotatingRoom());
      //wr.add(2, new BottomLessPit());
      
      //wr.add(5, new EndEmptyRoom());
      //wr.add(1, new EndPortcullis());      
      
      wr.add(2, new OneWayCorridor());
      //wr.add(3, new OneWayPortcullis());
      
      //wr.add(15, new TwoWayEmptyRoom());
      //wr.add(2, new TwoWayDarkRoom());
      //wr.add(8, new TwoWayStraightEmptyRoom());
      wr.add(4, new TwoWayCorridor());
      
      wr.add(8, new ThreeWayCorridors());
      //wr.add(2, new ThreeWayTrap());
      //wr.add(2, new ThreeWayDarkRoom());
      //wr.add(30, new ThreeWayEmptyRoom());
      //wr.add(6, new ThreeWayPortcullis());
      
      wr.add(15, new FourWayEmptyRoom());
      //wr.add(2, new FourWayPit());
      //wr.add(3, new FourWayTrap());
      wr.add(2, new FourWayCorridors());
      
          
      return wr.next();
   }
   
   public boolean[]getDoors(){
      return Doors;
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
   public boolean isSearchable(){
      return searchable;
   }
   public void setSearchable(boolean b){
      searchable = b;
   }
  
}