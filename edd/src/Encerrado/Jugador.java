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
    public Jugador(String nombre, Ficha fichas, int fila){
        this.nombre = nombre;
        this.ficha1 = fichas;
        this.ficha2 = new Ficha(fila,fila,fichas.getColor());
    }

    //Mueve cualquier ficha disponible 
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


    public boolean fichaPertenece(Ficha ficha){
        return false;
    }
    //Mover una ficha en especifico
    public Tablero moverFicha(Tablero tablero, int fila, int columna, Ficha ficha){
        if(ficha != null && (ficha.equals(this.ficha1) || ficha.equals(this.ficha2))){
            Ficha aux = tablero.moverFicha(fila, columna, ficha);
            if(aux == null){
                System.out.println("No se pudo mover");
            }else{
                    if(this.ficha1.equals(ficha)){
                        this.ficha1 = ficha;
                    }else if(this.ficha2.equals(ficha)){
                        this.ficha2 = ficha;
                    }
                }
        }else{
            System.out.println("No existe esa ficha");
        }
       /* if(aux == null){
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
        }*/
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

    public int[][] movimientosDisponiblesCord(Tablero tablero){
        return null;
        //return aux1+aux2;
    }

    public void setFicha1(Ficha ficha){
        this.ficha1 = ficha;
    }

    public void setFicha2(Ficha ficha){
        this.ficha2 = ficha;
    }

    @Override
    public String toString(){
        return this.nombre + " Ficha " +ficha1 +" Ficha "+ ficha2;
    }


}
