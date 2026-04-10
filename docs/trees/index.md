# Trees & Binary Search Trees

This block moves beyond flat lists to introduce hierarchical data structures. You will learn core tree fundamentals and focus heavily on Binary Search Trees (BSTs) to store, organize, and retrieve data efficiently.

Master these concepts to build faster systems and understand the logical foundations behind advanced databases and map data structures.

!!! info "What this block is for"
    Trees organize data hierarchically, much like a computer's file system. Binary Search Trees (BSTs) combine the structural flexibility of linked lists with the elimination speed of binary search. By maintaining an order invariant where left descendants are smaller and right descendants are larger, BSTs allow you to skip entire branches during a search. This turns \(O(n)\) exhaustive searches into fast \(O(\log n)\) lookups when the tree is properly balanced.

## What you should get from this block
- Identify key tree anatomy: root, parent, child, leaf, height, and depth.
- Understand the BST order invariant and how it structurally dictates where data is placed.
- Trace and implement standard traversals (in-order, pre-order, post-order) by hand.
- Write and execute core BST operations: search, insert, and delete.
- Handle complex edge decisions safely, such as empty trees, tied/duplicate values, or deleting a node with two children.
- Analyze performance characteristics and explain why an unbalanced BST degrades to \(O(n)\) linear time.

!!! tip "How to study (simple routine)"
    Slides → draw tiny trees (3–7 nodes) by hand → trace insertions and deletions step-by-step → code the logic. 
    Always ask: "Is the tree currently empty? Does the node I'm deleting have zero, one, or two children? What is the base case for this recursive step?"

## Key topics

<div class="grid cards" markdown>

- :material-pine-tree: **Tree Fundamentals**  
  Terminology, basic anatomy, and navigating branching structures using recursion.  
  [Open →](tree-fundamentals.md){ .md-button .md-button--primary }

- :material-file-tree: **Binary Search Trees (BST)**  
  The BST order invariant, efficient searching, and recursively inserting new nodes.  
  [Open →](bst.md){ .md-button .md-button--primary }

- :material-scissors-cutting: **BST Deletion & Traversals**  
  Removing nodes safely, handling structural edge cases, and processing data in sorted order.  
  [Open →](bst-advanced.md){ .md-button .md-button--primary }

</div>

> "Linear structures line things up. Trees branch out to organize the world's complexity."

![Binary Search Tree Structure](https://i.imgur.com/TzUNzRo.png)