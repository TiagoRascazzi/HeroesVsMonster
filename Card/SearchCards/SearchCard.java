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
      wr.add(1, new GiantCentipedeSearch());
      wr.add(1, new PotionSearch());
      wr.add(1, new SecretDoorSearch());
      wr.add(1, new TrapSearch());
      wr.add(1, new TreasureSearch());
      //wr.add(1, new Empty());
      //wr.add(1, new GoldenGuineas());
      //wr.add(1, new Jewellery());
      //wr.add(1, new Potion());
      //wr.add(1, new Ring());
      //wr.add(1, new RubyBracelet());
      //wr.add(1, new SecretDoor());
      //wr.add(1, new ShuffleTheDeck());
      //wr.add(1, new Trap());
      return wr.next();  // return the random card 
   
   }
   
}