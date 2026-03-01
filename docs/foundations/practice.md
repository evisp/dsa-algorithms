# Practice your skills (Foundations)

Use this page to practice all three Foundations topics. Write pseudocode first, then implement in Java later.

!!! info "Goal"
    Write algorithms as clear, testable steps before writing any Java.

## Jump to a topic
- [A) Algorithm fundamentals](#a-algorithm-fundamentals)
- [B) Algorithm analysis (complexity)](#b-algorithm-analysis-complexity)
- [C) Algorithm strategies](#c-algorithm-strategies)


## How to work (quick)
- Work in groups of 3–4.
- Rotate roles each exercise (driver, spec-keeper, edge-case tester, timekeeper).

??? note "Role definitions (optional)"
    - Driver: writes the final pseudocode.
    - Spec-keeper: enforces clear inputs/outputs and tie/empty rules.
    - Edge-case tester: invents tricky cases and checks the steps.
    - Timekeeper: keeps the group moving.

!!! tip "Rule for discussion"
    If your algorithm has ties or empty input cases, decide the behavior and write it down.


## What to submit (for each exercise)

??? note "Submission template (copy/paste per exercise)"
    - **Problem (one sentence):**
    - **Inputs (types + constraints):**
    - **Output (exact meaning):**
    - **Assumptions (if any):**
    - **Tie-break rule (if any):**
    - **Empty-input behavior:**
    - **Invalid-input behavior (if any):**
    - **Pseudocode:**
    - **Edge cases (at least 3):**
    - **Dry run (tiny example):**

??? note "Pseudocode style guide"
    Use plain language + structured control flow.

    ```text
    if condition:
        do something
    else:
        do something else

    for each item x in items:
        ...

    for i from 0 to n-1:
        ...

    while condition:
        ...
    ```

    Avoid Java syntax details (no semicolons, no classes). Focus on clarity.


## A) Algorithm fundamentals

### Warm-up: make an ambiguous algorithm precise
=== "Task"
    **Problem:** “Pick the best movie to watch.”

    As a group, agree on one definition of “best” and write 3–5 steps.

    Required decisions:
    - What is the input?
    - What is the output?
    - What happens if the list is empty?
    - What happens if there’s a tie?

=== "Your work"
    Use the submission template above.



### Exercise 1 — Tallest person (max + ties)
=== "Task"
    **Task:** Given a list of heights, determine the tallest height (and optionally who it belongs to).

    Use this dataset for your dry run:
    - `heights = [165, 172, 180, 180, 158, 190, 175]`

    Required decisions:
    - Empty list: return a special value or error (choose one and write it down).
    - Ties: if multiple people share the max height, choose a rule (first, last, or all).

=== "Hint"
    - You can’t know the maximum without looking at every value at least once, so plan a single scan.  
    - Track a “best so far” value; if you also need the index/name, track that too (and only update it when your tie rule says so).

=== "Solution"
    **Tie rule used here:** first maximum wins (keep the earliest index).  
    **Empty input behavior:** error.

    **Pseudocode (max height only):**
    ```text
    if heights is empty:
        error ("no tallest in an empty list")

    best = heights

    for each h in heights:
        if h > best:
            best = h

    return best
    ```

    **Pseudocode (max height + index, first max wins):**
    ```text
    if heights is empty:
        error

    bestIndex = 0

    for i from 1 to length(heights)-1:
        if heights[i] > heights[bestIndex]:
            bestIndex = i

    return bestIndex
    ```

    **Dry run (given dataset):**
    - Start: bestIndex=0 (165)
    - See 172 → update bestIndex=1
    - See 180 → update bestIndex=2
    - See 180 (tie) → do nothing (first max wins)
    - See 190 → update bestIndex=5
    - Answer: index 5, height 190



### Exercise 2 — Total cost (sum + validation rules)
=== "Task"
    **Task:** Given a list of food prices, compute the total.

    Use this dataset for your dry run:
    - `prices = [2.50, 1.20, 0.00, 5.80]`

    Now discuss this twist and decide a policy:
    - `prices = [2.50, -1.00, 3.00]`

    Required decisions:
    - Are negative values allowed?
    - If not allowed, do you error, skip them, or replace them?

=== "Hint"
    - A total is usually a single scan: start with `total = 0`, then add each price.  
    - If you reject negatives, decide what “reject” means: stop with an error, or skip invalid values and continue (both are valid if you state it clearly).

=== "Solution"
    **Policy used here:** negative prices are invalid → *error* (stop immediately).  

    **Pseudocode (sum, reject negatives):**
    ```text
    total = 0

    for each price p in prices:
        if p < 0:
            error ("negative price not allowed")
        total = total + p

    return total
    ```

    **Dry run (prices = [2.50, 1.20, 0.00, 5.80]):**
    - total = 0
    - add 2.50 → total = 2.50
    - add 1.20 → total = 3.70
    - add 0.00 → total = 3.70
    - add 5.80 → total = 9.50
    - return 9.50

    **Dry run (prices = [2.50, -1.00, 3.00]):**
    - total = 0
    - add 2.50 → total = 2.50
    - see -1.00 → error ("negative price not allowed")

    **Optional extension (if you prefer “skip invalid” instead of error):**
    ```text
    total = 0
    invalidCount = 0

    for each price p in prices:
        if p < 0:
            invalidCount = invalidCount + 1
            continue
        total = total + p

    return (total, invalidCount)
    ```



### Exercise 3 — Next bus (filter + minimum selection)
=== "Task"
    **Task:** You are at a bus stop. You have arrival times in minutes from now.  
    Return the next bus (the smallest non-negative time).

    Example input:
    - `arrivals = [-3, 12, 0, 5, 5, 28]`

    Required decisions:
    - Do you allow `0`? (arriving now)
    - If two buses arrive at the same time, do you return the first, all, or just the time value?
    - What do you return if there is no valid (non-negative) arrival?

    Edge cases to include:
    - All arrivals are negative.
    - The list is empty.
    - Duplicate times.

=== "Hint"
    - Ignore invalid values (negative minutes) while searching for the smallest valid time.  
    - Keep a `best` value that starts as “not found yet”; update it whenever you find a valid time that is smaller.

=== "Solution"
    **Policy used here:**  
    - `0` is allowed (a bus arriving now counts).  
    - If there are ties, return the time value (not “first/all”).  
    - If no valid arrival exists, return `"no bus"`.

    **Pseudocode (return next time or 'no bus'):**
    ```text
    best = null  # means "no valid bus found yet"

    for each t in arrivals:
        if t < 0:
            continue

        if best is null or t < best:
            best = t

    if best is null:
        return "no bus"

    return best
    ```

    **Dry run (arrivals = [-3, 12, 0, 5, 5, 28]):**
    - best = null
    - t=-3 → skip
    - t=12 → best=12
    - t=0 → best=0
    - t=5 → 5 < 0? no → keep best=0
    - t=5 → keep best=0
    - t=28 → keep best=0
    - return 0

    **Edge case examples:**
    - `arrivals = []` → return "no bus"
    - `arrivals = [-5, -1]` → return "no bus"
    - `arrivals = [7, 7, 2]` → return 2



### Exercise 4 — To-do cleanup (remove completed + keep order)
=== "Task"
    **Task:** You have:
    - `tasks`: a list of task titles (strings)
    - `completed`: a list of completed task titles

    Return a new list containing only tasks that are not completed, preserving original order.

    Example:
    - `tasks = ["Quiz", "Read", "Lab", "Read", "Email"]`
    - `completed = ["Read"]`
    - output: `["Quiz", "Lab", "Email"]`

    Required decisions:
    - If a task appears twice and is completed, do you remove both occurrences?
    - If `completed` contains a task not found in `tasks`, do you ignore it?
    - What happens if `tasks` is empty?

=== "Hint"
    - You’re filtering: scan `tasks` once and build a new list with only the tasks you want to keep.  
    - Membership checks matter: if `completed` is large, checking “is this task completed?” is easier if `completed` behaves like a set.

=== "Solution"
    **Policy used here:**  
    - If a completed title appears in `tasks` multiple times, remove all of its occurrences.  
    - If `completed` contains titles not in `tasks`, ignore them.  
    - If `tasks` is empty, return an empty list.

    **Pseudocode (stable filter):**
    ```text
    completedSet = set made from completed
    result = empty list
    removedCount = 0

    for each title in tasks:
        if title is in completedSet:
            removedCount = removedCount + 1
        else:
            append title to result

    return result
    ```

    **Dry run**
    - tasks = ["Quiz", "Read", "Lab", "Read", "Email"]
    - completed = ["Read"]
    - completedSet = {"Read"}

    Scan tasks:
    - "Quiz" not in set → keep
    - "Read" in set → remove
    - "Lab" not in set → keep
    - "Read" in set → remove
    - "Email" not in set → keep

    Result: ["Quiz", "Lab", "Email"]

    **Optional extension**
    Return both the filtered list and how many were removed:
    ```text
    return (result, removedCount)
    ```

    **Edge case examples**
    - tasks = [] → []
    - completed = [] → return tasks unchanged
    - tasks = ["Read", "Read"] and completed = ["Read"] → []



## B) Algorithm analysis (complexity)

This section trains one skill: read code and explain how it **scales** as input size grows.

!!! info "What to write for each exercise"
    - Define \(n\) (what does it count?).
    - Time complexity (Big-O).
    - Auxiliary space complexity (Big-O).
    - One-sentence justification (e.g., “one pass over n items”, “nested loops over n”, “sort dominates”).

!!! tip "Quick rules that usually work"
    - One loop over \(n\) items → \(O(n)\)
    - Two nested loops over \(n\) → \(O(n^2)\)
    - Sort then scan → usually \(O(n \log n)\) (sorting dominates)
    - Recursion: include call stack space (depth matters)

!!! warning "Common mistake"
    Don’t forget to say what \(n\) is, and don’t count the input array itself as “extra space” unless you explicitly say you are.


### Exercise B1 — One pass (pseudocode)

=== "Task"
    For the pseudocode below, write:

    - What is \(n\)?
    - Time complexity (Big-O)
    - Auxiliary space complexity (Big-O)
    - One-sentence justification

    ```text
    total = 0
    for each price p in prices:
        total = total + p
    return total
    ```

=== "Hint"
    - Start by defining \(n =\) number of items in `prices`.
    - Count passes: this code touches each element once and uses only a few variables.

=== "Solution"
    - **What is \(n\)?**  
      \(n\) = the number of items in `prices`.

    - **Time complexity (Big-O):**  
      \(O(n)\)

    - **Auxiliary space complexity (Big-O):**  
      \(O(1)\)

    - **One-sentence justification:**  
      The algorithm makes one pass over the \(n\) prices and uses only a constant amount of extra memory (`total` and the loop variable).

### Exercise B2 — Pairwise comparisons (pseudocode)

=== "Task"
    For the pseudocode below, write:

    - What is \(n\)?
    - Time complexity (Big-O)
    - Auxiliary space complexity (Big-O)
    - One-sentence justification

    ```text
    count = 0
    for i from 0 to n-1:
        for j from i+1 to n-1:
            if arr[i] == arr[j]:
                count = count + 1
    return count
    ```

=== "Hint"
    - Focus on how many pairs \((i, j)\) the loops generate.
    - The inner loop runs many times for each outer iteration (this is the “pairwise” pattern).

=== "Solution"
    - **What is \(n\)?**  
      \(n\) = the number of elements in `arr`.

    - **Time complexity (Big-O):**  
      \(O(n^2)\)

    - **Auxiliary space complexity (Big-O):**  
      \(O(1)\)

    - **Short justification:**  
      The outer loop picks a starting index `i`, and for each `i` the inner loop compares `arr[i]` with every later element `arr[j]`.  
      That creates about \((n-1) + (n-2) + ... + 1 = n(n-1)/2\) comparisons in total, which grows like \(n^2\).  
      The algorithm only uses a few variables (`count`, `i`, `j`), so extra space stays constant.



### Exercise B3 — Sort then scan (Java)

=== "Task"
    Analyze the function below:

    - Define \(n\)
    - Time complexity (Big-O)
    - Auxiliary space complexity (Big-O) (assume the sort may use extra memory)
    - One-sentence justification

    ```java
    int countAdjacentDuplicates(int[] arr) {
        java.util.Arrays.sort(arr);

        int count = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                count++;
            }
        }
        return count;
    }
    ```

=== "Hint"
    - Separate the work into two phases: sorting, then a single scan.
    - Your final Big-O should be dominated by whichever phase grows faster.

=== "Solution"
    - **What is \(n\)?**  
      \(n\) = the number of elements in `arr`.

    - **Time complexity (Big-O):**  
      \(O(n \log n)\)

    - **Auxiliary space complexity (Big-O):**  
      \(O(n)\) (assuming the sort may use extra memory)

    - **Short justification:**  
      The function has two phases: it first sorts the array, then it does one linear scan to count equal neighbors.  
      Sorting dominates the runtime because \(O(n \log n)\) grows faster than the \(O(n)\) scan, so total time is \(O(n \log n)\).  
      If we assume the sorting step may allocate extra memory, the auxiliary space can be up to \(O(n)\), while the scan itself uses only a constant amount of extra space.


### Exercise B4 — “Contains” inside a loop (Java)

=== "Task"
    Analyze the function below:

    - Define \(n\) and \(m\) (you may need two variables)
    - Time complexity (Big-O)
    - Auxiliary space complexity (Big-O)
    - One-sentence justification

    ```java
    java.util.List<String> keepNotCompleted(
            java.util.List<String> tasks,
            java.util.List<String> completed) {

        java.util.List<String> result = new java.util.ArrayList<>();

        for (String t : tasks) {
            if (!completed.contains(t)) {
                result.add(t);
            }
        }
        return result;
    }
    ```

=== "Hint"
    - `completed.contains(t)` is itself a scan for a `List` (think: “how many checks per call?”).
    - If `tasks` has \(n\) items and `completed` has \(m\) items, how many total checks can happen?

=== "Solution"
    - **What is \(n\) and \(m\)?**  
      \(n\) = the number of items in `tasks`.  
      \(m\) = the number of items in `completed`.

    - **Time complexity (Big-O):**  
      \(O(n \cdot m)\)

    - **Auxiliary space complexity (Big-O):**  
      \(O(n)\)

    - **Short justification:**  
      The loop runs once for each task (\(n\) iterations). For each task, `completed.contains(t)` may scan through the `completed` list, which can take up to \(m\) checks in the worst case.  
      That gives up to \(n \cdot m\) total checks overall.  
      The method builds a new `result` list that can contain up to \(n\) tasks, so extra space is \(O(n)\) (ignoring the input lists).


### Exercise B5 — Recursion growth (Python)

=== "Task"
    Analyze the function below:

    - Define \(n\)
    - Time complexity (Big-O)
    - Auxiliary space complexity (Big-O)
    - One-sentence justification

    ```py
    def fib(n):
        if n < 0:
            raise ValueError("n must be >= 0")
        if n == 0:
            return 0
        if n == 1:
            return 1
        return fib(n - 1) + fib(n - 2)
    ```

=== "Hint"
    - Draw a small call tree for `fib(5)` and notice repeated subproblems.
    - Space: even if you don’t allocate arrays, recursion uses stack frames.

=== "Solution"
    - **What is \(n\)?**  
      \(n\) is the input number in `fib(n)` (it controls how deep the recursion goes and how many calls get made).

    - **Time complexity (Big-O):**  
      \(O(2^n)\)

    - **Auxiliary space complexity (Big-O):**  
      \(O(n)\)

    - **Short justification:**  
      This version recomputes the same values many times (for example, `fib(n-2)` is computed inside both `fib(n-1)` and `fib(n)`).  
      The number of calls grows exponentially with \(n\), so the runtime is \(O(2^n)\).  
      The deepest chain of calls is length \(n\), so the recursion stack uses \(O(n)\) extra space.  
      **Intuition for the growth:** each call to `fib(n)` creates *two more calls* (`fib(n-1)` and `fib(n-2)`), and most of those calls create two more calls again.  
      So the total number of calls spreads out like a branching tree, and the number of nodes in that tree increases very quickly as \(n\) increases.

### Exercise B6 — Binary search (C)

=== "Task"
    Analyze the function below (assume the array is already sorted):

    - Define \(n\)
    - Time complexity (Big-O)
    - Auxiliary space complexity (Big-O)
    - One-sentence justification

    ```c
    int binary_search(int *a, int n, int target) {
        int lo = 0;
        int hi = n - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (a[mid] == target) return mid;
            if (a[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }
    ```

=== "Hint"
    - Each loop iteration cuts the remaining search range roughly in half.
    - Track “how many times can you halve \(n\) before it becomes 1?”

=== "Solution"
    - **What is \(n\)?**  
      \(n\) is the number of elements in the sorted array `a`.

    - **Time complexity (Big-O):**  
      \(O(\log n)\)

    - **Auxiliary space complexity (Big-O):**  
      \(O(1)\)

    - **Short justification:**  
      Each loop iteration compares `target` with `a[mid]` and then throws away half of the remaining search interval by moving either `lo` up or `hi` down.  
      Halving the search space repeatedly takes about \(\log_2(n)\) steps until the interval becomes empty or you find the target, and each step does constant work (a few arithmetic ops and comparisons).  
      The algorithm only uses a handful of integer variables (`lo`, `hi`, `mid`), so extra space stays constant.




## C) Algorithm strategies

This section is about choosing a strategy and justifying it (not just coding).
For each exercise:

- Pick the best strategy (brute force / divide & conquer / greedy / DP / backtracking). 
- Explain why in 1–2 sentences.
- Write pseudocode with decisions + edge cases. 

!!! info "How to work (recommended)"
    - Spend 2 minutes agreeing on the strategy and the rules (ties, empty input, invalid input). 
    - Spend 6–8 minutes writing pseudocode.
    - Spend 2 minutes sharing with another group: “Why this strategy?” 

!!! tip "What a good justification sounds like"
    “This problem is about ____, and the constraint is ____, so ____ strategy fits.” 


### Exercise C1 — Plan a study day

=== "Task"
    **Use case:** You have 1 free afternoon. You want to complete as many study tasks as possible before you must leave.

    Each task has:

    - `durationMinutes`
    - `deadlineMinutesFromNow`

    **Goal:** Choose the maximum number of tasks you can finish *before their deadlines*.

    Example input:

    - tasks = [(30, 60), (20, 25), (25, 40), (15, 20), (50, 55)]

    Required decisions:

    - What happens if tasks is empty?
    - What if two tasks have the same deadline?
    - Do you return the list of chosen tasks or just the count?

    Your group must:

    - Pick a strategy.
    - Write pseudocode.
    - Do one tiny dry run on the example.

=== "Hint"
    - This feels like scheduling/selection: you’re choosing a subset under time constraints. 
    - Try proposing a greedy rule as a group (e.g., earliest deadline first, shortest duration first) and challenge it with a counterexample.

=== "Solution"
    - **Strategy**: Greedy — sort tasks by increasing deadline; keep a running `total` time and a max-heap of chosen durations; if `total` exceeds the current deadline, remove the longest chosen task. 
    - **Why**: Deadline order ensures you never “waste” time that would make you miss an earlier deadline, and when you’re over time, dropping the single longest task frees the most time while reducing the count by only 1, which best preserves feasibility for future tasks. 

    - Decisions:
        - If `tasks` is empty: return `0`.
        - Same deadline: break ties by shorter duration first (any consistent tie-break is OK).
        - Return: count of tasks (returning the actual tasks is possible by storing IDs in the heap).

    - Pseudocode:
    ```text
    function maxTasksBeforeDeadlines(tasks):
        if tasks is empty:
            return 0

        sort tasks by deadline ascending (tie: duration ascending)

        total = 0
        maxHeap = empty   // durations of chosen tasks

        for each (dur, ddl) in tasks:
            total = total + dur
            push dur into maxHeap
            if total > ddl:
                total = total - popMax(maxHeap)   // remove longest duration

        return size(maxHeap)
    ```



### Exercise C2 — Find a name fast

=== "Task"
    **Use case:** You have a sorted list of student IDs (ascending). You want to find whether a specific ID exists.

    Example input:

    - ids = [101, 105, 120, 150, 151, 200, 260, 300]
    - target = 151

    Required decisions:

    - What if the list is empty?
    - If the target appears multiple times, do you return any index, the first, or just true/false?

    Your group must:

    - Pick a strategy.
    - Write pseudocode.
    - State time + space complexity at a high level.

=== "Hint"
    - If the data is sorted, you can often cut the search space in half each step (divide & conquer idea). 
    - Compare that to scanning from left to right (brute force baseline). 

=== "Solution"
    - **Strategy**: Divide & conquer (binary search) because `ids` is sorted, so each comparison can discard half the remaining range. 

    - Decisions:
        - If `ids` is empty: return `false` (not found). 
        - If `target` appears multiple times: return just **true/false** (existence), since that’s what the use case asks; (if you wanted the first index, you’d use a “leftmost” binary search variant). 

    - Pseudocode (returns true/false):
    ```text
    function containsSorted(ids, target):
        if length(ids) == 0:
            return false

        lo = 0
        hi = length(ids) - 1

        while lo <= hi:
            mid = lo + (hi - lo) // 2
            if ids[mid] == target:
                return true
            else if ids[mid] < target:
                lo = mid + 1
            else:
                hi = mid - 1

        return false
    ```




> Choose the strategy first; then the code becomes the easy part.
