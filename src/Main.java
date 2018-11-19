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
        String insert_new_transaction = "INSERT INTO transactions ("+DBconstants.from_id+"," +
                DBconstants.to_id + "," +  DBconstants.amount + "," +
                DBconstants.amount_from_before + "," +
                DBconstants.amount_to_before
                + ")"+
                "VALUES " +
                "(" + "'" + transaction.getFrom_id()+ "'" + "," +
                "'" + transaction.getTo_id() + "'" + ","+
                transaction.getAmount()+ "," +
                transaction.getAmount_from_before() + "," +
                transaction.getAmount_to_before()
                + ")" ;
        System.out.println(insert_new_transaction);

    }
}
