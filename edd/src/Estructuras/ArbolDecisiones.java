package edd.src.Estructuras;

import edd.src.Encerrado.*;
import edd.src.Estructuras.ArbolMiniMax.VerticeMinimax;

//import edd.src.Encerrado.Tablero;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

//import edd.src.Estructuras.ArbolMiniMax;

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

  /**
   * Metodo para modificar el atributo juego de la clase
   * @param juego nuevo valor para el atributo
   */
  public void setJuego(Juego juego) {
    this.juego = juego;
    this.tablero = juego.getTablero();
    construirArbol();
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
    this.raiz = new VerticeMinimax(str);
    colaVertices = new Cola<>();
    colaJuegos = new Cola<>();
    colaVertices.push((VerticeMinimax) this.raiz);
    colaJuegos.push(this.juego);
    Jugador[] jugadores = {juego.getIA(), juego.getJugador()};
    colorI= jugadores[0].getFicha1().getColor();
    int p=0;
    //Mientras cola de vertices no este vacia, seguimos constuyendo el arbol
    while(!colaVertices.isEmpty()){
      int c = colaVertices.size();
      for(int l=0; l<c;l++){
    recursionDeNodos(jugadores, p);
      }
      if(p==0){
      p++;
      }else{
        p=0;
      }
    }
  calcularValor(this.raiz,colorI);
  System.out.println(this);

  }

  /**
   * Metodo que construye un los hijos de un vertice al estado de tal vertice, apoyandose de la cola de vertices para saber 
   * cual sera el vertice al cual se le crearan los hijos y y la colaJuegos para conocer el estado del juego
   * sobre el cual se calcularan los hijos de este vertice (tales hijos representan jugadas posibles)
   * @param jugadores arreglo que contiene a nuestros dos jugadoes
   * @param i entero que representa la posicion del arreglo de jugadores a usar
   */
  public void recursionDeNodos(Jugador [] jugadores, int i) {

    //System.out.println("TABLERO PICK");
    
    colaJuegos.peek().setTablero(colaJuegos.peek().getTablero().actualizaRef());
    /*System.out.println(colaJuegos.peek());
    System.out.println(jugadores[i]);
    System.out.println(jugadores[i].movimientosDisponibles(colaJuegos.peek().getTablero()));*/
    if(colaVertices.peek().profundidad()<11){
    int[] arr =
      jugadores[i].movimientosDisponiblesCord(colaJuegos.peek().getTablero());
     
    int[] arrF1 = { arr[0], arr[1] };
    int[] arrF2 = { arr[2], arr[3] };
   
    String[] permutaciones = jugadasPosibles(
      arrF1,
      arrF2,
      jugadores[i],
      colaJuegos.peek()
    );
    //if (!this.raiz.hayIzquierdo() && !this.raiz.hayDerecho()) {
      if(true){
        Tablero Tabaux=new Tablero();
      VerticeMinimax aux = colaVertices.pop();
      aux.color=colorear(aux, colorI);
     
     // Juego auxJuego = colaJuegos.pop();
     if(juegoAux1!=null){
       juegoAux1.setTablero(juegoAux1.getTablero().actualizaRef());
       Tabaux = juegoAux1.getTablero();
     
       aux.izquierdo = new VerticeMinimax(Tabaux.estadoTablero());
       aux.izquierdo.padre=aux;
       colaVertices.push((VerticeMinimax) aux.izquierdo);
     }
      //colaJuegos.push(auxJuego);
      
    //  auxJuego = colaJuegos.pop();
      //colaJuegos.push(auxJuego);
     // Tabaux = auxJuego.getTablero();
     if(juegoAux2!=null){
      juegoAux2.setTablero(juegoAux2.getTablero().actualizaRef());
      Tabaux = juegoAux2.getTablero();
     
      aux.derecho = new VerticeMinimax(Tabaux.estadoTablero());
      aux.derecho.padre=aux;
      colaVertices.push((VerticeMinimax) aux.derecho);
     }
      
     
     

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
    
    jueguito.setTablero(jueguito.getTablero().actualizaRef());
    lista.add(jueguito);
    for (int i = 0; i < arrF1.length; i++) {
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
          lista.add(sim1);
        }
      }
    }
    for (int i = 0; i < arrF2.length; i++) {
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
          
          lista.add(sim1);
        }
      }
    }

    lista.delete(jueguito);
    String[] permutacion = new String[lista.size()];
    juegoAux1=null;
    juegoAux2=null;
    Iterator<Juego> iterador = lista.iterator();
    int i = 0;
    while (iterador.hasNext()) {
      Juego aux = iterador.next();
      aux.setTablero(aux.getTablero().actualizaRef());
      if(i==0){
        juegoAux1=aux;
       
      }else{
        juegoAux2=aux;
      }
      colaJuegos.push(aux);
      permutacion[i] = aux.getTablero().estadoTablero();
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
