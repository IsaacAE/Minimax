package edd.src.Encerrado;

/**
 *Clase que representa una ficha en el tablero
 *@author Alcantara Estrada Kevin Isaac
 *@auhtor Rubio Haro Mauricio
 */
public class Ficha implements Cloneable{

  //Atributos privados de la clase

  private int color; //El color de la ficha: 1 para rojo, 0 para azul y -1 para ficha vacia
  private String figura; // ñas fichas se representan con un * del color correspondiente o como " " si es ficha vacia
  //De forma predeterminada, todas las fichas comienzan en 0,0.
  private int fila = 0;
  private int columna = 0;

  //Para pintar de rojo
  public static final String rojo = "\u001B[31m";

  public static final String reset = "\u001B[0m";
  //Para ointar de azul
  public static final String azul = "\u001B[36m";

  public static final String ANSI_GREEN = "\u001B[32m";

  //Constructor de la ficha que recibe un numero para determinar su color, 1 para roja, 0 para azul
  public Ficha(int color) {
    if (color == 1) {
      this.color = 1;
      this.figura = rojo + "*" + reset; //pintamos rojo
    } else if(color == 2){
      this.color = 2;
      this.figura = ANSI_GREEN +"*"+reset;
    }else {
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

  public Ficha(int fila, int columna) {
    this.fila = fila;
    this.columna = columna;
  }

  public Ficha (Ficha object){
    this.color = object.getColor();
    this.figura = object.getFigura();
    this.fila = object.getFila();
    this.columna = object.getColumna();
  }

  /**
   * Metodo que devuelve el valor del atributo color de la ficha
   * @return int
   */
  public int getColor() {
    return this.color;
  }

  public int getFila() {
    return this.fila;
  }

  public String getFigura() {
    return this.figura;
  }

  public void setFila(int fila) {
    this.fila = fila;
  }

  public void setColumna(int columna) {
    this.columna = columna;
  }

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
  @Override
  public Object clone(){  
    try{  
        return super.clone();  
    }catch(Exception e){ 
        return null; 
    }
  }
}
