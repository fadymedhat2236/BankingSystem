public class Transaction {
    private String from_id;
    private String to_id;
    private float amount;

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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
