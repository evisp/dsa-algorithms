# Graph Algorithms: Dijkstra & A*

Every graph problem eventually asks the same question: *how do we get from here to there?* Finding a path is straightforward — finding the **shortest** or **cheapest** one is where the interesting work begins. This lecture covers the two most important shortest-path algorithms: Dijkstra's algorithm, which finds optimal paths across any weighted graph, and A*, which gets there faster when you have some idea of where you are going.

!!! info "In this lecture"
    - **Why shortest path?** The problem, and why BFS alone is not enough.
    - **Dijkstra's algorithm:** intuition, step-by-step mechanics, and complexity.
    - **The priority queue:** the data structure that makes Dijkstra tick.
    - **A\*:** how a heuristic function turns Dijkstra into a guided search.
    - **Dijkstra vs A\*:** when to use which, and why it matters.


## 1) Why Shortest Path?

Path-finding is everywhere. The moment you add **cost** to a graph — distance, time, money, risk — the question shifts from "is there a path?" to "what is the best path?" That distinction drives a huge class of real-world systems.

| Real-World Problem | Nodes | Edges | Cost |
| :--- | :--- | :--- | :--- |
| GPS navigation | Intersections | Roads | Travel time or distance |
| Game AI (NPC movement) | Grid cells or waypoints | Passable moves | Movement cost or terrain penalty |
| Network packet routing | Routers | Network links | Latency or bandwidth |
| Flight planning | Airports | Routes | Fuel cost or duration |
| Robotics | Positions in space | Reachable moves | Energy or risk |

In every case, the problem is identical at the graph level: given a weighted graph and a source vertex, find the minimum-cost path to one or more target vertices. Dijkstra and A\* are the two canonical answers.


## 2) The Problem, Formally

Given a weighted, directed graph \(G = (V, E, w)\) — where \(w(u, v)\) is the cost of traversing edge \((u, v)\) — the **shortest path** from source \(s\) to target \(t\) is the sequence of vertices \((s, v_1, v_2, \ldots, t)\) that minimises total edge weight.

### Why BFS Is Not Enough

BFS finds the path with the fewest edges. On an unweighted graph, that is the shortest path. On a weighted graph, it is not.

```text
        2           9
  A ──────── B ──────── C
  │                     │
  └─────────────────────┘
              1
```

BFS would find A → B → C (two edges). The shortest path by cost is A → C directly (cost 1). BFS never considers it because it has "one more hop." The moment edge weights differ, hop count is the wrong metric.

The fix is to stop expanding nodes in the order they were discovered and start expanding them in order of **accumulated cost**. That is precisely what Dijkstra does.


## 3) Dijkstra's Algorithm

### Intuition

Imagine you are standing at a city centre with a map. You do not know the whole layout, but you can see the roads immediately around you and their lengths. Dijkstra's strategy is simple: always take the cheapest road you have seen so far. Never commit to a longer path while a shorter one is still available.

More formally — maintain a set of **settled** nodes whose shortest distance from the source is confirmed, and a **frontier** of candidate nodes seen but not yet settled. At each step, settle the frontier node with the lowest accumulated cost, then update the costs of its unvisited neighbours.

### Running Example

We will trace Dijkstra on a small city network throughout this section. All edges are directed; weights represent travel time in minutes.

```text
              4           2
  A (0) ──────── B ─────────── E
    │            │               \
  3 │          1 │             3  \
    │            │                 \
    └──── C ─────┘────── D ─────────┘
         (?)    5        (?)
```

Restated as a clean edge list:

```text
  A → B   cost 4
  A → C   cost 3
  B → C   cost 1
  B → E   cost 2
  C → D   cost 5
  D → E   cost 3
```

Source: A. Goal: find shortest distance from A to every other node.

### Step-by-Step Trace

**Initialise.** Set dist[A] = 0, all others = ∞. Add A to the frontier.

| Step | Settled | dist[A] | dist[B] | dist[C] | dist[D] | dist[E] |
| :--- | :--- | :---: | :---: | :---: | :---: | :---: |
| Init | — | 0 | ∞ | ∞ | ∞ | ∞ |
| 1 | A | 0 | 4 | 3 | ∞ | ∞ |
| 2 | C | 0 | 4 | 3 | 8 | ∞ |
| 3 | B | 0 | 4 | 3 | 8 | 6 |
| 4 | E | 0 | 4 | 3 | 8 | 6 |
| 5 | D | 0 | 4 | 3 | 8 | 6 |

**Step 1 — Settle A (cost 0).** Relax its neighbours: dist[B] = 4, dist[C] = 3.

**Step 2 — Settle C (cost 3, cheapest frontier node).** Relax: dist[D] = 3 + 5 = 8.

**Step 3 — Settle B (cost 4).** Relax: dist[E] = 4 + 2 = 6. D is already 8, B → C would give 4 + 1 = 5 — but C is already settled, so we skip it.

**Step 4 — Settle E (cost 6).** No outgoing edges. Nothing to relax.

**Step 5 — Settle D (cost 8).** D → E would give 8 + 3 = 11, which is worse than the current dist[E] = 6. No update.

**Result:**

```text
  A → A: 0
  A → B: 4
  A → C: 3
  A → D: 8
  A → E: 6   (via A → B → E)
```

### The Relaxation Step

The core operation in Dijkstra is **edge relaxation**. When settling node \(u\), for each neighbour \(v\):

```text
  if dist[u] + w(u, v) < dist[v]:
      dist[v] = dist[u] + w(u, v)
      prev[v] = u
```

The `prev` array lets you reconstruct the actual path by tracing back from the target. This is how GPS tells you to "turn left in 200m" — it is walking the `prev` chain from your destination back to your current position, then reversing it.


## 4) How Dijkstra Works Internally

### The Priority Queue

Dijkstra's efficiency depends entirely on how fast you can answer the question: *which frontier node has the lowest cost?* Scanning the full frontier each time costs \(O(V)\) per step — too slow for large graphs.

The answer is a **min-heap priority queue**. A min-heap always returns the minimum element in \(O(\log n)\) time. Insert a node with its current cost; when you settle a node, pop the minimum.

```text
  After Step 1 (settling A):

  Priority Queue (min-heap):
  ┌──────────┬──────┐
  │  Node    │ Cost │
  ├──────────┼──────┤
  │  C       │  3   │  ← next to settle
  │  B       │  4   │
  └──────────┴──────┘
```

Each node is pushed to the heap when its distance is updated. It is settled (popped) when it reaches the top. If a node is popped but already settled, it is a stale entry — skip it.

### Complexity

| Operation | Count | Cost each | Total |
| :--- | :--- | :--- | :--- |
| Push to heap (one per edge relaxation) | \(O(E)\) | \(O(\log E)\) | \(O(E \log E)\) |
| Pop from heap (one per node) | \(O(V)\) | \(O(\log V)\) | \(O(V \log V)\) |
| **Overall** | | | **\(O((V + E) \log V)\)** |

Since \(E \leq V^2\), \(\log E \leq 2 \log V\), so the bound simplifies cleanly.

!!! tip "Why Dijkstra is greedy — and why that is safe"
    Dijkstra belongs to the family of greedy algorithms: it makes the locally optimal choice at each step (settle the cheapest node) and never revisits it. This works because all edge weights are non-negative. Once a node is settled, no future path can improve on its cost — any path through an unsettled node would be at least as expensive. If negative weights exist, this guarantee breaks, and you need Bellman-Ford instead.


## 5) A* Algorithm

### The Problem with Dijkstra

Dijkstra is optimal and complete, but it is also **blind**. It expands nodes purely by accumulated cost, with no sense of direction. On a city map, if you are navigating from London to Edinburgh, Dijkstra will happily explore roads toward Brighton, Plymouth, and Dover before making meaningful progress northward — all because their accumulated costs happen to be low.

A\* fixes this by giving the algorithm a compass.

### The Heuristic Function

A\* adds a **heuristic** \(h(n)\) to each node — an estimate of the remaining cost from that node to the target. Instead of sorting the frontier by accumulated cost \(g(n)\), A\* sorts by:

```text
  f(n) = g(n) + h(n)

  g(n)  =  actual cost to reach n from the source
  h(n)  =  estimated cost from n to the target
  f(n)  =  estimated total cost of the path through n
```

This steers expansion toward nodes that look promising — low accumulated cost *and* close to the goal.

### Running Example

Same city graph, same source A, target is now specifically E. We add straight-line distance estimates (the heuristic) for each node:

```text
  h(A) = 7   h(B) = 3   h(C) = 6   h(D) = 3   h(E) = 0
```

| Step | Settled | g(n) | h(n) | f(n) | Notes |
| :--- | :--- | :---: | :---: | :---: | :--- |
| Init | — | A=0 | 7 | 7 | Start |
| 1 | A | B=4, C=3 | 3, 6 | 7, 9 | B has lower f — expand B first |
| 2 | B | E=6, C=3 | 0, 6 | 6, 9 | E has lowest f — settle E |
| 3 | E | — | — | — | Target reached. Stop. |

A\* settled only **3 nodes** to find the optimal path A → B → E (cost 6). Dijkstra settled all 5. On this small graph the difference is minor; on a real road network with millions of nodes, it is the difference between milliseconds and seconds.

```text
  Dijkstra expansion order:   A → C → B → E → D
  A* expansion order:         A → B → E  (done)
```

### Admissibility — The One Rule

A heuristic is **admissible** if it never overestimates the true remaining cost:

```text
  h(n) ≤ actual cost from n to target   for all n
```

An admissible heuristic guarantees that A\* finds the optimal path. If the heuristic overestimates, A\* may skip over the true shortest path chasing what looks like a faster route.

Common admissible heuristics:

| Domain | Heuristic | Why it is admissible |
| :--- | :--- | :--- |
| Grid maps | Manhattan distance | Can never move diagonally, so straight-line count is a lower bound |
| Euclidean space | Straight-line (Euclidean) distance | A straight line is always the shortest possible path |
| Road networks | Haversine distance | Physical distance never exceeds road distance |
| Puzzle solving (8-puzzle) | Number of misplaced tiles | Each tile needs at least one move |

!!! tip "The tighter the heuristic, the faster A* runs"
    An admissible heuristic that constantly returns 0 is technically valid — it makes A\* behave exactly like Dijkstra. A heuristic that consistently estimates close to the true remaining cost prunes far more of the search space. Designing a good heuristic is often the most domain-specific and creative part of applying A\*.


## 6) Dijkstra vs A* — When to Use Which

The choice between the two algorithms comes down to one question: *do you have a reliable, admissible estimate of remaining cost?*

| Aspect | Dijkstra | A\* |
| :--- | :--- | :--- |
| **Goal** | Shortest path from source to all nodes | Shortest path from source to one target |
| **Heuristic** | None — blind expansion | Requires \(h(n)\), domain-specific |
| **Nodes expanded** | All reachable nodes | Subset directed toward target |
| **Optimality** | Always optimal | Optimal if \(h(n)\) is admissible |
| **Best for** | No clear target, or when all distances are needed | Single-target search with spatial or structural intuition |
| **Complexity** | \(O((V + E) \log V)\) | Same worst-case, far less in practice |

!!! tip "Decision rule"
    Use **Dijkstra** when you need shortest paths to *all* nodes, or when no meaningful heuristic exists. Use **A\*** when you have a single target and a domain-specific lower-bound estimate — GPS coordinates give you Euclidean distance, grid worlds give you Manhattan distance. If your heuristic is perfect (equals the true remaining cost exactly), A\* expands zero unnecessary nodes.


## 7) Real-World Applications

The same two algorithms underpin an enormous range of systems — the graph changes, but the logic does not.

- **GPS and navigation:** Google Maps and Apple Maps run variants of A\* over road network graphs with hundreds of millions of nodes, using Euclidean or Haversine distance as the heuristic. Traffic data updates edge weights in real time, which is why your route changes mid-journey.

- **Game AI and pathfinding:** Every NPC that navigates a game world — a soldier flanking an enemy, a courier delivering a package — is almost certainly running A\* over a navigation mesh or grid. The heuristic is Euclidean or Manhattan distance depending on movement rules. Dijkstra is used when the game needs to precompute reachability from a base or spawn point.

- **Network packet routing:** Routing protocols like OSPF (Open Shortest Path First) run Dijkstra on the graph of routers and links, with edge weights representing latency or link cost. Each router independently computes its own shortest-path tree to determine where to forward packets.

- **Robotics and motion planning:** A robot navigating a warehouse or a drone choosing a flight path uses A\* over a spatial graph of positions, with physical distance as the heuristic. Obstacles update edge weights to infinity; the algorithm re-plans around them.

Graph traversal algorithms — BFS, DFS, and cycle detection — are covered in the previous section. Dynamic programming approaches to shortest paths (Bellman-Ford, Floyd-Warshall) are covered in the next.


> "The purpose of Dijkstra's algorithm is not to find a path — it is to find *the* path. A\* is Dijkstra with the wisdom to know which direction to look first."
