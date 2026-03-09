// BankQueue — manages both FIFO queue (Ex2) and VIP priority queue (Ex3)
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BankQueue {

    private Queue<Customer>         customerQueue;  // FIFO  — Exercise 2
    private PriorityQueue<Customer> vipQueue;       // Priority — Exercise 3
    private int                     arrivalCounter; // stamps arrival order for FIFO stability

    public BankQueue() {
        this.customerQueue  = new LinkedList<>();
        this.arrivalCounter = 0;

        // Higher priorityLevel served first (3 before 1)
        // Tie-break: lower arrivalOrder first → preserves FIFO among equal priorities
        this.vipQueue = new PriorityQueue<>(
            Comparator.comparingInt(Customer::getPriorityLevel).reversed()
                      .thenComparingInt(Customer::getArrivalOrder)
        );
    }

    // EXERCISE 2 — FIFO Queue

    /*
     * PSEUDOCODE:
     * addCustomerToQueue(customer):
     *   if customer is null → print "Invalid customer" → return
     *   offer customer to back of FIFO queue
     *   print confirmation + queue size
     */
    public void addCustomerToQueue(Customer customer) {
       	// WRITE YOUR CODE HERE
    }

    /*
     * PSEUDOCODE:
     * serveCustomer():
     *   if queue is empty → print "Queue empty" → return null
     *   poll() front customer (FIFO)
     *   print name + remaining count
     *   return served customer
     */
    public Customer serveCustomer() {
        // WRITE YOUR CODE HERE
    }

    /*
     * PSEUDOCODE:
     * printQueue():
     *   if queue is empty → print "Queue empty" → return
     *   iterate with 1-based position counter
     *   print position + customer name
     */
    public void printQueue() {
        if (customerQueue.isEmpty()) {
            System.out.println("Queue empty.");
            return;
        }
        System.out.println("Current queue (" + customerQueue.size() + " customers):");
        int position = 1;
        for (Customer c : customerQueue) {
            System.out.println("  " + position + ". " + c.getName());
            position++;
        }
    }

    // EXERCISE 3 — Priority Queue (VIP Service)

    /*
     * PSEUDOCODE:
     * addVIPCustomer(customer, priority):
     *   if customer is null → print "Invalid customer" → return
     *   stamp arrivalOrder on customer (for FIFO tie-break)
     *   offer to vipQueue
     *   print name + priority label + queue size
     */
    public void addVIPCustomer(Customer customer, int priority) {
        // WRITE YOUR CODE HERE

    }

    /*
     * PSEUDOCODE:
     * serveNextCustomer():
     *   if vipQueue is empty → print "VIP queue empty" → return null
     *   poll() highest-priority customer (PriorityQueue handles ordering)
     *   print name + priority label + remaining count
     *   return served customer
     */
    public Customer serveNextCustomer() {
        // WRITE YOUR CODE HERE
    }

    public void printVIPQueue() {
        if (vipQueue.isEmpty()) {
            System.out.println("VIP queue empty.");
            return;
        }
        System.out.println("VIP queue (" + vipQueue.size() + " customers):");
        vipQueue.stream()
                .sorted(Comparator.comparingInt(Customer::getPriorityLevel).reversed()
                                  .thenComparingInt(Customer::getArrivalOrder))
                .forEach(c -> System.out.println("  - " + c.getName()
                        + " [" + (c.getPriorityLevel() >= 3 ? "VIP" : "Normal") + "]"));
    }

    // ════════════════════════════════════════════════════════════════════════════
    // Helpers
    // ════════════════════════════════════════════════════════════════════════════
    public int     getSize()     { return customerQueue.size(); }
    public boolean isEmpty()     { return customerQueue.isEmpty(); }
    public int     getVIPSize()  { return vipQueue.size(); }
    public boolean isVIPEmpty()  { return vipQueue.isEmpty(); }
}