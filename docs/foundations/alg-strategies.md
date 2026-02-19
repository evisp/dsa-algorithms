# Algorithm strategies (how to design better solutions)

Now that you can measure growth (time/space), the next skill is choosing a *strategy* that fits the problem—so you don’t default to the first solution that comes to mind. 

!!! info "In this lecture"
    - What the main algorithm design strategies are and when to use each. 
    - How to recognize “problem patterns” (searching, sorting, optimization, constraints). 
    - How to write strategy-based pseudocode with clear decisions and edge cases. 
    - How to reason about trade-offs (time vs space, simplicity vs scalability). 

## Resources

<div class="grid cards" markdown>

- :material-file-pdf-box: __Slides (PDF)__  
  Read and actively work through them.  
  [Open slides →](https://github.com/evisp/dsa-algorithms/blob/main/docs/slides/2.AlgorithmAnalysis_ProblemSolving.pdf){ .md-button .md-button--primary }

- :material-youtube: __Algorithm design techniques__  
  Overview video.  
  [Watch →](youtube.com/watch?v=y1ng8VmYMl8&feature=youtu.be){ .md-button }

- :material-notebook-outline: __Practice__  
  Do these after reading the notes.  
  [Go to practice →](practice.md){ .md-button .md-button--primary }

</div>

!!! note "How to use the materials"
    Slides first → read these notes → then do practice (we’ll keep solutions separate).

![Motivation](https://i.imgur.com/9k1tsQW.png)

## 1) A problem-solving frame

A good algorithm strategy starts *before* coding: first understand what must be solved, then choose an approach, then test and refine it. 
If you skip the “understand + plan” part, you usually end up guessing in code and missing edge cases. 

In this course, keep using the same workflow (every time): 

1. Restate the problem in one sentence (what is the goal?). 
2. List inputs and outputs (types, constraints, and what to do on empty input). 
3. Decide tricky behavior upfront (ties, invalid values, missing data). 
4. Write pseudocode (clear loops and conditions). 
5. Do a tiny dry run (3–7 items). 
6. Add 2–3 edge cases and check your steps still make sense. 
7. Only then implement in Java. 


## 2) Brute force (baseline)

**Brute force** means “try all possibilities” or “check everything directly,” with minimal cleverness. It’s often the best *starting point* because it’s simple and helps you validate what the correct answer should look like.  

It also gives you a baseline: once you have a correct brute-force solution, you can measure it and clearly see what an “improved” strategy is actually improving. Brute force is a good choice when inputs are small or when you need a quick, reliable first version, but it often becomes too slow as \(n\) grows, which is why we learn other strategies.

### Example: linear search
**Problem statement:** Find the index of `target` in an array, or return `-1` if not found. 

**Inputs / outputs**

- Input: `arr` (length \(n\)), `target`. 
- Output: index in `0..n-1` or `-1`. 

**Required decisions**

- Empty input → return `-1`.
- Multiple matches → return the first match.

**Pseudocode**
```text
if arr is empty:
    return -1

for i from 0 to length(arr)-1:
    if arr[i] == target:
        return i

return -1
```

**Edge cases**\

- `arr = []` → `-1`
- `arr = [5]`, `target = 5` → `0`
- `arr = [3, 3, 3]`, `target = 3` → `0`

**Time/space note**
- Brute force linear search checks items one by one, so it scales with the input size. 


## 3) Divide and conquer

**Divide and conquer** splits a problem into smaller subproblems, solves them (often recursively), then combines results.  
It works well when the problem can be cleanly divided into similar parts.

This approach is powerful because each subproblem is a smaller version of the original, so your solution “reuses” the same idea repeatedly.  
If the combine step is efficient, the overall algorithm can scale much better than a direct, all-at-once approach.

### Pattern
- Divide: break the problem into smaller parts.
- Conquer: solve each part (often recursively).
- Combine: merge partial answers into the final answer. 

### Example: merge sort (idea)
**Problem statement:** Sort an array in ascending order. 

**Inputs / outputs**
- Input: `arr` of length \(n\). 
- Output: sorted array. 

**Required decisions**
- Empty array → return empty array.
- Duplicates → keep them (stable behavior can be discussed later).

**Pseudocode (high-level)**
```text
function mergeSort(arr):
    if length(arr) <= 1:
        return arr

    mid = length(arr) / 2
    left = mergeSort(first half of arr)
    right = mergeSort(second half of arr)

    return merge(left, right)
```

**Merge step (core idea)**
```text
function merge(left, right):
    result = empty list
    i = 0
    j = 0

    while i < length(left) and j < length(right):
        if left[i] <= right[j]:
            append left[i] to result
            i = i + 1
        else:
            append right[j] to result
            j = j + 1

    append remaining items from left (if any)
    append remaining items from right (if any)

    return result
```

**Dry run (tiny)**

- `arr = [4, 1, 3, 2]`
- split → `[4, 1]` and `[3, 2]`
- sort halves → `[1, 4]` and `[2, 3]`
- merge → `[1, 2, 3, 4]`

**Time/space note**
- Merge sort is a classic divide-and-conquer algorithm. 


## 4) Recursion (a tool, not a goal)

**Recursion** means a function solves a problem by calling itself on smaller versions of the same problem.   

It always needs (1) a **base case** that stops, and (2) a recursive step that makes the input smaller so you actually reach the base case.   

Think of recursion as a *problem-shrinking tool*: it can make solutions clearer, but it doesn’t automatically make them efficient. 

### Example: Fibonacci (classic caution)
**Problem statement:** Compute `fib(n)` where `fib(0)=0`, `fib(1)=1`.   
This example is popular because it looks simple, but the naive recursive version repeats the same subproblems many times, so it can become very slow for large `n`. 

**Required decisions**
- If `n < 0` → error (invalid input).  
- Large `n` → warn about performance (and later we’ll learn how to avoid repeated work). 


**Pseudocode (naive recursion)**
```text
function fib(n):
    if n < 0:
        error

    if n == 0:
        return 0
    if n == 1:
        return 1

    return fib(n-1) + fib(n-2)
```

**Why this matters**
Naive recursion here repeats the same subproblems many times, which can become very slow.   
This is your first hint that “elegant” can still be inefficient—later, dynamic programming fixes this. 

## 5) Greedy algorithms

A **greedy** algorithm makes the best local choice at each step, hoping those choices add up to a globally optimal solution.   

It’s often fast and simple because it commits early and doesn’t “look back” to reconsider decisions.   

Greedy works well only when the problem has the right structure (often called the greedy-choice property), so you should either justify why greedy is correct for that problem or know a counterexample where it fails. 

### Example: activity selection
**Problem statement:** Select the maximum number of non-overlapping activities given start/end times. 

**Inputs / outputs**
- Input: list of activities `(start, end)`. 
- Output: a set/list of selected non-overlapping activities. 

**Required decisions**
- Empty list → return empty result.
- Tie on end time → pick either; define a tie rule (e.g., earlier start).

**Greedy choice**
Always pick the activity that finishes earliest (after sorting by end time). 

**Pseudocode**
```text
function selectActivities(activities):
    sort activities by end time (ascending)

    result = empty list
    currentEnd = -infinity

    for each activity a in activities:
        if a.start >= currentEnd:
            append a to result
            currentEnd = a.end

    return result
```

**Dry run (tiny)**
Activities:

- A(1, 3), B(2, 5), C(4, 7), D(6, 9)
Sorted by end: A, B, C, D
Pick A (end=3) → skip B (starts 2) → pick C (starts 4) → skip/pick D depending on overlap.

**Time note**
This approach typically involves sorting then a single pass. 


## 6) Dynamic programming (DP)

**Dynamic programming** solves problems by breaking them into subproblems and reusing results instead of recomputing them.  
It’s a strong strategy when (1) the same subproblems appear many times and (2) the best overall answer can be built from best answers to smaller parts of the problem.

DP is often the difference between “works only for small inputs” and “works at realistic scale”, because it removes repeated work by saving intermediate answers.

### Core idea (memoization)
Store the answer to each subproblem the first time you compute it, then reuse it.

In practice you’ll see two common DP styles:
- **Top-down (memoization):** write a recursive solution, but cache results so each subproblem is solved once.
- **Bottom-up (tabulation):** fill a table from small to large subproblems, so you never need recursion.


### Example: Fibonacci with memory (concept)
**Problem statement:** Same as Fibonacci, but avoid repeated work. 

**Required decisions**
- `n < 0` → error
- `n` large → ensure memory structure can hold up to `n`

**Pseudocode (top-down with memo)**
```text
function fibMemo(n, memo):
    if n < 0:
        error
    if n == 0:
        return 0
    if n == 1:
        return 1

    if memo[n] is already filled:
        return memo[n]

    memo[n] = fibMemo(n-1, memo) + fibMemo(n-2, memo)
    return memo[n]
```

**Where DP shows up**
DP is common in optimization and decision problems (e.g., knapsack-like constraints, sequence alignment, shortest path variants). 


## 7) Backtracking (search with undo)

**Backtracking** builds a solution step by step, and when a partial solution violates constraints, it undoes the last choice and tries a different path.   

It’s often used for puzzles and constraint satisfaction problems, where you need to explore possibilities but also *prune* paths that cannot work.   

You can think of it as “systematic trial + undo”: choose, test, and if it fails, back up and try the next option. 


### Example: subset sum (concept)
**Problem statement:** Given a list of integers, determine if there exists a subset that sums to `target`.

**Inputs / outputs**

- Input: `nums`, `target`
- Output: `true/false`

**Required decisions**

- Empty list → only sum 0 is possible
- If negatives exist → define whether allowed (changes pruning)

**Pseudocode (decision tree)**
```text
function existsSubsetSum(nums, index, remaining):
    if remaining == 0:
        return true
    if index == length(nums):
        return false

    # Choice 1: take nums[index]
    if existsSubsetSum(nums, index + 1, remaining - nums[index]) is true:
        return true

    # Choice 2: skip nums[index]
    return existsSubsetSum(nums, index + 1, remaining)
```

**Dry run (tiny)**

- `nums = [3, 4, 5]`, `target = 9`
- Try take 3 → remaining 6 → try take 4 → remaining 2 → fail → backtrack → try take 5 → remaining 1 → fail → backtrack → skip 3 → try 4+5 → success.


## Strategy cheat sheet

| Strategy | Use when… | Watch out for… |
|---|---|---|
| Brute force | You want the simplest correct solution first, or inputs are small.  | Can get slow fast as data grows; use it as a baseline to beat.  |
| Divide & Conquer | You can split the problem into similar smaller parts and combine results (e.g., sorting).  | The “combine” step must be efficient; recursion and extra memory can matter.  |
| Recursion (tool) | The problem naturally repeats on smaller inputs; often used inside divide & conquer.  | Always have a base case; naive recursion can repeat work and become very slow.  |
| Greedy | You can make one best choice now and move on (common in scheduling-style problems).  | Not always correct; you must know (or argue) why greedy works for that problem.  |
| Dynamic Programming | The same subproblems show up again and again, and you can reuse results.  | Uses extra memory to save time; choose memoization (top-down) or tabulation (bottom-up).  |
| Backtracking | You must try options, but stop early when a partial choice breaks constraints (puzzles, constraint problems).  | Worst-case can still be huge; pruning/constraints are what make it practical.  |



## Key takeaways
- Brute force is a good baseline, but it often doesn’t scale. 
- Divide & conquer, greedy, DP, and backtracking are patterns you can recognize and reuse. 
- Recursion is a tool; without care it can repeat work and become slow. 
- Always define decisions (ties, empty input, invalid input) before coding. 

> The right strategy doesn’t just make code faster. It makes the problem easier to think about.