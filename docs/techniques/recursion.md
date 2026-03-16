# Recursion


Recursion solves problems by letting a function call itself on a smaller version of the same problem.  
Instead of handling the full task all at once, it breaks the problem into simpler subproblems until it reaches a case that is easy to solve directly.  
This idea appears in file systems, web crawlers, pathfinding, tree and graph traversal, and divide-and-conquer algorithms like merge sort and quick sort.


>> Instead of solving everything at once, recursion breaks a big task into smaller tasks until it reaches a simple case that can be solved directly.


![Motivation](https://i.imgur.com/2eXWwCS.png)


In this tutorial, you'll learn what recursion really is, how the call stack behaves, when recursion is a great idea, when it is not, and how classic examples like factorial, Fibonacci, and Towers of Hanoi make the pattern clear.

!!! info "In this lecture"
    - What recursion is: a function solving a problem by calling itself on a smaller subproblem.
    - Base case and recursive step: the two parts that make recursion work.
    - Classic examples: factorial, Fibonacci, sum of first N numbers, and Towers of Hanoi.
    - Recursion vs iteration: trade-offs in time, memory, readability, and practical use.


## Resources


<div class="grid cards" markdown>


- :material-file-pdf-box: **Slides (PDF)**  
  Read first — covers the intuition, examples, pitfalls, and use cases of recursion.  
  [Open slides →](practice.md){ .md-button .md-button--primary }


- :material-youtube: **Recursion Fundamentals**  
  Watch after slides to see call stack behavior and tiny examples step-by-step.  
  [Watch →](practice.md){ .md-button }


- :material-notebook-outline: **Practice**  
  Do last: trace recursive calls, identify base cases, and compare recursive vs iterative solutions.  
  [Go to practice →](practice.md){ .md-button .md-button--primary }


</div>


!!! note "How to use the materials"
    Slides → notes → short video → practice.  
    If recursion feels confusing, trace one tiny example by hand and write every function call on paper before reading code.


## 1) The big idea: solve big problems by shrinking them


Recursion is useful when a problem has the **same structure** at different sizes.  
Instead of solving the whole problem directly, you solve one smaller version, then another smaller version, and so on, until the problem becomes simple.

Think of it like climbing down a ladder one step at a time until you reach the ground.  
The key idea is: each recursive call should move you **closer** to the stopping point.


**Real-world intuition:**


- File systems: folders contain folders inside folders
- Web crawlers: one page leads to more pages
- Maze/pathfinding: explore one path, then smaller remaining choices
- Divide-and-conquer algorithms: split big problems into smaller ones

![Real World Use Cases](https://i.imgur.com/ypaWhVO.png)


>> If the problem naturally breaks into smaller copies of itself, recursion is worth considering.


## 2) What recursion is


Recursion happens when a function calls itself to solve a smaller version of the same problem.  
**Each call gets stored in memory until it finishes, and then results return back up the chain.**


A recursive solution usually has two required parts:


- A **base case** that stops the recursion
- A **recursive step** that reduces the problem


Without a base case, recursion never stops.  
Without progress toward the base case, recursion also never stops.


**Tiny idea example:**  
To count down from 3:

- Print 3, then solve “count down from 2”
- Print 2, then solve “count down from 1”
- Print 1, then stop


**Pseudocode**:
```text
function countdown(n):
    if n == 0:
        return
    print(n)
    countdown(n - 1)
```


## 3) Base case and recursive step


These are the two most important ideas in recursion.  Every correct recursive solution depends on them: one part tells the function when to stop, and the other part tells it how to keep reducing the problem in a meaningful way. 

### Base case
The base case is the smallest or simplest version of the problem.  It gives a direct answer without making another recursive call, so this is the point where the recursion stops and begins returning results back up the call stack.  You can think of it as the “nothing more to break down” case. 

### Recursive step
The recursive step handles the larger version of the problem by reducing it to a smaller or simpler one and then calling the function again.  Its job is not just to repeat the same work, but to move the input closer to the base case each time.  If the recursive step does not make real progress, the function will keep calling itself forever or until the program crashes. 


**Mental model:**

- Base case = “I already know how to solve this tiny version.”
- Recursive step = “Reduce the big version into a smaller one.”


**Example:** Factorial  
\(5! = 5 \cdot 4!\)  
\(4! = 4 \cdot 3!\)  
\(3! = 3 \cdot 2!\)  
\(2! = 2 \cdot 1!\)  
\(1! = 1\)


Here:

- Base case: `factorial(1) = 1`
- Recursive step: `factorial(n) = n * factorial(n - 1)`


**Pseudocode**:
```text
function factorial(n):
    if n == 1:
        return 1
    return n * factorial(n - 1)
```


!!! warning "Common mistake"
    A recursive function is not correct just because it calls itself.
    It must:
    
    - stop at a base case,
    - reduce the problem size,
    - and return the correct value while unwinding.


## 4) Call stack intuition


Every recursive call is placed on the **call stack**.  

That means the program remembers where it was, what values it had, and what work remains after the deeper call returns.


For factorial of 4, the calls look like this:


- `factorial(4)` waits for `factorial(3)`
- `factorial(3)` waits for `factorial(2)`
- `factorial(2)` waits for `factorial(1)`
- `factorial(1)` returns `1`
- Then the stack unwinds: `2 * 1`, then `3 * 2`, then `4 * 6`

![Factorial Stack](https://dkq85ftleqhzg.cloudfront.net/algo_book/images/recursion/pop_from_stack.png)


This “go down, then come back up” pattern is why recursion can feel harder at first.  
You need to understand both the **calling phase** and the **returning phase**.


>> When recursion is confusing, draw the call stack.


## 5) Classic examples


### Factorial


Factorial is a standard first example because the recursive pattern is clear.  
Each value depends on the previous smaller factorial.


**Definition**:
```text
n! = n × (n-1) × (n-2) × ... × 2 × 1
```


**Recursive idea**:
```text
n! = n × (n-1)!
```


**Pseudocode**:
```text
function factorial(n):
    if n == 1:
        return 1
    return n * factorial(n - 1)
```


**Complexity**:
| Measure | Cost |
|---------|------|
| Time | O(n) |
| Space | O(n) |


**How to read**:  
There are `n` calls, and each call uses stack memory.


### Fibonacci


Fibonacci shows recursion clearly, but also shows how recursion can become inefficient.


**Sequence**:
```text
1, 1, 2, 3, 5, 8, 13, ...
```


**Recursive idea**:
```text
fib(n) = fib(n - 1) + fib(n - 2)
```


**Pseudocode**:
```text
function fib(n):
    if n <= 1:
        return 1
    return fib(n - 1) + fib(n - 2)
```

![Fibonacci Stack](https://miro.medium.com/v2/1*svQ784qk1hvBE3iz7VGGgQ.jpeg)

**Why it is expensive**:  
The same values get recomputed many times.  
For example, `fib(5)` needs `fib(4)` and `fib(3)`, but `fib(4)` also needs `fib(3)` again.


**Complexity**:
| Measure | Cost |
|---------|------|
| Time | O(2^n) |
| Space | O(n) |


!!! warning "Important lesson"
    Recursive Fibonacci is elegant, but inefficient.
    Good idea for teaching recursion.
    Bad idea for large inputs.


### Sum of first N numbers


This example is simpler than Fibonacci and helps you see progress toward a base case.


**Idea**:
```text
sum(n) = n + sum(n - 1)
```


**Pseudocode**:
```text
function sum(n):
    if n == 1:
        return 1
    return n + sum(n - 1)
```


**Tiny example**:  

`sum(4) = 4 + sum(3)`  
`sum(3) = 3 + sum(2)`  
`sum(2) = 2 + sum(1)`  
`sum(1) = 1`


Then it returns:  

`1 → 3 → 6 → 10`


## 6) Recursion vs iteration

Recursion and iteration can often solve the same problem, but they do not always have the same practical cost.

### Recursion
- Often shorter and more elegant
- Natural for trees, graphs, divide-and-conquer, and backtracking
- Uses call stack memory
- Can be slower due to function-call overhead


### Iteration
- Uses loops instead of self-calls
- Usually easier on memory
- Often faster in practice
- Better for simple repetition problems


**Quick comparison:**
| Approach | Time | Extra Space | Good for |
|----------|------|-------------|----------|
| Recursive factorial | O(n) | O(n) | Teaching, elegance |
| Iterative factorial | O(n) | O(1) | Practical efficiency |
| Recursive Fibonacci | O(2^n) | O(n) | Concept demonstration |
| Iterative Fibonacci | O(n) | O(1) | Real implementation |



>> Use recursion when it makes the problem simpler to express.  
>> Use iteration when a loop is simpler and more efficient.


## 7) When recursion is a good idea


Recursion is a strong choice when the problem naturally splits into smaller self-similar subproblems.


**Great use cases:**

- Tree traversals
- Graph depth-first search
- Divide-and-conquer algorithms like merge sort and quick sort
- Binary search
- Backtracking problems like Sudoku or N-Queens
- Generating permutations or combinations


**Avoid recursion when:**


- A simple loop solves the problem more clearly
- The recursion depth may become too large
- Memory usage matters a lot
- The recursive version repeats too much work


**Real advice**:  

Recursion is a tool, not a rule.  
Do not force recursion into every problem.


## 8) Advanced example: Towers of Hanoi


Towers of Hanoi is one of the clearest demonstrations of recursion.


You have:
- Three rods
- Several disks of different sizes
- The goal of moving all disks from one rod to another


**Rules:**


1. Only one disk can be moved at a time.
2. You can only move the top disk of a rod.
3. You cannot place a larger disk on top of a smaller disk.

![Towers of Hanoi](https://miro.medium.com/v2/resize:fit:640/format:webp/1*qMZVgYekRZ6Wgalu6w1jkA.gif)

### The recursive strategy


To move `n` disks from source to destination:


1. Move `n - 1` disks from source to helper
2. Move the largest disk to destination
3. Move `n - 1` disks from helper to destination


If there is only one disk, move it directly.  
That is the base case.


**Pseudocode**:
```text
function hanoi(n, source, helper, destination):
    if n == 1:
        print("Move disk from " + source + " to " + destination)
        return

    hanoi(n - 1, source, destination, helper)
    print("Move disk from " + source + " to " + destination)
    hanoi(n - 1, helper, source, destination)
```


**Why it matters**:  
This example makes the recursive structure very visible.  
Solve a smaller version, do one direct action, then solve the smaller version again.


**Complexity**:
| Measure | Cost |
|---------|------|
| Time | O(2^n) |
| Space | O(n) |


**How to read**:  
Each extra disk makes the problem much larger.  
This is a correct recursive solution, but not a cheap one.


### Another recursion example from algorithms


Quick sort also uses recursion:

- Partition the array around a pivot
- Recursively sort the left side
- Recursively sort the right side


This is one reason recursion matters in Data Structures and Algorithms: many important algorithms depend on it.

![Quick Sort](https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/Quicksort-example.gif/250px-Quicksort-example.gif?_=20110419161403)

## 9) Common pitfalls


Recursion is powerful, but practicioners often make the same mistakes.


!!! warning "Most common problems"
    - Missing base case
    - Wrong base case
    - Recursive call does not reduce the problem
    - Returning the wrong value after the recursive call
    - Infinite recursion
    - Stack overflow
    - Recomputing the same subproblems many times


**Debugging rule**:  

Test recursion first with tiny values like `n = 1`, `n = 2`, `n = 3`.  
If those are not perfectly clear, bigger inputs will only hide the bug.


!!! note "Best debugging habit"
    Ask these three questions:
    
    1. What is the base case?
    2. How does the input get smaller?
    3. What should this function return after the recursive call finishes?


## 10) Key takeaways


Recursion means a function solves a problem by calling itself on a smaller version of that problem.  
A correct recursive solution must have a **base case** and a **recursive step**.


Use recursion when the problem naturally has self-similar structure.  
Be careful with memory usage, repeated work, and deep recursion.


Trace tiny examples first.  
If you can explain the call stack, you understand the algorithm.


> "Recursion: because sometimes, going in circles is the only way to get somewhere."
