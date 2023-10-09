package lee_libros_paralelo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BaseLibro {
        String title;
        String url;
        String archivo;
        String texto;


        public BaseLibro(String title, String url) {
            this.title = title;
            this.url = url;
            this.archivo = this.title.trim() + ".html";
        }

        public void addText(String texto){
            try {
                this.texto = texto;

            }catch (Exception e){
                System.out.println("Exception on addText " + e.getMessage());
            }
        }

        public void saveText (){
            try {
                System.out.println("Saving " + this.archivo);
                if (this.texto != null || this.texto.isEmpty()){
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.archivo))){
                        writer.write(this.texto);
                        writer.close();
                    }
                }
            } catch (IOException ioe){
                System.out.println(this.archivo + "I/O exception" + ioe.getMessage());
            } catch (Exception e){
                System.out.println(this.title + " " + e.getMessage());
            }
        }
        public void display(){
            System.out.println(this.title + "  " + this.url);
        }

}

