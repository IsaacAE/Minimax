package edd.src.Estructuras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArbolMiniMax<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    /**
     * Clase interna protegida para vértices de árboles MiniMax. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice, un valor y un booleano.
    */
    protected class VerticeMinimax extends Vertice {
        public int color;
        public int valor;
        public boolean visitado;

        /**
         * Constructor con parametros de la clase
         * @param elemento elemento que estara en el vertice
         */
        public VerticeMinimax(T elemento){
            super(elemento);
            color=-1; 
            valor=-1;
            visitado=false;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * 
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if (this == null){
                return "";
            }
            if (this.color == 1){
                return "R{" + this.elemento.toString() + "} C:"+this.color+" V:"+this.valor + " "+this.visitado;
            }else if(color==0){
            return "A{" + this.elemento.toString() + "}C:"+this.color+" V:"+this.valor+ " "+this.visitado;
            }else if(color==3){
                return "B{" + this.elemento.toString() + "}C:"+this.color+" V:"+this.valor+ " "+this.visitado;
            }else{
                return "J{" + this.elemento.toString() + "}C:"+this.color+" V:"+this.valor+" "+ this.visitado;
            }
        }

        


    }

    /**
     * COnstructor sin parametros de la clase
     */
    public ArbolMiniMax() {
        super();
    }

    /**
     * Construye un árbol MinMax a partir de una colección. El árbol
     * MiniMax tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  rojinegro.
     */
    public ArbolMiniMax(Collection<T> coleccion) {
        
    }

    /**
     * Metodo que otorga un valor al color del nodo respecto al valor del color de su padre
     * @param a vertice que deseamos "colorear"
     * @param j valor del color respecto al que lo colorearemos
     * @return int
     */
    public int colorear(Vertice a, int j){
        int k;
        if(j==0){
            k=1;
        }else{
            k=0;
        }
        VerticeMinimax b = convertirMiniMax(a);
        if(this.raiz==a){
            return 3;
        }else{
            VerticeMinimax c = convertirMiniMax(a.padre);
            if(c.color==k ||c.color==3){
                return j;
            }else if(c.color==j){
                return k;
            }else{
                return 10;
            }
        }
    }


/**
 * Metodo para calcular el valor del nodo utilizando el algoritmo de MinMax
 * @param v vertice del cual se quiere calcular el valor
 * @param i color respecto al cual se calculara el valor
 */
public void calcularValor(Vertice v,int i){
    VerticeMinimax k = convertirMiniMax(v);
    VerticeMinimax kd=null;
    VerticeMinimax ki=null;
    if(!k.visitado){
        if(v.hayIzquierdo()){
        calcularValor(v.izquierdo,i);
       
       ki = convertirMiniMax(k.izquierdo);
        }
         
        if(v.hayDerecho()){
        calcularValor(v.derecho,i);
        kd = convertirMiniMax(k.derecho);
        }
       
       
        if(k.hayDerecho()){
        if(k.color==i && k.color!=3){
            k.valor= Math.max(ki.valor, kd.valor);
        }else if(k.color!=3){
            k.valor=Math.min(ki.valor,kd.valor);
        }
    }else if(k.hayIzquierdo()){
        int dev =ki.valor;
        k.valor=dev;
    }
            k.visitado=true;
        
    }
}

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeMinimax}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice Minimax con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeMinimax(elemento);
    }

    /**
     * Transforma un vertice de la clase VerticeArbolBinario en uns instancia de vertice Minimax
     * @param vertice vertice a convertir
     * @return vertice
     */
    protected VerticeMinimax convertirMiniMax(VerticeArbolBinario<T> vertice) {
        return (VerticeMinimax) vertice;
    }

    /**
     * Regresa el color del vértice.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice 
     */
    public int getColor(VerticeArbolBinario<T> vertice) {
        VerticeMinimax aux = (VerticeMinimax) vertice;
        return aux.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void add(T elemento) {
        if (elemento != null) {
            super.add(elemento);

        }

    }

    
   


    /**
     * Elimina un elemento del árbol. 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public boolean delete(T elemento) {
        return false;
    }

  
}