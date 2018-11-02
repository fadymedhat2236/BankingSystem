import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
public class Server
{

    public static void main(String[] args)
    {
        /*
        1-open the server socket.
        2-wait for client request.
        3-create I/O streams for communications.
        4-perform I/O operations with the client.
        5-close the sockets
        */
        try
        {
            //1.Listen
            //2.accept
            //3.create socket (I/O) with client
            ServerSocket s = new ServerSocket(1234);
            while (true)
            {

                Socket c = s.accept();

                System.out.println("ClientProcess Arrived");
                clientHandler ch = new clientHandler(c);
                //handle client in parrallel
                Thread t = new Thread(ch);
                t.start();
                //create new light weight process
                //and run in parallel and the main thread
                //continues

            }
            //s.close();
        } catch (IOException ex)
        {
            System.out.println("Something went wrong");
        }
    }
}
