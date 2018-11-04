import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        try {
            FileWriter writer = new FileWriter("dummyDB.txt", false);
            writer.write("ayhaga");
            writer.write("\r\n");   // write new line
            writer.write("Good Bye!");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
