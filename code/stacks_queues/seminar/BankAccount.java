// BankAccount class with transaction stack
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class BankAccount {
    private String accountNumber;
    private double balance;
    private Stack<Transaction> transactionHistory;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new Stack<>();
    }

    // ─── Core Operations ────────────────────────────────────────────────────────

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        Transaction t = new Transaction(
                Transaction.Type.DEPOSIT,
                amount,
                "Deposited to account " + accountNumber);
        transactionHistory.push(t);
        saveTransactionToCSV(t);
        System.out.println("Deposited: $" + amount + " | Balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds!");
            return;
        }
        balance -= amount;
        Transaction t = new Transaction(
                Transaction.Type.WITHDRAW,
                amount,
                "Withdrawn from account " + accountNumber);
        transactionHistory.push(t);
        saveTransactionToCSV(t);
        System.out.println("Withdrawn: $" + amount + " | Balance: $" + balance);
    }

    public static void transferFunds(BankAccount from, BankAccount to, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return;
        }
        if (amount > from.balance) {
            System.out.println("Insufficient funds for transfer!");
            return;
        }
        from.balance -= amount;
        to.balance += amount;

        // FIX: separate Transaction objects so each account can undo independently
        String description = "Transfer from " + from.accountNumber + " to " + to.accountNumber;
        Transaction tFrom = new Transaction(Transaction.Type.TRANSFER, amount, description);
        Transaction tTo   = new Transaction(Transaction.Type.TRANSFER, amount, description);

        from.transactionHistory.push(tFrom);
        to.transactionHistory.push(tTo);
        from.saveTransactionToCSV(tFrom);
        to.saveTransactionToCSV(tTo);

        System.out.println("Transferred $" + amount
                + " from " + from.accountNumber + " to " + to.accountNumber);
    }

    // ─── Exercise 1: Undo (LIFO) ────────────────────────────────────────────────

    /*
     * PSEUDOCODE:
     * undoLastTransaction():
     *   if stack is empty → print "No transactions to undo" → return
     *   pop top transaction
     *   if DEPOSIT  → balance -= amount   (reverse the deposit)
     *   if WITHDRAW → balance += amount   (restore withdrawn funds)
     *   if TRANSFER → balance += amount   (restore this account's side only)
     *   print confirmation with new balance
     */
    public void undoLastTransaction() {
		// WRITE YOUR CODE HERE
    }

    // ─── Inspection Helpers ─────────────────────────────────────────────────────

    public void getLastTransaction() {
        // WRITE YOUR CODE HERE
    }

    public void printTransactionHistory() {
        // WRITE YOUR CODE HERE
    }

    // ─── Getters ────────────────────────────────────────────────────────────────

    public double getBalance()        { return balance; }
    public String getAccountNumber()  { return accountNumber; }

    // ─── CSV Persistence ────────────────────────────────────────────────────────

    private void saveTransactionToCSV(Transaction transaction) {
        try (FileWriter writer = new FileWriter("transactions.csv", true)) {
            writer.write(transaction.toCSV() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving transaction to CSV: " + e.getMessage());
        }
    }
}