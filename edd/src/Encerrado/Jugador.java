package edd.src.Encerrado;

/**
 * Clase que simula un jugador de encerrado
 * @author Alcantara Estrada Kevin Isaac
 * @author Rubio Haro Mauricio
 */
public class Jugador {

//Atributos de la clase
  public String nombre;
  //De forma predeterminada, todas las fichas comienzan en 0,0.
  public Ficha ficha1;
  public Ficha ficha2;

  /**
   * Primer constructor con parametros de la clase
   * @param nombre
   * @param fichas
   */
  public Jugador(String nombre, Ficha fichas) {
    this.nombre = nombre;
    this.ficha1 = fichas;
    this.ficha2 = new Ficha(fichas.getColor());
  }
/**
 * Segundo constructor con parametros de la clase
 * @param nombre
 * @param fichas
 * @param fila
 */
  public Jugador(String nombre, Ficha fichas, int fila) {
    this.nombre = nombre;
    this.ficha1 = fichas;
    this.ficha2 = new Ficha(fila, fila, fichas.getColor());
  }
/**
 * Tercer constructor con parametros (para clonar a un objeto de la misma clase)
 * @param object Jugador sobre el cual se hace la copia
 */
  public Jugador(Jugador object){
    this.nombre = object.getNombre();
    this.ficha1 = new Ficha(object.getFicha1());
    this.ficha2 = new Ficha(object.getFicha2());
  }
/**
 * Metodo que devuelve el valor del atributo nombre
 * @return String
 */
  public String getNombre(){
    return this.nombre;
  }

  /**
 * Metodo que devuelve el valor del atributo ficha1
 * @return Ficha
 */
  public Ficha getFicha1(){
    return this.ficha1;
  }

  /**
 * Metodo que devuelve el valor del atributo ficha2
 * @return Ficha
 */
  public Ficha getFicha2(){
    return this.ficha2;
  }
  //Mueve cualquier ficha disponible
  /**
   * Metodo que mueve la primer ficha disponible a la primer casilla disponible
   * @param tablero tablero sobre el cual 
   * @param fila
   * @param columna
   * @return Tablero
   */
  public Tablero moverFicha(Tablero tablero, int fila, int columna) {
    Ficha aux = tablero.moverFicha(fila, columna, ficha1);
    if (aux == null) {
      aux = tablero.moverFicha(fila, columna, ficha2);
      if (aux == null) {
        return null;
      } else {
        System.out.println("Se movio la ficha 2");
        setFicha2(aux);
      }
    } else {
      System.out.println("Se movio la ficha 1");
      setFicha1(aux);
    }
    return tablero;
  }

  /**
   * Metodo unicamente usado para mover las fichas
   * @param tablero
   * @param fila
   * @param columna
   * @param ficha
   */
  public Tablero asignarFicha(
    Tablero tablero,
    int fila,
    int columna,
    int ficha
  ) {
    Ficha aux = null;
    if (ficha == 1 || ficha == 2) {
      if (ficha == 1) {
        aux = tablero.asignarFicha(fila, columna, this.ficha1);
      } else {
        aux = tablero.asignarFicha(fila, columna, this.ficha2);
      }
      if (aux != null) {
        if (ficha == 1) {
          this.ficha1 = aux;
        } else {
          this.ficha2 = aux;
        }
      }else{
          return null;
      }
    }else{
        return null;
    }
    return tablero;
  }

  /**
   * Metodo para saber si una ficha pertenece al jugador o no
   * @param ficha Ficha sobre la que se hace la verificacion
   * @return boolean 
   */
  public boolean fichaPertenece(Ficha ficha) {
    if(ficha.equals(this.ficha1) || ficha.equals(this.ficha2)){
      return true;
    }
    return false;
  }
/**
 * Metodo que compara una ficha con las dos que posee el juegaodr
 * @param ficha Ficha que se compara
 * @return Ficha
 */
  public Ficha buscarFicha(Ficha ficha){
    if(ficha.equals(this.ficha1)){
      return this.ficha1;
    }else if(ficha.equals(this.ficha2)){
      return this.ficha2;
    }
    return null;
  }

/**
 * Metodo que calcula mos cuadrantes disponibles a los que se pueden mover ambas fichas del jugador
 * @param tablero
 * @return int[]
 */
    public int[] movimientosDisponiblesCord(Tablero tablero){
        int[] coordenadas = new int[4];
        int[] ficha1Cord = new int[2];
        int[] ficha2Cord = new int[2];

        ficha1Cord=tablero.movimientosDisponiblesCord(this.ficha1);
        ficha2Cord=tablero.movimientosDisponiblesCord(this.ficha2);
        coordenadas[0]=ficha1Cord[0];
        coordenadas[1]=ficha1Cord[1];
        coordenadas[2]=ficha2Cord[0];
        coordenadas[3]=ficha2Cord[1];
        return coordenadas;
    }

    
  //Mover una ficha en especifico
  /**
   * Metodo para mover una ficha elegida
   * @param tablero Tablero sobre el cual se intentará mover la ficha
   * @param fila fila a la que se quiere mover la ficha
   * @param columna columna a la que se quiere mover la ficha
   * @param ficha ficha a mover
   * @return Tablero
   */
  public Tablero moverFicha(
    Tablero tablero,
    int fila,
    int columna,
    Ficha ficha
  ) {
    if (
      ficha != null && (ficha.equals(this.ficha1) || ficha.equals(this.ficha2))
    ) {
      Ficha aux = tablero.moverFicha(fila, columna, ficha);
      if (aux == null) {
        //System.out.println("No se pudo mover");
      } else {
        if (this.ficha1.equals(ficha)) {
          this.ficha1 = ficha;
        } else if (this.ficha2.equals(ficha)) {
          this.ficha2 = ficha;
        }
      }
    } else {
      System.out.println("No existe esa ficha");

    }
    return tablero;
  }

  /**
   * Metodo que calcula el total de movimientos disponibles de las fichas del jugador
   * @param tablero Tablero sobre el cual se quiere calcular los movimientos disponibles
   * @return int
   */
  public int movimientosDisponibles(Tablero tablero) {
    int aux1 = tablero.movimientosDisponibles(ficha1);
    int aux2 = tablero.movimientosDisponibles(ficha2);
    return aux1 + aux2;
  }

 
  /**
  * Metodo para modificar la primer ficha del jugador
  * @param ficha 
  */
  public void setFicha1(Ficha ficha) {
    this.ficha1 = ficha;
  }

/**
 * Metodo para modificar la segunda ficha del jugador
 * @param ficha
 */
  public void setFicha2(Ficha ficha) {
    this.ficha2 = ficha;
  }


  /**
   * Metodo para saber si dos instancias de la clase son iguales
   * @return boolean
   */
  @Override
  public boolean equals(Object object){
    if (object == this) {
      return true;
    }
    if (!(object instanceof Ficha)) {
      return false;
    }


    Jugador aux = (Jugador) object;
    if (
      aux.nombre == this.nombre &&
      aux.ficha1.equals(this.ficha1) &&
      aux.ficha2.equals(this.ficha2)
    ) {
      return true;
    }
    return false;
  }

  /**
   * Metodo que representa en cadena al jugador
   * @return String
   */
  @Override
  public String toString() {
    return this.nombre + " Ficha " + ficha1 + " Ficha " + ficha2;
  }
}

