package edd.src.Encerrado;
public class Jugador {
    public String nombre;
    //public String fichas;
    public Ficha ficha;

    public Jugador(String nombre, Ficha fichas){
        this.nombre = nombre;
        this.ficha = fichas;
    }

    public boolean moverFicha(){
        return false;
    }

    @Override
    public String toString(){
        return this.nombre + ficha;
    }


}
