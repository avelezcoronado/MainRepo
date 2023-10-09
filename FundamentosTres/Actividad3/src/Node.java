class Node {
    int clave;
    String nombre;
    int edad;
    String direccion;

    public Node(int clave,String nombre, int edad, String direccion) {
        this.clave =clave;
        this.nombre = nombre;
        this.edad = edad;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Clave: "+ clave + ", Nombre: " + nombre + ", Edad: " + edad + ", Dirección: " + direccion;
    }
}
