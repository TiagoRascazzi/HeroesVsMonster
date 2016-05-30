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
      
      
      // Comment those to test the card while coding them one at the time
      /*
      wr.add(1, new EmptyRoom());         //DONE   
      wr.add(1, new CaveIn());            //DONE
      wr.add(1, new Jewellery());         //DONE
      wr.add(1, new Bracelet());          //DONE
      wr.add(1, new CrossfireTrap());     //DONE
      wr.add(1, new VampireBats());       //DONE
      wr.add(1, new Potion());            //DONE
      */
             
      //wr.add(1, new TorchGoesOut());      //not DONE 
      
      
      //Monstercards                      //not DONE working on them now
      wr.add(1, new ChampionOfChaos());   //not DONE
      //wr.add(1, new DeathWarrior());      //not DONE
      //wr.add(1, new Goblin());            //not DONE
      //wr.add(1, new MountainTroll());     //not DONE
      //wr.add(1, new Orc());               //not DONE
      
      
      //wr.add(1, new Crypt());           //Finish those one later
      //wr.add(1, new CurseOfTheWizard()); 
      //wr.add(1, new DeadAdventurer());
      //wr.add(1, new GiantSpider());
      //wr.add(1, new SneakAttack());
      //wr.add(1, new TrapDoor());
      
      return wr.next();  // return the random card 
   }
}