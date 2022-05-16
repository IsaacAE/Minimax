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
    System.out.println("Va a comenzar " + jugadorEnTurno);
  }

  public void turno(Jugador jugador) {
    System.out.println("Ingresa la ficha que quieres mover ");
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

  //Valida al recibir una cadena y revisa si es posible moverla
  public int[] validarCoordenada(String mensaje, Jugador jugador) {
    String str;
    boolean condicion = false;
    do {
      try {
        System.out.println(mensaje);
        sc = new Scanner(System.in);
        str = sc.nextLine();
        if (str.charAt(1) != ',') {
          System.out.println("Entro aqui");
          condicion = false;
          continue;
        }
        int coordenadas[] = new int[2];
        coordenadas[0] = Character.getNumericValue(str.charAt(0));
        coordenadas[1] = Character.getNumericValue(str.charAt(2));
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

  //Valida la entretrada de un valor tipo coordenada, valores entre 0 y 2: (0,2)
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
