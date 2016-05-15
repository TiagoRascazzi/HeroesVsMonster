import java.util.ArrayList;
import java.util.Random;

public class WeightedRandom<anyType>{
   
   private ArrayList<anyType> items = new ArrayList<anyType>();
   private ArrayList<Double> weight = new ArrayList<Double>();
   private Random random;
   private double total;
   
   public WeightedRandom(){
      random = new Random();
   }
   
   public void add(double w, anyType x){
      items.add(x);
      weight.add(w);
      total += w;
   }
   
   public anyType next(){
      double rand = random.nextDouble();
      for(int i=1; i<items.size(); i++)
         if(rand < weight.get(i-1) && rand > weight.get(i))
            return items.get(i);
      return null;
   }
   
}