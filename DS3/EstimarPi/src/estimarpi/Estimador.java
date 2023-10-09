/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estimarpi;

import java.util.SplittableRandom;

/**
 *
 * @author aaronvelez
 */
public class Estimador implements Runnable {
        int n_puntos;
        public void run(){
            this.estimar_pi();
        }
        public Estimador(int n_puntos){
            this.n_puntos = n_puntos;
            
        }
         public void estimar_pi(){
       
        int n_en_circulo=0;
        double x, y, radio_cuadrado;
        SplittableRandom random= new SplittableRandom();
        for (int i = 0; i <this.n_puntos ; i++) {
            x=random.nextDouble(-1,0 + Math.nextUp(1));
            y=random.nextDouble(-1,0 + Math.nextUp(1));
            radio_cuadrado=(Math.pow(x,2))+(Math.pow(y,2));
            if (radio_cuadrado<=1){
                n_en_circulo+=1;
            }
        }
        double pi_estimado=4*n_en_circulo/ (double)n_puntos;
             System.out.println("Pi estimado es:"+ pi_estimado);
       

    }
    
    
}
