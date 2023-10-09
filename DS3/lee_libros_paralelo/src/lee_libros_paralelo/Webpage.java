package lee_libros_paralelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class Webpage implements Callable<String> {
    String url;
    public Webpage(String url){
        this.url = url;
    }
    @Override
    public String call() throws Exception{
        String texto = "";
        try {
            URL url = new URL(this.url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return texto;
    }
}
