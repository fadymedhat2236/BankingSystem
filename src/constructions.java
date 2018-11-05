


import com.sun.deploy.util.SessionState;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class constructions{

    public String driver = "com.mysql.jdbc.Driver";
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
            String sql = "CREATE DATABASE IF NOT EXISTS Database1";
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            System.out.println("Coneecteion succedded");
        }
        catch (Exception e){
            System.out.println("Error "+ e);
        }
        return con ;
    }

    public  Connection create_table_of_users(){
        Connection con = null ;
        try{
            url = url + "/Database1" ;
            // Register jdbc driver
            Class.forName(driver);
            //Open a connection
            con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "unique_id VARCHAR(4)" +
                    "balance double," +
                    "username VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
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
            // Register jdbc driver
            Class.forName(driver);
            //Open a connection
            con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "id int NOT NULL AUTO_INCREMENT," +
                    "from VARCHAR(4)," +
                    "to VARCHAR(4)," +
                    "amount double," +
                    "amount_from_before double" +
                    "amount_to_before double" +
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
            Connection con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            String AddNewUser = "INSERT INTO users (unique_id,balance,username,password) VALUES " +
                    "("+"'"+client.getId()+"'"+","+
                    client.getAmountOfmoney()+","+
                    "'"+client.getName()+"'" + "," +
                    "'"+client.getPassword()+"'" + ")" ;
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
            Connection con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            String AddNewUser = "UPDATE users TABLE SET balance = " + client.getAmountOfmoney()
                    +" WHERE unique_id in" + "(" + client.getId() + ")" ;
            stmt.executeUpdate(AddNewUser) ;

            System.out.println("updated successfully");
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
    } // smeha insert transaction

    public Client getClient(String password , String id){
        Client retrieved = new Client();
        try{
            Class.forName(driver);
            //Open a connection
            Connection con  = DriverManager.getConnection(url , username , pass) ;
            //System.out.println("Found it");
            Statement stmt = con.createStatement();
            //Retrieve client
            String query = "SELECT id,unique_id,balance,username,password FROM users" +
                    "WHERE unique_id =" +id + " AND password = " + password;
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
    public ArrayList<Transaction> getTransactions(Client client){
        ArrayList<Transaction>all_transactions = new ArrayList<>();
        try{
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
            String query = "SELECT from,to,amount,amount_from_before,amount_to_before FROM transactions" +
                    "WHERE to="+"'"+client.getId()+"'"+"OR from="
                    +"'"+client.getId()+"'" ;
            ResultSet resultSet = stmt.executeQuery(query) ;
            while (resultSet.next()){
                all_transactions.add(new Transaction());
                int index_now = all_transactions.size()-1;
                //Setting attributes
                all_transactions.get(index_now).setAmount(resultSet.getDouble("amount"));
                all_transactions.get(index_now).setTo_id(resultSet.getString("to"));
                all_transactions.get(index_now).setFrom_id(resultSet.getString("from"));
                all_transactions.get(index_now).setAmount_from_before
                        (resultSet.getDouble("amount_from_before"));
                all_transactions.get(index_now).setAmount_to_before(resultSet.getDouble
                        ("amount_to_before"));
            }
        }
        catch (Exception e){
            System.out.println("Error : "+e);
        }
        return all_transactions ;
    }
}
