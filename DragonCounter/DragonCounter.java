public class DragonCounter{
   
   private static int total;
   
   public static String getRandom(){
      WeightedRandom<String> wr = new WeightedRandom<String>();
      wr.add(Math.max(total--, 1), "DragonSleeping");
      wr.add(1, "DragonWakesUp");
      return wr.next();
   }
   
   public static void setTotal(int t){
      total = t;
   }
}   