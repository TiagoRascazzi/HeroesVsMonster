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
      
      wr.add(6, new EmptySearch());          //DONE
      wr.add(1, new PotionSearch());         //DONE
      wr.add(1, new GiantCentipedeSearch()); //DONE
      wr.add(1, new RingSearch());           //DONE
      wr.add(1, new JewellerySearch());      //DONE
      wr.add(1, new GoldenGuineasSearch());  //DONE
      
      //not DONE
      //wr.add(1, new SecretDoorSearch());
      //wr.add(1, new TrapSearch());
      
      return wr.next();  // return the random card 
   
   }
   
}