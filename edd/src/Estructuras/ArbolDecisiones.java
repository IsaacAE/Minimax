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

  public Tablero getTablero() {
    return this.tablero;
  }

  public Juego getJuego() {
    return this.juego;
  }

  public void construirArbol() {
    String str = tablero.estadoTablero();
    System.out.println("-->str " + str);
    //VerticeMinimax aux = new VerticeMinimax(str);
    int[] arr = juego.inicial.movimientosDisponiblesCord(tablero);

    int[] arrF1 = { arr[0], arr[1] };
    int[] arrF2 = { arr[2], arr[3] };

    String [] permutaciones = jugadasPosibles(arrF1, arrF2);
    this.raiz = new VerticeMinimax(str);
    this.raiz.izquierdo = new VerticeMinimax(permutaciones[0]);
    this.raiz.derecho = new VerticeMinimax(permutaciones[1]);
    System.out.println(this);
  }

  public String [] jugadasPosibles(int[] arrF1, int[] arrF2) {
    String [] permutacion = new String[2];
    Ficha[] fichasJinicial = {
      juego.inicial.getFicha1(),
      juego.inicial.getFicha2(),
    };
    Lista<Juego> lista = new Lista<>();
    lista.add(this.juego);

    for (int i = 0; i < arrF1.length; i++) {
      //System.out.println("i " + i);
      Juego sim1 = new Juego(lista.peek());
      sim1.setTablero(
        moverFicha(
          arrF1,
          juego.inicial.ficha1.getColor(),
          sim1.getTablero(),
          fichasJinicial[i]
        )
      );
      lista.add(sim1);
    }

    lista.delete(this.juego);
    Iterator<Juego> iterador = lista.iterator();
    Vertice aux = new VerticeMinimax(this.juego.getTablero().estadoTablero());
    Tablero tabAux = iterador.next().getTablero();
    System.out.println(tabAux);
    permutacion[0] = tabAux.estadoTablero();
    //aux.derecho = new VerticeMinimax(tabAux.estadoTablero());
    tabAux = iterador.next().getTablero();
    System.out.println(tabAux);
    permutacion[1] = tabAux.estadoTablero();
    //aux.izquierdo = new VerticeMinimax(tabAux.estadoTablero());
    //this.raiz = aux;
    //System.out.println("Arbolito " + this);
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
