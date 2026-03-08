# Practice your skills (Linear Data Structures)

Use this page to practice linear data structures, such as arrays, lists, stacks, and queues. Write **pseudocode first**, then implement in Java.

!!! info "Goal"
    Write algorithms as clear, testable steps before writing any Java. 

## Jump to a topic
- [A) ArrayList vs LinkedList (timing experiment)](#a-arraylist-vs-linkedlist-timing-experiment)
- [B) Arrays practice (classic problems)](#b-arrays-practice-classic-problems)
- [C) Stacks & Queues (Banking Practice)](#c-stacks--queues-banking-practice)


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

| File | Your Task |
|---|---|
| `Transaction.java` | Complete — no changes |
| `Customer.java` | Complete — no changes |
| `BankAccount.java` | ⚠️ Implement `undoLastTransaction()` |
| `BankQueue.java` | ⚠️ Implement all queue methods below |
| `BankingSystem.java` | Complete — run to test your work |


### Exercise 1 — BankAccount Transaction Stack (LIFO)

**File:** `BankAccount.java` → implement `undoLastTransaction()`

```java
public void undoLastTransaction()
```

| Case | Behaviour |
|---|---|
| Stack empty | Print `"No transactions to undo."` |
| Undo DEPOSIT | `balance -= amount` |
| Undo WITHDRAW | `balance += amount` |
| Undo TRANSFER | `balance += amount` (this account's side only) |

**Dry run**
```
deposit(100) → withdraw(30) → balance: $570, stack: [DEPOSIT, WITHDRAW]
undoLastTransaction()       → balance: $600, stack: [DEPOSIT]
undoLastTransaction()       → balance: $500, stack: []
undoLastTransaction()       → "No transactions to undo."
```

**Edge cases:** empty stack · single undo · multiple consecutive undos


### Exercise 2 — BankQueue Customer Service (FIFO)

**File:** `BankQueue.java` → implement three methods

```java
public void addCustomerToQueue(Customer customer)
public Customer serveCustomer()
public void printQueue()
```

| Case | Behaviour |
|---|---|
| `customer` is null | Print `"Invalid customer."`, return |
| `serveCustomer` on empty queue | Print `"Queue empty."`, return `null` |
| `printQueue` on empty queue | Print `"Queue empty."` |
| `printQueue` with customers | Print `1. Name` per customer (1-based position) |

**Dry run**
```
add(Turing) → add(Lovelace) → Queue: [1.Turing, 2.Lovelace]
serveCustomer() → returns Turing, Queue: [1.Lovelace]
```

**Edge cases:** empty queue · null customer · add after serving

### Exercise 3 — BankQueue VIP Priority Service

**File:** `BankQueue.java` → implement two more methods

```java
public void addVIPCustomer(Customer customer, int priority)  // 1=normal, 3=VIP
public Customer serveNextCustomer()
```

| Case | Behaviour |
|---|---|
| `customer` is null | Print `"Invalid customer."`, return |
| Empty VIP queue | Print `"VIP queue empty."`, return `null` |
| Priority 3 vs 1 | VIP (3) always served first |
| Same priority tie | Earlier arrival served first (FIFO) |

**Dry run**
```
addVIP(Turing,1) → addVIP(Lovelace,3) → addVIP(Dijkstra,1)
serveNextCustomer() → Lovelace [VIP]
serveNextCustomer() → Turing   [Normal, arrived first]
serveNextCustomer() → Dijkstra [Normal]
```

**Edge cases:** empty queue · all same priority (FIFO order) · mixed priorities### Required Decisions (All Exercises)

- Empty input behavior (null/error/message)
- Ties/stability (same priority = arrival order)
- Invalid inputs (negative amounts, null customers)

**Testing order**: Dry run by hand → pseudocode → Java → test edge cases with Main program.

> Master your data tools: Arrays hold everything together, lists flex when needed, stacks undo your last move, queues keep everything in fair order. 

> Build reliable systems that handle real chaos!

