package edd.src.Encerrado;
/**
 *Clase que representa una ficha en el tablero
 *@author Alcantara Estrada Kevin Isaac
 *@auhtor Rubio Haro Mauricio
 */
public class Ficha{
//Atributos privados de la clase

    private int color;//El color de la ficha: 1 para rojo, 0 para azul y -1 para ficha vacia
    private String figura;// ñas fichas se representan con un * del color correspondiente o como " " si es ficha vacia

    //Para pintar de rojo
public static final String rojo = "\u001B[31m";

//Para ointar de azul
public static final String azul = "\u001B[34m";

//Constructor de la ficha que recibe un numero para determinar su color, 1 para roja, 0 para azul
public Ficha(int color){
    if(color==1){
        this.color=1;
        this.figura = rojo+"*";//pintamos rojo
    }else{
        this.color=0;
        this.figura = azul+"*";//pintamos azul
    }
}

/**
 * Metodo constructor sin parametros para generar una ficha "vacia"
 */
public Ficha(){
    this.color=-1;
    this.figura=" ";
}

/**
 * Metodo que devuelve el valor del atributo color de la ficha
 * @return int
 */
public int getColor(){
    return this.color;
}

/**
 * Representacion en cadena de la ficha
 * @return String
 */
public String toString(){
    return this.figura;
}
}