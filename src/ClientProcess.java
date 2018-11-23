import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class ClientProcess {
    public static void main(String[] args)
    {
        /*
        Scanner in = new Scanner(System.in);
        System.out.println(Constants.ENTER_IP);
        String ip = in.nextLine();
        System.out.println(Constants.ENTER_PORT_NO);
        int port_no = in.nextInt();
        */
        try{
            ServerObject serverObject = Protocol.getServer_data();
            Socket socket = new Socket(serverObject.getIp(),serverObject.getPortNo());
            Protocol protocol = new Protocol(socket);
            protocol.startClient();
            protocol.getDin().close();
            protocol.getDout().close();
            protocol.getSocket().close();
        }catch (IOException e){
            //handle error
        }

    }
}
