package edd.src.Estructuras;

public class Practica4 {
    public static void main(String[] args) {
      ArbolAVL miArbolito = new ArbolAVL();

      miArbolito.add(15);
     
      miArbolito.add(16);
     
      miArbolito.add(14);
    
     miArbolito.add(13);
     
      miArbolito.add(12);
      
      miArbolito.add(6);
    
      miArbolito.add(4);
     
      miArbolito.add(-2);
      miArbolito.delete(16);
      miArbolito.delete(4);
      miArbolito.delete(6);

      miArbolito.add(18);
    
      miArbolito.add(19);
   
   
        }


}