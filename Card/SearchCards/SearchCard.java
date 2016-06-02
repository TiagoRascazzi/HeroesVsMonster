import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class SearchCard extends ActionCard{
   
   public SearchCard(int tID){
      super(tID);
   }
   
   public static SearchCard getRandom(){
      WeightedRandom<SearchCard> wr = new WeightedRandom<SearchCard>();
      //DO ONE A THE TIME
      wr.add(1, new EmptySearch());
      
      //DONE
      wr.add(1, new PotionSearch());
      wr.add(1, new GiantCentipedeSearch());
      
      //DONE BUT NEED TO INSERT CORRECT VALUE
      wr.add(1, new RingSearch());
      wr.add(1, new JewellerySearch());
      wr.add(1, new GoldenGuineasSearch());
      
      //not DONE
      wr.add(1, new SecretDoorSearch());
      
      //TODO
      //wr.add(1, new TrapSearch());
      
      return wr.next();  // return the random card 
   
   }
   
}