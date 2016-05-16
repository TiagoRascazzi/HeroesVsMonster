public abstract class ActionCard{
   
   private double probability;
   private int textureID;
   private static WeightedRandom wr;
   
   public ActionCard(double p, int tID){
      probability = p;
      textureID =  tID;
   }
   
   public int getTextureID(){
      return textureID;
   }
   
   public double getProbability(){
      return probability;
   }
   
   public void doAction(){}
   public abstract ActionCard getRandom();
   
}