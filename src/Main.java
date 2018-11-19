import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Client client = new Client();
        String id = "7s8X" ;
        String password = "1234" ;
        Transaction transaction  = new Transaction("Martin" , "Emil" , 100) ;
        String AddNewUser = "UPDATE users SET " +DBconstants.balance +
                " = " + client.getAmountOfmoney()
                +" WHERE unique_id " + "'" + client.getId() + "'" ;
        System.out.println(AddNewUser);

    }
}
