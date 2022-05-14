/**
 * @Author Mauricio Rubio Haro
 * @Author Kevin Isaac Alcántara Estrada
 */
package edd.src.Estructuras;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {

  protected class VerticeAVL extends Vertice {

    public int altura;
    public VerticeAVL raiz;
    public VerticeAVL padreAVL = null;
    public VerticeAVL izquierdo = null;
    public VerticeAVL derecho = null;

    public VerticeAVL(T elemento) {
      super(elemento);
      altura = 0;
    }

    /**
     * Regresa una representación en cadena del vértice rojinegro.
     *
     * @return una representación en cadena del vértice rojinegro.
     */
    public String toString() {
      return super.toString() + " altura: " + this.altura;
    }

    /**
     * Compara el vértice con otro objeto. La comparación es
     * <em>recursiva</em>.
     *
     * @param o el objeto con el cual se comparará el vértice.
     * @return <code>true</code> si el objeto es instancia de la clase
     *         {@link VerticeAVL}, su elemento es igual al elemento de
     *         éste vértice, los descendientes de ambos son recursivamente
     *         iguales, y los colores son iguales; <code>false</code> en
     *         otro caso.
     */
    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      @SuppressWarnings("unchecked")
      VerticeAVL vertice = (VerticeAVL) o;
      return altura == vertice.altura && super.equals(o);
    }

    public int getAltura() {
      return this.altura;
    }

    public void setAltura() {
      this.altura = super.altura() - 1;
    }
  }

  /**
   * Metodo para convertir vertice a verticeAVL
   */
  protected VerticeAVL convertirAVL(VerticeArbolBinario<T> vertice) {
    return (VerticeAVL) vertice;
  }

  /**
   * Crear un verticeAVL
   */
  protected Vertice nuevoVertice(T elemento) {
    return new VerticeAVL(elemento);
  }

  /**
   * Metodo para actualizar las alturas de cada vertices del arlcol
   * @param vertice
   */
  public void actualizarAlturas(Vertice vertice) {
    VerticeAVL vert = convertirAVL(vertice);
    if (this.raiz == null) {
      System.out.println("No puedo trabajar con un árbol vacio");
      return;
    }

    vert.setAltura();

    if (vert.hayDerecho() || vert.hayIzquierdo()) {
      if (vert.hayDerecho()) {
        VerticeAVL vertD = convertirAVL(vertice.derecho);
        actualizarAlturas(vertD);
      }

      if (vert.hayIzquierdo()) {
        VerticeAVL vertI = convertirAVL(vertice.izquierdo);
        actualizarAlturas(vertI);
      }
    }
  }

  /**
   * Metodo para eliminar un vertice
   */
  public boolean delete(T elemento) {
    boolean k = super.delete(elemento);
    actualizarAlturas(this.raiz);
   // do{
      revisarBalance(this.raiz);
      actualizarAlturas(this.raiz);
      //desbalanceado=revisarB(this.raiz);
     // }while(desbalanceado==true);
     System.out.println(this);
    return k;
  }

  public void rotacionDerecha(Vertice vert){
    if(vert==null|| !vert.hayIzquierdo()){
      return;
    }

    Vertice aux = vert;
    aux.padre=vert.padre;
    System.out.println("aux.izq"+aux.izquierdo);
   
    Vertice vi = vert.izquierdo;
   
    vi.padre=aux.padre;
    if(aux!=this.raiz){
      if(aux.padre.hayIzquierdo()){
      if(aux.padre.izquierdo.get().compareTo(aux.elemento)==0){
        vi.padre.izquierdo=vi;
      }
    }else{
        vi.padre.derecho=vi;
      }
    }else{
      this.raiz=vi;
    }
    aux.izquierdo=vi.derecho;
    if(vi.hayDerecho()){
      vi.derecho.padre=aux;
    }
    if(vi.hayIzquierdo()){
     // vi.izquierdo.padre=aux;
    }
    aux.padre=vi;
    vi.derecho=aux;
    //aux2.padre=aux;
   // aux.izquierdo=aux2;
  }

  public void rotacionIzquierda(Vertice vert){
    if(vert==null|| !vert.hayDerecho()){
      return;
    }

    Vertice vertice = vert;
    Vertice verticed= vert.derecho;
    verticed.padre=vertice.padre;
    if(vertice!=this.raiz){
      if(verticed.padre.hayIzquierdo()){
      if(vertice.padre.izquierdo.get().compareTo(vertice.elemento)==0){
        verticed.padre.izquierdo=verticed;
      }
    }else{
        verticed.padre.derecho=verticed;
      }
    }else{
      this.raiz=verticed;
    }
    vertice.derecho=verticed.izquierdo;
   
    if(verticed.hayIzquierdo()){
      verticed.izquierdo.padre=vertice;
    }
    vertice.padre=verticed;
    verticed.izquierdo=vertice;
  }

  

  /**
   * Metodo para añadir elementos
   */
  public void add(T elemento) {
    Vertice verti = nuevoVertice(elemento);
    if (this.raiz == null) {
      this.raiz = verti;
    } else {
      super.insert(this.raiz, elemento);
     
    }

    actualizarAlturas(this.raiz);
    
   
    do{
     revisarBalance(this.raiz);//}
    actualizarAlturas(this.raiz);
    desbalanceado=revisarB(this.raiz);
    }while(desbalanceado==true);
    System.out.println(this);
  }

  /**
   * Desbalance Derecho
   * @param vert
   * @param hIzq
   * @param hDer
   */
  public void desbalanceDerecho(Vertice vert, int hIzq, int hDer) {
  
    int wd = 0;
    if (vert.hayDerecho()) {
      if (vert.derecho.hayDerecho()) {
        wd = vert.derecho.derecho.altura() - 1;
      } else {
        rotacionDerecha(vert.derecho);
      actualizarAlturas(this.raiz);
        rotacionIzquierda(vert);
        actualizarAlturas(this.raiz);
        return;
        
      }
    }else{
      
    }

    if (wd == hIzq + 1) {
      rotacionIzquierda(vert);
      actualizarAlturas(this.raiz);
    } else if (wd == hIzq) {
      if(vert.hayIzquierdo()){
        rotacionDerecha(vert.derecho);
        actualizarAlturas(this.raiz);
        rotacionIzquierda(vert);
        actualizarAlturas(this.raiz);
      revisarBalance(this.raiz);
      actualizarAlturas(this.raiz);
      }
    }else{
      if(vert.hayDerecho()){
      revisarBalance(vert.derecho);
      actualizarAlturas(this.raiz);
    }
    if(vert.hayIzquierdo()){
      revisarBalance(vert.izquierdo);
      actualizarAlturas(this.raiz);
    }
    }
  }

  /**
   * Analisis de donde hay que rebalancear
   * @param vert
   * @param hIzq
   * @param hDer
   */
  public void rebalancear(Vertice vert, int hIzq, int hDer) {
    if (hDer == hIzq + 2) {
      desbalanceDerecho(vert, hIzq, hDer);
      actualizarAlturas(this.raiz);
    } else if (hIzq == hDer + 2) {
      desbalanceIzquierda(vert, hIzq, hDer);
      actualizarAlturas(this.raiz);
    }
    actualizarAlturas(this.raiz);
  }

 

  

  /**
   * Metodo que revisa si esta desbalanceado desde arriba hacia abajo
   * @param verti
   * @return
   */
  public boolean revisarBalance(Vertice verti) {
    int izq = 0, der = 0;
  
    if (verti.hayIzquierdo()) {
      VerticeAVL vertI = convertirAVL(verti.izquierdo);
      vertI.setAltura();
      izq = vertI.altura;
    }
    
    if (verti.hayDerecho()) {
      VerticeAVL vertD = convertirAVL(verti.derecho);
      vertD.setAltura();
      der = vertD.altura;
    }

    int op = Math.abs(izq - der);
   
    if (Math.abs(izq - der) < 2) {
     
      if(verti.hayDerecho()){
       
      if(verti.altura()-1==2 &&  !verti.hayIzquierdo() && verti.derecho.altura()-1==1&& verti.derecho.hayDerecho()){
       
      
        rotacionIzquierda(verti);
        actualizarAlturas(this.raiz);
        
      
      }else if(verti.altura()-1==2 &&  !verti.hayIzquierdo() && verti.derecho.altura()-1==1&& !verti.derecho.hayDerecho()){
        rotacionDerecha(verti.derecho);
        actualizarAlturas(this.raiz);
        rotacionIzquierda(verti);
        actualizarAlturas(this.raiz);
    }
  }else if(verti.hayIzquierdo()){
    if(verti.altura()-1==2 &&  !verti.hayDerecho() && verti.izquierdo.altura()-1==1&& verti.izquierdo.hayIzquierdo()){
      rotacionDerecha(verti);
      actualizarAlturas(this.raiz);
    }else if(verti.altura()-1==2 &&  !verti.hayDerecho() && verti.izquierdo.altura()-1==1&& !verti.izquierdo.hayIzquierdo()){
      rotacionIzquierda(verti.izquierdo);
      actualizarAlturas(this.raiz);
      rotacionDerecha(verti);
      actualizarAlturas(this.raiz);
  }
  }
      
     

      if (verti.hayDerecho()) {
        revisarBalance(verti.derecho);
        actualizarAlturas(this.raiz);
      }
      if (verti.hayIzquierdo()) {
        revisarBalance(verti.izquierdo);
        actualizarAlturas(this.raiz);
      }

   
    } else if(op==2){
     if(verti.hayDerecho()){
        revisarBalance(verti.derecho);
        actualizarAlturas(this.raiz);
        }
        if (verti.hayIzquierdo()) {
        revisarBalance(verti.izquierdo);
        actualizarAlturas(this.raiz);
        }
        rebalancear(verti, izq, der);
        actualizarAlturas(this.raiz);
    }else{
      if (verti.hayDerecho()) {
        revisarBalance(verti.derecho);
        actualizarAlturas(this.raiz);
        }
        if (verti.hayIzquierdo()) {
        revisarBalance(verti.izquierdo);
        actualizarAlturas(this.raiz);
        }
    
    }
    return true;
  }

 

 

  /**
   * Metodo que balancea cuando se encuentra desbalancea del lado izquierdo
   *
   * @param vertice
   * @param kIzq
   * @param k altura de hijo derecho de vertice
   */
  public void desbalanceIzquierda(Vertice vertice, int kIzq, int k) {
    System.out.println("DESBALANCE IZQUIERDO +k"+ vertice);
    Vertice HIzq = vertice.izquierdo;
    if (HIzq.hayIzquierdo()) {
      Vertice WIzq = HIzq.izquierdo;
      Vertice WDer = HIzq.derecho;
      VerticeAVL WizqAVL = convertirAVL(WIzq);
      VerticeAVL WderAVL = convertirAVL(WDer);
      if (WizqAVL.altura == k + 1) {

        System.out.println("Caso 1: Linea recta");
       rotacionDerecha(vertice);
        
        actualizarAlturas(this.raiz);
        //revisarBalance(this.raiz);
      } else if (
        WizqAVL.altura == k && WderAVL.altura == k + 1
      ) {
        System.out.println("Caso 2: ZIG ZAG");
        
        
        
       
        if(vertice.hayDerecho()){
          rotacionIzquierda(vertice.izquierdo);
          actualizarAlturas(vertice);
          rotacionDerecha(vertice);
          actualizarAlturas(vertice);
          }else{
            rotacionDerecha(vertice);
            actualizarAlturas(vertice);
          }
        
    
      
      actualizarAlturas(vertice);
       
      }else{
        if(vertice.hayDerecho()){
          revisarBalance(vertice.derecho);
          }
          if(vertice.hayIzquierdo()){
         revisarBalance(vertice.izquierdo);
          }
      }
    }

    if(vertice.hayDerecho()){
    revisarBalance(vertice.derecho);
    }else if(vertice.hayIzquierdo()){
      revisarBalance(vertice.izquierdo);
    }
    
  }

  /**
   * Revisa si un vertice esta desbalanceado
   * @param verti
   * @return
   */
  static boolean desbalanceado=false;
  public boolean revisarB(Vertice verti) {
    desbalanceado=false;
    int izq = 0, der = 0;
    if (verti.hayIzquierdo()) {
      VerticeAVL vertI = convertirAVL(verti.izquierdo);
      vertI.setAltura();
      izq = vertI.altura() - 1;
    }

    if (verti.hayDerecho()) {
      VerticeAVL vertD = convertirAVL(verti.derecho);
      vertD.setAltura();
      der = vertD.altura() - 1;
    }

    int op = Math.abs(izq - der);
    if (Math.abs(izq - der) < 2) {
      if(verti.hayDerecho()){
      revisarB(verti.derecho);
      }
      if(verti.hayIzquierdo()){
        revisarB(verti.izquierdo);
        }
    } else {
      desbalanceado=true;
    }
    return desbalanceado;
  }
  
}