public class Cell<anyType>{
   
   private int posX;
   private int posY;
   private int key;
   private anyType value;
   
   public Cell(int x, int y, anyType v, int k){
      posX = x;
      posY = y;
      key = k;
      value = v;
   }
   
   public anyType setVal(anyType v){
      anyType oldVal = value;
      value = v;
      return oldVal;
   }
   
   public anyType getVal(){
      return value;
   }
   
   public int getKey(){
      return key;
   }
   
   public String toString(){
      return value+" at ("+posX+", "+posY+") with key: "+key;
   }
   
}