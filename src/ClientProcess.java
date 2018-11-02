import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class ClientProcess {
    public static void main(String[] args)
    {
        /*
        1-create a socket object
        2-create I/O streams for the communications
        3-perform I/O streams with the server
        4-close the socket when done
         */
        try {
            Socket socket = new Socket("127.0.0.1", 1234);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            while(true){
                String input;
                Scanner sc=new Scanner(System.in);
                input=sc.nextLine();
                dos.writeUTF(input);
                if(input.equals(Constants.SIGNUP)){
                    String name=sc.nextLine();//username
                    dos.writeUTF(name);
                    String password=sc.nextLine();//password
                    dos.writeUTF(password);
                    String money=sc.nextLine();//initial amount of money
                    dos.writeUTF(money);
                    String generatedID=dis.readUTF().toString();
                    Client client=new Client(name,password,Float.valueOf(money),generatedID);
                    //TODO:store the client in the database or the file
                }
                if(input.equals(Constants.LOGOUT)){
                    break;
                }
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
