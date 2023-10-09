package lee_libros_paralelo;

import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Lee_libros_paralelo {

    public static void main(String[] args) {

        if (args.length > 0){
            Instant inicio = Instant.now();
            System.out.println(args[0]);
            ArrayList<BaseLibro> base = ReadTextFile(args[0]);
            processList(base,700);
            saveBooks(base);
            Instant fin = Instant.now();
            Duration dif = Duration.between(inicio,fin);
            System.out.println("Archivos Descargados!. Tiempo: " + dif);
        }
    }
    public static void saveBooks (ArrayList<BaseLibro> lista){
        for (BaseLibro libro : lista){
            libro.saveText();
        }
    }

    public static void processList (ArrayList<BaseLibro> lista, int segundos){
        ArrayList<Future<String>> lista_textos = new ArrayList<>();
        try {
            int processors = Runtime.getRuntime().availableProcessors(); // OBTENIENDO CANTIDAD DE PROCESADORES DEL DISPOSITIVO
            System.out.println("Processors available: " + processors); // CUANTOS PROCESADORES HAY
            ExecutorService executor = Executors.newFixedThreadPool(processors);
            for (BaseLibro libro: lista) {
                libro.display();
                Future<String> texto = executor.submit(new Webpage(libro.url));
                lista_textos.add(texto); // AQUI ES LO QUE PERMITE QUE NO ESPERE A TERMINAR UNA DESCARGA PARA CONTINUAR CON LA OTRA
            }
            executor.awaitTermination(segundos, TimeUnit.MILLISECONDS); // DESPUES DE 10 SEGUNDOS TERMINA EL PROCESO SIN IMPORTAR QUE NO HAYA TERMINADO
                                                                    // ES NECESARIO
            executor.shutdown();
            // RECUPERAR TEXTOS
            for (int i = 0; i < lista.size(); i++) {
                BaseLibro book = lista.get(i);
                String html_texto = lista_textos.get(i).get(segundos, TimeUnit.MILLISECONDS);
                book.addText(html_texto);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
       // return lista_textos;
    }

    public static ArrayList ReadTextFile(String archivo)
    {
        ArrayList<BaseLibro> array_libros = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            String texto[];
            while ((linea = reader.readLine()) !=null){
                texto = linea.split("~");
                BaseLibro libro = new BaseLibro(texto[0], texto[1]);
                array_libros.add(libro);
            }
            reader.close();
        }catch (IOException ex){
            System.out.println(ex.getLocalizedMessage());

        }
        return array_libros;
    }
}
