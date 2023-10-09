import java.util.SplittableRandom;
 import java.time.Duration;
 import java.time.Instant;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.concurrent.Callable;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.concurrent.Future;
 import java.util.concurrent.TimeUnit;
 /**
  *
  * @author dii
  */
 public class TiempoAproxPI {

     /**
      * @param args the command line arguments
      */
     public static void main(String[] args) {
        generarTable();
     
         
         // Checar numero de puntos (desde argumentos)
         Instant inicio = Instant.now();
         int num_puntos=100;
         int num_procesadores =1;
         String tipo = "C";
         if (args.length>0) {
             num_puntos = Integer.parseInt(args[0]);
             if (num_puntos < 0) {
                 num_puntos =  num_puntos * -1;
             }
             if (num_puntos == 0) {
                 num_puntos = 200;
             }
         }
         // Checar numero de procesadores (desde argumentos)
         if (args.length > 1) {
             num_procesadores = Integer.parseInt(args[1]);
             if (num_procesadores < 0) {
                 num_procesadores = num_procesadores *-1;
             }
             if (num_procesadores == 0) {
                 num_procesadores = Runtime.getRuntime().availableProcessors();
             }
         }
         // Checar tipo de trabajo concurrente
         if (args.length > 2) {
             tipo = args[2];
         }
         //Hacer calculo serial o paralelo
         double pi=0;
         if (num_procesadores == 1) {
             System.out.println("Procesamiento serial");
             pi = estimar_pi_serial(num_puntos,10);
         } else {
             System.out.println("Proesamiento concurrente");
             if (tipo.equals("C")== true) {
                 System.out.println("Estimador");
             } else {
                 System.out.println("Futures");
                 pi = run_test_futures(num_puntos,10, num_procesadores);
             }
         }
         // Desplegar PI
         System.out.println("PI estimado="+pi);
         Instant fin = Instant.now();
         long tiempoComputo = Duration.between(inicio,fin).toMillis();
         System.out.println("Tiempo de computo: "+tiempoComputo+" milisegundos");
         System.out.println("Procesadores:"+num_procesadores);
     } 
     
     
  
            
        
        
            
            
        
        
        
        
        
    }
     
     
     
     public static Double run_test_futures(int n_points, int n_repeats, int processors) {
         Double mejor_pi=0.0;
         try {
             //int processors = Runtime.getRuntime().availableProcessors();
             System.out.println("Processors:" + processors);
             ExecutorService executor = Executors.newFixedThreadPool(processors);
             List<Future<Double>> lista = new ArrayList<Future<Double>>();
             Callable callable = new PiAprox(n_points);
             for (int i = 0; i < n_repeats; i++) {
                 Future<Double> future_pi = executor.submit(callable);
                 lista.add(future_pi);
             }
             executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
             Double pi = 3.14159265;
             Double menor = pi;
             Double menor_dif = pi;
             Double dif = 0.0;
             for (Future<Double> future : lista) {
                 dif = pi - future.get();
                 System.out.println("Dif:" + String.format("%,.8f", dif));
                 if (Math.abs(dif) < menor) {
                     menor = dif;
                     menor_dif = future.get();
                     System.out.println("menor_dif:" + String.format("%,.8f", menor_dif) + "                                      menor:" + String.format("%,.8f", menor));
                 }
             }
             executor.shutdownNow();
             System.out.println("Pi:" + String.format("%,.8f", pi) + " estimado:" + String.format("%,.8f", menor_dif) + " diferencia:" + String.format("%,.8f", menor));
             mejor_pi = menor_dif;
         } catch (Exception ex) {
             System.out.println("EXCEPTION:" + ex.getMessage());
         }
         return mejor_pi;

     }
     
     

     public static double estimar_pi_serial(int n_puntos, int n_veces) {
         Double pi = 3.14159265;
         Double pi_obtenido = 0.0;
         Double menor = pi;
         Double menor_dif = pi;
         Double dif = 0.0;
         for (int i = 0; i < n_veces; i++) {
             pi_obtenido = estimar_pi(n_puntos);
             dif = pi - pi_obtenido;
             System.out.println("Dif:" + String.format("%,.8f", dif));
             if (Math.abs(dif) < menor) {
                 menor = dif;
                 menor_dif = pi_obtenido;
                 System.out.println("menor_dif:" + String.format("%,.8f", menor_dif) + "                                      menor:" + String.format("%,.8f", menor));
             }
         }
         System.out.println("+----------+----------+-----------+");
         System.out.println("|   Pi     | Estimado |  Dif.     |");
         System.out.println("+----------+----------+-----------+");
         System.out.println(String.format("|%,.8f|", pi) + String.format("%,.8f|", menor_dif) + String.format("%,.8f|", menor));
         System.out.println("+----------+----------+-----------+");
         return menor_dif;
     }


     public static double estimar_pi(int n_puntos) {
         double pi_estimado = 0;
         int n_en_circulo = 0;
         double x, y, radio_cuadrado;
         SplittableRandom random = new SplittableRandom();
         for (int i = 0; i < n_puntos; i++) {
             x = random.nextDouble(-1, 0+Math.nextUp(1));
             y = random.nextDouble(-1, 0+Math.nextUp(1));
             radio_cuadrado = ( Math.pow(x,2)+Math.pow(y, 2));
             if (radio_cuadrado <= 1) {
                 n_en_circulo += 1;
             }
         }
         pi_estimado = 4 * n_en_circulo / (double) n_puntos;
         return pi_estimado;
     }
     public static class PiAprox implements Callable<Double> {

         int n_points;

         public PiAprox(int n_points) {
             this.n_points = n_points;
         }

         @Override
         public Double call() throws Exception {
             int within_circle = 0;
             double x, y, radius_squared;
             SplittableRandom random = new SplittableRandom();
             for (int i = 0; i < n_points; i++) {
                 x = random.nextDouble(-1, 0 + Math.nextUp(1));
                 y = random.nextDouble(-1, 0 + Math.nextUp(1));
                 radius_squared = Math.pow(x, 2) + Math.pow(y, 2);
                 if (radius_squared <= 1) {
                     within_circle += 1;
                 }
             }
             double pi_estimate = 4.0 * within_circle / (double) n_points;
             System.out.println("Estimación de PI:" + pi_estimate);
             return pi_estimate;
         }
     }

 }