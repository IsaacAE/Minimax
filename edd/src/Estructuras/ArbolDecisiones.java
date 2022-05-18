package edd.src.Estructuras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edd.src.Encerrado.Juego;

public class ArbolDecisiones<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    Juego juego;
    
    public ArbolDecisiones(Juego juego){
        this.super();
        this.juego = juego;
        construirArbol();
    }

    public void construirArbol(){
        String str = juego.getTablero();
    }
}
