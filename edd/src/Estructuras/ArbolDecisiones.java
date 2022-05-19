package edd.src.Estructuras;

import edd.src.Encerrado.*;
import edd.src.Estructuras.ArbolMiniMax.VerticeMinimax;

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
  Juego juegoAux1;
  Juego juegoAux2;

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

    int p=0;
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

  
   
  System.out.println(this);
  calcularValor(this.raiz,1);
  System.out.println(this);

  }

  public void recursionDeNodos(Jugador [] jugadores, int i) {

    //System.out.println("TABLERO PICK");
    
    colaJuegos.peek().setTablero(colaJuegos.peek().getTablero().actualizaRef());
    /*System.out.println(colaJuegos.peek());
    System.out.println(jugadores[i]);
    System.out.println(jugadores[i].movimientosDisponibles(colaJuegos.peek().getTablero()));*/
    if(colaVertices.peek().profundidad()<8){
    int[] arr =
      jugadores[i].movimientosDisponiblesCord(colaJuegos.peek().getTablero());
     
    int[] arrF1 = { arr[0], arr[1] };
    int[] arrF2 = { arr[2], arr[3] };
    for(int h=0;h<arrF1.length;h++){
      System.out.println(arrF1[h]);
    }
    for(int l=0;l<arrF2.length;l++){
      System.out.println(arrF2[l]);
    }
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
      aux.color=colorear(aux);
     
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
      
     
     aux.color=colorear(aux);
     if(juegoAux1==null&&juegoAux2==null){
       aux.valor=100;
     }
      System.out.println("Cola 1->" + colaJuegos);
    System.out.println("Cola 1->" + colaVertices);
    
      System.out.println(this);

    /* while(!colaVertices.isEmpty()){

      if(aux.color==0){
        recursionDeNodos(jugadores, 1);
      }else {
        recursionDeNodos(jugadores,0);
      }
    }*/
    }
  }else{
    VerticeMinimax auxi =colaVertices.pop();
    auxi.visitado=true;
    System.out.println(auxi);
    auxi.valor= (int) (Math.random()*6);
   /* 
    VerticeMinimax b= convertirMinimax(auxi.padre);
    b.visitado=true;
    b.valor= (int) (Math.random()*6);*/
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
    System.out.println("JUEGUITO");
    
    jueguito.setTablero(jueguito.getTablero().actualizaRef());
    //Juego juegoAux = new Juego(this.juego);
    //juegoAux.setTablero(tab);
    for(int h=0;h<arrF1.length;h++){
      System.out.println(arrF1[h]);
    }
    for(int l=0;l<arrF2.length;l++){
      System.out.println(arrF2[l]);
    }
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
    System.out.println("LISTAAAA");
    System.out.println(lista);
    juegoAux1=null;
    juegoAux2=null;
    Iterator<Juego> iterador = lista.iterator();
    int i = 0;
    while (iterador.hasNext()) {
      Juego aux = iterador.next();
      aux.setTablero(aux.getTablero().actualizaRef());
      if(i==0){
        juegoAux1=aux;
        System.out.println("JUEGO AUX 1");
        System.out.println(juegoAux1);
      }else{
        juegoAux2=aux;
        System.out.println("JUEGO AUX 2");
        System.out.println(juegoAux2);
      }
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
   // tablerito= tablerito.actualizaRef();
   System.out.println("TABLERITOOOOO");
   System.out.println(tablerito);
    return tablerito;
  }
}
