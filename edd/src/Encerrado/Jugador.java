package edd.src.Encerrado;

public class Jugador {

  public String nombre;

  //De forma predeterminada, todas las fichas comienzan en 0,0.
  public Ficha ficha1;
  public Ficha ficha2;

  public Jugador(String nombre, Ficha fichas) {
    this.nombre = nombre;
    this.ficha1 = fichas;
    this.ficha2 = new Ficha(fichas.getColor());
  }

  public Jugador(String nombre, Ficha fichas, int fila) {
    this.nombre = nombre;
    this.ficha1 = fichas;
    this.ficha2 = new Ficha(fila, fila, fichas.getColor());
  }

  //Mueve cualquier ficha disponible
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

  public boolean fichaPertenece(Ficha ficha) {
    if(ficha.equals(this.ficha1) || ficha.equals(this.ficha2)){
      return true;
    }
    return false;
  }

  public Ficha buscarFicha(Ficha ficha){
    if(ficha.equals(this.ficha1)){
      return this.ficha1;
    }else if(ficha.equals(this.ficha2)){
      return this.ficha2;
    }
    return null;
  }


    public int[][] movimientosDisponiblesCord(Tablero tablero){
        int[][] coordenadas = new int[2][4];
        int[][] ficha1Cord = new int[2][2];
        int[][] ficha2Cord = new int[2][2];
         ficha1Cord= tablero.movimientosDisponiblesCord(ficha1);
        ficha2Cord= tablero.movimientosDisponiblesCord(ficha2);
        coordenadas [0][0]= ficha1Cord[0][0];
        coordenadas [0][1]= ficha1Cord[0][1];
        coordenadas [0][2]= ficha1Cord[1][0];
        coordenadas [0][3]= ficha1Cord[1][1];

        coordenadas [1][0]= ficha2Cord[0][0];
        coordenadas [1][1]= ficha2Cord[0][1];
        coordenadas [1][2]= ficha2Cord[1][0];
        coordenadas [1][3]= ficha2Cord[1][1];

        return coordenadas;
        //return aux1+aux2;
    }
  //Mover una ficha en especifico
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
        System.out.println("No se pudo mover");
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

  public int movimientosDisponibles(Tablero tablero) {
    //System.out.println("Calculando jugadas de " + this.nombre);
    int aux1 = tablero.movimientosDisponibles(ficha1);
    int aux2 = tablero.movimientosDisponibles(ficha2);
    //System.out.println("Ficha 1 -> " + aux1);
    //System.out.println("Ficha 2 -> " + aux2);
    return aux1 + aux2;
  }

 

  public void setFicha1(Ficha ficha) {
    this.ficha1 = ficha;
  }

  public void setFicha2(Ficha ficha) {
    this.ficha2 = ficha;
  }

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

  @Override
  public String toString() {
    return this.nombre + " Ficha " + ficha1 + " Ficha " + ficha2;
  }
}

