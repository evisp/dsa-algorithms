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


## A) ArrayList vs LinkedList (timing experiment)

In this task, you compare search time for `ArrayList<String>` vs `LinkedList<String>` by timing how long it takes to check whether a random word exists in a list (e.g., in our case, we will time method `contains(word)` on different dataset sizes).

=== "Task"
    **Problem:** Measure how long it takes to check whether a random word exists in a list.

    **Data to generate**
    - Two lists: `ArrayList<String>` and `LinkedList<String>`.
    - Random words: length 8 characters.
    - Dataset sizes: 1,000; 10,000; 100,000; 500,000; 1,000,000 elements.

    **Experiment steps**
    - For each dataset size:
      - Fill both lists with random words.
      - Generate a new random word `target`.
      - Measure time for `arrayList.contains(target)`.
      - Measure time for `linkedList.contains(target)`.
      - Print results in a clean table.

    **Required decisions**
    - Will you run each measurement once or multiple times and average?
    - Do you test a word that is likely missing, likely present, or both?
    - Do you reuse the same `target` for both lists? (Recommended: yes, for fairness.)

=== "Hint"
    - Put timing logic in one helper method so you don’t repeat code.
    - Use `System.nanoTime()` for timing. 
    - Run multiple trials (and ignore the first run) so results are less noisy. 

=== "Solution"
    **Pseudocode (experiment plan)**
    ```text
    sizes = 

    for each n in sizes:
        arrayList = empty ArrayList of strings
        linkedList = empty LinkedList of strings

        repeat n times:
            w = randomWord(length 8)
            add w to arrayList
            add w to linkedList

        target = randomWord(length 8)

        t1 = timeContains(arrayList, target, trials)
        t2 = timeContains(linkedList, target, trials)

        print n, t1, t2
    ```

    **Pseudocode (timing helper)**
    ```text
    function timeContains(list, target, trials):
        warmup once: list.contains(target)

        totalNanos = 0

        repeat trials times:
            start = nanoTime()
            list.contains(target)
            end = nanoTime()
            totalNanos = totalNanos + (end - start)

        return totalNanos / trials
    ```

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


