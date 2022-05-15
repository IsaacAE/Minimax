package edd.src.Encerrado;

public class Juego {

  Tablero tablero = new Tablero();
  Jugador jugador = new Jugador("User", new Ficha(0));
  Jugador IA = new Jugador("IA", new Ficha(1));

  public Juego() {
    tableroPredeterminado();
  }

  public boolean moverFicha(int fila, int columna, Jugador jugador) {
    Tablero aux = jugador.moverFicha(tablero, fila, columna);
    if (aux == null) {
      return false;
    }
    tablero = aux;
    if (jugador.nombre.equals("User")) {
      setJugador(jugador);
    } else {
      setIA(jugador);
    }
    return true;
  }

    //Mover una ficha especifica
    public boolean moverFicha(int fila, int columna, Jugador jugador, Ficha ficha) {
    Tablero aux = jugador.moverFicha(tablero, fila, columna, ficha);
    if (aux == null) {
      return false;
    }
    this.tablero = aux;
    if (jugador.nombre.equals("User")) {
      setJugador(jugador);
    } else {
      setIA(jugador);
    }
    return true;
  }

  public void tableroPredeterminado() {
    moverFicha(0, 2, jugador, jugador.ficha1);
    moverFicha(2, 0, jugador, jugador.ficha2);
    moverFicha(0, 0, IA, IA.ficha1);
    IA.ficha2.setFila(1);
    IA.ficha2.setColumna(1);
    moverFicha(2, 2, IA, IA.ficha2);
  }

  public void asignarTablero(Ficha ficha, Jugador jugador){
      
  }

  public Jugador perdedor() {
    int movimientosJ = this.jugador.movimientosDisponibles(this.getTablero());
    int movimientosIA = this.IA.movimientosDisponibles(this.getTablero());
    if (movimientosJ == 0) {
      System.out.println("El jugador " + this.jugador + " ha perdido");
      return this.jugador;
    } else if (movimientosIA == 0) {
      System.out.println("El jugador " + this.IA + " ha perdido");
      return this.IA;
    }
    return null;
  }

  public Jugador getJugador() {
    return this.jugador;
  }

  public Tablero getTablero() {
    return this.tablero;
  }

  public Jugador getIA() {
    return this.IA;
  }

  @Override
  public String toString() {
    
    return tablero +"\n Jugadores: \n" + jugador + "\n" + IA;
  }

  public void setTablero(Tablero tablero) {
    this.tablero = tablero;
  }

  public void setJugador(Jugador jugador) {
    this.jugador = jugador;
  }

  public void setIA(Jugador jugador) {
    this.IA = jugador;
  }
}
