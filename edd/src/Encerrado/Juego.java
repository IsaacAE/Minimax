package edd.src.Encerrado;
public class Juego {
    Tablero tablero = new Tablero();
    Jugador jugador = new Jugador("User", new Ficha(0));
    Jugador IA = new Jugador("IA", new Ficha(1));
 
    public Juego(){
        tableroPredeterminado();
    }

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

    public void tableroPredeterminado(){
        moverFicha(0, 2, jugador);
        moverFicha(2, 0, jugador);
        moverFicha(0, 0, IA);
        IA.ficha1.setFila(0);
        IA.ficha1.setColumna(2);
        moverFicha(2, 2, IA);
        //moverFicha(2, 2, IA);
        //moverFicha(0, 0, IA);
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

