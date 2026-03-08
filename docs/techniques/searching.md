# Searching

Searching finds specific items in data collections fast. Without good search, even the best data is useless. 
Google needs it for results, banks for fraud detection, stores for product lookup.

Linear search checks every item one-by-one. Binary search halves the space each step (needs sorted data).

![Motivation](https://i.imgur.com/O2p82Na.png)

In this tutorial, you'll learn both methods step-by-step, their real costs, when to sort first, and Java shortcuts.


!!! info "In this lecture"
    - Linear search: simple scanning, O(n) time, works on unsorted data.
    - Binary search: divide-and-conquer, O(log n) time, requires sorted input.
    - When to sort first, real costs, and common edge cases (empty, not found).
    - Java `Arrays.binarySearch()` and best practices.

## Resources

<div class="grid cards" markdown>

- :material-file-pdf-box: **Slides (PDF)**  
  Read first — covers algorithms, examples, and complexity proofs.  
  [Open slides →](https://github.com/evisp/dsa-algorithms/blob/main/docs/slides/5.Searching_Sorting.pdf){ .md-button .md-button--primary } 

- :material-youtube: **Video 1 (Linear Search)**  
  Watch after slides to see sequential checking step-by-step.  
  [Watch →](practice.md){ .md-button }

- :material-youtube: **Video 2 (Binary Search)**  
  Focus on halving process and why log n is so fast.  
  [Watch →](practice.md){ .md-button }

- :material-notebook-outline: **Practice**  
  Do last: trace examples, code both, test edge cases.  
  [Go to practice →](practice.md){ .md-button .md-button--primary }

</div>

!!! note "How to use the materials"
    Slides → notes → Video 1 → Video 2 → practice.  
    If stuck, trace one tiny example (4-6 items) on paper before checking solutions.

## 1) The big idea: check everything vs guess smart

Linear search works like finding your keys by checking every room in your house — it always works but takes longer with bigger houses.  

Binary search is like guessing a birthday: "Is it before or after June?" — each answer cuts your work in half.

**Classroom rule**: Under 1000 items or unsorted data? Use linear. Anything bigger that you search often? Sort once, then binary forever.


## 2) Linear search

### What linear search is
Starts at position 0, checks each item one by one until it finds the target or reaches the end.  
Returns the index number if found, -1 if not found.  
Works on any data — doesn't care if sorted or unsorted.

### Core behavior
Worst case checks every single item. No shortcuts, just a simple loop from start to finish.

**Tiny example**: Find 7 in [3, 1, 7, 4]  
- Check position 0: 3 ≠ 7  
- Check position 1: 1 ≠ 7  
- Check position 2: 7 = 7 → return 2

**Real-world examples**: 

- Finding an employee's name in an unsorted employee list
- Looking up a contact in your phone's unsorted call history
- Checking if a product exists in an unsorted inventory spreadsheet

**Pseudocode**:
```
for i from 0 to length-1:
    if arr[i] == target:
        return i
return -1  // not found
```


**How it works**: 

- `i` starts at position 0 and moves right one by one
- Each step checks if `arr[i]` matches the target
- First match returns that position immediately
- Reaching end returns -1 (not found)


### Complexity
| Case | Time |
|------|------|
| Best (first item) | O(1) |
| Average | O(n/2) ≈ O(n) |
| Worst (last/not found) | O(n) |

**How to read**: Work grows with list size — fine for small n, slow for millions. 

!!! note "Java example"
    ```java
    for (int i = 0; i < arr.length; i++) {
        if (arr[i].equals(target)) return i;
    }
    return -1;
    ```

## 3) Binary search

### What binary search is

!!! warning "MUST be sorted first"
    Binary search **only** works on sorted data. Unsorted = wrong answers.

Looks at the middle item, eliminates half the list, then repeats.  
Much faster than linear for large lists — cuts work in half every step.  
Returns index if found, -1 if not found.

**Classroom rule**: Sort first. Always.

### The algorithm

We keep track of two positions: `low` (start of the search range) and `high` (end of the search range).  
On each step we compute `mid = (low + high) / 2` and compare `arr[mid]` with the target.  
If `arr[mid]` is too small, we move `low` to `mid + 1`. If it is too big, we move `high` to `mid - 1`.  
We repeat this until we either find the target or `low` goes past `high`.

**Tiny dry run**: Find `7` in the sorted array `[1, 3, 4, 7]`  

- Start: `low = 0`, `high = 3`  
  - `mid = (0 + 3) / 2 = 1`, `arr[1] = 3`  
  - `3 < 7` → target is to the **right** → set `low = 2`  

- Step 2: `low = 2`, `high = 3`  
  - `mid = (2 + 3) / 2 = 2`, `arr[2] = 4`  
  - `4 < 7` → target is to the **right** → set `low = 3`  

- Step 3: `low = 3`, `high = 3`  
  - `mid = (3 + 3) / 2 = 3`, `arr[3] = 7`  
  - `7 == 7` → found → return `3`

**Why it is fast (idea only)**:  
Each comparison throws away about half of the remaining elements.  
If you start with `n` items, after one step you have about `n/2`, then `n/4`, then `n/8`, and so on, until only 1 is left.  
The number of times you can halve `n` like this is about `log₂ n`, so the running time is \(O(\log n)\).

**Pseudocode**:
```
low = 0
high = length - 1
while low <= high:
    mid = (low + high) / 2
    if arr[mid] == target: return mid
    else if arr[mid] < target: low = mid + 1
    else: high = mid - 1
return -1
```


### Complexity

| Case | Time | What it means |
|------|------|---------------|
| Best (target at middle) | O(1) | Found on first try |
| Average | O(log n) | Typical case |
| Worst (end of list) | O(log n) | Still fast |

**How to read this table**:  

- **O(1)** = constant time, same work regardless of list size  
- **O(log n)** = logarithmic, grows very slowly  
- **30 steps** can search **1 billion items**  
- **1 million items** takes **~20 steps max**

**Real numbers**:  

- 1,000 items → 10 steps  
- 1,000,000 items → 20 steps  
- 1,000,000,000 items → 30 steps

!!! warning "Common mistake"
    Calling binary on unsorted data → wrong results! Always sort first or use linear.

## 4) Java built-ins

`Arrays.binarySearch(array, target)` — Java's fast binary search.  
**Array MUST be sorted first**. Returns index if found, negative if missing.

```java
int[] numbers = {1, 3, 5, 7};  // Must be sorted first
Arrays.sort(numbers);           // O(n log n) - do this first
int found = Arrays.binarySearch(numbers, 5);   // returns 2
int missing = Arrays.binarySearch(numbers, 4); // returns -2
```

**For lists**: `Collections.binarySearch(list, target)` — same rules apply.

**Why use it**: Java's version is faster, tested, handles edge cases. Skip writing your own.

**Key improvements:**

- **Clear workflow**: shows sort → search sequence
- **Concrete numbers**: students see exact return values (-2)
- **Practical emphasis**: "Skip writing your own" = real advice
- **Same structure**: maintains professor-style clarity


## 5) Choosing the right search (and pitfalls)

### Linear vs binary — when to use each

**Three key questions to ask:**

1. **How big is my data?** Under 100 items → linear is fine. Over 1,000 → binary wins.
2. **Is it sorted?** No → linear only. Yes → binary.
3. **How often do I search?** Once → linear OK. Repeatedly → sort + binary.

**Quick decision table:**
| Situation | Use |
|-----------|-----|
| Small data (<100 items) | Linear |
| Large data, unsorted, one search | Linear |
| Large data, will search repeatedly | Sort + Binary |
| Data always sorted | Binary |

**Rule**: If n>1000 and repeated lookups → sort + binary. Else linear.

!!! note "Real costs matter"
    Sorting 1M items once: ~20M ops. Then each binary: ~20 ops. Wins big.

### Key takeaways
Use linear for simplicity/small data. Binary + sort for speed on large/repeated searches.  
Always handle not-found (-1) and empty input. Trace tiny cases first.

> "Search smart: sort once, scan never." 