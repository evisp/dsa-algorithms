# Stacks and Queues

Stacks and queues are two essential **abstract data types (ADTs)** that control *the order* in which items are removed. 

They show up everywhere in real systems (editors, browsers, scheduling, buffering), so learning them helps you recognize the right tool quickly. 

!!! info "In this lecture"
    - What an ADT is, and why “what it does” matters more than “how it’s built” at first. 
    - Stack vs queue: LIFO vs FIFO, and how that changes behavior. 
    - Core operations and vocabulary: top, front, rear, empty. 
    - Real-world use cases (undo/redo, function calls, scheduling, buffering, request handling). 
    - Variations: circular queues and deques (what problems they solve). 
    - Complexity: why the main operations are typically \(O(1)\). 

## Resources

<div class="grid cards" markdown>

- :material-file-pdf-box: __Slides (PDF)__  
  Read the slides first and take notes as you go.  
  [Open slides →](https://github.com/evisp/dsa-algorithms/blob/main/docs/slides/4.Stacks_Queues.pdf){ .md-button .md-button--primary }

- :material-youtube: __Video 1 (Stacks)__  
  See why “last in, first out” matches undo, browser back, and function calls.  
  [Watch →](https://www.youtube.com/watch?v=E8PwwXz-4DE){ .md-button }

- :material-youtube: __Video 2 (Queues)__  
  See why “first in, first out” matches waiting lines, scheduling, and buffering.  
  [Watch →](https://youtu.be/3LZDpo9EyAc){ .md-button }

- :material-notebook-outline: __Practice__  
  Do this last to check that you can apply the ideas on your own.  
  [Go to practice →](practice.md){ .md-button .md-button--primary }

</div>


!!! note "How to use the materials"
    Slides → these notes → practice, and always do a tiny dry run by hand before you debug. 

## 1) The big idea: “Who goes next?”

Stacks and queues both store sequences, but they differ in the *removal order*. That single rule determines which real systems they model well.   

> Stack = LIFO (Last In, First Out):** the most recent item added is the first one removed.   

Examples where this feels natural:

- **Undo/Redo in editors:** the last action you performed is the first action you want to undo.   
- **Browser history navigation:** the last page you visited is the first one you go back to.   
- **Function calls (call management):** the most recent function call must finish before returning to the previous one.   

> Queue = FIFO (First In, First Out):** the earliest item added is the first one removed.   


Examples where this feels natural:

- **Checkout/customer service lines:** the first person to arrive is served first.   
- **Print spooling / request handling:** jobs are processed in arrival order to keep things fair and predictable.   
- **Task scheduling and buffering:** items wait their turn, which helps smooth bursts of work or data.   


## 2) Stacks (LIFO)

A stack follows **LIFO (Last In, First Out)**: the last item you add is the first one you remove.   
You can picture it like a stack of plates where you only touch the top plate. 

![Stack](https://miro.medium.com/v2/0*IyTI2ZjInN2n_hiB.gif)

### Core operations (what you can do)

A stack is “top-focused”: you mainly interact with the item that is currently on top.  
These four actions are the basic tools you use to add, remove, or check that top item safely. 

- `push(x)`: Put `x` on top of the stack. 
- `pop()`: Take the top item off the stack and return it. 
- `peek()`: Look at the top item without removing it. 
- `isEmpty()`: Check whether the stack has no items (useful before `pop()`/`peek()`). 

### Why stacks matter (use cases)

Stacks are useful when “the last thing that happened” should be handled first. Think of moments where you want to go *back one step at a time* in the exact reverse order you did things.  

![Stack Use Cases](https://i.imgur.com/rpVti8O.jpeg)

- **Undo / redo in editors:** Your most recent change is the first one you undo (and redo brings it back in the same last-first order).
- **Browser history (Back/Forward):** You return to the most recently visited page first.
- **Function calls:** When one function calls another, the newest call must finish before you can continue where you left off.
- **Backtracking (mazes, puzzles):** When you hit a dead end, you return to the most recent choice point and try a different path.
- **Expression evaluation / parentheses matching:** You need to resolve the most recently opened part first to keep the order correct. 


## 3) Queues (FIFO)

A queue follows **FIFO (First In, First Out)**: the first item that enters is the first item that leaves.   
It matches everyday “waiting your turn” situations—new arrivals go to the back, and service happens from the front. 

### Core operations (what you can do)

A queue has two ends: you **add** items at the back and **remove** items from the front.   
These actions are the basic tools you use to manage that order without guessing what comes next. 

![Queue](https://miro.medium.com/v2/resize:fit:1280/0*HUWegihFk4x2x5vS.gif)

- `enqueue(x)` / add: Put `x` at the **back** of the line. 
- `dequeue()`: Take the item from the **front** of the line and return it. 
- `peek()` (front): Look at the front item without removing it. 
- `isEmpty()`: Check whether the queue has no items (useful before `dequeue()`/`peek()`). 


### Why queues matter (use cases)

Queues matter because they help you handle work in the same order it arrived.   
This makes systems feel fair and predictable: older items are handled before newer ones, like people waiting their turn. 

![Queues](https://i.imgur.com/nuPZgK8.png)

- **Customer service / call centers:** Calls or tickets wait in a line, and the oldest request is handled first. 
- **Printer jobs (print spooling):** Documents are printed in the order they were sent to the printer. 
- **Website/app requests:** When many requests arrive, a queue helps process them one by one in arrival order. 
- **Task scheduling (CPU/work scheduling):** Tasks wait their turn so the system can keep working smoothly instead of trying to do everything at once. 
- **Buffering (temporary waiting area):** Data can arrive in bursts; a queue holds it briefly and processes it in order. 
- **Finding things “level by level” (BFS):** When exploring a network/map, a queue helps visit the closest items first, then the next layer, in the order they were discovered. 

## 4) Variations you’ll meet

Sometimes a “normal” queue isn’t enough. Two common upgrades help when you want to use space better or you need more flexibility in how items enter and leave.

A **circular queue** is still a regular queue (first in, first out), but it’s smarter about space. Instead of letting empty spots at the front go to waste after removals, it “loops around” and reuses them. This shows up in **buffering** (data waiting to be processed) and **round-robin scheduling** (taking turns repeatedly without restarting the line).

A **deque** (double-ended queue) is a queue with two doors. You can add or remove items at the **front** and also at the **back**. This is useful when you sometimes need “wait your turn” behavior, but other times you need to handle something urgently at the front (common in task scheduling and similar systems).


## 5) Performance: what’s fast and what’s not

Stacks and queues are popular because the usual actions are quick: you add/remove from one end (stack: top; queue: front/back), so the work stays about the same even as the structure grows.   
In Big‑O terms, that’s why `push`/`enqueue`, `pop`/`dequeue`, and `peek` are typically \(O(1)\).   
But if you want to find a specific item “somewhere inside”, stacks and queues aren’t built for that—you usually have to look through items one by one, which takes \(O(n)\). 

| Operation | Stack | Queue |
|---|---|---|
| Add | Push: O(1)  | Enqueue: O(1)  |
| Remove | Pop: O(1)  | Dequeue: O(1)  |
| Peek | O(1)  | O(1)  |
| Find/search for a specific item | O(n)  | O(n)  |

!!! note "Practical rule"
    Use a **stack** when you need “most recent first” (undo, backtracking, returning from function calls).   
    Use a **queue** when you need “first come, first served” (waiting lines, scheduling, buffering, handling requests in arrival order). 

> Remember: Stacks push you to the top, but queues keep you in line.
