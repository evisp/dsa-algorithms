# Tree Fundamentals

Trees organize data hierarchically. Unlike arrays or linked lists where data is arranged in a flat line, trees branch out. Understanding these basic structures and their terminology is the required first step before moving on to Binary Search Trees (BSTs).

In this tutorial, you will learn the anatomy of a tree, trace small examples by hand, and see how trees can be built using both arrays and linked nodes.

!!! info "In this lecture"
    - **Core concepts:** Hierarchy, acyclic structures, and root-to-leaf paths.
    - **Terminology:** Root, leaf, parent, child, sibling, depth, height, and subtrees.
    - **Representations:** How to store trees in arrays vs. memory pointers.
    - **Categorization:** General trees vs. binary trees.

## Resources

<div class="grid cards" markdown>

- :material-file-pdf-box: **Slides (PDF)**
    Review these first. They cover visual intuition, terminology, and memory diagrams.
    [Open slides →](https://github.com/evisp/dsa-algorithms/blob/main/docs/slides/8.Trees.pdf){ .md-button .md-button--primary }

- :material-notebook-outline: **Practice**
    Complete this after reading. You will draw trees, label terms, and calculate array indices.
    [Go to practice →](practice.md){ .md-button .md-button--primary }

</div>

!!! note "How to study this material"
    Read the definitions, draw a simple 5-node tree on paper, and physically label every term. Then, calculate where each node would sit in an array. Always ask yourself: "Where is index 2? What is the height? Are there any cycles?"


## 1) Why Trees Matter

Flat data structures (like standard arrays or linked lists) often require scanning every item to find what you need. Trees allow you to skip entire branches of data, making searches significantly faster.

**Real-world examples of tree structures:**

- **File systems:** Folders containing folders.
- **Organization charts:** Executives → Managers → Employees.
- **Decision making:** If/then branching logic.

**A simple hierarchy:**

```text
Documents/
├── Work/
│   └── report.pdf
└── Personal/
    └── photo.jpg
```

## 2) What is a Tree?

In computer science, a tree is a **directed, acyclic, connected graph**. 

**The strict rules of a tree:**

1. **One Root:** There is exactly one starting node at the top.
2. **Directional Edges:** Connections only point downwards (from parent to child).
3. **Acyclic (No Cycles):** You can never loop back up to a previous node. 
4. **Single Parent:** Every node (except the root) has exactly one parent.

**A standard 5-node binary tree:**

```text
      10
     /  \
    5    15
   / \
  3   7
```

**Basic Node Structure (Pseudocode):**

```java
class Node {
    int data;
    Node[] children; // Array/List of children for a general tree
                     // Or just 'Node left, Node right' for a binary tree
}
```


## 3) Complete Terminology

### Anatomy of a Tree

- **Node:** A single unit of data in the tree.
- **Root:** The absolute top node. It has no parent.
- **Leaf:** A node with zero children. (e.g., `3`, `7`, and `15` in the tree above).
- **Edge:** The connection between two nodes.
- **Parent/Child:** A node pointing down to another is the parent; the node being pointed to is the child.
- **Sibling:** Nodes that share the exact same parent.
- **Subtree:** Any node in the tree can be viewed as the "root" of its own smaller tree. 

### Measurements: Height vs. Depth

Students frequently confuse these two. Memorize the difference:

- **Depth (Counts Down):** The number of edges from the **root down to the node**.
    - The root has a depth of `0`.
    - Nodes `5` and `15` have a depth of `1`.
- **Height (Counts Up):** The number of edges from the **node down to its furthest leaf**.
    - All leaves have a height of `0`.
    - Node `5` has a height of `1`.
    - The root node `10` has a height of `2`. (The height of the root is considered the height of the entire tree).


## 4) Array Representation

Trees can be flattened into a standard array. This is highly efficient for **complete trees** (where every level is completely filled) because it wastes zero memory on pointers.

**The Math (0-Indexed Array):**
If a parent node is at index `i`:

- Left child is at index: `(2 * i) + 1`
- Right child is at index: `(2 * i) + 2`
- Parent is at index: `(i - 1) / 2` *(using integer division)*

**Tracing our 5-node tree:**

```text
      10 (i=0)
     /  \
 (i=1)5  15 (i=2)
   / \
(i=3)3 7 (i=4)
```

| Index | 0  | 1 | 2  | 3 | 4 |
|-------|----|---|----|---|---|
| Value | 10 | 5 | 15 | 3 | 7 |

**Pros:** Extremely compact, fast mathematical access.  
**Cons:** Fixed size. If the tree is sparse (unbalanced), the array will be full of empty, wasted spaces.


## 5) Linked Representation

This is the most common way to build trees. We use dynamic objects connected by memory pointers, exactly like a Linked List, but with multiple pointers per node.

**Binary Node Setup:**
```java
class Node {
    int data;
    Node left;
    Node right;
}
```

**Memory trace for our 5-node tree:**

- `Node(10)` → `left` points to `Node(5)`, `right` points to `Node(15)`
- `Node(5)`  → `left` points to `Node(3)`, `right` points to `Node(7)`
- `Node(15)` → `left` is `null`, `right` is `null` (It's a leaf)
- `Node(3)`  → `left` is `null`, `right` is `null` (It's a leaf)
- `Node(7)`  → `left` is `null`, `right` is `null` (It's a leaf)

**Pros:** Easily grows and shrinks; handles unbalanced data without wasting empty space.  
**Cons:** Uses slightly more memory because every node must store pointer addresses.


## 6) Tree Types Overview

| Type | Restriction | Common Use Case |
|------|-------------|-----------------|
| **General** | Unlimited children per node. | File systems, DOM parsing. |
| **Binary** | Maximum of 2 children per node. | Binary Search Trees, basic routing. |
| **Full** | Nodes have exactly 0 or 2 children (no single children). | Expression parsing, Huffman coding. |
| **Perfect** | All internal nodes have 2 children, and all leaves are at the exact same level. | Efficient data routing, foundational for heaps. |


## 7) Common Pitfalls

!!! warning "Watch out for these mistakes"
    - **Drawing cycles:** If you can loop infinitely following the arrows, it is a graph, not a tree.
    - **Swapping height and depth:** Remember: *Depth* drops down from the root. *Height* builds up from the leaves.
    - **Array off-by-one errors:** The formula `2i + 1` only works if the root is at index `0`. If you put the root at index `1` by mistake, the math breaks.
    - **Defining leaves incorrectly:** A leaf isn't just "at the bottom"—it is strictly any node with *zero* children. 

## 8) Summary

- A tree is a **hierarchy without cycles**. 
- Learn the difference between depth (distance from root) and height (distance to leaf).
- Use arrays for dense, static trees (like heaps). Use linked nodes for dynamic, changing data.
- Binary trees are just a structural rule (max 2 children); they set the stage for Binary Search Trees (BSTs) in the next lesson.

> Remember: In a tree, roots come first, just like in life
