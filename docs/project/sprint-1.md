# Sprint 1 - Sorting & Searching Benchmark Lab

![Overview](https://i.imgur.com/NC0tSb9.png)

## Goal
In this sprint you’ll experience what algorithm work looks like in the real world: you don’t just “write code that works”, you build solutions that are clear, testable, and comparable. You will practice turning a specification into an implementation, checking correctness on edge cases, and developing the habit of analyzing trade-offs using evidence (measured results), not guesses.

## Project organization
The starter project is organized into a few small packages so you can focus on the algorithms while everything else stays consistent for everyone.

- `dsa.lab`  
  Entry point and configuration. `Main` is where we choose dataset types, input size, and how many runs to execute.

- `dsa.lab.model`  
  The data model used throughout the lab (e.g., `Record`). All algorithms sort/search using the same record format.

- `dsa.lab.alg.sort` and `dsa.lab.alg.search`  
  The algorithm area. This is where you implement your work in `StudentSorts` (sorting) and `StudentSearches` (searching).

- `dsa.lab.data`  
  Dataset generation. Creates different input “shapes” (random, nearly sorted, duplicates, etc.) so we can observe how algorithms behave.

- `dsa.lab.bench`  
  Benchmark runner + results output. Runs the algorithms multiple times, measures time, and writes the summary CSV table.

- `dsa.lab.util`  
  Shared helpers (copying arrays, validation checks like “is sorted?”) to keep the rest of the code clean and consistent.

!!! warning "Rule for fair comparisons"
    Do not modify the benchmark/timing logic unless your instructor explicitly asks you to. Your grade/comparison is about the algorithms, not changing the measurement setup. 

## Your tasks
Implement the required algorithms **only** in the two student files so everyone runs under the same benchmark setup:

- `dsa.lab.alg.sort.StudentSorts`
  - Implement **Insertion Sort**, **Merge Sort**, **Quick Sort**.
  - Sort rule: sort the `Record[]` **ascending by `Record.key()`**.
  - Correctness expectations: handle `null`, empty arrays, 1-element arrays, and duplicates without crashing.

- `dsa.lab.alg.search.StudentSearches`
  - Implement **Linear Search** and **Binary Search**.
  - Return rule: return a valid index if found, otherwise `-1`.
  - Duplicates: binary search may return **any** matching index.
  - Important: binary search assumes the array is already sorted by key (the benchmark prepares a sorted copy for it).

Then run `dsa.lab.Main` and confirm you get:

- Console progress output showing which dataset/algorithm is currently running (e.g., `Job 3/25: SORT QuickSort | RANDOM`).
- `results_summary.csv` in your project run directory with **one row per algorithm** (overall average across all datasets), including `avg_ms`, `final_avg_ms`, and `ok_rate`.

## Submission
Submit **two** items:

- **Source code**  
  A zip of your Eclipse project *or* a link to your GitHub repository. Your project must run by starting `dsa.lab.Main`.

- **Short report (3–5 pages, PDF)**
  Write a clear report that explains what you did and what you learned. Include:

  - **Implementation strategy:** how you approached each algorithm (Insertion, Merge, Quick; Linear vs Binary), and any design choices (e.g., pivot choice for quicksort, recursion vs iterative).
  - **Complexity analysis:** time and space complexity for each algorithm (best/average/worst where appropriate), and what that means in practice.
  - **Experimental insights:** what patterns you observed across dataset types (random, nearly sorted, duplicates, reversed, etc.), which algorithms performed well/poorly and why.
  - **Correctness & edge cases:** how you ensured the algorithms work (duplicates, empty arrays, already sorted input), and what tests you relied on.


> Make it correct first, make it clear next, and only then make it fast.