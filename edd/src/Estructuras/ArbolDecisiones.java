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
  Cola<Tablero> colaTableros;

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
    colaTableros = new Cola<>();
    colaVertices.push((VerticeMinimax) this.raiz);
    colaTableros.push(tablero);
    //System.out.println("Peek vertices " + colaVertices.peek());
    //System.out.println("Peek tablero " + colaTableros.peek());
    Jugador[] jugadores = { juego.getIA(), juego.getJugador() };
    System.out.println("Cola vertices ->" + colaVertices);
    System.out.println("Cola Tableros ->" + colaTableros);

    /**------------------------- */
    //System.out.println("Cola vertices ->" + colaVertices);
    //System.out.println("Cola Tableros ->" + colaTableros);
    
    /**--------------------------- */
    int i = 0;
    int x = 0;
    do {
      int colaLenght = colaVertices.size();
      for (int j = 0; j < colaLenght; j++) {
        VerticeMinimax aux = colaVertices.pop();
        Tablero auxTab = colaTableros.pop();
        int[] arr = jugadores[0].movimientosDisponiblesCord(auxTab);
        for (int a : arr) {
          System.out.println("Arr->"+a);
        }
        System.out.println("jugador en turno " + jugadores[i]);
        int[] arrF1 = { arr[0], arr[1] };
        int[] arrF2 = { arr[2], arr[3] };
        String[] permutaciones = jugadasPosibles(arrF1, arrF2, jugadores[i], auxTab);
        for (String s : permutaciones) {
            System.out.println("Permutaciones "+s);
        }
        aux.izquierdo = new VerticeMinimax(permutaciones[0]);
        aux.derecho = new VerticeMinimax(permutaciones[1]);
        colaVertices.push((VerticeMinimax) aux.izquierdo);
        colaVertices.push((VerticeMinimax) aux.derecho);
        System.out.println("Cola vertices ->" + colaVertices);
        System.out.println("Cola Tableros ->" + colaTableros);
        System.out.println("----------------------------------");
      }
      if (i == 0) {
        i++;
      } else if (i == 1) {
        i--;
        //break;
      }
      x++;
    } while (x < 3);
    //int i = 0;
    //int x = 0;
    //while (this.raiz.profundidad() < 10) {
    /*while(x < 2){
      System.out.println("Cola vertices ->"+colaVertices);
      System.out.println("Cola Tableros ->"+colaTableros);
      VerticeMinimax aux = colaVertices.pop();
      Tablero auxTab = colaTableros.pop();
      int[] arr = jugadores[i].movimientosDisponiblesCord(auxTab);
      System.out.println("jugador en turno "+jugadores[i]);
      int[] arrF1 = { arr[0], arr[1] };
      int[] arrF2 = { arr[2], arr[3] };
      String [] permutaciones = jugadasPosibles(arrF1, arrF2, jugadores[i]);
      aux.izquierdo = new VerticeMinimax(permutaciones[0]);
      aux.derecho = new VerticeMinimax(permutaciones[1]);
      colaVertices.push((VerticeMinimax)aux.izquierdo);
      colaVertices.push((VerticeMinimax)aux.derecho);
      if(i == 0){
        i++;
      }else if(i == 1){
        i--;
        //break;
      }
      x++;
      System.out.println("Cola vertices ->"+colaVertices);
      System.out.println("Cola Tableros ->"+colaTableros);
    }*/
    System.out.println(this);
    /*String str = tablero.estadoTablero();
    System.out.println("-->str " + str);
    //VerticeMinimax aux = new VerticeMinimax(str);
    int[] arr = juego.inicial.movimientosDisponiblesCord(tablero);

    int[] arrF1 = { arr[0], arr[1] };
    int[] arrF2 = { arr[2], arr[3] };

    String [] permutaciones = jugadasPosibles(arrF1, arrF2);
    this.raiz = new VerticeMinimax(str);
    this.raiz.izquierdo = new VerticeMinimax(permutaciones[0]);
    this.raiz.derecho = new VerticeMinimax(permutaciones[1]);
    System.out.println(this);*/
  }

  public String[] jugadasPosibles(int[] arrF1, int[] arrF2, Jugador jugador, Tablero tab) {
    String[] permutacion = new String[2];
    Ficha[] fichasJinicial = { jugador.getFicha1(), jugador.getFicha2() };
    Lista<Juego> lista = new Lista<>();
    Juego juegoAux = new Juego(this.juego);
    juegoAux.setTablero(tab);
    lista.add(this.juego);

    for (int i = 0; i < arrF1.length; i++) {
      //System.out.println("i " + i);
      Juego sim1 = new Juego(lista.peek());
      sim1.setTablero(
        moverFicha(
          arrF1,
          jugador.ficha1.getColor(),
          sim1.getTablero(),
          fichasJinicial[i]
        )
      );
      lista.add(sim1);
    }
    /*for (int i = 0; i < arrF1.length; i++) {
      //System.out.println("i " + i);
      Juego sim1 = new Juego(lista.peek());
      sim1.setTablero(
        moverFicha(
          arrF1,
          jugador.ficha1.getColor(),
          sim1.getTablero(),
          fichasJinicial[i]
        )
      );
      lista.add(sim1);
    }*/

    lista.delete(this.juego);
    Iterator<Juego> iterador = lista.iterator();
    System.out.println("Lista lenght"+lista.size());
    Vertice aux = new VerticeMinimax(this.juego.getTablero().estadoTablero());
    Tablero tabAux = iterador.next().getTablero();
    colaTableros.push(tabAux);
    //System.out.println(tabAux);
    permutacion[0] = tabAux.estadoTablero();
    //aux.derecho = new VerticeMinimax(tabAux.estadoTablero());
    tabAux = iterador.next().getTablero();
    colaTableros.push(tabAux);
    //System.out.println(tabAux);
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
