# How do we measure and compare algorithms?

This lecture builds the habit you started last time: don’t just write code that works—write solutions you can compare and trust when input sizes grow.

!!! info "In this lecture"
    - What “time” and “space” complexity mean (in plain words).
    - How to estimate complexity from loops and recursion.
    - How to compare algorithms using Big-O and best/average/worst case.
    - How to validate your expectations with basic timing in Java.

## Resources

<div class="grid cards" markdown>

- :material-file-pdf-box: __Slides (PDF)__  
  Download and skim before class.  
  [Open slides →](www.google.com){ .md-button .md-button--primary }

- :material-youtube: __Big-O intuition (video)__  
  A quick recap of growth rates (pick your favorite resource).  
  [Watch →](www.google.com){ .md-button }

- :material-notebook-outline: __Practice__  
  Do these after reading the notes.  
  [Go to practice →](practice.md){ .md-button .md-button--primary }


</div>

!!! note "How to use the materials"
    Slides first → read these notes → then watch the video → then practice.


![Motivation](https://i.imgur.com/0b4VCaO.png)


## 1) What are we measuring?

When we compare algorithms, we usually compare two resources:

- **Time**: how the number of “steps” grows as input size grows.
- **Space**: how much extra memory (beyond the input) the algorithm needs as input size grows.

The key idea is scalability: an approach that feels instant on 1,000 items may be unusable on 1,000,000 items.

### Tiny intuition example 
Imagine you’re building a simple feature in a learning platform:

- **Task A:** “Is this student ID in the list?” (search)
- **Task B:** “Do we have any duplicate student IDs?” (duplicate check)

Even if both feel fast on small data, they scale very differently as the list grows. 

- **Scan a list once (Task A, linear search):** double the number of students → about **2×** more checks.
- **Scan inside another scan (naive duplicate check):** double the number of students → about **4×** more comparisons.
- **Why this matters:** at 1,000 items both may seem fine; at 100,000 items the “4× when doubling” pattern becomes a problem quickly. 

!!! warning "Common pitfalls (read this before timing anything)"
    - Treating runtime on *your* laptop as the goal (we care about **growth** as \(n\) increases).   
    - Ignoring memory use (time and space often trade off; faster can mean more memory).   
    - Comparing algorithms only on small inputs (bad growth can hide until inputs get large). 


## 2) Complexity = a “growth story” 

When we say an algorithm is “fast” or “slow”, we usually don’t mean a single timing on one laptop.   
We mean: **as the input size grows**, how quickly do the required steps (time) and required memory (space) grow? 

A simple mental model is: an algorithm is a recipe, and \(n\) is “how much food you’re cooking”.   
The key question is: if \(n\) becomes 10× bigger, does the work become ~10× bigger, ~100× bigger, or something else? 

![Complexity](https://i.imgur.com/FJM8Inc.png)

### Examples of why growth matters

- **Searching in a large list (e.g., contacts, students, products).**  
  If your method checks items one by one, it scales with how many items exist, so growth becomes noticeable as the list gets large. 

- **Detecting duplicates / matching pairs (e.g., duplicate emails, repeated IDs, “are any two files the same?”).**  
  A naive approach compares many pairs, and pairwise work can explode as the dataset grows. 

- **Sorting and merging real data (e.g., merging log files by timestamp, ranking items for a feed).**  
  Many practical systems sort or merge to make later steps predictable, so you care how sorting cost grows with input size. 

### Growth patterns you should recognize 

- **Constant-ish work:** the work doesn’t really grow with \(n\) (example idea: “read one known position”).   
- **One pass growth:** work grows in direct proportion to \(n\) (touch each item once).   
- **Pairwise growth:** work grows like “compare everything with everything” (this is the classic nested-loop explosion).   
- **Repeated halving growth:** work grows slowly because each step dramatically reduces the remaining problem (common in “search in sorted data” ideas).   
- **Sort-then-process growth:** you pay a sorting cost up front, then you often get a clean linear pass afterward. 

### Time vs space (trade-offs)

**Time complexity** is about how the number of steps grows as \(n\) grows.  

**Space complexity** is about how memory usage grows as \(n\) grows, including extra data structures and (when applicable) recursion stack depth.  

Optimizing time can increase space usage (and optimizing space can make time worse), so you usually balance both based on constraints. 

!!! warning "Quick self-check (before you see notation)"
    - Am I touching each item once, or am I repeatedly revisiting earlier items?   
    - Am I comparing pairs (or triples) of items? That’s a common growth trap.   
    - Am I storing extra data to go faster later (time–space trade-off)? 


## 3) Big-O notation (growth rates)

**Big-O** is a way to *name* a growth pattern: it classifies how an algorithm’s time or space usage increases as the input size \(n\) becomes large.   

Instead of focusing on exact milliseconds (which depend on hardware, language, and implementation details), **Big-O focuses on the dominant trend** as \(n\) grows. 

Two important details make Big-O useful:

- It ignores constant factors and lower-order terms, because for large \(n\) the dominant term shapes scalability (e.g., “\(3n + 10\)” and “\(n\)” grow in the same overall way).   
- It lets you compare algorithms at a high level across different environments, because it’s about growth with respect to \(n\), not one specific machine. 

Big-O is often used as an upper bound (commonly aligned with worst-case thinking), but it’s not the whole performance story—data patterns, constants, and memory effects still matter in practice. 

Here are the most common time complexities you’ll see: 

- **O(1)**: constant time (does not grow with input size).
- **O(log n)**: logarithmic (very scalable).
- **O(n)**: linear (usually fine).
- **O(n log n)**: linearithmic (common in efficient sorting).
- **O(n²)**: quadratic (often too slow for large inputs).
- **O(2ⁿ)**, **O(n!)**: explosive growth (avoid for large \(n\) unless the input is guaranteed small).

### A practical comparison mindset
If one algorithm is \(O(n)\) and another is \(O(n^2)\), the \(O(n)\) algorithm will **eventually** win as \(n\) grows—even if the \(O(n^2)\) version seems fine on small examples.

A useful way to feel this: when you *double* the input size, a linear algorithm tends to do about 2× the work, while a quadratic algorithm tends to do about 4× the work, so the gap widens quickly as datasets grow.

## 4) Best, average, and worst case

Some algorithms run faster or slower depending on the input arrangement, so we describe three cases. 

- **Best case**: the most favorable valid input (fastest).   
- **Worst case**: the least favorable valid input (slowest).   
- **Average case**: the *expected* runtime over “typical” inputs—this only makes sense if you define what “typical” means. 

**What “average” means:** you assume a distribution of inputs (e.g., target position is equally likely anywhere, or “target is missing 30% of the time”), then you average the number of steps under that assumption.   

!!! warning "Rule for average case"
    If you don’t state your assumption, “average case” is not a well-defined claim. 


### Worked example A: Linear search

**Problem statement:** Given an array and a target value, return the index of the target, or -1 if it’s not present.

**Inputs**

- `arr`: array of length \(n\)
- `target`: value to search for

**Output**

- An index in `0..n-1`, or `-1`

**Required decisions**

- Empty array → return `-1`
- If multiple matches exist → return the first match

**Pseudocode**
```text
if arr is empty:
    return -1

for i from 0 to length(arr)-1:
    if arr[i] == target:
        return i

return -1
```

**Time complexity**

- Best case: target is at index 0 → O(1)
- Worst case: target is last or missing → O(n)
- Average case: often treated as O(n) under typical assumptions

**Dry run (tiny input)**

- `arr = [7, 2, 9, 4]`, `target = 9`
- i=0 → 7 ≠ 9
- i=1 → 2 ≠ 9
- i=2 → 9 == 9 → return 2

**Edge cases**

- `arr = []` → return -1
- `arr = [5]`, `target = 5` → return 0
- `arr = [3, 3, 3]`, `target = 3` → return 0 (first match rule)


## 5) Example where all cases match: maximum in a list

**Problem statement:** Return the maximum value in an array of integers.

**Key idea:** You must check every element to be sure it’s the maximum, even if the array “looks sorted”.

**Inputs**

- `arr`: array of length \(n\)

**Output**

- Maximum value

**Required decisions**

- Empty array → error (or return a special value; choose and document one)

**Pseudocode**
```text
if arr is empty:
    error ("no max in empty array")

best = arr
for i from 1 to length(arr)-1:
    if arr[i] > best:
        best = arr[i]

return best
```

**Time complexity**

- Best = Average = Worst = O(n)

**Dry run**

- `arr = [4, 1, 6, 2]`
- best=4
- compare 1 → best stays 4
- compare 6 → best becomes 6
- compare 2 → best stays 6
- return 6


## 6) Measuring time in practice (Java timing)

Big-O tells you how an algorithm scales in theory; timing experiments help you confirm your expectations and compare two real implementations under realistic conditions.   
The goal is not one “perfect” number, but a *trend* as input size grows (does time roughly double, quadruple, etc.?). 

A basic (and fair) timing approach:

- Measure time before execution → run the algorithm → measure time after execution. 
- Repeat multiple runs and average, because single runs are noisy. 
- Test several input sizes and (if relevant) several input patterns, because best/average/worst behavior can differ. 


### Minimal timing pattern
```java
long start = System.nanoTime();
algorithm(input);
long end = System.nanoTime();
long elapsedNanos = end - start;
```

### A fair experiment plan

Timing can be misleading if you only test one input size or run the algorithm once.   
Real machines have noise (background processes, caching, JVM effects), so you want repeated runs and a range of \(n\) values to see the scaling trend rather than a single “lucky” measurement.   
By keeping input generation consistent and changing only \(n\), you make the comparison between algorithms much more trustworthy. 

1. Choose multiple input sizes (example: 1k, 2k, 4k, 8k, 16k, 32k). 
2. For each size, generate inputs the same way (random, sorted, reversed—state which). 
3. Run multiple trials per size and average results (one run is too noisy). 
4. Focus on trends: when n doubles, does time ~double (linear) or ~quadruple (quadratic)? 

!!! warning "What can go wrong (so you don’t misinterpret results)"
    - One-off measurements are noisy (background tasks, CPU frequency changes, caching effects). 
    - The JVM may warm up (later runs can be faster after JIT optimization). 
    - Different input patterns can change behavior (best/average/worst can look very different). 


## Key takeaways
- Compare algorithms by *growth*, not by one runtime number.
- Start by defining \(n\), then count repeated work.
- Use Big-O for scalable comparison, and timing to validate.
- Always state your decisions: empty input, ties, and what “average case” assumes.

> Don't optimize for today’s input. Optimize for how the work grows when tomorrow’s data arrives.
