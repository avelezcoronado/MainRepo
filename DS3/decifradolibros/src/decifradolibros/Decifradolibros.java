/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decifradolibros;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dii
 */
public class Decifradolibros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // *** Aplicar a la inversa el cifrado del CESAR
        Instant inicio = Instant.now();
        String directorio;
        String llave;
        if (args.length > 1) {
            directorio = args[0];
            llave = args[1];
        }
        else {
            directorio = ".";
            llave = "Todos esos momentos se perderan como lagrimas en la lluvia";
        }
        // Leer el archivo DECODER
        LinkedTreeMap<String,LinkedTreeMap>  decoder = cargaCipher("decode_cipher.json");
        // Cargar la lista de libros cifrados
        List<File> lista_archivos = 
               obtenListaArchivos(directorio, "cifrado"); 
               //extension = .cifrado
        for (File archivo : lista_archivos) {
            System.out.println(archivo.toString());
        }
        List<Libro> lista_libros = cargaArchivosSerial(lista_archivos);
        for (Libro libro : lista_libros) {
            System.out.println(libro.original.substring(1800,1840));
            
        }
        // Descifrar
        descifrar(lista_libros,llave,decoder);
        // Guardar archivo descifrado
        guardarLibros(lista_libros,"descifrado");
        
       Instant fin = Instant.now();
        long tiempoComputo = Duration.between(inicio,fin).toMillis();
        System.out.println("Tiempo de computo: "+tiempoComputo+" milisegundos");
        
               
    }
    
    public static void guardarLibros(List<Libro> lista_libros,String extension){
        for (Libro libro : lista_libros) {
            libro.guardaArchivo(extension);
            
        }
    }
    
    public static void descifrar(List<Libro> lista_libros,String llave,LinkedTreeMap<String,LinkedTreeMap>encoder){
        for (Libro libro : lista_libros) {
            System.out.println("Procesando:"+libro.titulo);
            libro.llave = llave;
            libro.diccionario_cifrado = encoder;
            libro.cifrar();
            
        }
    }
    public static List<Libro> 
        cargaArchivosSerial(List<File> lista_archivos) {
            List<Libro> lista_libros = new ArrayList<>();
            try {
                for (File archivo : lista_archivos) {
                    Libro libro = new Libro(archivo.toString());
                    libro.cargaLibro();
                    lista_libros.add(libro);
                }
            } catch (Exception ex) {
                System.out.println("cargaArchivosSerial:"+ex.getMessage());
            }
            return lista_libros;
        
    }
    public static List<File> obtenListaArchivos(String ruta,
            String ext) {
        List<File> lista_archivos = new ArrayList<>();
        File directorio = new File(ruta);
        File[] lista = directorio.listFiles();
        for (File archivo : lista) {
            String nombre_archivo = archivo.toString();
            int indice = nombre_archivo.lastIndexOf(".");
            if (indice > 0) {
                String extension = nombre_archivo.substring(indice+1);
                if(extension.equals( ext )) {
                    lista_archivos.add(archivo);
                }
            }
        }
        return lista_archivos;
    }
    public static LinkedTreeMap cargaCipher(String archivo) {
        LinkedTreeMap map=null;
        Gson gson = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            String texto="";
            while ( (linea= reader.readLine()) !=null ){
                texto += linea;
            }
            map = gson.fromJson(texto, LinkedTreeMap.class);
        } catch (IOException e) {
            System.out.println("cargaCipher "+e.getMessage());
        }
        return map;
    }
}
