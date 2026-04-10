# Binary Search Trees: Operations & Efficiency

A standard binary tree is just a structural shell. A **Binary Search Tree (BST)** is a functional powerhouse because it enforces an ordering rule. This rule turns a simple branching structure into a high-speed engine for searching and sorting.

!!! info "In this lecture"
    - **The BST Property:** The logic that makes $O(\log n)$ possible.
    - **Key Operations:** How to search, add, and remove without breaking the tree.
    - **Traversals:** Navigating the tree to extract sorted data.
    - **The Balancing Problem:** Why sorted input is a BST's worst enemy.


## 1) The BST Property

To be a BST, every single node must follow this rule:

1. **Left Subtree:** All values must be **less than** the parent.
2. **Right Subtree:** All values must be **greater than** the parent.



**The Logic:**
```text
      [10]
     /    \
   [5]    [15]
   / \    /  \
 [2] [7][12] [20]
```
*Notice: Every node to the left of 10 is smaller than 10; every node to the right is larger. This applies recursively to every subtree (e.g., 2 and 7 are correctly placed relative to 5).*


## 2) Key Operations

### Search & Access

![Search](https://miro.medium.com/v2/resize:fit:1400/1*xP3GnQssdFyxJvG_tCnKfw.gif)

In an array, you have to check every element ($O(n)$). In a BST, you cut the search area in half at every step ($O(\log n)$).

**Example: Find 7**
1. Start at **10**. $7 < 10$, so go **Left**.
2. Arrive at **5**. $7 > 5$, so go **Right**.
3. Arrive at **7**. **Found.**

### Add (Insertion)

![Insert](https://upload.wikimedia.org/wikipedia/commons/8/83/Binary-search-tree-insertion-animation.gif)

You don't "force" a node into a specific spot. You follow the search logic until you hit a `null` gap—that is where the node belongs.

**Example: Insert 13**
```text
      10
     /  \
    5    15
        /  \
      [ ]   20  <-- 13 is < 15, so it goes here.
```

### Remove (Deletion)
Deletion is the most complex operation because you have to stitch the tree back together.

**Case 1: Delete a Leaf (Node with no children)**

Just remove it.
*Example: Delete 3.*

![DeleteLeaf](https://courses.grainger.illinois.edu/cs225/sp2026/assets/notes/bst/removeleaf.png)

**Case 2: Delete a Node with One Child**
The child moves up to take the parent's place.

![DeleteOne](https://courses.grainger.illinois.edu/cs225/sp2026/assets/notes/bst/onechildremove.png)

**Case 3: Delete a Node with Two Children**
Find the **In-Order Successor** (the smallest value in the right subtree, or the largest value on the left subtree). Swap it with the node you want to delete, then delete the successor's original spot.

![Delete Node](https://courses.grainger.illinois.edu/cs225/sp2026/assets/notes/bst/remove2child.png)


## 3) Tree Traversals
Traversal is how we "visit" every node. The order determines what the output looks like.

| Traversal | Order | Common Use |
| :--- | :--- | :--- |
| **In-Order** | Left → Root → Right | Extracting data in **Sorted Order**. |
| **Pre-Order** | Root → Left → Right | **Cloning** or serializing a tree. |
| **Post-Order** | Left → Right → Root | **Deleting** a tree (bottom-up). |



**Example Trace:**
```text
      10
     /  \
    5    15
```
* **In-Order:** `5, 10, 15` (Ascending)
* **Pre-Order:** `10, 5, 15` (Root first)
* **Post-Order:** `5, 15, 10` (Root last)


## 4) The Balancing Problem (Skewed Trees)
A BST is only efficient if it is "balanced" (bushy). If you insert data that is already sorted (e.g., 1, 2, 3, 4, 5), the tree becomes a **Skewed Tree**.

**The "Stick" Problem:**
```text
  1
   \
    2
     \
      3
       \
        4
```


**The Result:** * Search time drops from $O(\log n)$ to $O(n)$. 

* You are effectively using a Linked List with extra overhead. 
* **The Fix:** Real systems use **Self-Balancing Trees** (like AVL or Red-Black trees) that "rotate" the nodes to keep the height minimal.

---

## 5) Summary Table

| Operation | Average (Balanced) | Worst Case (Skewed) |
| :--- | :--- | :--- |
| **Search** | $O(\log n)$ | $O(n)$ |
| **Insert** | $O(\log n)$ | $O(n)$ |
| **Delete** | $O(\log n)$ | $O(n)$ |
| **Space** | $O(n)$ | $O(n)$ |

!!! warning "Student Tip"
    If you are asked to "Sort" an array using a tree, you are effectively inserting all elements into a BST and then performing an **In-Order Traversal**. Because the BST property forces smaller values to the left and larger to the right, walking the tree in the `Left → Root → Right` sequence naturally extracts the data in perfectly ascending order.


## 5) Summary & Efficiency

In a perfect world, your tree is balanced  and your operations are lightning fast. In the real world, the efficiency of your BST depends entirely on its height.

### Operation Complexity

| Operation | Average Case (Balanced) | Worst Case (Skewed) | Why? |
| :--- | :--- | :--- | :--- |
| **Search / Access** | $O(\log n)$ | $O(n)$ | In a skewed tree, you are just searching a list. |
| **Insert** | $O(\log n)$ | $O(n)$ | You must search for the correct leaf spot first. |
| **Delete** | $O(\log n)$ | $O(n)$ | Requires searching for the node and its successor. |

### Key Takeaways 

* **The BST Property:** Left < Root < Right. If this is broken at any node, the whole structure fails as a Search Tree.
* **Access vs. Search:** Remember that trees do not have indices. To "access" the 5th element, you must search for it.
* **Traversals:** * **In-Order:** Sorted output.
    * **Pre-Order:** Top-down (good for cloning).
    * **Post-Order:** Bottom-up (good for clearing memory).
* **Shape Matters:** If your tree looks like a stick, your code will run slow.

> Remember: In a tree, roots come first, just like in life

