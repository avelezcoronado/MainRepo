import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lista lista = null;
        boolean ordenada = false;

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Crear lista");
            System.out.println("2. Consultar si la lista será ordenada");
            System.out.println("3. Agregar nodo");
            System.out.println("4. Verificar si la lista está vacía");
            System.out.println("5. Eliminar nodo");
            System.out.println("6. Mostrar número de nodos");
            System.out.println("7. Mostrar todos los elementos");
            System.out.println("8. Mostrar posición de un nodo");
            System.out.println("9. Mostrar nodos mayores que un valor");
            System.out.println("10. Salir");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("¿La lista será ordenada? (true/false)");
                    ordenada = scanner.nextBoolean();
                    lista = new Lista(ordenada);
                    break;
                case 2:
                    System.out.println("La lista está ordenada: " + ordenada);
                    break;
                case 3:
                    System.out.println("Ingrese el dato a agregar:");
                    int dato = scanner.nextInt();
                    lista.agregarNodo(dato);
                    break;
                case 4:
                    System.out.println("La lista está vacía: " + lista.estaVacia());
                    break;
                case 5:
                    System.out.println("Ingrese el dato a eliminar:");
                    int datoEliminar = scanner.nextInt();
                    lista.eliminarNodo(datoEliminar);
                    break;
                case 6:
                    System.out.println("Número de nodos en la lista: " + lista.contarNodos());
                    break;
                case 7:
                    System.out.println("Elementos en la lista:");
                    lista.mostrarLista();
                    break;
                case 8:
                    System.out.println("Ingrese la posición a consultar:");
                    int posicion = scanner.nextInt();
                    lista.mostrarPosicion(posicion);
                    break;
                case 9:
                    System.out.println("Ingrese el valor a comparar:");
                    int valor = scanner.nextInt();
                    System.out.println("Nodos mayores que " + valor + ":");
                    lista.mostrarMayoresQue(valor);
                    break;
                case 10:
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
}
