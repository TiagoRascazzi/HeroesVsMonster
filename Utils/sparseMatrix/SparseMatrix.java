import java.util.*;
import java.lang.*;

public class SparseMatrix<anyType> implements Matrixable<anyType> {

   private ArrayList<Cell<anyType>> list; //stores the actual elements private
   int numElements;  //number of valid elements in the list 
   private int numRows, numCols; //logical dimensions 
   
   public SparseMatrix (int r, int c) {
      list = new ArrayList();
      numElements = 0;
      numRows = r;
      numCols = c;
   }
   
   //returns the element at row r, col c
   public anyType get(int r, int c){
      if(isValidLoc(r, c)){
         int key = r*numCols+c;
         for(int i=0; i<numElements; i++)
            if( list.get(i).getKey() == key)
               return list.get(i).getVal();
      }else{
         throw new IndexOutOfBoundsException();
      }     
      return null;
   }
   
   //changes element at (r,c), returns old value
   public anyType set(int r, int c, anyType x){
      if(isValidLoc(r, c)){
         int key = r*numCols+c;
         for(int i=0; i<numElements; i++)
            if( list.get(i).getKey() == key)
               return list.get(i).setVal(x);
      }else{
         throw new IndexOutOfBoundsException(errorMsg("set", x, r, c));
      }
      
      return null;
   }
   
   //adds obj at row r, col c
   public void add(int r, int c, anyType x){
      if(isValidLoc(r, c)){
         int key = r*numCols+c;
         list.add(new Cell(r, c, x, key));
         numElements++;
      }else{
         throw new IndexOutOfBoundsException(errorMsg("add", x, r, c));
      }
   }
   
   //remove obj at row r, col c, returns old value
   public anyType remove(int r, int c){
      if(isValidLoc(r, c)){
         int key = r*numCols+c;
         for(int i=0; i<numElements; i++){
            if( list.get(i).getKey() == key){
               numElements--;
               return (list.remove(i)).getVal();
            }
         }
      }else{
         throw new IndexOutOfBoundsException();
      }     
      return null;
   }
   
   //returns # actual elements stored
   public int size(){
      return numElements;
   }
   
   //returns # rows set in constructor
   public int numRows(){
      return numRows;
   }	
   
   //returns # cols set in constructor
   public int numColumns(){
      return numCols;
   }	
   
   public boolean isValidLoc(int r, int c){
      if(r<numRows && c<numCols && r>=0 && c>=0)
         return true;
      return false;
   }
   
   public String errorMsg(String action, anyType obj, int r, int c){
      return "\nTried to "+action+" a \""+obj.getClass().getName()+"\"\t\n of value: "+obj+"\t\n at location: ("+r+", "+c+")";
   }
   
   /*public String toString(){
      String s = "";
      for(int i=0; i<list.size(); i++)
         s += list.get(i)+"\n";
      return s;
   }*/
   public String toString(){
      String s = "";
      for(int i=0; i<numRows; i++){
         for(int j=0; j<numCols; j++)
               System.out.print(this.get(i, j)!=null? this.get(i, j):'-');
         System.out.println("");
      }
      
      //show list in order
      //for(int i=0; i<list.size(); i++)
      //   s += list.get(i)+" at list index: "+i+"\n";
      
      return s;
   }
   
}