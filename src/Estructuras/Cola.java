package edd.src.Estructuras;

public class Cola<T> extends PushPop<T> {

    /**
     * Agregar al final
     */
    public void push(T elemento) {
        if(elemento == null){
            throw new IllegalArgumentException("");
        }
        Nodo aux = new Nodo(elemento);
        if (longi == 0) {
            cabeza = aux;
            longi++;
            ultimo = cabeza;
            return;
        }
        Nodo temp = cabeza;
        while (temp.siguiente != null) {
            temp = temp.siguiente;
        }
        temp.siguiente = aux;
        aux.siguiente = null;
        ultimo = aux;
        longi++;

    }

    /**
     * Sale por la izquierda, entra por la derecha
     */
    public Cola<T> clone() {
        Cola<T> nueva = new Cola<>();
        if (this.isEmpty()) {
            return nueva;
        }
        nueva.push(this.cabeza.elemento);
        Nodo aux = cabeza;
        while(aux.siguiente != null){
            aux = aux.siguiente;
            nueva.push(aux.elemento);
        }
        return nueva;
    }

    /**
     * Sale por la izquierda, entra por la derecha
     */
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "";
        }
        String regreso = this.cabeza.elemento.toString();
        Nodo n = this.cabeza;
        while (n.siguiente != null) {
            regreso += ", " + n.siguiente.elemento.toString();
            n = n.siguiente;
        }
        return regreso;
    }
}