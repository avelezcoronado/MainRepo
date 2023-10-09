/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cifradolibros;


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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;



/**
 *
 * @author federico cirett galan
 */


public class CifradoLibros {
    /**     * @param args the command line arguments */
    public static void main(String[] args) {
     
        Instant inicio = Instant.now();
        LinkedTreeMap<String,LinkedTreeMap> encoder = cargaCipher("encode_cipher.json");
        String directorio;
        String llave;
        if (args.length > 0) {
            directorio = args[0];
            llave = args[1];
        }
        else {
            directorio = ".";
            llave = "Todos estos moomentos se perderan coomo lagrimas en la lluvia";
        }
        //List<Libro> lista = cifradoLibros(directorio, encoder);
         
        
        List<File> lista_archivos =  obtenListaArchivos(directorio);
        List<Libro> lista_libros = cargarLibros(lista_archivos);
        cifradaLibrosParalelo(lista_libros,encoder,llave);
        for (Libro libro : lista_libros) {
            System.out.println(libro.original.substring(1763,1800)+" - "+libro.cifrado.substring(1763,1800));
            libro.guardaArchivo();
        }
        System.out.println("Total libros:"+lista_libros.size());        
        Instant fin = Instant.now();
        long tiempoComputo = Duration.between(inicio,fin).toMillis();
        System.out.println("Tiempo de computo: "+tiempoComputo+" milisegundos");
    }
    
    public static void cifradaLibrosParalelo(List<Libro> lista_libros,LinkedTreeMap<String,LinkedTreeMap> codificador,String llave){
        try{
            List<Future<String>> lista_textos= new ArrayList();
            int procesadores =  Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(procesadores);
            for (Libro libro : lista_libros) {
                String texto = libro.original;
                Future<String> cifrado  = executor.submit(new Cifrador(llave,texto,codificador));
                lista_textos.add(cifrado);
            }
            executor.awaitTermination(1, TimeUnit.SECONDS);
            executor.shutdown();
            for (int i = 0; i < lista_textos.size(); i++) {
            Libro book = lista_libros.get(i);
            book.cifrado = lista_textos.get(i).get().toString();
            
            
            
        }
        }catch (Exception ex){
            System.out.println("cifradoParalelo"+ex.getMessage());
        }
        
    } 
    
    public static List<Libro> cifradoLibros(String folder, LinkedTreeMap<String,LinkedTreeMap> encoder){
        List<Libro> lista_libros = new ArrayList<>();
        List<File> lista_archivos = obtenListaArchivos(folder);
        for (File archivo : lista_archivos) {
            System.out.println("Leyendo: "+  archivo.toString());
            Libro libro = new Libro(archivo.toString());
            libro.cargaLibro();
            libro.diccionario_cifrado = encoder;
            libro.llave = "Todos esos momentos se perderan como lagrimas en la lluvia";
            libro.cifrar();
            lista_libros.add(libro);
            
            
            
        }
        for (Libro libro : lista_libros) {
            libro.guardaArchivo();
            
        }
        return lista_libros;
    }
    
    public static List<Libro> cargarLibros(List<File> lista_archivos)  {
            List<Libro> lista_libros = new ArrayList<>();
            List<Future<String>> lista_textos = new ArrayList<>();
            try {            
                int procesadores = Runtime.getRuntime().availableProcessors();
                    System.out.println("Procesadores (cargaLibros):"+procesadores);
                ExecutorService executor = Executors.newFixedThreadPool(procesadores);
                    for (File archivo : lista_archivos) {
                        Future<String> texto = executor.submit(new Texto(archivo.toString()));
                        lista_textos.add(texto);
                        
                    
                }
                    executor.awaitTermination(1,TimeUnit.SECONDS);
                    executor.shutdownNow();
                    for (int i = 0; i < lista_archivos.size(); i++) {
                    Libro libro = new Libro(lista_archivos.get(i).toString());
                    libro.original = lista_textos.get(i).get(1,TimeUnit.SECONDS);
                    lista_libros.add(libro);
                    
                    
                }
                      
            }catch (Exception ex){
                System.out.println("cargaLibros:"+ ex.getMessage());
            }
            
            return lista_libros;         
        }
    
    public static List<File> obtenListaArchivos(String ruta) {
        List<File> lista_archivos  =  new ArrayList<>();
        File directorio = new File(ruta);
        File[] lista = directorio.listFiles();
        for  (File archivo : lista){
            String nombre_archivo =archivo.toString();
            int indice = nombre_archivo.lastIndexOf(".");
            if (indice > 0 ){
                String extension = nombre_archivo.substring(indice+1);
                if (extension.equals("html")){
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
