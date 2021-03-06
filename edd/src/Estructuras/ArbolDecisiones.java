package edd.src.Estructuras;

import edd.src.Encerrado.*;
import edd.src.Estructuras.ArbolMiniMax.VerticeMinimax;

//import edd.src.Encerrado.Tablero;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Clase que simula un arbol para tomar una decision
 * @author Alcantara Estrada Kevin Isaac
 * @author Rubio Haro Mauricio
 */
public class ArbolDecisiones<T extends Comparable<T>> extends ArbolMiniMax {
//Atributos privados de la clase
  Juego juego;
  Tablero tablero;
  Cola<VerticeMinimax> colaVertices;
  Cola<Juego> colaJuegos;
  Juego juegoAux1;
  Juego juegoAux2;
  int colorI;

 
/**
 * Constructor sin parametros de la clase
 */
  public ArbolDecisiones() {
    super();
  }

  public VerticeMinimax hijoDerechoRaiz(){
    if(raiz.hayDerecho()){
      return (VerticeMinimax)raiz.derecho;
    }
    return null;
  }
  public VerticeMinimax hijoIzquierdoRaiz(){
    if(raiz.hayIzquierdo()){
    return (VerticeMinimax)raiz.izquierdo;
    }
    return null;
  }

  public T getElemento(VerticeMinimax vertice){
    return (T) vertice.elemento;
  }
  public int getValor(VerticeMinimax vertice){
    return vertice.valor;
  }

  /**
   * Metodo para modificar el atributo juego de la clase
   * @param juego nuevo valor para el atributo
   */
  public void setJuego(Juego juego) {
    //this.juego = new Juego(juego);
    this.juego = juego;
    this.tablero = juego.getTablero();
    //construirArbol();
  }
/**
 * Metodo que devuelve el valor del atributo tablero
 * @return Tablero
 */
  public Tablero getTablero() {
    return this.tablero;
  }

  /**
   * Metodo que devuelve el valor del atributo juego
   * @return JUego
   */
  public Juego getJuego() {
    return this.juego;
  }

/**
 * Metodo que genera el ArbolDecisiones a partir del valor del atributo juego de la clase
 * y forma el arbol dedecisiones de manera iterativa con apoyo de otros metodos
 */
  public void construirArbol() {
    String str = tablero.estadoTablero();
    this.raiz = new VerticeMinimax(str);//creamos la raiz
    colaVertices = new Cola<>();
    colaJuegos = new Cola<>();
    colaVertices.push((VerticeMinimax) this.raiz);
    colaJuegos.push(this.juego);
    Jugador[] jugadores = {juego.getIA(), juego.getJugador()};
    colorI= jugadores[0].getFicha1().getColor();//damos a colorI el color de la IA
    int p=0;
    //Mientras cola de vertices no este vacia, seguimos constuyendo el arbol
    while(!colaVertices.isEmpty()){
      int c = colaVertices.size();
      for(int l=0; l<c;l++){//aplicaremos el metodo tantas veces como vertices habia en la cola antes de comenzar el proceso
    recursionDeNodos(jugadores, p);
      }
      if(p==0){//este condicional es para alternar enre los jugadores que se usan dentro del metodo recursionNodos
      p++;
      }else{
        p=0;
      }
    }
    
  calcularValor(this.raiz,colorI);//calculamos el valor de los vertices aplicando MInMax

  }

  /**
   * Metodo que construye un los hijos de un vertice al estado de tal vertice, apoyandose de la cola de vertices para saber 
   * cual sera el vertice al cual se le crearan los hijos y y la colaJuegos para conocer el estado del juego
   * sobre el cual se calcularan los hijos de este vertice (tales hijos representan jugadas posibles)
   * @param jugadores arreglo que contiene a nuestros dos jugadoes
   * @param i entero que representa la posicion del arreglo de jugadores a usar
   */
  public void recursionDeNodos(Jugador [] jugadores, int i) {

    //Sactualizamos referencias del primer elemento de la colaJuegos
    colaJuegos.peek().setTablero(colaJuegos.peek().getTablero().actualizaRef());
    if(colaVertices.peek().profundidad()<11){//Para que no se cicle y limitar la profundidad de las hojas
      int[] arr =jugadores[i].movimientosDisponiblesCord(colaJuegos.peek().getTablero());//calculamos los movimientos disponibles de las fichas del jugador
   
      //dividimos el arreglo por ficha
    int[] arrF1 = { arr[0], arr[1] };
    int[] arrF2 = { arr[2], arr[3] };
   
    //calculamos los resultados posibles a partir de los posibles movimientos de las fichas
    String[] permutaciones = jugadasPosibles(
      arrF1,
      arrF2,
      jugadores[i],
      colaJuegos.peek()
    );
    
      if(true){

        Tablero Tabaux=new Tablero();
      VerticeMinimax aux = colaVertices.pop();//tomamos el primer vertice de la cola de vertices para manejarlo
      aux.color=colorear(aux, colorI);//coloreamos tal vertice
     
     //si hay una posible jugada entonces creamos un nuevo vertice con el estado de tal jugada y agregamos el vertice creado a la colaVertices
     if(juegoAux1!=null){
       juegoAux1.setTablero(juegoAux1.getTablero().actualizaRef());
       Tabaux = juegoAux1.getTablero();
     
       aux.izquierdo = new VerticeMinimax(Tabaux.estadoTablero());
       aux.izquierdo.padre=aux;
       colaVertices.push((VerticeMinimax) aux.izquierdo);
     }
     
     //si hay una posible segunda jugada entonces creamos un nuevo vertice con el estado de tal jugada y agregamos el vertice creado a la colaVertices
     if(juegoAux2!=null){
      juegoAux2.setTablero(juegoAux2.getTablero().actualizaRef());
      Tabaux = juegoAux2.getTablero();
     
      aux.derecho = new VerticeMinimax(Tabaux.estadoTablero());
      aux.derecho.padre=aux;
      colaVertices.push((VerticeMinimax) aux.derecho);
     }
      
     
     
//Si no hay posibles jugadas, hemos llegado a un estado final y convertimos al vertice en hoja, lo quitamos de la cola de vertices
     if(juegoAux1==null&&juegoAux2==null){
       if(aux.color==colorI){
       aux.valor=1;
       }else{
         aux.valor=-1;
       }
       aux.visitado=true;
     }
      
    }
  }else{
    VerticeMinimax auxi =colaVertices.pop();
    auxi.visitado=true;
    auxi.valor= 0;
  }
  }

  /**
   * Metodo que calcula las jugadas posibles de las fichas de un jugadores respecto al estado del juego
   * @param arrF1 Arreglo con las posiciones a las cuales se puede mover la primer ficha del jugador
   * @param arrF2 Arreglo con las posiciones a las cuales se puede mover la segunda ficha del jugador
   * @param jugador Jugador del cual se calcularan los movimientos disponibles
   * @param jueguito Juego respecto al cual se haran los calculos
   * @return
   */
  public String[] jugadasPosibles(
    int[] arrF1,
    int[] arrF2,
    Jugador jugador,
    Juego jueguito
  ) {
    Ficha[] fichasJinicial = { jugador.getFicha1(), jugador.getFicha2() };
    Lista<Juego> lista = new Lista<>();
    //Actualizamos referencias del tablero actual
    jueguito.setTablero(jueguito.getTablero().actualizaRef());
    lista.add(jueguito);//agregamos el juego que pasamos como parametro
    for (int i = 0; i < arrF1.length; i++) {//revisamos las posibles jugadas de la ficha 1 y creamos los estados resultantes
      if (arrF1[i] > 0) {
        Juego sim1 = new Juego(lista.peek());
        sim1.setTablero(sim1.getTablero().actualizaRef());
        boolean aux = sim1.moverFichaCuadrante(
          arrF1[i],
          jugador,
          fichasJinicial[0]
        );
        sim1.setTablero(sim1.getTablero().actualizaRef());
        if (aux) {
          lista.add(sim1);//si hay jugadas disponibles, agregamos tales juegos resultantes a la lista
        }
      }
    }
    for (int i = 0; i < arrF2.length; i++) {//revisamos las posibles jugadas de la ficha 1 y creamos los estados resultantes
      if (arrF2[i] > 0) {
        Juego sim1 = new Juego(lista.peek());
        sim1.setTablero(sim1.getTablero().actualizaRef());
        boolean aux = sim1.moverFichaCuadrante(
          arrF2[i],
          jugador,
          fichasJinicial[1]
        );
        sim1.setTablero(sim1.getTablero().actualizaRef());
        if (aux) {
          
          lista.add(sim1);//agregamos los juegos resultantes de las jugadas posibles a la lista
        }
      }
    }

    lista.delete(jueguito);//eliminamos de la lista el estado original del juego
    String[] permutacion = new String[lista.size()];
    juegoAux1=null;//vaciamos variables
    juegoAux2=null;//vaciamos variables
    Iterator<Juego> iterador = lista.iterator();
    int i = 0;
    while (iterador.hasNext()) {//guardamos los juegos resultantes si es que los hubo
      Juego aux = iterador.next();
      aux.setTablero(aux.getTablero().actualizaRef());
      if(i==0){
        juegoAux1=aux;
       
      }else{
        juegoAux2=aux;
      }
      colaJuegos.push(aux);//agregamos los posibles juegos resultantes a la colaJuegos
      permutacion[i] = aux.getTablero().estadoTablero();//tomamos el estado del tablero de tal juego
      i++;
    }
    colaJuegos.pop();
    return permutacion;
  }


  /**
   * Metodo que pretende mover una ficha
   * @param arr arreglo con las posibles 
   * @param color
   * @param tablerito
   * @param ficha
   * @return
   */
  public Tablero moverFicha(
    int[] arr,
    int color,
    Tablero tablerito,
    Ficha ficha
  ) {
    for (int i = 0; i < arr.length; i++) {
      switch (arr[i]) {
        case 1:
          tablerito.moverFicha(0, 0, ficha);
          break;
        case 2:
          tablerito.moverFicha(0, 2, ficha);
          break;
        case 3:
          tablerito.moverFicha(1, 1, ficha);
          break;
        case 4:
          tablerito.moverFicha(2, 0, ficha);
          break;
        case 5:
          tablerito.moverFicha(2, 2, ficha);
          break;
        default:
          //System.out.println("No se mueve");
          break;
      }
    }
   // tablerito= tablerito.actualizaRef();
    return tablerito;
  }
}
