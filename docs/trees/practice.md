# BST Practice — Properties, Traversals & Operations

This page tests your understanding of Binary Search Trees through three progressive blocks. Work through them in order; each one shifts from **reading and reasoning** to **designing** to **implementing**.

!!! info "In this page"
    - **Block 1:** Construct a tree by hand and read its properties, traversals, and deletions.
    - **Block 2:** Use case and design decisions 
    - **Block 3:** Coding tasks

## Block 1 — Build It, Read It, Break It

Work through the following parts **in order**. Each section builds on the tree you constructed in the previous one. Write your answers on paper before checking any hints.


### Part A — Build the Tree

Insert the following product IDs into an empty BST **in the order given**:

```
50, 30, 70, 20, 40, 60, 80
```

=== "Task"
    Draw the resulting tree. Label every node with its ID.

??? hint "Hint"
    Start with `50` as the root. For each subsequent value, ask: is it smaller or larger than the current node? Go left if smaller, right if larger. Repeat until you find a `null` gap.

### Part B — Tree Properties

Using the tree you drew in Part A, answer the following **without writing any code**.

=== "Task"
    1. What is the **height** of the tree?
    2. What is the **depth** of node `60`?
    3. How many **leaf nodes** does the tree have?
    4. How many **internal nodes** (non-leaves) does the tree have?
    5. Is the tree **balanced**? Justify your answer in one sentence.

??? hint "Hint"
    - Height = number of edges on the longest path from root to any leaf.
    - Depth = number of edges from the root to that specific node.
    - A leaf has no children. An internal node has at least one child.
    - A tree is balanced if the height difference between the left and right subtrees is ≤ 1 **at every node**, not just the root.

### Part C — Traversals

Still using the same tree from Part A.

=== "Task"
    Write the output of each traversal:

    1. **In-Order** (Left → Root → Right)
    2. **Pre-Order** (Root → Left → Right)
    3. **Post-Order** (Left → Right → Root)

    Then answer: which traversal would you use to print all products sorted by ID? Why?

??? hint "Hint"
    Pick a small subtree first (e.g. the left subtree rooted at `30`) and trace it in isolation. Then expand to the full tree.

### Part D — Deletion

Apply the following deletions **one at a time**, in order. Re-draw the tree after each step.

=== "Task"
    1. Delete **20** — identify the case first, then redraw.
    2. Delete **70** — identify the case first, then redraw.
    3. Delete **50** — identify the case first, then redraw.

    For each deletion, state which case applies:

    - **Case 1** — Leaf (no children)
    - **Case 2** — One child
    - **Case 3** — Two children → identify the in-order successor before redrawing

??? hint "Hint"
    For Case 3, the in-order successor is the **smallest value in the right subtree** of the node being deleted. Find it by going right once, then as far left as possible.


### Part E — Reflect

=== "Task"
    Answer the following in 1–2 sentences each:

    1. After all three deletions in Part D, is the tree still a valid BST? How would you verify this without code?
    2. What would happen to the tree's efficiency if you had inserted the original values in sorted order (`20, 30, 40, 50, 60, 70, 80`) instead?
    3. Name one real-world scenario where you would choose a BST over a sorted array. What property of a BST makes it the better fit?


## Block 2 — You Are the Engineering Team

You are a founding engineering team that just received a brief from your product manager. Your job is to make real decisions, justify them, and present your reasoning to the class.

!!! info "The Scenario — QuickCart"
    QuickCart is a new e-commerce startup. The product catalog has 10,000 items, each with an `id`, `name`, `category`, and `price`. The team needs to decide how to store, search, and manage products efficiently. You have been hired as the backend engineering team. The PM has split the work into five briefs — one per group.


### Group 1 — The Key Decision

The team is debating which field to use as the BST key.

=== "Brief"
    1. Which field do you choose as the key and why?
    2. What breaks if you pick `price` as the key? What breaks if you pick `name`?
    3. What happens when a product's price changes after it has already been inserted?

    **Deliverable:** A short written justification (5–7 sentences) + present your decision to the class. Be ready to defend it.

??? hint "Hint"
    A good BST key must be unique, comparable, and stable. Ask yourself: can this field change after insertion? Can two products share the same value?


### Group 2 — The Search Feature

The PM wants two features: fast lookup by product ID, and a "browse by price range" filter (e.g. all products between $50 and $200).

=== "Brief"
    1. How does the BST handle an exact ID search? Trace the steps for `search(75)` on a small example tree.
    2. Can a BST keyed on `id` efficiently return all products in a price range? Why or why not?
    3. What data structure would you recommend for price-range queries instead?

    **Deliverable:** A diagram showing the search path for an exact lookup + a written recommendation for the price-range feature with justification.

??? hint "Hint"
    Think about what the BST property guarantees. It orders by key — what does it tell you about non-key fields like `price`?

### Group 3 — The Insertion Problem

The catalog is loaded from a CSV file where products are already sorted by ID in ascending order (`1, 2, 3, ... 10000`).

=== "Brief"
    1. Draw what the BST looks like after inserting just the first 6 products from this file.
    2. What is the worst-case search time for this tree? Express it in terms of $n$.
    3. How would you fix the loading strategy — without changing the BST code itself — so the tree stays balanced?

    **Deliverable:** Two tree diagrams (bad load vs. good load) + a one-paragraph explanation of your fix.

??? hint "Hint"
    Think about what insertion order produces a balanced tree. What value would you want to insert first from a sorted list?

### Group 4 — The Delete & Update Dilemma

The PM wants a "remove discontinued products" button and a "update price" field on the admin panel.

=== "Brief"
    1. Trace `delete(70)` on the tree below. Which case applies? What is the in-order successor?
        ```text
             [50]
            /    \
         [30]    [70]
         /  \    /   \
      [20] [40] [60] [80]
        ```
    2. A junior developer implements `updatePrice(id, newPrice)` as: delete the product, then re-insert it with the new price. What could go wrong?
    3. Write pseudocode for a safer `update()` method that avoids this problem.

    **Deliverable:** The redrawn tree after deletion + pseudocode for `update()` + a one-sentence risk warning.

??? hint "Hint"
    For question 2, think about what re-insertion does to the tree's structure. Does the product end up in the same position? Does it matter?

### Group 5 — The Scaling Question *(Extension)*

QuickCart grows from 10,000 to 10,000,000 products. The catalog is updated in real time with hundreds of insertions and deletions per second.

=== "Brief"
    1. At what point does a standard BST become a liability in this system? What is the specific risk?
    2. What is a self-balancing BST and what problem does it solve? Name one example (AVL or Red-Black tree).
    3. Research one real database or system that uses a tree structure internally. Why did they choose it?

    **Deliverable:** A one-slide pitch — *"We recommend switching to X because..."* — presented to the class in 3 minutes.

??? hint "Hint"
    Think about what happens to tree height when insertions arrive in unpredictable order at high volume. What guarantee does a self-balancing tree provide that a standard BST does not?

### Presentation Rules

Each group has **10 minutes** to prepare and **3–5 minutes** to present. After each presentation, other groups may challenge with one question.

!!! warning "Cross-Group Dependency"
    Group 2's recommendation depends on the key that Group 1 chose. Group 4's delete trace uses the same tree that Group 3 discussed. Listen to each other's presentations, your answers may need to reference them.

## Block 3 — Implementation Tasks

The following tasks use the `ProductBST` codebase from [GitHub](LINK). Each task targets a specific method. Write and test your solution locally before submitting.

### Task 1 — `findMin()` and `findMax()` 

The catalog needs two utility methods: one that returns the product with the **lowest ID** and one that returns the product with the **highest ID**.

=== "Task"
    Implement the following two methods in `ProductBST.java`:

    ```java
    public Product findMin() {
        // your code here
    }

    public Product findMax() {
        // your code here
    }
    ```

    **Constraints:**
    - Both methods must be **iterative** (no recursion).
    - If the tree is empty, return `null`.
    - Do not traverse the entire tree — use the BST property to go directly to the answer.

    **Verify your solution** by calling both methods on the catalog from `Main.java` and confirming:
    - `findMin()` returns `[ID: 25 | Mouse | $25.00]`
    - `findMax()` returns `[ID: 175 | Headphones | $150.00]`

??? hint "Hint"
    The BST property tells you exactly which direction to go. To find the minimum, ask yourself: where are the smallest values always stored?


### Task 2 — `countLeaves()` 

A leaf node is a node with **no children**. This method counts how many leaf nodes the tree currently has.

=== "Task"
    Implement the following method in `ProductBST.java`:

    ```java
    public int countLeaves() {
        // your code here
    }
    ```

    **Constraints:**
    - Must be **recursive**.
    - An empty tree has `0` leaves.
    - A tree with only a root node has `1` leaf.

    **Verify your solution** on the catalog from `Main.java`:
    - The 7-node catalog should return `4` leaves: nodes `25`, `75`, `125`, `175`.

    **Then answer in a comment:** what is the time complexity of `countLeaves()`? Why can it not do better than that?

??? hint "Hint"
    Think about the base cases first. When do you know a node is a leaf? When do you know a node is `null`? Write those two cases before anything else.


### Task 3 — `searchByName(String name)` 

The BST is keyed on `id`. A user wants to search for a product by **name** instead (e.g. `"Webcam"`).

=== "Task"
    Implement the following method in `ProductBST.java`:

    ```java
    public Product searchByName(String name) {
        // your code here
    }
    ```

    **Constraints:**
    - The search must be **case-insensitive** (`"webcam"` and `"Webcam"` both match).
    - Return the first matching product found, or `null` if no match exists.
    - You may not add any new fields or restructure the tree.

    **Then answer the following in a comment above the method:**
    1. What traversal strategy did you use and why?
    2. What is the time complexity of this method compared to `search(int id)`?
    3. Why is the BST property of **no help** here?

??? hint "Hint"
    Since `name` is not the key, the BST property gives you no directional guidance. You have no choice but to visit every node. Which traversal you pick does not affect correctness — but pick one and be consistent.


### Task 4 — `isSkewed()`

A skewed tree is one where every node has **at most one child** — the tree has degenerated into a straight line. This method detects that condition.

=== "Task"
    Implement the following method in `ProductBST.java`:

    ```java
    public boolean isSkewed() {
        // your code here
    }
    ```

    **Constraints:**
    - Return `true` if every node in the tree has at most one child.
    - An empty tree and a single-node tree both return `true`.
    - Must work for both left-skewed and right-skewed trees.

    **Verify your solution** with two cases:
    - The balanced 7-node catalog from `Main.java` → should return `false`.
    - A tree built by inserting `10, 20, 30, 40` in that order → should return `true`.

    **Then answer in a comment:** if `isSkewed()` returns `true` on your catalog, what does that tell you about the order products were inserted? What would you do to fix it?

??? hint "Hint"
    A node causes the tree to *not* be skewed when it has **both** a left and a right child. Think about what condition to check at each node, and how to propagate that check recursively.