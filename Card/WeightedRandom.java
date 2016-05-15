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
      double rand = random.nextDouble()*100;
      
      int lastPerc = 0;
      for(int i=0; i<weight.size(); i++){
         
         double currentPerc = weight.get(i)/total*100;
         
         if(lastPerc < rand && rand < currentPerc+lastPerc){
            return items.get(i);
         }
         lastPerc+=currentPerc;
         
      }
      return null;
   }
      
   public String toString(){
      String str = "";
      for(int i=0; i<items.size(); i++)
         str += items.get(i)+" -> "+ weight.get(i)+"\n";
      return str;
   }
   
   //Randomness tester
   /*public static void main(String[]args){
      
      WeightedRandom<Integer> wr = new WeightedRandom<Integer>();
      
      double w1 = 1;
      double w2 = 4;
      double w3 = 8;
      
      wr.add(w1, new Integer(1) );
      wr.add(w2, new Integer(2) );
      wr.add(w3, new Integer(3) );
      
      System.out.println(wr);
      
      String str = "";
      for(int i=0; i< 100; i++)
         str+=wr.next();
      
      int count = 0;
      for(int i=0; i<str.length(); i++)
         if(str.charAt(i) == '1' )
            count++;
      System.out.println("1: "+count);
      
      count = 0;
      for(int i=0; i<str.length(); i++)
         if(str.charAt(i) == '2')
            count++;
      System.out.println("2: "+count);
            count = 0;
      for(int i=0; i<str.length(); i++)
         if(str.charAt(i) == '3')
            count++;
      System.out.println("3: "+count); 
   }*/
}