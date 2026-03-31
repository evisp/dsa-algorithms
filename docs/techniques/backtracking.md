# Backtracking

Backtracking uses recursion to try one choice at a time, go deeper if it works, and undo to try another if stuck.  

It works well for puzzles where wrong paths are common but rules let you skip bad ideas early.  

You see it in N-Queens, Sudoku, and job interview problems like permutations.

>> Try a choice. Recurse. Undo if no solution. Repeat.

![Backtracking Tree](https://i.imgur.com/UZCvfNn.png)

In this tutorial, you learn backtracking as "recursion that undoes mistakes," trace it on N-Queens and Sudoku, see why it's slow but smart, and get tips to code your own.

!!! info "In this lecture"
    - Backtracking: recursion + undo for finding solutions.
    - Key steps: choose → check → recurse → undo.
    - Examples: N-Queens, Sudoku.
    - Costs, tips, and practice requirements.

!!! info "Sample skeleton code"
    - Use the starter code in this folder to complete the backtracking tasks:
      [Backtracking skeleton code](https://github.com/evisp/dsa-algorithms/tree/main/code/techniques/backtracking)
    - Included tasks:
      - N-Queens
      - Sudoku
    - Your job:
      - Read the problem requirements in the lecture notes.
      - Complete the missing backtracking logic.
      - Test your solution on small examples before larger cases.

## 1) The big idea: recursion + undo for exhaustive search

Backtracking handles problems too complex for direct computation by exploring a decision tree exhaustively but intelligently.  

It uses recursion to go deeper on promising paths and backtracks (undos choices) when stuck, avoiding full brute force.  

This matters because many real problems, such as scheduling, puzzles, graph coloring, have exponential possibilities that explode without pruning.

**Real-world intuition:**

- N-Queens/Sudoku: try positions, check constraints, undo invalid
- Permutations: build sequences, swap back if invalid
- Maze solving: try paths, dead-end → backtrack
- Job scheduling: assign tasks, conflict → undo

![Real World Use Cases](https://i.imgur.com/QfoSz7t.png)

>> If choices must satisfy constraints and failures are common, backtracking prunes the impossible.

## 2) What backtracking is

Backtracking is recursion with a "try → recurse → undo" cycle for search problems.  

**Each recursive call makes one choice, explores deeper if valid, and undoes it to try alternatives.**  

Without undoing, you'd get stuck in one branch; without constraints, you'd explore everything.

A backtracking solution has three parts:

- **Choose**: Pick a decision (e.g., place queen here)
- **Explore**: Recurse on the reduced problem
- **Undo**: Backtrack by removing the choice if no solution

**Tiny example:** Path in [1,2,3] picking increasing numbers.

Pseudocode:
```text
function pickIncreasing(path, candidates):
    if no more candidates:
        print(path)  // found a solution
        return
    for each candidate:
        if candidate > path.last:
            path.add(candidate)
            pickIncreasing(path, remaining)  // explore
            path.removeLast()  // undo
```

## 3) Backtracking vs plain recursion

Plain recursion computes values by shrinking; backtracking searches solutions by branching and pruning.

| Aspect | Plain Recursion | Backtracking |
|--------|-----------------|--------------|
| Goal | Compute result | Find valid path |
| Calls | Linear/branching to base | Branch on choices |
| Undo | No (builds result) | Yes (remove choice) |
| Best for | Factorial, trees | Puzzles, permutations |

### Why high complexity?

Brute force tries **every possible combination**; that's terrifying for real puzzles.  
**N-Queens: N! total boards** — N=8 means 8×7×6×5×4×3×2×1 = **40,320** full boards to check.  
**Sudoku: 9^m where m=empty cells** — 40 empty cells = 9⁴⁰ ≈ **1.2×10³⁸ possibilities** (way more than atoms in the Earth).

Backtracking **doesn't check everything** because **constraints prune dead ends early**; row/col/diag checks fail 90%+ of choices instantly.

!!! warning "Without pruning"
    Pure brute force = death:  
    - N=8 Queens: **40K boards** → 1000+ seconds  
    - Sudoku 40 empty: **10³⁸ grids** → age of universe not enough  
    Backtracking + constraints: **seconds**.

**Example breakdown:**

- N=8 Queens: 40K possibilities → **~100 valid paths** → solves instantly
- Sudoku 40 empty: 10³⁸ grids → **thousands of smart tries** → solves in 1s


>> Backtracking trades memory for systematic exploration — elegant but watch the depth.

## 4) Classic example: N-Queens

![N-Queens](https://i.imgur.com/7CgyZ61.png)

**Problem**: Place N queens on an N×N chessboard so no two attack (same row/col/diagonal).

**Inputs/Outputs**:

- Input: integer N (1 ≤ N ≤ 12); empty board
- Output: true/false + one valid board (print Q/. )
- Constraints: one queen per row; attacks checked left/diags
- Edges: N=1 (trivial), N=4 (2 sols), empty (N=0 invalid)

### N-Queens intuition

Queens attack horizontally, vertically, **or diagonally**; only one per row/column allowed.

**Why backtracking fits perfectly**:  

- **One decision per column**: Try row 0, 1, ... N-1 for current column  
- **Check attacks immediately**: Look left (row), upper/lower-left diagonals  
- **Most choices fail fast**: 90%+ row picks attack existing queens → undo instantly  
- **One solution enough**: Find first valid board, stop searching

**Mental model**: Walk column-by-column. At each step, most rows are "blocked." Try open row → check → next column → backtrack if stuck.

>> Column-by-column eliminates row/column conflicts automatically.

### Pseudocde for N-Queens

**Pseudocode** (column-by-column):
```text
function solve(board, col):
    if col == N:
        return true  // all queens placed
    for row = 0 to N-1:
        if isSafe(board, row, col):  // no attacks left/diags
            board[row][col] = 1  // place
            if solve(board, col+1):
                return true
            board[row][col] = 0  // undo
    return false
```
`isSafe`: check row left, upper/lower-left diags.

**Dry run N=2** (no solution):

- col=0: try row=0 → recurse col=1
- col=1: row=0 attacks diag → row=1 attacks col → backtrack
- col=0 row=1 → same → false

**Complexity**:

| Measure | Cost     |
|---------|----------|
| Time    | O(N!)   |
| Space   | O(N)    |


## 5) Classic example: Sudoku solver

![Sudoku](https://i.imgur.com/zeCIkve.png)

**Problem**: Fill a 9×9 grid so each row/col/3×3 box has 1-9 unique (0=empty).

**Inputs/Outputs**:

- Input: 9×9 int[][] board (0 empty)
- Output: true if solvable (modifies in-place)
- Constraints: row/col/box uniqueness
- Edges: full board (true), unsolvable partial, single empty

### Sudoku intuition

**The puzzle**: Fill 9×9 grid so **each row, column, and 3×3 box contains 1-9 exactly once**.  
Empty cells marked `0`; given numbers fixed.

**Why backtracking works here**:  

- **One empty cell at a time**: Find next `0` in row-major order  
- **Try digits 1-9**: Most fail row/col/box uniqueness check immediately  
- **Prune early**: 8/9 digits typically invalid → only 1-2 worth recursing  
- **In-place modify**: Fill → recurse → empty on backtrack

**Mental model**: Like filling a form. For each blank, cross out used numbers in row/col/box. Try remaining → next blank → erase and try different if stuck.

>> Find next empty. Try safe digits. Most fail fast. Repeat.


**Pseudocode**:
```text
function solve(board):
    pos = findEmpty(board)  // row-major next 0
    if pos == null:
        return true  // solved
    row, col = pos
    for num = 1 to 9:
        if isSafe(board, row, col, num):  // row/col/box
            board[row][col] = num
            if solve(board):
                return true
            board[row][col] = 0  // undo
    return false
```
`isSafe`: scan row/col/3×3 box for num.

**Dry run** (top-left 2×2 mini, one empty):
```
5 0
0 1
```
- pos=(0,1): try 1? row dup → 2? safe → recurse
- next pos=(1,0): try 1? col → 3? safe → solved!

**Complexity**:

| Measure | Cost      |
|---------|-----------|
| Time    | O(9^m)   |
| Space   | O(1)     |


## 6) When backtracking shines (and its costs)

Backtracking excels when problems have heavy constraints that prune most branches early.

**Great use cases:**

- Constraint puzzles (N-Queens, Sudoku)
- Permutations/combinations with validity
- Graph coloring, exact cover
- Subset sum/knapsack variants

**Avoid/costs:**

- No constraints → pure exponential explosion
- Deep recursion → stack overflow (N>30 risky)
- Optimize: good choice order (most-constrained first)

| Approach     | Time     | Good for              |
|--------------|----------|-----------------------|
| Backtracking | O(b^d)  | Prunable search       |
| Iteration    | O(b^d)  | If no recursion limit |
| DP           | O(nW)   | Overlapping subproblems |

>> Choose backtracking for clarity on tree-like searches; add memo if repeats appear.

## 7) Common pitfalls

!!! warning "Most common problems"
    - Forgetting to undo (state leaks between branches)
    - Weak isSafe (misses constraints → wrong solutions)
    - Poor order: try easy nums/rows first for faster pruning
    - No base case or infinite recursion on invalid input
    - Testing only solved cases (miss unsolvable edges)

**Debugging rule:**  
Always trace N=2/4 or 4×4 Sudoku by hand; write board states per call.

!!! note "Best debugging habit"
    Ask:
    1. Does it undo every choice?
    2. Does isSafe catch all constraints?
    3. What happens on dead-end/unsolvable input?

## 8) Practical tasks guidance

Implement N-Queens and Sudoku in Java

!!! note "Requirements checklist"
    - **Inputs/outputs**: Handle N=1-8, partial/unsolvable boards; print one solution.
    - **Pseudocode first**: Write logic before Java (no imports beyond basics).
    - **Test progression**: n=1/2/4 → full 8/9×9; time medium cases.
    - **Edges explicit**: Empty board false; full true; unsolvable → false.
    - **Extensions**: Count all solutions; optimize with bitmasks.

Trace by hand first, code second, compare runtimes.

## 9) Key takeaways

Backtracking tries one choice at a time. Go deeper if good, undo and try next if stuck.  
Practice on N-Queens and Sudoku. Good constraints make slow algorithms fast enough.

> "Try. If stuck, undo. Try next."