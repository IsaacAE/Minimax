package edd.src.Encerrado;
public class Jugador {
    public String nombre;
    //public String fichas;
    public Ficha ficha1;
    public Ficha ficha2;

    public Jugador(String nombre, Ficha fichas){
        this.nombre = nombre;
        this.ficha1 = fichas;
        this.ficha2 = new Ficha(fichas.getColor());
    }

    public Tablero moverFicha(Tablero tablero, int fila, int columna){
        Ficha aux = tablero.moverFicha(fila, columna, ficha1);
        if(aux == null){
            aux = tablero.moverFicha(fila, columna, ficha2);
            if(aux == null){
                return null;
            }else{
                System.out.println("Se movio la ficha 2");
                setFicha2(aux);
            }

        }else{
            System.out.println("Se movio la ficha 1");
            setFicha1(aux);
        }
        return tablero;
    }

    public void setFicha1(Ficha ficha){
        this.ficha1 = ficha;
    }

    public void setFicha2(Ficha ficha){
        this.ficha2 = ficha;
    }

    @Override
    public String toString(){
        return this.nombre + ficha1 + ficha2;
    }


}
