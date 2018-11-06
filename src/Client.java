import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private String password;
    private String id;
    private double amountOfMoney;
    //to be removed when adding DB
    private ArrayList<Transaction> transactions;

    public Client() {
        this.transactions= new ArrayList<Transaction>(); this.name = "";
    }

    public Client(String name, String password, double amountOfMoney, String id) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.amountOfMoney=amountOfMoney;
        this.transactions= new ArrayList<Transaction>();
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }public double getAmountOfmoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfmoney) {
        this.amountOfMoney = amountOfmoney;
    }



    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
    public synchronized void  addTranscation(Transaction transaction){
        this.transactions.add(transaction);

    }
    public synchronized void deposit(double money){
        this.amountOfMoney+=money;
    }
    public synchronized void withdraw(double money){
        this.amountOfMoney-=money;
    }
    public String toString(){
        return this.id+this.password;
    }
}
