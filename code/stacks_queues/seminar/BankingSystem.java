// Main class to test the banking system
public class BankingSystem {
    public static void main(String[] args) {

        // ════════════════════════════════════════════════════════════════════════
        // SETUP
        // ════════════════════════════════════════════════════════════════════════
        BankAccount turingAcc   = new BankAccount("TUR-001", 500.0);
        BankAccount lovelaceAcc = new BankAccount("LOV-002", 300.0);
        BankAccount knuthAcc    = new BankAccount("KNU-003", 200.0);
        BankAccount dijkstraAcc = new BankAccount("DIJ-004", 150.0);
        BankAccount hopperAcc   = new BankAccount("HOP-005", 400.0);

        Customer turing   = new Customer("Alan Turing",     turingAcc);
        Customer lovelace = new Customer("Ada Lovelace",    lovelaceAcc);
        Customer knuth    = new Customer("Donald Knuth",    knuthAcc,    3); // VIP
        Customer dijkstra = new Customer("Edsger Dijkstra", dijkstraAcc, 1);
        Customer hopper   = new Customer("Grace Hopper",    hopperAcc,   3); // VIP

        // ════════════════════════════════════════════════════════════════════════
        // EXERCISE 1 — Transaction Stack (LIFO / Undo)
        // ════════════════════════════════════════════════════════════════════════
        System.out.println("══════════════════════════════════════");
        System.out.println(" EX 1: Transaction Stack (Undo)");
        System.out.println("══════════════════════════════════════");

        turingAcc.deposit(100);                          // balance: 600
        turingAcc.withdraw(30);                          // balance: 570
        turingAcc.printTransactionHistory();

        System.out.println("\n-- Undo WITHDRAW --");
        turingAcc.undoLastTransaction();                 // expected: 600
        System.out.println("Expected: $600.0 | Got: $" + turingAcc.getBalance());

        System.out.println("\n-- Undo DEPOSIT --");
        turingAcc.undoLastTransaction();                 // expected: 500
        System.out.println("Expected: $500.0 | Got: $" + turingAcc.getBalance());

        System.out.println("\n-- Edge: undo on empty stack --");
        turingAcc.undoLastTransaction();

        System.out.println("\n-- Transfer + undo --");
        BankAccount.transferFunds(lovelaceAcc, dijkstraAcc, 100);
        lovelaceAcc.undoLastTransaction();               // restores Lovelace's side
        System.out.println("Lovelace restored: $" + lovelaceAcc.getBalance());

        // ════════════════════════════════════════════════════════════════════════
        // EXERCISE 2 — FIFO Queue
        // ════════════════════════════════════════════════════════════════════════
        System.out.println("\n══════════════════════════════════════");
        System.out.println(" EX 2: Customer Queue (FIFO)");
        System.out.println("══════════════════════════════════════");

        BankQueue queue = new BankQueue();

        System.out.println("\n-- Edge: serve empty queue --");
        queue.serveCustomer();                           // expected: null

        queue.addCustomerToQueue(turing);
        queue.addCustomerToQueue(lovelace);
        queue.printQueue();

        System.out.println("\n-- Serve (expected: Turing first) --");
        queue.serveCustomer();
        queue.printQueue();

        queue.addCustomerToQueue(knuth);                 // add after serving
        queue.serveCustomer();                           // Lovelace
        queue.serveCustomer();                           // Knuth
        queue.serveCustomer();                           // Edge: empty again

        System.out.println("\n-- Edge: null customer --");
        queue.addCustomerToQueue(null);

        // ════════════════════════════════════════════════════════════════════════
        // EXERCISE 3 — Priority Queue (VIP Service)
        // ════════════════════════════════════════════════════════════════════════
        System.out.println("\n══════════════════════════════════════");
        System.out.println(" EX 3: VIP Priority Queue");
        System.out.println("══════════════════════════════════════");

        BankQueue vipQueue = new BankQueue();

        System.out.println("\n-- Edge: serve empty VIP queue --");
        vipQueue.serveNextCustomer();

        // Spec dry run: Turing(1), Lovelace(3), Dijkstra(1) → Lovelace first
        vipQueue.addVIPCustomer(turing,   1);
        vipQueue.addVIPCustomer(lovelace, 3);
        vipQueue.addVIPCustomer(dijkstra, 1);
        vipQueue.printVIPQueue();

        System.out.println("\n-- Serve (expected: Ada Lovelace — VIP) --");
        vipQueue.serveNextCustomer();

        System.out.println("\n-- Serve (expected: Turing — FIFO among priority 1) --");
        vipQueue.serveNextCustomer();

        System.out.println("\n-- Serve (expected: Dijkstra) --");
        vipQueue.serveNextCustomer();

        System.out.println("\n-- Edge: all same priority → FIFO order --");
        vipQueue.addVIPCustomer(hopper, 3);
        vipQueue.addVIPCustomer(knuth,  3);
        vipQueue.serveNextCustomer();                    // expected: Hopper
        vipQueue.serveNextCustomer();                    // expected: Knuth

        System.out.println("\n══════════════════════════════════════");
        System.out.println(" All tests complete.");
        System.out.println("══════════════════════════════════════");
    }
}