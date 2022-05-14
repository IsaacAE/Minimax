package edd.src.Encerrado;

//CLASE PAR HACER PRUEBAS DE LOS METODOS
public class Main {

  public static void main(String[] args) {
    Tab tab = new Tab();
    //tab.pintarTablero();
    tab.disposicion(1);
    tab.setTablero(tab.moverFicha(0, 0, 1, 1, 0));
    tab.pintarTablero();
    tab.setTablero(tab.moverFicha(0, 0, 1, 1, 0));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(2, 2, 0, 0, 0));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(0, 2, 2, 2, 1));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(0, 2, 2, 2, 1));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(2, 0, 0, 0, 1));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(0, 0, 2, 0, 1));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(2, 2, 2, 0, 0));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(0, 2, 2, 0, 1));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(1, 1, 2, 0, 0));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(2, 2, 1, 1, 0));
    tab.pintarTablero();

    tab.setTablero(tab.moverFicha(0, 2, 2, 2, 1));
    tab.pintarTablero();
    //tab.disposicion(2);
  }
}
