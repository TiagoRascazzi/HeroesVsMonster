import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class CryptCards extends ActionCard{
   
   public CryptCards(int tID){
      super(tID);
   }
   
   public static CryptCards getRandom(){
      WeightedRandom<CryptCards> wr = new WeightedRandom<CryptCards>();
           
      wr.add(1, new BraceletCrypt());
      wr.add(1, new BroochCrypt());
      wr.add(4, new EmptyCrypt());
      wr.add(1, new GoldenGuineasCrypt());
      wr.add(1, new JewelledDaggerCrypt());
      wr.add(3, new PotionCrypt());
      //wr.add(1, new ShuffleTheDeck());
      wr.add(2, new SkeletonCrypt());
      wr.add(2, new TrapCrypt());
      
      return wr.next();  // return the random card 
     
   }
   
}