import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
public class Server
{

    public static void main(String[] args)
    {
        try
        {
            ServerSocket s = new ServerSocket(1234);
            while (true)
            {

                Socket c = s.accept();

                System.out.println("ClientProcess Arrived");
                clientHandler ch = new clientHandler(c);
                //handle client in parallel
                Thread t = new Thread(ch);
                t.start();
            }
        } catch (IOException ex)
        {
            System.out.println("Something went wrong");
        }
    }
}
