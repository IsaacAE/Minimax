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
    System.out.println(juego.getTablero());
    System.out.println(
      "\n Este es el tablero inicial ¿Deseas mantenerlo asi? (S/N)"
    );
    if (validarSioNo()) {
      comenzarJuego();
    } else {
      asignarTablero();
    }
  }

  public void comenzarJuego() {}

  public void asignarTablero() {
    int[] arr;
    
    do{
        arr = validarCoordenada();
        for (int i : arr) {
          System.out.println(i);
        }
    }while(arr == null);
    
  }

  public int[] validarCoordenada() {
    String str;
    boolean condicion = false;
    do {
      try {
        System.out.println("Escoge una coordenada para la IA   ej: 2,0 )");
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
        System.out.println("Salimos de aqui");
        if (
          coordenadas[0] >= 0 &&
          coordenadas[0] <= 2 &&
          coordenadas[1] >= 0 &&
          coordenadas[1] <= 2
        ) {
          
          condicion = true;
          return coordenadas;
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
