# Build a Stack and Queue from Scratch

Stacks and queues are small, but they teach a big lesson: **a data structure is just a few rules + careful updates**.   

When you build them yourself, you stop thinking of `push()` or `dequeue()` as magic and start seeing the simple steps underneath. 

!!! info "What you’ll learn"
    - How to build a stack from scratch using a linked list (good for a structure that grows as needed). 
    - How to build a queue from scratch using an array (good for fast, predictable storage). 
    - The key safety rules: empty checks, full checks (for fixed arrays), and keeping pointers/indexes consistent. 

You may access the complete code [here](practice.md)

## 1) Why build them yourself?

Java already has classes that behave like stacks and queues, so in real projects you’ll often just use the standard library.   

But in a data structures course, building your own is how you learn what must be true after every operation (after `push`, after `pop`, after `enqueue`, after `dequeue`). 

When you implement these from scratch, you practice three important skills:

- **Keeping a structure consistent.** After every operation, the “top” (stack) or “front/back” (queue) must still point to the correct place. 
- **Handling empty cases safely.** Your code must behave clearly when there is nothing to remove or peek at. 
- **Understanding the trade-off.** Stacks and queues are fast for adding/removing at the ends, but not designed for searching inside. 

!!! info "Bigger motivation"
    Once you can build a stack and a queue, you’re ready for bigger structures that reuse the same ideas (like deques, priority queues, and graph/maze search helpers). 

## 2) Part A — Stack from scratch (using a linked list)

A stack follows LIFO: the last item you add is the first item you remove.   
A linked list is a natural foundation because adding/removing at the front is simple, and the stack can grow without a fixed capacity. 

### Step 0 — Decide what you store
We’ll write the stack using generics so one stack can store many types (numbers, strings, your own objects).   
That’s why the node stores `T data` instead of a specific type like `int`. 

!!! note "Design choice"
    On underflow (removing from empty), the reference code prints a message and returns `null`.   
    In production code, many teams prefer throwing an exception instead, but the key is to pick one behavior and document it. 

### Step 1 — Define the Node
A node is one “box”: it stores a value and a link to the next node.   
For a stack, we only need a `next` link because we only move in one direction. 

```java
class Node<T> {
    T data;
    Node next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}
```


### Step 2 — Keep one pointer: `top`
In a list-based stack, `top` points to the most recently added node.   
If `top` is `null`, the stack is empty. 

```java
class ListStack<T> {
    private Node top; // Top of the stack

    public ListStack() {
        top = null;
    }
}
```

### Step 3 — Implement `push` (add to top)
Pushing means: create a new node and place it in front of the current top.   
The new node becomes the new `top`. 

```java
public void push(T item) {
    Node newNode = new Node<>(item);
    newNode.next = top;
    top = newNode;
}
```

!!! warning "Order matters"
    Set `newNode.next` before replacing `top`, otherwise you can lose the link to the rest of the stack. 

### Step 4 — Implement `pop` (remove from top)
Popping means: take the value at `top`, then move `top` down to the next node.   
If the stack is empty, you must handle it (underflow). 

```java
public T pop() {
    if (isEmpty()) {
        System.out.println("Stack Underflow");
        return null;
    }
    T item = top.data;
    top = top.next;
    return item;
}
```

### Step 5 — Implement `peek` and `isEmpty`
`peek` lets you look at the top item without removing it, and `isEmpty` is the safety check you use before removing. 

```java
public T peek() {
    if (isEmpty()) return null;
    return top.data;
}

public boolean isEmpty() {
    return top == null;
}
```

## 3) Part B — Queue from scratch (using an array)

A queue follows FIFO: the first item you add is the first item you remove.   
An array foundation is fast and memory-friendly, but if you use a fixed-size array you must handle “full queue” cases. 

### Step 0 — The key idea: use a circular array
A queue adds at the back and removes from the front.   
If we only moved `front` forward, we would “waste” space at the beginning—so we use wrap-around with modulo to reuse freed slots. 

### Step 1 — Track `front`, `rear`, `size`, `capacity`

These variables fully describe the queue state.   

- `front`: index of the front item to remove next.   
- `rear`: index where the last item was added.   
- `size`: number of items currently stored (also helps detect empty/full).   
- `capacity`: maximum number of items.   

```java
public class ArrayQueue<T> {
    private T[] arr;
    private int front, rear, size, capacity;

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
        front = size = 0;
        rear = -1;
    }
}
```

!!! note "Why `rear` starts at -1"
    Starting at `-1` makes the first enqueue land on index `0` after you do `rear = (rear + 1) % capacity`. 

### Step 2 — Empty and full checks
Before removing, check empty.   
Before adding, check full (because this is a fixed-size array). 

```java
public boolean isEmpty() {
    return size == 0;
}

public boolean isFull() {
    return size == capacity;
}
```

### Step 3 — Implement `enqueue` (add to the back)
Enqueue moves `rear` forward (with wrap-around), stores the item, then increases `size`.   
If the queue is full, the reference code prints a message and does nothing. 

```java
public void enqueue(T item) {
    if (isFull()) {
        System.out.println("Queue Overflow");
        return;
    }
    rear = (rear + 1) % capacity;
    arr[rear] = item;
    size++;
}
```

!!! warning "The wrap-around line is the whole trick"
    In an array queue, `rear` normally moves to the right as you add items.  
    But after you remove a few items, there can be empty slots at the beginning of the array—and a “normal” queue would get stuck at the end and waste that space.  
    `rear = (rear + 1) % capacity` makes the index **loop back to 0** when it reaches `capacity - 1`, so the queue can reuse those freed slots.  
    The same idea is used for `front` when removing, which is why this is called a *circular* queue.


### Step 4 — Implement `dequeue` (remove from the front)
Dequeue reads the front item, moves `front` forward (with wrap-around), then decreases `size`.   
If the queue is empty, the reference code prints a message and returns `null`. 

```java
public T dequeue() {
    if (isEmpty()) {
        System.out.println("Queue Underflow");
        return null;
    }
    T item = arr[front];
    front = (front + 1) % capacity;
    size--;
    return item;
}
```

After reading `arr[front]`, we move `front` one step forward so the next dequeue will remove the next item in line.  
The modulo (`% capacity`) is what makes the index “wrap around” to the start of the array when it reaches the end, so we can reuse freed space.  
Finally, we decrement `size` to keep `isEmpty()` and `isFull()` correct—`front` and `rear` only tell us *where*, but `size` tells us *how many*.


### Step 5 — Implement `front()` (peek at the front)
This returns the next item that would be removed, without removing it.   

```java
public T front() {
    if (isEmpty()) {
        System.out.println("Queue is empty");
        return null;
    }
    return arr[front];
}
```

### Step 6 — Optional: expose `size`
Keeping `size` makes it easy to report how many items are in the queue.   

```java
public int getSize() {
    return size;
}
```

## 4) Quick edge tests (how you know it works)

These are the first tests you should try after implementing each structure.   

- Stack: `pop()` on empty should trigger your underflow behavior; `push(1), push(2), pop()` should return `2`; `peek()` should not remove the item. 
- Queue: `dequeue()` on empty should trigger underflow; enqueue up to capacity should trigger overflow on the next insert; repeated dequeue/enqueue should still work because of wrap-around. 

!!! note "What students often miss"
    Most bugs here are not “Java syntax” bugs; they are “state update” bugs (forgetting to move a pointer/index, or updating in the wrong order). 

> Remember: Stacks push you to the top, but queues keep you in line.
