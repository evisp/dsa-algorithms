# Practice your skills (Linear Data Structures)

Use this page to practice linear data structures, such as arrays, lists, stacks, and queues. Write **pseudocode first**, then implement in Java.

!!! info "Goal"
    Write algorithms as clear, testable steps before writing any Java. 

## Jump to a topic
- [A) ArrayList vs LinkedList (timing experiment)](#a-arraylist-vs-linkedlist-timing-experiment)
- [B) Arrays practice (classic problems)](#b-arrays-practice-classic-problems)
- [C) Stacks and Queues (Banking Practice)](#c-stacks-and-queues-banking-practice)


!!! tip "Rule for discussion"
    If your algorithm has ties, empty input, or invalid input cases, decide the behavior and write it down.


## A) Arrays vs ArrayList vs LinkedList (timing experiment)

In this experiment you will *observe* how runtime changes for three classic operations when the data structure changes:  
- `int[]` (classic Java array)  
- `ArrayList<Integer>`  
- `LinkedList<Integer>`  

The code is provided (you do not write code), and your job is to **analyze the results** and connect them to the expected Big‑O behavior. 

=== "Task"
    **Problem:** Compare how long it takes to perform:
    
    1. Access by index (`get(i)` / `a[i]`)
    2. Insert + delete at a position (middle index)
    3. Search (linear scan / `contains`)
    
    **Dataset sizes:** 1,000; 10,000; 100,000; 500,000; 1,000,000 elements.

    **What you get:** A ready-to-run Java project that prints timing tables for each operation.

=== "Code (provided)"
    [Repository link](https://github.com/evisp/dsa-algorithms/tree/main/code/arrays_linkedlists/PerformanceMeasure)  

    Files/classes included:

    - `Main.java`: Runs the three experiments and prints tables.  
    - `BenchmarkTimer.java`: Small timing helper using `System.nanoTime()`, with warmup + multiple trials.   
    - `DataFactory.java`: Builds the same dataset for all structures (`0..n-1`) and generates random indices/targets.  
    - `AccessBenchmark.java`: Times indexed reads for `int[]`, `ArrayList.get(i)`, `LinkedList.get(i)`.  
    - `InsertDeleteBenchmark.java`: Times “insert then delete at middle index” for all three structures (arrays do copy via `System.arraycopy`).  
    - `SearchBenchmark.java`: Times linear search for all three (`arrayContains` for `int[]`, `contains` for lists).

=== "Your task / analysis"
    Answer these three key questions:

    1. **Access:** How do the `LinkedList.get(i)` times scale as `n` grows compared to `int[]` and `ArrayList.get(i)`? Explain in terms of “traversal to index” vs direct indexing.  
    2. **Insert/Delete at position:** Which structure becomes slowest for insert/delete in the middle as `n` grows, and why do arrays/ArrayList pay a “shift/copy” cost while LinkedList pays a “walk to position” cost?   
    3. **Search:** For the “missing” target vs “present” target, what difference do you see and why (early stop vs full scan), and do the results match the expected linear-time search for these structures?   

!!! warning "Timing is tricky"
    One run is often misleading because of JVM warm-up and general system noise.   
    Treat this as a trend experiment: as \(n\) grows, does time roughly grow like a straight line? 

## B) Arrays practice (classic problems)

These problems train the core skills you need for arrays: scanning, two pointers, and using an extra data structure when it saves time.

### Exercise 1 — Two Sum (fast lookup)

=== "Task"
    **Problem:** Given an integer array `nums` and an integer `target`, return indices of two numbers that add to `target`.

    **Function signature**
    ```java
    int[] twoSum(int[] nums, int target)
    ```

    **Required decisions**

    - If there are multiple valid answers, return any.
    - If there is no solution, what do you return? (error, `null`, `[-1, -1]`, etc.)
    - You cannot use the same element twice.

    **Dry run dataset**

    - `nums = [2, 7, 11, 15]`, `target = 9` → output `[0, 1]`

=== "Hint"
    - Brute force checks all pairs (simple but slow).
    - To aim for \(O(n)\), store what you’ve seen so far in a map: value → index.

=== "Solution"
    Solution withheld by “the algorithm” until after the lecture.

### Exercise 2 — Remove duplicates from sorted array (two pointers)

=== "Task"
    **Problem:** Given a sorted array, remove duplicates **in-place** so each value appears once.

    **Function signature**
    ```java
    int removeDuplicates(int[] nums)
    ```

    **Required decisions**

    - Empty input: return 0.
    - The first `k` items of `nums` must be the unique values in order.
    - Extra space must be \(O(1)\).

    **Dry run datasets**

    - `nums = [1, 1, 2]` → return `2`, array starts with `[1, 2, ...]`
    - `nums = [0,0,1,1,1,2,2,3,3,4]` → return `5`, array starts with `[0,1,2,3,4,...]`

=== "Hint"
    - Use two pointers:
      - one pointer reads the array from left to right,
      - one pointer writes the next unique value.

=== "Solution"
    Solution withheld by “the algorithm” until after the lecture.


### Exercise 3 — Reverse an array in-place (two pointers)

=== "Task"
    **Problem:** Reverse an integer array in-place (no extra array).

    **Function signature**
    ```java
    void reverseArray(int[] nums)
    ```

    **Required decisions**

    - Empty array and size 1: no changes.
    - Must run in \(O(n)\) time.
    - Must use \(O(1)\) extra space.

    **Dry run datasets**

    - `[1, 2, 3, 4, 5]` → `[5, 4, 3, 2, 1]`
    - `[7, 8, 9, 10]` → `[10, 9, 8, 7]`

=== "Hint"
    - Swap the first and last, then move inward.

=== "Solution"
    Solution withheld by “the algorithm” until after the lecture.


## C) Stacks & Queues (Banking Practice)

Practice stacks (LIFO) and queues (FIFO) using a banking system context. Write **pseudocode first**, then implement with proper edge case handling.

!!! info "Goal"
    Use stacks for transaction undo, queues for customer service (FIFO), and priority queues for VIP service.


### Project Files

All project files are available in the repository below. Some methods are **intentionally left incomplete**; your task is to implement them based on the exercises.

🔗 [Project Repository Link](https://github.com/evisp/dsa-algorithms/tree/main/code/stacks_queues/seminar)

| File | Your Task |
|---|---|
| `Transaction.java` | Complete — no changes |
| `Customer.java` | Complete — no changes |
| `BankAccount.java` | ⚠️ Implement `undoLastTransaction()` |
| `BankQueue.java` | ⚠️ Implement all queue methods below |
| `BankingSystem.java` | Complete — run to test your work |


### Exercise 1: BankAccount Transaction Stack (LIFO)

In this exercise you will work with a **stack of transactions** that records all operations performed on a bank account. The stack follows **Last-In-First-Out (LIFO)** behavior.

Your task is to implement functionality that allows the program to **inspect and undo transactions** stored in the stack.


#### Undo the Last Transaction

Implement a method that **reverses the most recent transaction** in the history.

```java
public void undoLastTransaction()
```

**Requirements**

- If the transaction stack is empty, print:  
  `"No transactions to undo."`
- Remove the **most recent transaction** from the stack.
- Update the account balance depending on the transaction type:
  - **DEPOSIT** → subtract the amount from the balance
  - **WITHDRAW** → add the amount back to the balance
  - **TRANSFER** → add the amount back (only this account’s side)
- Print a message indicating the undone transaction and the new balance.


#### View the Most Recent Transaction

Implement a method that **shows the latest transaction without removing it** from the stack.

```java
public void getLastTransaction()
```

**Requirements**

- If the stack is empty, print:  
  `"No transactions yet."`
- Otherwise, print the **most recent transaction**.


#### Print the Transaction History

Implement a method that prints **all transactions stored in the stack**.

```java
public void printTransactionHistory()
```

**Requirements**

- If the stack is empty, print:  
  `"No transaction history available."`
- Otherwise, print all transactions belonging to the account in the order stored in the stack.

### Exercise 2: BankQueue Customer Service (FIFO)

In this exercise you will implement a **customer queue** for a bank service desk.  
The queue follows **First-In-First-Out (FIFO)** behavior: the first customer to join the queue is the first to be served.

Your task is to implement methods that allow the system to **add customers, serve the next customer, and inspect the queue**.


#### Add a Customer to the Queue

Implement a method that adds a new customer to the end of the queue.

```java
public void addCustomerToQueue(Customer customer)
```

**Requirements**

- If `customer` is `null`, print:  
  `"Invalid customer."`  
  and do not add anything to the queue.
- Otherwise, add the customer to the **end of the queue**.

#### Serve the Next Customer

Implement a method that removes and returns the **next customer to be served**.

```java
public Customer serveCustomer()
```

**Requirements**

- If the queue is empty, print:  
  `"Queue empty."`  
  and return `null`.
- Otherwise, remove the **first customer in the queue** and return it.

#### Print the Queue

Implement a method that prints all customers currently waiting in the queue.

```java
public void printQueue()
```

**Requirements**

- If the queue is empty, print:  
  `"Queue empty."`
- Otherwise, print all customers in the queue with their **position number (starting from 1)**.

Example format:

```
1. Alan Turing
2. Ada Lovalace
3. Dennis Ritchie
```

**Edge cases to consider:** empty queue · null customer · adding customers after some have already been served

### Exercise 3: BankQueue VIP Priority Service

In this exercise you will extend the bank queue system to support **VIP priority service**.  
Customers may have different priority levels, and those with **higher priority must be served first**.

If two customers have the **same priority**, they must still follow **FIFO order** (the one who arrived earlier is served first).

#### Add a Customer with Priority

Implement a method that adds a customer to the queue together with a priority level.

```java
public void addVIPCustomer(Customer customer, int priority)
```

**Requirements**

- If `customer` is `null`, print:  
  `"Invalid customer."`  
  and do not add the customer.
- The `priority` represents the service level:
  - `1` → Normal customer
  - `3` → VIP customer
- Customers with **higher priority must be served before lower priority customers**.
- Customers with the **same priority must preserve arrival order (FIFO)**.

#### Subtask 2: Serve the Next Customer

Implement a method that selects and serves the **next appropriate customer based on priority**.

```java
public Customer serveNextCustomer()
```

**Requirements**

- If the queue is empty, print:  
  `"VIP queue empty."`  
  and return `null`.
- Otherwise:
  - Serve the **highest priority customer available**.
  - If multiple customers share the same priority, serve the **one who arrived first**.
- Remove the served customer from the queue and return it.


> Master your data tools: Arrays hold everything together, lists flex when needed, stacks undo your last move, queues keep everything in fair order. 

> Build reliable systems that handle real chaos!

