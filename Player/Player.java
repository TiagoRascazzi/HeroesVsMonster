public abstract class Player{
   
   private String name;
   private int posX;
   private int posY;
   private int life;
   
   //Charracteristic
   private int strength;
   private int agility;
   private int armor;
   private int luck;
   
   //playerNum is between 1-4
   public Player(String n, int x, int y, int maxLife, int strength, int agility, int armor, int luck){
      name = n;
      
      posX = x;
      posY = y;
      life = maxLife;
      
      this.strength = strength;
      this.agility = agility;
      this.armor = armor;
      this.luck = luck;
   }
   
   public void playTurn(){
      //if player dont miss turn (reason to skip: insructed from card, no possible move, voluntary)
         //then get what user want to do  only one at the time
            //move to valid pos
            //search room if searchable
         
   }
   
   public void move(){
      //if valid move and not other players are in it
         //if room had no tile then get new tile
   }
   
   public void search(){
      //get a card from search deck if room wasnt search more then twice consecusively
   }
   
   public String getName(){
      return name;
   }
   
}