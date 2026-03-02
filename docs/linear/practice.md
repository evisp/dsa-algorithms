# Practice your skills (Linear Data Structures)

Use this page to practice linear data structures, such as arrays, lists, stacks, and queues. Write **pseudocode first**, then implement in Java.

!!! info "Goal"
    Write algorithms as clear, testable steps before writing any Java. 

## Jump to a topic
- [A) ArrayList vs LinkedList (timing experiment)](#a-arraylist-vs-linkedlist-timing-experiment)
- [B) Arrays practice (classic problems)](#b-arrays-practice-classic-problems)
- [C) Stacks & queues (coming soon)](#c-stacks--queues-coming-soon)


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
    [Repository link](https://github.com/YOUR_NAME/YOUR_REPO_HERE)  

    Files/classes included:

    - `Main.java`: Runs the three experiments and prints tables.  
    - `BenchmarkTimer.java`: Small timing helper using `System.nanoTime()`, with warmup + multiple trials. [web:33][web:52]  
    - `DataFactory.java`: Builds the same dataset for all structures (`0..n-1`) and generates random indices/targets.  
    - `AccessBenchmark.java`: Times indexed reads for `int[]`, `ArrayList.get(i)`, `LinkedList.get(i)`.  
    - `InsertDeleteBenchmark.java`: Times “insert then delete at middle index” for all three structures (arrays do copy via `System.arraycopy`). [web:65][web:80]  
    - `SearchBenchmark.java`: Times linear search for all three (`arrayContains` for `int[]`, `contains` for lists). [web:79]  

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



## C) Stacks & queues (coming soon)

We'll add stack and queue practice next. Until then, remember: life is basically one big queue; except in programming, at least you get to decide the rules.  


