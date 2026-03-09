// Transaction class to store transaction details
import java.util.Date;

public class Transaction {
    public enum Type { DEPOSIT, WITHDRAW, TRANSFER }

    private Type   type;
    private double amount;
    private Date   date;
    private String details;

    public Transaction(Type type, double amount, String details) {
        this.type    = type;
        this.amount  = amount;
        this.date    = new Date();
        this.details = details;
    }

    // ─── Getters (required by BankAccount.undoLastTransaction) ──────────────────
    public Type   getType()    { return type; }
    public double getAmount()  { return amount; }
    public Date   getDate()    { return date; }
    public String getDetails() { return details; }

    // ─── Output ──────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return type + " of $" + amount + " on " + date + " (" + details + ")";
    }

    public String toCSV() {
        return type + "," + amount + "," + date + "," + details;
    }
}