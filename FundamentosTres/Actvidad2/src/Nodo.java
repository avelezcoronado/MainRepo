public class Nodo {

    int dato;
    Nodo siguiente;

    public Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public void mostrarDato() {
        System.out.print(dato + " ");
    }
}
