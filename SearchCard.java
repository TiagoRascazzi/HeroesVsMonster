import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class SearchCard extends ActionCard{
   
   public SearchCard(int tID){
      super(tID);
   }
   
   public SearchCard getRandom(){
      WeightedRandom<RoomCard> wr = new WeightedRandom<RoomCard>();
      //DO ONE A THE TIME
      wr.add(1, new Empty());
      wr.add(1, new GoldenGuineas());
      wr.add(1, new Jewellery());
      wr.add(1, new Potion());
      wr.add(1, new Ring());
      wr.add(1, new RubyBracelet());
      wr.add(1, new SecretDoor());
      wr.add(1, new ShuffleTheDeck());
      wr.add(1, new Trap());
      return wr.next();  // return the random card 
   
   }
   
}