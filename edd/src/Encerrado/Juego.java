package edd.src.Encerrado;

public class Juego{
//Atributos de la clase
  Tablero tablero = new Tablero();
  public Jugador jugador = new Jugador("User", new Ficha(0));
  Jugador IA = new Jugador("IA", new Ficha(1));
  public Jugador inicial = jugador;

  /**
   * Constructor sin parametros de la clase
   */
  public Juego() {
    tableroPredeterminado();
  }
  //public Jugador getIncial(){}
  public Juego(Juego object){
    this.tablero = new Tablero(object.getTablero());
    this.jugador = new Jugador(object.getJugador());
    this.IA = new Jugador(object.getIA());
    //this.inicial = object.inicial;
  }

  /**
   * Metodo para mover una ficha
   * @param fila fila a la que se quiere mover la ficha
   * @param columna colummna a la que se quiere mover la ficha
   * @param jugador Jugador duenio de la ficha
   * @return boolean
   */
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

  /**
   * Metodo para asignar fichas a diferentes posiciones del tablero
   * @param fila fila en la que se quiere colocar la ficha
   * @param columna columna en la que se quiere colocar la ficha
   * @param jugador Jugador duenio de la ficha
   * @param ficha Ficha a colocar
   * @return
   */
  public boolean asignarFicha(int fila, int columna, Jugador jugador, int ficha){
    //jugador = new Jugador("User", new Ficha(0));
    //IA = new Jugador("IA", new Ficha(1));
    Tablero aux = jugador.asignarFicha(this.tablero, fila, columna, ficha);
    if (aux == null) {
      return false;
    }
    tablero = aux;
    if (jugador.nombre.equals("User")) {
      setJugador(jugador);
    } else {
      setIA(jugador);
    }
    //System.out.println("Perrrraaa"+this);
    return true;
  }

  /**
   * Metodo que mueve fichas de manera aleatoria
   * @param jugador Jugador duenio de las fichas
   * @return boolean
   */
  public boolean moverFichaRandom(Jugador jugador){
    boolean ok = false;
    while(!ok){
    int fila = (int) (Math.random()*3);
    int columna = (int) (Math.random()*3);
    ok = moverFicha(fila, columna, jugador);
   }
    return ok;
  }

    /**
     * Metodo para mover una ficha 
     * @param fila fila a la cual se quiere mover la ficha
     * @param columna columna a la cual se quiere mover la ficha
     * @param jugador Jugador duenio de las fichas
     * @param ficha Ficha a mover
     * @return
     */
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

  /**
   * Metodo que genera un tablero predeterminado
   */
  public void tableroPredeterminado() {
    moverFicha(0, 2, jugador, jugador.ficha1);
    moverFicha(2, 0, jugador, jugador.ficha2);
    moverFicha(0, 0, IA, IA.ficha1);
    IA.ficha2.setFila(1);
    IA.ficha2.setColumna(1);
    moverFicha(2, 2, IA, IA.ficha2);
  }

  /*public void asignarTablero(Ficha ficha, Jugador jugador){
      
  }*/

  /**
   * Metodo que verifica si un jugador aun tiene movimientos disponibles o no, en el segundo caso se le declara perdedor
   * y se devuelve tal jugador
   * @return Jugador
   */
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

  /**
   * Metodo que verifica si hay peredor en el juego
   * @return boolean
   */
  public boolean hayPerdedor() {
    int movimientosJ = this.jugador.movimientosDisponibles(this.getTablero());
    int movimientosIA = this.IA.movimientosDisponibles(this.getTablero());
    if (movimientosJ == 0) {
      System.out.println("El jugador " + this.jugador + " ha perdido");
      return true;
    } else if (movimientosIA == 0) {
      System.out.println("El jugador " + this.IA + " ha perdido");
      return true;
    }
    return false;
  }
/**
 * Metodo que mueve una ficha a un cuadrante del tablero
 * @param cuandrante Cuadrante del tablero a donde se quiere mover
 * @param jugador Jugador duenio de la ficha
 * @param ficha Ficha a mover
 * @return boolean
 */
  public boolean moverFichaCuadrante(int cuandrante , Jugador jugador, Ficha ficha){
    boolean aux = false;
    if(cuandrante >= 1 && cuandrante <= 5){
      switch(cuandrante){
        case 1: 
        aux = this.moverFicha(0, 0, jugador, ficha);
        break;
  
        case 2: 
        aux = this.moverFicha(0, 2, jugador, ficha);
        break;
  
        case 3: 
        aux = this.moverFicha(1, 1, jugador, ficha);
        break;
  
        case 4: 
        aux = this.moverFicha(2, 0, jugador, ficha);
        break;
  
        case 5: 
        aux = this.moverFicha(2, 2, jugador, ficha);
        break;
      }
    }
    return aux;
  }


  public void repararTablero(){
    //Tablero aux = new Tablero();
    this.tablero = new Tablero();
    this.asignarFicha(jugador.ficha1.getFila(), jugador.ficha1.getColumna(), jugador, 1);
    this.asignarFicha(jugador.ficha2.getFila(), jugador.ficha2.getColumna(), jugador, 2);
    this.asignarFicha(IA.ficha2.getFila(), IA.ficha2.getColumna(), IA, 2);
    this.asignarFicha(IA.ficha1.getFila(), IA.ficha1.getColumna(), IA, 1);
    //System.out.println("Reparacion de tablero");
    //System.out.println(this.tablero);
  }

  /**
   * Metodo que devuelve el valor del atributo jugador
   * @return Jugador
   */
  public Jugador getJugador() {
    return this.jugador;
  }

  /**
   * Metodo que devuelve el valor del atributo tablero
   * @return Tablero
   */
  public Tablero getTablero() {
    return this.tablero;
  }

   /**
   * Metodo que devuelve el valor del atributo IA
   * @return Jugador
   */
  public Jugador getIA() {
    return this.IA;
  }

  /**
   * Metodo que representa en cadena a la clase
   * @return String
   */
  @Override
  public String toString() {
    return tablero +"\n Jugadores: \n" + jugador + "\n" + IA;
  }

  /**
   * Metodo que modifica el valor del atributo tablero
   * @param tablero nuevo valor para el atributo
   */
  public void setTablero(Tablero tablero) {
    this.tablero = tablero;
  }

  /**
   * Metodo que modifica el valor del atributo jugador
   * @param jugador nuevo valor para el atributo
   */
  public void setJugador(Jugador jugador) {
    this.jugador = jugador;
  }

  /**
   * Metodo que modifica el valor del atributo IA
   * @param jugador nuevo valor para el atributo
   */
  public void setIA(Jugador jugador) {
    this.IA = jugador;
  }
}
