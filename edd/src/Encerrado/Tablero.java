package edd.src.Encerrado;

import java.io.FileNotFoundException;
//import javax.lang.model.util.ElementScanner14;
//import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
//import wizard.src.Estructuras.*;
//import wizard.Archivo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
/**
 * @author Mauricio Rubio Haro
 * @author Kevin Isaac Alcantara Estrada
 */
//package minimax.src;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa un tablero para jugar
 * @author Alcantara Estrada Kevin Isaac
 * @author Rubio Haro Mauricio
 */
public class Tablero {

  //atributo unico de la clase, un tablero que contiene fichas
  Ficha[][] tablero;

  /**
   * Metodo constructor sin parametros, crea un tablero de 3x3
   */
  public Tablero() {
    tablero = new Ficha[3][3];
  }

  /**
   * Metodo que devuelve el atributo tablero
   * @return Ficha[][]
   */
  public Ficha[][] getTablero() {
    return this.tablero;
  }

  //Para pintar de blanco partes del tablero y no se pinte de un solo color de las fichas
  public static final String blanco = "\u001B[0m";

  /**
   * Metodo para pintar en consola al tablero
   */
  @Override
   public String toString() {
    int asciiValue = 92; //Valor en codigo ASCII del simbolo \

    char linea = (char) asciiValue; // Para poder imprimir en pantalla el simbolo \
    System.out.println();
    System.out.println(
      "[" +
      this.tablero[0][0] +
      blanco +
      "]------[" +
      blanco +
      this.tablero[0][2] +
      blanco +
      "]"
    );
    System.out.println(blanco + " | " + linea + "        /       |" + blanco);
    System.out.println(
      " |   [" + blanco + this.tablero[1][1] + blanco + "]     |"
    );
    System.out.println(blanco + " | /    " + linea + "       |" + blanco);
    System.out.println(
      "[" +
      blanco +
      this.tablero[2][0] +
      blanco +
      "]------[" +
      blanco +
      this.tablero[2][2] +
      blanco +
      "]"
    );
    //System.out.println();
    return "\n";
  }

  /**
   * Metodo para que el usuario elija la disposicion inicial de las fichas y el tablero tome tal estado
   * @param num // 0 para jugar con azules, 1 para jugar con rojas (esto se usara mas adelante)
   */
  public void disposicion(int num) {
    //Creacion fichas de colores
    Ficha fa1 = new Ficha(0);
    Ficha fa2 = new Ficha(0);
    Ficha fr1 = new Ficha(1);
    Ficha fr2 = new Ficha(1);
    Ficha vac = new Ficha();
    //Creacion ficha vacia, nos servira para poder intercambiarla con las otras fichas al moverse
    this.tablero[1][1] = vac;
    //Acomodo de las fichas segun el caso dado
    if (num == 0) {
      this.tablero[0][0] = fa1;
      this.tablero[0][2] = fr1;
      this.tablero[2][0] = fr2;
      this.tablero[2][2] = fa2;
      System.out.println(tablero);
      //pintarTablero();
    } else {
      this.tablero[0][0] = fr1;
      this.tablero[0][2] = fa1;
      this.tablero[2][0] = fa2;
      this.tablero[2][2] = fr2;
      System.out.println(tablero);
    }
  }

  /**
   * Metodo para modificar el tablero de la clase tablero
   * @param tablero Arreglo del tipo Ficha[][] con el cual se modificara el valor actual del atributo tablero
   */
  public void setTablero(Ficha[][] tablero) {
    this.tablero = tablero;
  }

  /**
   * Metodo que verifica si la posicion dada es de las que estan disponibles en el tablero
   * es decir, si la casilla elegida realmente pertenece al tablero del juego
   * @param fila
   * @param columna
   * @return boolean
   */
  public boolean validarPos(int fila, int columna) {
    //Esquina superior izquierda
    if (fila == 0 && columna == 0) {
      return true;
    }

    //Casilla central
    if (fila == 1 && columna == 1) {
      return true;
    }

    //Esquina inferior izquierda
    if (fila == 2 && columna == 0) {
      return true;
    }
    //Esquina inferior derecha
    if (fila == 2 && columna == 2) {
      return true;
    }
    //Esquina superior derecha
    if (fila == 0 && columna == 2) {
      return true;
    }

    //SI la casilla dada no corresponde a ninguna de las anteriores, regresa falso
    //System.out.println("Error en validarPos");
    return false;
  }

  /**
   * Metodo para cambiar las fichas de posicion entre si, se usa principalmente para cambiar una ficha de color por la ficha vacia
   * @param indice1
   * @param indice2
   * @param indice3
   * @param indice4
   */

  public void cambiarFichas(
    int indice1,
    int indice2,
    int indice3,
    int indice4
  ) {
    Ficha aux = this.tablero[indice1][indice2];
    this.tablero[indice1][indice2] = this.tablero[indice3][indice4];
    this.tablero[indice3][indice4] = aux;
  }

  /**
   * Metodo para validar el movimiento que se quiere hacer, no toma en cuenta si hay fichas en las casillas dadas
   * solo considera los valores de las posiciones dadas y las relaciones entre si
   * @param filaInicial
   * @param columInicial
   * @param filaFinal
   * @param columFinal
   * @return boolean
   */
  public boolean validarMov(
    int filaInicial,
    int columInicial,
    int filaFinal,
    int columFinal
  ) {
    //SI se quiere mover de entre las esquinas inferiores (de izquierda a derecha o viceversa) devuelve falso
    if (
      filaInicial == 2 &&
      filaFinal == 2 &&
      Math.abs(columFinal - columInicial) == 2
    ) {
      return false;
    }
    //Para mover de arriba hacia abajo y viceversa
    if (Math.abs(columInicial - columFinal) == 2 && filaInicial == filaFinal) {
      return true;
    }

    //Caso especial para mover de abajo hacia arriba desde la esquina superior derecha hacia la esquina inferior derecha
    if (Math.abs(filaInicial - filaFinal) == 2 && columInicial == columFinal) {
      return true;
    }

    //Para mover del centro a las esquinas y viceversa
    if (Math.abs(filaInicial - filaFinal) == 1) {
      return true;
    }
    //Para mover de la esquina superior derecha a la esquina superior izquierda y viceversa
    if (columInicial == 0 && columFinal == 0) {
      return true;
    }

    //SI no se cumple ninguna de esas condiciones, regresa falso
    return false;
  }

  /**
   * Metodo para mover una ficha
   * @param filaInicial
   * @param columInicial
   * @param filaFinal
   * @param columFinal
   * @param turno Para considerar si se puede mover la ficha elegida, el turno debe variar entre 1 y 0
   * @return Ficha[][] Se podria cambiar
   */
  /*public Ficha[][] moverFicha(int filaInicial, int columInicial, int filaFinal, int columFinal, int turno){
        //SI se quiere mover la ficha al mismo sitio seria incorrecto, esta validacion la podemos hacer fuera del metodo
        //como al pedir los datos de las posiciones, esto es solo para pruebas
        if(filaInicial==filaFinal&&columInicial==columFinal){
            System.out.println("Hace falta atrapa bobos");
        }
     
        //Para saber si el movimiento que se quiere hacer es valido, esto se puede hacer fuera del metodo
        //se coloco aqui para pruebas
        if(!validarMov(filaInicial, columInicial, filaFinal, columFinal)){
            System.out.println("Movimiento invalido");
            return this.tablero;
        }
           //Verificamos que la ficha que se quiere mover es del color del jugador y a la vez que no sea la ficha vacia
                if(tablero[filaInicial][columInicial].getColor()==turno){
                    //Verificamos que a donde se quiere mover la ficha, no hay otra ficha de color
                    //es decir, a donde se quiere mover la ficha, esta la ficha vacia y por tanto se puede hacer el intercambio de fichas
                    if(tablero[filaFinal][columFinal].getColor()==-1){
                        //Si lo anterior se cumple, movemos la ficha
                        cambiarFichas(filaInicial,columInicial,filaFinal,columFinal);
                    }else{
                        System.out.println("Movimiento incorrecto"); //Mas adelante necesitaremos atrapabobos
                    }
                    }else{
                        System.out.println("No es tu turno o intentas mover una ficha donde no hay nada");//Mas adelante necesitaremos atrapabobos
                    }

        return this.tablero;
    }*/
  public Ficha moverFicha(int fila, int columna, Ficha ficha) {
    if (
      validarPos(fila, columna) &&
      validarMov(ficha.getFila(), ficha.getColumna(), fila, columna) &&
      tablero[fila][columna] == null
    ) {
      tablero[ficha.getFila()][ficha.getColumna()] = null;
      tablero[fila][columna] = ficha;
      ficha.setFila(fila);
      ficha.setColumna(columna);
      return ficha;
    } else {
      //System.out.println("Error al mover ficha");
    }
    return null;
  }


  public Ficha SimularMoverFicha(int fila, int columna, Ficha ficha) {
    if (
      validarPos(fila, columna) &&
      validarMov(ficha.getFila(), ficha.getColumna(), fila, columna) &&
      tablero[fila][columna] == null
    ) {
      return ficha;
    }else{
        System.out.println("No puedes mover la ficha");
        System.out.println(this);
        System.out.println("Fila "+ficha.getFila()+" columna "+ ficha.getColumna());
        //System.out.println("-->"+validarMov(ficha.getFila(), ficha.getColumna(), fila, columna));
        System.out.println(ficha);
    }
    return null;
  }

  public int movimientosDisponibles(Ficha ficha) {
    int aux = 0;
    //System.out.println("Calculando opciones de la ficha " + ficha);
    for (int i = 0; i < tablero.length; i++) {
      for (int j = 0; j < tablero[i].length; j++) {
        if (SimularMoverFicha(j, i, ficha) != null) {
          aux++;
        }
      }
    }
    return aux;
  }
}
