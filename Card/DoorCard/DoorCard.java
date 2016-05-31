public class DoorCard extends ActionCard{
   
   public DoorCard(int tID){
      super(tID);
   }
   public static DoorCard getRandom(){
      WeightedRandom<DoorCard> wr = new WeightedRandom<DoorCard>();
      
      wr.add(1, new DoorJammed());   
      wr.add(1, new DoorOpens());
      wr.add(1, new DoorTrap());
      
      return wr.next();  // return the random card 
   }
}