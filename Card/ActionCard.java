public abstract class ActionCard{
   
   private double probability;
   private int textureID;
   
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
   
}