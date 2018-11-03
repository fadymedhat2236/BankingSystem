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
            Protocol protocol = new Protocol(c);
            protocol.startServer();
            protocol.getDin().close();
            protocol.getDout().close();
            protocol.getSocket().close();
        } catch (Exception e)
        {
            System.out.println("Something went wrong ");
        }

    }

}