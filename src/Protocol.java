import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Protocol {
    Socket socket;
    DataOutputStream dout;
    DataInputStream din;

    public Protocol(Socket socket) {
        this.socket = socket;
        try{
            this.din=new DataInputStream(socket.getInputStream());
            this.dout=new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){
            //error occurred
        }
    }
    // Client Protocol
    public void startClient(){
        while(true){
            try{

                String server_string = din.readUTF().toString();
                Scanner user_input = new Scanner(System.in);
                String user_response;
                System.out.println(server_string);

                user_response = user_input.nextLine();
                dout.writeUTF(user_response);
                //TODO: logic for client

            }catch(IOException e){
                //handle error
            }
        }
    }

    // server protocol
    public void startServer(){
        while(true){
            try {
                String readString;
                dout.writeUTF(Constants.INITIAL_STEP);
                readString=din.readUTF().toString();
                //TODO: logic for server
                if(readString.equals(Constants.LOGIN)){
                    System.out.println("Logged in");
                }
                else if(readString.equals(Constants.SIGNUP)){
                    System.out.println("signed up");
                }
                else{
                    dout.writeUTF(Constants.ERROR);
                    break;
                }

            }
            catch(IOException e){
                //handle the error
            }
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDout() {
        return dout;
    }

    public void setDout(DataOutputStream dout) {
        this.dout = dout;
    }

    public DataInputStream getDin() {
        return din;
    }

    public void setDin(DataInputStream din) {
        this.din = din;
    }
}
