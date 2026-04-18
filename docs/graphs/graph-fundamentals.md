# Graph Fundamentals

Every structure you have studied so far — arrays, linked lists, trees — imposes a shape on your data. Graphs do the opposite: they let the **relationships between data** define the structure. This makes them the most expressive and widely applicable tool in a programmer's toolkit.

!!! info "In this lecture"
    - **What a graph is:** informal intuition and formal definition.
    - **Types of graphs:** directed, undirected, weighted, and unweighted.
    - **Key terminology:** the vocabulary you need to read and write graph algorithms.
    - **Representations:** how to store a graph in memory using adjacency lists and matrices.
    - **Choosing a representation:** when to use which, and why it matters.


## 1) Why Graphs?

Trees model hierarchies. Graphs model **everything else**.

When you open Google Maps and ask for directions, the roads are edges and the intersections are nodes. When Instagram suggests a new follower, it is walking a graph of user connections. When your package is routed through a supply chain, each warehouse and transport link is a node and an edge in a graph.

![Graph](https://i.imgur.com/hkGULbb.png)

A few concrete mappings:

| Real-World Problem | Nodes | Edges |
| :--- | :--- | :--- |
| Navigation (Google Maps) | Cities, intersections | Roads, with distance or time as weight |
| Social networks | Users | Friendships, follows |
| Web crawling | Web pages | Hyperlinks |
| Game / maze solving | Positions | Possible moves |
| Recommendation systems | Users and items | Interactions (views, likes, purchases) |

The moment you see *"objects with relationships between them"*, you are looking at a graph problem.


## 2) What Is a Graph?

A **graph** \(G\) is formally defined as an ordered pair:

\[G = (V, E)\]

- \(V\) is a **non-empty set of vertices** (also called nodes), each representing a distinct entity.
- \(E\) is a **set of edges**, where each edge connects a pair of vertices.

In a **directed graph** (digraph), each edge is an ordered pair \((u, v)\) — it points *from* \(u\) *to* \(v\), but not necessarily the other way. In an **undirected graph**, each edge is an unordered pair \(\{u, v\}\) — the connection goes both ways.

```text
Directed:           Undirected:

  A ──► B             A ─── B
  │                   |     |
  ▼                   C ─── D
  C
```


The same \(G = (V, E)\) definition maps directly onto real systems — only the meaning of nodes and edges changes:

- **Google Maps:** \(V\) = intersections and cities, \(E\) = roads with distance or travel time as weight. The graph is **directed** (one-way streets exist) and **weighted**.
- **Instagram follows:** \(V\) = users, \(E\) = follow relationships. The graph is **directed** — Alice can follow Bob without Bob following back.
- **Supply chain:** \(V\) = warehouses and distribution centres, \(E\) = transport routes between them. The graph is **directed** and **weighted** by cost or delivery time.

The structure is always the same. What changes is what the nodes and edges *mean*.


## 3) Types of Graphs

Graphs vary along two independent dimensions: **direction** and **weight**.

| Type | What it means | Typical use |
| :--- | :--- | :--- |
| **Undirected** | Edges have no direction; relationship is mutual | Friendships, road networks |
| **Directed** | Edges point one way; relationship is one-sided | Followers, web links, task dependencies |
| **Unweighted** | All edges are equal | Connectivity problems, BFS shortest path |
| **Weighted** | Edges carry a numeric value (cost, distance, time) | Navigation, network routing |

These combine freely — a road network is typically **directed and weighted** (one-way streets, travel times). A friendship graph is typically **undirected and unweighted**.


Here is how that plays out across the use cases you already know:

| Use Case | Directed or Undirected? | Weighted or Unweighted? | Why? |
| :--- | :--- | :--- | :--- |
| **Google Maps** | Directed | Weighted | Roads can be one-way; edges carry travel time or distance |
| **Facebook friends** | Undirected | Unweighted | Friendship is mutual; no cost between connections |
| **Instagram follows** | Directed | Unweighted | Alice can follow Bob without Bob following back |
| **Supply chain** | Directed | Weighted | Goods flow one way; edges carry cost or delivery time |
| **Web crawling** | Directed | Unweighted | Page A links to page B, not necessarily the reverse |

When you encounter a new problem, ask two questions: *Is the relationship one-way or mutual?* and *Does the connection have a cost or magnitude?* The answers tell you exactly which type of graph to reach for.


## 4) Key Terminology

Understanding these terms lets you read algorithm descriptions precisely and reason about graph structure without ambiguity.

The definitions below are illustrated using a small **directed social network** — five CS pioneers where an edge \((u, v)\) means *u follows v*:

```text
  Turing ──► Dijkstra ──► Hopper
    │            │
    ▼            ▼
  Knuth ──► Lovelace
```

- **Node (Vertex):** A single entity in the graph, represented as \(v \in V\).  
  *Here: each person — Turing, Dijkstra, Hopper, Knuth, Lovelace — is a node.*

- **Edge:** A connection between two nodes. Written as \((u, v)\) in directed graphs or \(\{u, v\}\) in undirected graphs.  
  *Here: \((\text{Turing}, \text{Dijkstra})\) means Turing follows Dijkstra. Dijkstra does not necessarily follow back.*

- **Degree:** The number of edges connected to a node.
  - **In-degree:** number of incoming edges — how many people follow this person.
  - **Out-degree:** number of outgoing edges — how many people this person follows.
  - **Total degree:** in-degree + out-degree.

  ```text
  Dijkstra:  in-degree  = 1  (Turing follows Dijkstra)
             out-degree = 2  (Dijkstra follows Hopper and Lovelace)
             total      = 3
  ```

- **Path:** A finite sequence of vertices \((v_1, v_2, \ldots, v_k)\) where each consecutive pair is connected by an edge.  
  *Here: Turing → Dijkstra → Hopper is a valid path of length 2. Turing can reach Hopper indirectly through Dijkstra.*

- **Cycle:** A path where the first and last vertex are the same, and no other vertex is repeated.  
  *There is no cycle here. If Lovelace followed Turing back, the sequence Turing → Knuth → Lovelace → Turing would form a cycle — a closed loop of mutual influence.*

!!! tip "Why cycles matter"
    Detecting cycles is a real algorithmic task. In task scheduling, a cycle means two tasks each depend on the other — a deadlock. In a social graph, cycles indicate tightly connected communities.


## 5) Graph Representations

Once you have a graph on paper, you need to store it in memory. There are two standard approaches. The choice affects how much space your program uses and how fast your algorithms run.

The examples below use this small directed graph — the same follow network from Section 4, with nodes numbered for clarity:

```text
  0 (Turing) ──► 1 (Dijkstra) ──► 2 (Hopper)
       │               │
       ▼               ▼
  3 (Knuth)  ──► 4 (Lovelace)
```

### Adjacency List

Each vertex holds a list of its **direct neighbours**. You store the graph as an array of lists, where index `i` contains every vertex that vertex `i` has an edge to.

```text
0 (Turing)   →[1][2]
1 (Dijkstra) →[3][4]
2 (Hopper)   → []
3 (Knuth)    →[4]
4 (Lovelace) → []
```

Reading this: Turing follows Dijkstra and Knuth. Hopper and Lovelace follow nobody.

- Space: \(O(V + E)\) — you only store edges that actually exist
- Best for **sparse graphs** — most social networks, road maps
- Adding or removing an edge is straightforward


### Adjacency Matrix

A 2D array of size \(V \times V\). Entry `matrix[u][v] = 1` if an edge exists from `u` to `v`, and `0` otherwise. For weighted graphs, store the weight instead of `1`.

```text
         0  1  2  3  4
  0  [ 0, 1, 0, 1, 0 ]   ← Turing follows Dijkstra and Knuth
  1  [ 0, 0, 1, 0, 1 ]   ← Dijkstra follows Hopper and Lovelace
  2  [ 0, 0, 0, 0, 0 ]   ← Hopper follows nobody
  3  [ 0, 0, 0, 0, 1 ]   ← Knuth follows Lovelace
  4  [ 0, 0, 0, 0, 0 ]   ← Lovelace follows nobody
```

Reading this: to check if Turing follows Hopper, look at `matrix[0][2]` — it is `0`, so no edge exists. This lookup is always \(O(1)\).

- Space: \(O(V^2)\) — you store a cell for every possible pair, even if no edge exists
- Best for **dense graphs** — networks where most nodes are connected to most others
- Edge lookup is \(O(1)\) — check a single cell directly


!!! tip "Spot the trade-off"
    With 5 nodes and 5 edges, the adjacency list stores **10 values** \((V + E)\). The matrix stores **25 values** \((V^2)\). Now imagine a social network with 1 billion users and an average of 500 friends each — the matrix would require \(10^{18}\) cells. The list would require roughly \(500 \times 10^9\). The choice is not academic; it is the difference between a program that runs and one that runs out of memory.

## 6) Choosing a Representation

The right choice depends on the **density** of your graph and the **operations** you need most.

| Aspect | Adjacency List | Adjacency Matrix |
| :--- | :--- | :--- |
| **Best for** | Sparse graphs | Dense graphs |
| **Space** | \(O(V + E)\) | \(O(V^2)\) |
| **Edge lookup** | \(O(V)\) traversal | \(O(1)\) direct access |
| **Flexibility** | Easy to add/remove edges | Fixed size, costly to resize |

!!! tip "Decision rule"
    If your graph has far fewer edges than \(V^2\) (most real-world graphs do), **use an adjacency list**. Reserve the matrix for problems where you need constant-time edge checks across a heavily connected graph.


## 7) Real-World Applications

You have already seen how graphs model navigation, social networks, and supply chains. The same structure powers a much wider range of systems:

- **Web crawling:** The internet is a directed graph of pages linked by hyperlinks. Search engines like Google traverse this graph to discover and index content — billions of nodes, trillions of edges.
- **Game and maze solving:** A maze is a graph where each cell is a node and open passages are edges. BFS finds the shortest exit; DFS exhaustively explores every possible path.
- **Task scheduling:** A build system (like Maven or Gradle) models tasks as nodes and dependencies as directed edges. A cycle in this graph means a deadlock — Task A needs Task B, which needs Task A.
- **Knowledge graphs:** Systems like Google's Knowledge Graph connect concepts, people, and facts as nodes and edges, powering search results and structured answers.

Graph algorithms — how to actually traverse and query these structures — are covered in depth in the next section.


> "In graphs, it's all about connections — just like in networking and life."

![Final](https://i.imgur.com/ZM6n1yJ.png)