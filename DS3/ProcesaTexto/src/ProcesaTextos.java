import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcesaTextos {
    public static void main(String[] args) {

        HashMap<String,Integer> hashm = cargaStopWords(args[2]);
        List<Texto>lista_texto=cargaTextosSerial(args[0],args[1]);
        procesaListaTextos(lista_texto);



    }
    public static void procesaListaTextos(List<Texto> lista_texto){
        for (Texto text:lista_texto) {
            text.cuentaPalabras();
            text.display(40);
            text.cuentaPalabraRepetidas();
            text.displayPalabrasRepetidas(10);


        }




    }

    public static HashMap<String,Integer> cargaStopWords(String archivo){
        HashMap<String,Integer> hash_words =  new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            while ( (linea= reader.readLine()) !=null ) {
                hash_words.put(linea,0);

            }

        } catch (IOException e) {
            System.out.println("CargarTexto:"+e.getMessage());
        }
        return hash_words;
    }
    public static ArrayList<Texto> cargaTextosSerial(String folder, String extension){
        ArrayList<Texto> lista= new ArrayList<>();
        try{
            List<File> archivos= obtenListaArchivos(folder,extension);
            for (File archivo:archivos) {
                Texto txt= new Texto(archivo.toString());
                txt.cargarTexto();
                lista.add(txt);
            }
        }catch (Exception e){
            System.out.println("CargaTextosSerial:"+e.getMessage());

        }
        return lista;
    }
    public static List<File> obtenListaArchivos(String ruta, String ext) {
        List<File> lista_archivos = new ArrayList<>();
        File directorio = new File(ruta);
        File[] lista =  directorio.listFiles();
        for (File archivo : lista) {
            String nombre_archivo = archivo.toString();
            int indice = nombre_archivo.lastIndexOf(".");
            if (indice > 0){
                String extension = nombre_archivo.substring(indice + 1);
                if (extension.equals(ext)){
                    lista_archivos.add(archivo);
                }
            }
        }
        return  lista_archivos;
    }
}