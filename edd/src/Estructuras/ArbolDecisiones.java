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

  public ArbolDecisiones() {
    super();
  }

  public void setJuego(Juego juego) {
    this.juego = juego;
    this.tablero = juego.getTablero();
    construirArbol();
  }

  public Tablero getTablero(){
    return this.tablero;
  }

  public Juego getJuego(){
    return this.juego;
  }

  public void construirArbol() {
    String str = tablero.estadoTablero();
    System.out.println("-->str " + str);
    System.out.println("Jugador inicial " + juego.inicial);
    System.out.println("Jugador inicial " + juego);
    VerticeMinimax aux = new VerticeMinimax(str);
    int[] arr = juego.inicial.movimientosDisponiblesCord(tablero);
    for (int i : arr) {
      System.out.println(": " + i);
    }
    int[] arrF1 = { arr[0], arr[1] };

    int[] arrF2 = { arr[2], arr[3] };

    Juego sim1 = new Juego(this.juego);
    //sim1.jugador.ficha1 = new Ficha(1);
    //sim1.moverFicha(1,1, sim1.getJugador());
    sim1.moverFicha(1, 1, sim1.getJugador(), sim1.getJugador().getFicha1());
    String str1 = sim1.getTablero().estadoTablero();
    System.out.println("Sim1 str --> "+str1);
    /*Tablero tab = sim1.getTablero();
    tab.moverFicha(1, 1, new Ficha(2));
    sim1.setTablero(tab);*/
    System.out.println("Sim1 ->"+sim1);
    System.out.println("Juego original ->"+this.juego);
    
    
    
    /*this.raiz = aux;
    this.raiz.izquierdo = new VerticeMinimax(str);
    this.raiz.derecho = new VerticeMinimax(str);
    System.out.println("Arbolito " + this);
    System.out.println(this.raiz.profundidad());*/
    //this.raiz = new Vertice
  }

  public void moverFicha(
    int[] arr,
    int color,
    Tablero tablerito,
    Ficha ficha
  ) {
    for (int i = 0; i < arr.length; i++) {
      switch (arr[i]) {
        case 1:
          ficha = tablerito.moverFicha(0, 0, ficha);
          break;
        case 2:
          ficha = tablerito.moverFicha(0, 2, ficha);
          break;
        case 3:
          ficha = tablerito.moverFicha(1, 1, ficha);
          break;
        case 4:
          ficha = tablerito.moverFicha(2, 0, ficha);
          break;
        case 5:
          ficha = tablerito.moverFicha(2, 2, ficha);
          break;
        default:
          System.out.println("No se mueve");
          break;
      }
    }
    //return ficha;
  }
}
