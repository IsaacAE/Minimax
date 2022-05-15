package edd.src.Encerrado;

public class Sistema {
    Juego juego = new Juego();
    Jugador turno;

    public Sistema(){
        iniciarJuego();
    }
    public void iniciarJuego(){
        System.out.println("BIENVENIDO A ENCERRADOS");
        System.out.println("\n Este es el tablero inicial");
        //System.out.println(juego);
        System.out.println(juego.getTablero());
        System.out.println("\n Este es el tablero inicial ¿Deseas mantenerlo asi?");
    }
}
