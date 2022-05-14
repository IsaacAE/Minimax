package edd.src.Encerrado;

//CLASE PAR HACER PRUEBAS DE LOS METODOS
public class Main {

  public static void main(String[] args) {
    Ficha roja = new Ficha(1);
    System.out.println("Posicion de la ficha "+roja);
    Jugador jugador = new Jugador("Mau", roja);
    System.out.println("Jugador "+jugador);
    Tablero tab = new Tablero();
    tab.pintarTablero();
    tab = jugador.moverFicha(tab, 0,2);
    tab.pintarTablero();
    System.out.println("Jugador "+jugador);
  }
}
