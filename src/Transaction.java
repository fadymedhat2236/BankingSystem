public class Transaction {
    private String from_id;
    private String to_id;
    private double amount;
    private double amount_from_before;
    private double amount_to_before;

    public double getAmount_from_before() {
        return amount_from_before;
    }

    public void setAmount_from_before(double amount_from_before) {
        this.amount_from_before = amount_from_before;
    }

    public double getAmount_to_before() {
        return amount_to_before;
    }

    public void setAmount_to_before(double amount_to_before) {
        this.amount_to_before = amount_to_before;
    }

    public Transaction(String from_id, String to_id, float amount) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.amount = amount;
    }

    public Transaction() {
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
