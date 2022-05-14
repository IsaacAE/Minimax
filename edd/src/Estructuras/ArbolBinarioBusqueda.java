package edd.src.Estructuras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> {

  private class Iterador implements Iterator<T> {

    private Pila<Vertice> pila;

    public Iterador() {
      pila = new Pila<Vertice>();
      Vertice p = raiz;
      while (p != null) {
        pila.push(p);
        p = p.izquierdo;
      }
    }

    // falta hasNext
    public T next() {
      if (pila.isEmpty()) {
        throw new NoSuchElementException("vacio");
      }
      Vertice v = pila.pop();
      if (v.derecho != null) {
        Vertice u = v.derecho;
        while (u != null) {
          pila.push(u);
          u = u.izquierdo;
        }
      }

      return v.elemento;
    }

    public boolean hasNext() {
      return false;
    }
  }



  private boolean esHijoIzq = false;

  public Vertice verticeReemplazo(Vertice vertice) {
    Vertice reemplazarPadre = vertice;
    Vertice reemplazo = vertice;
    Vertice aux = vertice.derecho;
    while (aux != null) {
      reemplazarPadre = reemplazo;
      reemplazo = aux;
      aux = aux.izquierdo;
    }
    if (!reemplazo.equals(vertice.derecho)) {
      reemplazarPadre.izquierdo = reemplazo.derecho;
      reemplazo.derecho = vertice.derecho;
    }
   
    return reemplazo;
  }

  public boolean delete(T object) {
   
    Vertice vertice = this.search(this.raiz, object);
    if (vertice == null) {
      System.out.println("No se ha encontrado");
      return false;
    }
    if (vertice.izquierdo != null && vertice.derecho != null) {
      Vertice aux = vertice;
      Vertice padre = vertice.padre;
      Vertice reemplazo = verticeReemplazo(aux);
      if (aux.equals(this.raiz)) {
        this.raiz = reemplazo;
      } else if (esHijoIzq) {
        padre.izquierdo = reemplazo;
      } else {
        padre.derecho = reemplazo;
      }
      reemplazo.izquierdo = aux.izquierdo;
      return true;
    }
    Vertice parent = null;
    Vertice curr = this.raiz;
    while (curr != null && curr.elemento != object) {
      parent = curr;

      
      if (curr.elemento.compareTo(object) > 0) {
        curr = curr.izquierdo;
      } else {
        curr = curr.derecho;
      }
    }
    if (curr.izquierdo == null && curr.derecho == null) {
      
      if (curr != this.raiz) {
        if (parent.izquierdo == curr) {
          parent.izquierdo = null;
        } else {
          parent.derecho = null;
        }
      }
      
      else {
        this.raiz = null;
      }
    } else {
      
      Vertice child = (curr.izquierdo != null) ? curr.izquierdo : curr.derecho;

    
      if (curr != this.raiz) {
        if (curr == parent.izquierdo) {
          parent.izquierdo = child;
        } else {
          parent.derecho = child;
        }
      }
      
      else {
        this.raiz = child;
      }
    }


    return true;
  }

  public T pop() {
    return null;
  }

  public void add(T elemento) {
    if (elemento == null) {
      throw new IllegalArgumentException();
    }
    Vertice a = nuevoVertice(elemento);
    elementos++;
    if (isEmpty()) {
      raiz = a;
    } else {
      Vertice b = BFS();
      if (!b.hayIzquierdo()) {
        b.izquierdo = a;
        a.padre = b;
        return;
      }
      if (!b.hayDerecho()) {
        b.derecho = a;
        a.padre = b;
      }
    }
  }

  private Vertice BFS() {
    if (this.isEmpty()) {
      return null;
    }
    Cola<Vertice> a = new Cola<Vertice>();
    a.push(raiz);
    while (a.cabeza != null) {
      Vertice b = a.pop();
      if (b.hayIzquierdo()) {
        a.push(b.izquierdo);
      }
      if (b.hayDerecho()) {
        a.push(b.derecho);
      }
      if (!b.hayIzquierdo() || !b.hayDerecho()) {
        return b;
      }
    }
    return null;
  }

   
  /**
   * Metodo que regresa el recorrido BFS del arbol
   * @author Alcantara Estrada Kevin Isaac
   * @author Rubio Haro Mauricio
   * @param arbolE Arbol binario
   */
 
  protected Cola<T> modBFS(ArbolBinario arbolE) {

    //Si es vacio no procedemos
    if (arbolE.isEmpty()) {
      System.out.println("No puedo proceder con un arbol vacio");
      return null;
    }
    //Colas para llevar a cabo el recorrido
    Cola<Vertice> a = new Cola<Vertice>();
     Cola<T> colaBFS = new Cola<T>();
    //Anadimos vertice inicial
    a.push(arbolE.raiz);
    //Mientras la cola no sea vacia, proseguimos con el algoritmo
    while (a.cabeza != null) {
      Vertice b = a.pop();
      colaBFS.push(b.get());
      //Anadimos a los vertices vecinos
      if (b.hayIzquierdo()) {
        a.push(b.izquierdo);
      }
      if (b.hayDerecho()) {
        a.push(b.derecho);
      }
    }
   return colaBFS;
  }

  /**
   * Metodo que devuelve el elemento mas pequeño del arbol
   * @author Alcantara Estrada Kevin
   * @author Rubio Haro Mauricio
   * @param verti Vertice inicial (usualmente la raiz)
   * @return Verti 
   */
  protected Vertice ultimoIzquierdo(Vertice verti) {
    while(verti.hayIzquierdo()){
    
      verti = verti.izquierdo;
    }
    return verti;
  }

  /**
   * Metodo que devuelve el elemento mas grande del arbol
   * @author Alcantara Estrada Kevin
   * @author Rubio Haro Mauricio
   * @param verti Vertice inicial (usualmente la raiz)
   * @return Verti 
   */
  protected Vertice ultimoDerecho(Vertice verti) {
    
    //Mientras el vertice tenga vertice derecho, recorremos el arbol
      while(verti.hayDerecho()){
    
      verti = verti.derecho;
    }
    return verti;
  }

  /**
   * Metodo que busca el elemento dentro del arbol
   * @author Alcantara Estrada Kevin
   * @author Rubio Haro Mauricio
   * @param verti Vertice inicial (usualmente la raiz)
   * @param elemento ELemento a buscar
   */
  public Vertice search(Vertice vertice, T elemento) {
    if (vertice == null) {
      return null;
    }
    if (vertice.elemento == elemento) {
      return vertice;
    }
    if (vertice.get().compareTo(elemento) < 0) {
      return search(vertice.derecho, elemento);
    }
    if (vertice.get().compareTo(elemento) > 0) {
      esHijoIzq = true;
      return search(vertice.izquierdo, elemento);
    }
    return null;
  }


 

 
  /**
   * Metodo para ordenar una lista haciendo uso del metodo quicksort
   * @author Alcantara Estrada Kevin Isaac
   * @author Rubio Haro Mauricio
   * @param lista Lista a ordenar
   * @param inicio indice del elemento inicial
   * @param ultimo indice del ultimo elemento
   */
  private void ordenarLista(Lista<T> lista, int inicio, int ultimo) {
    Lista<T> listaNueva = new Lista<T>();

    int indice_izq, indice_der;
    T pivote;
    //Caso base de nuestra recursión
    if (inicio > ultimo) {
      return;
    }
    //Asignación de valores a nuestras variables auxiliares
    indice_izq = inicio;
    indice_der = ultimo;
    //Primero se hará el ordenamiento tomando como base el valor de la variable pivote, que será el valor del primer elemento del arreglo
    pivote = lista.elemIndice(inicio);
    //El ciclo se realizará hasta que nuestras variables auxiliares sen encuentre
    while (indice_der > indice_izq) {
      // Buscamos un valor que sea menor que nuestro pivote desde el último elemento de nuestro arreglo y hacia la izquierda
      while (
        indice_izq < indice_der &&
        (pivote.compareTo(lista.elemIndice(indice_der)) <= 0)
      ) {
        indice_der--;
      }
      //Partiendo desde el primer elemento de nuestro arreglo, buscamos un elemento que sea mayor que el valor de nuestro pivote y seguimos la búsqueda hacia la derecha
      while (
        (pivote.compareTo(lista.elemIndice(indice_izq)) >= 0) &&
        indice_izq < indice_der
      ) {
        indice_izq++;
      }
      // Una vez que hemos encontrado los elementos que buscábamos, si el elemento que buscamos desde la izquierda esta a la derecha del que buscamos por la derecha, intercambiamos los valores de dichos elementos en el arreglo
      if (indice_izq < indice_der) {
        /*num_aux = array[indice_der];
                array[indice_der] = array[indice_izq];
                array[indice_izq] = num_aux;*/
        lista.intercambiar(indice_der, indice_izq);
      }
    }
    // Finalmente, al encontrarse nuestros indices auxiliares, intercambiamos el valor nuestro pivote (el valor del primer elemento del arreglo) por el valor del elemento del arreglo en el cual se encontraron nuestros indices auxiliares
    lista.intercambiar(inicio, indice_izq);
    //  lista.intercambiar(indice_izq,pivote);
    //NOTA: de esta manera ya tenemos el pivote ubicado en el lugar que le corresponde en el arreglo y, a su derecha, los elementos mayores a este y, a su izquierda, los valores menores a esta

    //COMENZAMOS LAS LLAMADAS RECURSIVAS

    //Ordenamos los números que están a la izquierda de nuestro pivote
    ordenarLista(lista, inicio, indice_der - 1);
    //Ordenamos los números que están a la derecha de nuestro pivote
    ordenarLista(lista, indice_der + 1, ultimo);
    // }

    //return lista;
  }
  

/**
 * Metodo para construir un arbol de acuerdo a una lista desordenada
 * @author Alcantara Estrada Kevin
 * @author Rubio Haro Mauricio
 * @param lista Lista desordenada
 */
  public void buildUnsorted(Lista<T> lista) {
    ArbolBinarioBusqueda<T> arbolinio = new ArbolBinarioBusqueda<T>();
    //Si la lista es vacia
    if(lista.isEmpty()){
      System.out.println("No puedo proceder con una lista vacia");
      return;
    }
    //Ordenamos la lista con el algoritmo quicksort y como la lista es desordenada, nos toma O(nlog(n))
    ordenarLista(lista, 0, lista.size() - 1);
    System.out.println(lista.toString());
    /*
    T elem = lista.elemIndice(lista.size() / 2);
    arbolinio.add(elem);
    lista.delete(elem);*/
    buildSorted(lista);
     
    }
   

  


  
  /**
   * Metodo para insertar un elemento en el arbol
   * @author Alcantara Estrada Kevin Isaac
   * @param verti Vertice donde se comienza a ver si se puede insertar
   * @param elemento Elemento a insertar
   */

  public void insert(Vertice verti, T elemento){
    /*if(verti==null){
      Vertice nuevo = nuevoVertice(elemento);
      this.add(elemento);
    }*/
        if(verti.get().compareTo(elemento)>0){
            if(!verti.hayIzquierdo()){
                Vertice nuevo = nuevoVertice(elemento);
                verti.izquierdo=nuevo;
                nuevo.padre=verti;
            }else{
                insert(verti.izquierdo,elemento);
            }
        }

        if(verti.get().compareTo(elemento)<0){
            if(!verti.hayDerecho()){
                Vertice nuevo = nuevoVertice(elemento);
                verti.derecho=nuevo;
                nuevo.padre=verti;
            }else{
                insert(verti.derecho,elemento);
            }
        }

      }


       
  /**
   * Metodo para crear el arbol conn una lista ordenada
   * @author Alcantara Estrada Kevin Isaac
   * @author Rubio Haro Mauricio
   * @param lista Lista ordenada sobre la cual construir el arbol
   */
  public void buildSorted(Lista<T> lista) {
    if (lista.size() == 0) {
      return;
    }
    if (lista.size() == 1) {
      T elemento = lista.peek();
      this.raiz = new Vertice(lista.peek());
      return;
    }
    int mitad;
    if (lista.size() % 2 != 0) {
      mitad = (lista.size() / 2) + 1;
    } else {
      mitad = lista.size() / 2;
    }
    System.out.println("Lista-->" + lista);
    //System.out.println("Mitad "+lista.size()/2);
    this.raiz = new Vertice(lista.eliminarIndice(mitad));
    System.out.println("Lista-->" + lista);
    Lista<Integer> listaIzq = new Lista<>();
    IteradorLista<T> iterador = lista.iteradorLista();
    /**
     * Desplazamos el iterador hasta donde estaba la raiz, a partir de ahi
     * vamos a recorrer hacia la izquiera, de mayor a menor para insertar
     * en el nodo izquierdo
     */
    for (int i = 0; i < mitad - 1; i++) {
      if (iterador.hasNext()) {
        iterador.next();
      }
    }
    /**
     * Insertamos los elementos al arbol
     */
    for (int i = 0; i < mitad - 1; i++) {
      if (iterador.hasPrevious()) {
        this.insert(this.raiz, iterador.previous());
      }
    }
    iterador = lista.iteradorLista();
    for (int i = 0; i < mitad - 2; i++) {
      if (iterador.hasNext()) {
        iterador.next();
      }
    }
    for (int i = 0; i < mitad; i++) {
      if (iterador.hasNext()) {
        this.insert(this.raiz, iterador.next());
      }
    }

    return;
  }



    /**
     * Metodo que devuelve un toString del arbol con el recorrido in orden DFS
     * @author Alcantara Estrada Kevin Isaac
     * @author Rubio Haro Mauricio
     * @
     */
    Lista<T> colaDFS = new Lista<T>();
   // Override
    public String ptoString(){
      //LLevamos el algoritmo hasta tener al final de la lista al elemnto mas grande de todo el arbol
      while((!colaDFS.contains(ultimoDerecho(this.raiz).get()))){
       // System.out.println("Ultimo Der" +ultimoDerecho(this.raiz).get());
      inOrderDFS(raiz.izquierdo, raiz, raiz.derecho);
      }
       String s= colaDFS.toString();
       return s;

    }

    /**
     * Metodo que recorre el arbol en DFS inOrder y devuelve la lista con los vertices
     * @author Alcantara Estrada Kevin
     * @author Rubio Haro Mauricio
     * @param izq Vertice izquierdo
     * @param cen Vertice del centro
     * @param der Vertice de la derecha
     */
    protected void inOrderDFS(Vertice izq, Vertice cen, Vertice der){
     
      //Mientras el elemento que checamos no este en la lista
      if(!colaDFS.contains(cen.get())){
        //mientras haya vertice izquierdo y este no este ya en la lista
      if(cen.hayIzquierdo()&&!colaDFS.contains(izq.get())){
        //seguimos recorriendo hacia la izquierda
        cen= cen.izquierdo;
        inOrderDFS(cen.izquierdo,cen,cen.derecho);
      }else{
        //agregamos el vertice sobre el que estamos a la lista
      T elemento = cen.get();
        colaDFS.add(elemento);
       
        //si hay vertices derechos los recorremos con este algoritmo
        if(cen.hayDerecho()){
          cen=cen.derecho;
          inOrderDFS(cen.izquierdo,cen,cen.derecho);
          }
         
        }
      }else{
        //De nuevo revisamos si hay derecho para seguir el algoritmo por ahi
        if(cen.hayDerecho()){ 
          cen=cen.derecho;
          inOrderDFS(cen.izquierdo,cen,cen.derecho);
          }
      }
      }
    

      /**
 * Metodo que toma un arbol binario y lo transforma en un Arbol Binario de Busqueda balanceado y devuelve este
 * @param arb Arbol
 * @return ArbolBinarioBusqueda
 */
public ArbolBinarioBusqueda convertBST(ArbolBinario arb){
  
 Cola<T> colaBFS = modBFS(arb);
 // System.out.println("COLA" +colaBFS.toString());
 Lista<T> lista = new Lista<T>();
 Lista<T> mitad1 = new Lista<T>();
  Lista<T> mitad2 = new Lista<T>();

  //nuevo.add(colaBFS.pop());
  while(!colaBFS.isEmpty()){
    lista.add(colaBFS.pop());
  }
  ArbolBinarioBusqueda nuevo = new ArbolBinarioBusqueda(lista,true);

    return nuevo;
}

/**
 * Metodo que crear al Arbol BInario
 * @param lista Lista sobre la cual construirlo
 * @param isSorted para decir si esta ordenada la lista o no
 */
  public ArbolBinarioBusqueda(Lista<T> lista, boolean isSorted) {
    if (isSorted) {
      buildSorted(lista);
    } else {
      System.out.println("Unsorted");
      buildUnsorted(lista);
    }
  }


/**
 * Metodo que constructor sin parametros
 */
  public ArbolBinarioBusqueda() {}

  /**
   * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
   *
   * @return un iterador para iterar el árbol.
   */
  @Override
  public Iterator<T> iterator() {
    return new Iterador();
  }
}
 // }