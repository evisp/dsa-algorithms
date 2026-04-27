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

The following tasks use the scaffold below. Copy the three files into your project and implement each method in order. Test locally before moving on.

### The Scaffold

**`City.java`**
```java
public class City {
    public String name;
    public String type; // "capital", "port", "town"

    public City(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override public String toString() { return name + " (" + type + ")"; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof City)) return false;
        return this.name.equals(((City) o).name);
    }

    @Override public int hashCode() { return name.hashCode(); }
}
```

**`Road.java`**
```java
public class Road {
    public City from;
    public City to;
    public int distanceKm;
    public boolean isBidirectional;

    public Road(City from, City to, int distanceKm, boolean isBidirectional) {
        this.from = from;
        this.to = to;
        this.distanceKm = distanceKm;
        this.isBidirectional = isBidirectional;
    }

    @Override public String toString() { return "Road to " + to.name + " (" + distanceKm + "km)"; }
}
```

**`CityGraph.java`**
```java
import java.util.*;

public class CityGraph {

    private HashMap<City, List<Road>> adjacencyList;

    public CityGraph() {
        this.adjacencyList = new HashMap<>();
    }

    // ── Task 1 ─────────────────────────────────────────────
    public void addCity(City city) {
        // your code here
    }

    public void addRoad(City from, City to, int distanceKm, boolean isBidirectional) {
        // your code here
    }

    public void printMap() {
        // your code here
    }

    // ── Task 2 ─────────────────────────────────────────────
    public int[] getDegree(City city) {
        // Return int[] { inDegree, outDegree, totalDegree }
        // your code here
        return new int[]{0, 0, 0};
    }

    // ── Task 3 ─────────────────────────────────────────────
    public boolean hasRoad(City from, City to) {
        // your code here
        return false;
    }

    // ── Task 4 (Extension) ─────────────────────────────────
    public boolean hasRoute(City from, City to) {
        // your code here
        return false;
    }

    // ── Sample data ────────────────────────────────────────
    public static void main(String[] args) {
        CityGraph albania = new CityGraph();

        City tirana      = new City("Tirana",       "capital");
        City durres      = new City("Durrës",        "port");
        City elbasan     = new City("Elbasan",       "town");
        City vlore       = new City("Vlorë",         "port");
        City korce       = new City("Korçë",         "town");
        City gjirokaster = new City("Gjirokastër",   "town");

        albania.addRoad(tirana,   durres,      38,  true);
        albania.addRoad(tirana,   elbasan,     54,  false);
        albania.addRoad(durres,   vlore,      148,  true);
        albania.addRoad(elbasan,  korce,       95,  false);
        albania.addRoad(korce,    gjirokaster, 80,  false);

        albania.printMap();
    }
}
```

### Task 1 — `addCity`, `addRoad`, `printMap`

=== "Task"
    Implement the three foundational methods:

    - `addCity(City city)` — adds a city to the graph with an empty road list. If it already exists, do nothing.
    - `addRoad(City from, City to, int distanceKm, boolean isBidirectional)` — adds a directed road between two cities. If either city does not exist, add it first. If `isBidirectional` is `true`, add a road in both directions.
    - `printMap()` — prints every city and its outgoing roads, one city per line.

    **Then answer:** what happens if `addRoad(tirana, durres, 38, true)` is called twice? Should duplicate roads be allowed? How would you prevent them?

??? hint "Hint"
    `addCity` should check `adjacencyList.containsKey(city)` before doing anything.
    `addRoad` should call `addCity` on both ends, create a `Road` object, and add it to `from`'s list.
    If bidirectional, create a second `Road` in reverse and add it to `to`'s list.


### Task 2 — `getDegree(City city)`

=== "Task"
    Implement `getDegree(City city)` so that it returns `{ inDegree, outDegree, totalDegree }`.

    - **Out-degree:** how many roads leave this city — read directly from its road list.
    - **In-degree:** how many roads from other cities lead into this city — requires scanning the whole graph.
    - **Total degree:** in + out.

    **Constraints:** return `{ -1, -1, -1 }` if the city does not exist. Do not add new fields.

    **Verify on the sample data:**
    - `getDegree(tirana)` → `{ 1, 2, 3 }`
    - `getDegree(gjirokaster)` → `{ 1, 0, 1 }`

    **Then answer in a comment:** what is the time complexity of computing in-degree this way? How would you redesign the class to make in-degree lookups faster?

??? hint "Hint"
    Out-degree = `adjacencyList.get(city).size()`.
    For in-degree, loop through every city's road list and count how many `Road` objects have `.to.equals(city)`.

### Task 3 — `hasRoad(City from, City to)`

=== "Task"
    Implement `hasRoad(City from, City to)` returning `true` if a direct directed road exists, `false` otherwise.

    **Constraints:** return `false` if either city does not exist. Do not traverse the whole graph.

    **Verify on the sample data:**
    - `hasRoad(tirana, durres)` → `true`
    - `hasRoad(durres, tirana)` → `true` *(bidirectional)*
    - `hasRoad(tirana, elbasan)` → `true`
    - `hasRoad(elbasan, tirana)` → `false` *(one-way)*
    - `hasRoad(tirana, gjirokaster)` → `false`

    **Then answer in a comment:**
    1. What is the time complexity of this method using an adjacency list?
    2. What would it be with an adjacency matrix?
    3. For a country with 500 cities and 800 roads, which representation uses less memory and why?

??? hint "Hint"
    Check `from` exists, then iterate over `adjacencyList.get(from)` and check whether any `Road` has `.to.equals(to)`.

### Task 4 — `hasRoute(City from, City to)` *(Extension)*

=== "Task"
    Implement `hasRoute(City from, City to)` returning `true` if any sequence of roads leads from `from` to `to`.

    **Constraints:** return `false` if either city does not exist. A city is not reachable from itself unless there is an explicit cycle. Track visited cities to avoid infinite loops. Choose BFS or DFS and justify your choice in a comment.

    **Verify on the sample data:**
    
    - `hasRoute(tirana, gjirokaster)` → `true` *(via Elbasan → Korçë)*
    - `hasRoute(gjirokaster, tirana)` → `false`
    - `hasRoute(vlore, korce)` → `false`

    **Then answer in a comment:**

    1. What data structure does your traversal use internally?
    2. What is the time complexity in terms of V + E?
    3. Would BFS and DFS ever return different results for this method? Why or why not?

??? hint "Hint"
    Add `from` to a visited `Set<City>` and a queue (BFS) or stack (DFS).
    At each step, take the next city — if it equals `to`, return `true`.
    Otherwise add all unvisited neighbours and mark them visited.
    If the structure empties without finding `to`, return `false`.

!!! warning "Edge case checklist"
    Before submitting any task, verify your solution handles:

    - **Empty graph** — calling any method on a graph with no vertices.
    - **Non-existent node** — querying a vertex that was never added.
    - **Self-loop** — `addEdge("CS101", "CS101")`. Is this valid? Does your code handle it without crashing?
    - **Disconnected components** — a node that has no edges to or from any other node.

