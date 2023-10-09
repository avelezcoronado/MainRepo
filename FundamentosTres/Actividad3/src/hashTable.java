import java.util.LinkedList;

class TablaHash {
    LinkedList<Node>[] tabla; // LinkedList para las collisiones

    // Constructor
    public TablaHash(int tamaño) {
        this.tabla = new LinkedList[tamaño];
        for (int i = 0; i < tamaño; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    public int hashAritmetica(int clave) {
        return clave % tabla.length;

    }


    public int hashPlegamiento(int clave) {
        int suma = 0;
        while (clave > 0) {
            suma += clave % 10;
            clave /= 10;
        }
        return suma % tabla.length;
    }

    // Hash function: Multiplicative
    public int hashMultiplicacion(int clave) {
        double A = 0.6180339887;
        return (int) (tabla.length * (clave * A % 1));
    }

    // Agregando los nodos pero usamos chaining para los collisions
    public void agregarNodo(int clave, Node nodo) {
        int indice = hashAritmetica(clave); // Change the hash function if needed
        tabla[indice].add(nodo);
    }


    public void mostrarTabla() {
        for (int i = 0; i < tabla.length; i++) {
            System.out.print("Índice " + i + ": ");
            if (!tabla[i].isEmpty()) {
                for (Node nodo : tabla[i]) {
                    System.out.println(nodo);
                }
            } else {
                System.out.println("Vacío");
            }
        }
    }
}

