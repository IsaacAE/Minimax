package edd.src.Encerrado;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Mauricio Rubio Haro
 * @author Kevin Isaac Alcantara Estrada
 */

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
   * Metodo constructor que clona una instancia de la clase
   * @param object Tablero a copiar
   */
  public Tablero(Tablero object){
    this.tablero = new Ficha [3][3];
    for ( int i = 0; i < object.getTablero().length; i++) {
      for (int j = 0; j < tablero.length; j++) {
          this.tablero[i][j] = object.tablero[i][j];
      }
    }
   

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
   * @return String
   */
  @Override
  public String toString() {
    int asciiValue = 92; //Valor en codigo ASCII del simbolo \

    char linea = (char) asciiValue; // Para poder imprimir en pantalla el simbolo \
    System.out.println();
    

    for(int g =0; g<this.tablero.length;g++){
      for(int h=0; h<this.tablero.length;h++){
        if(this.tablero[h][g]==null){
          this.tablero[h][g]= new Ficha();
        }
      }
    }

      System.out.println("1 [" +
      this.tablero[0][0] +
      blanco +
      "]------[" +
      blanco +
      this.tablero[0][2] +
      blanco +
      "] 2"
    );
    System.out.println(blanco + " | " + linea + "      /   |" + blanco);
    System.out.println(
      " |   3[" + blanco + this.tablero[1][1] + blanco + "]     |"
    );
    System.out.println(blanco + " | /       " +    linea + "  |" + blanco);
    System.out.println(
      "4 [" +
      blanco +
      this.tablero[2][0] +
      blanco +
      "]      [" +
      blanco +
      this.tablero[2][2] +
      blanco +
      "] 5"
    );

    for(int g =0; g<this.tablero.length;g++){
      for(int h=0; h<this.tablero.length;h++){
        if(this.tablero[h][g].getColor()==-1){
          this.tablero[h][g]= null;
        }
      }
    }
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
   * @param fila FIla dada
   * @param columna COlumna dada
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
   * @param indice1 Fila de la ficha 1
   * @param indice2 Columna de la ficha 1
   * @param indice3 Fila de la ficha 2
   * @param indice4 Columna de la ficha 2
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
   * @param filaInicial Fila actual de la ficha
   * @param columInicial Columna inicial de la ficha
   * @param filaFinal Fila a la que se pretende mover la ficha
   * @param columFinal Columna a la que se pretende mover la ficha
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
      filaInicial == 0 && columInicial == 2 && filaFinal == 2 && columFinal == 2
    ) {
      return true;
    }

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
   * @param filaInicial FIla donde esta la ficha
   * @param columInicial Columna en donde esta la fila
   * @param filaFinal Fila a la que se quiere mover la ficha
   * @param columFinal Columna a donde se quiere mover la ficha
   * @param turno Para considerar si se puede mover la ficha elegida, el turno debe variar entre 1 y 0
   * @return Ficha[][] Se podria cambiar
   */
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

  /**
   * Regresa una ficha segun el numero (cuadrante)
   * @param numero
   * @return Ficha
   */
  public Ficha buscarPosicion(int numero) {
    Ficha ficha = null;
    switch (numero) {
      case 1:
        ficha = this.tablero[0][0]; //Arriba a la izquierda
        break;
      case 2:
        ficha = this.tablero[0][2]; //Arriba a la derecha
        break;
      case 3:
        ficha = this.tablero[1][1]; //El centro
        break;
      case 4:
        ficha = this.tablero[2][0]; //Abajo a la izquierda
        break;
      case 5:
        ficha = this.tablero[2][2]; //Abajo a la derecha
        break;
    }
    return ficha;
  }

  /**
   * Regresa las coordenadas del tablero segun un cuadrante
   * @param numero
   * @return int[]
   */
  public int[] buscarPosicionCord(int numero) {
    //Ficha ficha=null;
    int aux[] = new int[2];
    switch (numero) {
      case 1:
        aux[0] = 0;
        aux[1] = 0;
        //ficha= this.tablero[0][0]; //Arriba a la izquierda
        break;
      case 2:
        aux[0] = 0;
        aux[1] = 2;
        //ficha=this.tablero[0][2]; //Arriba a la derecha
        break;
      case 3:
        aux[0] = 1;
        aux[1] = 1;
        //ficha= this.tablero[1][1]; //El centro
        break;
      case 4:
        aux[0] = 2;
        aux[1] = 0;
        //ficha= this.tablero[2][0]; //Abajo a la izquierda
        break;
      case 5:
        aux[0] = 2;
        aux[1] = 2;
        //ficha= this.tablero[2][2]; //Abajo a la derecha
        break;
    }
    return aux;
  }

  /**
   * Metodo que permite colocar una ficha en el tablero
   * @param fila Fila donde se quiere colocar la ficha
   * @param columna COlumna donde se quiere colocar la ficha
   * @param ficha Ficha a colocar
   * @return Ficha
   */
  public Ficha asignarFicha(int fila, int columna, Ficha ficha) {
    //System.out.println("Tablero -->"+this);
    //System.out.println("Esto es lo que hay "+tablero[fila][columna]);
    if (tablero[fila][columna] == null) {
      //System.out.println("Entro al if");
      tablero[fila][columna] = ficha;
      ficha.setFila(fila);
      ficha.setColumna(columna);
      //System.out.println("--> Tablero "+this);
      return ficha;
    }
    return null;
  }

  /**
   * Metodo que simula mover una ficha a un lugar dado para verificar si es posible realizar tal movimiento
   * @param fila Fila en la que se encuentra la ficha
   * @param columna Columna en la que se encuentra la ficha
   * @param ficha Ficha que se simulara mover
   * @return Ficha
   */
  public Ficha SimularMoverFicha(int fila, int columna, Ficha ficha) {
    if (
      validarPos(fila, columna) &&
      validarMov(ficha.getFila(), ficha.getColumna(), fila, columna) &&
      tablero[fila][columna] == null
    ) {
      return ficha;
    }
    return null;
  }

  /**
   * Metodo que devuelve el numero de movimientos disponibles de una ficha
   * @param ficha Objeto de la clase ficha que sera utilizada para calcular los movimientos posibles
   * @return int
   */
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

  /**
   * Metodo que calcula los movimientos disponibles de una ficha y los coloca en un arreglo indicando los los lugares del tablero disponibles
   * @param ficha Ficha de la cual se calcularan los movimientos disponibles
   * @return int[]
   */
  public int[] movimientosDisponiblesCord(Ficha ficha) {
    int[] coord = new int[2];
    int k = 0;

    if (SimularMoverFicha(0, 0, ficha) != null) {
      coord[k] = 1;
      k++;
    }

    if (SimularMoverFicha(0, 2, ficha) != null) {
      coord[k] = 2;
      k++;
    }

    if (SimularMoverFicha(1, 1, ficha) != null) {
      coord[k] = 3;
      k++;
    }

    if (SimularMoverFicha(2, 0, ficha) != null) {
      coord[k] = 4;
      k++;
    }

    if (SimularMoverFicha(2, 2, ficha) != null) {
      coord[k] = 5;
      k++;
    }

    return coord;
  }

  /**
   * Metodo que toma el estado actual del tablero y lo representa como una cadena de texto
   * @return String
   */
  public String estadoTablero() {
    String s = "";
    //Para la primera
    //0 azul 1 rojo
    if (this.tablero[0][0] == null) {
      s += "2";
    } else if (this.tablero[0][0].getColor() == 0) {
      s += "0";
    } else {
      s += "1";
    }

    //Para la segunda
    if (this.tablero[0][2] == null) {
      s += "2";
    } else if (this.tablero[0][2].getColor() == 0) {
      s += "0";
    } else {
      s += "1";
    }

    //Para la tercera
    if (this.tablero[1][1] == null) {
      s += "2";
    } else if (this.tablero[1][1].getColor() == 0) {
      s += "0";
    } else {
      s += "1";
    }

    //Para la cuarta
    if (this.tablero[2][0] == null) {
      s += "2";
    } else if (this.tablero[2][0].getColor() == 0) {
      s += "0";
    } else {
      s += "1";
    }

    //Para la quinta
    if (this.tablero[2][2] == null) {
      s += "2";
    } else if (this.tablero[2][2].getColor() == 0) {
      s += "0";
    } else {
      s += "1";
    }

    return s;
  }

  /**
   * Metodo para actualizar las referencias y posiciones de las fichas que estan en el tablero
   * @return Tablero
   */
  public Tablero actualizaRef() {
    for (int j = 0; j < this.tablero.length; j++) {
      for (int g = 0; g < this.tablero.length; g++) {
        if (this.tablero[j][g] != null) {
          this.tablero[j][g].setFila(j);
          this.tablero[j][g].setColumna(g);
        }
      }
    }
    return this;
  }
}
