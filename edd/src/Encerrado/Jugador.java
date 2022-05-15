package edd.src.Encerrado;
public class Jugador {
    public String nombre;
    
    //De forma predeterminada, todas las fichas comienzan en 0,0.
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

    public int movimientosDisponibles(Tablero tablero){
        System.out.println("Calculando jugadas de "+this.nombre);
        int aux1 = tablero.movimientosDisponibles(ficha1);
        int aux2 = tablero.movimientosDisponibles(ficha2);
        System.out.println("Ficha 1 -> "+aux1);
        System.out.println("Ficha 2 -> "+aux2);
        return aux1+aux2;
    }

    public void setFicha1(Ficha ficha){
        this.ficha1 = ficha;
    }

    public void setFicha2(Ficha ficha){
        this.ficha2 = ficha;
    }

    @Override
    public String toString(){
        return this.nombre + " Ficha 1 " +ficha1 +" Ficha 2 "+ ficha2;
    }


}
