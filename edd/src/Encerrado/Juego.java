package edd.src.Encerrado;
public class Juego {
    Tablero tablero = new Tablero();
    Jugador jugador = new Jugador("User", new Ficha(0));
    Jugador IA = new Jugador("IA", new Ficha(1));
 
    public boolean moverFicha(int fila, int columna, Jugador jugador){
        Tablero aux = jugador.moverFicha(tablero, fila, columna);
        if(aux == null){
            return false;
        }
        tablero = aux;
        if(jugador.nombre.equals("User")){
            setJugador(jugador);
        }else{
            setIA(jugador);
        }
        return true;
    }


    @Override
    public String toString(){
        tablero.pintarTablero();
        return "\n Jugadores: \n"+jugador+"\n"+IA;
    }
    public void setTablero(Tablero tablero){
        this.tablero = tablero;
    }
    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }
    public void setIA(Jugador jugador){
        this.IA = jugador;
    }
}

