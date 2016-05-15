public abstract class Card{
   
   private double probability;
   private int textureID;
   
   public Card(double p, int tID){
      probability = p;
      textureID =  tID;
   }
   
   public int getTextureID(){
      return textureID;
   }
   
   public double getProbability(){
      return probability;
   }
   
}