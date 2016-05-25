import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

public abstract class RoomCard extends ActionCard{
      
   public RoomCard(int tID){
      super(tID);
   }
   
   public static RoomCard getRandom(){
      WeightedRandom<RoomCard> wr = new WeightedRandom<RoomCard>();
      wr.add(34, new EmptyRoom());
      wr.add(1, new Bracelet());
      //wr.add(1, new CaveIn());
      wr.add(1, new Jewellery());
      wr.add(1, new CrossfireTrap());
      wr.add(6, new Crypt());
      //wr.add(1, new CurseOfTheWizard());
      wr.add(5, new DeadAdventurer());
      //wr.add(1, new GiantSpider());
      //wr.add(1, new Monster());
      //wr.add(1, new Potion());
      wr.add(5, new SneakAttack());
      wr.add(3, new TorchGoesOut());
      //wr.add(1, new TrapDoor());
      wr.add(3, new VampireBats());
      return wr.next();  // return the random card 
   }
}