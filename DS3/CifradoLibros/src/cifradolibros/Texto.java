/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cifradolibros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 *
 * @author aaronvelez
 */
public class Texto implements Callable<String> {
    String archivo;

    public Texto(String archivo) {
        this.archivo = archivo;
    }
    @Override
    
    
    public String call() throws Exception{
        String texto = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.archivo));
            String linea;
            while ((linea = reader.readLine()) !=null) {
            texto += linea;
        }
        }catch (IOException e){
                System.out.println("Text: "+ e.getMessage());
                }
        return texto;
        
    }
    
    
}
