


import com.sun.deploy.util.SessionState;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class constructions{

    public String driver = "com.mysql.cj.jdbc.Driver";
    public String url = "jdbc:mysql://localhost";
    public String username = "root";
    public String pass = "";


    public Connection create_database () {
        Connection con  = null ;
        try{
            // Register jdbc driver
            Class.forName(driver);
            //Open a connection
            con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            String sql = "CREATE DATABASE IF NOT EXISTS Database3";
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
            statement.close();
           // System.out.println("Coneecteion succedded");
        }
        catch (Exception e){
            System.out.println("Error "+ e);
        }
        return con ;
    }

    public  Connection create_table_of_users(){
        Connection con = null ;
        try{
            url = url + "/Database3" ;
            // Register jdbc driver
            Class.forName(driver);
            //Open a connection
            con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    DBconstants.id_auto_increment+" int NOT NULL AUTO_INCREMENT," +
                    DBconstants.unique_id+" VARCHAR(4) ," +
                    DBconstants.balance+" double," +
                    DBconstants.username+" VARCHAR(255) NOT NULL," +
                    DBconstants.password+" VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ")" ;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql) ;

            // System.out.println("Table created successfully");
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
        return con;
    }

    public Connection create_table_of_transactions(){
        Connection con = null ;
        try{
            String url = "jdbc:mysql://localhost/Database3";
            // Register jdbc driver
            Class.forName(driver);
            //Open a connection
            con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                    DBconstants.id + " int NOT NULL AUTO_INCREMENT," +
                    DBconstants.from_id + " VARCHAR(4)," +
                    DBconstants.to_id + " VARCHAR(4)," +
                    DBconstants.amount+" double," +
                    DBconstants.amount_from_before + " double," +
                    DBconstants.amount_to_before + " double," +
                    DBconstants.from_name + " VARCHAR (15)," +
                    DBconstants.to_name + " VARCHAR (15)," +
                    "PRIMARY KEY (id)" +
                    ")" ;
            Statement statement = con.createStatement();
            statement.executeUpdate(sql) ;
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
        return con ;
    }

    public void insert_new(Client client)
    {
        try
        {
            Class.forName(driver);
            //Open a connection
            String url = "jdbc:mysql://localhost/Database3";
            Connection con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            String AddNewUser = "INSERT INTO users ("+DBconstants.unique_id+"," +
                    DBconstants.balance + "," + DBconstants.username + "," + DBconstants.password
                    +")"+
                    "VALUES " +
                    "("+"'"+client.getId()+"'"+","+
                    client.getAmountOfmoney()+","+
                    "'"+client.getName()+"'" + "," +
                    "'"+client.getPassword() +"'"+ ")" ;
            stmt.executeUpdate(AddNewUser) ;

           // System.out.println("Inserted successfully");
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
    }

    public void update_client(Client client){
        try
        {
            Class.forName(driver);
            //Open a connection
            String URL = "jdbc:mysql://localhost/database3";
            Connection con  = DriverManager.getConnection(URL , username , pass) ;
            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            String AddNewUser = "UPDATE users SET " +DBconstants.balance +
                    " = " + client.getAmountOfmoney()
                    +" WHERE unique_id =" + "'" + client.getId() + "'" ;
            stmt.executeUpdate(AddNewUser) ;

        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
    }

    public Client getClient(String password , String id){
        Client retrieved = new Client();
        try{
            String url = "jdbc:mysql://localhost/Database3";
            Class.forName(driver);
            //Open a connection
            Connection con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            //Retrieve client
            String query = "SELECT "+
                    DBconstants.unique_id + ","+
                    DBconstants.balance + ","+
                    DBconstants.username + ","+
                    DBconstants.password +
                    " FROM users " +
                    "WHERE "  + DBconstants.unique_id  + " = " +
                    "'" + id + "'"
                    + " AND password = " +
                    "'" + password + "'";
            ResultSet resultSet = stmt.executeQuery(query) ;
            while(resultSet.next()){
                retrieved.setAmountOfMoney(resultSet.getDouble("balance"));
                retrieved.setId(resultSet.getString("unique_id"));
                retrieved.setPassword(resultSet.getString("password"));
                retrieved.setName(resultSet.getString("username"));
            }
            resultSet.close();
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
        return retrieved ;
    }

    public Client get_client (String id){
        Client retrieved = new Client() ;
        try{
            String url = "jdbc:mysql://localhost/Database3";
            Class.forName(driver);
            //Open a connection
            Connection con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            //Retrieve client
            String query = "SELECT "+
                    DBconstants.unique_id + ","+
                    DBconstants.balance + ","+
                    DBconstants.username + ","+
                    DBconstants.password +
                    " FROM users " +
                    "WHERE "  + DBconstants.unique_id  + " = " +
                    "'" + id + "'"
                    ;
            ResultSet resultSet = stmt.executeQuery(query) ;
            while(resultSet.next()){
                retrieved.setAmountOfMoney(resultSet.getDouble("balance"));
                retrieved.setId(resultSet.getString("unique_id"));
                retrieved.setPassword(resultSet.getString("password"));
                retrieved.setName(resultSet.getString("username"));
            }
            resultSet.close();
        }
        catch (Exception e) {
            System.out.println("Error : " + e);
        }return retrieved;
    }

    public void insert_transaction(Transaction transaction){
        try{
            String url = "jdbc:mysql://localhost/Database3";
            Class.forName(driver);
            //Open a connection
            Connection con  = DriverManager.getConnection(url , username , pass) ;

            //System.out.println("Found it");
            Statement stmt = con.createStatement();

            String insert = "INSERT INTO transactions (" +
                    DBconstants.from_id + "," +
                    DBconstants.to_id  +  "," +
                    DBconstants.amount + ","  +
                    DBconstants.amount_from_before + "," +
                    DBconstants.amount_to_before + "," +
                    DBconstants.from_name + "," +
                    DBconstants.to_name +
                    ")" + " VALUES (" +
                    "'" + transaction.getFrom_id() + "'" + "," +
                    "'" + transaction.getTo_id() + "'" + "," +
                    transaction.getAmount() + "," +
                    transaction.getAmount_from_before() + "," +
                    transaction.getAmount_to_before() + "," +
                    "'"+ transaction.getFrom_name() +"'" + "," +
                    "'" + transaction.getTo_name() +"'" +
                    ")"
                    ;
            stmt.executeUpdate(insert) ;
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
    }

    public ArrayList<Transaction> getTransactions(Client client){
        ArrayList<Transaction>all_transactions = new ArrayList<>();
        try{
            String url = "jdbc:mysql://localhost/Database3";
            Class.forName(driver);
            //Open a connection
            Connection con  = DriverManager.getConnection(url , username , pass) ;

            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            //retrieve all transactions
            /*
            "id int NOT NULL AUTO_INCREMENT," +
                    "from VARCHAR(4)," +
                    "to VARCHAR(4)," +
                    "amount double," +
                    "amount_from_before double" +
                    "amount_to_before double" +
             */
            String query = "SELECT " +
                    DBconstants.from_id + "," +
                    DBconstants.to_id + "," +
                    DBconstants.amount + "," +
                    DBconstants.amount_to_before + "," +
                    DBconstants.amount_from_before + "," +
                    DBconstants.from_name + "," +
                    DBconstants.to_name  +
                    " FROM transactions " +
                    "WHERE to_id = "+"'"+client.getId()+"'"+" OR from_id = "
                    +"'"+client.getId()+"'" ;
            ResultSet resultSet = stmt.executeQuery(query) ;
            while (resultSet.next()){
                all_transactions.add(new Transaction());
                int index_now = all_transactions.size()-1;
                //Setting attributes
                all_transactions.get(index_now).setAmount(resultSet.getDouble(DBconstants.amount));
                all_transactions.get(index_now).setTo_id(resultSet.getString(DBconstants.to_id));
                all_transactions.get(index_now).setFrom_id(resultSet.getString(DBconstants.from_id));
                all_transactions.get(index_now).setAmount_from_before
                        (resultSet.getDouble(DBconstants.amount_from_before));
                all_transactions.get(index_now).setAmount_to_before(resultSet.getDouble
                        (DBconstants.amount_to_before));
                all_transactions.get(index_now).setFrom_name(resultSet.getString(
                        (DBconstants.from_name)));
                all_transactions.get(index_now).setTo_name(resultSet.getString(
                        (DBconstants.to_name)));
            }
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
        return all_transactions ;

    }

    boolean check_if_user_is_here (String id){
        try{
        String url = "jdbc:mysql://localhost/Database3";

        Class.forName(driver);
        //Open a connection
        Connection con  = DriverManager.getConnection(url , username , pass) ;

        //System.out.println("Found it");
        Statement stmt = con.createStatement();

        String query =  //Retrieve client
                "SELECT "+
                        DBconstants.unique_id + ","+
                        DBconstants.balance + ","+
                        DBconstants.username + ","+
                        DBconstants.password +
                        " FROM users " +
                        "WHERE "  + DBconstants.unique_id  + " = " +
                        "'" + id + "'" ;
        ResultSet resultSet =  stmt.executeQuery(query) ;
        if(resultSet.next())
            return true ;
        else return false ;
        }
        catch (Exception io){
            System.out.println("ERROR :  " +io);
        }
        return false ;
    }

   public boolean transfer_money(Transaction transaction){
        try
        {
            String url = "jdbc:mysql://localhost/Database3";

            Class.forName(driver);
            //Open a connection
            Connection con  = DriverManager.getConnection(url , username , pass) ;

            //System.out.println("Found it");
            Statement stmt = con.createStatement();

            String query =  //Retrieve client
                    "SELECT "+
                DBconstants.unique_id + ","+
                DBconstants.balance + ","+
                DBconstants.username + ","+
                DBconstants.password +
                " FROM users " +
                "WHERE "  + DBconstants.unique_id  + " = " +
                "'" + transaction.getTo_id() + "'" ;
            if(check_if_user_is_here(transaction.getTo_id())==false) {
                return false;
            }

            else
            {
                double from_before = 0 , to_before = 0  ;
                String from_name="",to_name="";
                String query1 = "SELECT "+
                        DBconstants.unique_id + ","+
                        DBconstants.balance + ","+
                        DBconstants.username + ","+
                        DBconstants.password +
                        " FROM users " +
                        "WHERE "  + DBconstants.unique_id  + " = " +
                        "'" + transaction.getFrom_id() + "'" ;
                ResultSet resultSet = stmt.executeQuery(query1) ;
                if(resultSet.next()){
                    from_before = resultSet.getDouble(DBconstants.balance) ;
                    from_name = resultSet.getString(DBconstants.username);
                }

                else {
                    from_before = transaction.getAmount_from_before() ;
                    from_name = transaction.getFrom_name();
                }

                query1 = "SELECT "+
                        DBconstants.unique_id + ","+
                        DBconstants.balance + ","+
                        DBconstants.username + ","+
                        DBconstants.password +
                        " FROM users " +
                        "WHERE "  + DBconstants.unique_id  + " = " +
                        "'" + transaction.getTo_id() + "'" ;
                resultSet = stmt.executeQuery(query1) ;

                if (resultSet.next()){
                    to_before = resultSet.getDouble(DBconstants.balance) ;
                    to_name = resultSet.getString(DBconstants.username);
                }

                String insert_new_transaction = "INSERT INTO transactions ("+DBconstants.from_id+"," +
                        DBconstants.to_id + "," +  DBconstants.amount + "," +
                        DBconstants.amount_from_before + "," +
                        DBconstants.amount_to_before   +","+
                        DBconstants.from_name + ","+
                        DBconstants.to_name
                        + ")"+
                        "VALUES " +
                        "(" + "'" + transaction.getFrom_id()+ "'" + "," +
                        "'" + transaction.getTo_id() + "'" + ","+
                        transaction.getAmount()+ "," +
                        from_before + "," +
                        to_before + ","+
                       "'"+ from_name+"'"+","+
                        "'"+to_name+"'"
                        + ")" ;
                stmt.executeUpdate(insert_new_transaction) ;

                // Update from and to clients

                double data1  = from_before-transaction.getAmount() ;
                String update = "UPDATE users SET " +DBconstants.balance +
                        " = " + data1
                        +" WHERE unique_id in" + "('" + transaction.getFrom_id() + "')" ;
                stmt.executeUpdate(update) ;

                data1 = to_before + transaction.getAmount() ;
                update = "UPDATE users SET " +DBconstants.balance +
                        " = " + data1
                        +" WHERE unique_id in" + "('" + transaction.getTo_id() + "')" ;
                stmt.executeUpdate(update) ;
                return true ;
            }
        }
        catch (Exception io)
        {
            System.out.println("Error  : " + io);
        }
        return  true ;
    }
}
