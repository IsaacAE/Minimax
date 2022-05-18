package edd.src.Estructuras;

public class Pruebas{

public static void main(String[] args){
    ArbolMiniMax m = new ArbolMiniMax();
    m.add(80);
    m.expandirArbol(m.raiz);
    m.calcularValor(m.raiz);
   // m.creaVertices(search(m.raiz, 80));
    System.out.println(m);

}

}