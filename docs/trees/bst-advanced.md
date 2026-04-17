# BST in Practice — Building a Product Catalog

A BST isn't just a diagram on a whiteboard. This page walks through a concrete Java implementation: a **product catalog** where each product has a unique integer ID, and the tree uses that ID as its ordering key. The full source code is available on [GitHub](https://github.com/evisp/dsa-algorithms/tree/main/code/binary_search_trees).

!!! info "In this lecture"
    - **The Data Model:** How `Product` and `Node` divide responsibility.
    - **Insertion:** The public/private recursive pattern.
    - **Search:** Watching the BST property eliminate half the tree per step.
    - **Tree Metrics:** Methods that let you inspect and verify the tree's health.
    - **Deletion:** The two-children case in a real context.
    - **Design Decisions:** The trade-offs baked into this implementation.

## 1) The Data Model

Two classes, two jobs. `Product` holds the data; `Node` wraps it so the tree can link things together.

```java
// Node.java — the structural shell
class Node {
    Product product;
    Node left, right;

    public Node(Product product) {
        this.product = product;
        this.left = null;
        this.right = null;
    }
}
```

```java
// Product.java — the payload
public class Product {
    private int id;
    private String name;
    private double price;

    public int getId()    { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("[ID: %d | %s | $%.2f]", id, name, price);
    }
}
```

The BST only ever calls `getId()` to make comparisons. The `name` and `price` fields are invisible to the tree's logic — they're just cargo that travels with the key. This separation means you could swap `Product` for any other class without touching the tree code.

!!! warning "Key Point"
    The BST property is enforced on **one field only** — `id`. If two products had the same ID, the tree would silently ignore the duplicate (see Section 6). Always document this assumption.



## 2) Building the Catalog — Insertion

Insertion follows a **public wrapper / private recursive worker** pattern. The public method is the clean entry point; the private method carries the state (the current node) through each recursive call.

```java
// Public API — hides recursion from the caller
public void insert(Product product) {
    root = insertRecursive(root, product);
}

// The logic: go left if smaller, go right if larger
private Node insertRecursive(Node current, Product product) {
    if (current == null) {
        return new Node(product);          // Base case: found the gap
    }
    if (product.getId() < current.product.getId()) {
        current.left = insertRecursive(current.left, product);
    } else if (product.getId() > current.product.getId()) {
        current.right = insertRecursive(current.right, product);
    }
    // Duplicate IDs are ignored
    return current;
}
```

**Dry Run — Building the catalog from `Main.java`:**

Products are inserted in this order: `100, 50, 150, 25, 75, 125, 175`

```text
Step 1: Insert 100       Step 2: Insert 50        Step 3: Insert 150
      [100]                    [100]                    [100]
                              /                         /    \
                           [50]                      [50]   [150]

Step 4-7: Insert 25, 75, 125, 175

            [100]
           /     \
        [50]     [150]
        / \      /   \
      [25][75] [125] [175]
```

*Notice: the insertion order was deliberately chosen to produce a balanced tree. Inserting `25, 50, 75, 100, 125, 150, 175` (sorted order) would create a straight right-leaning stick instead.*

**Common pitfalls:**

- Forgetting `return current` at the end — the node is not re-linked to its parent without it.
- Treating duplicates as errors instead of defining a clear policy (ignore, overwrite, or reject).
- Assuming the tree will "auto-balance" — standard BSTs do not rotate.



## 3) Searching the Catalog

Each comparison at a node eliminates an entire subtree. This is the BST property doing its job.

```java
private Product searchRecursive(Node current, int id, int step) {
    if (current == null) {
        System.out.println("  Step " + step + ": Hit null. ID not found.");
        return null;
    }
    System.out.println("  Step " + step + ": Comparing with ID " + current.product.getId());

    if (id == current.product.getId()) {
        System.out.println("  Found! Total comparisons: " + step);
        return current.product;
    }
    if (id < current.product.getId()) {
        return searchRecursive(current.left, id, step + 1);
    } else {
        return searchRecursive(current.right, id, step + 1);
    }
}
```

**Dry Run — `search(75)`:**

```text
Step 1: Compare with 100 → 75 < 100, go Left
Step 2: Compare with 50  → 75 > 50,  go Right
Step 3: Compare with 75  → Match! Found in 3 comparisons.
```

**Dry Run — `search(999)` (missing ID):**

```text
Step 1: Compare with 100 → 999 > 100, go Right
Step 2: Compare with 150 → 999 > 150, go Right
Step 3: Compare with 175 → 999 > 175, go Right
Step 4: Hit null → not found.
```

In a balanced tree of 7 nodes, the worst case is 3 comparisons — `log₂(7) ≈ 3`. In a skewed tree of 7 nodes, worst case is 7.



## 4) Tree Metrics — Measuring Health

A BST you cannot inspect is a black box. These utility methods let you verify the tree is behaving correctly after insertions and deletions.

| Method | What it measures | Why it matters |
| :--- | :--- | :--- |
| `getHeight()` | Longest root-to-leaf path | Proxy for worst-case search time |
| `isBalanced()` | Height difference ≤ 1 at every node | Confirms O(log n) is still valid |
| `size()` | Total node count | Quick sanity check after insert/delete |
| `countLeaves()` | Nodes with no children | Structural insight; all leaves = stick |
| `findMin()` / `findMax()` | Leftmost / rightmost node | Proves the sorted property holds end-to-end |

**Height — the key metric:**

```java
private int calculateHeight(Node node) {
    if (node == null) return -1;   // Edge-based height: empty tree = -1
    return 1 + Math.max(calculateHeight(node.left),
                        calculateHeight(node.right));
}
```

For the 7-node catalog above, `getHeight()` returns `2` — the shortest possible height for 7 nodes. If `getHeight()` ever returns `n - 1`, your tree is a linked list.

**Balance check:**

```java
private boolean checkBalance(Node node) {
    if (node == null) return true;
    int diff = Math.abs(calculateHeight(node.left) - calculateHeight(node.right));
    return diff <= 1 && checkBalance(node.left) && checkBalance(node.right);
}
```

**findMin — the simplest proof the tree is ordered:**

```java
public Product findMin() {
    if (root == null) return null;
    Node current = root;
    while (current.left != null) current = current.left;
    return current.product;   // Always [ID: 25 | Mouse | $25.00]
}
```

*Always go left. The leftmost node is guaranteed to be the smallest by the BST property.*



## 5) Deletion in Context

Deletion is covered in theory on the [BST Operations page](LINK). Here the focus is on **Case 3 — two children**, which is the only case with non-obvious code.

The strategy: don't actually remove the node. Instead, find its **in-order successor** (the smallest value in its right subtree), copy that value up, then delete the successor from its original spot.

```java
private Node deleteRecursive(Node current, int id) {
    if (current == null) return null;

    // Step 1: Navigate to the target
    if (id < current.product.getId()) {
        current.left = deleteRecursive(current.left, id);
    } else if (id > current.product.getId()) {
        current.right = deleteRecursive(current.right, id);
    } else {
        // Step 2: Found it — handle the three cases
        if (current.left == null) return current.right;   // Case A: leaf or right-only child
        if (current.right == null) return current.left;   // Case B: left-only child

        // Case C: Two children
        Node successor = findMinNode(current.right);       // Smallest in right subtree
        current.product = successor.product;               // Overwrite with successor's data
        current.right = deleteRecursive(current.right,     // Delete successor's original node
                            successor.product.getId());
    }
    return current;
}
```

**Dry Run — `delete(100)` (the root, two children):**

```text
Before:
            [100] Laptop
           /     \
        [50]     [150]
        / \      /   \
      [25][75] [125] [175]

Step 1: Find successor of 100 → go right to [150], then left to [125] (Webcam)
Step 2: Copy [125] Webcam into the root position
Step 3: Delete [125] from the right subtree (it's a leaf there)

After:
            [125] Webcam
           /     \
        [50]     [150]
        / \         \
      [25][75]      [175]
```

*The BST property still holds: everything left of 125 is smaller; everything right is larger.*



## 6) Design Decisions & Pitfalls

Every implementation makes choices. Here are the key ones in this codebase — and what they cost.

**Duplicates are silently ignored.**
In `insertRecursive`, if `product.getId() == current.product.getId()`, the method returns without inserting. This is a valid policy for a catalog with unique product IDs, but it must be documented. If you need to update a product's price, you need a separate `update()` method — insertion won't overwrite it.

**The key is an integer ID, not a String name.**
Integer comparison (`<`, `>`, `==`) is simple and fast. If you sorted by `name` instead, you would replace all `getId()` calls with `getName().compareTo(...)` — but tie-breaking (case sensitivity, locale) becomes a concern.

**Insertion order controls tree shape.**
The 7 products in `Main.java` were *deliberately* inserted in an order that produces a balanced tree (root ≈ median value first). In a real system, you have no control over insertion order, which is why production systems use self-balancing trees (AVL, Red-Black).

**Edge cases this implementation handles:**

- `search()` on a missing ID → returns `null`, logs "not found"
- `delete()` on a non-existent ID → tree is unchanged (the `null` base case in `deleteRecursive` short-circuits silently)
- `delete()` on an empty tree → `root` is `null`, `deleteRecursive` returns `null` immediately

**Edge cases this implementation does *not* handle:**

- `insert(null)` → `NullPointerException` at `product.getId()`
- Integer ID overflow (not relevant here, but worth noting in production)

!!! tip "Connecting Back to Theory"
    Run `printInOrder()` on this catalog and you get: `25 50 75 100 125 150 175` — products sorted by ID in ascending order. The tree did the sorting automatically, just by maintaining its shape. This is exactly the **In-Order Traversal → Sorted Output** property from the theory page. No extra sorting step is needed; the structure *is* the sort.

## 7) General Design Guidelines

When you move from a textbook BST to a real implementation, a handful of decisions shape everything else. Getting these right upfront saves significant refactoring later.

**Choose your key carefully.**
The key is the field the BST orders by. It must be **unique**, **comparable**, and **stable** (it should not change after insertion). An integer ID is the safest choice. A mutable field like `price` is a dangerous key — if the price changes, the node is now in the wrong position and searches will silently fail.

**Separate data from structure.**
Keep the payload class (`Product`) completely unaware of the tree. It should have no `left`, `right`, or `parent` references. This makes the data class reusable in other structures (a list, a hash map) without modification.

**Define a duplicate policy before you write a single line.**
Your three options are: ignore (current implementation), overwrite, or reject with an exception. There is no universally correct answer — but leaving it undefined guarantees a subtle bug.

**Make the recursive worker private.**
The pattern used here — a public `insert(Product p)` that delegates to a private `insertRecursive(Node current, Product p)` — is standard for a reason. It hides the `Node` type and the recursion mechanics from the caller. The caller should never need to know the tree is recursive internally.



## 8) Key Aspects to Consider Before Submitting

Use this as a self-review checklist before finalising any BST implementation.

### Correctness

- [ ] Does the BST property hold at **every node** after every operation? (Not just the root.)
- [ ] Does `search()` return `null` (not crash) for a missing key?
- [ ] Does `delete()` handle all three cases: leaf, one child, two children?
- [ ] For the two-children deletion case: is the **successor correctly re-linked**, not just copied?
- [ ] Does `insert()` respect the duplicate policy you defined?

### Edge Cases

- [ ] **Empty tree:** What does `search()`, `delete()`, `findMin()`, `getHeight()` return on an empty tree? All should handle `root == null` gracefully.
- [ ] **Single node:** Deleting the only node should leave `root = null`.
- [ ] **Skewed tree:** Does your code still produce correct output if every node is inserted in sorted order?
- [ ] **Duplicate keys:** Is the behaviour consistent with your documented policy?

### Recursion

- [ ] Is the **base case** clearly identifiable (typically `if (node == null)`)?
- [ ] Does every recursive branch make progress toward the base case?
- [ ] Does the method **return the (possibly modified) node** back up the call stack? Forgetting `return current` is the single most common BST bug.

### Complexity Awareness

| Scenario | Height | Search / Insert / Delete |
| :--- | :--- | :--- |
| Balanced tree (bushy) | $O(\log n)$ | $O(\log n)$ |
| Skewed tree (stick) | $O(n)$ | $O(n)$ |

- [ ] Can you identify what insertion order would produce a **worst-case skewed tree** for your specific dataset?
- [ ] If efficiency matters in production, have you considered a self-balancing alternative (AVL, Red-Black)?

### Readability

- [ ] Are public methods named after **what they do** (`insert`, `search`, `delete`), not how they do it?
- [ ] Is the duplicate policy documented in a comment at the point where it is enforced?
- [ ] Would a teammate be able to trace a dry run through your code without asking you questions?

!!! warning "The Most Common BST Bug"
    In a recursive deletion or insertion, always `return current` at the end of the method — even if you made no changes to that node. Without it, the parent's `left` or `right` pointer becomes `null` and you silently delete a whole subtree. If your tree shrinks unexpectedly after an operation, this is the first place to look.


> A tree is only as smart as the order you feed it. The BST property guarantees correctness — your insertion strategy determines speed.