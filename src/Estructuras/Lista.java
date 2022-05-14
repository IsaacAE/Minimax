package edd.src.Estructuras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lista<T> implements Collection<T> {

  private Nodo cabeza;

  public Nodo getCabeza() {
    return this.cabeza;
  }

  private Nodo ultimo;

  public Nodo getUltimo() {
    return this.ultimo;
  }

  public int longi;

  // Clase Nodo
  private class Nodo {

    public T elemento;
    public Nodo anterior;
    public Nodo siguiente;

    public Nodo(T elemento) {
      this.elemento = elemento;
    }
  }

  // Iterador
  private class Iterador implements IteradorLista<T> {

    public Nodo anterior;
    public Nodo siguiente;

    public Iterador() {
      siguiente = cabeza;
    }

    @Override
    public boolean hasNext() {
      return siguiente != null;
    }

    @Override
    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      T regresar = siguiente.elemento;

      this.anterior = this.siguiente;
      this.siguiente = siguiente.siguiente;
      return regresar;
    }

    @Override
    public boolean hasPrevious() {
      return anterior != null;
    }

    @Override
    public T previous() {
      if (!hasPrevious()) throw new NoSuchElementException();
      T regresar = anterior.elemento;

      this.siguiente = this.anterior;
      this.anterior = anterior.anterior;
      return regresar;
    }

    @Override
    public void start() {
      this.anterior = null;
      this.siguiente = cabeza;
    }

    @Override
    public void end() {
      this.anterior = ultimo;
      this.siguiente = null;
    }
  }

  /**
   * Agrega un elemento a la lista.
   *
   * @param elemento el elemento a agregar.
   * @throws IllegalArgumentException si <code>elemento</code> es
   *                                  <code>null</code>.
   */
  @Override
  public void add(T elemento) {
    if (elemento == null) {
      throw new IllegalArgumentException("El elemento es null");
    }
    agregaFinal(elemento);
    // System.out.println("->" + cabeza);
    // System.out.println("u ->" + ultimo);
  }

  /**
   * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
   * el elemento a agregar será el primero y último.
   *
   * @param elemento el elemento a agregar.
   * @throws IllegalArgumentException si <code>elemento</code> es
   *                                  <code>null</code>.
   */
  public void agregaInicio(T elemento) {
    if (elemento == null) {
      throw new IllegalArgumentException("El elemento es null");
    }
    Nodo nuevo = new Nodo(elemento);
    if (cabeza == null) {
      this.cabeza = this.ultimo = nuevo;
    } else {
      this.cabeza.anterior = nuevo;
      nuevo.siguiente = this.cabeza;
      this.cabeza = nuevo;
    }
    longi++;
  }

  /**
   * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
   * el elemento a agregar será el primero y último.
   *
   * @param elemento el elemento a agregar.
   * @throws IllegalArgumentException si <code>elemento</code> es
   *                                  <code>null</code>.
   */
  public void agregaFinal(T elemento) {
    if (elemento == null) {
      throw new IllegalArgumentException("El elemento es null");
    }
    Nodo nuevo = new Nodo(elemento);
    if (cabeza == null) {
      this.cabeza = this.ultimo = nuevo;
    } else {
      this.ultimo.siguiente = nuevo;
      nuevo.anterior = this.ultimo;
      this.ultimo = nuevo;
    }
    this.longi++;
  }

  private Nodo buscaElemento(T elemento) {
    Nodo n = cabeza;
    while (n != null) {
      if (elemento.equals(n.elemento)) {
        return n;
      }
      n = n.siguiente;
    }
    return null;
  }

  /**
   * Cortar la lista hasta el elemento situado en el indice, el cual tambien es borrado
   * @param indice
   * @return Lista
   */
  public Lista trim(int indice){
    Iterator<T> iterador = this.iterator();
    //System.out.println(iterador.next());
    for (int i = 0; i <= indice; i++) {
      if(iterador.hasNext())
      this.delete(iterador.next());
    }
    return this;
  }

  /**
   * Elimina un elemento de la lista.
   *
   * @param elemento el elemento a eliminar.
   */
  public boolean delete(T elemento) {
    if (elemento == null) return false;
    Nodo n = buscaElemento(elemento);
    if (n == null) {
      return false;
    }
    if (longi == 1) {
      empty();
      return true;
    }
    if (n == cabeza) {
      cabeza = cabeza.siguiente;
      cabeza.anterior = null;
      longi--;
      return true;
    }
    if (n == ultimo) {
      ultimo = ultimo.anterior;
      ultimo.siguiente = null;
      longi--;
      return true;
    }
    n.siguiente.anterior = n.anterior;
    n.anterior.siguiente = n.siguiente;
    longi--;
    return true;
  }

  /**
   * Regresa un elemento de la lista. (Ultimo)
   * y lo elimina.
   *
   * @return El elemento a sacar.
   */
  public T pop() {
    T valor = ultimo.elemento;
    ultimo = ultimo.anterior;
    ultimo.siguiente = null;
    longi--;
    return valor;
  }

  /**
   * Regresa el número de elementos en la lista.
   *
   * @return el número de elementos en la lista.
   */
  public int size() {
    return longi;
  }

  /**
   * Nos dice si un elemento está contenido en la lista.
   *
   * @param elemento el elemento que queremos verificar si está contenido en
   *                 la lista.
   * @return <code>true</code> si el elemento está contenido en la lista,
   *         <code>false</code> en otro caso.
   */
  public boolean contains(T elemento) {
    if (buscaElemento(elemento) == null) {
      return false;
    }
    return true;
  }

  /**
   * Vacía la lista.
   *
   */
  public void empty() {
    cabeza = ultimo = null;
    longi = 0;
  }

  /**
   * Nos dice si la lista es vacía.
   *
   * @return <code>true</code> si la lista es vacía, <code>false</code> en
   *         otro caso.
   */
  public boolean isEmpty() {
    return longi == 0;
  }

  /**
   * Regresa una copia de la lista.
   *
   * @return una copia de la lista.
   */
  public Lista<T> clone() {
    Lista<T> nueva = new Lista<T>();
    Nodo nodo = cabeza;
    while (nodo != null) {
      nueva.add(nodo.elemento);
      nodo = nodo.siguiente;
    }
    return nueva;
  }

  /**
   * Nos dice si la coleccion es igual a otra coleccion recibida.
   *
   * @param coleccion la coleccion con el que hay que comparar.
   * @return <tt>true</tt> si la coleccion es igual a la coleccion recibido
   *         <tt>false</tt> en otro caso.
   */
  public boolean equals(Collection<T> coleccion) {
    // lo vemos en clase
    if (coleccion instanceof Lista) {
      return true;
    }
    return false;
  }

  /**
   * Metodo que invierte el orden de la lista .
   *
   * Primero comprobamos que la longitud sea mayor a 1, luego
   * vamos a recorrer la lista mediante un for con el iterador
   * empezando desde atrás, estos elementos del iterador se
   * agregarán a la misma lista desde la que es llamado el método
   * (this), el tiempo de ejecución es n, dado que el bucle for se
   * va a ejecutar en función de la lista, es decir, de n. Por último
   * vamos a eliminar los elementos que ya existían en la lista
   * para que únicamente estén los que metimos en reversa, esto
   * lo hacemos con un bucle for y un iterador
   */
  public void reverse() {
    Iterador iterador = new Iterador();
    iterador.end();
    if (longi > 1) {
      for (int i = 0; i < this.longi; i++) {
        if (iterador.hasPrevious()) this.add(iterador.previous());
      }
      iterador.start();
      for (int i = 0; i < this.longi; i++) {
        if (iterador.hasNext()) this.delete(iterador.next());
      }
    }
  }

  /**
   * Regresa una representación en cadena de la coleccion.
   *
   * Si la longitud de la lista es menor o igual a 0, no
   * hacemos nada, en otro caso, con un bucle se va a ir
   * agregando el valor de cada nodo mediante el iterador
   * a una variable string, que posteriormente será devuelto.
   * Para quitar el último "->" de la lista, se hace uso de un
   * substring al string que estaremos regresando
   *
   * @return una representación en cadena de la coleccion.
   *         a -> b -> c -> d
   */
  // Tu codigo aqui
  public String toString() {
    String elements = "";
    Iterador iterador = new Iterador();
    int i = 0;
    if (this.longi <= 0) {
      return "";
    } else {
      while (i < this.longi) {
        if (iterador.hasNext()) {
          elements += iterador.next() + " -> ";
        }
        i++;
      }
      elements = elements.substring(0, elements.length() - 4);
      return elements;
    }
  }

  /**
   * Junta dos listas siempre y cuando sean del mismo tipo.
   *
   * @param lista         Lista que se quiere pegar con
   *                      la lista desde donde es llamado
   *                      el método
   * Si las listas son iguales, mientras que el contador iniciando
   * en 0 sea menor que la longitud de la lista, vamos a ir agergando
   * elementos a la lista this por medio del iterador de la lista que
   * pasa por parámetro
   */
  public void append(Lista<T> lista) {
    Iterator<T> iterador = lista.iterator();
    int x = 0;
    if (equals(lista)) {
      while (x < lista.longi) {
        if (iterador.hasNext()) {
          this.add(iterador.next());
        } else {
          break;
        }
        x++;
      }
    }

    return;
  }

  /**
   * Regresa un entero con la posicion del elemento.
   * Solo nos importara la primera aparición del elemento
   * Empieza a contar desde 0.
   *
   * @param elemento elemento del cual queremos conocer la posición.
   * @return entero con la posicion del elemento
   * @throws IllegalArgumentException si <code>elemento</code> es
   *                                       <code>null</code>.
   * @throws NoSuchElementException si lista.contains(elemento)==false
   * @throws NoSuchElementException si lista.isEmpty(elemento)==true
   */
  public int indexOf(T elemento) {
    int cont = 0;
    if (isEmpty()) {
      throw new IllegalArgumentException(
        "La lista es vacía y no tiene ningún elemento"
      );
    }
    if (elemento == null) {
      throw new IllegalArgumentException("El elemento es null");
    }

    if (!contains(elemento)) {
      throw new NoSuchElementException(
        "El elemento no se encuentra en la lista"
      );
    }

    Nodo nuevo = new Nodo(elemento);

    // if (cabeza == null) {
    //throw new IllegalArgumentException("La lista es vacía y no tiene ningún elemento");

    Nodo n = cabeza;
    while (n != null) {
      if (elemento.equals(n.elemento)) {
        break;
      }
      n = n.siguiente;
      cont++;
    }

    return cont;
  }

  /**
   * Inserta un elemento en un índice explícito.
   *
   * Si el índice es menor que cero, el elemento se agrega al inicio de la
   * lista. Si el índice es mayor o igual que el número de elementos en la
   * lista, el elemento se agrega al fina de la misma. En otro caso, después
   * de mandar llamar el método, el elemento tendrá el índice que se
   * especifica en la lista.
   *
   * Si i es menor que 0, será agregada por medio de insert, si es mayor
   * que la longitud de nuestra lista será agregada con el método add, en el
   * otro caso, recorreremos la lista n+1 veces, cuando el iterador del bucle
   * for sea igual al inidice i pasado por parámetro (cuando nuestro ciclo esta
   * a la par de donde queremos insertar el elemento) agregamos el elemento deseado,
   * posteriormente agregamos el resto de los elementos que quedaban. Por
   * último borramos todos lo elementos que originalmente tenía la lista, puesto
   * que son los que contenía la lista originalmente y no nos interesan
   *
   * @param i        el índice dónde insertar el elemento. Si es menor que 0 el
   *                 elemento se agrega al inicio, y si es mayor o igual que el
   *                 número
   *                 de elementos en la lista se agrega al final.
   * @param elemento el elemento a insertar.
   * @throws IllegalArgumentException si <code>elemento</code> es
   *                                  <code>null</code>.
   */
  public void insert(int i, T elemento) {
    if (i <= 0) {
      agregaInicio(elemento);
    } else if (i >= this.longi) {
      add(elemento);
    } else {
      Iterador iterador = new Iterador();
      int logiAux = this.longi;
      for (int j = 0; j < logiAux + 1; j++) {
        if (j == i) {
          this.add(elemento);
        } else {
          if (iterador.hasNext()) {
            this.add(iterador.next());
          }
        }
      }
      iterador.start();
      for (int j = 0; j < logiAux; j++) {
        if (iterador.hasNext()) this.delete(iterador.next());
      }
    }
  }

  /** Mezclar de forma alternada dos listas
   *
   * Tenemos 3 casos posibles; cuando las listas tienen el mismo tamaño resulta
   * sencillo sólamente recorrer con un bucle para cada lista y agregar a la par
   * un elemento de una lista con el de otra mediante un bucle for. Cuando la lista
   * this es más grande que la lista pasada por parámetro, hacemos un for que se va
   * a ejecutar n veces en función de la lista (de la lista menor) y haremos los mismo
   * que arriba, agregar los elementos mediante un for de forma alternada, posteriormente
   * agregaremos aquellos elementos de this que harán falta y que ya no irán alternados.
   * De manera análoga hacemos lo mismo pero cuando la lista (pasada por parámetro)
   * es más grande que la lista this.
   *
   * Al final, en cada uno de los casos necesitamos eliminar los elementos que ya
   * estaban en la lista, esto varía según el caso, aunque la forma en que borramos
   * estos elementos es análoga en los 3 casos.
   *
   * @param lista                 La lista con la que queremos hacer la mezcla
   */
  public void mezclaAlternada(Lista<T> lista) {
    // append(lista);
    Iterator<T> iteradorLista = lista.iterator();
    Iterador iteradorThis = new Iterador();
    int longiAux = this.size();

    // this y lista tiene el mismo tamaño
    if (this.size() == lista.size()) {
      for (int i = 0; i < lista.longi + this.longi; i++) {
        if (iteradorLista.hasNext() && iteradorThis.hasNext()) {
          this.add(iteradorThis.next());
          this.add(iteradorLista.next());
        }
      }
      iteradorThis.start();
      for (int i = 0; i < longiAux; i++) {
        if (iteradorThis.hasNext()) {
          this.delete(iteradorThis.next());
        }
      }
    }
    // this es mas grande que lista

    else if (this.size() > lista.size()) {
      for (int i = 0; i < longiAux; i++) {
        if (iteradorLista.hasNext() && iteradorThis.hasNext()) {
          this.add(iteradorThis.next());
          this.add(iteradorLista.next());
        }
      }
      for (int i = 0; i < longiAux - lista.size(); i++) {
        if (iteradorThis.hasNext()) {
          this.add(iteradorThis.next());
        }
      }
      iteradorThis.start();
      for (int i = 0; i < longiAux; i++) {
        if (iteradorThis.hasNext()) {
          this.delete(iteradorThis.next());
        }
      }
    }
    // lista es más grande que this

    else {
      for (int i = 0; i < longiAux; i++) {
        if (iteradorLista.hasNext() && iteradorThis.hasNext()) {
          this.add(iteradorThis.next());
          this.add(iteradorLista.next());
        }
      }
      for (int i = 0; i < lista.size() - longiAux; i++) {
        if (iteradorLista.hasNext()) {
          this.add(iteradorLista.next());
        }
      }
      iteradorThis.start();
      for (int i = 0; i < longiAux; i++) {
        if (iteradorThis.hasNext()) {
          this.delete(iteradorThis.next());
        }
      }
    }
  }

  /**
   * Regresa un iterador para recorrer la lista en una dirección.
   *
   * @return un iterador para recorrer la lista en una dirección.
   */
  public Iterator<T> iterator() {
    return new Iterador();
  }

  /**
   * Regresa un iterador para recorrer la lista en ambas direcciones.
   *
   * @return un iterador para recorrer la lista en ambas direcciones.
   */
  public IteradorLista<T> iteradorLista() {
    return new Iterador();
  }

  //
  public T eliminarIndice(int indice) {
    int numAux = 1;
    Nodo aux = this.cabeza;
    while (numAux < indice) {
      aux = aux.siguiente;
      numAux++;
    }
    delete(aux.elemento);
    return aux.elemento;
  }

  public T elemIndice(int indice) {
    int numAux = 0;
    Nodo aux = this.cabeza;
    while (numAux < indice) {
      aux = aux.siguiente;
      numAux++;
    }
    return aux.elemento;
  }

  public void intercambiar(int indice1, int indice2) {
    T elemento = this.elemIndice(indice1);
    this.nodoIndice(indice1).elemento = this.elemIndice(indice2);
    this.nodoIndice(indice2).elemento = elemento;
  }

  public Nodo nodoIndice(int indice) {
    int numAux = 0;
    Nodo aux = this.cabeza;
    while (numAux < indice) {
      aux = aux.siguiente;
      numAux++;
    }

    return aux;
  }

  /**
   * Elimina usando indices iniciando desde 0 (return)
   * @param indice
   */
  public T eliminarIndice0R(int indice) {
    if (indice == 0) {
      T aux = this.peek();
      delete(this.cabeza.elemento);
      return aux;
    } else {
      int numAux = 1;
      Nodo aux = this.cabeza;
      while (numAux <= indice) {
        aux = aux.siguiente;
        numAux++;
      }
      delete(aux.elemento);
      return aux.elemento;
    }
  }

  /**
   * Elimina usando indices iniciando desde 0
   * @param indice
   */
  public void eliminarIndice0(int indice) {
    if (indice == 0) {
      delete(this.cabeza.elemento);
    } else {
      int numAux = 1;
      Nodo aux = this.cabeza;
      while (numAux <= indice) {
        aux = aux.siguiente;
        numAux++;
      }
      delete(aux.elemento);
      return;
    }
  }

  public T get(int indice) {
    if (indice == 0) {
      return cabeza.elemento;
    }
    int numAux = 0;
    Nodo aux = this.cabeza;
    while (numAux < indice) {
      aux = aux.siguiente;
      numAux++;
    }
    return aux.elemento;
  }

  public T elemInd(int indice) {
    int numAux = 1;
    Nodo aux = this.cabeza;
    while (numAux < indice) {
      aux = aux.siguiente;
      numAux++;
    }
    return aux.elemento;
  }

  public T peek() {
    if (isEmpty()) {
      throw new NoSuchElementException("");
    }
    return cabeza.elemento;
  }

  public T peekInverse() {
    if (isEmpty()) {
      throw new NoSuchElementException("");
    }
    return ultimo.elemento;
  }

  // miLista.mergeSort();
  // Merge Sort
  public Lista<T> mergeSort(Comparator<T> comparador) {
    if (longi == 1 || longi == 0) {
      return clone();
    }
    Lista<T> izq = new Lista<T>();
    Lista<T> der = new Lista<T>();

    int mitad = longi / 2;
    Nodo aux = cabeza;
    while (aux != null && mitad-- != 0) {
      izq.add(aux.elemento);
      aux = aux.siguiente;
    }
    while (aux != null) {
      der.add(aux.elemento);
      aux = aux.siguiente;
    }
    //System.out.println("izq: " + izq.toString());
    //System.out.println("der: " + der.toString());
    izq = izq.mergeSort(comparador);
    der = der.mergeSort(comparador);
    return merge(izq, der, comparador);
  }

  public Lista<T> merge(Lista<T> izq, Lista<T> der, Comparator<T> comparador) {
    Lista<T> resultado = new Lista<T>();
    Nodo auxIzq = izq.cabeza;
    Nodo auxDer = der.cabeza;
    while (auxIzq != null && auxDer != null) {
      if (comparador.compare(auxIzq.elemento, auxDer.elemento) < 0) {
        resultado.add(auxIzq.elemento);
        auxIzq = auxIzq.siguiente;
      } else {
        resultado.add(auxDer.elemento);
        auxDer = auxDer.siguiente;
      }
    }
    if (auxIzq == null) {
      while (auxDer != null) {
        resultado.add(auxDer.elemento);
        auxDer = auxDer.siguiente;
      }
    } else {
      while (auxIzq != null) {
        resultado.add(auxIzq.elemento);
        auxIzq = auxIzq.siguiente;
      }
    }
    return resultado;
  }
  
  
  
  
  
  
  
  
}
