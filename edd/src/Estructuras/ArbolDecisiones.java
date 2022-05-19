package edd.src.Estructuras;

import edd.src.Encerrado.*;
//import edd.src.Encerrado.Tablero;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

//import edd.src.Estructuras.ArbolMiniMax;

public class ArbolDecisiones<T extends Comparable<T>> extends ArbolMiniMax {

  Juego juego;
  Tablero tablero;
  Cola<VerticeMinimax> colaVertices;
  Cola<Juego> colaJuegos;

  //VerticeMinimax raiz;

  public ArbolDecisiones() {
    super();
  }

  public void setJuego(Juego juego) {
    this.juego = juego;
    this.tablero = juego.getTablero();
    construirArbol();
  }

  public Tablero getTablero() {
    return this.tablero;
  }

  public Juego getJuego() {
    return this.juego;
  }

  public void construirArbol() {
    String str = tablero.estadoTablero();
    this.raiz = new VerticeMinimax(str);
    colaVertices = new Cola<>();
    colaJuegos = new Cola<>();
    colaVertices.push((VerticeMinimax) this.raiz);
    colaJuegos.push(this.juego);
    System.out.println("Peek vertices " + colaVertices.peek());
    System.out.println("Peek tablero " + colaJuegos.peek().getTablero());
    Jugador[] jugadores = { juego.getIA(), juego.getJugador() };

    recursionDeNodos(jugadores, 0);
    recursionDeNodos(jugadores, 1);
    recursionDeNodos(jugadores, 1);
    recursionDeNodos(jugadores, 0);
    recursionDeNodos(jugadores, 0);
    
    
    //recursionDeNodos(jugadores);
    /*System.out.println("-----------------------");
    recursionDeNodos(jugadores, 1);
    System.out.println("Cola 1->" + colaJuegos);
    System.out.println("Cola 1->" + colaVertices);
    recursionDeNodos(jugadores, 1);
    System.out.println("Cola 1->" + colaJuegos);
    System.out.println("Cola 1->" + colaVertices);*/
    



  }

  public void recursionDeNodos(Jugador [] jugadores, int i) {
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
      VerticeMinimax aux = colaVertices.pop();
      Juego auxJuego = colaJuegos.pop();
      Tablero Tabaux = auxJuego.getTablero();
      //colaJuegos.push(auxJuego);
      aux.izquierdo = new VerticeMinimax(Tabaux.estadoTablero());
      auxJuego = colaJuegos.pop();
      colaJuegos.push(auxJuego);
      Tabaux = auxJuego.getTablero();
      aux.derecho = new VerticeMinimax(Tabaux.estadoTablero());
      colaVertices.push((VerticeMinimax) aux.izquierdo);
      colaVertices.push((VerticeMinimax) aux.derecho);
      System.out.println("Cola 1->" + colaJuegos);
    System.out.println("Cola 1->" + colaVertices);
      System.out.println(this);
    }
  }

  public String[] jugadasPosibles(
    int[] arrF1,
    int[] arrF2,
    Jugador jugador,
    Juego jueguito
  ) {
    Ficha[] fichasJinicial = { jugador.getFicha1(), jugador.getFicha2() };
    Lista<Juego> lista = new Lista<>();
    //Juego juegoAux = new Juego(this.juego);
    //juegoAux.setTablero(tab);
    lista.add(jueguito);
    for (int i = 0; i < arrF1.length; i++) {
      if (arrF1[i] > 0) {
        Juego sim1 = new Juego(lista.peek());
        boolean aux = sim1.moverFichaCuadrante(
          arrF1[i],
          jugador,
          fichasJinicial[0]
        );
        if (aux) {
          lista.add(sim1);
        }
      }
    }
    for (int i = 0; i < arrF2.length; i++) {
      if (arrF2[i] > 0) {
        Juego sim1 = new Juego(lista.peek());
        boolean aux = sim1.moverFichaCuadrante(
          arrF2[i],
          jugador,
          fichasJinicial[1]
        );
        if (aux) {
          lista.add(sim1);
        }
      }
    }

    lista.delete(jueguito);
    String[] permutacion = new String[lista.size()];

    Iterator<Juego> iterador = lista.iterator();
    int i = 0;
    while (iterador.hasNext()) {
      Juego aux = iterador.next();
      colaJuegos.push(aux);
      permutacion[i] = aux.getTablero().estadoTablero();
      i++;
    }
    colaJuegos.pop();
    System.out.println("Lista lenght" + lista.size());
    return permutacion;
  }

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
    return tablerito;
  }
}
