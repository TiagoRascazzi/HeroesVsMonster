import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;


public abstract class Tile{
   protected enum CardState {SHOW, ACTION, OPT1, OPT2, OPT3, OPT4, OPT5};
   protected CardState cardState;
   protected boolean active = false;
   
   public static final int TOP = 0;
   public static final int BOTTOM = 1;
   public static final int LEFT = 2;
   public static final int RIGHT = 3; 
  
   private int orientation;
   private int textureID;
   private int maxNumOfPlayers;
   
   private boolean keepPlaying;
   private boolean giveRoomCard;
   private boolean searchable;
   
   private boolean[]Side = new boolean[4];
   private boolean[]Doors = new boolean[4];
   private boolean[]OneWay = new boolean[4];
   
   public Tile(int tID, boolean kp, boolean grc, boolean ls, boolean rs, boolean ts, boolean bs){
      orientation = LEFT;

      textureID = tID;
      maxNumOfPlayers = 1;
      keepPlaying = kp;
      giveRoomCard = grc;
      searchable = true;
      Side = new boolean[]{ts, rs, bs, ls};
      Doors = new boolean[]{false, false, false, false};
      OneWay = new boolean[]{false, false, false, false};
   }
   public Tile(int orien, int tID, boolean kp, boolean grc, boolean ls, boolean rs, boolean ts, boolean bs){
      textureID = tID;
      maxNumOfPlayers = 1;
      keepPlaying = kp;
      giveRoomCard = grc;
      searchable = true;
      
      Side = new boolean[]{ts, rs, bs, ls};
      Doors = new boolean[]{false, false, false, false};
      OneWay = new boolean[]{false, false, false, false};
      
      setOrientation(orien);
   }
   
   public void setOneWay(boolean top, boolean rigth, boolean bottom, boolean left){
      OneWay[0] = top;
      OneWay[1] = rigth;
      OneWay[2] = bottom;
      OneWay[3] = left;
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
      rotateArr90Deg(Side);
      rotateArr90Deg(Doors);
      rotateArr90Deg(OneWay);
      
      orientation = getNextCwRotation(orientation);
   }
   public void rotateArr90Deg(boolean[]arr){
      boolean tmp1 = arr[3];
      boolean tmp2 = arr[1];
      arr[3] = arr[2];
      arr[1] = arr[0];
      arr[0] = tmp1;
      arr[2] = tmp2;
   }
   
   public void rotate180DegClockwise(){
      rotateArr180Deg(Side);
      rotateArr180Deg(Doors);
      rotateArr180Deg(OneWay);
      
      orientation = getNextCwRotation(getNextCwRotation(orientation));
   }
   public void rotateArr180Deg(boolean[]arr){
      boolean tmp1 = arr[3];
      boolean tmp2 = arr[0];
      arr[3] = arr[1];
      arr[1] = tmp1;
      arr[0] = arr[2];
      arr[2] = tmp2;
   }
   
   public void rotate270DegClockwise(){
      rotateArr270Deg(Side);
      rotateArr270Deg(Doors);
      rotateArr270Deg(OneWay);
      
      orientation = getNextCwRotation(getNextCwRotation(getNextCwRotation(orientation)));
   }
   public void rotateArr270Deg(boolean[]arr){
      boolean tmp1 = arr[3];
      boolean tmp2 = arr[1];
      arr[3] = arr[0];
      arr[1] = arr[2];
      arr[0] = tmp2;
      arr[2] = tmp1; 
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
      
     wr.add(5, new RotatingRoom());
      wr.add(2, new BottomLessPit());
      
      wr.add(5, new EndEmptyRoom());
      wr.add(1, new EndPortcullis());      
      
      wr.add(2, new OneWayCorridor());
      wr.add(3, new OneWayPortcullis());
      
      wr.add(15, new TwoWayEmptyRoom());
      wr.add(2, new TwoWayDarkRoom());
      wr.add(8, new TwoWayStraightEmptyRoom());
      wr.add(4, new TwoWayCorridor());
      
      wr.add(8, new ThreeWayCorridors());
      wr.add(2, new ThreeWayTrap());
      wr.add(2, new ThreeWayDarkRoom());
      wr.add(30, new ThreeWayEmptyRoom());
      wr.add(6, new ThreeWayPortcullis());
      
      wr.add(15, new FourWayEmptyRoom());
      wr.add(2, new FourWayPit());
      wr.add(3, new FourWayTrap());
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
   
   public boolean getOneWayTop(){
      return OneWay[0];
   }
   public boolean getOneWayRigth(){
      return OneWay[1];
   }
   public boolean getOneWayBottom(){
      return OneWay[2];
   }
   public boolean getOneWayLeft(){
      return OneWay[3];
   }
   
   public boolean isLeftSideOpen(){
      return Side[3];
   }
   public boolean isRightSideOpen(){
      return Side[1];
   }
   public boolean isTopSideOpen(){
      return Side[0];
   }
   public boolean isBottomSideOpen(){
      return Side[2];
   }
   public boolean isSearchable(){
      return searchable;
   }
   public void setSearchable(boolean b){
      searchable = b;
   }
  
   public int getMaxNumOfPlayers(){
      return maxNumOfPlayers;
   }
   public int getTextureID(){
      return textureID;
   } 
   public void drawAction(Graphics2D g, int posX, int posY){}
   public ActionCard processKeyInput(KeyEvent e){
      if(cardState == CardState.SHOW){
         if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            cardState = CardState.ACTION;
         }
      }
      return null;
   }
   
   public ActionCard processMouseInput(Point screenSize, MouseEvent e){
      if(cardState == CardState.SHOW){
         if(GUI.hover((int)(screenSize.x/2)-25, (int)(screenSize.x/2)-25+75, (int)(screenSize.y-(3*screenSize.y/16))+10, (int)(screenSize.y-(3*screenSize.y/16))+30) ){
            cardState = CardState.ACTION;
         }
      }
      return null;
   }
}