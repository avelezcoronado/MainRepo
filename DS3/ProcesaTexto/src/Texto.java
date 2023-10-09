import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Texto {
    String filename;
    String texto;
    String palabras[];
    int total_palabras;
    int palabras_repetidas;
    int palabras_stop;
    int palabras_significantes;
    ArrayList<HashMap> top10;
    HashMap<String, Integer> diccionario;

    public Texto(String filename){
        this.filename = filename;
        this.texto  = "";
        this.total_palabras  = 0;
        this.palabras_repetidas= 0;
        this.palabras_stop =0;
        this.palabras_significantes = 0;


    }

    public void cargarTexto(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            String linea;
            String texto ="";
            while ( (linea= reader.readLine()) !=null ) {
                texto += linea;
            }
            this.texto = texto;
        } catch (IOException e) {
            System.out.println("CargarTexto:"+e.getMessage());
        }
    }
public void cuentaPalabras(){
        this.texto = this.texto.toLowerCase();
String[] cadenas = this.texto.split("[^a-zA-Z]+");
this.palabras = cadenas;
this.total_palabras = cadenas.length;

}

public void  cuentaPalabraRepetidas(){
    HashMap<String, Integer> diccionario = new HashMap<>();
    for (String palabra: palabras) {
        if( diccionario.containsKey(palabra)){
            int conteo = diccionario.get(palabra);
            diccionario.put(palabra,conteo+ 1);
        }else {
            diccionario.put(palabra,1);
        }


    }
    this.palabras_repetidas = diccionario.size();
    this.diccionario = diccionario;
}

    public void displayPalabrasRepetidas(int num_pal){
        ArrayList<String> palabras = new ArrayList<>(this.diccionario.keySet());
        ArrayList<Integer> conteo = new ArrayList<>(this.diccionario.values());
        System.out.println(">>>>>>>>>"+this.filename);
        int cuenta_pal = 0;
        for (int i = 0; i < palabras.size(); i++) {
            if (cuenta_pal<num_pal) {
                if (conteo.get(i) > 1) {
                    System.out.println(palabras.get(i) + ": " + conteo.get(i));
                    cuenta_pal++;
                }
            }

        }
    }
    public void display(int n){
        System.out.println("Total palabras: "+ this.total_palabras);
        System.out.println(this.texto.substring(n,n+n));
    }
}
