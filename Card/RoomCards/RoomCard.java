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
      //DO ONE A THE TIME
      
      
      // Those are done but commented out for testing purpose
      //wr.add(1, new EmptyRoom());         //DONE   
      wr.add(1, new CaveIn());            //DONE
      /*wr.add(1, new Jewellery());         //DONE
      wr.add(1, new Bracelet());          //DONE
      wr.add(1, new CrossfireTrap());     //DONE
      wr.add(1, new VampireBats());       //DONE
      */
      
      
      //wr.add(1, new Potion());
      //wr.add(1, new TorchGoesOut());
      
      //wr.add(1, new Crypt());
      //wr.add(1, new CurseOfTheWizard());  //Finish this one later
      //wr.add(1, new DeadAdventurer());
      //wr.add(1, new GiantSpider());
      //wr.add(1, new Monster());
      //wr.add(1, new SneakAttack());
      //wr.add(1, new TrapDoor());
      return wr.next();  // return the random card 
   }
}