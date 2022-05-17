package edd.src.Encerrado;

import java.lang.Character;
import java.util.Scanner;

public class Sistema {

  Scanner sc;
  Juego juego = new Juego();
  Jugador turno;

  public Sistema() {
    iniciarJuego();
  }

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

  public void reiniciarJuego() {
    System.out.println("Desea reiniciar el juego? S/N");
    if (validarSioNo()) {
      iniciarJuego();
    } else {
      System.out.println("FIN");
    }
  }

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
      System.out.println("Turno de " + jugadorEnTurno);
      aux = turno(jugadorEnTurno);
      if (jugadorEnTurno.equals(juego.getJugador())) {
        jugadorEnTurno = juego.getIA();
      } else {
        jugadorEnTurno = juego.getJugador();
      }
    } while (aux);
  }

  public boolean turno(Jugador jugador) {
    boolean hayPerdedor = false;
    hayPerdedor = juego.hayPerdedor();
    if (!hayPerdedor) {
      if (jugador.equals(juego.getIA())) {
        try {
          //Ponemos a "Dormir" el programa durante los ms que queremos
          Thread.sleep(3*1000);
          juego.moverFichaRandom(jugador);
       } catch (Exception e) {
          System.out.println(e);
       }
      } else {
        int posicion = validarNumeros(
          "Ingresa la ficha que quieres mover ",
          jugador
        );
        Ficha fichaMover = juego.getTablero().buscarPosicion(posicion);
        System.out.println("La ficha que se va a mover " + fichaMover);
        int coordenadas[] = validarCoordenada(
          "Ingresa a donde la quieres mover (1-5)",
          jugador
        );
        for (int i : coordenadas) {
          System.out.println("Coordenadas " + i);
        }
        juego.moverFicha(coordenadas[0], coordenadas[1], jugador, fichaMover);
      }

      System.out.println(juego);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Valida que el jugador pueda tomar una ficha segun el indice del tablero .
   * Recibe el indice en enteros, busca que la ficha exista en el tablero y que
   * le pertenezca al jugador
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

  public void asignarTablero() {
    int[] arr;
    juego.setTablero(new Tablero());
    juego.setJugador(new Jugador("User", new Ficha(0)));
    juego.setIA(new Jugador("IA", new Ficha(1)));
    System.out.println(juego.tablero);
    for (int i = 0; i < 2; i++) {
      arr =
        validarCoordenada(
          "Escoge una coordenada para la IA, ficha " + (i + 1) + "  ej: 2,0 "
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
          "Escoge una coordenada para la User, ficha " + (i + 1) + "  ej: 2,0 "
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
   * Valida si una coordenada se puede mover, regresa la coordenada en un arreglo entero
   * @param mensaje
   * @return
   */
  public int[] validarCoordenada(String mensaje, Jugador jugador) {
    boolean condicion = false;
    do {
      try {
        int coordenadas[] = new int[2];
        sc = new Scanner(System.in);
        System.out.println(mensaje);
        int eleccion = sc.nextInt();
        switch (eleccion) {
          case 1:
            coordenadas[0] = 0;
            coordenadas[1] = 0;
            break;
          case 2:
            coordenadas[0] = 0;
            coordenadas[1] = 2;
            break;
          case 3:
            coordenadas[0] = 1;
            coordenadas[1] = 1;
            break;
          case 4:
            coordenadas[0] = 2;
            coordenadas[1] = 0;
          case 5:
            coordenadas[0] = 2;
            coordenadas[1] = 2;
            break;
          default:
          throw new Exception();
            //break;
        }
        /*if (str.charAt(1) != ',') {
          System.out.println("Entro aqui");
          condicion = false;
          continue;
        }*/

        //coordenadas[0] = Character.getNumericValue(str.charAt(0));
        //coordenadas[1] = Character.getNumericValue(str.charAt(2));
        if (
          coordenadas[0] >= 0 &&
          coordenadas[0] <= 2 &&
          coordenadas[1] >= 0 &&
          coordenadas[1] <= 2
        ) {
          //Verifica si alguna ficha puede ser colocada en dicha coordenada
          //System.out.println("If");
          Ficha aux;
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

  public int[] validarCoordenada(String mensaje) {
    String str;
    boolean condicion = false;
    do {
      try {
        System.out.println(mensaje);
        sc = new Scanner(System.in);
        str = sc.nextLine();
        if (str.charAt(1) != ',') {
          condicion = false;
          continue;
        }
        int coordenadas[] = new int[2];
        coordenadas[0] = Character.getNumericValue(str.charAt(0));
        coordenadas[1] = Character.getNumericValue(str.charAt(2));
        //System.out.println("Salimos de aqui");
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
