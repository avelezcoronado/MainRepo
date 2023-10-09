/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cifradolibros;

import com.google.gson.internal.LinkedTreeMap;
import java.util.concurrent.Callable;

/**
 *
 * @author aaronvelez
 */
public class Cifrador implements Callable<String>{
    String llave;
    String texto;
    String encriptado;
    LinkedTreeMap<String,LinkedTreeMap> codificador;

    public Cifrador(String llave, String texto, LinkedTreeMap<String, LinkedTreeMap> codificador) {
        this.llave = llave;
        this.texto = texto;
        this.codificador = codificador;
    }
    
    
    
     @Override
     
    public String call() throws Exception{
     char[] texto = this.texto.toCharArray();
        try {
            String llave = this.llave.toUpperCase();
            char[] llave_chars = llave.toCharArray();
        
            int len_key = llave_chars.length;
        
            for (int i = 0; i < texto.length ; i++) {
                int key_index = i % len_key;
                char key = llave_chars[key_index];
                String skey = ""+key;
                LinkedTreeMap<String,String> diccionario = this.codificador.get(skey);
                String sc = ""+texto[i];
                texto[i] = diccionario.get(sc).charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Error en cifrado");
            System.out.println(e.getMessage());
        }
        this.encriptado = String.copyValueOf(texto);
        return this.encriptado;
    }
    
    
    
}
