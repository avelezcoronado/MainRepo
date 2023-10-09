import java.util.Random;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Random random_num = new Random();
        Scanner scanner = new Scanner(System.in);
        TablaHash tabla = new TablaHash(10); // Cambiar el tamaño de la tabla según lo que necesites

        int opcion;
        do {
            System.out.println("\nMenú:");
            System.out.println("1. Usar función de aritmética modular");
            System.out.println("2. Usar función de plegamiento");
            System.out.println("3. Usar función de multiplicación");
            System.out.println("4. Mostrar tabla");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                case 2:
                case 3:
                    scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la edad: ");
                    int edad = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese la dirección: ");
                    String direccion = scanner.nextLine();
                    // Aqui generaramos una clave random
                    int clave = random_num.nextInt(1000);

                    Node nodo = new Node(clave, nombre, edad, direccion);

                    switch (opcion) {
                        case 1:
                            tabla.agregarNodo(tabla.hashAritmetica(clave), nodo);
                            break;
                        case 2:
                            tabla.agregarNodo(tabla.hashPlegamiento(clave), nodo);
                            break;
                        case 3:
                            tabla.agregarNodo(tabla.hashMultiplicacion(clave), nodo);
                            break;
                    }
                    break;
                case 4:
                    tabla.mostrarTabla();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}