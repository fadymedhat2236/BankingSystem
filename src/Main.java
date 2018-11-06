import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Client client = new Client();
        String id = "7s8X" ;
        String password = "1234" ;
        String query = "SELECT "+
                DBconstants.unique_id + ","+
                DBconstants.balance + ","+
                DBconstants.username + ","+
                DBconstants.password + ","+
                "FROM users " +
                "WHERE unique_id = " +
                "'" + id + "'"
                + " AND password = " +
                "'" + password + "'";
        System.out.println(query);
    }
}
