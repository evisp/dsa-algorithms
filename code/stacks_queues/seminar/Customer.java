// Customer class representing bank clients
public class Customer {
    private String name;
    private BankAccount bankAccount;
    private int priorityLevel;   // 1 = normal, 3 = VIP
    private int arrivalOrder;    // set by queue at insertion — preserves FIFO among equal priorities

    // ─── Standard customer (normal priority) ────────────────────────────────────
    public Customer(String name, BankAccount bankAccount) {
        this.name         = name;
        this.bankAccount  = bankAccount;
        this.priorityLevel = 1;   // default: normal
        this.arrivalOrder  = 0;   // assigned later by PriorityBankQueue
    }

    // ─── Customer with explicit priority (for VIP queue) ────────────────────────
    public Customer(String name, BankAccount bankAccount, int priorityLevel) {
        this.name          = name;
        this.bankAccount   = bankAccount;
        this.priorityLevel = priorityLevel;
        this.arrivalOrder  = 0;   // assigned later by PriorityBankQueue
    }

    // ─── Getters ─────────────────────────────────────────────────────────────────
    public String getName()            { return name; }
    public BankAccount getBankAccount(){ return bankAccount; }
    public int getPriorityLevel()      { return priorityLevel; }
    public int getArrivalOrder()       { return arrivalOrder; }

    // ─── Setter (called by PriorityBankQueue on insertion) ───────────────────────
    public void setArrivalOrder(int order) { this.arrivalOrder = order; }

    // ─── Display ─────────────────────────────────────────────────────────────────
    public void printAccountDetails() {
        System.out.println("Customer:       " + name);
        System.out.println("Account Number: " + bankAccount.getAccountNumber());
        System.out.println("Balance:        $" + bankAccount.getBalance());
        System.out.println("Priority:       " + (priorityLevel == 3 ? "VIP" : "Normal"));
    }

    @Override
    public String toString() {
        return name + " (Priority: " + (priorityLevel == 3 ? "VIP" : "Normal") + ")";
    }
}