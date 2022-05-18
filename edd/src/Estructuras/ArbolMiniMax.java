package edd.src.Estructuras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArbolMiniMax<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
    */
    protected class VerticeMinimax extends Vertice {
        public int color;
        public int valor;
        public boolean visitado;

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

        

     /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * 
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
       // @Override
       /* public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeMinimax vertice = (VerticeMinimax) o;
            return color == vertice.color && super.equals(o);
        }*/

    }

    public ArbolMiniMax() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  rojinegro.
     */
    public ArbolMiniMax(Collection<T> coleccion) {
        //super(coleccion);
    }

    public int colorear(Vertice a){
        VerticeMinimax b = convertirMiniMax(a);
        if(this.raiz==a){
            return 3;
        }else{
            VerticeMinimax c = convertirMiniMax(a.padre);
            if(c.color==0 ){
                return 1;
            }else if(c.color==1||c.color==3){
                return 0;
            }else{
                return 10;
            }
        }
    }

    public void creaVertices(Vertice v){
       VerticeMinimax vi = convertirMiniMax(v);
        vi.color= colorear(v);
        vi.izquierdo= nuevoVertice(v.get());
        vi.derecho= nuevoVertice(v.get());
        vi.izquierdo.padre=v;
        vi.derecho.padre=v;
         v=vi;
     vi.padre=v.padre;
    }



    public void expandirArbol(Vertice v){
       
        if(v.profundidad()<5){
            creaVertices(v);
            expandirArbol(v.izquierdo);
            expandirArbol(v.derecho);
        }
        VerticeMinimax j = convertirMiniMax(v);
        j.color=colorear(j);
        if(v.profundidad()>=5){
        j.valor= (int)(Math.random()*8);
        j.visitado=true;
        }


       // System.out.println(v.profundidad());
    }


public void calcularValor(Vertice v){
    VerticeMinimax k = convertirMiniMax(v);
    if(!k.visitado){
        calcularValor(v.izquierdo);
        calcularValor(v.derecho);
        VerticeMinimax ki = convertirMiniMax(k.izquierdo);
        VerticeMinimax kd = convertirMiniMax(k.derecho);

        if(k.color==0){
            k.valor= Math.max(ki.valor, kd.valor);
        }else if(k.color==1){
            k.valor=Math.min(ki.valor,kd.valor);
        }else{
            k.valor=-1;
        }
            k.visitado=true;
        
    }
}

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeMinimax(elemento);
    }

    protected VerticeMinimax convertirMiniMax(VerticeArbolBinario<T> vertice) {
        return (VerticeMinimax) vertice;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * 
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *                            VerticeRojinegro}.
     */
    public int getColor(VerticeArbolBinario<T> vertice) {
        VerticeMinimax aux = (VerticeMinimax) vertice;
        return aux.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void add(T elemento) {
        if (elemento != null) {
            super.add(elemento);

            // ToDo ... Conseguir el ultimoAgregado
            /*VerticeRojinegro v = convertirRojiNegro(ultimoAgregado);
            v.color = Color.ROJO;
            rebalancea(v);*/
        }

    }

    
    //tiene2hijos
    //hasAbuelo
    //getAbuelo


    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public boolean delete(T elemento) {
        // Caso 1
        // Caso 2
        // Caso 3
        return false;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    //@Override
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                "pueden girar a la izquierda " +
                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    //@Override
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                "pueden girar a la derecha " +
                "por el usuario.");
    }
}