public class Lista {
        Nodo primero;
        boolean ordenada;

        public Lista(boolean ordenada) {
            this.primero = null;
            this.ordenada = ordenada;
        }

        public boolean estaVacia() {
            return primero == null;
        }

        public void agregarNodo(int dato) {
            Nodo nuevoNodo = new Nodo(dato);

            if (ordenada) {
                if (primero == null || dato <= primero.dato) {
                    nuevoNodo.siguiente = primero;
                    primero = nuevoNodo;
                } else {
                    Nodo actual = primero;
                    while (actual.siguiente != null && actual.siguiente.dato < dato) {
                        actual = actual.siguiente;
                    }
                    nuevoNodo.siguiente = actual.siguiente;
                    actual.siguiente = nuevoNodo;
                }
            } else {
                nuevoNodo.siguiente = primero;
                primero = nuevoNodo;
            }
        }

        public void eliminarNodo(int dato) {
            if (primero == null) {
                return;
            }

            if (primero.dato == dato) {
                primero = primero.siguiente;
                return;
            }

            Nodo actual = primero;
            while (actual.siguiente != null && actual.siguiente.dato != dato) {
                actual = actual.siguiente;
            }

            if (actual.siguiente != null) {
                actual.siguiente = actual.siguiente.siguiente;
            }
        }

        public int contarNodos() {
            int contador = 0;
            Nodo actual = primero;
            while (actual != null) {
                contador++;
                actual = actual.siguiente;
            }
            return contador;
        }

        public void mostrarLista() {
            Nodo actual = primero;
            while (actual != null) {
                actual.mostrarDato();
                actual = actual.siguiente;
            }
            System.out.println();
        }

        public void mostrarPosicion(int posicion) {
            Nodo actual = primero;
            int contador = 0;
            while (actual != null) {
                if (contador == posicion) {
                    System.out.println("El dato en la posición " + posicion + " es: " + actual.dato);
                    return;
                }
                contador++;
                actual = actual.siguiente;
            }
            System.out.println("No existe un elemento en la posición " + posicion);
        }

        public void mostrarMayoresQue(int valor) {
            Nodo actual = primero;
            while (actual != null) {
                if (actual.dato > valor) {
                    actual.mostrarDato();
                }
                actual = actual.siguiente;
            }
            System.out.println();
        }
    }





