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

            ServerSocket s = new ServerSocket(Protocol.getServer_fromIp(Protocol.get_IP()).getPortNo());
            constructions DB = new constructions();
            DB.create_database();
            DB.create_table_of_users();
            DB.create_table_of_transactions() ;
            
            while (true)
            {
                Socket c = s.accept();
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
