import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

class BaseLibro {
String titulo;
String url;

    public BaseLibro(String titulo, String url) {
        this.titulo = titulo;
        this.url = url;
    }

    public void display(){
        System.out.println(this.titulo + "        "+this.url);
    }
}

public class leer_libros {

    public static void main(String[] args) {
        if(args.length>0){
            Instant inicio = Instant.now();
            System.out.println(args[0]);
            ArrayList<BaseLibro> base = ReadTextFiles(args[0]);
            processList(base);
            Instant fin = Instant.now();
            Duration dif = Duration.between(inicio,fin);
            System.out.println("Archivos Descargados!  Tiempo:" + dif);
        }
        /*String URL = "https://www.gutenberg.org/cache/epub/40182/pg40182-images.html";
          String filename = "Oriente.html";
          DownloadWebPage(URL,filename);
        */

    }

    public static void processList(ArrayList<BaseLibro> lista){
        for (BaseLibro libro : lista){
            libro.display();
            String archivo = libro.titulo + ".html";
            DownloadWebPage(libro.url,archivo);
        }

    }
    public static ArrayList ReadTextFiles(String archivo){
        ArrayList<BaseLibro> array_libros = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            String texto[];
            while ((linea=reader.readLine())!=null){
                texto=linea.split("~");
                BaseLibro libro = new BaseLibro(texto[0],texto[1]);
                array_libros.add(libro);

            }
            reader.close();


        }catch (IOException ioe) {
            System.out.println(ioe.getLocalizedMessage());
        }
        return array_libros;
    }
    public static void DownloadWebPage(String webpage,String archivo_salida) {
        try{
            URL url = new URL(webpage);

            BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo_salida));

            String line;
            while((line= readr.readLine()) != null) {
                writer.write(line);
            }
            readr.close();
            writer.close();
            System.out.println("Archivo "+archivo_salida+ " descargado.");


        } catch (MalformedURLException mue){
            System.out.println("URL Malformado");
        }


        catch (IOException ie) {
            System.out.println("Error de I/O");
        }


    }
}
