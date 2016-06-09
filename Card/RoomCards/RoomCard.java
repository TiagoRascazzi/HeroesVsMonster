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
      
      wr.add(34, new EmptyRoom());         //DONE   
      wr.add(1, new CaveIn());            //DONE
      wr.add(1, new Jewellery());         //DONE
      wr.add(1, new Bracelet());          //DONE
      wr.add(1, new CrossfireTrap());     //DONE
      wr.add(3, new VampireBats());       //DONE
      wr.add(2, new Potion());            //DONE
      wr.add(3, new CurseOfTheWizard());  //DONE       
      wr.add(2, new TorchGoesOut());      //DONE 
      wr.add(6, new Crypt());             //DONE 
      
      //Monstercards                      //DONE
      wr.add(2, new ChampionOfChaos());   //DONE
      wr.add(3, new DeathWarrior());      //DONE
      wr.add(5, new Goblin());            //DONE
      wr.add(3, new MountainTroll());     //DONE
      wr.add(4, new Orc());               //DONE
      
      
      //Finish those one later       
      //wr.add(1, new DeadAdventurer());
      //wr.add(1, new GiantSpider());
      //wr.add(1, new SneakAttack());
      //wr.add(1, new TrapDoor());
      
      return wr.next();  // return the random card 
   }
}