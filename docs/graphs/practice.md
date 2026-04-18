# Graph Fundamentals: Practice

This page tests your understanding of graphs through three progressive blocks. Work through them in order; each one shifts from **reading and reasoning** to **designing** to **implementing**.

!!! info "In this page"
    - **Block 1:** Build a graph by hand and read its properties, representations, and structure.
    - **Block 2:** Use case and design decisions — you are the engineering team.
    - **Block 3:** Coding tasks — implement core graph operations on a provided scaffold.


## Block 1 — Build It, Read It, Trace It

Work through the following parts **in order**. Each section builds on the graph you constructed in the previous one. Write your answers on paper before checking any hints.


### Part A — Build the Graph

The following directed edges represent a follow network among computer science pioneers. An edge from X to Y means **X follows Y**.

```
Shannon → Turing
Shannon → Hopper
Turing → Dijkstra
Turing → Knuth
Turing → Lovelace
Dijkstra → Hopper
Dijkstra → Knuth
Knuth → Lovelace
Knuth → Shannon
Hopper → Turing
Lovelace → Dijkstra
McCarthy → Turing
McCarthy → Shannon
```

=== "Task"
    Draw the resulting directed graph. Label every node and draw arrows for every edge.
    Then answer: which nodes have no outgoing edges? What does that mean in this network?

??? hint "Hint"
    Start by drawing all six nodes first: Turing, Dijkstra, Hopper, Knuth, Lovelace, Shannon.
    Then add one directed arrow per edge. A node with no outgoing edges follows nobody.


### Part B — Graph Properties

Using the graph you drew in Part A, answer the following **without writing any code**.

=== "Task"
    For every node, compute:
    1. **In-degree** — how many people follow this person?
    2. **Out-degree** — how many people does this person follow?
    3. **Total degree** — in-degree + out-degree.

    Then answer:
    - Which node has the **highest in-degree**? What does that mean in this network?
    - Which node has the **highest out-degree**? What does that mean?
    - Does the graph contain a **cycle**? If yes, trace the path. If no, explain why not.

??? hint "Hint"
    To find in-degree, count how many arrows *point to* a node.
    To find out-degree, count how many arrows *leave* a node.
    A cycle exists if you can start at a node and return to it by following directed edges.


### Part C — Representations

Still using the same graph from Part A.

=== "Task"
    1. Construct the **adjacency list** for this graph. List every node and its direct neighbours.
    2. Construct the **adjacency matrix** for this graph. Use `1` for an edge, `0` for no edge.
    3. Answer the following using each representation:
        - Does the edge **(Turing → Hopper)** exist?
        - How did you verify it in the adjacency list?
        - How did you verify it in the adjacency matrix?

??? hint "Hint"
    For the adjacency list: index 0 could be Turing, index 1 could be Dijkstra, and so on.
    For the matrix: `matrix[u][v] = 1` means an edge from `u` to `v`.
    To check Turing → Hopper in the matrix, look up `matrix[Turing][Hopper]` directly.


### Part D — Reflect

=== "Task"
    Answer the following in 1–2 sentences each:

    1. This graph has 6 nodes and 6 edges. How many cells does the adjacency matrix store?
       How many of those cells represent an actual edge? What does this tell you about the trade-off?
    2. If Lovelace followed Turing back, would a cycle exist? Trace the exact path.
    3. Shannon has in-degree 0. What does an isolated node with no incoming edges represent
       in this follow network? Is it structurally valid?
    4. What would happen to your adjacency matrix if the network grew from 6 to 600 users
       but each user followed only 5 others on average?


## Block 2 — You Are the Engineering Team

You are a founding engineering team that just received a brief from your product manager. Your job is to make real decisions, justify them, and present your reasoning to the class.

!!! info "The Scenario — ConnectED"
    ConnectED is a university course management system. The system must model **course prerequisites** — before enrolling in a course, a student must have completed specific earlier courses. The data team has modelled this as a graph where each course is a node and a directed edge from course A to course B means *A must be completed before B*. The PM has split the engineering problem into five briefs — one per group.


### Group 1 — Model the Graph

The team is debating how to model the prerequisite relationship.

=== "Brief"
    1. Should this graph be **directed or undirected**? Justify your answer in one sentence.
    2. Should it be **weighted or unweighted**? Is there any useful information an edge weight could carry?
    3. What does a **cycle** mean in this system — for example, CS301 requires CS401, and CS401 requires CS301?
       Is this valid? What should the system do if it detects one?

    **Deliverable:** A short written justification (5–7 sentences) + present your decision to the class. Be ready to defend it.

??? hint "Hint"
    Ask yourself: if A is a prerequisite for B, does that automatically mean B is a prerequisite for A?
    That answer tells you whether the relationship is mutual or one-directional.
    For weights, think about what useful metadata could live on an edge — credit load, expected difficulty, semester gap.


### Group 2 — Choose a Representation

The system has 500 courses and approximately 1,200 prerequisite links.

=== "Brief"
    1. Calculate the space used by an **adjacency matrix** for this graph. Show your working in terms of \(V^2\).
    2. Calculate the space used by an **adjacency list** for this graph. Show your working in terms of \(V + E\).
    3. Which representation do you recommend and why?
    4. Would your answer change if every course required every other course? Why?

    **Deliverable:** A table comparing both representations for this specific scenario + a one-sentence recommendation.

??? hint "Hint"
    Plug in the numbers: V = 500, E = 1200. Compute V² and V + E.
    The ratio between those two numbers tells you how dense or sparse the graph is.
    A sparse graph wastes most of the matrix cells.


### Group 3 — Reachability

A student wants to know: *"Starting from Introduction to Programming, can I eventually take Advanced Algorithms?"*

=== "Brief"
    1. Describe in plain language how you would answer this question using the graph.
    2. Which traversal would you use — **BFS or DFS**? Does it matter for this problem? Justify your answer.
    3. Trace your chosen traversal on this small example:
        ```text
        CS101 → CS201 → CS301 → CS401
                      → CS302
        ```
        Starting from CS101, can you reach CS401? Show every step.

    **Deliverable:** A step-by-step trace of the traversal + a one-paragraph written justification of your traversal choice.

??? hint "Hint"
    Reachability means: is there any path — direct or indirect — from the start node to the target?
    Both BFS and DFS can answer this. Think about which one is simpler to implement and whether the
    shortest path matters here or just whether a path exists at all.


### Group 4 — Cycle Detection

A faculty member submits a new course that creates the following dependency:

```text
CS301 requires CS401
CS401 requires CS301
```

=== "Brief"
    1. Draw this subgraph. What structure does it form?
    2. Why is this cycle a problem for students trying to enrol?
    3. The system needs to **reject** this submission before saving it to the database.
       In plain pseudocode, describe how you would detect the cycle before it is accepted.
    4. Is it possible to have a cycle involving more than two courses? Give one example.

    **Deliverable:** A drawn diagram of the cycle + pseudocode for cycle detection + a one-sentence error message
    the system should display to the faculty member.

??? hint "Hint"
    A cycle means you can follow directed edges and return to where you started.
    Think about what happens during a DFS when you visit a node you have already seen
    in the *current* traversal path — that is your cycle detection signal.


### Group 5 — Scaling *(Extension)*

ConnectED grows from one university to a national platform with 50,000 courses across 200 institutions.

=== "Brief"
    1. With 50,000 nodes, what is the size of an adjacency matrix in cells? Is this feasible?
    2. Traversal now needs to explore potentially thousands of prerequisite chains.
       What is the time complexity of BFS/DFS in terms of \(V + E\)? Does this scale?
    3. Research one real system that models course or task dependencies as a graph
       (e.g. build tools like Maven or Gradle, or workflow engines). How do they handle scale?

    **Deliverable:** A one-slide pitch — *"At 50,000 nodes we recommend switching to X because..."*
    Presented to the class in 3 minutes.

??? hint "Hint"
    At 50,000 nodes, compute V² to see how large the matrix becomes.
    For the traversal question, remember that \(O(V + E)\) is generally considered efficient —
    the question is whether E can grow dangerously large in a dense prerequisite network.


### Presentation Rules

Each group has **10 minutes** to prepare and **3–5 minutes** to present. After each presentation, other groups may challenge with one question.

!!! warning "Cross-Group Dependency"
    Group 2's representation choice depends on the graph type that Group 1 decided on.
    Group 3 and Group 4 both traverse the same graph structure.
    Group 5 builds directly on the representation Group 2 recommended.
    Listen to each other's presentations — your answers may need to reference them.


## Block 3 — Implementation Tasks

The following tasks use the `Graph` scaffold below. Copy it into a new file `Graph.java` and implement each method in the order given. Test your solution locally before moving to the next task.

### The Scaffold

```java
import java.util.*;

public class Graph {

    private Map<String, List<String>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    // ── Task 1 ─────────────────────────────────────────────
    public void addVertex(String vertex) {
        // your code here
    }

    public void addEdge(String from, String to) {
        // your code here
    }

    public void printAdjacencyList() {
        // your code here
    }

    // ── Task 2 ─────────────────────────────────────────────
    public int[] getDegree(String vertex) {
        // Return int[] { inDegree, outDegree, totalDegree }
        // your code here
        return new int[]{0, 0, 0};
    }

    // ── Task 3 ─────────────────────────────────────────────
    public boolean hasEdge(String from, String to) {
        // your code here
        return false;
    }

    // ── Task 4 (Extension) ─────────────────────────────────
    public boolean hasPath(String from, String to) {
        // your code here
        return false;
    }
}
```


### Task 1 — `addVertex`, `addEdge`, `printAdjacencyList`

Before you can do anything with a graph, you need to be able to build one.

=== "Task"
    Implement the three foundational methods:

    - `addVertex(String vertex)` — adds a node to the graph with an empty neighbour list.
      If the vertex already exists, do nothing.
    - `addEdge(String from, String to)` — adds a directed edge from one node to another.
      If either vertex does not exist, add it first before creating the edge.
    - `printAdjacencyList()` — prints every vertex and its list of neighbours, one per line.

    **Expected output** for the ConnectED sample:
    ```
    CS101 → [CS201]
    CS201 → [CS301, CS302]
    CS301 → [CS401]
    CS302 → []
    CS401 → []
    ```

    **Then answer in a comment:** what happens if you call `addEdge("CS101", "CS201")` twice?
    Should your implementation allow duplicate edges? How would you prevent them?

??? hint "Hint"
    `addVertex` should check `adjacencyList.containsKey(vertex)` before doing anything.
    `addEdge` should call `addVertex` on both nodes to be safe, then add `to` to the list of `from`.
    For `printAdjacencyList`, iterate over every entry in the map and print key → value.


### Task 2 — `getDegree(String vertex)`

Given a node, compute how connected it is.

=== "Task"
    Implement `getDegree(String vertex)` so that it returns an array of three integers:
    `{ inDegree, outDegree, totalDegree }`.

    - **Out-degree:** how many nodes this vertex points to. This is easy to find — look at its neighbour list.
    - **In-degree:** how many nodes point to this vertex. This requires scanning the entire graph.
    - **Total degree:** in-degree + out-degree.

    **Constraints:**
    - If the vertex does not exist in the graph, return `{ -1, -1, -1 }`.
    - Do not add any new fields to the class.

    **Verify on the ConnectED sample:**
    - `getDegree("CS201")` → `{ 1, 2, 3 }`
    - `getDegree("CS401")` → `{ 1, 0, 1 }`

    **Then answer in a comment:** what is the time complexity of computing in-degree this way?
    How would you reduce it if in-degree queries needed to be fast and frequent?

??? hint "Hint"
    Out-degree = `adjacencyList.get(vertex).size()`.
    For in-degree, loop through every vertex's neighbour list and count how many times
    `vertex` appears as a neighbour.


### Task 3 — `hasEdge(String from, String to)`

Given two nodes, check whether a direct connection exists between them.

=== "Task"
    Implement `hasEdge(String from, String to)` so that it returns `true` if a directed edge
    from `from` to `to` exists, and `false` otherwise.

    **Constraints:**
    - If either vertex does not exist, return `false`.
    - Do not traverse the entire graph — use the adjacency list directly.

    **Verify on the ConnectED sample:**
    - `hasEdge("CS101", "CS201")` → `true`
    - `hasEdge("CS201", "CS101")` → `false`
    - `hasEdge("CS101", "CS401")` → `false`

    **Then answer in a comment above the method:**
    1. What is the time complexity of this method using an adjacency list?
    2. What would it be if you used an adjacency matrix instead?
    3. Which is faster for this specific operation, and why?

??? hint "Hint"
    Check that `from` exists in the map, then check whether `adjacencyList.get(from)` contains `to`.
    One null check, one list contains check — that is the whole method.


### Task 4 — `hasPath(String from, String to)` *(Extension)*

A direct edge tells you about immediate connections. A path tells you about reachability across the whole graph.

=== "Task"
    Implement `hasPath(String from, String to)` so that it returns `true` if any path —
    direct or through intermediate nodes — exists from `from` to `to`.

    **Constraints:**
    - If either vertex does not exist, return `false`.
    - A vertex is not considered a path to itself unless there is an explicit cycle.
    - You must track visited nodes to avoid infinite loops.
    - Choose either BFS or DFS. State your choice and justify it in a comment above the method.

    **Verify on the ConnectED sample:**
    - `hasPath("CS101", "CS401")` → `true`
    - `hasPath("CS401", "CS101")` → `false`
    - `hasPath("CS302", "CS401")` → `false`

    **Then answer in a comment:**
    1. What data structure does your traversal use internally?
    2. What is the time complexity of `hasPath` in terms of \(V + E\)?
    3. Would the result ever differ between BFS and DFS for this method? Why or why not?

??? hint "Hint"
    Start at `from`. Add it to a visited set and a queue (BFS) or stack (DFS).
    At each step, take the next node, check if it equals `to` — if yes, return `true`.
    Otherwise, add all unvisited neighbours to the queue/stack and mark them visited.
    If you exhaust all reachable nodes without finding `to`, return `false`.



!!! warning "Edge case checklist"
    Before submitting any task, verify your solution handles:

    - **Empty graph** — calling any method on a graph with no vertices.
    - **Non-existent node** — querying a vertex that was never added.
    - **Self-loop** — `addEdge("CS101", "CS101")`. Is this valid? Does your code handle it without crashing?
    - **Disconnected components** — a node that has no edges to or from any other node.

