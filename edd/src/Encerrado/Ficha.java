package edd.src.Encerrado;

/**
 *Clase que representa una ficha en el tablero
 *@author Alcantara Estrada Kevin Isaac
 *@auhtor Rubio Haro Mauricio
 */
public class Ficha {

  //Atributos privados de la clase

  private int color; //El color de la ficha: 1 para rojo, 0 para azul y -1 para ficha vacia
  private String figura; // las fichas se representan con un * del color correspondiente o como " " si es ficha vacia
  //De forma predeterminada, todas las fichas comienzan en 0,0.
  private int fila = 0; //fila en donde esta la ficha
  private int columna = 0; //columna en donde esta la ficha

  //Para pintar de rojo
  public static final String rojo = "\u001B[31m";
//Para pintar de blanco
  public static final String reset = "\u001B[0m";
  //Para pintar de azul
  public static final String azul = "\u001B[36m";

  //Constructor de la ficha que recibe un numero para determinar su color, 1 para roja, 0 para azul
  public Ficha(int color) {
    if (color == 1) {
      this.color = 1;
      this.figura = rojo + "*" + reset; //pintamos rojo
    } else {
      this.color = 0;
      this.figura = azul + "*" + reset; //pintamos azul
    }
  }

  /**
   * Metodo constructor sin parametros para generar una ficha "vacia"
   */
  public Ficha() {
    this.color = -1;
    this.figura = " ";
  }
/**
 * Constructor con parametros de la ficha (modifica el color de la ficha)
 * @param fila
 * @param columna
 * @param color
 */
  public Ficha(int fila, int columna, int color) {
    if (color == 1) {
      this.color = 1;
      this.figura = rojo + "*" + reset; //pintamos rojo
    } else {
      this.color = 0;
      this.figura = azul + "*" + reset; //pintamos azul
    }
    this.fila = fila;
    this.columna = columna;
  }
/**
 * Constructor con parametros de la ficha (no modifica el color de la ficha)
 * @param fila
 * @param columna
 */
  public Ficha(int fila, int columna) {
    this.fila = fila;
    this.columna = columna;
  }

  /**
   * Metodo que devuelve el valor del atributo color de la ficha
   * @return int
   */
  public int getColor() {
    return this.color;
  }

  /**
   * Metodo que devuelve el valor del atributo fila de la ficha
   * @return fila
   */
  public int getFila() {
    return this.fila;
  }

  /**
   * Metodo que modifica el valor del atributo fila de la ficha
   * @param fila
   */
  public void setFila(int fila) {
    this.fila = fila;
  }

  /**
   * Metodo que modifica el valor del atributo ficha
   * @param columna
   */
  public void setColumna(int columna) {
    this.columna = columna;
  }

  /**
   * Metodo que devuelve el valor del atributo columna de la ficha
   * @return columna
   */
  public int getColumna() {
    return this.columna;
  }

  /**
   * Representacion en cadena de la ficha
   * @return String
   */
  public String toString() {
    return this.figura + "F " + this.fila + "C " + this.columna;
  }

  /**
   * Metodo para saber si una ficha es igual al otra
   * @param object
   */
  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Ficha)) {
      return false;
    }
    Ficha aux = (Ficha) object;
    if (
      aux.fila == this.fila &&
      aux.columna == this.columna &&
      aux.color == this.getColor()
    ) {
      return true;
    }
    return false;
  }
}
