import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class clientHandler implements Runnable
{

    private Socket c;
    private DataInputStream dis;
    private DataOutputStream dos;
    public clientHandler(Socket c)
    {
        this.c = c;
    }

    @Override
    public void run()
    {
        try
        {
            DataOutputStream dos = new DataOutputStream(c.getOutputStream());
            DataInputStream dis = new DataInputStream(c.getInputStream());
            this.dis=dis;
            this.dos=dos;
            //4.perform IO with client

            this.initialFunction();

            //5. close comm with client
            dos.close();
            dis.close();
            c.close();
        } catch (Exception e)
        {
            System.out.println("Something went wrong ");
        }

    }

    private void initialFunction() {
        try {
            String readString;
            dos.writeUTF(Constants.INITIAL_STEP);
            readString=dis.readUTF().toString();
            if(readString.equals(Constants.LOGIN)){
                login();
            }
            else if(readString.equals(Constants.SIGNUP)){
                signUp();
            }
            else{
                dos.writeUTF(Constants.ERROR);
            }

        }
        catch(IOException e){
            //handle the error
        }
    }

    public void login() {
        try {
            dos.writeUTF(Constants.ENTER_YOUR_ACCOUNT_NUMBER);
            String accountNumber= dis.readUTF().toString().toLowerCase();
            dos.writeUTF(Constants.ENTER_YOUR_PASSWORD);
            String password=dis.readUTF().toString().toLowerCase();

            //check for username and password
            //TODO:check for the name and password


        }
        catch(IOException e){
            //handle error
        }
    }
    public void repeatedLogic(){
        try {
            dos.writeUTF(Constants.REPEATED_STRING);
            String response=dis.readUTF().toString().toLowerCase();
            // current balance
            if(response.equals(Constants.VIEW_CURRENT_BALANCE)){
                //TODO:
            }
            // deposit
            else if(response.equals(Constants.DEPOSIT_MONEY)){
                //TODO:
            }
            // withdraw
            else if(response.equals(Constants.WITHDRAW_FROM_YOUR_BALANCE)) {
                //TODO:
            }
            // transfer
            else if(response.equals(Constants.TRANSFER_MONEY)){
                //TODO:
            }
            // view transaction
            else if(response.equals(Constants.VIEW_TRANSACTIONS)){
                //TODO:
            }
            // logout
            else if(response.equals(Constants.LOGOUT)){
                //TODO:
            }
            //error
            else{
                // handle error
            }
        }
        catch(IOException e){
            //handle error
        }
    }

    private void signUp() {
        //TODO:
    }



}