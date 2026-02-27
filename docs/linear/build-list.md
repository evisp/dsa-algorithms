# Build a Linked List from Scratch 

Linked lists show up in many “real” systems (history, undo, playlists), but the bigger reason we study them is simpler: they teach you how to build a data structure by connecting small pieces safely.  
Once you can build a linked list, stacks and queues feel much less mysterious.

!!! info "What you’ll learn"
    - Why we build a linked list ourselves (instead of using Java’s built-in classes).
    - Why we use generics (`<T>`) so one list works for many types.
    - How to implement a simple singly linked list step by step (with small code snippets).

Full code can be found [here](https://github.com/evisp/dsa-algorithms/tree/main/code/arrays_linkedlists)

## 1) Why build it yourself?

Java already provides `LinkedList`, so in real projects you will often *use* the built-in version.  
But in a data structures course, building your own is how you learn what the structure really is.

When you write a linked list from scratch, you build three important skills:

- **You learn how a data structure is “wired”.**  
  `next` is not a magic word—it’s just a reference that connects one node to the next, forming a chain.
- **You learn safe “pointer thinking” in Java.**  
  Linked lists teach you to update references in the right order so you don’t accidentally disconnect the list and lose nodes.
- **You learn to reason about cost and trade-offs.**  
  Some operations are fast because they only change a couple of links; others are slow because you must walk node by node to reach a position.

!!! info "Bigger motivation"
    Not every useful data structure is built into a language in exactly the form you need.  
    When you can build (and modify) structures yourself, you can adapt them to your problem: add a `tail`, store extra info, enforce rules, or create a specialized version for performance.


## 2) Why generics (`<T>`)?

We want **one** linked list class that can store:

- Numbers (using wrapper types like `Integer`)
- Strings
- Your own objects like `Student` or `Employee`

That is exactly what generics give us: `MyLinkedList<T>` can hold any type `T`.  
You write the linked list once, then reuse it everywhere.

!!! note "Primitives and generics"
    Java generics can’t be used directly with primitive types (so not `MyLinkedList<int>`).  
    Use wrapper types like `Integer` instead. 


## 3) Step-by-step: building the list

We’ll build a **singly linked list** (each node points forward to the next node).  
We will keep two important references: `head` (first node) and `tail` (last node).

### Step 0 — Define a Node
A node is one “box” in the chain: it stores a value and a link to the next node.  
If a node’s `next` is `null`, that node is the last one in the list. 

By connecting many nodes through `next`, we build a sequence even though the nodes may live in different places in memory. 


```java
private static class Node<T> {
    T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
    }
}
```

**Idea check:** if `next` is `null`, that node is the last one.


### Step 1 — Start the list: `head`, `tail`, `size`

An empty list has no nodes, so both `head` and `tail` are `null`. 
That makes emptiness easy to check: if `head` is `null`, there is nothing to traverse, nothing to remove, and nothing to print. 
We also keep `size` so we can answer “how many items are in the list?” quickly, without counting nodes every time.

!!! note "Important rule"
    When the list becomes empty again (after removing the last node), update **both** `head` and `tail` back to `null`.  
    This keeps the list consistent and prevents “ghost” references to old nodes.


```java
private Node<T> head;
private Node<T> tail;
private int size;

public boolean isEmpty() {
    return size == 0;
}

public int size() {
    return size;
}
```


### Step 2 — Insert at the front (`addFirst`)

Inserting at the front is the simplest (and usually fastest) insertion in a singly linked list. 
You don’t need to search for a position; everything happens right at the head.

To add at the front, we:

1. Create a new node.
2. Point it to the current head.
3. Make it the new head. 

After these steps, the new node becomes the first element, and the old list starts right after it.  
If the list was empty before, we must also update `tail` to point to this new node (because it is both first and last).

```java
public void addFirst(T value) {
    Node<T> n = new Node<>(value);
    n.next = head;
    head = n;

    if (tail == null) {
        tail = head;
    }
    size++;
}
```

This is fast because we only change a couple of references.


### Step 3 — Insert at the end (`addLast`)

Adding at the end is a very common operation (think: building a list item by item).  
If we keep a `tail` reference, we can add at the end without walking through the whole list first. 

If we keep a `tail`, adding at the end can be simple:

- Connect the old tail to the new node (`tail.next = newNode`). 
- Move `tail` forward (`tail = newNode`). 

There are two cases to handle:

- **Empty list:** `head` and `tail` should both point to the new node.
- **Non-empty list:** link from the old `tail`, then update `tail` to the new last node.

!!! note "Why `tail` matters"
    Without a `tail`, inserting at the end usually requires traversal from `head` to find the last node first, which is slower. 


```java
public void addLast(T value) {
    Node<T> n = new Node<>(value);

    if (tail == null) {
        head = n;
        tail = n;
    } else {
        tail.next = n;
        tail = n;
    }
    size++;
}
```


### Step 4 — Traversal (how we “read” the list)

Traversal is how we **visit every item** in a linked list.  
To visit nodes, we start at `head` and follow `next` until we reach `null` (which means “there is no next node”).

This is the basic pattern behind many tasks:

- Printing the list
- Searching for a value
- Finding a place to insert or delete

!!! note "Key idea"
    A linked list cannot “jump” to item #10 directly.  
    To reach a position, you usually must traverse from the start, one node at a time.

```java
Node<T> curr = head;
while (curr != null) {
    System.out.println(curr.value);
    curr = curr.next;
}
```

You will use traversal logic again and again (printing, searching, inserting in the middle).


### Step 5 — Remove from the front (`removeFirst`)

Removing the first node means “move head forward”.   
In other words, the second node becomes the new first node.

This operation is nice because it does **not** require traversal:

- If the list is empty, there is nothing to remove.
- Otherwise, set `head = head.next` and the old head is no longer part of the list. 

One more important detail: if the list had only one node, then after removal the list becomes empty, so you must also set `tail = null`.

!!! warning "Empty list check"
    Always check if `head` is `null` before removing.  
    Removing from an empty list should be handled clearly (error or special return), but it should never crash your program.


```java
public T removeFirst() {
    if (head == null) {
        throw new java.util.NoSuchElementException("Cannot remove from an empty list");
    }

    T removed = head.value;
    head = head.next;
    size--;

    if (head == null) {
        tail = null;
    }

    return removed;
}
```

### Step 6 — Remove a value (`remove`)

Removing a value from a singly linked list is trickier than removing the head, because you must keep the chain connected.  
To remove a node in the middle, you reconnect the list so it “skips over” that node by updating the previous node’s link. 

That is why we usually walk with **two references**:

- `curr` = the node we are currently looking at
- `prev` = the node just before `curr`

When we find the target, the key line is:

- `prev.next = curr.next` (this bypasses `curr`) 

There are a few important cases to handle:

- **Target is at the head:** update `head` (special case).
- **Target is in the middle:** use `prev.next = curr.next`.
- **Target is at the tail:** after bypassing, also update `tail` to `prev`.

!!! warning "Order matters"
    Always make sure you still have a way to reach the rest of the list.  
    If you lose track of `curr.next` and update links in the wrong order, you can disconnect nodes permanently.


```java
public boolean remove(T target) {
    if (head == null) return false;

    if (java.util.Objects.equals(head.value, target)) {
        removeFirst();
        return true;
    }

    Node<T> prev = head;
    Node<T> curr = head.next;

    while (curr != null) {
        if (java.util.Objects.equals(curr.value, target)) {
            prev.next = curr.next;

            if (curr == tail) {
                tail = prev;
            }

            size--;
            return true;
        }

        prev = curr;
        curr = curr.next;
    }

    return false;
}
```

!!! warning "The #1 linked-list mistake"
    If you overwrite a link too early, you can lose part of the list.  
    Always think: “After this line, can I still reach the rest of the nodes from `head`?”


## 4) A tiny demo (same list, different types)

The best proof that generics are worth it is this: we can use **the same** `MyLinkedList<T>` for completely different kinds of data. 

We don’t rewrite the linked list for numbers, and we don’t rewrite it again for `Student` objects; we just choose a different type for `T`.

Here is how we can use the same list for numbers and for `Student` objects:

```java
MyLinkedList<Integer> numbers = new MyLinkedList<>();
numbers.addLast(10);
numbers.addLast(20);
numbers.addFirst(5);

MyLinkedList<Student> students = new MyLinkedList<>();
students.addLast(new Student(1, "Ada Lovelace"));
students.addLast(new Student(2, "Alan Turing"));
students.addLast(new Student(3, "Grace Hopper"));
students.addFirst(new Student(0, "Edsger Dijkstra"));
```

Notice what stays the same: `addFirst`, `addLast`, `removeFirst`, `remove`—the list operations do not care what `T` is. 

Only the **type** changes: `Integer` for numbers (wrapper type) and `Student` for your own objects.

!!! note "Why `Integer` and not `int`?"
    With generics you use wrapper types like `Integer` instead of `int`. 


```java
MyLinkedList<Integer> numbers = new MyLinkedList<>();
numbers.addLast(10);
numbers.addLast(20);
numbers.addFirst(5);

MyLinkedList<Student> students = new MyLinkedList<>();
students.addLast(new Student(1, "Ada Lovelace"));
students.addLast(new Student(2, "Alan Turing"));
students.addLast(new Student(3, "Grace Hopper"));
students.addFirst(new Student(0, "Edsger Dijkstra"));
```

A simple `Student` class works well here because our list stores any type `T`.

```java
public class Student {
    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }
}
```

### Summary

In this page, you built a singly linked list from scratch to understand how a data structure works “inside”, not just how to use it.  

You learned that a linked list is a chain of nodes connected by `next` references, and that many operations depend on traversal from `head` to `null`. 

You also saw why we use **generics** (`MyLinkedList<T>`): one implementation can store many types (numbers via wrapper classes and custom objects like `Student`).

Most importantly, you practiced the core skill behind data structures: updating links safely so the list stays connected.

> “When you understand the links between small parts, you can build bigger systems with confidence.”
