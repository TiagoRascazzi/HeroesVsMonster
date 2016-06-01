import java.awt.Point;

public class DoorCard extends ActionCard{
   
   //TODO the door is only added to the fourwayroom tile
   
   public DoorCard(int tID){
      super(tID);
   }
   public static DoorCard getRandom(Point p){
      WeightedRandom<DoorCard> wr = new WeightedRandom<DoorCard>();
      
      wr.add(1, new DoorJammed(p));   
      wr.add(1, new DoorOpens(p));
      wr.add(1, new DoorTrap(p)); 
 
      //wr.add(1, "DoorJammed");   
      //wr.add(1, "DoorOpens");
      //wr.add(1, "DoorTrap");
      
      return wr.next();  // return the random card 
   }
}