# Graph Algorithms: Practice

This page covers the implementation of Dijkstra's algorithm and A* on a real weighted graph. You will work in the `CityGraph` [repository](https://github.com/evisp/dsa-algorithms/tree/main/code) - all scaffold code is already there. Your job is to implement four methods in order.

!!! info "In this page"
    - **Task 1:** `reconstructPath` — shared path-building helper.
    - **Task 2:** `dijkstra` — shortest path using accumulated cost.
    - **Task 3:** `heuristic` — admissible straight-line distance estimate.
    - **Task 4:** `aStar` — guided shortest path using cost + heuristic.

**Repository:** `[repo link](https://github.com/evisp/dsa-algorithms/tree/main/code)`  
**Files to edit:** `CityGraph.java` only. Do not modify `City.java` or `Road.java`.


## The Graph You Are Working With

All four tasks use the same Albanian city network. Study it before you start — you will trace your implementations against it by hand.

```text
        38 km           148 km
Tirana ──────── Durrës ──────── Vlorë
  │    (bidir)          (bidir)
  │ 54 km
  │ (bidir)
Elbasan ─────── Korçë ──────── Gjirokastër
         95 km          80 km
         (bidir)        (one-way)
```

Roads and weights as declared in `main()`:

```java
addRoad(tirana,   durres,       38,  true);
addRoad(tirana,   elbasan,      54,  true);
addRoad(durres,   vlore,       148,  true);
addRoad(elbasan,  korce,        95,  true);
addRoad(korce,    gjirokaster,  80,  false);
```

`City` objects carry `lat` and `lon` fields (decimal degrees) — you will need these in Task 3.


## Task 1 — `reconstructPath`

```java
private List<City> reconstructPath(Map<City, City> prev, City start, City end)
```

=== "Task"
    This helper is called by both Dijkstra and A\*. It receives a `prev` map — where `prev.get(v)` is the node that settled `v` — and traces back from `end` to `start`.

    Implement the method so that it:

    - Walks `prev` from `end` back toward `start`, prepending each city to build the path in the correct order.
    - Returns the complete path as a `List<City>` from `start` to `end`.
    - Returns an **empty list** if no path was found (i.e. `start` is not reachable from the `prev` chain).

    **Verify:**
    ```java
    // Given prev = { Elbasan→Tirana, Korçë→Elbasan }
    // reconstructPath(prev, tirana, korce)
    // → [Tirana, Elbasan, Korçë]

    // Given prev = {} (empty)
    // reconstructPath(prev, tirana, vlore)
    // → []
    ```

??? hint "Hint"
    Use a `LinkedList` and `addFirst` to reverse the path as you walk backwards.
    Walk: `current = end` → `current = prev.get(current)` → stop when `current` is `null`.
    After the loop, check whether the first element equals `start`. If not, no path exists.


## Task 2 — `dijkstra`

```java
public List<City> dijkstra(City start, City end)
```

=== "Task"
    Implement Dijkstra's algorithm. It must return the **minimum-cost path** from `start` to `end` as a `List<City>`, or an empty list if no path exists.

    Requirements:

    - Initialise `dist` for all known cities to `Integer.MAX_VALUE`, then set `dist[start] = 0`.
    - Use a `PriorityQueue` ordered by current known distance.
    - On each step, relax all outgoing roads from the settled city. Update `dist` and `prev` only when a shorter path is found.
    - Stop as soon as `end` is settled.
    - Use `reconstructPath` to return the result.

    **Verify:**
    ```java
    dijkstra(tirana, korce)
    // → [Tirana, Elbasan, Korçë]   total cost: 149 km

    dijkstra(tirana, gjirokaster)
    // → [Tirana, Elbasan, Korçë, Gjirokastër]   total cost: 229 km

    dijkstra(vlore, gjirokaster)
    // → []   (no outgoing road from Vlorë leads toward Gjirokastër)
    ```

    **Then answer in a comment above the method:**
    1. What is the time complexity of your implementation in terms of V and E?
    2. Why is it safe to stop as soon as `end` is settled, rather than waiting for the queue to empty?

??? hint "Hint"
    The comparator for the priority queue should read from the live `dist` map:
    `Comparator.comparingInt(c -> dist.getOrDefault(c, Integer.MAX_VALUE))`.
    When you pop a city, check whether it equals `end` and break if so.
    Stale entries will appear in the queue — ignore them by checking whether the popped cost matches `dist`.


## Task 3 — `heuristic`

```java
private double heuristic(City a, City b)
```

=== "Task"
    Implement the heuristic function used by A\*. It must return the **straight-line distance in kilometres** between two cities using the Haversine formula.

    The Haversine formula for two points \((lat_1, lon_1)\) and \((lat_2, lon_2)\):

    ```text
    dLat = toRadians(lat2 - lat1)
    dLon = toRadians(lon2 - lon1)

    h = sin²(dLat/2) + cos(toRadians(lat1)) · cos(toRadians(lat2)) · sin²(dLon/2)

    distance = 2 · R · arcsin(√h)     where R = 6371.0 km
    ```

    **Verify** (approximate values):
    ```java
    heuristic(tirana, korce)      // ≈ 89.0 km
    heuristic(tirana, gjirokaster) // ≈ 128.0 km
    heuristic(durres, gjirokaster) // ≈ 155.0 km
    ```

    **Then answer in a comment above the method:**
    1. Why is straight-line distance an **admissible** heuristic for road navigation?
    2. What would happen to A\*'s correctness if `heuristic` always returned a value *larger* than the true road distance?

??? hint "Hint"
    `Math.toRadians`, `Math.sin`, `Math.cos`, `Math.sqrt`, and `Math.asin` are all available in `java.lang.Math`.
    The heuristic must never overestimate — road distance is always greater than or equal to straight-line distance, so Haversine is safe.


## Task 4 — `aStar`

```java
public List<City> aStar(City start, City end)
```

=== "Task"
    Implement A\*. The structure is nearly identical to Dijkstra — the only differences are how the priority queue orders nodes and how visited nodes are handled.

    Requirements:

    - Use a `g` map for actual cost from `start` (same role as `dist` in Dijkstra).
    - Order the `PriorityQueue` by `g(n) + heuristic(n, end)`.
    - Add a `visited` set — once a city is settled, skip it if it appears again in the queue.
    - Stop as soon as `end` is settled.
    - Use `reconstructPath` to return the result.

    **Verify:** A\* must return the **same paths** as Dijkstra for all inputs on this graph.
    ```java
    aStar(tirana, korce)          // → [Tirana, Elbasan, Korçë]
    aStar(tirana, gjirokaster)    // → [Tirana, Elbasan, Korçë, Gjirokastër]
    aStar(vlore, gjirokaster)     // → []
    ```

    **Then answer in a comment above the method:**
    1. Dijkstra does not need a `visited` set but A\* does. Why?
    2. On this graph, does A\* settle fewer nodes than Dijkstra to find `tirana → gjirokaster`?
       Trace both by hand and compare the expansion order.

??? hint "Hint"
    The comparator must combine both scores:
    `Comparator.comparingDouble(c -> g.getOrDefault(c, Integer.MAX_VALUE) + heuristic(c, end))`.
    The `visited` check goes at the top of the loop: if the popped city is already in `visited`, skip it with `continue`.
    Everything else — relaxation, `prev` updates, `reconstructPath` — is identical to your Dijkstra.


!!! warning "Edge case checklist"
    Before submitting, verify your solution handles all of the following:

    - **No path exists** — `dijkstra(vlore, gjirokaster)` and `aStar(vlore, gjirokaster)` must both return `[]`, not crash.
    - **Start equals end** — `dijkstra(tirana, tirana)`. What should this return? Check your `reconstructPath` logic.
    - **Non-existent city** — passing a `City` object that was never added to the graph. Both methods must return `[]` immediately.
    - **A\* and Dijkstra agree** — for every input you test, both algorithms must return the same path. If they disagree, your heuristic is not admissible.