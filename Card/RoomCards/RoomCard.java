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
      
      //wr.add(1, new EmptyRoom());         //DONE   
      //wr.add(1, new CaveIn());            //DONE
     // wr.add(1, new Jewellery());         //DONE
      //wr.add(1, new Bracelet());          //DONE
     // wr.add(1, new CrossfireTrap());     //DONE
     // wr.add(1, new VampireBats());       //DONE
      //wr.add(1, new Potion());            //DONE
     // wr.add(1, new CurseOfTheWizard());  //DONE
      
      //Monstercards                      //DONE
     // wr.add(1, new ChampionOfChaos());   //DONE
     // wr.add(1, new DeathWarrior());      //DONE
    //  wr.add(1, new Goblin());            //DONE
    //  wr.add(1, new MountainTroll());     //DONE
//wr.add(1, new Orc());               //DONE
        
        
      wr.add(1, new TorchGoesOut());      //not DONE 
      
      
      //Finish those one later
      
      //wr.add(1, new Crypt());           
      //wr.add(1, new DeadAdventurer());
      
      //wr.add(1, new GiantSpider());
      //wr.add(1, new SneakAttack());
      //wr.add(1, new TrapDoor());
      
      return wr.next();  // return the random card 
   }
}