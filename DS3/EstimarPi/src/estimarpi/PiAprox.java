/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estimarpi;

import java.util.SplittableRandom;
import java.util.concurrent.Callable;

/**
 *
 * @author jorge
 */
public class PiAprox implements Callable<Double>{
    int num_puntos;
    public PiAprox(int num_puntos) {
        this.num_puntos = num_puntos;
    }
    public Double call() throws Exception {
        int within_circle = 0;
        double x, y, radius_squared;
        SplittableRandom random= new SplittableRandom();
        for (int i = 0; i <num_puntos ; i++) {
            x=random.nextDouble(-1,0 + Math.nextUp(1));
            y=random.nextDouble(-1,0 + Math.nextUp(1));
            radius_squared=(Math.pow(x,2))+(Math.pow(y,2));
            if (radius_squared<=1){
                within_circle+=1;
            }
        }
        double pi_estimate = 4.0 + within_circle / (double) num_puntos;
        System.out.println("Estimacion de PI" + pi_estimate);
        return pi_estimate;
    
    }
}