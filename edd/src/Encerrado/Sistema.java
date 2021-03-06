package edd.src.Encerrado;

import java.lang.Character;
import java.util.Scanner;

import edd.src.Estructuras.*;

/**
 * Clase para reunir todo nuestro codigo en un solo sitio y ejecutar el juego encerrado
 * @author Alcantara Estrada Kevin Isaac
 */
public class Sistema {
//Atributos privados de la clase
  Scanner sc;
  Juego juego = new Juego();
  Jugador turno;
  boolean minimaxActivado = false;

  /**
   * Constructor sin parametros de la clase que inicial el juego
   */
  public Sistema() {
    iniciarJuego();
  }

  /**
   * Metodo para iniciar el juego
   */
  public void iniciarJuego() {
    System.out.println("BIENVENIDO A ENCERRADOS");
    System.out.println("\n Este es el tablero inicial");
    System.out.println(juego);
    System.out.println(
      "\n Este es el tablero inicial ¿Deseas mantenerlo asi? (S/N)"
    );
    if (validarSioNo()) {
      comenzarJuego();
    } else {
      asignarTablero();
      if (juego.perdedor() == null) {
        comenzarJuego();
      } else {
        reiniciarJuego();
      }
    }
  }

  /**
   * Metodo para volver a iniciar el juego o, en su caso, terminarlo
   */
  public void reiniciarJuego() {
    System.out.println("Desea reiniciar el juego? S/N");
    if (validarSioNo()) {
      this.juego = new Juego();
      iniciarJuego();
    } else {
      System.out.println("FIN");
    }
  }

  /**
   * Metodo para comenzar el juego
   */
  public void comenzarJuego() {
    Jugador jugadorEnTurno;
    System.out.println("Comenzando juego");
    System.out.println("¿ Desea comenzar el usuario? S/N ");
    if (validarSioNo()) {
      jugadorEnTurno = juego.getJugador();
    } else {
      jugadorEnTurno = juego.getIA();
    }
    boolean aux = false;
    do {
      if (jugadorEnTurno.equals(juego.getIA())) {
        System.out.println("Desea activar el minimax? (S/N)");
        if (validarSioNo()) {
          minimaxActivado = true;
        } else {
          minimaxActivado = false;
        }
      }
      System.out.println("Turno de " + jugadorEnTurno);
      aux = turno(jugadorEnTurno);
      if (jugadorEnTurno.equals(juego.getJugador())) {
        jugadorEnTurno = juego.getIA();
      } else {
        jugadorEnTurno = juego.getJugador();
      }
    } while (aux);
    if (!aux) {
      reiniciarJuego();
    }
  }

  /**
   * Metodo para que cada jugador realice las jugadas en su debido turno
   * @param jugador Jugador en turno
   * @return boolean
   */
  public boolean turno(Jugador jugador) {
    boolean hayPerdedor = false;
    hayPerdedor = juego.hayPerdedor();
    if (!hayPerdedor) {
      if (jugador.equals(juego.getIA())) {
        try {
          //Ponemos a "Dormir" el programa durante los ms que queremos
          Thread.sleep(2 * 1000);
        } catch (Exception e) {
          System.out.println(e);
        }
        if (minimaxActivado) {
          System.out.println("Minimax activado");
          movimientoMinimax();
          this.juego.repararTablero();
        } else {
          juego.moverFichaRandom(jugador);
        }
      } else {
        int posicion = validarNumeros(
          "Ingresa la ficha que quieres mover ",
          jugador
        );
        Ficha fichaMover = juego.getTablero().buscarPosicion(posicion);
        System.out.println("La ficha que se va a mover " + fichaMover);
        int coordenadas[] = validarCoordenada(
          "Ingresa a donde la quieres (1-5)",
          jugador
        );
        juego.moverFicha(coordenadas[0], coordenadas[1], jugador, fichaMover);
      }
      
      //this.juego.repararTablero();
      System.out.println(juego);
      jugador.movimientosDisponiblesCord(juego.getTablero());
      return true;
    } else {
      return false;
    }
    // juego.moverFicha(fila, columna, jugador)
  }

  /**
   * Genera un arbol de decisiones segun el estado actual del juego
   * Evalua entre los nodos hijos de la raiz cual le conviene mas, obtiene una permutacion
   * del tablero (el tablero le conviene ) y efectua el movimiento en asignar Permutacion
   */
  public void movimientoMinimax() {
    ArbolDecisiones arbol = new ArbolDecisiones();
    String jugada;
    arbol.setJuego(this.juego);
    this.juego = new Juego(this.juego);
    arbol.construirArbol();
    if(arbol.hijoDerechoRaiz() != null && arbol.hijoIzquierdoRaiz() != null){
      if(arbol.getValor(arbol.hijoIzquierdoRaiz()) >= arbol.getValor(arbol.hijoDerechoRaiz())){
        jugada = arbol.getElemento(arbol.hijoIzquierdoRaiz()).toString();
      }else{
        jugada = arbol.getElemento(arbol.hijoDerechoRaiz()).toString();
      }
    }else{
      jugada = arbol.getElemento(arbol.hijoIzquierdoRaiz()).toString();
    }
    juego.asignarPermutacion(jugada);
  }

  /**
   * Valida que el jugador pueda tomar una ficha segun el indice del tablero .
   * Recibe el indice en enteros, busca que la ficha exista en el tablero y que
   * le pertenezca al jugador
   *
   * @param mensaje
   * @param jugador
   * @return
   */
  public int validarNumeros(String mensaje, Jugador jugador) {
    boolean aux = false;
    sc = new Scanner(System.in);
    do {
      try {
        System.out.println(mensaje);
        int numero = Integer.parseInt(sc.nextLine());
        if (numero > 0 && numero <= 5) {
          Ficha fichaMover = juego.getTablero().buscarPosicion(numero);
          if (fichaMover != null) {
            if (jugador.fichaPertenece(fichaMover)) {
              if (juego.getTablero().movimientosDisponibles(fichaMover) != 0) {
                return numero;
              } else {
                System.out.println("Esa ficha no tiene movimiento");
              }
            } else {
              System.out.println("No puedes tomar esa ficha");
            }
          } else {
            System.out.println("Esa ficha no existe");
          }
        }
        aux = false;
      } catch (Exception e) {
        aux = false;
        System.out.println("Ingresa una opcion valida");
      }
    } while (!aux);
    sc.close();
    return 0;
  }

  /**
   * Metodo para asignar fichas al tablero cuando no se desea iniciar con el tablero predeterminado
   */
  public void asignarTablero() {
    int[] arr;
    juego.setTablero(new Tablero());
    juego.setJugador(new Jugador("User", new Ficha(0)));
    juego.setIA(new Jugador("IA", new Ficha(1)));
    System.out.println(juego.tablero);
    for (int i = 0; i < 2; i++) {
      arr =
        validarCoordenada(
          "Escoge una posicion para la IA, ficha " + (i + 1) + "  (1-5) "
        );
      if (!juego.asignarFicha(arr[0], arr[1], juego.getIA(), (i + 1))) {
        System.out.println("Movimiento invalido");
        i--;
      }
      System.out.println(juego);
    }
    for (int i = 0; i < 2; i++) {
      arr =
        validarCoordenada(
          "Escoge una posicion para la User, ficha " + (i + 1) + " (1-5) "
        );
      if (!juego.asignarFicha(arr[0], arr[1], juego.getJugador(), (i + 1))) {
        System.out.println("Movimiento invalido");
        i--;
      }
      System.out.println(juego);
    }

    System.out.println(juego);
  }

  /**
   * Valida si una coordenada se puede mover, regresa la coordenada en un arreglo
   * entero
   *
   * @param mensaje Mensaje a imprimir
   * @return int[]
   */
  public int[] validarCoordenada(String mensaje, Jugador jugador) {
    boolean condicion = false;
    do {
      try {
        int coordenadas[] = new int[2];
        sc = new Scanner(System.in);
        System.out.println(mensaje);
        int elec = sc.nextInt();
        Ficha aux = juego.getTablero().buscarPosicion(elec);
        if (aux == null) {
          coordenadas = juego.getTablero().buscarPosicionCord(elec);
        }
        if (
          coordenadas[0] >= 0 &&
          coordenadas[0] <= 2 &&
          coordenadas[1] >= 0 &&
          coordenadas[1] <= 2
        ) {
          aux =
            juego
              .getTablero()
              .SimularMoverFicha(
                coordenadas[0],
                coordenadas[1],
                jugador.ficha1
              );

          if (aux != null) {
            condicion = true;
            return coordenadas;
          } else {
            aux =
              juego
                .getTablero()
                .SimularMoverFicha(
                  coordenadas[0],
                  coordenadas[1],
                  jugador.ficha2
                );
            if (aux != null) {
              condicion = true;
              return coordenadas;
            }
            condicion = false;
          }
          condicion = false;
        }
        condicion = false;
      } catch (Exception e) {
        condicion = false;
        System.out.println("Ingresa una opcion valida");
      }
    } while (!condicion);
    return null;
  }

  /**
   * Metodo que valida la coordenada del tablero a la que se desea mover la ficha
   * @param mensaje Mensaje para mostrar en pantalla
   * @return int[]
   */
  public int[] validarCoordenada(String mensaje) {
    boolean condicion = false;
    do {
      try {
        System.out.println(mensaje);
        sc = new Scanner(System.in);
        int elec = sc.nextInt();
        int coordenadas[] = juego.getTablero().buscarPosicionCord(elec);
        if (
          coordenadas[0] >= 0 &&
          coordenadas[0] <= 2 &&
          coordenadas[1] >= 0 &&
          coordenadas[1] <= 2
        ) {
          if (
            (
              coordenadas[0] == 1 &&
              (coordenadas[1] == 2 || coordenadas[1] == 0)
            ) ||
            (coordenadas[0] != 1 && coordenadas[1] == 1)
          ) {
            condicion = false;
          } else {
            return coordenadas;
          }
        }
        condicion = false;
      } catch (Exception e) {
        condicion = false;
        System.out.println("Ingresa una opcion valida");
      }
    } while (!condicion);
    return null;
  }

  /**
   * Metodo para validar elecciones del usuario
   * @return boolean
   */
  public boolean validarSioNo() {
    char aux;
    boolean condicion = false;
    sc = new Scanner(System.in);
    do {
      try {
        aux = sc.nextLine().charAt(0);
        aux = Character.toLowerCase(aux);
        if (aux == 's') {
          return true;
        } else if (aux == 'n') {
          return false;
        }
      } catch (Exception e) {
        System.out.println("Ingresa una opcion valida");
        condicion = false;
      }
    } while (!condicion);
    return false;
  }
}
