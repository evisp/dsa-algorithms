# Graphs

This block moves beyond hierarchical structures to introduce the most general and expressive
data structure in computer science. You will learn how to model networks of relationships
and apply fundamental traversal and pathfinding algorithms to solve real-world problems.

Master these concepts to understand the logical foundations behind navigation systems,
social networks, dependency resolution, and web crawlers.

!!! info "What this block is for"
    Graphs model pairwise relationships between objects — cities connected by roads, users
    linked by friendships, or tasks ordered by dependencies. Unlike trees, graphs impose no
    hierarchy and can contain cycles. By representing a graph as an adjacency list or matrix,
    you can systematically explore it using Breadth-First Search (BFS) or Depth-First Search
    (DFS), turning complex connectivity questions into tractable \(O(V + E)\) problems.

## What you should get from this block
- Identify key graph anatomy: vertices, edges, degree, paths, cycles, and connected components.
- Distinguish directed vs. undirected graphs and weighted vs. unweighted edges.
- Choose between adjacency list and adjacency matrix representations based on density and use case.
- Trace and implement BFS and DFS by hand, including visited-node tracking.
- Apply BFS for shortest-path (unweighted) problems and DFS for cycle detection and reachability.
- Handle edge decisions safely: disconnected graphs, isolated vertices, and self-loops.

!!! tip "How to study (simple routine)"
    Slides → draw tiny graphs (4–6 nodes) by hand → trace BFS and DFS step-by-step using a
    queue and stack respectively → code the logic.  
    Always ask: "Is the graph directed? Have I visited this node already? Is the graph
    fully connected, or do I need to restart traversal for isolated components?"

## Key topics

<div class="grid cards" markdown>

- :material-graph: **Graph Fundamentals**  
  Terminology, representations (adjacency list vs. matrix), and the structural properties
  that define different graph types.  
  [Open →](graph-fundamentals.md){ .md-button .md-button--primary }

- :material-map-search: **Graph Algorithms**  
  BFS and DFS traversals, shortest paths in unweighted graphs, cycle detection,
  and connected components.  
  [Open →](graph-algorithms.md){ .md-button .md-button--primary }

- :material-pencil-box: **Practice**  
  Hands-on problems combining representation choices and traversal strategies across
  a range of graph types.  
  [Open →](practice.md){ .md-button .md-button--primary }

</div>

> "Trees show hierarchy. Graphs show everything else — the messy, connected, cyclic world."

![Graph Structure](https://i.imgur.com/jraQNtr.png)