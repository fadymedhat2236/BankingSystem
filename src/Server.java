import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class Server
{

    public static void main(String[] args)
    {
        try
        {
            ServerSocket s = new ServerSocket(1234);
            constructions DB = new constructions();
            DB.create_database();
            DB.create_table_of_users();
            DB.create_table_of_transactions() ;
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
